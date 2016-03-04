import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
public class Track implements Serializable {
	private final String label;
	private final int tracklength;
	private final int parktrain;
	private final int inspectionposition;
	private final int cleaningposition;
	private final int repairingposition; //TODO: add different types of repairing positions
	private final int washingposition;
	private int tracktype;
	private Activity[] busytime;
	private final int maxdrivebacklength;

	private int[] occupation;
	private ArrayList<Composition> compositionlist;
	private ArrayList<Event> eventlist;

	public Track(String label, int tracklength, int parktrain, int inspectionposition,  int cleaningposition, int repairingposition, int washingposition, int maxdrivebacklength ) {
		this.label = label;
		this.tracklength = tracklength;
		this.parktrain = parktrain;
		this.inspectionposition = inspectionposition;
		this.cleaningposition = cleaningposition;
		this.repairingposition = repairingposition;
		this.washingposition = washingposition;
		this.maxdrivebacklength = maxdrivebacklength;
		tracktype = 0;
		occupation = new int[tracklength];
		compositionlist = new ArrayList<Composition>();
		eventlist = new ArrayList<Event>();
		busytime = new Activity[60*24];
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
		maxdrivebacklength = 10000; //default
		occupation = new int[tracklength];
		compositionlist = new ArrayList<Composition>();
		eventlist = new ArrayList<Event>();
		busytime = new Activity[60*24];
	}

