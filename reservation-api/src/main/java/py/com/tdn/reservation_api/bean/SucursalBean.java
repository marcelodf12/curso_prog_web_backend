package py.com.tdn.reservation_api.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name="DELIVERY_TDN_SUCURSAL")
public class SucursalBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static transient Logger log = Logger.getLogger(SucursalBean.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idSucursal;
	
	@OneToOne(mappedBy="sucursal")
	@JsonIgnore
	private UserBean user;
	
	private Double lat;
	
	private Double lon;
	
	@OneToMany(mappedBy="sucursal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<TrackBean> tracks;
	
	public SucursalBean() {
		super();
	}

	public SucursalBean(Integer idSucursal, UserBean user, Double lat, Double lon, List<TrackBean> tracks) {
		super();
		this.idSucursal = idSucursal;
		this.user = user;
		this.lat = lat;
		this.lon = lon;
		this.tracks = tracks;
	}
	
	public SucursalBean(SucursalBean sucursal) {
		super();
		this.idSucursal = sucursal.idSucursal;
		this.user = sucursal.user;
		this.lat = sucursal.lat;
		this.lon = sucursal.lon;
		this.tracks = sucursal.tracks;
	}

	public Integer getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Integer idSucursal) {
		this.idSucursal = idSucursal;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
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

	public List<TrackBean> getTracks() {
		return tracks;
	}

	public void setTracks(List<TrackBean> tracks) {
		this.tracks = tracks;
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
