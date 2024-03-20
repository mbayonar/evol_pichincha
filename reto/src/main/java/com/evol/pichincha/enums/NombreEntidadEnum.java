package com.evol.pichincha.enums;

public enum NombreEntidadEnum {
    
    MONEDA("Moneda"),
    TIPO_CAMBIO("Tipo de cambio"),
    USUARIO("Usuario");
    
    private String valor;

    private NombreEntidadEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
}
