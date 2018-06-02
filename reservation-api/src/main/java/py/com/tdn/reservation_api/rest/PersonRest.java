package py.com.tdn.reservation_api.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.com.tdn.reservation_api.bean.PersonBean;
import py.com.tdn.reservation_api.ejb.PersonEjb;
import py.com.tdn.reservation_api.utils.Messages;
import py.com.tdn.reservation_api.utils.Respuesta; 
 
@Path("/persona")
public class PersonRest { 
 
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) { 
    	PersonEjb personEjb = PersonEjb.getIntance();
    	Respuesta<PersonBean> r = new Respuesta<>();
    	try {
    		PersonBean p = personEjb.findById(id);
    		if(p!=null){
    			r.setTittle(Messages.PERSONA_ENCONTRADA);
    			r.setVisible(false);
    			r.setData(p);
    			return Response.status(200).entity(r).build();
    		}
    		r.setTittle(Messages.PERSONA_NO_ENCONTRADA);
    		r.setMessage(Messages.PERSONA_NO_ENCONTRADA_MESSAGE.replace("{id}", id.toString()));
    		r.setVisible(true);
    		r.setType("warn");
    		return Response.status(404).entity(r).build(); 		
		} catch (Exception e) {
    		r.setTittle(Messages.TITTLE_ERROR_GENERAL);
    		r.setMessage(Messages.ERROR_GENERAL);
    		r.setVisible(true);
    		r.setType("error");
			return Response.status(500).entity(r).build();
		}
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPerson() { 
    	PersonEjb personEjb = PersonEjb.getIntance();
    	Respuesta<List<PersonBean>> r = new Respuesta<>();
    	try {
    		List<PersonBean> list = personEjb.listAll();
    		if(list!=null && list.size()>0){
    			r.setTittle(Messages.PERSONA_ENCONTRADA);
    			r.setVisible(false);
    			r.setData(list);
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
    		r.setType("error");
			return Response.status(500).entity(r).build();			
		}
    }
    
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response newPerson(PersonBean p) { 
    	PersonEjb personEjb = PersonEjb.getIntance();
    	
    	Respuesta<PersonBean> r = new Respuesta<>();
    	if(p!=null){
    		try {
    			PersonBean pNew = personEjb.save(p);
    			if(pNew!=null){
        			r.setTittle(Messages.PERSONA_CREADA_TITTLE);
        			r.setMessage(Messages.PERSONA_CREADA_MESSAGE.replace("{id}", pNew.getId().toString()));
        			r.setVisible(true);
        			r.setType("success");
        			r.setData(pNew);
        			return Response.status(201).entity(r).build();
    			}
			} catch (Exception e) {
				System.out.print(e);
			}
    		r.setTittle(Messages.TITTLE_ERROR_GENERAL);
    		r.setMessage(Messages.ERROR_GENERAL);
    		r.setVisible(true);
    		r.setType("error");
			return Response.status(500).entity(r).build();	
    	}
		r.setTittle(Messages.PERSONA_NULL);
		r.setVisible(true);
		r.setType("error");
		return Response.status(400).entity(r).build();	
    }
    
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, PersonBean p) { 
    	PersonEjb personEjb = PersonEjb.getIntance();
    	
    	Respuesta<PersonBean> r = new Respuesta<>();
    	if(p!=null){
    		try {
    			p.setId(id);
    			PersonBean pNew = personEjb.save(p);
    			if(pNew!=null){
        			r.setTittle(Messages.PERSONA_ACTUALIZADA_TITTLE);
        			r.setMessage(Messages.PERSONA_ACTUALIZADA_MESSAGE);
        			r.setVisible(true);
        			r.setType("success");
        			r.setData(pNew);
        			return Response.status(200).entity(r).build();
    			}
			} catch (Exception e) {
				System.out.print(e);
			}
    		r.setTittle(Messages.TITTLE_ERROR_GENERAL);
    		r.setMessage(Messages.ERROR_GENERAL);
    		r.setVisible(true);
    		r.setType("error");
			return Response.status(500).entity(r).build();	
    	}
		r.setTittle(Messages.PERSONA_NULL);
		r.setVisible(true);
		r.setType("error");
		return Response.status(400).entity(r).build();
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Integer id){
    	PersonEjb personEjb = PersonEjb.getIntance();
    	Respuesta<Object> r = new Respuesta<>();
    	try {
    		personEjb.delete(id);
		} catch (Exception e) {
    		r.setTittle(Messages.TITTLE_ERROR_GENERAL);
    		r.setMessage(Messages.ERROR_GENERAL);
    		r.setVisible(true);
    		r.setType("error");
			return Response.status(500).entity(r).build();
		}
		r.setTittle(Messages.PERSONA_ELIMINADA_TITTLE);
		r.setMessage(Messages.PERSONA_ELIMINADA_MESSAGE);
		r.setVisible(true);
		r.setType("success");
		return Response.status(204).entity(r).build();
    }
    
 
}