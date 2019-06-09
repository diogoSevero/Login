package desafio.pitang.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import desafio.pitang.dao.UsuarioDAO;
import desafio.pitang.dto.LoginDTO;
import desafio.pitang.dto.TelefoneDTO;
import desafio.pitang.dto.TokenDTO;
import desafio.pitang.dto.UsuarioDTO;
import desafio.pitang.excecoes.AutenticacaoException;
import desafio.pitang.excecoes.AutenticacaoException.TipoErroAutenticacao;
import desafio.pitang.model.Telefone;
import desafio.pitang.model.Usuario;
import desafio.pitang.util.JwtUtil;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioDAO usuarioDao;

	@Override
	@Transactional
	public TokenDTO logar(LoginDTO loginDTO) throws AutenticacaoException {
		Usuario usuario = usuarioDao.findUsuarioByEmail(loginDTO.getEmail());

		validarCamposLogin(loginDTO.getEmail(), loginDTO.getPassword(), usuario);
		Date dataLogin = new Date();
		usuario.setDataUltimoLogin(dataLogin);
		usuarioDao.save(usuario);

		String token = JwtUtil.generateToken(loginDTO.getEmail(), loginDTO.getPassword());
		return new TokenDTO(token);
	}

	private void validarCamposLogin(String email, String senha, Usuario usuario) throws AutenticacaoException {
		if (usuario == null) {
			throw new AutenticacaoException(TipoErroAutenticacao.USUARIO_INEXISTENTE_SENHA_ERRADA);
		}

		if (!descriptografiaBase64Decoder(usuario.getSenha()).equals(senha)) {
			throw new AutenticacaoException(TipoErroAutenticacao.USUARIO_INEXISTENTE_SENHA_ERRADA);
		}

		if (email == null || email.equals("")) {
			throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
		}

		if (senha == null || senha.equals("")) {
			throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
		}
	}

	@Override
	@Transactional
	public TokenDTO adicionarUsuario(UsuarioDTO usuarioDTO) throws AutenticacaoException {

		validarCamposCadastrar(usuarioDTO);
		Usuario user = converterUsuarioDtoParaUsuarioEntidade(usuarioDTO);
		user = usuarioDao.save(user);

		String token = JwtUtil.generateToken(usuarioDTO.getEmail(), usuarioDTO.getPassword());
		return new TokenDTO(token);
	}

	public void validarCamposCadastrar(UsuarioDTO usuarioDTO) throws AutenticacaoException {

		Usuario usuario = usuarioDao.findUsuarioByEmail(usuarioDTO.getEmail());

		if (usuario != null) {
			throw new AutenticacaoException(TipoErroAutenticacao.USUARIO_EXISTENTE);
		}

		if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().equals("")) {
			throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
		}

		if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().equals("")) {
			throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
		}

		if (usuarioDTO.getFirstName() == null || usuarioDTO.getFirstName().equals("")) {
			throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
		}

		if (usuarioDTO.getLastName() == null || usuarioDTO.getLastName().equals("")) {
			throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
		}

		Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = emailPattern.matcher(usuarioDTO.getEmail());
		if (!matcher.matches()) {
			throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INVALIDOS);
		}

		Pattern telefonePattern = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
		List<TelefoneDTO> lista = usuarioDTO.getPhones();
		for (TelefoneDTO tel : lista) {
			Matcher matcherNumber = telefonePattern.matcher(String.valueOf(tel.getNumber()));
			Matcher matcherAreaCode = telefonePattern.matcher(String.valueOf(tel.getArea_code()));
			if (!matcherNumber.matches() || !matcherAreaCode.matches()) {
				throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INVALIDOS);
			}
		}

	}

	public Usuario converterUsuarioDtoParaUsuarioEntidade(desafio.pitang.dto.UsuarioDTO usuarioTransfer) {

		Usuario usuario = new Usuario();

		if (usuarioTransfer != null) {
			usuario.setNome(usuarioTransfer.getFirstName());
			usuario.setSobrenome(usuarioTransfer.getLastName());
			usuario.setEmail(usuarioTransfer.getEmail());
			usuario.setSenha(criptografiaBase64Encoder(usuarioTransfer.getPassword()));

			List<Telefone> listaTelefones = formarListaTelefones(usuarioTransfer, usuario);
			usuario.setTelefones(listaTelefones);

		}

		Date data = new Date();

		usuario.setDataCriacao(data);

		return usuario;
	}

	public static String criptografiaBase64Encoder(String valor) {

		return new Base64().encodeToString(valor.getBytes());
	}

	public static String descriptografiaBase64Decoder(String valorCriptografado) {

		return new String(new Base64().decode(valorCriptografado));
	}

	public List<Telefone> formarListaTelefones(desafio.pitang.dto.UsuarioDTO usuarioTransfer, Usuario usuario) {

		List<Telefone> lista = new ArrayList<Telefone>();

		if (usuarioTransfer.getPhones() != null && !usuarioTransfer.getPhones().isEmpty()) {
			for (desafio.pitang.dto.TelefoneDTO tel : usuarioTransfer.getPhones()) {
				Telefone telefone = new Telefone();
				telefone.setCodigoArea(tel.getArea_code());
				telefone.setCodigoPais(tel.getCountry_code());
				telefone.setNumero(tel.getNumber());
				telefone.setUsuario(usuario);
				lista.add(telefone);
			}
		}

		return lista;
	}

	public UsuarioDTO converterUsuarioParaUsuarioDTO(Usuario usuario) {

		UsuarioDTO usuarioDTO = new UsuarioDTO();

		usuarioDTO.setEmail(usuario.getEmail());
		usuarioDTO.setFirstName(usuario.getNome());
		usuarioDTO.setLastName(usuario.getSobrenome());
		usuarioDTO.setCreated_at(usuario.getDataCriacao());
		usuarioDTO.setLast_login(usuario.getDataUltimoLogin());

		List<TelefoneDTO> lista = new ArrayList<TelefoneDTO>();

		for (Telefone tel : usuario.getTelefones()) {
			TelefoneDTO telefone = new TelefoneDTO();
			telefone.setArea_code(tel.getCodigoArea());
			telefone.setCountry_code(tel.getCodigoPais());
			telefone.setNumber(tel.getNumero());
			lista.add(telefone);
		}

		usuarioDTO.setPhones(lista);

		return usuarioDTO;
	}

	@Override
	public UsuarioDTO recuperarUsuario(String token) throws AutenticacaoException {

		if (token == null || token.equals("")) {
			throw new AutenticacaoException(TipoErroAutenticacao.NAO_AUTORIZADO);
		}

		String email = JwtUtil.getEmail(token);
		String senha = JwtUtil.getSenha(token);

		Usuario usuario = usuarioDao.findUsuarioByEmail(email);

		validarCamposLogin(email, senha, usuario);

		UsuarioDTO usuarioDTO = converterUsuarioParaUsuarioDTO(usuario);

		return usuarioDTO;
	}

}
