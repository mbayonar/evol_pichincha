package com.evol.pichincha.servicio.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evol.pichincha.entidad.Moneda;
import com.evol.pichincha.entidad.TipoCambio;
import com.evol.pichincha.enums.NombreEntidadEnum;
import com.evol.pichincha.excepcion.EntidadDuplicadaExcepcion;
import com.evol.pichincha.repositorio.MonedaRepositorio;
import com.evol.pichincha.repositorio.TipoCambioRepositorio;
import com.evol.pichincha.servicio.TipoCambioServicio;
import com.evol.pichincha.util.Criterio;
import com.evol.pichincha.util.RespuestaControlador;
import com.evol.pichincha.util.RespuestaControladorServicio;

import reactor.core.publisher.Mono;

@Service
public class TipoCambioServicioImpl extends BaseServicioImpl<TipoCambio, Long> implements TipoCambioServicio {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private RespuestaControladorServicio respuestaControladorServicio;

    @Autowired
    private TipoCambioRepositorio tipoCambioRepositorio;

    @Autowired
    private MonedaRepositorio monedaRepositorio;

    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    public TipoCambioServicioImpl(TipoCambioRepositorio tipoCambioRepositorio) {
        super(tipoCambioRepositorio);
    }

    @Override
    public RespuestaControlador crear(TipoCambio tipoCambio) throws EntidadDuplicadaExcepcion {
        this.tipoCambioRepositorio.crear(tipoCambio);
        return this.respuestaControladorServicio.obtenerRespuestaDeExitoCrearConData(NombreEntidadEnum.TIPO_CAMBIO.getValor(), tipoCambio.getId());
    }

    @Override
    public RespuestaControlador actualizar(TipoCambio tipoCambio) throws EntidadDuplicadaExcepcion {
        this.tipoCambioRepositorio.actualizar(tipoCambio);
        return respuestaControladorServicio.obtenerRespuestaDeExitoActualizar(NombreEntidadEnum.TIPO_CAMBIO.getValor());
    }

    @Override
    public RespuestaControlador eliminar(Long tipoCambioId) {
        RespuestaControlador respuesta;
        TipoCambio tipoCambio;
        Boolean puedeEliminar;

        puedeEliminar = true;

        if (puedeEliminar == null || !puedeEliminar) {
            respuesta = RespuestaControlador.obtenerRespuestaDeError("El " + NombreEntidadEnum.TIPO_CAMBIO.getValor().toLowerCase() + " ha sido asignado a uno o varios usuarios y no se puede eliminar");
        } else {
            tipoCambio = tipoCambioRepositorio.obtener(tipoCambioId);
            tipoCambio.setEstado(Boolean.FALSE);
            tipoCambioRepositorio.actualizar(tipoCambio);
            respuesta = respuestaControladorServicio.obtenerRespuestaDeExitoEliminar(NombreEntidadEnum.TIPO_CAMBIO.getValor());
        }

        return respuesta;
    }

    @Override
    public TipoCambio obtener(Long id) {
        TipoCambio tipoCambio;
        Criterio filtro = Criterio.forClass(TipoCambio.class);
        filtro.createAlias("moneda", "mon", JoinType.LEFT_OUTER_JOIN);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        filtro.add(Restrictions.eq("id", id));
        tipoCambio = tipoCambioRepositorio.obtenerPorCriterio(filtro);

        return tipoCambio;
    }

    @Override
    public List<TipoCambio> obtenerTodos() {
        Criterio filtro = Criterio.forClass(TipoCambio.class);
        filtro.createAlias("moneda", "mon", JoinType.LEFT_OUTER_JOIN);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        Criteria busqueda = filtro.getExecutableCriteria(this.sessionFactory.getCurrentSession());
        busqueda.setProjection(null);
        busqueda.setFirstResult(((Criterio) filtro).getFirstResult());
        return (List<TipoCambio>) busqueda.list();
    }

    @Override
    @Transactional
    public Mono<TipoCambio> obtenerTipoCambioDeMoneda(Long monedaId) {
        return Mono.fromSupplier(() -> {
            Criterio filtro = Criterio.forClass(TipoCambio.class);
            filtro.createAlias("moneda", "mon", JoinType.LEFT_OUTER_JOIN);
            filtro.add(Restrictions.eq("estado", Boolean.TRUE));
            filtro.add(Restrictions.eq("mon.id", monedaId));
            filtro.add(Restrictions.eq("fechaCambio", new Date()));
            return tipoCambioRepositorio.obtenerPorCriterio(filtro);
        });
    }

    @Override
    @Transactional
    public Mono<Map<String, Object>> procesarTipoDeCambio(Double monto, Long monedaOrigenId, Long monedaDestinoId) {
        return Mono.zip(
                obtenerTipoCambioDeMoneda(monedaOrigenId),
                obtenerTipoCambioDeMoneda(monedaDestinoId),
                (tipoCambioOrigen, tipoCambioDestino) -> {
                    Map<String, Object> resultado = new HashMap<>();

                    if (tipoCambioOrigen != null && tipoCambioDestino != null
                            && tipoCambioOrigen.getMoneda() != null && tipoCambioDestino.getMoneda() != null) {

                        Double multOrigen = obtenerMultiplicador(tipoCambioOrigen.getMoneda(), tipoCambioDestino.getMoneda(), tipoCambioOrigen.getPrecioVenta());
                        Double multDestino = obtenerMultiplicador(tipoCambioOrigen.getMoneda(), tipoCambioDestino.getMoneda(), tipoCambioDestino.getPrecioVenta());
                        Double multiplicador = multOrigen * multDestino;
                        Double montoEnDestino = monto * multiplicador;

                        resultado.put("montoTipoCambio", montoEnDestino);
                        resultado.put("tipoCambio", multiplicador);
                        resultado.put("monedaOrigen", tipoCambioOrigen.getMoneda().getNombre());
                        resultado.put("monedaDestino", tipoCambioDestino.getMoneda().getNombre());
                        resultado.put("fecha", tipoCambioOrigen.getFechaCambio());
                        resultado.put("extraInfo", "Operación exitosa");
                    } else {
                        resultado.put("precioCompra", null);
                        resultado.put("tipoCambio", null);
                        resultado.put("monedaOrigen", monedaRepositorio.obtener(monedaOrigenId));
                        resultado.put("monedaDestino", monedaRepositorio.obtener(monedaDestinoId));
                        resultado.put("extraInfo", "No se encontró el tipo de cambio para la fecha actual");
                    }

                    resultado.put("monto", monto);
                    return resultado;
                });
    }

    public Double obtenerMultiplicador(Moneda monedaOrigen, Moneda monedaDestino, Double precioVenta) {
        return monedaOrigen.getJerarquia() > monedaDestino.getJerarquia() ? 1 / precioVenta : precioVenta;
    }

}
