package br.com.test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Adriano
 *
 */
@Controller
public class HomeController {

	/**
	 * @return
	 */
	@RequestMapping("/")
	public String index() {
		ModelAndView view = new ModelAndView();
		view.setViewName("index");
		return "forward:/resources/index.html";
	}

}
