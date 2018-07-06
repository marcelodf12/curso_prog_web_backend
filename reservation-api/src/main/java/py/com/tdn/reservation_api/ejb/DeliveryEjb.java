package py.com.tdn.reservation_api.ejb;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

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

@Stateless
public class DeliveryEjb {
	
	@EJB
	ClientDao clientDao;
	
	@EJB
	PackageDao packageDao;
	
	@EJB
	DeliveryDao deliveryDao;
	
	@EJB
	SucursalDao sucursalDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public DeliveryBean createDelivery(DeliveryBean d){
		d.setStatus("PREPARADO");
		d.setTracks(new ArrayList<TrackBean>());
		d.setInputDate(new Date());
		try {
			ClientBean c = clientDao.findById(ClientBean.class, d.getClient().getIdClient());
			PackageBean p = packageDao.findById(PackageBean.class, d.getPack().getIdPackage());
			if(p.getDelivery()==null){
				d.setClient(c);
				d.setPack(p);
				deliveryDao.create(d);
				return d;
			}
		} catch (Exception e) {
			log.error("Failed!", e);
		}
		return null;	
	}
	
	public DeliveryBean addTrack(Integer idDelivery, TrackBean t){
		try {
			DeliveryBean d = deliveryDao.findById(DeliveryBean.class, idDelivery);
			SucursalBean s = sucursalDao.findById(SucursalBean.class, t.getSucursal().getIdSucursal());
			if(d!=null && t!=null && s!=null){
				t.setInputDate(new Date());
				t.setSucursal(s);
				t.setDelivery(d);
				if(d.getTracks()==null || d.getTracks().isEmpty()){
					d.setTracks(new ArrayList<TrackBean>());
					d.setStatus("EN CAMINO");
				}
				d.getTracks().add(t);
				deliveryDao.create(d);
				log.info(d);
				return d;
			}
		} catch (Exception e) {
			log.error("Failed!", e);
		}
		return null;
		
	}


}
