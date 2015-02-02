package br.com.test.unit.test;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.test.service.VeiculoService;

@Configuration
public class TestRestConfig{

	@Bean
	public VeiculoService veiculoService() {
		return Mockito.mock(VeiculoService.class);
	}
}