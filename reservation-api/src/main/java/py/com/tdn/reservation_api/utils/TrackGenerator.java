package py.com.tdn.reservation_api.utils;

import java.security.SecureRandom;

public class TrackGenerator {
	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final SecureRandom rnd = new SecureRandom();
	
	public static String newTrack(){
		int len = 32;
		StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( CHARACTERS.charAt( rnd.nextInt(CHARACTERS.length()) ) );
		   return sb.toString();		
	}
	
	private TrackGenerator(){
		super();
	}

}
