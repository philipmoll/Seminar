//PARKING 3 WITH COUPLE DECOUPLE IMPLEMENTATION--> reordered
//DOES NOT THROW ERROR WHEN NOT PARKED, SIMPLY RETURNS THE NUMBER NOT PARKED
//FINAL FORM
//USE EXTRA TRACKS AS PARKTRACK

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
public class Parking5 implements Serializable{ //TODO: test!
	private int CHOICE = 1; //MAXDRIVEBACKORDER
	private ArrayList<Track> parkingtracks;
	private ArrayList<Track> lifotracks;
	//private int[][] parkingpositions;
	//private ArrayList<Parking> previousparkings;
	private ArrayList<Event> timeline;
	//private ArrayList<int[]> freetracktimes;
	private int notparked;
	private Track[] tracks;

	public Parking5(ArrayList<Event> eventlist, Track[] tracks) throws MethodFailException, TrackNotFreeException, IOException{
		// input: eventlist met per compositie op welke tijd hij aankomt en weggaat en waarheen/waarvandaan, tracklist
		this.tracks = tracks;
		notparked = 0;
		//take parktracks out of tracks and order by maxbackwardlength
		parkingtracks = new ArrayList<Track>();
		sortTracks(tracks);

		lifotracks = new ArrayList<Track>();
		sortTracks2(tracks);

		//TODO: zetuit!!!
		ArrayList<Track> parkingtracks2 = new ArrayList<Track>();
		parkingtracks2 = (ArrayList<Track>) DeepCopy.copy(parkingtracks);
		//parkingtracks.add(parkingtracks2.get(0));
		//parkingtracks.add(parkingtracks2.get(1));
		//				parkingtracks.add(0,parkingtracks2.get(2))


		for (int i = 0; i<parkingtracks.size(); i++){
			System.out.println("track: "+parkingtracks.get(i).getLabel()+" maxbackwardlength: "+parkingtracks.get(i).getMaxDriveBackLength()+" length: "+parkingtracks.get(i).getTracklength());
		}
		//sort eventlist into timeline
		timeline = new ArrayList<Event>();
		sortEvents(eventlist);
		for (int i = 0; i<timeline.size(); i++){
			//System.out.println("i: "+i+" Event: "+timeline.get(i)+" Final block: "+timeline.get(i).getEventblock()+" finalevent: "+timeline.get(i).getFinalType()+" beginside: "+timeline.get(i).getSidestart()+" endside: "+timeline.get(i).getSideend()+" starttime this: "+timeline.get(i).getStarttime()+" starttime related: "+timeline.get(i).getRelatedEvent().getStarttime()+ " endtime this: "+timeline.get(i).getEndtime()+" endtime related: "+timeline.get(i).getRelatedEvent().getEndtime());
			System.out.println("i: "+i+" Event: "+timeline.get(i)+" Final block: "+timeline.get(i).getEventblock()+" finalevent: "+timeline.get(i).getFinalType()+" beginside: "+timeline.get(i).getSidestart()+" endside: "+timeline.get(i).getSideend()+" starttime this: "+timeline.get(i).getStarttime()+" starttime related: "+timeline.get(i).getRelatedEvent().getStarttime()+ " endtime this: "+timeline.get(i).getEndtime()+" endtime related: "+timeline.get(i).getRelatedEvent().getEndtime()+" time: "+timeline.get(i).getTime());
			//if final type == 1, reorder a little
			int nrevents = 0;
			if (timeline.get(i).getFinalType() == 1){
				if (timeline.get(i).getType() == 0){
					ArrayList<Event> finalevents = new ArrayList<>();
					finalevents.add(timeline.get(i));
					nrevents++;
					for (int j = 1; j<=2; j++){ //assumption: max 3 in one composition
						if (i+j<timeline.size()){
							if (timeline.get(i+j).getFinalType()==1 && timeline.get(i).getTime() == timeline.get(i+j).getTime() /*&& timeline.get(i).getEventblock().getOrigincomposition()==timeline.get(i+j).getEventblock().getOrigincomposition()*/){ //if they are the same final arrivingcomposition
								finalevents.add(timeline.get(i+j));
								nrevents++;
							}
							else{
								break;
							}
						}
					}
					ArrayList<Event> tempevents = reorderEvents(finalevents,nrevents,i,0);
					for (int j = 0; j<nrevents; j++){
						timeline.remove(i+j);
						timeline.add(i+j,tempevents.get(j));
					}
				}
				if (timeline.get(i).getType() == 1){
					ArrayList<Event> finalevents = new ArrayList<>();
					finalevents.add(timeline.get(i));
					nrevents++;
					for (int j = 1; j<=2; j++){ //assumption: max 3 in one composition
						if(i+j<timeline.size()){
							if (timeline.get(i+j).getFinalType()==1 && timeline.get(i).getTime() == timeline.get(i+j).getTime() /*&& timeline.get(i).getEventblock().getOrigincomposition()==timeline.get(i+j).getEventblock().getOrigincomposition()*/){ //if they are the same final arrivingcomposition
								finalevents.add(timeline.get(i+j));
								nrevents++;
							}
							else{
								break;
							}
						}
					}
					ArrayList<Event> tempevents = reorderEvents(finalevents,nrevents,i,0);
					for (int j = 0; j<nrevents; j++){
						timeline.remove(i+j);
						timeline.add(i+j,tempevents.get(j));
					}

				}

			}
			//i=i+nrevents;
		}
		System.out.println();
		//		System.out.println(timeline.get(2).getEventblock().getCutpositionarr1());
		//		System.out.println(timeline.get(3).getEventblock().getCutpositionarr1());

		for (int i = 0; i< timeline.size(); i++){
			System.gc();
			System.out.println("event "+i+ " "+timeline.get(i));
			boolean skip = false;
			if (i != timeline.size()-1){
				if (timeline.get(i+1)== timeline.get(i).getRelatedEvent()){
					System.out.println("SKIP EVENT "+i+" and "+(i+1));
					skip = true;
					i = i+1;
				}
			}
			if (!skip){
				if (timeline.get(i).getType()==1){ //if it is a departure
					departure(timeline.get(i), i);
					System.out.println("Departure from track "+timeline.get(i).getEventTrack().getLabel()+" at time "+timeline.get(i).getTime());
					for (int x = 0; x<timeline.get(i).getEventTrack().getEventlist().size(); x++){
						System.out.println(timeline.get(i).getEventTrack().getEventlist().get(x)+" dep side: "+timeline.get(i).getEventTrack().getEventlist().get(x).getDepartureSide()+" arr time: "+timeline.get(i).getEventTrack().getEventlist().get(x).getStarttime()+" dep time: "+timeline.get(i).getEventTrack().getEventlist().get(x).getEndtime());
					}
				}
				else if (timeline.get(i).getType()==0) { //if it is an arrival
					boolean parked = arrival(timeline.get(i), i);
					if (parked){
						for (int x = 0; x<timeline.get(i).getEventTrack().getEventlist().size(); x++){
							System.out.println(timeline.get(i).getEventTrack().getEventlist().get(x)+" dep side: "+timeline.get(i).getEventTrack().getEventlist().get(x).getDepartureSide()+" dep time: "+timeline.get(i).getEventTrack().getEventlist().get(x).getEndtime());
						}
						System.out.println("Arrival at track "+timeline.get(i).getEventTrack().getLabel()+" at time "+timeline.get(i).getTime());
					}
					else {
						System.out.println("WARNING: Event "+i+", "+timeline.get(i)+" cannot be parked");
						notparked++;
						//timeline.remove(timeline.get(i).getRelatedEvent());
					}
				}
				else{
					throw new IOException("Type of event "+i+" should be 0 (arrival) or 1 (departure), but is "+i);
				}
			}

		}
	}

