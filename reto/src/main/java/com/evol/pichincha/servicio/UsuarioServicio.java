package com.evol.pichincha.servicio;

import com.evol.pichincha.entidad.Usuario;
import com.evol.pichincha.util.RespuestaControlador;

public interface UsuarioServicio extends BaseServicio<Usuario, Long> {

	public RespuestaControlador logeo(String login, String password);

}