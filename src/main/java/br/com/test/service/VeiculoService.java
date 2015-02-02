package br.com.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.test.entity.Veiculo;
import br.com.test.repository.VeiculoRepositoryIntf;

/**
 * @author Adriano S. Bonfiglio
 *
 */
@Service
public class VeiculoService {
	
	@Resource
	VeiculoRepositoryIntf veiculoRepository;
	
	/**
	 * @return
	 */
	public List<Veiculo> list() {
		return veiculoRepository.findAll();
	}
	
	/**
	 * @param veiculo
	 * @return
	 */
	public Veiculo save(Veiculo veiculo) {
		return veiculoRepository.save(veiculo);
	}
	
	public Veiculo update(Integer id, Veiculo veiculo) {
		Veiculo persistedVeiculo = veiculoRepository.findOne(id);
		if(persistedVeiculo != null) {
			persistedVeiculo.setAno(veiculo.getAno());
			persistedVeiculo.setFabricante(veiculo.getFabricante());
			persistedVeiculo.setFoto(veiculo.getFoto());
			persistedVeiculo.setModelo(veiculo.getModelo());
			veiculoRepository.save(persistedVeiculo);
		}
		return persistedVeiculo;
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Veiculo find(Integer id) {
		return veiculoRepository.findOne(id);
	}
	
	/**
	 * @param id
	 */
	public void delete(Veiculo veiculo) {
		veiculoRepository.delete(veiculo);
	}
}