	private ArrayList<Event> reorderEvents(ArrayList<Event> arrivalevents1, int nrevents, int i, int dep) throws MethodFailException{ //TODO: heel erg aanpassen
		ArrayList<Event> arrivalevents = new ArrayList<>();
		//if A side arrival, sort from end to beginning
		if (arrivalevents1.get(0).getSidestart()==0){
			if (nrevents == 1){
				arrivalevents.add(arrivalevents1.get(0));
			}
			else if (nrevents == 2){
				if (arrivalevents1.get(1).getSidestart()!= 0){
					throw new MethodFailException("Sidestart is not 0, but "+ arrivalevents1.get(1).getSidestart()+" and should be, since it arrives in a composition that arrives at side 0");
				}
				if (arrivalevents1.get(0).getEventblock().getCutpositionarr1()<arrivalevents1.get(1).getEventblock().getCutpositionarr1()){
					arrivalevents.add(arrivalevents1.get(1));
					arrivalevents.add(arrivalevents1.get(0));
				}
				else{
					arrivalevents.add(arrivalevents1.get(0));
					arrivalevents.add(arrivalevents1.get(1));
				}
			}
			else if (nrevents == 3){
				if (arrivalevents1.get(1).getSidestart()!=0 || arrivalevents1.get(2).getSidestart()!=0){
					throw new MethodFailException("Sidestart is not 0, but "+ arrivalevents1.get(1).getSidestart()+" and "+ arrivalevents1.get(2).getSidestart()+" and should be, since it arrives in a composition that arrives at side 0");
				}
				if (arrivalevents1.get(0).getEventblock().getCutpositionarr1()<arrivalevents1.get(1).getEventblock().getCutpositionarr1()){
					arrivalevents.add(arrivalevents1.get(1));
					arrivalevents.add(arrivalevents1.get(0));
				}
				else{
					arrivalevents.add(arrivalevents1.get(0));
					arrivalevents.add(arrivalevents1.get(1));
				}
				if (arrivalevents1.get(2).getEventblock().getCutpositionarr1()>arrivalevents.get(0).getEventblock().getCutpositionarr1()){
					arrivalevents.add(0,arrivalevents1.get(2));
				}
				else if (arrivalevents1.get(2).getEventblock().getCutpositionarr1()<arrivalevents.get(1).getEventblock().getCutpositionarr1()){
					arrivalevents.add(arrivalevents1.get(2));
				}
				else {
					arrivalevents.add(1,arrivalevents1.get(2));
				}
			}
			else{
				throw new MethodFailException("Nrevents is "+nrevents+" and can only be 1, 2, or 3 (i = "+i+")");
			}
		}
		//else if B side arrival, sort from beginning to end
		else if (arrivalevents1.get(0).getSidestart()==1){
			if (nrevents == 1){
				arrivalevents.add(arrivalevents1.get(0));
			}
			else if (nrevents == 2){
				if (arrivalevents1.get(1).getSidestart()!= 1){
					throw new MethodFailException("Sidestart is not 1, but "+ arrivalevents1.get(1).getSidestart()+" and should be, since it arrives in a composition that arrives at side 1");
				}
				if (arrivalevents1.get(0).getEventblock().getCutpositionarr1()>arrivalevents1.get(1).getEventblock().getCutpositionarr1()){
					arrivalevents.add(arrivalevents1.get(1));
					arrivalevents.add(arrivalevents1.get(0));
				}
				else{
					arrivalevents.add(arrivalevents1.get(0));
					arrivalevents.add(arrivalevents1.get(1));
				}
			}
			else if (nrevents == 3){
				if (arrivalevents1.get(1).getSidestart()!=1 || arrivalevents1.get(2).getSidestart()!=1){
					throw new MethodFailException("Sidestart is not 1, but "+ arrivalevents1.get(1).getSidestart()+" and "+ arrivalevents1.get(2).getSidestart()+" and should be, since it arrives in a composition that arrives at side 1");
				}
				if (arrivalevents1.get(0).getEventblock().getCutpositionarr1()>arrivalevents1.get(1).getEventblock().getCutpositionarr1()){
					arrivalevents.add(arrivalevents1.get(1));
					arrivalevents.add(arrivalevents1.get(0));
				}
				else{
					arrivalevents.add(arrivalevents1.get(0));
					arrivalevents.add(arrivalevents1.get(1));
				}
				if (arrivalevents1.get(2).getEventblock().getCutpositionarr1()<arrivalevents.get(0).getEventblock().getCutpositionarr1()){
					arrivalevents.add(0,arrivalevents1.get(2));
				}
				else if (arrivalevents1.get(2).getEventblock().getCutpositionarr1()>arrivalevents.get(1).getEventblock().getCutpositionarr1()){
					arrivalevents.add(arrivalevents1.get(2));
				}
				else {
					arrivalevents.add(1,arrivalevents1.get(2));
				}
			}
			else{
				throw new MethodFailException("Nrevents is "+nrevents+" and can only be 1, 2, or 3 (i = "+i+")");
			}
		}
		//else methodfail exception
		else{
			throw new MethodFailException("Sidestart of event is "+arrivalevents1.get(0)+" and can only be 0 or 1 (A or B, respectively)");
		}

		if (dep ==1) { //if it is a departure sequence
			ArrayList<Event> arrivalevents_dep = new ArrayList<Event>();
			for (int j = nrevents-1; j>=0; j--){
				arrivalevents_dep.add(arrivalevents.get(j));
			}
			return arrivalevents_dep;
		}
		else{ //if it is an arriving sequence
			return arrivalevents;
		}
	}

