package py.com.tdn.reservation_api.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import py.com.tdn.reservation_api.bean.PersonBean;

@Stateless
public class PersonEjb {
	
	private static List<PersonBean> persons = new ArrayList<>();
	
	public PersonBean save(PersonBean p){
		PersonBean pAux = findById(p.getId());
		if(pAux==null){
			p.setId(persons.size()+1);
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
	
	public PersonBean findById(Integer id){
		if(id!=null)
			for(PersonBean p:persons){
				if(id.compareTo(p.getId())==0)
					return p;
			}
		return null;
	}
	
	public List<PersonBean> listAll(){
		return persons;
	}
	
	public void delete(Integer pId){
		for (Iterator<PersonBean> iter = persons.listIterator(); iter.hasNext(); ) {
			PersonBean a = iter.next();
		    if (a.getId().compareTo(pId)==0) {
		        iter.remove();
		    }
		}
	}

}
