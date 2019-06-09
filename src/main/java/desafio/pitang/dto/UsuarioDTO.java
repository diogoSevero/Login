package desafio.pitang.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 8744310714748664192L;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Date created_at;
	private Date last_login;
	private List<TelefoneDTO> phones;

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<TelefoneDTO> getPhones() {
		return phones;
	}

	public void setPhones(List<TelefoneDTO> phones) {
		this.phones = phones;
	}

}
