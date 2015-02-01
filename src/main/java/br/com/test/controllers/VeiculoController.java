package br.com.test.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import br.com.test.entity.Veiculo;
import br.com.test.service.VeiculoService;

/**
 * @author Adriano S. Bonfiglio
 *
 */
@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoController extends AbstractController {

	@Autowired
	VeiculoService veiculoService;

	/**
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Veiculo> list() {
		return veiculoService.list();
	}

	/**
	 * @param veiculo
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Veiculo> save(@ModelAttribute @Valid Veiculo veiculo,
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
		veiculoService.save(veiculo);
		return new ResponseEntity<Veiculo>(veiculo, HttpStatus.CREATED);

	}

	/**
	 * @param id
	 * @param veiculo
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public ResponseEntity<Veiculo> update(@PathVariable Integer id,
			@ModelAttribute @Valid Veiculo veiculo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println(fieldError);
			}
			veiculo.setErrors(bindingResult.getFieldErrors());

			return new ResponseEntity<Veiculo>(veiculo,
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		veiculoService.update(id, veiculo);
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
			veiculoService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			message.put("message", "Problemas ao deletar veículo");
			return new ResponseEntity<Map<String, String>>(message,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		message.put("message", "Veículo deletado com sucesso");
		return new ResponseEntity<Map<String, String>>(message, HttpStatus.OK);
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