	private void sortEvents(ArrayList<Event> eventlist) throws MethodFailException{
		if (eventlist.size()>0){
			timeline.add(eventlist.get(0));
			if (eventlist.get(1).getTime() >= timeline.get(0).getTime()){
				timeline.add(eventlist.get(1));
			}
			else {
				timeline.add(0,eventlist.get(1));
			}
			for (int i = 2; i< eventlist.size(); i++){
				if (eventlist.get(i).getTime()<timeline.get(0).getTime()){
					timeline.add(0,eventlist.get(i));
				}
				else if (eventlist.get(i).getTime()>=timeline.get(timeline.size()-1).getTime()){
					timeline.add(eventlist.get(i));
				}
				else{
					for (int j = 0; j< timeline.size()-1; j++){
						if (eventlist.get(i).getTime()>=timeline.get(j).getTime() && eventlist.get(i).getTime() < timeline.get(j+1).getTime()){
							timeline.add(j+1,eventlist.get(i));
							break;
						}
					}
				}
			}
			//throw exception if timeline ordered incorrectly
			for (int i = 0; i<timeline.size()-1; i++){
				if (timeline.get(i).getTime() > timeline.get(i+1).getTime()){
					throw new MethodFailException("Timeline ordering in Parking constructor failed at position i = "+i+": timeline.get(i).getTime() = "+timeline.get(i).getTime()+" and timeline.get(i+1).getTime() = "+timeline.get(i+1).getTime());
				}
			}
		}
	}

