package py.com.tdn.reservation_api.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name="DELIVERY_TDN_CLIENT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClientBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static transient Logger log = Logger.getLogger(ClientBean.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idClient;
	
	@Basic(fetch=FetchType.LAZY)
	private String firtName;
	
	private String lastName;
	
	private String idNumber;
	
	private String email;
	
	private Date birthDate;
	
	private Double lat;
	
	private Double lon;
	
	@OneToMany(mappedBy="client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DeliveryBean> deliveries;
	
	public ClientBean() {
		super();
	}
	

	public ClientBean(Integer idClient, String firtName, String lastName, String idNumber, Double lat, Double lon, Date birthDate, String email, List<DeliveryBean> deliveries) {
		super();
		this.idClient = idClient;
		this.firtName = firtName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.birthDate = birthDate;
		this.email = email;
		this.lat = lat;
		this.lon = lon;
		this.deliveries = deliveries;
	}
	
	public ClientBean(ClientBean client) {
		super();
		this.idClient = client.idClient;
		this.firtName = client.firtName;
		this.lastName = client.lastName;
		this.idNumber = client.idNumber;
		this.birthDate = client.birthDate;
		this.email = client.email;
		this.lat = client.lat;
		this.lon = client.lon;
		this.deliveries = client.deliveries;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Date getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	

	public List<DeliveryBean> getDeliveries() {
		return deliveries;
	}


	public void setDeliveries(List<DeliveryBean> deliveries) {
		this.deliveries = deliveries;
	}


	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writerWithDefaultPrettyPrinter()
			        .writeValueAsString(this);
		} catch (JsonProcessingException e) {
			log.error(e);
			return "";
		}
	}

}
