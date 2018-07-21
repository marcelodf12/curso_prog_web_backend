package py.com.tdn.reservation_api.ejb;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.bean.ClientBean;
import py.com.tdn.reservation_api.bean.DeliveryBean;
import py.com.tdn.reservation_api.bean.PackageBean;
import py.com.tdn.reservation_api.bean.SucursalBean;
import py.com.tdn.reservation_api.bean.TrackBean;
import py.com.tdn.reservation_api.dao.ClientDao;
import py.com.tdn.reservation_api.dao.DeliveryDao;
import py.com.tdn.reservation_api.dao.PackageDao;
import py.com.tdn.reservation_api.dao.SucursalDao;
import py.com.tdn.reservation_api.dao.TrackDao;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DeliveryEjb {
	
	@EJB
	ClientDao clientDao;
	
	@EJB
	PackageDao packageDao;
	
	@EJB
	DeliveryDao deliveryDao;
	
	@EJB
	SucursalDao sucursalDao;
	
	@EJB
	TrackDao trackDao;
	
	@Resource
	private SessionContext sessionContext;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public DeliveryBean createDelivery(DeliveryBean d){
		//UserTransaction tx = null;
		d.setStatus("PREPARADO");
		d.setTracks(new ArrayList<TrackBean>());
		d.setInputDate(new Date());
		try {
			//tx = sessionContext.getUserTransaction();
			//tx.begin();
			ClientBean c = clientDao.findById(ClientBean.class, d.getClient().getIdClient());
			PackageBean p = packageDao.findById(PackageBean.class, d.getPack().getIdPackage());
			if(p.getDelivery()==null){
				d.setClient(c);
				d.setPack(p);
				deliveryDao.create(d);
				//tx.commit();
				return d;
			}
		} catch (Exception e) {
			log.error("Failed!", e);
		}
		return null;	
	}
	
	public DeliveryBean addTrack(Integer idDelivery, TrackBean t){
		return deliveryDao.addTrack(idDelivery, t);
	}
	
	public DeliveryBean finalizar(Integer idDelivery){
		
		return deliveryDao.finalizar(idDelivery);
	}


}