	public void sortTracks2(Track[] tracks) throws MethodFailException{
		int count = 0;
		int x = 0;
		for (int i = 0; i<tracks.length; i++){
			if (tracks[i].getParktrain()==-1){
				if (count ==0){
					lifotracks.add(tracks[i]);
				}
				else if (count ==1){
					if (tracks[i].getTotalBusyTime()<lifotracks.get(0).getTotalBusyTime()){
						lifotracks.add(0,tracks[i]);
					}
					else {
						lifotracks.add(tracks[i]);
					}
					x=i;
					break;
				}
				count++;
			}
		}
		for (int i = x+1; i<tracks.length ; i++){
			if(tracks[i].getParktrain() == -1){
				if (tracks[i].getTotalBusyTime()<lifotracks.get(0).getTotalBusyTime()){
					lifotracks.add(0,tracks[i]);
				}
				else if (tracks[i].getTotalBusyTime()>=lifotracks.get(lifotracks.size()-1).getTotalBusyTime()){
					lifotracks.add(tracks[i]);
				}
				else{
					for (int j = 0; j< lifotracks.size()-1; j++){
						if (tracks[i].getTotalBusyTime()>=lifotracks.get(j).getTotalBusyTime() && tracks[i].getTotalBusyTime()<lifotracks.get(j+1).getTotalBusyTime()){
							lifotracks.add(j+1,tracks[i]);
							break;
						}
					}
				}
			}
		}
		//throw exception if parkingtracks ordered incorrectly
		for (int i = 0; i<lifotracks.size()-1; i++){
			if (lifotracks.get(i).getTotalBusyTime() > lifotracks.get(i+1).getTotalBusyTime()){
				throw new MethodFailException("Lifotracks ordering in Parking constructor failed at position i = "+i+": lifotracks.get(i).getTotalBusyTime() = "+lifotracks.get(i).getTotalBusyTime()+" and lifotracks.get(i+1).getTotalBusyTime() = "+lifotracks.get(i+1).getTotalBusyTime());
			}
		}
	}

	public void sortTracks(Track[] tracks) throws MethodFailException{
		int count = 0;
		int x = 0;
		for (int i = 0; i<tracks.length; i++){
			if (tracks[i].getParktrain()==1){
				if (count ==0){
					parkingtracks.add(tracks[i]);
				}
				else if (count ==1){
					if (tracks[i].getMaxDriveBackLength()<parkingtracks.get(0).getMaxDriveBackLength()){
						parkingtracks.add(0,tracks[i]);
					}
					else {
						parkingtracks.add(tracks[i]);
					}
					x=i;
					break;
				}
				count++;
			} 
		}
		for (int i = x+1; i<tracks.length ; i++){
			if(tracks[i].getParktrain() == 1){
				if (tracks[i].getMaxDriveBackLength()<parkingtracks.get(0).getMaxDriveBackLength()){
					parkingtracks.add(0,tracks[i]);
				}
				else if (tracks[i].getMaxDriveBackLength()>=parkingtracks.get(parkingtracks.size()-1).getMaxDriveBackLength()){
					parkingtracks.add(tracks[i]);
				}
				else{
					for (int j = 0; j< parkingtracks.size()-1; j++){
						if (tracks[i].getMaxDriveBackLength()>=parkingtracks.get(j).getMaxDriveBackLength() && tracks[i].getMaxDriveBackLength()<parkingtracks.get(j+1).getMaxDriveBackLength()){
							parkingtracks.add(j+1,tracks[i]);
							break;
						}
					}
				}
			}
		}
		//throw exception if parkingtracks ordered incorrectly
		for (int i = 0; i<parkingtracks.size()-1; i++){
			if (parkingtracks.get(i).getMaxDriveBackLength() > parkingtracks.get(i+1).getMaxDriveBackLength()){
				throw new MethodFailException("Parkingtracks ordering in Parking constructor failed at position i = "+i+": parkingtracks.get(i).getMaxDriveBackLength() = "+parkingtracks.get(i).getMaxDriveBackLength()+" and parkingtracks.get(i+1).getMaxDriveBackLength() = "+parkingtracks.get(i+1).getMaxDriveBackLength());
			}
		}
	}

	public boolean arrival(Event arrivalevent, int i) throws TrackNotFreeException, MethodFailException, IOException{
		boolean parked = false;
		if(!parked){
			if (CHOICE ==1){
				parked = arrivalMaxDriveBackOrder(arrivalevent, i);
			}
		}
		if (!parked){
			System.out.println("IK BEN HIER");
			for (int j = 0; j<lifotracks.size(); j++){
				parked = lifoPark(arrivalevent, lifotracks.get(j),i);
				if (parked == true){
					break;
				}
			}
			if (parked == true){
				System.out.println("Event "+i+" is parked at track "+arrivalevent.getEventTrack().getLabel());
			}
			else {
				System.out.println("Arrival "+i+" cannot be parked simply");
			}
		}
		return parked;
	}

	public boolean arrivalMaxDriveBackOrder(Event arrivalevent, int i) throws TrackNotFreeException, IOException, MethodFailException{
		boolean parked = false;
		for (int j = 0; j<parkingtracks.size(); j++){
			//check for the first track with maxdrivebacklength > size block if it fits on the correct side
			//if: block can drive back at track
			if (parkingtracks.get(j).getMaxDriveBackLength()>=arrivalevent.getEventblock().getLength()){
				parked = simplePark(arrivalevent, parkingtracks.get(j), i);
			}
			if (parked == true){
				System.out.println("Event "+i+" is parked at track "+arrivalevent.getEventTrack().getLabel());
				break;
			}
		}
		if (!parked){
			//			ArrayList<Track> trackssortedoccupied = sortTracksOccupied(parkingtracks);
			//			for (int j = 0; j<trackssortedoccupied.size(); j++){
			//			if (trackssortedoccupied.get(j).getMaxDriveBackLength()<arrivalevent.getEventblock().getLength()){
			//				parked = simplePark(arrivalevent, trackssortedoccupied.get(j), i);
			//			}
			for (int j = 0; j<parkingtracks.size(); j++){
				if (parkingtracks.get(j).getMaxDriveBackLength()<arrivalevent.getEventblock().getLength()){
					parked = simplePark(arrivalevent, parkingtracks.get(j), i);
				}
				if (parked == true){
					break;
				}
			}
			if (!parked){
				System.out.println("Arrival "+i+" cannot be parked simply");
			}
			else {
				System.out.println("Event "+i+" is parked at track "+arrivalevent.getEventTrack().getLabel());
			}
		}
		return parked;
	}



