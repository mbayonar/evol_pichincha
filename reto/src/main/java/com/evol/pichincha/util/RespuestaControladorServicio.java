package com.evol.pichincha.util;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RespuestaControladorServicio {

    @Value("${mensaje.crear.error}")
    private String mensajeCrearError;

    @Value("${mensaje.actualizar.error}")
    private String mensajeActualizarError;

    @Value("${mensaje.eliminar.error}")
    private String mensajeEliminarError;

    public RespuestaControlador obtenerRespuestaDeExitoCrear(String nombreEntidad) {
        return RespuestaControlador.obtenerRespuestaDeExito(MessageFormat.format(Constantes.RESPUESTA_CONTROLADOR.MENSAJE_CREAR_EXITO, nombreEntidad));
    }

    public RespuestaControlador obtenerRespuestaDeExitoCrearConData(String nombreEntidad, Object data) {
        RespuestaControlador respuestaControlador = RespuestaControlador.obtenerRespuestaDeExito(MessageFormat.format(Constantes.RESPUESTA_CONTROLADOR.MENSAJE_CREAR_EXITO, nombreEntidad));
        respuestaControlador.setData(data);
        return respuestaControlador;
    }

    public RespuestaControlador obtenerRespuestaDeErrorCrear(String nombreEntidad) {
        return RespuestaControlador.obtenerRespuestaDeError(MessageFormat.format(mensajeCrearError, nombreEntidad));
    }

    public RespuestaControlador obtenerRespuestaDeExitoActualizar(String nombreEntidad) {
        return RespuestaControlador.obtenerRespuestaDeExito(MessageFormat.format(Constantes.RESPUESTA_CONTROLADOR.MENSAJE_ACTUALIZAR_EXITO, nombreEntidad));
    }

    public RespuestaControlador obtenerRespuestaDeExitoActualizarConData(String nombreEntidad, Object data) {
        RespuestaControlador respuestaControlador = RespuestaControlador.obtenerRespuestaDeExito(MessageFormat.format(Constantes.RESPUESTA_CONTROLADOR.MENSAJE_ACTUALIZAR_EXITO, nombreEntidad));
        respuestaControlador.setData(data);
        return respuestaControlador;
    }

    public RespuestaControlador obtenerRespuestaDeErrorActualizar(String nombreEntidad) {
        return RespuestaControlador.obtenerRespuestaDeError(MessageFormat.format(mensajeActualizarError, nombreEntidad));
    }

    public RespuestaControlador obtenerRespuestaDeExitoEliminar(String nombreEntidad) {
        return RespuestaControlador.obtenerRespuestaDeExito(MessageFormat.format(Constantes.RESPUESTA_CONTROLADOR.MENSAJE_ELIMINAR_EXITO, nombreEntidad));
    }

    public RespuestaControlador obtenerRespuestaDeErrorEliminar(String nombreEntidad) {
        return RespuestaControlador.obtenerRespuestaDeError(MessageFormat.format(mensajeEliminarError, nombreEntidad));
    }

    public RespuestaControlador obtenerRespuestaDeErrorDuplicado(String nombreEntidad, String propiedadDuplicada) {
        return RespuestaControlador.obtenerRespuestaDeError(MessageFormat.format(Constantes.RESPUESTA_CONTROLADOR.MENSAJE_DUPLICADO, nombreEntidad, propiedadDuplicada));
    }

    public String obtenerMensajeErrorActualizar(String nombreEntidad) {
        return MessageFormat.format(mensajeActualizarError, nombreEntidad);
    }

    public RespuestaControlador obtenerRespuestaDeErrorObtener(String nombreEntidad) {
        return RespuestaControlador.obtenerRespuestaDeError(MessageFormat.format(Constantes.RESPUESTA_CONTROLADOR.MENSAJE_OBTENER_ERROR, nombreEntidad));
    }

    public RespuestaControlador obtenerRespuestaDeErrorListar(String nombreEntidad) {
        return RespuestaControlador.obtenerRespuestaDeError(MessageFormat.format(Constantes.RESPUESTA_CONTROLADOR.MENSAJE_LISTAR_ERROR, nombreEntidad));
    }

}
