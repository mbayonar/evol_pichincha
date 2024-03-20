package com.evol.pichincha.controlador;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.evol.pichincha.excepcion.EntidadDuplicadaExcepcion;
import com.evol.pichincha.servicio.BaseServicio;
import com.evol.pichincha.util.RespuestaControlador;
import com.evol.pichincha.util.RespuestaControladorServicio;

public abstract class BaseControladorImpl<Entidad, TipoLlave> implements BaseControlador<Entidad, TipoLlave> {

    public final Logger logger = LogManager.getLogger(getClass());

    private final BaseServicio<Entidad, TipoLlave> servicio;

    public final String nombreEntidad;

    @Autowired	
    public RespuestaControladorServicio respuestaControladorServicio;

    public BaseControladorImpl(BaseServicio<Entidad, TipoLlave> servicio, String nombreEntidad) {
        this.servicio = servicio;
        this.nombreEntidad = nombreEntidad;
    }

    @PostMapping
    @Override
    public ResponseEntity<RespuestaControlador> crear(@RequestBody Entidad entidad) {
        RespuestaControlador respuestaControlador;
        try {
            respuestaControlador = servicio.crear(entidad);
        } catch (EntidadDuplicadaExcepcion ede) { // Excepción de entidad duplicada
            respuestaControlador = RespuestaControlador.obtenerRespuestaDeError(ede.getMessage());
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorCrear(nombreEntidad);
        }
        return new ResponseEntity<>(respuestaControlador, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<RespuestaControlador> obtenerTodos() {
        RespuestaControlador respuestaControlador;
        try {
            respuestaControlador = RespuestaControlador.obtenerRespuestaExitoConData(servicio.obtenerTodos());
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorListar(nombreEntidad);
        }
        return new ResponseEntity<>(respuestaControlador, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @Override
    public ResponseEntity<RespuestaControlador> obtener(@PathVariable("id") TipoLlave id) {
        RespuestaControlador respuestaControlador;
        try {
            respuestaControlador = RespuestaControlador.obtenerRespuestaExitoConData(servicio.obtener(id));
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorObtener(nombreEntidad);
        }
        return new ResponseEntity<>(respuestaControlador, HttpStatus.OK);
    }

    @PutMapping
    @Override
    public ResponseEntity<RespuestaControlador> actualizar(@RequestBody Entidad entidad) {
        RespuestaControlador respuestaControlador;
        try {
            respuestaControlador = servicio.actualizar(entidad);
        } catch (EntidadDuplicadaExcepcion ede) {
            respuestaControlador = RespuestaControlador.obtenerRespuestaDeError(ede.getMessage());
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorActualizar(nombreEntidad);
        }
        return new ResponseEntity<>(respuestaControlador, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @Override
    public ResponseEntity<RespuestaControlador> eliminar(@PathVariable("id") TipoLlave id) {
        RespuestaControlador respuestaControlador;
        try {
            respuestaControlador = servicio.eliminar(id);
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorEliminar(nombreEntidad);
        }
        return new ResponseEntity<>(respuestaControlador, HttpStatus.OK);
    }

}