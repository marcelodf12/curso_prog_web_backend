package py.com.tdn.reservation_api.bean;

import java.io.Serializable;

public class AddressBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String city;
	private String state;
	private String zip;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
}
