package com.evol.pichincha.controlador;

import org.springframework.http.ResponseEntity;

import com.evol.pichincha.util.RespuestaControlador;


public interface BaseControlador<Entidad, TipoLlave> {

    public ResponseEntity<RespuestaControlador> crear(Entidad entidad);

    public ResponseEntity<RespuestaControlador> obtener(TipoLlave id);

    public ResponseEntity<RespuestaControlador> actualizar(Entidad entidad);

    public ResponseEntity<RespuestaControlador> eliminar(TipoLlave entidadId);

    public ResponseEntity<RespuestaControlador> obtenerTodos();

}
