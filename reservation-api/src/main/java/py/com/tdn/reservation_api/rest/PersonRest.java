package py.com.tdn.reservation_api.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.com.tdn.reservation_api.dto.Person;
import py.com.tdn.reservation_api.ejb.PersonEjb; 
 
@Path("/persona")
public class PersonRest { 
 
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) { 
    	PersonEjb personEjb = PersonEjb.getIntance();
        Person p = personEjb.findById(id);
        if(p!=null)
        	return Response.status(200).entity(p).build(); 
        return Response.status(404).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPerson() { 
    	PersonEjb personEjb = PersonEjb.getIntance();
        List<Person> list = personEjb.listAll();
        if(list!=null && list.size()>0)
        	return Response.status(200).entity(list).build(); 
        return Response.status(204).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response newPerson(Person p) { 
    	PersonEjb personEjb = PersonEjb.getIntance();
        Person pNew = personEjb.save(p);
        if(p!=null)
        	return Response.status(201).entity(pNew).build(); 
        return Response.status(500).build();
    } 
 
}