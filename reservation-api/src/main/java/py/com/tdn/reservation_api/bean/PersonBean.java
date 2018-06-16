package py.com.tdn.reservation_api.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PersonBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String idNumber;
    private String nationality;
    private List<AddressBean> address;
    
    // Constructor del JavaBean Obligatorio
	public PersonBean() {
		super();
	}

	// Constructor por campos
	public PersonBean(Integer iD, String firstName, String lastName, Date birthDate, String email, String idNumber,
			String nationality, List<AddressBean> address) {
		super();
		this.id = iD;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.email = email;
		this.idNumber = idNumber;
		this.nationality = nationality;
		this.address = address;
	}
	
	// Constructor por copia
	public PersonBean(PersonBean person) {
		super();
		this.id = person.id;
		this.firstName = person.firstName;
		this.lastName = person.lastName;
		this.birthDate = person.birthDate;
		this.email = person.email;
		this.idNumber = person.idNumber;
		this.nationality = person.nationality;
		this.address = person.address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public List<AddressBean> getAddress() {
		return address;
	}

	public void setAddress(List<AddressBean> address) {
		this.address = address;
	}

	

}
