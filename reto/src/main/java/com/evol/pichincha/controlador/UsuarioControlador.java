package com.evol.pichincha.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evol.pichincha.entidad.Usuario;
import com.evol.pichincha.enums.NombreEntidadEnum;
import com.evol.pichincha.servicio.UsuarioServicio;
import com.evol.pichincha.util.RespuestaControlador;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class UsuarioControlador extends BaseControladorImpl<Usuario, Long> implements BaseControlador<Usuario, Long> {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        super(usuarioServicio, NombreEntidadEnum.USUARIO.getValor());
    }

    @PostMapping("logeo")
    public ResponseEntity<RespuestaControlador> logeo(@RequestParam("login") String login, @RequestParam("password") String password) {
        RespuestaControlador respuestaControlador;
        HttpStatus httpStatus;
        try {
            respuestaControlador = usuarioServicio.logeo(login, password);
            httpStatus = HttpStatus.OK;
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorActualizar(NombreEntidadEnum.USUARIO.getValor());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(respuestaControlador, httpStatus);
    }

}
