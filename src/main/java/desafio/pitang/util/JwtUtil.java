package desafio.pitang.util;

import java.util.Calendar;
import java.util.Date;

import desafio.pitang.excecoes.AutenticacaoException;
import desafio.pitang.excecoes.AutenticacaoException.TipoErroAutenticacao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

  private static final String SECRET = "UserRegisterApp";
  private static final String TOKEN_PREFIX = "Bearer";

  /**
   * Método que gera o token encapsulando as informações do e-mail e senha.
   * 
   * @param email
   * @param password
   * @return
   */
  public static String generateToken(String email, String password) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    Date dateHourToken = cal.getTime();
    cal.add(Calendar.MINUTE, 2);
    Date dateHourExpiredToken = cal.getTime();

    Claims claims = Jwts.claims().setSubject(email).setIssuedAt(dateHourToken).//
        setExpiration(dateHourExpiredToken);
    claims.put("password", password);

    String jwt = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET).compact();

    return TOKEN_PREFIX + " " + jwt;
  }

  /**
   * Método que dado o token retorna o e-mail descriptografado.
   * 
   * @param token
   * @return
   * @throws AutenticacaoException
   */
  public static String getEmail(String token) throws AutenticacaoException {
    try {
      Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
      return body.getSubject();
    } catch (ExpiredJwtException e) {
      throw new AutenticacaoException(TipoErroAutenticacao.TOKEN_EXPIRADO);
    }
  }

  /**
   * Método que dado o token, retorna a senha.
   * 
   * @param token
   * @return
   * @throws AutenticacaoException
   */
  public static String getSenha(String token) throws AutenticacaoException {
    try {
      Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
      return (String) body.get("password");
    } catch (ExpiredJwtException e) {
      throw new AutenticacaoException(TipoErroAutenticacao.TOKEN_EXPIRADO);
    }
  }

}
