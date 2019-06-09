package desafio.pitang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Telefone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_TELEFONE")
  private long id;

  @Column(name = "NUMERO")
  @JsonProperty("number")
  private int numero;

  @Column(name = "CODIGO_AREA")
  @JsonProperty("area_code")
  private int codigoArea;

  @Column(name = "CODIGO_PAIS")
  @JsonProperty("country_code")
  private String codigoPais;

  @ManyToOne
  @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
  private Usuario usuario;

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public int getCodigoArea() {
    return codigoArea;
  }

  public void setCodigoArea(int codigoArea) {
    this.codigoArea = codigoArea;
  }

  public String getCodigoPais() {
    return codigoPais;
  }

  public void setCodigoPais(String codigoPais) {
    this.codigoPais = codigoPais;
  }

}
