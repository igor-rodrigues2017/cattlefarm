package br.com.igorrodrigues.cattlefarm.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.igorrodrigues.cattlefarm.DAOs.AnimalDao;
import br.com.igorrodrigues.cattlefarm.models.Bovine;
import br.com.igorrodrigues.cattlefarm.models.BovineType;
import br.com.igorrodrigues.cattlefarm.models.Sex;

@RestController
@RequestMapping("/api")
public class RestApiController {

	@Autowired
	AnimalDao animalDao;

	@GetMapping(value = "/listaBovinos", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public List<Bovine> listAllBovinesXml() {
		List<Bovine> lsitaBovinos = animalDao.listarTodosBovinos();

		for (Bovine bovine : lsitaBovinos) {
			bovine.setAge();
		}
		return lsitaBovinos;
	}

	@GetMapping(value = "/listaBovinos/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Bovine retornaBovinoJson(@PathVariable Integer id) {
		Bovine bovine = animalDao.find(id);
		bovine.setAge();
		return bovine;
	}

	@GetMapping(value = "/listaBovinosFiltrada", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public List<Bovine> buscarBovino(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "sex", required = false) Sex sex,
			@RequestParam(value = "type", required = false) BovineType type,
			@RequestParam(value = "nick", required = false) String nick,
			@RequestParam(value = "arrobaValue", required = false) BigDecimal arrobavalue) {

		List<Bovine> bovinosFiltrados = animalDao.listarBovinos(id, sex, type, nick);
		Bovine.setAgeOfList(bovinosFiltrados);
		if (arrobavalue != null) {
			for (Bovine bovine : bovinosFiltrados) {
				bovine.setValue(arrobavalue);
			}
			return bovinosFiltrados;
		}
		return bovinosFiltrados;

	}
}