	public boolean simplePark(Event arrivalevent, Track parkingtrack, int i) throws TrackNotFreeException, IOException, MethodFailException{
		boolean parked = false;
		//if: enough room on track
		if (parkingtrack.getTracklength() - parkingtrack.getCompositionLengthOnTrack()>=arrivalevent.getEventblock().getLength()){
			//try to park without reverse arrival/departure of itself
			System.out.println("Try park event "+i+" on track "+parkingtrack.getLabel()+" without reverse arrival/departure itself");
			parked = park1(arrivalevent, parkingtrack, i); //no reverse arrival/departure itself
			//if: not easily parked, but can drive back
			if (!parked){
				System.out.println("Failed, try park event "+i+" on track "+parkingtrack.getLabel()+" with reverse arrival");
				if (parkingtrack.getMaxDriveBackLength() >= arrivalevent.getEventblock().getLength()){
					//toggle reverse arrival, and try to park with reverse arrival
					arrivalevent.toggleReverseArrival();
					parked = park1(arrivalevent,parkingtrack, i);//reverse arrival
					//if not parked, toggle reverse arrival and reverse departure and try to park with reverse departure
					if (!parked){
						System.out.println("Failed, try park event "+i+" on track "+parkingtrack.getLabel()+" with reverse departure");
						arrivalevent.toggleReverseArrival();
						arrivalevent.toggleReverseDeparture();
						parked = park1(arrivalevent,parkingtrack,i);//reverse departure
						//if not parked, toggle reverse arrival and try to park with reverse arrival and departure
						if (!parked){
							System.out.println("Failed, try park event "+i+" on track "+parkingtrack.getLabel()+" with reverse arrival and departure");
							arrivalevent.toggleReverseArrival();
							parked = park1(arrivalevent,parkingtrack,i);//reverse arrival and departure
							//if not parked, toggle reverse arrival and reverse departure back to original setting
							if (!parked){
								System.out.println("Failed, continue to next track");
								arrivalevent.toggleReverseArrival();
								arrivalevent.toggleReverseDeparture();
							}
						}
					}
				}
			}
		}
		else{
			System.out.println("Not enough room available on track "+parkingtrack.getLabel());
		}
		return parked;
	}

