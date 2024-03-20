package com.evol.pichincha.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import lombok.Data;

@Data
@Entity
@Table(name = "moneda")
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
@SelectBeforeUpdate
public class Moneda extends AuditoriaEntidad {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "abreviatura", nullable = false)
    private String abreviatura;

    @Column(name = "jerarquia", nullable = false)
    private int jerarquia;
}
