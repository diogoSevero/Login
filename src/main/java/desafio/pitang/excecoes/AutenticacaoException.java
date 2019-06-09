package desafio.pitang.excecoes;

import org.springframework.http.HttpStatus;

public class AutenticacaoException extends Exception {

  public enum TipoErroAutenticacao {

    USUARIO_EXISTENTE("E-mail already exists", HttpStatus.BAD_REQUEST), //
    CAMPOS_INEXISTENTES("Missing Fields", HttpStatus.BAD_REQUEST), //
    USUARIO_INEXISTENTE_SENHA_ERRADA("Invalid e-mail or password", HttpStatus.BAD_REQUEST), //
    CAMPOS_INVALIDOS("Invalid Fields", HttpStatus.BAD_REQUEST), //
    NAO_AUTORIZADO("Unauthorized", HttpStatus.UNAUTHORIZED), //
    TOKEN_EXPIRADO("Unauthorized - invalid session", HttpStatus.UNAUTHORIZED);

    private String mensagem;
    private HttpStatus status;

    TipoErroAutenticacao(String mensagem, HttpStatus status) {
      this.mensagem = mensagem;
      this.status = status;
    }

    String getMensagem() {
      return this.mensagem;
    }

    HttpStatus getStatus() {
      return this.status;
    }
  }

  private static final long serialVersionUID = 6129682377370647659L;
  private HttpStatus status;

  public AutenticacaoException(TipoErroAutenticacao tipoAutenticacao) {
    super(tipoAutenticacao.getMensagem());
    this.status = tipoAutenticacao.getStatus();
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

}
