package com.evol.pichincha.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
@SelectBeforeUpdate
public class Usuario extends AuditoriaEntidad {

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

	@Transient
    private String token;

}