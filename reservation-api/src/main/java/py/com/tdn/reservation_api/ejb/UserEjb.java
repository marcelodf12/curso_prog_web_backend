package py.com.tdn.reservation_api.ejb;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.bean.SucursalBean;
import py.com.tdn.reservation_api.bean.UserBean;
import py.com.tdn.reservation_api.dao.SucursalDao;
import py.com.tdn.reservation_api.dao.UserDao;
import py.com.tdn.reservation_api.dto.ChangePasswordDto;
import py.com.tdn.reservation_api.utils.Pagination;
import py.com.tdn.reservation_api.utils.RandomGenerator;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserEjb {
	
	@EJB
	private UserDao userDao;
	
	@EJB
	private SucursalDao sucursalDao;
	
	@EJB
	private RandomGenerator randomGenerator;
	
	private Logger log = Logger.getLogger(UserEjb.class);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public UserBean createUser(UserBean user){
		if(user.getIdUser()==null && user.getSucursal()!=null){
			try {
				SucursalBean sucursal = sucursalDao.findById(SucursalBean.class, user.getSucursal().getIdSucursal());
				log.debug(sucursal);
				if(sucursal!=null && sucursal.getUser()==null){
					user.setSalt(randomGenerator.newSalt());
					user.setPassword(DigestUtils.sha256Hex(user.getPassword()+user.getSalt()));
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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public UserBean editUser(UserBean user){
		if(user.getIdUser()!=null && user.getSucursal()!=null){
			try {
				SucursalBean sucursal = sucursalDao.findById(SucursalBean.class, user.getSucursal().getIdSucursal());
				UserBean userDB =  userDao.findById(UserBean.class, user.getIdUser());
				if(userDB!=null && sucursal!=null && sucursal.getUser()==null){
					if(user.getName()!=null)
						userDB.setName(user.getName());
					if(user.getEmail()!=null)
						userDB.setEmail(user.getEmail());
					if(user.getSucursal()!=null && user.getSucursal().getIdSucursal()!=null)
						userDB.setSucursal(sucursal);
					return userDao.update(userDB);
				}
			} catch (Exception e) {
				log.error("Failed!", e);
				return null;
			}
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean changePassword(Integer id, ChangePasswordDto pass){
		if(pass.getNewPass().compareTo(pass.getConfirmPass())==0){
			UserBean user = userDao.findById(UserBean.class, id);
			String passHashOld = DigestUtils.sha256Hex(pass.getOldPass()+user.getSalt());
			if(user!=null && passHashOld.compareTo(user.getPassword())==0){
				user.setSalt(randomGenerator.newSalt());
				String passHashNew = DigestUtils.sha256Hex(pass.getNewPass()+user.getSalt());
				user.setPassword(passHashNew);
				userDao.update(user);
				return true;
			}
		}
		return false;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean deleteUser(Integer id){
		try {
			userDao.delete(UserBean.class, id);
			return true;
		} catch (Exception e) {
			log.error("Failed!", e);
			return false;
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String login(Integer id, UserBean user){
		try {
			UserBean userDB = userDao.findById(UserBean.class, id);
			if(userDB.getPassword().compareTo(DigestUtils.sha256Hex(user.getPassword()+userDB.getSalt()))==0){
				userDB.setToken(randomGenerator.newToken());
				Calendar tomorrow = Calendar.getInstance();
				tomorrow.add(Calendar.DAY_OF_YEAR, 1);
				userDB.setExpirationToken(tomorrow);
				userDao.update(userDB);
				return userDB.getToken();
			}
		} catch (Exception e) {
			log.error("Failed!", e);
		}
		return "";
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean logout(Integer id){
		try {
			UserBean userDB = userDao.findById(UserBean.class, id);
			userDB.setToken("");
			userDao.update(userDB);
			return true;
		} catch (Exception e) {
			log.error("Failed!", e);
		}
		return false;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean checkLogin(Integer id, String token){
		try {
			UserBean userDB = userDao.findById(UserBean.class, id);
			if(userDB.getToken().compareTo("")!=0){
				if(userDB.getToken().compareTo(token)==0){
					userDB.setToken(randomGenerator.newToken());
					Calendar tomorrow = Calendar.getInstance();
					tomorrow.add(Calendar.DAY_OF_YEAR, 1);
					userDB.setExpirationToken(tomorrow);
					userDao.update(userDB);
					return true;
				}
			}
		} catch (Exception e) {
			log.error("Failed!", e);
		}
		return false;
	}

}
