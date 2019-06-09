package desafio.pitang.login;

import java.util.List;

import desafio.pitang.dto.LoginDto;
import desafio.pitang.dto.TelefoneDto;
import desafio.pitang.dto.UsuarioDto;

public class TesteUtil {

  public static UsuarioDto criarUsuario(String firstName, String lastName, String email, String password,
      List<TelefoneDto> phones) {

    UsuarioDto usuario = new UsuarioDto();
    usuario.setFirstName(firstName);
    usuario.setLastName(lastName);
    usuario.setEmail(email);
    usuario.setPassword(password);
    usuario.setPhones(phones);

    return usuario;
  }

  public static LoginDto criarLogin(String email, String password) {

    LoginDto login = new LoginDto();
    login.setEmail(email);
    login.setPassword(password);

    return login;
  }

  public static TelefoneDto criarTelefone(int number, int area_code, String country_code) {

    TelefoneDto telefone = new TelefoneDto();
    telefone.setNumber(number);
    telefone.setArea_code(area_code);
    telefone.setCountry_code(country_code);

    return telefone;
  }
}
