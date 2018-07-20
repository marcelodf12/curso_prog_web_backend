package py.com.tdn.reservation_api.dao;

import javax.ejb.Stateless;

import py.com.tdn.reservation_api.bean.ClientBean;
import py.com.tdn.reservation_api.bean.DeliveryBean;

@Stateless
public class ClientDao extends GenericDao<ClientBean, Integer> {

	public ClientBean test() {
		ClientBean c = em.find(DeliveryBean.class, 25).getClient();
		log.debug(c.getIdClient());
		//log.debug(c);
		return c;
	}

}
