package py.com.tdn.reservation_api.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name="DELIVERY_TDN_DELIVERY")
public class DeliveryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static transient Logger log = Logger.getLogger(DeliveryBean.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idDelivery;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="idPackage")
	private PackageBean pack;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="idClient")
	private ClientBean client;
	
	private Date inputDate;
	
	private Date tentativeDate;
	
	private Date realDate;
	
	private String status;
	
	@OneToMany(mappedBy="delivery",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TrackBean> tracks;
	
	public DeliveryBean() {
		super();
	}

	public DeliveryBean(Integer idDelivery, PackageBean pack, ClientBean client, Date inputDate, Date tentativeDate,
			Date realDate, String status, List<TrackBean> tracks) {
		super();
		this.idDelivery = idDelivery;
		this.pack = pack;
		this.client = client;
		this.inputDate = inputDate;
		this.tentativeDate = tentativeDate;
		this.realDate = realDate;
		this.status = status;
		this.tracks = tracks;
	}
	
	public DeliveryBean(DeliveryBean delivery) {
		super();
		this.idDelivery = delivery.idDelivery;
		this.pack = delivery.pack;
		this.client = delivery.client;
		this.inputDate = delivery.inputDate;
		this.tentativeDate = delivery.tentativeDate;
		this.realDate = delivery.realDate;
		this.status = delivery.status;
		this.tracks = delivery.tracks;
	}

	public Integer getIdDelivery() {
		return idDelivery;
	}

	public void setIdDelivery(Integer idDelivery) {
		this.idDelivery = idDelivery;
	}

	public PackageBean getPack() {
		return pack;
	}

	public void setPack(PackageBean pack) {
		this.pack = pack;
	}

	public ClientBean getClient() {
		return client;
	}

	public void setClient(ClientBean client) {
		this.client = client;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getTentativeDate() {
		return tentativeDate;
	}

	public void setTentativeDate(Date tentativeDate) {
		this.tentativeDate = tentativeDate;
	}

	public Date getRealDate() {
		return realDate;
	}

	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
