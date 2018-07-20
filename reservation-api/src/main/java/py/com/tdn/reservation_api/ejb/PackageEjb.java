package py.com.tdn.reservation_api.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.bean.DeliveryBean;
import py.com.tdn.reservation_api.bean.PackageBean;
import py.com.tdn.reservation_api.dao.PackageDao;
import py.com.tdn.reservation_api.utils.Pagination;
import py.com.tdn.reservation_api.utils.TrackGenerator;

@Stateless
public class PackageEjb {
	
	@EJB
	PackageDao packageDao;
	
	@EJB
	TrackGenerator trackEjb;
	
	private int atributoPrivado = 0;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public PackageBean createPackage(PackageBean p){
		if(p.getIdPackage()==null){
			try {
				p.setTrackCode(trackEjb.newTrack());
				return packageDao.create(p);
			} catch (Exception e) {
				log.error("Failed!", e);
				return null;
			}
		}
		return null;
	}
	
	public DeliveryBean getPackageByTrackNumber(String trackNumber){
		DeliveryBean d = packageDao.getPackageByTrackNumber(trackNumber);
		d.setClient(null);
		return d;
	}
	
	public Pagination<PackageBean> find(
			String description,
    		String trackCode,
			String orderBy,
			String order,
			Integer currentPage,
			Integer pageSize){
		try {
			String column = "description";
			String filter = "";
			
			atributoPrivado++;
			log.debug("+++++++++++++++++++++++++" + atributoPrivado + "+++++++++++++++++++++++++++++++++++");
			
			if(description!=null) 		{ column ="description"; 	filter = description; }
			else if(trackCode!=null){ column ="trackCode"; 	filter = trackCode; }
			
			if(	orderBy==null || !orderBy.matches("description|trackCode") )
				orderBy = "description";
			
			if( order==null || order.compareTo("ASC")!=0) order = "DESC";
			
			currentPage = currentPage!=null?currentPage:1;
			pageSize = pageSize!=null?pageSize:10;
			
			return packageDao.findByColumnWithPagination(PackageBean.class, column, filter, orderBy, order, currentPage, pageSize);
		} catch (Exception e) {
			log.error("failed!",e);
			return null;
		}
	}

}
