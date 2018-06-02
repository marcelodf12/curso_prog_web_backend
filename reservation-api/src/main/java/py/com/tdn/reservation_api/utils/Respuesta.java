package py.com.tdn.reservation_api.utils;

import java.io.Serializable;

public class Respuesta<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tittle;
	private String message;
	private Boolean visible;
	private String type;
	private T data;
	
	public Respuesta() {
		super();
	}

	public Respuesta(String tittle, String message, Boolean visible, String type, T data) {
		super();
		this.tittle = tittle;
		this.message = message;
		this.visible = visible;
		this.type = type;
		this.data = data;
	}
	
	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public Boolean getVisible() {
		return visible;
	}



	public void setVisible(Boolean visible) {
		this.visible = visible;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public T getData() {
		return data;
	}



	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
