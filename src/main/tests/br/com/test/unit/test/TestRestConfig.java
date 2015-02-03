package br.com.test.unit.test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.test.repository.VeiculoRepositoryIntf;
import br.com.test.service.VeiculoService;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
public class TestRestConfig{

	@Bean
	public VeiculoService veiculoService() {
		return Mockito.mock(VeiculoService.class);
	}
	
	@Bean
	public VeiculoRepositoryIntf veiculoRepositoryIntf() {
		return Mockito.mock(VeiculoRepositoryIntf.class);
	}
}