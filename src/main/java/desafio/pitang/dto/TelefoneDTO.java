package desafio.pitang.dto;

import java.io.Serializable;

public class TelefoneDTO implements Serializable{

	private static final long serialVersionUID = -1724236637742688996L;
	
	private int number;
	private int area_code;
	private String country_code;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getArea_code() {
		return area_code;
	}
	public void setArea_code(int area_code) {
		this.area_code = area_code;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	
	

}
