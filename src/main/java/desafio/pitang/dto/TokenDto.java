package desafio.pitang.dto;

import java.io.Serializable;

public class TokenDto implements Serializable {

  private static final long serialVersionUID = -6677922336145596719L;

  private String token;

  public TokenDto(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}