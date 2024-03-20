package com.evol.pichincha.servicio;

import java.util.Map;

import com.evol.pichincha.entidad.TipoCambio;

import reactor.core.publisher.Mono;

public interface TipoCambioServicio extends BaseServicio<TipoCambio, Long> {

	public Mono<TipoCambio> obtenerTipoCambioDeMoneda(Long monedaId);

	public Mono<Map<String, Object>> procesarTipoDeCambio(Double monto, Long monedaOrigenId, Long monedaDestinoId);

}
