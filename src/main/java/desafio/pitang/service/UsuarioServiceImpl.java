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

import desafio.pitang.dao.UsuarioDao;
import desafio.pitang.dto.LoginDto;
import desafio.pitang.dto.TelefoneDto;
import desafio.pitang.dto.TokenDto;
import desafio.pitang.dto.UsuarioDto;
import desafio.pitang.excecoes.AutenticacaoException;
import desafio.pitang.excecoes.AutenticacaoException.TipoErroAutenticacao;
import desafio.pitang.model.Telefone;
import desafio.pitang.model.Usuario;
import desafio.pitang.util.JwtUtil;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  @Autowired
  UsuarioDao usuarioDao;

  @Override
  @Transactional
  public TokenDto logar(LoginDto loginDto) throws AutenticacaoException {
    Usuario usuario = usuarioDao.findUsuarioByEmail(loginDto.getEmail());

    validarCamposLogin(loginDto.getEmail(), loginDto.getPassword(), usuario);
    Date dataLogin = new Date();
    usuario.setDataUltimoLogin(dataLogin);
    usuarioDao.save(usuario);

    String token = JwtUtil.generateToken(loginDto.getEmail(), loginDto.getPassword());
    return new TokenDto(token);
  }

  private void validarCamposLogin(String email, String senha, Usuario usuario)//
      throws AutenticacaoException {
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
  public TokenDto adicionarUsuario(UsuarioDto usuarioDto) throws AutenticacaoException {

    validarCamposCadastrar(usuarioDto);
    Usuario user = converterUsuarioDtoParaUsuarioEntidade(usuarioDto);
    user = usuarioDao.save(user);

    String token = JwtUtil.generateToken(usuarioDto.getEmail(), usuarioDto.getPassword());
    return new TokenDto(token);
  }

  /**
   * Método que trata das validações ao cadastrar um uusário.
   * 
   * @param usuarioDto
   * @throws AutenticacaoException
   */
  public void validarCamposCadastrar(UsuarioDto usuarioDto) throws AutenticacaoException {

    Usuario usuario = usuarioDao.findUsuarioByEmail(usuarioDto.getEmail());

    if (usuario != null) {
      throw new AutenticacaoException(TipoErroAutenticacao.USUARIO_EXISTENTE);
    }

    if (usuarioDto.getEmail() == null || usuarioDto.getEmail().equals("")) {
      throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }

    if (usuarioDto.getPassword() == null || usuarioDto.getPassword().equals("")) {
      throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }

    if (usuarioDto.getFirstName() == null || usuarioDto.getFirstName().equals("")) {
      throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }

    if (usuarioDto.getLastName() == null || usuarioDto.getLastName().equals("")) {
      throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }

    if (usuarioDto.getPhones() == null || usuarioDto.getPhones().isEmpty()) {
      throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }

    for (TelefoneDto tel : usuarioDto.getPhones()) {
      if (tel.getCountry_code() == null || tel.getCountry_code().isEmpty()) {
        throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
      }

      if (tel.getArea_code() == 0 || tel.getNumber() == 0) {
        throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INEXISTENTES);
      }
    }

    Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", //
        Pattern.CASE_INSENSITIVE);
    Matcher matcher = emailPattern.matcher(usuarioDto.getEmail());
    if (!matcher.matches()) {
      throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INVALIDOS);
    }

    Pattern telefonePattern = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
    List<TelefoneDto> lista = usuarioDto.getPhones();
    for (TelefoneDto tel : lista) {
      Matcher matcherNumber = telefonePattern.matcher(String.valueOf(tel.getNumber()));
      Matcher matcherAreaCode = telefonePattern.matcher(String.valueOf(tel.getArea_code()));
      if (!matcherNumber.matches() || !matcherAreaCode.matches()) {
        throw new AutenticacaoException(TipoErroAutenticacao.CAMPOS_INVALIDOS);
      }
    }

  }

  /**
   * Método que converte um objeto do tipo UsuarioDto para um objeto do tipo
   * Usuario.
   * 
   * @param usuarioTransfer
   * @return
   */
  public Usuario converterUsuarioDtoParaUsuarioEntidade(UsuarioDto usuarioTransfer) {

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

  /**
   * Método que forma a lista de objetos do tipo Telefone a partir da lista de
   * objetos do tipo TelefoneDto.
   * 
   * @param usuarioTransfer
   * @param usuario
   * @return
   */
  public List<Telefone> formarListaTelefones(UsuarioDto usuarioTransfer, Usuario usuario) {

    List<Telefone> lista = new ArrayList<Telefone>();

    if (usuarioTransfer.getPhones() != null && !usuarioTransfer.getPhones().isEmpty()) {
      for (desafio.pitang.dto.TelefoneDto tel : usuarioTransfer.getPhones()) {
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

  /**
   * Método que converte um objeto to tipo usuário para um objeto do tipo
   * UsuarioDto.
   * 
   * @param usuario
   * @return
   */
  public UsuarioDto converterUsuarioParaUsuarioDto(Usuario usuario) {

    UsuarioDto usuarioDto = new UsuarioDto();

    usuarioDto.setEmail(usuario.getEmail());
    usuarioDto.setFirstName(usuario.getNome());
    usuarioDto.setLastName(usuario.getSobrenome());
    usuarioDto.setCreated_at(usuario.getDataCriacao());
    usuarioDto.setLast_login(usuario.getDataUltimoLogin());

    List<TelefoneDto> lista = new ArrayList<TelefoneDto>();

    for (Telefone tel : usuario.getTelefones()) {
      TelefoneDto telefone = new TelefoneDto();
      telefone.setArea_code(tel.getCodigoArea());
      telefone.setCountry_code(tel.getCodigoPais());
      telefone.setNumber(tel.getNumero());
      lista.add(telefone);
    }

    usuarioDto.setPhones(lista);

    return usuarioDto;
  }

  @Override
  public UsuarioDto recuperarUsuario(String token) throws AutenticacaoException {

    if (token == null || token.equals("")) {
      throw new AutenticacaoException(TipoErroAutenticacao.NAO_AUTORIZADO);
    }

    String email = JwtUtil.getEmail(token);
    String senha = JwtUtil.getSenha(token);

    Usuario usuario = usuarioDao.findUsuarioByEmail(email);

    validarCamposLogin(email, senha, usuario);

    UsuarioDto usuarioDto = converterUsuarioParaUsuarioDto(usuario);

    return usuarioDto;
  }

}
