package br.com.test.unit.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnit44Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.test.controllers.VeiculoController;
import br.com.test.entity.Veiculo;
import br.com.test.repository.VeiculoRepositoryIntf;
import br.com.test.service.VeiculoService;

/**
 * @author Adriano S. Bonfiglio
 *
 */
@RunWith(MockitoJUnit44Runner.class)
//@ContextConfiguration(classes = { TestRestConfig.class, Config.class})
//@WebAppConfiguration
public class VeiculoControllerTest {

	private MockMvc mockMvc;
	
	@InjectMocks
	private VeiculoController veiculoController;
	
	@Mock
	private VeiculoService veiculoService;
	
	@Resource
	private VeiculoRepositoryIntf veiculoRepositoryIntf;
	
	@Autowired
	private org.springframework.web.context.WebApplicationContext webApplicationContext;

	/**
	 * 
	 */
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(veiculoController).build();
		//veiculoService = Mockito.mock(VeiculoService.class);
		MockitoAnnotations.initMocks(this);
		//Mockito.reset(veiculoService);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void findAll() throws Exception {
		List<Veiculo> veiculos = new ArrayList<Veiculo>();
		Veiculo veiculo1 = new Veiculo();
		veiculo1.setModelo("Modelo 1");
		veiculo1.setFabricante("Fabricante 1");
		veiculo1.setAno(new Long(1));
		veiculo1.setFoto("foto 1");
		veiculos.add(veiculo1);

		Veiculo veiculo2 = new Veiculo();
		veiculo2.setModelo("Modelo 2");
		veiculo2.setFabricante("Fabricante 2");
		veiculo2.setAno(new Long(2));
		veiculo2.setFoto("foto 2");
		veiculos.add(veiculo2);
	
		//when(veiculoRepositoryIntf.findAll()).thenReturn(veiculos);
		when(veiculoService.list()).thenReturn(veiculos);

		mockMvc.perform(get("/veiculos"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType("application/json;charset=UTF-8"))
				// .andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].modelo", is("Modelo 1")));

	}
	
	@Test
	public void findOne() throws Exception{
		Veiculo veiculo1 = new Veiculo();
		veiculo1.setModelo("Modelo 1");
		veiculo1.setFabricante("Fabricante 1");
		veiculo1.setAno(new Long(1));
		veiculo1.setFoto("foto 1");
	
		when(veiculoService.find(1)).thenReturn(veiculo1);
		mockMvc.perform(get("/veiculos/1"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType("application/json;charset=UTF-8"))
				// .andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$modelo", is("Modelo 1")));
	}
	
	/**
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void save() throws IOException, Exception {
		Veiculo veiculo = new Veiculo();
		veiculo.setFabricante("Teste");
		veiculo.setModelo("Teste Modelo");
		veiculo.setAno(2015L);
		//when(veiculoRepositoryIntf.save(veiculo)).thenReturn(veiculo);
		when(veiculoService.save(veiculo)).thenReturn(veiculo);
		
		mockMvc.perform(
				post("/veiculos/save")
						.contentType(MediaType.APPLICATION_JSON)
						.param("fabricante", veiculo.getFabricante())
						.param("modelo", veiculo.getModelo())
						.param("ano", veiculo.getAno().toString()))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].modelo", is("Teste Modelo")));
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void deleteTest() throws Exception {
		Veiculo veiculo1 = new Veiculo();
		veiculo1.setModelo("Modelo 1");
		veiculo1.setFabricante("Fabricante 1");
		veiculo1.setAno(new Long(1));
		veiculo1.setFoto("foto 1");
		
		Mockito.doNothing().when(veiculoService).delete(veiculo1);
		when(veiculoService.find(1)).thenReturn(veiculo1);
		
		mockMvc.perform(delete("/veiculos/delete/1"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType("application/json;charset=UTF-8"))
				.andExpect(
						jsonPath("$message",
								is("Veículo deletado com sucesso")));
	}
}
