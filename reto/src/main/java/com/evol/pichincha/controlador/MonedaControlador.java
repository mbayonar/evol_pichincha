package com.evol.pichincha.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evol.pichincha.entidad.Moneda;
import com.evol.pichincha.enums.NombreEntidadEnum;
import com.evol.pichincha.servicio.MonedaServicio;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("/moneda")
public class MonedaControlador extends BaseControladorImpl<Moneda, Long> implements BaseControlador<Moneda, Long> {

    @Autowired
    public MonedaControlador(MonedaServicio monedaServicio) {
        super(monedaServicio, NombreEntidadEnum.MONEDA.getValor());
    }

}
