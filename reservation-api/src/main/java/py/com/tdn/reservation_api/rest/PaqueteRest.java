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

import py.com.tdn.reservation_api.bean.DeliveryBean;
import py.com.tdn.reservation_api.bean.PackageBean;
import py.com.tdn.reservation_api.ejb.PackageEjb;
import py.com.tdn.reservation_api.utils.Messages;
import py.com.tdn.reservation_api.utils.Pagination;
import py.com.tdn.reservation_api.utils.Respuesta;

@Path("/package")
public class PaqueteRest {
	
	Logger log = Logger.getLogger(this.getClass());
	
	@EJB
	private PackageEjb packageEjb;
	
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	public Response createPackage(PackageBean p){
		Respuesta<PackageBean> r = new Respuesta<>();
		if(p!=null){
			try {
				p = packageEjb.createPackage(p);
				if(p!=null && p.getIdPackage()!=null){
					r.setTittle(Messages.PERSONA_CREADA_TITTLE);
					r.setMessage(Messages.PERSONA_CREADA_MESSAGE);
					r.setType(Messages.SUCCESS);
					r.setVisible(true);
					r.setData(p);
					return Response.status(201).entity(r).build();
				}
			} catch (Exception e) {
				log.error("Failed!", e);
			}
		}
		r.setTittle(Messages.ERROR);
		r.setMessage(Messages.ERROR_GENERAL);
		r.setType(Messages.ERROR);
		r.setVisible(true);
		return Response.status(500).entity(r).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{track}")
	public Response getPackageByTrack(@PathParam("track") String track){
		Respuesta<DeliveryBean> r = new Respuesta<>();
		if(track!=null){
			try {
				DeliveryBean d = packageEjb.getPackageByTrackNumber(track);
				if(d!=null){
					r.setTittle(Messages.PERSONA_ENCONTRADA);
					r.setMessage(Messages.PERSONA_ENCONTRADA);
					r.setType(Messages.SUCCESS);
					r.setVisible(false);
					r.setData(d);
					return Response.status(200).entity(r).build();
				}else{
					r.setTittle(Messages.PERSONA_NO_ENCONTRADA);
					r.setMessage(Messages.PERSONA_NO_ENCONTRADA);
					r.setType(Messages.SUCCESS);
					r.setVisible(true);
					return Response.status(404).entity(r).build();
				}
			} catch (Exception e) {
				log.error("Failed!", e);
			}
		}
		r.setTittle(Messages.ERROR);
		r.setMessage(Messages.ERROR_GENERAL);
		r.setType(Messages.ERROR);
		r.setVisible(true);
		return Response.status(500).entity(r).build();
		
		
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClient(
    		@QueryParam("description") String description,
    		@QueryParam("trackCode") String trackCode,
    		@QueryParam("orderBy") String orderBy,
    		@QueryParam("order") String order,
    		@QueryParam("currentPage")Integer currentPage,
    		@QueryParam("pageSize")Integer pageSize) { 
    	Respuesta<Pagination<PackageBean>> r = new Respuesta<>();
    	try {
    		Pagination<PackageBean> pages = packageEjb.find(description, trackCode, orderBy, order, currentPage, pageSize);
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
	

}
