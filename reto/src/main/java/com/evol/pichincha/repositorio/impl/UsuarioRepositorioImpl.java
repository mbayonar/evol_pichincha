package com.evol.pichincha.repositorio.impl;

import org.springframework.stereotype.Repository;

import com.evol.pichincha.entidad.Usuario;
import com.evol.pichincha.repositorio.UsuarioRepositorio;

@Repository
public class UsuarioRepositorioImpl extends BaseRepositorioImpl<Usuario, Long> implements UsuarioRepositorio {

}
