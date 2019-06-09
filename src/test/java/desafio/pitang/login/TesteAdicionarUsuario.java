package desafio.pitang.login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import desafio.pitang.dto.TelefoneDto;
import desafio.pitang.dto.TokenDto;
import desafio.pitang.dto.UsuarioDto;
import desafio.pitang.enumeration.TipoErroAutenticacao;
import desafio.pitang.excecoes.AutenticacaoException;
import desafio.pitang.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TesteAdicionarUsuario {

  @Autowired
  UsuarioService usuarioService;

  @Test
  public void testeAdicionarUsuario() {

    UsuarioDto usuarioBefore = null;
    UsuarioDto usuarioAfter = null;

    String firstName = "José";
    String lastName = "da Silva";
    String email = "josedasilva@pitang.com.br";
    String password = "j0s3d@s1lv@";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      TokenDto token = usuarioService.adicionarUsuario(usuarioBefore);
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
      assertTrue("Last login is not set yet", usuarioAfter.getLast_login() == null);

    } catch (AutenticacaoException e) {
      fail(String.format("Erro de autenticação: %s", e.getMessage()));
    }
  }

  @Test
  public void testeAdicionarPrimeiroNomeVazio() {

    String firstName = "";
    String lastName = "das Dores";
    String email = "mariadasdores@pitang.com.br";
    String password = "m@r1@d@sd0r3s";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuario);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields error for empty first name",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testeAdicionarPrimeiroNomeNulo() {

    String firstName = null;
    String lastName = "das Dores";
    String email = "mariadasdores@pitang.com.br";
    String password = "m@r1@d@sd0r3s";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuario);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields error for first name null",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testeAdicionarSobrenomeVazio() {

    String firstName = "Clodoaldo";
    String lastName = "";
    String email = "clodoaldodafonseca@pitang.com.br";
    String password = "cl0d0@ld0";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuario);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields error for empty last name",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testeAdicionarSobrenomeNulo() {

    String firstName = null;
    String lastName = "";
    String email = "clodoaldodafonseca@pitang.com.br";
    String password = "cl0d0@ld0";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuario);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields error for last name null",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testeAdicionarEmailVazio() {

    String firstName = "Guilhermina";
    String lastName = "Oliveira";
    String email = "";
    String password = "gu1lh3rm1n@";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuario);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields error for empty email",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testeAdicionarEmailNulo() {

    String firstName = "Guilhermina";
    String lastName = "Oliveira";
    String email = "";
    String password = "gu1lh3rm1n@";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuario);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields error for email null",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testeAdicionarEmailInvalido() {

    String firstName = "Saulo";
    String lastName = "Farias";
    String password = "s@ul0";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, null, password, telefones);

    String[] listaEmailsInvalidos = { "saulo", "saulo@", "@pitang.com.br", "saulo@pitang" };

    for (String emailInvalido : listaEmailsInvalidos) {

      try {
        usuario.setEmail(emailInvalido);
        usuarioService.adicionarUsuario(usuario);
        fail(String.format("It should have never passed an invalid email such as %s", emailInvalido));

      } catch (AutenticacaoException e) {
        assertTrue(String.format("It should have thrown invalid fields error for email %s", usuario.getEmail()),
            e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INVALIDOS);
      }
    }
  }

  @Test
  public void testeAdicionarSenhaVazia() {

    String firstName = "Paola";
    String lastName = "Rodrigues";
    String email = "paolarodrigues@pitang.com.br";
    String password = "";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuario);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields error for empty password",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testeAdicionarSenhaNula() {

    String firstName = "Paola";
    String lastName = "Rodrigues";
    String email = "paolarodrigues@pitang.com.br";
    String password = null;
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuario);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields error for password null",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testeAdicionarTelefoneVazio() {

    String firstName = "Roberto";
    String lastName = "dos Santos";
    String email = "robertodossantos@pitang.com.br";
    String password = "r0b3rt0";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuario);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields error for empty phones",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testeAdicionarTelefoneNulo() {

    String firstName = "Roberto";
    String lastName = "dos Santos";
    String email = "robertodossantos@pitang.com.br";
    String password = "r0b3rt0";
    List<TelefoneDto> telefones = null;

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuario);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields error for phones list null",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testeAdicionarTelefoneInvalido() {

    String firstName = "Roberto";
    String lastName = "dos Santos";
    String email = "robertodossantos@pitang.com.br";
    String password = "r0b3rt0";

    UsuarioDto usuario = TesteUtil.criarUsuario(firstName, lastName, email, password, null);

    TelefoneDto[] telefonesCamposFaltantes = new TelefoneDto[] { TesteUtil.criarTelefone(12345, 81, ""),
        TesteUtil.criarTelefone(12345, 81, null), TesteUtil.criarTelefone(0, 81, ""),
        TesteUtil.criarTelefone(12345, 0, "") };

    for (TelefoneDto telefoneDto : telefonesCamposFaltantes) {

      try {

        List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
        telefones.add(telefoneDto);
        usuario.setPhones(telefones);
        usuarioService.adicionarUsuario(usuario);
        fail("It should have never passed an invalid phone");

      } catch (AutenticacaoException e) {
        assertTrue("It should have thrown invalid fields error for phone",
            e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INVALIDOS);
      }
    }

    for (TelefoneDto telefoneDto : telefonesCamposFaltantes) {

      try {

        List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
        telefones.add(telefoneDto);
        usuario.setPhones(telefones);
        usuarioService.adicionarUsuario(usuario);
        fail("It should have never passed an invalid phone");

      } catch (AutenticacaoException e) {
        assertTrue("It should have thrown invalid fields error for phone",
            e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INVALIDOS);
      }
    }
  }

}
