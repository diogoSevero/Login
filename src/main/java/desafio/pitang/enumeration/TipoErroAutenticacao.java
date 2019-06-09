package desafio.pitang.enumeration;

import org.springframework.http.HttpStatus;

public enum TipoErroAutenticacao {

  USUARIO_EXISTENTE("E-mail already exists", HttpStatus.BAD_REQUEST), //
  CAMPOS_INEXISTENTES("Missing Fields", HttpStatus.BAD_REQUEST), //
  USUARIO_INEXISTENTE_SENHA_ERRADA("Invalid e-mail or password", HttpStatus.BAD_REQUEST), //
  CAMPOS_INVALIDOS("Invalid Fields", HttpStatus.BAD_REQUEST), //
  NAO_AUTORIZADO("Unauthorized", HttpStatus.UNAUTHORIZED), //
  TOKEN_EXPIRADO("Unauthorized - invalid session", HttpStatus.UNAUTHORIZED);

  public String mensagem;
  public HttpStatus status;

  TipoErroAutenticacao(String mensagem, HttpStatus status) {
    this.mensagem = mensagem;
    this.status = status;
  }

  public String getMensagem() {
    return this.mensagem;
  }

  public HttpStatus getStatus() {
    return this.status;
  }
}