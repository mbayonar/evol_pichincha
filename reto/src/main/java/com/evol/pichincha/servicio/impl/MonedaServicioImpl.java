package com.evol.pichincha.servicio.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evol.pichincha.entidad.Moneda;
import com.evol.pichincha.enums.NombreEntidadEnum;
import com.evol.pichincha.excepcion.EntidadDuplicadaExcepcion;
import com.evol.pichincha.repositorio.MonedaRepositorio;
import com.evol.pichincha.servicio.MonedaServicio;
import com.evol.pichincha.util.RespuestaControlador;
import com.evol.pichincha.util.RespuestaControladorServicio;

@Service
public class MonedaServicioImpl extends BaseServicioImpl<Moneda, Long> implements MonedaServicio {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private RespuestaControladorServicio respuestaControladorServicio;

    @Autowired
    private MonedaRepositorio monedaRepositorio;

    @Autowired
    public MonedaServicioImpl(MonedaRepositorio monedaRepositorio) {
        super(monedaRepositorio);
    }

    @Override
    public RespuestaControlador crear(Moneda moneda) throws EntidadDuplicadaExcepcion {
        this.monedaRepositorio.crear(moneda);
        return this.respuestaControladorServicio.obtenerRespuestaDeExitoCrearConData(NombreEntidadEnum.MONEDA.getValor(), moneda.getId());
    }

    @Override
    public RespuestaControlador actualizar(Moneda moneda) throws EntidadDuplicadaExcepcion {
        this.monedaRepositorio.actualizar(moneda);
        return respuestaControladorServicio.obtenerRespuestaDeExitoActualizar(NombreEntidadEnum.MONEDA.getValor());
    }

    @Override
    public RespuestaControlador eliminar(Long monedaId) {
        RespuestaControlador respuesta;
        Moneda moneda;
        Boolean puedeEliminar;

        puedeEliminar = true;

        if (puedeEliminar == null || !puedeEliminar) {
            respuesta = RespuestaControlador.obtenerRespuestaDeError("El " + NombreEntidadEnum.MONEDA.getValor().toLowerCase() + " ha sido asignado a uno o varios usuarios y no se puede eliminar");
        } else {
            moneda = monedaRepositorio.obtener(monedaId);
            moneda.setEstado(Boolean.FALSE);
            monedaRepositorio.actualizar(moneda);
            respuesta = respuestaControladorServicio.obtenerRespuestaDeExitoEliminar(NombreEntidadEnum.MONEDA.getValor());
        }

        return respuesta;
    }
}
