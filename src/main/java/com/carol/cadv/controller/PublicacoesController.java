package com.carol.cadv.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.carol.cadv.model.Publicacao;
import com.carol.cadv.repository.PublicacaoRepository;

@RestController
@RequestMapping("/publicacoes")
public class PublicacoesController {
	
	@Autowired
	private PublicacaoRepository publicacaoRepository;
	
	
	@GetMapping
	@Cacheable(value = "allPublicacoes")
	public List<Publicacao> listarPublicacoes () {
		List<Publicacao> publicacao = publicacaoRepository.findAll();
		return publicacao;
	}
	
	@GetMapping("{id}")
	public Publicacao detalhar(@PathVariable Long id) {
		Publicacao publicao = publicacaoRepository.getOne(id);
		
		return publicao;
		
	}
	
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "allPublicacoes", allEntries = true)
	public ResponseEntity<Publicacao> postPublicacao (@RequestBody Publicacao publicacao , UriComponentsBuilder uriBuilder) {
		publicacaoRepository.save(publicacao);
		URI uri = uriBuilder.path("/publicacoes/{id}").buildAndExpand(publicacao.getId()).toUri();
		return ResponseEntity.created(uri).body(publicacao);
		
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "allPublicacoes", allEntries = true)
	public ResponseEntity<Publicacao> atualizar (@PathVariable Long id, @RequestBody Publicacao publiNova){
		Optional<Publicacao> publicacao = publicacaoRepository.findById(id);
		
		if(publicacao.isPresent()) {
			Publicacao publiAntiga = publicacaoRepository.getOne(id);
			publiAntiga.setData(publiNova.getData());
			publiAntiga.setDescricao(publiNova.getDescricao());
			publiAntiga.setTitulo(publiNova.getTitulo());
			publiAntiga.setUrlImg(publiNova.getUrlImg());
				
			return ResponseEntity.ok().body(publiAntiga);
		}
		return ResponseEntity.badRequest().build();
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "allPublicacoes", allEntries = true)
	public ResponseEntity<?> deletar (@PathVariable Long id) {
		
		Optional<Publicacao> optional = publicacaoRepository.findById(id);
		if(optional.isPresent()) {
			publicacaoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.badRequest().build();
		
	}

}
