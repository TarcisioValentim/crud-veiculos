package br.com.test.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.test.entity.Veiculo;
import br.com.test.exceptions.BusinessException;
import br.com.test.service.VeiculoService;

/**
 * @author Adriano S. Bonfiglio
 *
 */
@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoController{

	@Autowired
	VeiculoService veiculoService;

	/**
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Veiculo> list() {
		List<Veiculo> veiculos = veiculoService.list();
		return veiculos;
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Veiculo find(@PathVariable Integer id) {
		Veiculo veiculo = veiculoService.find(id);
		return veiculo;
	}

	/**
	 * @param veiculo
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Veiculo> save(@RequestBody @Valid Veiculo veiculo, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println(fieldError);
			}
			veiculo.setErrors(bindingResult.getFieldErrors());

			return new ResponseEntity<Veiculo>(veiculo,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			veiculoService.save(veiculo);
		} catch (BusinessException e) {
			return new ResponseEntity<Veiculo>(veiculo,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Veiculo>(veiculo, HttpStatus.CREATED);

	}

	/**
	 * @param id
	 * @param veiculo
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public ResponseEntity<Veiculo> update(@RequestBody @Valid Veiculo veiculo, @PathVariable Integer id, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			veiculo.setErrors(bindingResult.getFieldErrors());
			return new ResponseEntity<Veiculo>(veiculo,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			veiculoService.update(id, veiculo);
		} catch (BusinessException e) {
			return new ResponseEntity<Veiculo>(veiculo,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Veiculo>(veiculo, HttpStatus.OK);

	}

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, String>> delete(@PathVariable Integer id) {
		Map<String, String> message = new HashMap<String, String>();
		try {
			Veiculo veiculo = veiculoService.find(id);
			veiculoService.delete(veiculo);
		} catch (Exception e) {
			e.printStackTrace();
			message.put("message", "Problemas ao deletar veículo");
			return new ResponseEntity<Map<String, String>>(message,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		message.put("message", "Veículo deletado com sucesso");
		return new ResponseEntity<Map<String, String>>(message, HttpStatus.OK);
	}

}
