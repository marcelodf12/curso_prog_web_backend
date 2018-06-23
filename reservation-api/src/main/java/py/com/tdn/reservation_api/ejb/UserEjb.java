package py.com.tdn.reservation_api.ejb;

import javax.ejb.EJB;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.bean.SucursalBean;
import py.com.tdn.reservation_api.bean.UserBean;
import py.com.tdn.reservation_api.dao.UserDao;
import py.com.tdn.reservation_api.dto.ChangePasswordDto;
import py.com.tdn.reservation_api.utils.Pagination;

public class UserEjb {
	
	@EJB
	private UserDao userDao;
	
	@EJB
	private SucursalDao sucursalDao;
	
	private Logger log = Logger.getLogger(UserEjb.class);
	
	public Pagination<UserBean> find(
				String name,
				String email,
				String orderBy,
				String order,
				Integer currentPage,
				Integer pageSize
			){
		String column = "name";
		String filter = "";
		
		if(	orderBy==null || !orderBy.matches("name|email") )
			orderBy = "name";
		
		if( order==null || order.compareTo("ASC")!=0) order = "DESC";
		
		if(name!=null && name.compareTo("")==0 )		{ column = "name" ; filter=name; }
		else if(email!=null && email.compareTo("")==0) 	{ column = "email" ; filter=email; };
		
		currentPage = currentPage!=null?currentPage:1;
		pageSize = pageSize!=null?pageSize:10;
		
		return userDao.findByColumnWithPagination(UserBean.class, column, filter, orderBy, order, currentPage, pageSize);
	}
	
	public UserBean createUser(UserBean user){
		if(user.getIdUser()==null && user.getSucursal()!=null){
			try {
				SucursalBean sucursal = sucursalDao.findById(SucursalBean.class, user.getSucursal().getIdSucursal());
				if(sucursal!=null){
					user.setSucursal(sucursal);
					return userDao.create(user);
				}
			} catch (Exception e) {
				log.error("Failed!", e);
				return null;
			}
		}
		return null;
	}
	
	public UserBean editUser(UserBean user){
		if(user.getIdUser()!=null && user.getSucursal()!=null){
			try {
				SucursalBean sucursal = sucursalDao.findById(SucursalBean.class, user.getSucursal().getIdSucursal());
				if(sucursal!=null){
					user.setSucursal(sucursal);
					return userDao.update(user);
				}
			} catch (Exception e) {
				log.error("Failed!", e);
				return null;
			}
		}
		return null;
	}
	
	public Boolean changePassword(Integer id, ChangePasswordDto pass){
		if(pass.getNewPass().compareTo(pass.getConfirmPass())==0){
			UserBean user = userDao.findById(UserBean.class, id);
			String passHashNew = DigestUtils.sha256Hex(pass.getNewPass());
			String passHashOld = DigestUtils.sha256Hex(pass.getOldPass());
			if(user!=null && passHashOld.compareTo(user.getPassword())==0){
				user.setPassword(passHashNew);
				userDao.update(user);
				return true;
			}
		}
		return false;
	}
	
	public Boolean deleteUser(Integer id){
		try {
			userDao.delete(UserBean.class, id);
			return true;
		} catch (Exception e) {
			log.error("Failed!", e);
			return false;
		}
		
	}

}
