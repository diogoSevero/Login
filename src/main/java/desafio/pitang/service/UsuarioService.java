package desafio.pitang.service;

import desafio.pitang.dto.LoginDTO;
import desafio.pitang.dto.TokenDTO;
import desafio.pitang.dto.UsuarioDTO;
import desafio.pitang.excecoes.AutenticacaoException;

public interface UsuarioService {

	public TokenDTO logar(LoginDTO login) throws AutenticacaoException;

	public TokenDTO adicionarUsuario(UsuarioDTO usuario) throws AutenticacaoException;

	public UsuarioDTO recuperarUsuario(String token) throws AutenticacaoException;

}
