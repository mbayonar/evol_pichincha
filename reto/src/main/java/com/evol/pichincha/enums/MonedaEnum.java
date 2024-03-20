package com.evol.pichincha.enums;

public enum MonedaEnum {

    DOLAR(1L, "DOL"),
    SOLES(2L, "PEN"),
    EUROS(3L, "EUR");

    private Long id;
    private String valor;

    private MonedaEnum(Long id, String valor) {
        this.id = id;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