	public int getMaxDriveBackLength(){
		return maxdrivebacklength;
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

	public int getOccupied(int position)throws IndexOutOfBoundsException{
		if (position < 0 || position >= tracklength)
		{
			throw new IndexOutOfBoundsException("Index "+ position+" on track "+label+" of length "+tracklength+" in function getOccupied is out of trackbound");
		}
		return occupation[position];
	}

	public void setOccupied(int position) throws IndexOutOfBoundsException{
		if (position < 0 || position >= tracklength)
		{
			throw new IndexOutOfBoundsException("Index "+ position+" on track "+label+" of length "+tracklength+" in function setOccupied is out of trackbound");
		}
		this.occupation[position] = 1;
	}

	public void setOccupied(int beginposition, int endposition) throws IndexOutOfBoundsException{
		for (int i=beginposition; i<=endposition; i++){
			this.setOccupied(i);
		}
	}

	public void setFree(int position) throws IndexOutOfBoundsException{
		if (position < 0 || position >= tracklength)
		{
			throw new IndexOutOfBoundsException("Index "+ position+" on track "+label+" of length "+tracklength+" in function setFree is out of trackbound");
		}
		this.occupation[position] = 0;
	}

	public void setFree(int beginposition, int endposition) throws IndexOutOfBoundsException{
		for (int i=beginposition; i<=endposition; i++){
			this.setFree(i);
		}
	}

	public ArrayList<Composition> getCompositionlist(){
		return compositionlist;
	}
	
	public ArrayList<Event> getEventlist(){
		return eventlist;
	}

	public void addCompositiontoTrackLeft(Composition composition) throws TrackNotFreeException{ //LEFT: links op de map, index 0 
		int compositionslength = this.getCompositionLengthOnTrack();
		if (compositionslength+composition.getLength()>tracklength){
			throw new TrackNotFreeException("Track "+label+" has no room for a composition of length "+composition.getLength()+" (function addCompositionToTrackLeft)");
		}

		this.compositionlist.add(0,composition);
	}

	public void addCompositiontoTrackRight(Composition composition) throws TrackNotFreeException{ //RIGHT: rechts op de map, index max lengte arraylist
		int compositionslength = this.getCompositionLengthOnTrack();
		if (compositionslength+composition.getLength()>tracklength){
			throw new TrackNotFreeException("Track "+label+" has no room for a composition of length "+composition.getLength()+" (function addCompositionToTrackRight)");
		}
		this.compositionlist.add(composition);
	}

	public void addEventtoTrackLeft(Event event) throws TrackNotFreeException{ //TODO: test
		int compositionslength = this.getCompositionLengthOnTrack();
		if (compositionslength+event.getEventblock().getLength()>tracklength){
//			System.out.println("Compositionlength: "+compositionslength);
//			for (int i = 0; i<compositionlist.size(); i++){
//				System.out.println("a");
//				System.out.println(compositionlist.get(i));
//			}
//			for (int i = 0; i<eventlist.size(); i++){
//				System.out.println("b");
//				System.out.println(eventlist.get(i));
//			}
//			System.out.println("event.getEventblock().getLength()"+event.getEventblock().getLength());
//			System.out.println("tracklength: "+tracklength);
//			System.out.println(event);
//			System.out.println(event.getType());
//			System.out.println(event.getFinalType());
			throw new TrackNotFreeException("Track "+label+" has no room for an eventblock of length "+event.getEventblock().getLength()+" (function addEventtoTrackLeft)");
		}
		this.eventlist.add(0,event);
	}
	
	public void addEventtoTrackRight(Event event) throws TrackNotFreeException{ //TODO: test
		int compositionslength = this.getCompositionLengthOnTrack();
		if (compositionslength+event.getEventblock().getLength()>tracklength){
			throw new TrackNotFreeException("Track "+label+" has no room for an eventblock of length "+event.getEventblock().getLength()+" (function addEventtoTrackRight)");
		}
		this.eventlist.add(event);
	}
	
//	public void addEventtoTrack(Event event) throws TrackNotFreeException{ //RIGHT: rechts op de map, index max lengte arraylist
//		int compositionslength = this.getCompositionLengthOnTrack();
//		if (compositionslength+event.getEventblock().getLength()>tracklength){
//			throw new TrackNotFreeException("Track "+label+" has no room for an eventblock of length "+event.getEventblock().getLength()+" (function addEventtoTrackRight)");
//		}
//		this.eventlist.add(event);
//	}
	
	public void removeEventfromTrack(Event event) throws TrackNotFreeException, IOException{ //TODO: test
		boolean removed = this.eventlist.remove(event);
		if (removed == false){
			throw new IOException("Event "+event+" cannot be removed from track +"+this.getLabel()+" as it was never present on this track ("+label+")");
		}
	}
	
	public void removeCompositionfromTrack(Composition composition) throws TrackNotFreeException, IOException{ //TODO: test
		boolean removed = this.compositionlist.remove(composition);
		if (removed == false){
			throw new IOException("Composition cannot be removed from track as it was never present on this track ("+label+")");
		}
	}

	public void addCompositiontoTrack(Composition composition, int positionontrack) throws IndexOutOfBoundsException {
		if (positionontrack < 0 || positionontrack > compositionlist.size())
		{
			throw new IndexOutOfBoundsException("Index "+ positionontrack+" on track "+label+" in function addComposition is out of trackbound");
		}

		this.compositionlist.add(positionontrack,composition);
	}

	public void removeComposition(int position)throws IndexOutOfBoundsException{
		if (position < 0 || position > compositionlist.size()-1)
		{
			throw new IndexOutOfBoundsException("Index "+ position+" on track "+label+" in function removeComposition is out of trackbound");
		}
		compositionlist.remove(position);
	}

	/**This function returns the total length of the compositions on the track
	 * 
	 * @return Total length of compositions on track
	 */
	public int getCompositionLengthOnTrack(){ 
		int length = 0;
		for (int i = 0; i<compositionlist.size(); i++){
			length += compositionlist.get(i).getLength();
		}
		return length;
	}

	/**
	 * This function a arraylist of compositions located completely to the left (a) or the right (b) of the entire span of the location for an enteringcomposition location.
	 * Only compositions located completely to the left or right of a location are included
	 * 
	 * @param location, location on track we want to insert the enteringcomposition on
	 * @param aorb, a if we want compositions to the left, b if we want compositions to the right of location
	 * @param enteringcomposition, composition we want to insert
	 * 
	 * @return Total length of compositions on track
	 * 
	 * @throws IOException if input for aorb parameter is not a or b
	 */
	public ArrayList<Composition> getPartialCompositionListExcl (int location, String aorb, Composition enteringcomposition) throws IOException{
		ArrayList<Composition> partialcompositionlist = new ArrayList<Composition>();
		if (aorb == "a"){
			boolean stop = false;
			int i = 0;
			while (stop==false){
				Composition currentcomposition = compositionlist.get(i);
				if (currentcomposition.getLocationOnTrack()+currentcomposition.getLength()-1<location){
					partialcompositionlist.add(currentcomposition);
				}
				else{
					stop = true;
				}
				i++;
			}
		}
		else if (aorb == "b"){
			boolean stop = false;
			int i = compositionlist.size()-1;
			while (stop==false){
				Composition currentcomposition = compositionlist.get(i);
				if (currentcomposition.getLocationOnTrack()>location+enteringcomposition.getLength()-1){
					partialcompositionlist.add(0,currentcomposition);
				}
				else{
					stop = true;
				}
				i--;
			}
		}
		else{
			throw new IOException("Input in function moveComposition must be a or b, and is "+aorb);
		}

		return partialcompositionlist;
	}

	/**
	 * This function a arraylist of compositions located completely or partly to the left (a) or the right (b) of the entire span of the location for an enteringcomposition location.
	 * Also compositions located partly to the left or right of a location span are included
	 * 
	 * @param location, location on track we want to insert the enteringcomposition on
	 * @param aorb, a if we want compositions to the left, b if we want compositions to the right of location
	 * @param enteringcomposition, composition we want to insert
	 * 
	 * @return Total length of compositions on track
	 * 
	 * @throws IOException if input for aorb parameter is not a or b
	 */
	public ArrayList<Composition> getPartialCompositionListIncl (int location, String aorb, Composition enteringcomposition) throws IOException{
		ArrayList<Composition> partialcompositionlist = new ArrayList<Composition>();
		if (aorb == "a"){
			boolean stop = false;
			int i = 0;
			while (stop==false){
				Composition currentcomposition = compositionlist.get(i);
				if (currentcomposition.getLocationOnTrack()<=location+enteringcomposition.getLength()-1){
					partialcompositionlist.add(currentcomposition);
				}
				else{
					stop = true;
				}
				i++;
				if (i >= compositionlist.size()){
					break;
				}
			}
		}
		else if (aorb == "b"){
			boolean stop = false;
			int i = compositionlist.size()-1;
			while (stop==false){
				Composition currentcomposition = compositionlist.get(i);
				if (currentcomposition.getLocationOnTrack()+currentcomposition.getLength()-1>=location) {
					partialcompositionlist.add(0,currentcomposition);
				}
				else{
					stop = true;
				}
				i--;
				if (i < 0){
					break;
				}
			}
		}
		else{
			throw new IOException("Input in function moveComposition must be a or b, and is "+aorb);
		}

		return partialcompositionlist;
	} 

	public void setBusyTime(Activity activity){
		for(int i = activity.getPlannedTimeInteger(); i<activity.getPlannedTimeInteger()+activity.getTotalDurationInteger(); i++){
			busytime[i] = activity;
		}
	}

	public void removeBusyTime(Activity activity){
		for(int i = 0; i<busytime.length; i++){
			if(busytime[i] != null && busytime[i].equals(activity)){
				busytime[i] = null;
			}
		}
	}
	public boolean getBusy(int abc){
		if(busytime[abc]==null){
			return false;
		}
		else{
			return true;
		}
	}
	
	public int[] getBusyArray(int a, int b){
		int[] busyarray = new int[b-a+1];
		for (int i = a; i<=b; i++){
			if (this.getBusy(i) == true){
				busyarray[i]=1;
			}
			else{
				busyarray[i]=0;
			}
		}
		return busyarray;
	}
	
	public int getTotalBusyTime(){
		int totalbusytime=0;
		int[] busyarray = this.getBusyArray(0, 60*24-1);
		for (int i = 0; i<busyarray.length; i++){
			if (busyarray[i]==1){
				totalbusytime++;
			}
		}
		return totalbusytime;
	}
	
	public void removeBusyTimeMove(Activity activity) throws IOException{
		for(int i = activity.getPlannedTimeInteger()+activity.getTotalDurationInteger()-Main.moveduration; i<activity.getPlannedTimeInteger()+activity.getTotalDurationInteger(); i++){
			//System.out.print(busytime[i]);
			if(busytime[i] != null){
				busytime[i] = null;
				//System.out.print(activity.getPlannedTimeInteger() +" " + activity.getDurationInteger() + " " + i +" \n" );

			}
			else{
				//System.out.print(activity.getPlannedTimeInteger() +" " + activity.getDurationInteger() + " " + i +" \n" );
				
				throw new IOException("Cannot remove busytime if it does not exist.");
			}
		}
	}
 
	public boolean checkFeasibility(Activity activity, int timetobechecked){

		boolean feasible = true;

		for(int i = timetobechecked; i<timetobechecked+activity.getTotalDurationInteger(); i++){
			if(busytime[i]!=null){
				feasible = false;
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
				System.out.print(" ");
			}		
		}
	}
	
	public Activity getActivity(int index){
		Activity act = busytime[index];
		return act;
	}
	
	public boolean getConsecutive(Activity activity1, Activity activity2){
		boolean abcd;

		if(activity1.getPlannedTime() < activity2.getPlannedTime()){
			abcd = true;
			for(int i = activity1.getPlannedTimeInteger(); i < activity2.getPlannedTimeInteger(); i++){
				if(busytime[i] != null && (busytime[i] != activity1 || busytime[i] != activity2)){
					abcd = false;
					break;
				}
			}
		}
		else{
			abcd = true;
			for(int i = activity2.getPlannedTimeInteger(); i < activity1.getPlannedTimeInteger(); i++){
				if(busytime[i] != null && (busytime[i] != activity1 || busytime[i] != activity2)){
					abcd = false;
					break;
				}
			}
		}
		return abcd;
	}

}