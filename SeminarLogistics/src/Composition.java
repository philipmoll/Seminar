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


	public Composition(ArrayList<Train> compositiontrains) throws IOException{
		this.compositiontrains = compositiontrains;
		this.updateComposition();
		compositiontrack = null; //default
		locationontrack = -1; //default
	}

	public Composition(ArrayList<Train> compositiontrains, Track compositiontrack, int locationontrack) throws IndexOutOfBoundsException, TrackNotFreeException, IOException{
		this.compositiontrains = compositiontrains;

		//add composition to compositiontrack
		this.compositiontrack = compositiontrack;
		this.locationontrack = locationontrack;
		for (int i = locationontrack; i < locationontrack+compositionlength; i++){
			if (compositiontrack.getOccupied(i)==1){
				throw new TrackNotFreeException("Composition cannot be initialized on "+compositiontrack.getLabel()+" at position " + locationontrack + "as there is not enough free track space from location " + i + " onward");
			}
		}
		//check where to insert the composition in the compositionlist of the track
		ArrayList<Composition> compositionlist = this.compositiontrack.getCompositionlist();
		if (compositionlist.size()==0){ //just add if the track has no compositions yet
			this.compositiontrack.addCompositiontoTrackLeft(this);
		}
		else if (compositionlist.size()==1){ //check whether we insert it left or right of the existing train if there is only one composition
			if (compositionlist.get(0).getLocationOnTrack()<this.locationontrack){
				this.compositiontrack.addCompositiontoTrackRight(this);
			}
			else if (compositionlist.get(0).getLocationOnTrack()>this.locationontrack){
				this.compositiontrack.addCompositiontoTrackLeft(this);
			}
		}
		else{
			int[] locations = new int[compositionlist.size()];
			for (int i = 0; i<compositionlist.size();i++){
				locations[i] = compositionlist.get(i).getLocationOnTrack();
				//System.out.print(locations[i]+" ");
			}
			boolean check = false;
			int i = 0;
			int newposition = -1;
			if (locationontrack < locations[0]){ //if new train will be at first location
				newposition =0;
			}
			else if (locationontrack > locations[0] && locationontrack < locations[compositionlist.size()-1]){
				while (check == false && i < compositionlist.size()-1){
					if(locationontrack > locations[i]&&locationontrack <locations[i+1]){
						newposition = i+1;
						check = true;
					}
					i++;
				}
			}
			else if (locationontrack > locations[compositionlist.size()-1]){
				newposition = compositionlist.size();
			}

			this.compositiontrack.addCompositiontoTrack(this,newposition);
		}
		this.updateComposition();
		compositiontrack.setOccupied(locationontrack,locationontrack+compositionlength-1);
		//je hoeft dus niet meer zelf toe te voegen aan je track als je een compositie op een track maakt!! :)
	}

	//Splitting a composition of size 2, returns 2 compositions of size 1 train

	public void coupleComposition(Composition addcomposition) throws MisMatchException, IndexOutOfBoundsException, IOException{
		this.compositiontrains.addAll(addcomposition.getTrainList());

		//addcomposition = null; If this does not work well, we need to make the function in Main.
		//What happens if: In an ArrayList of objects, the objects is set to null but is not removed from the list?
		// The objects is then removed from the ArrayList completely.

		if (locationontrack != -1){
			int a = this.getPositionOnTrack();
			int b = addcomposition.getPositionOnTrack();
			this.compositiontrack.removeComposition(b);
			int x = Math.abs(a-b);
			if (x!=1){
				throw new MisMatchException("There are other compositions between the coupling compositions when coupling on track "+this.compositiontrack.getLabel()+ " (function coupleComposition)");
			}
			if (a>b)
			{
				//System.out.println("a>b, addcomposition.getLocationOnTrack() = " +addcomposition.getLocationOnTrack()+" addcomposition.getLength() = "+addcomposition.getLength()+" addcomposition.getLocationOnTrack()+addcomposition.getLength()+1 = "+(addcomposition.getLocationOnTrack()+addcomposition.getLength()+1) + " this.locationontrack = "+this.locationontrack);
				if (addcomposition.getLocationOnTrack()+addcomposition.getLength() != this.locationontrack){
					throw new MisMatchException("Compositions aren't located exactly next to each other when coupling on track "+this.compositiontrack.getLabel()+ " (function coupleComposition)");
				}
				this.updateComposition(addcomposition.getLocationOnTrack());
			}
			else{
				//System.out.println("a<b, this.getLocationOnTrack() = " +this.getLocationOnTrack()+" this.getLength() = "+this.getLength()+" this.getLocationOnTrack()+this.getLength()+1 = "+(this.getLocationOnTrack()+this.getLength()+1) + " addcomposition.locationontrack = "+addcomposition.locationontrack);


				if (this.locationontrack+this.compositionlength != addcomposition.getLocationOnTrack()){
					throw new MisMatchException("Compositions aren't located exactly next to each other when coupling on track "+this.compositiontrack.getLabel()+ " (function coupleComposition)");
				}
				this.updateComposition();
			}
		}
		else{
			this.updateComposition();
		}
	}

	public Composition decoupleComposition(int locationdecouple) throws MisMatchException, IndexOutOfBoundsException, TrackNotFreeException, IOException{
		if (compositionsize < 2){
			throw new IOException("Composition consists of less than two trains, thus decoupling is impossible");
		}
		if (locationdecouple < 0 || locationdecouple > compositionsize-2){
			throw new IndexOutOfBoundsException("Composition of size "+compositionsize+" cannot be decoupled at index "+locationdecouple);
		}
		ArrayList<Train> newcompositionlist = new ArrayList<>();
		int a = this.getSize();
		for(int i=0;i<a-1-locationdecouple;i++){
			newcompositionlist.add(0, this.getTrain(a-1-i));
			this.compositiontrains.remove(a-1-i);
		}

		this.updateComposition(); 

		Composition newcomposition;
		if (locationontrack == -1){ //if locationontrack == -1, composition hasn't been placed on a track yet
			newcomposition = new Composition(newcompositionlist); 
		}
		else{ //if locationontrack != -1, composition has been placed on a track yet
			newcomposition = new Composition(newcompositionlist,compositiontrack,locationontrack+this.compositionlength); 
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

	public ArrayList<Train> getTrainList(){
		return compositiontrains;
	}
	private void updateComposition() throws IOException{
		if(compositiontrains.size() <=0){
			throw new IOException("Input composition contains no trains in function updateComposition()");
		}
		int templength = 0;
		for(int i=0;i<this.getTrainList().size();i++){
			templength += this.compositiontrains.get(i).getLength();
			this.getTrain(i).setPosition(i);
			this.getTrain(i).setComposition(this);
		}
		this.compositionsize = compositiontrains.size();
		this.compositionlength = templength;
	}

	private void updateComposition(int locationontrack) throws IOException{
		if(compositiontrains.size() <=0){
			throw new IOException("Input composition contains no trains in function updateComposition(int locationontrack)");
		}
		int templength = 0;
		for(int i=0;i<this.getTrainList().size();i++){
			templength += this.compositiontrains.get(i).getLength();
			this.getTrain(i).setPosition(i);
			this.getTrain(i).setComposition(this);
		}
		this.locationontrack = locationontrack;

		this.compositionsize = compositiontrains.size();
		this.compositionlength = templength;
	}

	public Track getTrack(){
		return compositiontrack;
	}
	public int getLocationOnTrack(){
		return locationontrack;
	}

	//int aorb indicates whether we want to add the composition to the a (left) side or the b (right) side of the track
	public void moveComposition(Track track, int location, String aorb) throws TrackNotFreeException, IndexOutOfBoundsException, IOException, MisMatchException{ 
		//First check whether there is room on the new track
		for (int i = location; i<location+compositionlength;i++){
			//System.out.println(track.getOccupied(i)+" i: "+i);
			if (track.getOccupied(i) != 0){
				throw new TrackNotFreeException("Track "+track.getLabel()+" has no room for a composition of length "+compositionlength+" at location "+location+" (function moveComposition)");
			}
		}

		//addcomposition to compositionlist of new track, throw exceptions if infeasible direction
		if (aorb == "a"){
			//check whether the track is free to move out left, and move in left
			int x = -1;
			boolean check = true;
			for (int i = 0; i<location;i++){
				if (track.getOccupied(i)==1){
					x = i;
					check = false;
					break;
				}
			}
			if (this.getPositionOnTrack()!=0 || check == false){
				throw new TrackNotFreeException("Track "+track.getLabel()+" cannot enter on the left side for a composition of length "+compositionlength+" at location "+location+", blockage occurs at "+x+" (function moveComposition)");
			}

			track.addCompositiontoTrackLeft(this);
		}
		else if (aorb == "b") {
			//check whether the track is free to move out right, and move in right
			int x = -1;
			boolean check = true;
			for (int i=track.getTracklength()-1;i > location+compositionlength;i--){
				if (track.getOccupied(i)==1){
					x = i;
					check = false;
					break;
				}
			}
			if (this.getPositionOnTrack()!=this.compositiontrack.getCompositionlist().size()-1 || check == false){
				throw new TrackNotFreeException("Track "+track.getLabel()+" cannot enter on the right side for a composition of length "+compositionlength+" at location "+location+", blockage occurs at "+x+" (function moveComposition)");
			}
			track.addCompositiontoTrackRight(this);
		}
		else{
			throw new IOException("Input in function moveComposition must be a or b, and is "+aorb);
		}

		compositiontrack.setFree(this.locationontrack,this.locationontrack+compositionlength-1); //set current track free on correct indices

		compositiontrack.removeComposition(this.getPositionOnTrack());

		compositiontrack = track; //update compositiontrack
		locationontrack = location; //update locationontrack

		compositiontrack.setOccupied(locationontrack,locationontrack+compositionlength-1); //set new track occupied on correct indices
	}

	//String aorb indicates whether we want to add the composition to the a (left) side or the b (right) side of the track
	//int allow indicates whether we allow (1) or not (0) to move other compositions on the destination track to the left or to the right to make room for the composition we want to move

	public void moveComposition(Track track, int location, String aorb, int allow) throws TrackNotFreeException, IndexOutOfBoundsException, IOException, MisMatchException{ 
		if (allow == 0){
			this.moveComposition(track, location, aorb); //if not allowed, call default not allowed function
		}
		else if (allow == 1){
			if (aorb == "a"){ //move in from the left
				if (this.getPositionOnTrack()!=0){
					throw new TrackNotFreeException("The composition is not the first on track "+this.getTrack().getLabel()+ " and therefore cannot leave from the left side");
				}
				boolean check = true;
				for (int i = 0; i<location;i++){
					if (track.getOccupied(i)==1){
						check = false;
						break;
					}
				}
				if (check == true){
					this.moveComposition(track, location, aorb);
				}
				else{
					//check whether there is enough room on the track to the right of the desired location
					if (track.getTracklength() - location >= compositionlength+track.getCompositionLengthOnTrack()){
						throw new TrackNotFreeException("Track "+track.getLabel()+" does not fit a composition of length "+compositionlength+" at location "+location+" when entering left and possibly moving the other compositions to the right");
					}
				}
				
				//TODO check whether there are compositions that or not in the way that need to be moved
				//TODO If yes, move others
				//TODO move composition to new position
			}
			else if (aorb == "b"){ //move in from the right
				if (this.getPositionOnTrack()!=this.compositiontrack.getCompositionlist().size()-1){
					throw new TrackNotFreeException("The composition is not the last on track "+this.getTrack().getLabel()+ " and therefore cannot leave from the right side");
				}
				boolean check = true;
				for (int i=track.getTracklength()-1;i > location+compositionlength;i--){
					if (track.getOccupied(i)==1){
						check = false;
						break;
					}
				}
				if (check == true){
					this.moveComposition(track, location, aorb);		
				}
				
				//check whether there is enough room on the track to the left of the desired location
				if (location + compositionlength-1 >= compositionlength+track.getCompositionLengthOnTrack()){
					throw new TrackNotFreeException("Track "+track.getLabel()+" does not fit a composition of length "+compositionlength+" at location "+location+" when entering right and possibly moving the other compositions to the left");
				}
			}
			else{
				throw new IOException("Input in function moveComposition must be a or b, and is "+aorb);
			}
		}
	}



	public int getPositionOnTrack() throws MisMatchException{
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
	
	/**
	 * This function assigns a track in case a composition
	 * 
	 * @param initialtrack
	 */
	public void setTrack(Track initialtrack){
		
	}
	
	public void setLocationonTrack(){
	}
	
	}

}