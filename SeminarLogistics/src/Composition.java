import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */

@SuppressWarnings("serial")
public class Composition implements Serializable{

	private ArrayList<Train> compositiontrains;
	private Track compositiontrack;
	private int locationontrack;
	private double arrivaltime;
	private double departuretime;
	private Activity[] busytime;

	public Composition(ArrayList<Train> compositiontrains) throws IOException{
		this.compositiontrains = compositiontrains;
		this.updateComposition();
		compositiontrack = null; //default
		locationontrack = -1; //default
		arrivaltime = -1; //default
		departuretime = -1; //default
		busytime = new Activity[60*24];
	}

	public Composition(ArrayList<Train> compositiontrains, double arrivaltime, double departuretime) throws IOException{
		this.compositiontrains = compositiontrains;
		this.updateComposition();
		compositiontrack = null; //default
		locationontrack = -1; //default
		this.arrivaltime = arrivaltime; //default
		this.departuretime = departuretime;
		busytime = new Activity[60*24];
	}

	public Composition(ArrayList<Train> compositiontrains, Track compositiontrack, int locationontrack) throws IndexOutOfBoundsException, TrackNotFreeException, IOException{
		this.compositiontrains = compositiontrains;
		busytime = new Activity[60*24];

		//add composition to compositiontrack
		this.compositiontrack = compositiontrack;
		this.locationontrack = locationontrack;
		for (int i = locationontrack; i < locationontrack+this.getLength(); i++){
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
		compositiontrack.setOccupied(locationontrack,locationontrack+this.getLength()-1);
		//je hoeft dus niet meer zelf toe te voegen aan je track als je een compositie op een track maakt!! :)
	}

	//Splitting a composition of size 2, returns 2 compositions of size 1 train

	public void coupleComposition(Composition addcomposition) throws MisMatchException, IndexOutOfBoundsException, IOException{
		if (arrivaltime != addcomposition.getArrivaltime() || departuretime != addcomposition.getDeparturetime()){
			throw new MisMatchException("Arrivaltime or departuretime are not the same when coupling two compositions");
		}


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


				if (this.locationontrack+this.getLength() != addcomposition.getLocationOnTrack()){
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
		if (this.getSize() < 2){
			throw new IOException("Composition consists of less than two trains, thus decoupling is impossible");
		}
		if (locationdecouple < 0 || locationdecouple > this.getSize()-2){
			throw new IndexOutOfBoundsException("Composition of size "+this.getSize()+" cannot be decoupled at index "+locationdecouple);
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
			newcomposition = new Composition(newcompositionlist,compositiontrack,locationontrack+this.getLength()); 
		}

		newcomposition.setArrivaltime(arrivaltime);
		newcomposition.setDeparturetime(departuretime);
		return newcomposition;
	}

	public int getLength(){
		int compositionlength = 0;
		for (int i = 0; i<compositiontrains.size(); i++){
			compositionlength += compositiontrains.get(i).getLength();
		}
		return compositionlength;
	}
	public int getSize(){
		return compositiontrains.size();
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
		for(int i=0;i<this.getTrainList().size();i++){
			this.getTrain(i).setPosition(i);
			this.getTrain(i).setComposition(this);
		}
	}

	private void updateComposition(int locationontrack) throws IOException{
		if(compositiontrains.size() <=0){
			throw new IOException("Input composition contains no trains in function updateComposition(int locationontrack)");
		}
		for(int i=0;i<this.getTrainList().size();i++){
			this.getTrain(i).setPosition(i);
			this.getTrain(i).setComposition(this);
		}
		this.locationontrack = locationontrack;
	}

	public Track getTrack(){
		return compositiontrack;
	}
	public int getLocationOnTrack(){
		return locationontrack;
	}

	//int aorb indicates whether we want to add the composition to the a (left) side or the b (right) side of the track
	public void moveComposition(Track track, int location, String aorb) throws TrackNotFreeException, IndexOutOfBoundsException, IOException, MisMatchException{ 
		//if we move on the track
		if (track == compositiontrack){
			if (location < locationontrack){
				//move left (to a side)
				for (int i = location; i < locationontrack;i++){
					if (track.getOccupied(i) != 0){
						throw new TrackNotFreeException("On track "+track.getLabel()+" we cannot move from location "+locationontrack+" to location "+location);
					}
				}
				compositiontrack.setFree(locationontrack,locationontrack+this.getLength()-1); //set current track free on correct indices

				locationontrack = location; //update locationontrack

				compositiontrack.setOccupied(locationontrack,locationontrack+this.getLength()-1); //set new track occupied on correct indices
			}
			else if (location > locationontrack){
				//move right (to b side)
				for (int i = locationontrack+this.getLength(); i < location+this.getLength()-1;i++){
					if (track.getOccupied(i) != 0){
						throw new TrackNotFreeException("On track "+track.getLabel()+" we cannot move from location "+locationontrack+" to location "+location);
					}
				}
				compositiontrack.setFree(locationontrack,locationontrack+this.getLength()-1); //set current track free on correct indices

				locationontrack = location; //update locationontrack

				compositiontrack.setOccupied(locationontrack,locationontrack+this.getLength()-1); //set new track occupied on correct indices

			}
			else
			{
				throw new IOException("We move to the same location on track "+compositiontrack.getLabel()+", this is not possible");
			}

		}
		else{ //if we move to another track
			//First check whether there is room on the new track
			for (int i = location; i<location+this.getLength();i++){
				//System.out.println(track.getOccupied(i)+" i: "+i);
				if (track.getOccupied(i) != 0){
					throw new TrackNotFreeException("Track "+track.getLabel()+" has no room for a composition of length "+this.getLength()+" at location "+location+" (function moveComposition)");
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
					throw new TrackNotFreeException("Track "+track.getLabel()+" cannot enter on the left side for a composition of length "+this.getLength()+" at location "+location+", blockage occurs at "+x+" (function moveComposition)");
				}

				track.addCompositiontoTrackLeft(this);
			}
			else if (aorb == "b") {
				//check whether the track is free to move out right, and move in right
				int x = -1;
				boolean check = true;
				for (int i=track.getTracklength()-1;i > location+this.getLength();i--){
					if (track.getOccupied(i)==1){
						x = i;
						check = false;
						break;
					}
				}
				if (this.getPositionOnTrack()!=this.compositiontrack.getCompositionlist().size()-1 || check == false){
					throw new TrackNotFreeException("Track "+track.getLabel()+" cannot enter on the right side for a composition of length "+this.getLength()+" at location "+location+", blockage occurs at "+x+" (function moveComposition)");
				}
				track.addCompositiontoTrackRight(this);
			}
			else{
				throw new IOException("Input in function moveComposition must be a or b, and is "+aorb);
			}

			compositiontrack.setFree(this.locationontrack,this.locationontrack+this.getLength()-1); //set current track free on correct indices

			compositiontrack.removeComposition(this.getPositionOnTrack());

			compositiontrack = track; //update compositiontrack
			locationontrack = location; //update locationontrack

			compositiontrack.setOccupied(locationontrack,locationontrack+this.getLength()-1); //set new track occupied on correct indices
		}

	}

	//String aorb indicates whether we want to add the composition to the a (left) side or the b (right) side of the track
	//int allow indicates whether we allow (1) or not (0) to move other compositions on the destination track to the left or to the right to make room for the composition we want to move

	public void moveComposition(Track track, int location, String aorb, int allow) throws TrackNotFreeException, IndexOutOfBoundsException, IOException, MisMatchException{ 
		if (allow == 0 || track == compositiontrack){
			this.moveComposition(track, location, aorb); //if not allowed, call default not allowed function
		}
		else if (allow == 1){
			if (aorb == "a"){ //move in from the left
				//check if it can leave
				if (this.getPositionOnTrack()!=0){
					throw new TrackNotFreeException("The composition is not the first on track "+this.getTrack().getLabel()+ " and therefore cannot leave from the left side");
				}

				//check if it can enter freely
				boolean check = true;
				for (int i = 0; i<location+this.getLength();i++){
					if (track.getOccupied(i)==1){
						check = false;
						break;
					}
				}
				if (check == true){
					this.moveComposition(track, location, aorb);
				}
				//check if it can enter by first moving some other compositions
				else{
					//check whether there is enough room on the track to the right of the desired location
					if (track.getTracklength() - location +1< this.getLength()+track.getCompositionLengthOnTrack()){
						throw new TrackNotFreeException("Track "+track.getLabel()+" does not fit a composition of length "+this.getLength()+" at location "+location+" when entering left and possibly moving the other compositions to the right");
					}
				}
				//check whether we need to move compositions that are complete to the right of the desired location span\
				boolean movepartial = false; //default
				ArrayList<Composition> partiallistexclb = track.getPartialCompositionListExcl(location, "b", this);
				ArrayList<Composition> partiallistincla = track.getPartialCompositionListIncl(location, "a", this);
				int lengthincla = 0;
				for (int i = 0; i<partiallistincla.size(); i++){
					lengthincla += partiallistincla.get(i).getLength();
				}
				if (partiallistexclb.size()==0){
					movepartial = false; //partiallistexclb is empty, so we will never have to move it
				}
				else{
					int distance;
					distance = partiallistexclb.get(0).getLocationOnTrack()-(location+this.getLength());
					if (distance >= lengthincla){
						movepartial = false; //partiallistexcl is not in the way, so does not have to be moved
					}
					else {
						movepartial = true; //exclb moet wel gemoved
					}
				}
				//move compositions that are in the way
				if (movepartial == true){
					//move exclb
					int minstartposexclb = (location+this.getLength()-1)+(lengthincla+1);
					int[] locationlist = new int[partiallistexclb.size()];
					//find moves for exclb:
					locationlist[0]=minstartposexclb;
					for (int i = 1; i<partiallistexclb.size();i++){
						locationlist[i] = locationlist[i-1]+partiallistexclb.get(i-1).getLength();
					}
					//actually move exclb
					for (int i = partiallistexclb.size()-1; i>=0; i--){
						if (locationlist[i]>partiallistexclb.get(i).getLocationOnTrack()){
							//only move if necessary
							partiallistexclb.get(i).moveComposition(track, locationlist[i], "a"); //aorb is arbitrary here
						}
					}
				}

				//move incla
				int minstartposincla = (location+this.getLength());
				int[] locationlist2 = new int[partiallistincla.size()];
				//find moves for incla:
				locationlist2[0]=minstartposincla;
				for (int i = 1; i<partiallistincla.size();i++){
					locationlist2[i] = locationlist2[i-1]+partiallistincla.get(i-1).getLength();
				}
				//actually move incla
				for (int i = partiallistincla.size()-1; i>=0; i--){
					partiallistincla.get(i).moveComposition(track, locationlist2[i], "a"); //aorb is arbitrary here
				}

				//finally move current composition
				this.moveComposition(track, location, aorb);
			}

			else if (aorb == "b"){ //move in from the right
				if (this.getPositionOnTrack()!=compositiontrack.getCompositionlist().size()-1){
					throw new TrackNotFreeException("The composition is not the last on track "+this.getTrack().getLabel()+ " and therefore cannot leave from the right side");
				}
				boolean check = true;
				for (int i=track.getTracklength()-1;i >= location;i--){
					if (track.getOccupied(i)==1){
						check = false;
						break;
					}
				}
				if (check == true){
					this.moveComposition(track, location, aorb);		
				}

				//check whether there is enough room on the track to the left of the desired location
				if (location +this.getLength() < this.getLength()+track.getCompositionLengthOnTrack()){
					throw new TrackNotFreeException("Track "+track.getLabel()+" does not fit a composition of length "+this.getLength()+" at location "+location+" when entering right and possibly moving the other compositions to the left");
				}
				//check whether we need to move compositions that are complete to the left of the desired location span
				boolean movepartial = false; //default
				ArrayList<Composition> partiallistexcla = track.getPartialCompositionListExcl(location, "a", this);
				ArrayList<Composition> partiallistinclb = track.getPartialCompositionListIncl(location, "b", this);
				int lengthinclb = 0;
				for (int i = 0; i<partiallistinclb.size(); i++){
					lengthinclb += partiallistinclb.get(i).getLength();
				}
				if (partiallistexcla.size()==0){
					movepartial = false; //partiallistexcla is empty, so we will never have to move it
				}
				else{
					int distance;
					if (partiallistexcla.size()>0){
						distance = location - (partiallistexcla.get(partiallistexcla.size()-1).getLocationOnTrack() + partiallistexcla.get(partiallistexcla.size()-1).getLength());
						if (distance >= lengthinclb){
							movepartial = false; //excla hoeft niet gemoved
						}
						else {
							movepartial = true; //excla moet wel gemoved
						}
					}
				}
				//move compositions that are in the way
				if (movepartial == true){
					//move excla
					int maxstartposexcla = location-track.getCompositionLengthOnTrack();
					int[] locationlist = new int[partiallistexcla.size()];
					//find moves for excla:
					locationlist[0]=maxstartposexcla;
					for (int i = 1; i<partiallistexcla.size();i++){
						locationlist[i] = locationlist[i-1]+partiallistexcla.get(i-1).getLength();
					}
					//actually move excla
					for (int i = 0; i<partiallistexcla.size(); i++){
						if (locationlist[i]<partiallistexcla.get(i).getLocationOnTrack()){
							//only move if necessary
							partiallistexcla.get(i).moveComposition(track, locationlist[i], "b"); //aorb is arbitrary here
						}
					}
				}
				//move inclb
				int maxstartposinclb = (location-lengthinclb);
				int[] locationlist2 = new int[partiallistinclb.size()];
				//find moves for inclb:
				locationlist2[0]=maxstartposinclb;
				for (int i = 1; i<partiallistinclb.size();i++){
					locationlist2[i] = locationlist2[i-1]+partiallistinclb.get(i-1).getLength();
				}
				//actually move inclb
				for (int i = 0; i<partiallistinclb.size(); i++){
					partiallistinclb.get(i).moveComposition(track, locationlist2[i], "b"); //aorb is arbitrary here
				}
				//finally move current composition
				this.moveComposition(track, location, aorb);
			}
			else {
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
	 * This function assigns a track and a location on track to a composition in case it wasn't initialized a track.
	 * This location can only be right at the beginning of an arriving track.
	 * 
	 * @param initialtrack: the track we want to assign the composition to
	 * @param initiallocation: the location on the track we want to assign the composition to
	 * 
	 * @throws IOException 
	 * @throws TrackNotFreeException 
	 */
	public void setTrackandLocation(Track initialtrack, int initiallocation) throws IOException, TrackNotFreeException{
		if (compositiontrack != null || locationontrack != -1){
			throw new IOException("Track "+initialtrack.getLabel()+"cannot be assigned to the composition, as the composition is already assigned to a track");
		}
		for (int i = initiallocation; i<initiallocation+this.getLength();i++){
			if (initialtrack.getOccupied(i)==1){
				throw new TrackNotFreeException("The composition cannot be assigned to track "+initialtrack.getLabel()+" on location " + initiallocation +" as it is occupied");
			}
		}

		compositiontrack = initialtrack;
		locationontrack = initiallocation;
	}

	/**
	 * This function returns the arrivaltime of a composition/block
	 * 
	 * @return arrivaltime
	 */
	public double getArrivaltime(){
		return arrivaltime;
	}

	/**
	 * This function sets the arrivaltime of a composition/block
	 * 
	 * @param arrivaltime
	 */
	public void setArrivaltime(double arrivaltime){
		this.arrivaltime = arrivaltime;
	}

	/**
	 * This function returns the departuretime of a composition/block
	 * 
	 * @return departuretime
	 */
	public double getDeparturetime(){
		return departuretime;
	}
	public int getDepartureTimeInteger(){
		return (int) departuretime*60*24;
	}

	/**
	 * This function sets the departuretime of a composition/block
	 * 
	 * @param departuretime
	 */
	public void setDeparturetime(double departuretime){
		this.departuretime = departuretime;
	}

	/**
	 * This function returns the total time of the activities that still need to be performed on the composition/block
	 * 
	 * @return totalservicetime : total time of activities that still need to be performed on the composition/block
	 * @throws IOException
	 */
	public double getTotalServiceTime() throws IOException{ //TODO testfunctie!!
		double totalservicetime = 0;
		for (int i = 0; i<this.getSize(); i++){
			Train currenttrain = compositiontrains.get(i);
			for (int j = 0; j<=3; j++){
				if (currenttrain.getActivity(j)==true){
					totalservicetime += currenttrain.getActivityTime(j);
				}
			}
		}
		return totalservicetime;
	}

	public void setBusyTime(Activity activity){
		for(int i = activity.getPlannedTimeInteger(); i<activity.getPlannedTimeInteger()+activity.getTotalDurationInteger(); i++){
			busytime[i] = activity;
		}
	}
	

	public void removeBusyTime(Activity activity){
		for(int i = 0; i<busytime.length; i++){
			if(busytime[i] != null && busytime[i].equals(activity)){
				busytime[i] = null; //TODO: TEST WHETHER THIS IS ALLOWED, ONLY REMOVE REFERENCE TO OBJECT, NOT OBJECT SELF
			}
		}
	}
	public boolean checkFeasibility(Activity activity, int timetobechecked){

		boolean feasible = true;

		for(int i = timetobechecked; i<timetobechecked+activity.getDuration(); i++){
			if(busytime[i]!=null){
				feasible = false;
				break;
			}
		}
		return feasible;
	}
	public void printTimeLine(){
		for(int i = 0; i<60*24; i++){
			if(busytime[i]!=null){
				System.out.print(busytime[i].getActivity());
			}
			else{
				System.out.print(7);
			}
		}
	}
}