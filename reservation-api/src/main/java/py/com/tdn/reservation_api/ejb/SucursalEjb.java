package py.com.tdn.reservation_api.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.bean.SucursalBean;
import py.com.tdn.reservation_api.dao.SucursalDao;

@Stateless
public class SucursalEjb {


	@EJB
	SucursalDao sucursalDao;
	
	private Logger log = Logger.getLogger(SucursalEjb.class);
	
	public SucursalBean create(SucursalBean s){
		try {
			return sucursalDao.create(s);
		} catch (Exception e) {
			log.error("failed!",e);
			return null;
		}
	}
	
	public List<SucursalBean> findAll(){
		try {
			return sucursalDao.findAll();
		} catch (Exception e) {
			log.error("failed!",e);
			return null;
		}
		
	}
	
	
}
