package desafio.pitang.login;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import desafio.pitang.dao.UsuarioDao;
import desafio.pitang.dto.LoginDto;
import desafio.pitang.dto.TelefoneDto;
import desafio.pitang.dto.UsuarioDto;
import desafio.pitang.enumeration.TipoErroAutenticacao;
import desafio.pitang.excecoes.AutenticacaoException;
import desafio.pitang.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TesteLogarUsuario {

  @Autowired
  UsuarioService usuarioService;

  @Autowired
  UsuarioDao usuarioDao;

  @Test
  public void testLogarUsuario() {
    UsuarioDto usuarioBefore = null;

    String firstName = "Drielly";
    String lastName = "Menezes";
    String email = "drielly.menezes@pitang.com.br";
    String password = "drielly$$$menezes***";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(3333333, 55, "+98"));

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuarioBefore);

      LoginDto login = TesteUtil.criarLogin(email, password);

      usuarioService.logar(login);

    } catch (AutenticacaoException e) {
      fail(String.format("Authentication Error: %s", e.getMessage()));
    }
  }

  @Test
  public void testLogarUsuarioEmailVazio() {
    UsuarioDto usuarioBefore = null;

    String firstName = "José";
    String lastName = "da Silva";
    String email = "josedasilva@pitang.com.br";
    String password = "j0s3d@s1lv@";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(12345, 81, "+55"));

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuarioBefore);

      LoginDto login = TesteUtil.criarLogin("", password);

      usuarioService.logar(login);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields for empty email",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testLogarUsuarioEmailNulo() {
    UsuarioDto usuarioBefore = null;

    String firstName = "Diogo";
    String lastName = "Severo";
    String email = "diogo.severo@pitang.com.br";
    String password = "dio*&go$$severo";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(45678, 65, "+98"));

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuarioBefore);

      LoginDto login = TesteUtil.criarLogin(null, password);

      usuarioService.logar(login);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields for email null",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testLogarUsuarioSenhaNulo() {
    UsuarioDto usuarioBefore = null;

    String firstName = "Juliana";
    String lastName = "Alves";
    String email = "juliana.alves@pitang.com.br";
    String password = "J%uli*an%%a";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(9876, 44, "+45"));

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuarioBefore);

      LoginDto login = TesteUtil.criarLogin(email, null);

      usuarioService.logar(login);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields for password null",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testLogarUsuarioSenhaVazia() {
    UsuarioDto usuarioBefore = null;

    String firstName = "Maria";
    String lastName = "Silva";
    String email = "maria.silva@pitang.com.br";
    String password = "maria*&silva";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(987643, 23, "+22"));

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuarioBefore);

      LoginDto login = TesteUtil.criarLogin(email, "");

      usuarioService.logar(login);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown missing fields for empty password",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.CAMPOS_INEXISTENTES);
    }
  }

  @Test
  public void testLogarUsuarioSenhaInvalida() {
    UsuarioDto usuarioBefore = null;

    String firstName = "José";
    String lastName = "Mariano";
    String email = "jose.mariano@pitang.com.br";
    String password = "jose**mariano&&";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(987654, 87, "+87"));

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuarioBefore);

      LoginDto login = TesteUtil.criarLogin(email, "senha&&nova");

      usuarioService.logar(login);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown invalid fields error for invalid password",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.USUARIO_INEXISTENTE_SENHA_ERRADA);
    }
  }

  @Test
  public void testLogarUsuarioEmailInvalido() {
    UsuarioDto usuarioBefore = null;

    String firstName = "Thiago";
    String lastName = "Cabral";
    String email = "thiago.cabral@pitang.com.br";
    String password = "thiago***cabral&&&";
    List<TelefoneDto> telefones = new ArrayList<TelefoneDto>();
    telefones.add(TesteUtil.criarTelefone(222222, 44, "+87"));

    usuarioBefore = TesteUtil.criarUsuario(firstName, lastName, email, password, telefones);

    try {
      usuarioService.adicionarUsuario(usuarioBefore);

      LoginDto login = TesteUtil.criarLogin("thiago.cabraal@pitang.com.br", password);

      usuarioService.logar(login);

    } catch (AutenticacaoException e) {
      assertTrue("It should have thrown invalid fields error for invalid email",
          e.getTipoErroAutenticacao() == TipoErroAutenticacao.USUARIO_INEXISTENTE_SENHA_ERRADA);
    }
  }
}
