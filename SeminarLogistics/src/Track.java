import java.util.ArrayList;


/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
public class Track {
private final String label;
private final int tracklength;
private final int parktrain;
private final int inspectionposition;
private final int cleaningposition;
private final int repairingposition; //TODO: add different types of repairing positions
private final int washingposition;
private int tracktype;

private int[] occupation;
private ArrayList<Composition> compositionlist;

public Track(String label, int tracklength, int parktrain, int inspectionposition,  int cleaningposition, int repairingposition, int washingposition, int tracktype ) {
	this.label = label;
	this.tracklength = tracklength;
	this.parktrain = parktrain;
	this.inspectionposition = inspectionposition;
	this.cleaningposition = cleaningposition;
	this.repairingposition = repairingposition;
	this.washingposition = washingposition;
	this.tracktype = tracktype;
	occupation = new int[tracklength];
	compositionlist = new ArrayList<Composition>();
}

public Track(String label, int tracklength, int parktrain, int inspectionposition,  int cleaningposition, int repairingposition, int washingposition) {
	this.label = label;
	this.tracklength = tracklength;
	this.parktrain = parktrain;
	this.inspectionposition = inspectionposition;
	this.cleaningposition = cleaningposition;
	this.repairingposition = repairingposition;
	this.washingposition = washingposition;
	tracktype = 0;
	occupation = new int[tracklength];
	compositionlist = new ArrayList<Composition>();
}


public String getLabel(){
	return label;
}
public int getTracklength(){
	return tracklength;
}
public int getParktrain(){
	return parktrain;
}
public int getInspectionposition(){
	return inspectionposition;
}
public int getCleaningposition(){
	return cleaningposition;
}
public int getRepairingposition(){
	return repairingposition;
}
public int getWashingposition(){
	return washingposition;
}
public int getTracktype(){
	return tracktype;
}

public int getOccupied(int position){
	return occupation[position];
}

public void setOccupied(int position){ //TODO: throw exception when position is out of bounds
	this.occupation[position] = 1;
}

public void setOccupied(int beginposition, int endposition){ //TODO: throw exception when position is out of bounds
	for (int i=beginposition; i<=endposition; i++){
		this.setOccupied(i);
	}
}

public void setFree(int position){ //TODO: throw exception when position is out of bounds
	this.occupation[position] = 0;
}

public void setFree(int beginposition, int endposition){ //TODO: throw exception when position is out of bounds
	for (int i=beginposition; i<=endposition; i++){
		this.setFree(i);
	}
}

public ArrayList<Composition> getCompositionlist(){
	return compositionlist;
}

public void addCompositiontoTrackLeft(Composition composition){ //LEFT: links op de map, index 0 //TODO: check total length
	this.compositionlist.add(0,composition);
}

public void addCompositiontoTrackRight(Composition composition){ //RIGHT: rechts op de map, index max lengte arraylist
	this.compositionlist.add(compositionlist.size(),composition);
}

}