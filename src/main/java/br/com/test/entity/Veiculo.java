package br.com.test.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.validation.FieldError;

/**
 * @author Adriano S. Bonfiglio
 *
 */
@Entity
@Table(name = "veiculo")
public class Veiculo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private Integer id;

	@Column(name = "fabricante")
	@NotNull(message="fabricante não pode ser nulo")
	private String fabricante;
	@Column(name = "modelo", nullable = false, length = 30)
	@NotNull(message="o modelo não pode ser nulo")
	private String modelo;
	@Column(name = "ano", length = 4, nullable = false)
	@NotNull(message="O ano não pode ser nulo")
	private Long ano;
	@Column(name = "foto", length = 45)
	@Null
	private String foto;
	
	@Transient
	Map<String, String> errors = new HashMap<String, String>();

	public Integer getId() {
		return id;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public Long getAno() {
		return ano;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getFoto() {
		return foto;
	}
	
	/**
	 * @param fieldErrors
	 */
	public void setErrors(List<FieldError> fieldErrors) {
		if(!fieldErrors.isEmpty()) {
			for (FieldError fieldError : fieldErrors) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
		}
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}

	
}
