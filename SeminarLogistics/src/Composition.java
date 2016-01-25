import java.io.IOException;
import java.util.*;

/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */

public class Composition {

	private ArrayList<Train> compositiontrains;
	private int compositionlength;
	private int compositionsize; //number of trains
	private Track compositiontrack;
	private int locationontrack;
	//private int time; Because it is not relevant anymore once the train is in our system/shunting yard

	public Composition(ArrayList<Train> compositiontrains){
		this.compositiontrains = compositiontrains;
		this.updateComposition();
		compositiontrack = null;
		locationontrack = -1;
	}
	
	public Composition(ArrayList<Train> compositiontrains, Track compositiontrack, int locationontrack){
		this.compositiontrains = compositiontrains;
		this.updateComposition();
		this.compositiontrack = compositiontrack;
		this.locationontrack = locationontrack;
	}

	//TODO: Do not forget to change the position for all functions!

	//Splitting a composition of size 2, returns 2 compositions of size 1 train

	public Composition coupleComposition(Composition addcomposition) throws MisMatchException, IndexOutofBoundsException{ //TODO: het is geen return functie!!!!!! pas aan naar VOID na overleg met Friso
		if (locationontrack == -1){
	
		this.compositiontrains.addAll(addcomposition.getCompositionList());
		}
		else{
			int a = this.getPositionOnTrack();
			int b = addcomposition.getPositionOnTrack();
			int x = Math.abs(a-b);
			if (x!=1){
				throw new MisMatchException("Compositions aren't located next to each other when coupling on track "+this.compositiontrack.getLabel()+ " (function coupleComposition)");
			}
			if (a>b)
			{
				this.locationontrack = addcomposition.getLocationOnTrack();
			}
			this.compositiontrack.removeComposition(b);
		}
		
		//addcomposition = null; If this does not work well, we need to make the function in Main.
		//What happens if: In an ArrayList of objects, the objects is set to null but is not removed from the list?
		// The objects is then removed from the ArrayList completely.
		this.updateComposition();
		return this;
	}
	
	public Composition decoupleComposition(int locationdecouple) throws MisMatchException{
		
		ArrayList<Train> newcompositionlist = new ArrayList<>();
		int a = this.getSize();
		for(int i=0;i<a-1-locationdecouple;i++){
			newcompositionlist.add(0, this.getTrain(a-1-i));
			this.compositiontrains.remove(a-1-i);
		}
		
		this.updateComposition(); //TODO: TIJD EN VERTREKPLEK TOEVOEGEN!!!!
		
		Composition newcomposition; //TODO: update testfunctie
		if (locationontrack == -1){ //if locationontrack == -1, composition hasn't been placed on a track yet
		newcomposition = new Composition(newcompositionlist); //TODO: 1.0, 1 ZEKER NOG VERANDEREN!!!!!!!!!!!
		}
		else{ //if locationontrack != -1, composition has been placed on a track yet
			newcomposition = new Composition(newcompositionlist,compositiontrack,locationontrack+this.compositionlength); //TODO: 1.0, 1 ZEKER NOG VERANDEREN!!!!!!!!!!!
			compositiontrack.addCompositiontoTrack(newcomposition,this.getPositionOnTrack());
		}
		return newcomposition;
	}

	public int getLength(){
		return compositionlength;
	}
	public int getSize(){
		return compositionsize;
	}

	public Train getTrain(int i){
		return this.compositiontrains.get(i);
	}

	public ArrayList<Train> getCompositionList(){
		return compositiontrains;
	}
	private void updateComposition(){
		int templength = 0;
		for(int i=0;i<this.getCompositionList().size();i++){ //TODO: What if composition = 0?
			templength += this.compositiontrains.get(i).getLength();
			this.getTrain(i).setPosition(i);
			this.getTrain(i).setComposition(this);
		}
		this.compositionsize = compositiontrains.size();
		this.compositionlength = templength;
	}
	public Track getTrack(){
		return compositiontrack;
	}
	public int getLocationOnTrack(){
		return locationontrack;
	}
	
	//int aorb indicates whether we want to add the composition to the a side or the b side of the track
	public void moveComposition(Track track, int location, String aorb) throws TrackNotFreeException, IndexOutofBoundsException, IOException, MisMatchException{ //TODO testfunctie
		//First check whether there is room on the new track
		for (int i = location; i<location+compositionlength;i++){
			if (track.getOccupied(i) != 0){
				throw new TrackNotFreeException("Track "+track.getLabel()+" has no room for a composition of length "+compositionlength+" at location "+location+" (function moveComposition)");
			}
		}
		compositiontrack.setFree(locationontrack,locationontrack+compositionlength); //set current track free on correct indices
		compositiontrack.removeComposition(this.getPositionOnTrack());
		
		compositiontrack = track; //update compositiontrack
		locationontrack = location; //update locationontrack
		//addcomposition to compositionlist of new track:
		compositiontrack.setOccupied(locationontrack,locationontrack+compositionlength); //set new track occupied on correct indices
		if (aorb == "a"){
			compositiontrack.addCompositiontoTrackLeft(this);
		}
		else if (aorb == "b") {
			compositiontrack.addCompositiontoTrackRight(this);
		}
		else{
			throw new IOException("Input in function moveComposition must be a or b, and is "+aorb);
		}
	}
	
	public int getPositionOnTrack() throws MisMatchException{ //TODO: testfunctie
		int positionontrack = -1;
		ArrayList<Composition> compositionlist = new ArrayList<>();
		compositionlist = compositiontrack.getCompositionlist();
		for (int i = 0; i<compositionlist.size(); i++){
			if (compositionlist.get(i)== this){
				positionontrack = i;
				break;
			}
		}
		if(positionontrack == -1 || positionontrack > compositionlist.size()-1){
			throw new MisMatchException("(Compositions on track) and (track of composition) of track"+compositiontrack.getLabel()+"do not match");
		}
		return positionontrack;
	}
	
}