package tuio;

import TUIO.*;

import java.util.*;
import widget.*;

public class MTedt implements TuioListener {

	TuioClient client=null;
	MTSurface surface; // la surface qui recevra les messages des curseurs.
	
	private void initConnexion() {
		int port=3333;
		client=new TuioClient(port);
		client.connect();
		if (!client.isConnected()) {
			System.exit(1);
		}
		System.out.println("connexion");		
	}
	
	public MTedt() {
		initConnexion();
	}
	
	public MTedt(MTSurface s) {
		initConnexion();
		surface=s;
	}
	
	public void stop() {
		client.disconnect();
		System.out.println("deconnexion");
	}
	
		
	/** Listeners **/
	
	public void addTuioObject(TuioObject tobj) {
	}

	public void updateTuioObject(TuioObject tobj) {
	}
	
	public void removeTuioObject(TuioObject tobj) {
	}

	public void addTuioCursor(TuioCursor tcur) {
	}

	public void updateTuioCursor(TuioCursor tcur) {
	}
	
	public void removeTuioCursor(TuioCursor tcur) {
	}

	
	public void refresh(TuioTime frameTime) {
	}

}
