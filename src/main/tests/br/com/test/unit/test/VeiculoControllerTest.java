package br.com.test.unit.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.test.config.Config;
import br.com.test.entity.Veiculo;
import br.com.test.service.VeiculoService;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Adriano S. Bonfiglio
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestRestConfig.class, Config.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.DEFAULT)
public class VeiculoControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private VeiculoService veiculoServiceMock;

	@Autowired
	private org.springframework.web.context.WebApplicationContext webApplicationContext;

	/**
	 * 
	 */
	@Before
	public void setUp() {
		veiculoServiceMock = Mockito.mock(VeiculoService.class);
		Mockito.reset(veiculoServiceMock);
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void findAll() throws Exception {
		Veiculo veiculo1 = new Veiculo();
		veiculo1.setModelo("Modelo 1");
		veiculo1.setFabricante("Fabricante 1");
		veiculo1.setAno(new Long(1));
		veiculo1.setFoto("foto 1");

		Veiculo veiculo2 = new Veiculo();
		veiculo2.setModelo("Modelo 2");
		veiculo2.setFabricante("Fabricante 2");
		veiculo2.setAno(new Long(2));
		veiculo2.setFoto("foto 2");

		when(veiculoServiceMock.list()).thenReturn(
				Arrays.asList(veiculo1, veiculo2));

		mockMvc.perform(get("/veiculos"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType("application/json;charset=UTF-8"))
				// .andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].modelo", is("New Fiesta")));

		// verify(veiculoServiceMock, times(1)).list();
		// verifyNoMoreInteractions(veiculoServiceMock);
	}

	/**
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void save() throws IOException, Exception {
		mockMvc.perform(
				post("/veiculos/save")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("fabricante", "Teste")
						.param("modelo", "Teste Modelo")
						.param("ano", "2015")
						.param("foto", "foto"))
				.andExpect(status().isCreated());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void delete() throws Exception {
		mockMvc.perform(get("/delete/{id}").param("id", "6"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType("application/json;charset=UTF-8"))
				.andExpect(
						jsonPath("$[0].message",
								is("Veículo deletado com sucesso")));
	}
}
