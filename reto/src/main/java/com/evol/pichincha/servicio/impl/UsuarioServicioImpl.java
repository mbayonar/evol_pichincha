package com.evol.pichincha.servicio.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.evol.pichincha.entidad.Usuario;
import com.evol.pichincha.enums.NombreEntidadEnum;
import com.evol.pichincha.excepcion.EntidadDuplicadaExcepcion;
import com.evol.pichincha.repositorio.UsuarioRepositorio;
import com.evol.pichincha.servicio.UsuarioServicio;
import com.evol.pichincha.util.Constantes;
import com.evol.pichincha.util.Criterio;
import com.evol.pichincha.util.RespuestaControlador;
import com.evol.pichincha.util.RespuestaControladorServicio;
import com.evol.pichincha.util.SistemaUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UsuarioServicioImpl extends BaseServicioImpl<Usuario, Long> implements UsuarioServicio {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private RespuestaControladorServicio respuestaControladorServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    protected SessionFactory sessionFactory;
    
    @Autowired
    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        super(usuarioRepositorio);
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public RespuestaControlador crear(Usuario usuario) throws EntidadDuplicadaExcepcion {
        this.usuarioRepositorio.crear(usuario);
        return this.respuestaControladorServicio.obtenerRespuestaDeExitoCrearConData(NombreEntidadEnum.USUARIO.getValor(), usuario.getId());
    }

    @Override
    public RespuestaControlador actualizar(Usuario usuario) throws EntidadDuplicadaExcepcion {
        this.usuarioRepositorio.actualizar(usuario);
        return respuestaControladorServicio.obtenerRespuestaDeExitoActualizar(NombreEntidadEnum.USUARIO.getValor());
    }

    @Override
    public RespuestaControlador eliminar(Long userId) {
        RespuestaControlador respuesta;
        Usuario usuario;
        Boolean puedeEliminar;

        puedeEliminar = true;

        if (puedeEliminar == null || !puedeEliminar) {
            respuesta = RespuestaControlador.obtenerRespuestaDeError("El " + NombreEntidadEnum.USUARIO.getValor().toLowerCase() + " ha sido asignado a uno o varios usuarios y no se puede eliminar");
        } else {
            usuario = usuarioRepositorio.obtener(userId);
            if (SistemaUtil.esNoNulo(usuario)) {
            	usuario.setEstado(Boolean.FALSE);
            }
            usuarioRepositorio.actualizar(usuario);
            respuesta = respuestaControladorServicio.obtenerRespuestaDeExitoEliminar(NombreEntidadEnum.USUARIO.getValor());
        }

        return respuesta;
    }

    @Override
    public Usuario obtener(Long id) {
    	Usuario usuario;
        Criterio filtro = Criterio.forClass(Usuario.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        filtro.add(Restrictions.eq("id", id));
        usuario = usuarioRepositorio.obtenerPorCriterio(filtro);
        return usuario;
    }

    @Override
    public List<Usuario> obtenerTodos() {
        Criterio filtro = Criterio.forClass(Usuario.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));

        return usuarioRepositorio.buscarPorCriteriaSinProyecciones(filtro);
    }

    @Override
	public RespuestaControlador logeo(String login, String contrasena) {
    	RespuestaControlador respuesta = RespuestaControlador.obtenerRespuestaDeError(Constantes.RESPUESTA_CONTROLADOR.MENSAJE_ERROR_AUTENTICACION);
		String token;
		Criterio filtro;

		filtro = Criterio.forClass(Usuario.class);
		filtro.add(Restrictions.eq("login", login));
		filtro.add(Restrictions.eq("password", contrasena));
		filtro.add(Restrictions.eq("estado", Boolean.TRUE));

		token = "";
		Usuario usuarioSession = usuarioRepositorio.obtenerPorCriterio(filtro);
		if (SistemaUtil.esNoNulo(usuarioSession)) {
			token = this.generarJWToken(login);
            usuarioSession.setToken(token);
            this.usuarioRepositorio.actualizar(usuarioSession);
            respuesta = RespuestaControlador.obtenerRespuestaExitoConData(usuarioSession);
		}
		return respuesta;
	}

	private String generarJWToken(String usuario) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("softtekJWT").setSubject(usuario)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}