package py.com.tdn.reservation_api.rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.bean.ClientBean;
import py.com.tdn.reservation_api.ejb.ClientEjb;
import py.com.tdn.reservation_api.utils.Messages;
import py.com.tdn.reservation_api.utils.Pagination;
import py.com.tdn.reservation_api.utils.Respuesta;

@Path("/client")
public class ClientRest {
	
	@EJB
	private ClientEjb clientEjb;
	
	
	private Logger log = Logger.getLogger(this.getClass());

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClient(@PathParam("id") Integer id) { 
    	Respuesta<ClientBean> r = new Respuesta<>();
    	try {
    		ClientBean p = clientEjb.findById(id);
    		if(p!=null){
    			r.setTittle(Messages.PERSONA_ENCONTRADA);
    			r.setVisible(false);
    			r.setData(p);
    			return Response.status(200).entity(r).build();
    		}
    		r.setTittle(Messages.PERSONA_NO_ENCONTRADA);
    		r.setMessage(Messages.PERSONA_NO_ENCONTRADA_MESSAGE.replace("{id}", id.toString()));
    		r.setVisible(true);
    		r.setType(Messages.WARN);
    		return Response.status(404).entity(r).build(); 		
		} catch (Exception e) {
    		r.setTittle(Messages.TITTLE_ERROR_GENERAL);
    		r.setMessage(Messages.ERROR_GENERAL);
    		r.setVisible(true);
    		r.setType(Messages.ERROR);
			return Response.status(500).entity(r).build();
		}
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClient(
    		@QueryParam("firstName") String firstName,
    		@QueryParam("lastName") String lastName,
    		@QueryParam("email") String email,
    		@QueryParam("idNumber") String idNumber,
    		@QueryParam("orderBy") String orderBy,
    		@QueryParam("order") String order,
    		@QueryParam("currentPage")Integer currentPage,
    		@QueryParam("pageSize")Integer pageSize) { 
    	Respuesta<Pagination<ClientBean>> r = new Respuesta<>();
    	try {
    		Pagination<ClientBean> pages = clientEjb.find(firstName, lastName, email, idNumber, orderBy, order, currentPage, pageSize);
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
    public Response newClient(ClientBean client) { 
    	Respuesta<ClientBean> r = new Respuesta<>();
    	if(client!=null){
    		try {
    			ClientBean clientNew = clientEjb.create(client);
    			if(clientNew!=null){
        			r.setTittle(Messages.PERSONA_CREADA_TITTLE);
        			r.setMessage(Messages.PERSONA_CREADA_MESSAGE.replace("{id}", clientNew.getIdClient().toString()));
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(clientNew);
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

}
