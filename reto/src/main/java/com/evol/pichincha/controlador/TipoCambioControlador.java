package com.evol.pichincha.controlador;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evol.pichincha.entidad.TipoCambio;
import com.evol.pichincha.enums.NombreEntidadEnum;
import com.evol.pichincha.servicio.TipoCambioServicio;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tipoCambio")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class TipoCambioControlador extends BaseControladorImpl<TipoCambio, Long> implements BaseControlador<TipoCambio, Long> {

    @Autowired
    private TipoCambioServicio tipoCambioServicio;
    
    @Autowired
    public TipoCambioControlador(TipoCambioServicio tipoCambioServicio) {
        super(tipoCambioServicio, NombreEntidadEnum.TIPO_CAMBIO.getValor());
    }

    @PostMapping("procesarTipoDeCambio")
    public Mono<Map<String, Object>> procesarTipoDeCambio(@RequestParam(value = "monto", required = false) Double monto,
                                            @RequestParam(value = "monedaOrigenId", required = false) Long monedaOrigenId,
                                            @RequestParam(value = "monedaDestinoId", required = false) Long monedaDestinoId) {
        
    	Mono<Map<String, Object>> paramsMap = tipoCambioServicio.procesarTipoDeCambio(monto, monedaOrigenId, monedaDestinoId);

        return paramsMap;
    }
}
