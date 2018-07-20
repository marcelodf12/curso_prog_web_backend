package py.com.tdn.reservation_api.utils;

import java.security.SecureRandom;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import org.apache.log4j.Logger;

@Singleton
public class TrackGenerator {
	
	private String CHARACTERS;
	private SecureRandom rnd;
	
	private Logger log = Logger.getLogger(TrackGenerator.class);
	
	@PostConstruct
	public void init(){
		log.debug("Inicializando el Singleton TrackGenerator");
		CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		rnd = new SecureRandom();
	}
	
	
	public String newTrack(){
		int len = 32;
		StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( CHARACTERS.charAt( rnd.nextInt(CHARACTERS.length()) ) );
		   return sb.toString();		
	}
	
}
