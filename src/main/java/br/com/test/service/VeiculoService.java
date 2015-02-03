package br.com.test.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import br.com.test.entity.Veiculo;
import br.com.test.exceptions.BusinessException;
import br.com.test.repository.VeiculoRepositoryIntf;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * @author Adriano S. Bonfiglio
 *
 */
@Service
public class VeiculoService {

	@Resource
	private VeiculoRepositoryIntf veiculoRepository;
	@Autowired
	private ResourceLoader resourceLoader = null;

	/**
	 * @return
	 */
	public List<Veiculo> list() {
		return veiculoRepository.findAll();
	}

	/**
	 * @param veiculo
	 * @return
	 * @throws BusinessException
	 */
	public Veiculo save(Veiculo veiculo) throws BusinessException {
		Veiculo v = veiculoRepository.save(veiculo);
		if (v != null) {
			try {
				v.setFoto(v.getId()+".jpg");
				writeFoto(veiculo.getFile().get("base64"), veiculo);
				veiculoRepository.save(v);
			} catch (Base64DecodingException e) {
				throw new BusinessException(
						"Impossível converter aruqivo de foto");
			} catch (IOException e) {
				throw new BusinessException("Falha ao salvar arquivo de foto");
			}
		}
		return v;
	}

	/**
	 * @param id
	 * @param veiculo
	 * @return
	 * @throws BusinessException
	 */
	public Veiculo update(Integer id, Veiculo veiculo) throws BusinessException {
		Veiculo persistedVeiculo = veiculoRepository.findOne(id);
		if (persistedVeiculo != null) {
			String base64 = null;
			if(veiculo.getFile() != null) {
				base64 = veiculo.getFile().get("base64");
			}
			try {
				if (base64 != null) {
					writeFoto(veiculo.getFile().get("base64"), persistedVeiculo);
				}
			} catch (Base64DecodingException e) {
				throw new BusinessException(
						"Impossível converter aruqivo de foto");
			} catch (IOException e) {
				throw new BusinessException("Falha ao salvar arquivo de foto");
			}
			persistedVeiculo.setAno(veiculo.getAno());
			persistedVeiculo.setFabricante(veiculo.getFabricante());
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
	 * @throws BusinessException
	 * @throws IOException
	 */
	public void delete(Veiculo veiculo) throws BusinessException {
		try {
			veiculoRepository.delete(veiculo);
			deleteFoto(veiculo.getFoto());
		} catch (IOException e) {
			throw new BusinessException("Falha ao deletar imagem");
		}
	}

	private String getResourcePath() throws IOException {
		return resourceLoader.getResource("resources/images").getFile()
				.getPath();
	}

	/**
	 * @param base64
	 * @param veiculo
	 * @throws Base64DecodingException
	 * @throws IOException
	 */
	private void writeFoto(String base64, Veiculo veiculo)
			throws Base64DecodingException, IOException {
		byte[] data = Base64.decode(base64);
		try (OutputStream stream = new FileOutputStream(getResourcePath() + "/"
				+ veiculo.getId() + ".jpg")) {
			stream.write(data);
		}
	}

	/**
	 * @param foto
	 * @throws IOException
	 */
	private void deleteFoto(String foto) throws IOException {
		String fotoUrl = getResourcePath() + foto;
		File file = new File(fotoUrl);
		file.delete();
	}
}