	//TODO: IMPORTANT, WE ASSUME THAT ALL LIFO TRACKS CAN ONLY BE ENTERED FROM THE B (RIGHT) SIDE (INTERPRETATION)
	public boolean lifoPark(Event arrivalevent, Track parkingtrack, int i) throws TrackNotFreeException{
		boolean parked = false;
		if (parkingtrack.getCompositionLengthOnTrack() + arrivalevent.getEventblock().getLength() <= parkingtrack.getTracklength()){
			//if: feasible with other blocks
			boolean feasibleblocks = false;
			if (parkingtrack.getEventlist().size() == 0){
				feasibleblocks = true;
			}
			else{
				if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime()<= arrivalevent.getEndtime()){
					feasibleblocks = true;
				}
			}
			if(feasibleblocks == true){
				//if: feasible with busytime
				boolean feasiblebusytime = true;
				for (int j = arrivalevent.getStarttime(); j<= arrivalevent.getEndtime(); j++){
					if (parkingtrack.getBusyArray()[j] == 1){
						feasiblebusytime = false;
						break;
					}
				}
				if (feasiblebusytime = true){
					parked = true;
					arrivalBSide(arrivalevent, parkingtrack);
				}
			}
		}
		return parked;
	}


	public boolean park1(Event arrivalevent, Track parkingtrack, int x) throws MethodFailException, IOException, TrackNotFreeException{
		boolean parked = false;
		//if: A side arrival
		if (arrivalevent.getArrivalSide() == 0){
			//if: A side departure
			if (arrivalevent.getDepartureSide()==0){
				System.out.println("AA");
				//check if we can park it
				boolean feasible = checkFeasibleAA(arrivalevent, parkingtrack);
				//if: we can park it directly
				if (feasible){
					System.out.println("Succeeded directly");
					arrivalASide(arrivalevent,parkingtrack);
					parked = true;
				}
				//else: we cannot park it directly
				else{
					//if: there is at least one on track
					if (parkingtrack.getEventlist().size() > 0){
						System.out.println("Try toggle adjacent");
						//check if we can toggle the adjacent
						boolean toggle = togglecheck(parkingtrack.getEventlist().get(0),0);
						//if: we can toggle adjacent
						if (toggle){
							System.out.println("Toggle feasible");
							//toggle, and check if feasible
							System.out.println("Current dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							parkingtrack.getEventlist().get(0).toggleReverseDeparture();
							System.out.println("New dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							feasible = checkFeasibleAA(arrivalevent, parkingtrack);
							//if: we can park it
							if (feasible){
								System.out.println("Succeeded with toggle adjacent");
								arrivalASide(arrivalevent,parkingtrack);
								parked = true;
							}
							//else: undo toggle
							else{
								System.out.println("Not succeeded with toggle adjacent");
								parkingtrack.getEventlist().get(0).toggleReverseDeparture();
								System.out.println("Current dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							}
						}
					}
				}
			}
			//else if: B side departure
			else if (arrivalevent.getDepartureSide()==1){
				System.out.println("AB");
				//check if we can park it
				boolean feasible = checkFeasibleAB(arrivalevent, parkingtrack);
				//if: we can park it directly
				if (feasible){
					System.out.println("Succeeded directly");
					arrivalASide(arrivalevent,parkingtrack);
					parked = true;
				}
				//else: we cannot park it directly
				else{
					//if: there is at least one on track
					if (parkingtrack.getEventlist().size() > 0){
						System.out.println("Try toggle adjacent");
						//check if we can toggle the adjacent
						boolean toggle = togglecheck(parkingtrack.getEventlist().get(0),0);
						//if: we can toggle adjacent
						if (toggle){
							System.out.println("Toggle feasible");
							//toggle, and check if feasible
							System.out.println("Current dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							parkingtrack.getEventlist().get(0).toggleReverseDeparture();
							System.out.println("New dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							feasible = checkFeasibleAB(arrivalevent, parkingtrack);
							//if: we can park it
							if (feasible){
								System.out.println("Succeeded with toggle adjacent");
								arrivalASide(arrivalevent,parkingtrack);
								parked = true;
							}
							//else: undo toggle
							else{
								System.out.println("Not succeeded with toggle adjacent");
								parkingtrack.getEventlist().get(0).toggleReverseDeparture();
								System.out.println("Current dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							}
						}
					}
				}
			}
			//else: methodfail
			else{
				throw new MethodFailException("Event "+x+": departureside is "+arrivalevent.getDepartureSide()+", and should both be 0 or 1");
			}
		}
		//else if: B side arrival
		else if (arrivalevent.getArrivalSide() == 1){
			//if: A side departure
			if (arrivalevent.getDepartureSide()==0){
				System.out.println("BA");
				//check if we can park it
				boolean feasible = checkFeasibleBA(arrivalevent, parkingtrack);
				//if: we can park it directly
				if (feasible){
					System.out.println("Succeeded directly");
					arrivalBSide(arrivalevent,parkingtrack);
					parked = true;
				}
				//else: we cannot park it directly
				else{
					//if: there is at least one on track
					if (parkingtrack.getEventlist().size() > 0){
						System.out.println("Try toggle adjacent");
						//check if we can toggle the adjacent
						boolean toggle = togglecheck(parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1),1);
						//if: we can toggle adjacent
						if (toggle){
							System.out.println("Toggle feasible");
							//toggle, and check if feasible
							System.out.println("Current dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).toggleReverseDeparture();
							System.out.println("New dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							feasible = checkFeasibleBA(arrivalevent, parkingtrack);
							//if: we can park it
							if (feasible){
								System.out.println("Succeeded with toggle adjacent");
								arrivalBSide(arrivalevent,parkingtrack);
								parked = true;
							}
							//else: undo toggle
							else{
								System.out.println("Not succeeded with toggle adjacent");
								parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).toggleReverseDeparture();
								System.out.println("Current dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							}
						}
					}
				}
			}
			//else if: B side departure
			else if (arrivalevent.getDepartureSide()==1){
				System.out.println("BB");
				//check if we can park it
				boolean feasible = checkFeasibleBB(arrivalevent, parkingtrack);
				//if: we can park it directly
				if (feasible){
					System.out.println("Succeeded directly");
					arrivalBSide(arrivalevent,parkingtrack);
					parked = true;
				}
				//else: we cannot park it directly
				else{
					//if: there is at least one on track
					if (parkingtrack.getEventlist().size() > 0){
						System.out.println("Try toggle adjacent");
						//check if we can toggle the adjacent
						boolean toggle = togglecheck(parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1),1);
						//if: we can toggle adjacent
						if (toggle){
							System.out.println("Toggle feasible");
							//toggle, and check if feasible
							System.out.println("Current dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).toggleReverseDeparture();
							System.out.println("New dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							feasible = checkFeasibleBB(arrivalevent, parkingtrack);
							//if: we can park it
							if (feasible){
								System.out.println("Succeeded with toggle adjacent");
								arrivalBSide(arrivalevent,parkingtrack);
								parked = true;
							}
							//else: undo toggle
							else{
								System.out.println("Not succeeded with toggle adjacent");
								parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).toggleReverseDeparture();
								System.out.println("Current dep side: "+parkingtrack.getEventlist().get(0).getDepartureSide());
							}
						}
					}
				}
			}
			//else: methodfail
			else{
				throw new MethodFailException("Event "+x+": departureside is "+arrivalevent.getDepartureSide()+", and should both be 0 or 1");
			}
		}
		//else: methodfail
		else{
			throw new MethodFailException("Event "+x+": arrivalside is "+arrivalevent.getArrivalSide()+", and should both be 0 or 1");
		}
		return parked;
	}

	public boolean checkFeasibleAA(Event checkevent, Track parkingtrack) throws MethodFailException{
		boolean feasible = false;
		//if: only one on track
		if (parkingtrack.getEventlist().size() == 0){
			feasible = true;
		}
		//if: more on track
		else{
			//if: departure side most left is A
			if (parkingtrack.getEventlist().get(0).getDepartureSide() == 0){
				//if: departure time is larger than checkevent
				if (parkingtrack.getEventlist().get(0).getEndtime()>=checkevent.getEndtime()){
					feasible = true;
				}
				//else: departure time is not larger than checkevent
				else{
					//feasible is false
				}
			}
			//else if: departure side is B
			else if (parkingtrack.getEventlist().get(0).getDepartureSide() == 1){
				feasible = true;
			}
			else {
				throw new MethodFailException("parkingtrack.getEventlist().get(0).getDepartureSide() should be 0 or 1 but is "+parkingtrack.getEventlist().get(0).getDepartureSide());
			}
		}
		return feasible;
	}

	public boolean checkFeasibleAB(Event checkevent, Track parkingtrack) throws MethodFailException{
		boolean feasible = false;
		//if: only one on track
		if (parkingtrack.getEventlist().size() == 0){
			feasible = true;
		}
		//if: more on track
		else{
			//if: departure side most left is A
			if (parkingtrack.getEventlist().get(0).getDepartureSide() == 0){
				//not feasible
			}
			//else if: departure side most left is B
			else if (parkingtrack.getEventlist().get(0).getDepartureSide() == 1){
				//if: departure time is smaller than checkevent
				if (parkingtrack.getEventlist().get(0).getEndtime()<=checkevent.getEndtime()){
					feasible = true;
				}
				//else: departure time is not larger than checkevent
				else{
					//feasible is false
				}
			}
			else {
				throw new MethodFailException("parkingtrack.getEventlist().get(0).getDepartureSide() should be 0 or 1 but is "+parkingtrack.getEventlist().get(0).getDepartureSide());
			}
		}
		return feasible;
	}

	public boolean checkFeasibleBA(Event checkevent, Track parkingtrack) throws MethodFailException{
		boolean feasible = false;
		//if: only one on track
		if (parkingtrack.getEventlist().size() == 0){
			feasible = true;
		}
		//if: more on track
		else{
			//if: departure side most right is A
			if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getDepartureSide() == 0){
				//if: departure time is smaller than checkevent
				if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime()<=checkevent.getEndtime()){
					feasible = true;
				}
				//else: departure time is not larger than checkevent
				else{
					//feasible is false
				}
			}
			//else if: departure side most right is B
			else if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getDepartureSide() == 1){
				//not feasible
			}
			else {
				throw new MethodFailException("parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getDepartureSide() should be 0 or 1 but is "+parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getDepartureSide());
			}
		}
		return feasible;
	}

	public boolean checkFeasibleBB(Event checkevent, Track parkingtrack) throws MethodFailException{
		boolean feasible = false;
		//if: only one on track
		if (parkingtrack.getEventlist().size() == 0){
			feasible = true;
		}
		//if: more on track
		else{
			//if: departure side most left is A
			if (parkingtrack.getEventlist().get(0).getDepartureSide() == 0){
				feasible = true;
			}
			//else if: departure side is B
			else if (parkingtrack.getEventlist().get(0).getDepartureSide() == 1){
				//if: departure time is larger than checkevent
				if (parkingtrack.getEventlist().get(0).getEndtime()>=checkevent.getEndtime()){
					feasible = true;
				}
				//else: departure time is not larger than checkevent
				else{
					//feasible is false
				}
			}
			else {
				throw new MethodFailException("parkingtrack.getEventlist().get(0).getDepartureSide() should be 0 or 1 but is "+parkingtrack.getEventlist().get(0).getDepartureSide());
			}
		}
		return feasible;
	}


	public void arrivalASide(Event arrivalevent, Track parkingtrack) throws TrackNotFreeException{
		arrivalevent.setEventTrack(parkingtrack);
		parkingtrack.addEventtoTrackLeft(arrivalevent);
		parkingtrack.addCompositiontoTrackLeft(arrivalevent.getEventblock());
	}

	public void arrivalBSide(Event arrivalevent, Track parkingtrack) throws TrackNotFreeException{
		arrivalevent.setEventTrack(parkingtrack);
		parkingtrack.addEventtoTrackRight(arrivalevent);
		parkingtrack.addCompositiontoTrackRight(arrivalevent.getEventblock());
	}

	public boolean togglecheck(Event checkevent, int side) throws MethodFailException{
		boolean toggle = false;
		//if: it can drive back on track
		if (checkevent.getEventblock().getLength() <= checkevent.getEventTrack().getMaxDriveBackLength()){
			//if: only one on track
			if (checkevent.getEventTrack().getEventlist().size() <= 1){
				toggle = true;
			}
			//else: more on track
			else{
				//if: it's at the A side of the track
				if (side == 0){
					if (checkevent.getEventTrack().getEventlist().get(0)!=checkevent){
						throw new MethodFailException("Checkevent should be at side 0 but is not the first entry of EventTrack.getEventlist()");
					}
					else {
						//if: it is now leaving from the A side (only logical one, if it is now leaving from the B side changing to A side won't make it feasible)
						if (checkevent.getDepartureSide() == 0){
							boolean possible = false;
							for (int i = 1; i<checkevent.getEventTrack().getEventlist().size(); i++){
								//if: others on track leave from the A side (not possible)
								if (checkevent.getEventTrack().getEventlist().get(i).getDepartureSide()==0){
									possible = false;
									break;
								}
								//else if: others on track leave from the B side (only possible)
								else if (checkevent.getEventTrack().getEventlist().get(i).getDepartureSide()==1){
									//if: others leave after checkevent
									if (checkevent.getEventTrack().getEventlist().get(i).getEndtime()>checkevent.getEndtime()){
										possible = false;
										break;
									}
									//else: possible = true
									else {
										possible = true;
									}
								}
								//else: methodfail
								else {
									throw new MethodFailException("checkevent.getEventTrack().getEventlist().get("+i+").getDepartureSide() should be 0 or 1 and is "+checkevent.getEventTrack().getEventlist().get(i).getDepartureSide());
								}
							}
							if (possible == true){
								toggle = true;
							}
						}
						//else: now leaving from B side, changing will not make it possible
						else if (checkevent.getDepartureSide() == 1){
							//will not make it feasible
						}
						//else: methodfail
						else {
							throw new MethodFailException("Checkevent should have departure side 0 or 1 but is "+checkevent.getDepartureSide());
						}
					}
				}
				//else if: it's at the B side of the track
				else if (side == 1){
					if (checkevent.getEventTrack().getEventlist().get(checkevent.getEventTrack().getEventlist().size()-1)!=checkevent){
						throw new MethodFailException("Checkevent should be at side 0 but is not the first entry of EventTrack.getEventlist()");
					}
					else {
						//if: now leaving from A side, changing will not make it possible
						if (checkevent.getDepartureSide() == 0){
							//will not make it feasible
						}
						//if: it is now leaving from the B side (only logical one, if it is now leaving from the A side changing to B side won't make it feasible)
						else if (checkevent.getDepartureSide() == 1){
							boolean possible = false;
							for (int i = 0; i<checkevent.getEventTrack().getEventlist().size()-1; i++){
								//if: others on track leave from the A side (only possible)
								if (checkevent.getEventTrack().getEventlist().get(i).getDepartureSide()==0){
									//if: others leave after checkevent
									if (checkevent.getEventTrack().getEventlist().get(i).getEndtime()>checkevent.getEndtime()){
										possible = false;
										break;
									}
									//else: possible = true
									else {
										possible = true;
									}
								}
								//else if: others on track leave from the B side (not possible)
								else if (checkevent.getEventTrack().getEventlist().get(i).getDepartureSide()==1){
									possible = false;
									break;
								}
								//else: methodfail
								else {
									throw new MethodFailException("checkevent.getEventTrack().getEventlist().get("+i+").getDepartureSide() should be 0 or 1 and is "+checkevent.getEventTrack().getEventlist().get(i).getDepartureSide());
								}
							}
							if (possible == true){
								toggle = true;
							}
						}
						//else: methodfail
						else {
							throw new MethodFailException("Checkevent should have departure side 0 or 1 but is "+checkevent.getDepartureSide());
						}
					}
				}
				//else: methodfail
				else{
					throw new MethodFailException("Side should be 0 or 1 but is "+side);
				}
			}
		}
		return toggle;
	}

	public void departure(Event departureevent, int i) throws MethodFailException, TrackNotFreeException, IOException{
		//reverse leave
		//not reverse leave

		//if: departure from lifotrack
		if (departureevent.getEventTrack().getParktrain() == -1){
			departure(departureevent);
		}
		//else: departure from 'normal' track
		else{
			//if: we leave in reverse
			if (departureevent.getDepartureSide() == 0){
				if (departureevent.getEventTrack().getEventlist().get(0)==departureevent.getRelatedEvent()){
					departure(departureevent);
				}
			}
			else if (departureevent.getDepartureSide() == 1){
				if (departureevent.getEventTrack().getEventlist().get(departureevent.getEventTrack().getEventlist().size()-1)==departureevent.getRelatedEvent()){
					departure(departureevent);
				}
			}
			else{
				throw new MethodFailException("GetDepartureSide for event "+i+" not equal to 0 or 1, but "+departureevent.getDepartureSide());
			}
		}
	}

	public void departure(Event departureevent) throws TrackNotFreeException, IOException{
		departureevent.getEventTrack().removeEventfromTrack(departureevent.getRelatedEvent());
		departureevent.getEventTrack().removeCompositionfromTrack(departureevent.getRelatedEvent().getEventblock());
	}


	public ArrayList<Track> getParkingTracks(){
		return parkingtracks;
	}

	public ArrayList<Event> getTimeline(){
		return timeline;
	}

	public int getNotParked(){
		return notparked;
	}
}

