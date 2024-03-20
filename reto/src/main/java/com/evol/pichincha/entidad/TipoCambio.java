package com.evol.pichincha.entidad;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import lombok.Data;

@Data
@Entity
@Table(name = "tipocambio")
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
@SelectBeforeUpdate
public class TipoCambio extends AuditoriaEntidad {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmoneda", nullable = false)
    private Moneda moneda;

    @Column(name = "preciocompra", nullable = true)
    private Double precioCompra;

    @Column(name = "precioventa", nullable = true)
    private Double precioVenta;

    @Column(name = "fechacambio", nullable = false)
    private Date fechaCambio;

}
