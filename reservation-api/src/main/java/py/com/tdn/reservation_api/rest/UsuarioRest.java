package py.com.tdn.reservation_api.rest;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.bean.UserBean;
import py.com.tdn.reservation_api.dto.ChangePasswordDto;
import py.com.tdn.reservation_api.ejb.UserEjb;
import py.com.tdn.reservation_api.utils.Messages;
import py.com.tdn.reservation_api.utils.Pagination;
import py.com.tdn.reservation_api.utils.Respuesta;

@Path("/user")
public class UsuarioRest {
	
	@EJB
	private UserEjb userEjb;
	
	private Logger log =  Logger.getLogger(UsuarioRest.class);
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(
    		@QueryParam("name") String name,
    		@QueryParam("email") String email,
    		@QueryParam("orderBy") String orderBy,
    		@QueryParam("order") String order,
    		@QueryParam("currentPage")Integer currentPage,
    		@QueryParam("pageSize")Integer pageSize) { 
    	Respuesta<Pagination<UserBean>> r = new Respuesta<>();
    	try {
    		Pagination<UserBean> pages = userEjb.find(name, email, orderBy, order, currentPage, pageSize);
    		if(pages!=null && !pages.getItems().isEmpty()){
    			r.setTittle(Messages.PERSONA_ENCONTRADA);
    			r.setVisible(false);
    			r.setData(pages);
    			return Response.status(200).entity(r).build();
    		}
    		r.setTittle(Messages.PERSONA_NO_ENCONTRADA);
    		r.setMessage(Messages.NO_HAY_PERSONAS);
    		r.setVisible(true);
    		r.setType("warn");
    		return Response.status(204).entity(r).build();
		} catch (Exception e) {
    		r.setTittle(Messages.TITTLE_ERROR_GENERAL);
    		r.setMessage(Messages.ERROR_GENERAL);
    		r.setVisible(true);
    		r.setType(Messages.ERROR);
			return Response.status(500).entity(r).build();			
		}
    }
    
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response newUser(UserBean user) { 
    	Respuesta<UserBean> r = new Respuesta<>();
    	if(user!=null){
    		try {
    			UserBean userNew = userEjb.createUser(user);
    			if(userNew!=null){
        			r.setTittle(Messages.PERSONA_CREADA_TITTLE);
        			r.setMessage(Messages.PERSONA_CREADA_MESSAGE.replace("{id}", userNew.getIdUser().toString()));
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(userNew);
        			return Response.status(201).entity(r).build();
    			}
			} catch (Exception e) {
				log.error(e);
			}
    		r.setTittle(Messages.TITTLE_ERROR_GENERAL);
    		r.setMessage(Messages.ERROR_GENERAL);
    		r.setVisible(true);
    		r.setType(Messages.ERROR);
			return Response.status(500).entity(r).build();	
    	}
		r.setTittle(Messages.PERSONA_NULL);
		r.setVisible(true);
		r.setType(Messages.ERROR);
		return Response.status(400).entity(r).build();	
    }
	
	@PUT
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(@PathParam("id") Integer id, UserBean user) { 
    	Respuesta<UserBean> r = new Respuesta<>();
    	if(user!=null){
    		try {
    			user.setIdUser(id);
    			UserBean userNew = userEjb.editUser(user);
    			if(userNew!=null){
        			r.setTittle(Messages.PERSONA_CREADA_TITTLE);
        			r.setMessage(Messages.PERSONA_CREADA_MESSAGE.replace("{id}", userNew.getIdUser().toString()));
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(userNew);
        			return Response.status(201).entity(r).build();
    			}
			} catch (Exception e) {
				log.error(e);
			}
    		r.setTittle(Messages.TITTLE_ERROR_GENERAL);
    		r.setMessage(Messages.ERROR_GENERAL);
    		r.setVisible(true);
    		r.setType(Messages.ERROR);
			return Response.status(500).entity(r).build();	
    	}
		r.setTittle(Messages.PERSONA_NULL);
		r.setVisible(true);
		r.setType(Messages.ERROR);
		return Response.status(400).entity(r).build();	
    }
	
	@PUT
	@Path("/{id}/password")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePass(@PathParam("id") Integer id, ChangePasswordDto pass){
    	Respuesta<Boolean> r = new Respuesta<>();
    	if(pass!=null){
    		try {
    			Boolean change = userEjb.changePassword(id, pass);
    			if(change){
        			r.setTittle(Messages.CHANGE_PASS_SUCCESS);
        			r.setMessage("");
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(change);
        			return Response.status(200).entity(r).build();
    			}
			} catch (Exception e) {
				log.error(e);
			}
    	}
		r.setTittle(Messages.CHANGE_PASS_ERROR);
		r.setMessage("");
		r.setVisible(true);
		r.setType(Messages.ERROR);
		return Response.status(400).entity(r).build();		
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("id") Integer id){
    	Respuesta<Boolean> r = new Respuesta<>();
    	if(id!=null){
    		try {
    			Boolean delete = userEjb.deleteUser(id);
    			if(delete){
        			r.setTittle(Messages.DELETE_USER_SUCCESS);
        			r.setMessage("");
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(delete);
        			return Response.status(200).entity(r).build();
    			}
			} catch (Exception e) {
				log.error(e);
			}
    	}
		r.setTittle(Messages.DELETE_USER_ERROR);
		r.setMessage("");
		r.setVisible(true);
		r.setType(Messages.ERROR);
		return Response.status(400).entity(r).build();	
		
		
	}
	
	@POST
	@Path("/{id}/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(@PathParam("id") Integer id, UserBean user){
    	Respuesta<String> r = new Respuesta<>();
    	if(id!=null){
    		try {
    			String token = userEjb.login(id,user);
    			if(token.compareTo("")!=0){
        			r.setTittle(Messages.SUCCESS);
        			r.setMessage("");
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(token);
        			return Response.status(200).entity(r).build();
    			}
			} catch (Exception e) {
				log.error(e);
			}
    	}
		r.setTittle(Messages.ERROR);
		r.setMessage("");
		r.setVisible(true);
		r.setType(Messages.ERROR);
		return Response.status(400).entity(r).build();	
	}
	
	@POST
	@Path("/{id}/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logoutUser(@PathParam("id") Integer id){
    	Respuesta<Boolean> r = new Respuesta<>();
    	if(id!=null){
    		try {
    			Boolean logout = userEjb.logout(id);
    			if(logout){
        			r.setTittle(Messages.SUCCESS);
        			r.setMessage("");
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(logout);
        			return Response.status(200).entity(r).build();
    			}
			} catch (Exception e) {
				log.error(e);
			}
    	}
		r.setTittle(Messages.ERROR);
		r.setMessage("");
		r.setVisible(true);
		r.setType(Messages.ERROR);
		return Response.status(400).entity(r).build();	
	}


}
