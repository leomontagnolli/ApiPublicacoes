package com.carol.cadv.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.carol.cadv.controller.form.UsuarioForm;
import com.carol.cadv.model.Usuario;
import com.carol.cadv.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@GetMapping
	public List<Usuario> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();	
		return usuarios;	
	}
	@GetMapping("{id}")
	public ResponseEntity<Usuario> usuarioDetalhar (@PathVariable Long id){
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		if(usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario, UriComponentsBuilder uriBuilder) {
		usuarioRepository.save(usuario);
		
		URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(usuario);
	}

	@PutMapping("{id}")
	@Transactional
	public ResponseEntity<Usuario> atualizar (@PathVariable Long id, @RequestBody UsuarioForm usuario) {
		Optional<Usuario> optional = usuarioRepository.findById(id);
		if(optional.isPresent()) {
			Usuario usuarioNovo = optional.get();
			usuarioNovo.setSenha(usuario.getSenha());
			usuarioNovo.setNomeCompleto(usuario.getNomeCompleto());
			return ResponseEntity.ok().body(usuarioNovo);
		}
		
		return ResponseEntity.badRequest().build();
				
	}
	
}
