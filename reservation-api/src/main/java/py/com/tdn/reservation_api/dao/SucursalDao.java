package py.com.tdn.reservation_api.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import py.com.tdn.reservation_api.bean.SucursalBean;

@Stateless
public class SucursalDao extends GenericDao<SucursalBean, Integer>{

	@SuppressWarnings("unchecked")
	public List<SucursalBean> findAll() {
		String q = "SELECT s FROM SucursalBean s ORDER BY name";
		Query query = em.createQuery(q);
		return query.getResultList();
	}

}
