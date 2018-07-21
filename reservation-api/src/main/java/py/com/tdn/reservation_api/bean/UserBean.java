package py.com.tdn.reservation_api.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "DELIVERY_TDN_USER")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static transient Logger log = Logger.getLogger(UserBean.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idUser;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String token;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar expirationToken;
	
	private String salt;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idSucursal")
	private SucursalBean sucursal;
	
	public UserBean() {
		super();
	}
	
	public UserBean(Integer idUser, String name, String email, String password, SucursalBean sucursal) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.email = email;
		this.password = password;
		this.sucursal = sucursal;
	}
	
	public UserBean(UserBean user) {
		super();
		this.idUser = user.idUser;
		this.name = user.name;
		this.email = user.email;
		this.password = user.password;
		this.sucursal = user.sucursal;
	}

	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public SucursalBean getSucursal() {
		return sucursal;
	}

	public void setSucursal(SucursalBean sucursal) {
		this.sucursal = sucursal;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Calendar getExpirationToken() {
		return expirationToken;
	}

	public void setExpirationToken(Calendar expirationToken) {
		this.expirationToken = expirationToken;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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
