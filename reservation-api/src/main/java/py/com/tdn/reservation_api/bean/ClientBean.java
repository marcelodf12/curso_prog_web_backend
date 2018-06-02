package py.com.tdn.reservation_api.bean;

import java.io.Serializable;

public class ClientBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idClient;
	private String firtName;
	private String lastName;
	private String idNumber;
	private Double lat;
	private Double lon;
	
	
	public ClientBean() {
		super();
	}
	

	public ClientBean(Integer idClient, String firtName, String lastName, String idNumber, Double lat, Double lon) {
		super();
		this.idClient = idClient;
		this.firtName = firtName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.lat = lat;
		this.lon = lon;
	}
	
	public ClientBean(ClientBean client) {
		super();
		this.idClient = client.idClient;
		this.firtName = client.firtName;
		this.lastName = client.lastName;
		this.idNumber = client.idNumber;
		this.lat = client.lat;
		this.lon = client.lon;
	}


	public Integer getIdClient() {
		return idClient;
	}


	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}


	public String getFirtName() {
		return firtName;
	}


	public void setFirtName(String firtName) {
		this.firtName = firtName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getIdNumber() {
		return idNumber;
	}


	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}


	public Double getLat() {
		return lat;
	}


	public void setLat(Double lat) {
		this.lat = lat;
	}


	public Double getLon() {
		return lon;
	}


	public void setLon(Double lon) {
		this.lon = lon;
	}

}
