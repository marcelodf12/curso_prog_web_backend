package py.com.tdn.reservation_api.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import py.com.tdn.reservation_api.dto.Person;

public class PersonEjb {
	
	private static PersonEjb instance = new PersonEjb();
	
	private static List<Person> persons;
	
	private PersonEjb(){}
	
	public static PersonEjb getIntance(){
		if(persons==null)
			persons = new ArrayList<>();
		return instance;
	}
	
	public Person save(Person p){
		Person pAux = findById(p.getId());
		if(pAux==null){
			persons.add(p);
		}else{
			pAux.setFirstName(p.getFirstName());
			pAux.setLastName(p.getLastName());
			pAux.setBirthDate(p.getBirthDate());
			pAux.setEmail(p.getEmail());
			pAux.setIdNumber(p.getIdNumber());
			pAux.setNationality(p.getNationality());
			pAux.setAddress(p.getAddress());
		}
		return p;
	}
	
	public Person findById(Integer id){
		for(Person p:persons){
			if(id.compareTo(p.getId())==0)
				return p;
		}
		return null;
	}
	
	public List<Person> listAll(){
		return persons;
	}
	
	public void delete(Person p){
		for (Iterator<Person> iter = persons.listIterator(); iter.hasNext(); ) {
			Person a = iter.next();
		    if (a.getId().compareTo(p.getId())==0) {
		        iter.remove();
		    }
		}
	}

}
