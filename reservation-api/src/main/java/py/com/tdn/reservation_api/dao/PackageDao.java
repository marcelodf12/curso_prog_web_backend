package py.com.tdn.reservation_api.dao;

import javax.ejb.Stateless;
import javax.persistence.Query;

import py.com.tdn.reservation_api.bean.DeliveryBean;
import py.com.tdn.reservation_api.bean.PackageBean;

@Stateless
public class PackageDao extends GenericDao<PackageBean, Integer>{

	public DeliveryBean getPackageByTrackNumber(String trackNumber){
		String q = "SELECT d FROM DeliveryBean d JOIN FETCH d.tracks WHERE d.pack.trackCode=:trackCode";
		Query query = em.createQuery(q);
		query.setParameter("trackCode", trackNumber);
		query.setMaxResults(1);
		try {
			DeliveryBean d = (DeliveryBean) query.getSingleResult();
			return d;
		} catch (Exception e) {
			return null;
		}
		
	}
}
