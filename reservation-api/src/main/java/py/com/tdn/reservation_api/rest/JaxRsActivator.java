package py.com.tdn.reservation_api.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@ApplicationPath("/api/v1/")
public class JaxRsActivator extends Application {
    @Override
    public Set<Class<?>> getClasses() {
    	final Set<Class<?>> resources = new HashSet<>();
    	
    	/* Agregar todas las clases que contengas servicios rest*/
    	resources.add(ClientRest.class);
    	resources.add(DeliveryRest.class);
    	resources.add(PaqueteRest.class);
    	resources.add(SucursalRest.class);
    	resources.add(UsuarioRest.class);
    	resources.add(CORSFilter.class);
    	
    	/* Para genera documentación */
        resources.add(ApiListingResource.class);
        resources.add(SwaggerSerializers.class);

        return resources;
    }
}
