package desafio.pitang.service;

import desafio.pitang.dto.LoginDto;
import desafio.pitang.dto.TokenDto;
import desafio.pitang.dto.UsuarioDto;
import desafio.pitang.excecoes.AutenticacaoException;

public interface UsuarioService {

  public TokenDto logar(LoginDto login) throws AutenticacaoException;

  public TokenDto adicionarUsuario(UsuarioDto usuario) throws AutenticacaoException;

  public UsuarioDto recuperarUsuario(String token) throws AutenticacaoException;

}
