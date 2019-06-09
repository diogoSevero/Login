package desafio.pitang.login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import desafio.pitang.dto.LoginDto;
import desafio.pitang.dto.TelefoneDto;
import desafio.pitang.dto.TokenDto;
import desafio.pitang.dto.UsuarioDto;
import desafio.pitang.enumeration.TipoErroAutenticacao;
import desafio.pitang.excecoes.AutenticacaoException;
import desafio.pitang.service.UsuarioService;
import desafio.pitang.util.JwtUtil;
import io.jsonwebtoken.MalformedJwtException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TesteRecuperarUsuario {

  @Autowired
  UsuarioService usuarioService;

  @Test
  public void testeRecuperarUsuarioAposCadastrar() {

    UsuarioDto usuarioBefore = null;
    UsuarioDto usuarioAfter = null;

    String firstName = "João";
    String lastName = "Moreira";
    String email = "joaomoreira@pitang.com.br";
    String password = "j0@0m0r31r@";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      TokenDto TokenDto = usuarioService.adicionarUsuario(usuarioBefore);

      String token = TokenDto.getToken().replace("Bearer ", "");
      usuarioAfter = usuarioService.recuperarUsuario(token);

      assertTrue("First name does not match before and after saving",
          usuarioBefore.getFirstName().equals(usuarioAfter.getFirstName()));
      assertTrue("Last name does not match before and after saving",
          usuarioBefore.getLastName().equals(usuarioAfter.getLastName()));
      assertTrue("Email does not match before and after saving",
          usuarioBefore.getEmail().equals(usuarioAfter.getEmail()));
      assertFalse("Password shouldnt match before and after saving",
          usuarioBefore.getPassword().equals(usuarioAfter.getPassword()));

      assertFalse("Created at should not be null", usuarioAfter.getCreated_at() == null);
      assertTrue("Last login should not be set yet", usuarioAfter.getLast_login() == null);

    } catch (AutenticacaoException e) {
      fail(String.format("Authentication error: %s", e.getMessage()));
    }
  }

  @Test
  public void testeRecuperarUsuarioAposLogar() {

    UsuarioDto usuarioBefore = null;
    UsuarioDto usuarioAfter = null;

    String firstName = "Cristóvão";
    String lastName = "Fonseca";
    String email = "cristovaofonseca@pitang.com.br";
    String password = "cr1st0v@0";

    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    LoginDto loginDto = new LoginDto();
    loginDto.setEmail(email);
    loginDto.setPassword(password);

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      // Cadastrar usuario
      usuarioService.adicionarUsuario(usuarioBefore);

      // Logar usuario
      TokenDto token = usuarioService.logar(loginDto);

      // Recuperar usuario
      usuarioAfter = usuarioService.recuperarUsuario(token.getToken().replace("Bearer ", ""));

      assertTrue("First name does not match before and after saving",
          usuarioBefore.getFirstName().equals(usuarioAfter.getFirstName()));
      assertTrue("Last name does not match before and after saving",
          usuarioBefore.getLastName().equals(usuarioAfter.getLastName()));
      assertTrue("Email does not match before and after saving",
          usuarioBefore.getEmail().equals(usuarioAfter.getEmail()));
      assertFalse("Password shouldnt match before and after saving",
          usuarioBefore.getPassword().equals(usuarioAfter.getPassword()));

      assertFalse("Created at should not be null", usuarioAfter.getCreated_at() == null);
      assertFalse("Last login should have been set", usuarioAfter.getLast_login() == null);

    } catch (AutenticacaoException e) {
      fail(String.format("Authentication error: %s", e.getMessage()));
    }
  }

  @Test
  public void testeRecuperarUsuarioTokenVazio() {

    try {
      // Recuperar usuario
      usuarioService.recuperarUsuario("");
      fail("It should never have passed empty token");

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown unauthorized error for empty token",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.NAO_AUTORIZADO);
    }
  }

  @Test
  public void testeRecuperarUsuarioTokenNulo() {

    try {
      // Recuperar usuario
      usuarioService.recuperarUsuario(null);
      fail("It should never have passed empty token");

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown unauthorized error for empty token",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.NAO_AUTORIZADO);
    }
  }

  @Test
  public void testeRecuperarUsuarioTokenInvalido() {

    try {
      // Recuperar usuario
      usuarioService.recuperarUsuario("i_am_invalid");
      fail("It should never have passed token with invalid email");

    } catch (AutenticacaoException e) {
      fail("It should have thrown malformed Jwt Exception");

    } catch (MalformedJwtException e) {
      // Success!!
    }
  }

  @Test
  public void testeRecuperarUsuarioTokenEmailInvalido() {

    try {
      // Generate token with invalid email
      String token = JwtUtil.generateToken("i_dont_exist@pitang.com.br", "whatever");
      token = token.replace("Bearer ", "");

      // Recuperar usuario
      usuarioService.recuperarUsuario(token);
      fail("It should never have passed token with invalid email");

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown invalid e-mail or password error for token with invalid email",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.USUARIO_INEXISTENTE_SENHA_ERRADA);
    }
  }

  @Test
  public void testeRecuperarUsuarioTokenSenhaInvalida() {

    UsuarioDto usuarioBefore = null;

    String firstName = "Suellen";
    String lastName = "Soares";
    String email = "suellensoares@pitang.com.br";
    String password = "su3ll3n";

    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    LoginDto LoginDto = new LoginDto();
    LoginDto.setEmail(email);
    LoginDto.setPassword(password);

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      // Cadastrar usuario
      usuarioService.adicionarUsuario(usuarioBefore);

      // Generate token with invalid password
      String token = JwtUtil.generateToken(email, "i_am_invalid");
      token = token.replace("Bearer ", "");

      // Recuperar usuario
      usuarioService.recuperarUsuario(token);
      fail("It should never have passed token with invalid password");

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown invalid e-mail or password error for token with invalid password",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.USUARIO_INEXISTENTE_SENHA_ERRADA);
    }
  }

  @Test
  public void testeRecuperarUsuarioTokenExpirado() {

    UsuarioDto usuarioBefore = null;

    String firstName = "Cibele";
    String lastName = "Azevedo";
    String email = "cibeleazevedo@pitang.com.br";
    String password = "c1b3l3";

    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    LoginDto LoginDto = new LoginDto();
    LoginDto.setEmail(email);
    LoginDto.setPassword(password);

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      // Cadastrar usuario
      usuarioService.adicionarUsuario(usuarioBefore);

      // Logar usuario
      TokenDto tokenDto = usuarioService.logar(LoginDto);

      System.out.println("Waiting for two minutes to test expired token");
      TimeUnit.MINUTES.sleep(2L);
      System.out.println("Waiting is over");

      String token = tokenDto.getToken();
      token = token.replace("Bearer ", "");

      // Recuperar usuario
      usuarioService.recuperarUsuario(token);
      fail("It should never have passed token with expired token");

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown 'unauthorized - invalid session' error for token expired",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.TOKEN_EXPIRADO);

    } catch (InterruptedException e) {
      fail("This should have not been interrupted");
    }
  }
}
