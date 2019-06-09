package desafio.pitang.model;

import java.util.Date;

public class MensagemErro {

  private Date timestamp;
  private String mensagem;

  public MensagemErro() {
  }

  public MensagemErro(Date timestamp, String mensagem) {
    this.timestamp = timestamp;
    this.mensagem = mensagem;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

}
