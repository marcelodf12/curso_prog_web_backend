package py.com.tdn.reservation_api.dao;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import py.com.tdn.reservation_api.bean.DeliveryBean;
import py.com.tdn.reservation_api.bean.SucursalBean;
import py.com.tdn.reservation_api.bean.TrackBean;

@Stateless
public class DeliveryDao extends GenericDao<DeliveryBean, Integer>{
	
	@Transactional
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DeliveryBean addTrack(Integer idDelivery, TrackBean t){
		UserTransaction tx = null;
		try {
			DeliveryBean d = em.find(DeliveryBean.class, idDelivery);
			if(d.getStatus().compareTo("FINALIZADO")!=0){
				SucursalBean s = em.find(SucursalBean.class, t.getSucursal().getIdSucursal());
				if(d!=null && t!=null && s!=null){
					t.setSucursal(s);
					t.setDelivery(d);
					em.persist(t);
					t.setInputDate(new Date());
					
					d.setStatus("EN CAMINO");
					
				}
			}
			log.info(d.getPack().getIdPackage());
			log.info(d.getClient().getIdClient());
			for(TrackBean t1: d.getTracks()){
				log.info(t1.getIdTrack());
			}
			return d;
		} catch (Exception e) {
			log.error("Failed!", e);
		}
		return null;
	}
	
	@Transactional
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DeliveryBean finalizar(Integer idDelivery){
		DeliveryBean d = em.find(DeliveryBean.class, idDelivery);
		d.getPack().getIdPackage();
		for(TrackBean t : d.getTracks()){
			t.getIdTrack();
			t.getSucursal().getIdSucursal();
		}
		d.setStatus("FINALIZADO");
		return d;
	}

}
