package desafio.pitang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import desafio.pitang.dto.LoginDto;
import desafio.pitang.dto.TokenDto;
import desafio.pitang.dto.UsuarioDto;
import desafio.pitang.excecoes.AutenticacaoException;
import desafio.pitang.service.UsuarioService;

@RestController
public class UsuarioController {

  @Autowired
  UsuarioService usuarioService;

  /**
   * Método que representa o endpoint para o cadastro de usuário.
   * 
   * @param usuario
   * @return
   * @throws AutenticacaoException.
   */
  @RequestMapping(value = "/signup", method = RequestMethod.POST, headers = "Accept=application/json", //
      produces = "application/json")
  public ResponseEntity<TokenDto> cadastrarUsuario(@RequestBody UsuarioDto usuario) throws AutenticacaoException {

    TokenDto token = usuarioService.adicionarUsuario(usuario);

    return new ResponseEntity<TokenDto>(token, HttpStatus.CREATED);

  }

  /**
   * Método que representa o endpoint para o login de usuário.
   * 
   * @param login
   * @return
   * @throws AutenticacaoException.
   */
  @RequestMapping(value = "/signin", method = RequestMethod.POST, headers = "Accept=application/json", //
      produces = "application/json")
  public ResponseEntity<TokenDto> logarUsuario(@RequestBody LoginDto login) throws AutenticacaoException {

    TokenDto token = usuarioService.logar(login);

    return new ResponseEntity<TokenDto>(token, HttpStatus.OK);

  }

  /**
   * Método que representa o endpoint para recuperar dados do usuário logado.
   * 
   * @param token
   * @return
   * @throws AutenticacaoException.
   */
  @RequestMapping(value = "/me", method = RequestMethod.GET, headers = "Accept=application/json", //
      produces = "application/json")
  public ResponseEntity<UsuarioDto> getUsuario(@RequestParam(value = "token") String token)
      throws AutenticacaoException {

    UsuarioDto usuario = usuarioService.recuperarUsuario(token);

    return new ResponseEntity<UsuarioDto>(usuario, HttpStatus.OK);

  }

}
