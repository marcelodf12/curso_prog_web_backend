package py.com.tdn.reservation_api.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import py.com.tdn.reservation_api.bean.SucursalBean;
import py.com.tdn.reservation_api.ejb.SucursalEjb;
import py.com.tdn.reservation_api.utils.Messages;
import py.com.tdn.reservation_api.utils.Respuesta;

@Path("/sucursal")
public class SucursalRest {
	
	@EJB
	private SucursalEjb sucursalEjb;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll(){
		Respuesta<List<SucursalBean>> r = new Respuesta<>();
		try {
			List<SucursalBean> sucursales = sucursalEjb.findAll();
			if(sucursales != null && !sucursales.isEmpty()){
				r.setData(sucursales);
				r.setVisible(false);
				return Response.status(200).entity(r).build();
			}
		} catch (Exception e) {
			log.error(e);
			r.setTittle(Messages.ERROR_GENERAL);
			r.setMessage(Messages.ERROR_GENERAL);
			r.setType(Messages.ERROR);
			return Response.status(500).entity(r).build();
		}
		return Response.status(204).entity(r).build();
	}
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response newSucursal(SucursalBean sucursal) { 
    	Respuesta<SucursalBean> r = new Respuesta<>();
    	if(sucursal!=null){
    		try {
    			SucursalBean sucursalNew = sucursalEjb.create(sucursal);
    			if(sucursalNew!=null){
        			r.setTittle(Messages.PERSONA_CREADA_TITTLE);
        			r.setMessage(Messages.PERSONA_CREADA_MESSAGE.replace("{id}", sucursalNew.getIdSucursal().toString()));
        			r.setVisible(true);
        			r.setType(Messages.SUCCESS);
        			r.setData(sucursalNew);
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
