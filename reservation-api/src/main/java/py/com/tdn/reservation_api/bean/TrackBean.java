package py.com.tdn.reservation_api.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "DELIVERY_TDN_TRACK")
public class TrackBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static transient Logger log = Logger.getLogger(TrackBean.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Integer idTrack;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="idSucursal")
	private SucursalBean sucursal;
	
	private Double lon;
	
	private Double lat;
	
	private Date inputDate;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="idDelivery")
	@JsonIgnore
	private DeliveryBean delivery;
	
	public TrackBean() {
		super();
	}

	public TrackBean(Integer idTrack, SucursalBean sucursal, Double lon, Double lat, Date inputDate, DeliveryBean delivery) {
		super();
		this.idTrack = idTrack;
		this.sucursal = sucursal;
		this.lon = lon;
		this.lat = lat;
		this.inputDate = inputDate;
		this.delivery = delivery;
	}
	
	public TrackBean(TrackBean track) {
		super();
		this.idTrack = track.idTrack;
		this.sucursal = track.sucursal;
		this.lon = track.lon;
		this.lat = track.lat;
		this.inputDate = track.inputDate;
		this.delivery = track.delivery;
	}

	public Integer getIdTrack() {
		return idTrack;
	}

	public void setIdTrack(Integer idTrack) {
		this.idTrack = idTrack;
	}

	public SucursalBean getSucursal() {
		return sucursal;
	}

	public void setSucursal(SucursalBean sucursal) {
		this.sucursal = sucursal;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public DeliveryBean getOrder() {
		return delivery;
	}

	public void setOrder(DeliveryBean delivery) {
		this.delivery = delivery;
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