package br.com.test.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.test.entity.Veiculo;

/**
 * @author Adriano S. Bonfiglio
 *
 */
public interface VeiculoRepositoryIntf extends CrudRepository<Veiculo, Serializable> {

	/**
	 * @see org.springframework.data.repository.CrudRepository#findAll()
	 */
	List<Veiculo> findAll();
	
}
