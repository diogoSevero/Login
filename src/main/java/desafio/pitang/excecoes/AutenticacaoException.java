package desafio.pitang.excecoes;

import org.springframework.http.HttpStatus;

import desafio.pitang.enumeration.TipoErroAutenticacao;

public class AutenticacaoException extends Exception {

  private static final long serialVersionUID = 6129682377370647659L;
  private TipoErroAutenticacao tipoErroAutenticacao;

  public AutenticacaoException(TipoErroAutenticacao tipoAutenticacao) {
    super(tipoAutenticacao.getMensagem());
    this.tipoErroAutenticacao = tipoAutenticacao;
  }

  public HttpStatus getStatus() {
    return this.tipoErroAutenticacao.getStatus();
  }

  public TipoErroAutenticacao getTipoErroAutenticacao() {
    return this.tipoErroAutenticacao;
  }

}
