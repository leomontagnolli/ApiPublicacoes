package com.carol.cadv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carol.cadv.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
