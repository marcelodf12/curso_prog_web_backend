package py.com.tdn.reservation_api.dto;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChangePasswordDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static transient Logger log = Logger.getLogger(ChangePasswordDto.class);

	private String oldPass;
	
	private String newPass;
	
	private String confirmPass;

	public ChangePasswordDto() {
		super();
	}

	public ChangePasswordDto(String oldPass, String newPass, String confirmPass) {
		super();
		this.oldPass = oldPass;
		this.newPass = newPass;
		this.confirmPass = confirmPass;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
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
