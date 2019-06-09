package desafio.pitang.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_USUARIO")
  private Long id;

  @Column(name = "NOME")
  @JsonProperty("firstName")
  private String nome;

  @Column(name = "SOBRENOME")
  @JsonProperty("lastName")
  private String sobrenome;

  @Column(name = "EMAIL")
  @JsonProperty("email")
  private String email;

  @Column(name = "SENHA")
  @JsonProperty("password")
  private String senha;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonProperty("phones")
  private List<Telefone> telefones;

  @Column(name = "DATA_CRIACAO")
  private Date dataCriacao;

  @Column(name = "DATA_ULTIMO_LOGIN")
  private Date dataUltimoLogin;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDataCriacao() {
    return dataCriacao;
  }

  public void setDataCriacao(Date dataCriacao) {
    this.dataCriacao = dataCriacao;
  }

  public Date getDataUltimoLogin() {
    return dataUltimoLogin;
  }

  public void setDataUltimoLogin(Date dataUltimoLogin) {
    this.dataUltimoLogin = dataUltimoLogin;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSobrenome() {
    return sobrenome;
  }

  public void setSobrenome(String sobrenome) {
    this.sobrenome = sobrenome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public List<Telefone> getTelefones() {
    return telefones;
  }

  public void setTelefones(List<Telefone> telefones) {
    this.telefones = telefones;
  }

}
