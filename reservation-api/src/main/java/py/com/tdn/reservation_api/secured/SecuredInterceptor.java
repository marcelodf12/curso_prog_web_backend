package py.com.tdn.reservation_api.secured;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.ejb.UserEjb;
import py.com.tdn.reservation_api.utils.Respuesta;


@Secured
@Interceptor
public class SecuredInterceptor {
	
	@Inject
    HttpServletRequest request;
    
    @EJB
    UserEjb userEjb;
	
	Logger log = Logger.getLogger(SecuredInterceptor.class);
	
	
	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		try {
			String perfilNeed = ic.getMethod().getAnnotation(Secured.class).perfil();
			String token = request.getHeader("authorization");
			Integer id = new Integer(request.getHeader("userID"));
			log.debug("Peticion interceptada, token:"+token);
			if(token==null || token == ""){
				Respuesta<Object> r = new Respuesta<Object>();
				r.setVisible(true);
				r.setTittle("Forbidden");
				r.setType("error");
				r.setMessage("Usted no esta autenticado. Favor loguearse");
				return Response.status(403).entity(r).build();
			}
			log.debug(perfilNeed);
			log.debug(token);
			if(userEjb.checkLogin(id, token))
				return ic.proceed();
		} catch (Exception e) {
			log.error("Failed! ",e);
		}
		Respuesta<Object> r = new Respuesta<Object>();
		r.setVisible(true);
		r.setTittle("Forbidden");
		r.setType("error");
		r.setMessage("No tiene permisos para realizar esta operación");
		return Response.status(403).entity(r).build();	
	}

}
