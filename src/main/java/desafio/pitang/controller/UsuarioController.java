package desafio.pitang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import desafio.pitang.dto.LoginDTO;
import desafio.pitang.dto.TokenDTO;
import desafio.pitang.dto.UsuarioDTO;
import desafio.pitang.excecoes.AutenticacaoException;
import desafio.pitang.service.UsuarioService;

@RestController
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@RequestMapping(value = "/signup", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	public ResponseEntity<TokenDTO> cadastrarUsuario(@RequestBody UsuarioDTO usuario) throws AutenticacaoException {

		TokenDTO token = usuarioService.adicionarUsuario(usuario);

		return new ResponseEntity<TokenDTO>(token, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	public ResponseEntity<TokenDTO> logarUsuario(@RequestBody LoginDTO login) throws AutenticacaoException {

		TokenDTO token = usuarioService.logar(login);

		return new ResponseEntity<TokenDTO>(token, HttpStatus.OK);

	}

	@RequestMapping(value = "/me", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	public ResponseEntity<UsuarioDTO> getUsuario(@RequestParam(value = "token") String token)
			throws AutenticacaoException {

		UsuarioDTO usuario = usuarioService.recuperarUsuario(token);

		return new ResponseEntity<UsuarioDTO>(usuario, HttpStatus.OK);

	}

}
