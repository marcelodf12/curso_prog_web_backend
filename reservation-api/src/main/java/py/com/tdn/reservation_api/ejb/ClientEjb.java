package py.com.tdn.reservation_api.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.bean.ClientBean;
import py.com.tdn.reservation_api.dao.ClientDao;
import py.com.tdn.reservation_api.utils.Pagination;

@Stateless
public class ClientEjb {
	
	@EJB
	ClientDao clientDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public ClientBean create(ClientBean c){
		try {
			return clientDao.create(c);
		} catch (Exception e) {
			log.error("failed!",e);
			return null;
		}
		
	}
	
	public ClientBean findById(Integer clientId){
		try {
			return clientDao.findById(ClientBean.class, clientId);
		} catch (Exception e) {
			log.error("failed!",e);
			return null;
		}
	}
	
	public Pagination<ClientBean> find(
			String firstName,
    		String lastName,
    		String email,
    		String idNumber,
			String orderBy,
			String order,
			Integer currentPage,
			Integer pageSize){
		try {
			String column = "idNumber";
			String filter = "";
			
			if(idNumber!=null) 		{ column ="idNumber"; 	filter = idNumber; }
			else if(firstName!=null){ column ="firstName"; 	filter = firstName; }
			else if(lastName!=null) { column ="lastName"; 	filter = lastName; }
			else if(email!=null) 	{ column ="email"; 		filter = email; }
			
			if(	!orderBy.matches("idNumber|firstName|lastName|email") )
				orderBy = "idNumber";
			
			if( order.compareTo("ASC")!=0) order = "DESC";
			
			currentPage = currentPage!=null?currentPage:1;
			pageSize = pageSize!=null?pageSize:10;
			
			return clientDao.findByColumnWithPagination(ClientBean.class, column, filter, orderBy, order, currentPage, pageSize);
		} catch (Exception e) {
			log.error("failed!",e);
			return null;
		}
		
	}

}
