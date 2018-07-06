package py.com.tdn.reservation_api.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name="DELIVERY_TDN_PACKAGE")
public class PackageBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static transient Logger log = Logger.getLogger(PackageBean.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idPackage;
	
	@Column(unique=true)
	private String trackCode;
	
	private String description;
	
	private Integer width; //ancho
	
	private Integer height; //alto
	
	private Integer depth; //profunidad
	
	private Integer weight; //peso
	
	@OneToOne(mappedBy="pack", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private DeliveryBean delivery;

	public PackageBean() {
		super();
	}
	
	public PackageBean(Integer idPackage, String trackCode, String description, Integer width, Integer height,
			Integer depth, Integer weight, DeliveryBean delivery) {
		super();
		this.idPackage = idPackage;
		this.trackCode = trackCode;
		this.description = description;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.weight = weight;
		this.delivery = delivery;
	}
	
	public PackageBean(PackageBean pack) {
		super();
		this.idPackage = pack.idPackage;
		this.trackCode = pack.trackCode;
		this.description = pack.description;
		this.width = pack.width;
		this.height = pack.height;
		this.depth = pack.depth;
		this.weight = pack.weight;
		this.delivery = pack.delivery;
	}

	public Integer getIdPackage() {
		return idPackage;
	}
	public void setIdPackage(Integer idPackage) {
		this.idPackage = idPackage;
	}
	public String getTrackCode() {
		return trackCode;
	}
	public void setTrackCode(String trackCode) {
		this.trackCode = trackCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	
	public DeliveryBean getDelivery() {
		return delivery;
	}

	public void setDelivery(DeliveryBean delivery) {
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
