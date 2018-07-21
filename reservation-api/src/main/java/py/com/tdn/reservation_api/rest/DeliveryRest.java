package py.com.tdn.reservation_api.rest;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.bean.DeliveryBean;
import py.com.tdn.reservation_api.bean.TrackBean;
import py.com.tdn.reservation_api.ejb.DeliveryEjb;
import py.com.tdn.reservation_api.utils.Messages;
import py.com.tdn.reservation_api.utils.Respuesta;

@Path("/delivery")
public class DeliveryRest {
	
	@EJB
	private DeliveryEjb deliverEjb;
	
	Logger log = Logger.getLogger(DeliveryRest.class);
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response newClient(DeliveryBean delivery) { 
    	Respuesta<DeliveryBean> r = new Respuesta<>();
    	if(delivery!=null){
    		try {
    			DeliveryBean deliveryNew = deliverEjb.createDelivery(delivery);
    			if(deliveryNew!=null){
        			r.setTittle(Messages.PERSONA_CREADA_TITTLE);
        			r.setMessage(Messages.PERSONA_CREADA_MESSAGE.replace("{id}", deliveryNew.getIdDelivery().toString()));
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(deliveryNew);
        			return Response.status(201).entity(r).build();
    			}
			} catch (Exception e) {
				log.error(e);
			}
    		r.setTittle(Messages.TITTLE_ERROR_GENERAL);
    		r.setMessage(Messages.ERROR_GENERAL);
    		r.setVisible(true);
    		r.setType(Messages.ERROR);
			return Response.status(500).build();	
    	}
		r.setTittle(Messages.PERSONA_NULL);
		r.setVisible(true);
		r.setType(Messages.ERROR);
		return Response.status(400).build();	
    }
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/track")
    public Response addTrack(@PathParam("id") Integer idDelivery, TrackBean t) { 
    	Respuesta<DeliveryBean> r = new Respuesta<>();
    	if(t!=null){
    		try {
    			DeliveryBean deliveryNew = deliverEjb.addTrack(idDelivery, t);
    			if(deliveryNew!=null){
        			r.setTittle(Messages.PERSONA_ACTUALIZADA_TITTLE);
        			r.setMessage(Messages.PERSONA_ACTUALIZADA_MESSAGE.replace("{id}", deliveryNew.getIdDelivery().toString()));
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(deliveryNew);
        			return Response.status(201).entity(r).build();
    			}
			} catch (Exception e) {
				log.error("Failed!", e);
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
	public Response finalizar(@PathParam("id") Integer idDelivery) { 
		Respuesta<DeliveryBean> r = new Respuesta<>();
		if(idDelivery!=null){
			try {
				DeliveryBean d = deliverEjb.finalizar(idDelivery);
				if(d!=null){
					r.setTittle(Messages.PERSONA_ACTUALIZADA_TITTLE);
        			r.setMessage(Messages.PERSONA_ACTUALIZADA_MESSAGE.replace("{id}", d.getIdDelivery().toString()));
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(d);
        			return Response.status(200).entity(r).build();
				}
			} catch (Exception e) {
				log.error("Failed!", e);
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
