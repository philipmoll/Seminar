import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
public class Parking { //TODO: test

	private ArrayList<Track> parkingtracks;
	//private int[][] parkingpositions;
	//private ArrayList<Parking> previousparkings;
	private ArrayList<Event> timeline;
	private ArrayList<int[]> freetracktimes;

	public Parking(ArrayList<Event> eventlist, Track[] tracks) throws MethodFailException, TrackNotFreeException, IOException{
		// input: eventlist met per compositie op welke tijd hij aankomt en weggaat en waarheen/waarvandaan, tracklist

		//take parktracks out of tracks and order by maxbackwardlength
		parkingtracks = new ArrayList<Track>();
		sortTracks(tracks);


		//sort eventlist into timeline
		timeline = new ArrayList<Event>();
		sortEvents(eventlist);
		for (int i = 0; i<timeline.size(); i++){
			System.out.println(timeline.get(i));
		}

		freetracktimes = new ArrayList<>();
		for (int i = 0; i< timeline.size(); i++){
			System.out.println("event "+i);
			if (timeline.get(i).getType()==1){ //if it is a departure
				departure(timeline.get(i), i);
			}
			else if (timeline.get(i).getType()==0) { //if it is an arrival
				arrival(timeline.get(i), i);
			}
			else{
				throw new IOException("Type of event "+i+" should be 0 (arrival) or 1 (departure), but is "+i);
			}
		}
	}

	private void sortEvents(ArrayList<Event> eventlist) throws MethodFailException{
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
//			System.out.println(timeline.get(i));
			if (timeline.get(i).getTime() > timeline.get(i+1).getTime()){
				throw new MethodFailException("Timeline ordering in Parking constructor failed at position i = "+i+": timeline.get(i).getTime() = "+timeline.get(i).getTime()+" and timeline.get(i+1).getTime() = "+timeline.get(i+1).getTime());
			}
		}
	}


	public void sortTracks(Track[] tracks) throws MethodFailException{
		int count = 0;
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
					break;
				}
				count++;
			} 
		}
		for (int i = 2; i<tracks.length ; i++){
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

	public void arrival(Event arrivalevent, int i) throws TrackNotFreeException, MethodFailException, IOException{
		boolean parked = arrivalNormal(arrivalevent, i);
		if (parked == false){
			//TODO: try arrival reverse
		}

	}

	public boolean arrivalNormal(Event arrivalevent, int i) throws TrackNotFreeException, IOException{
		boolean parked = false;
		for (int j = 0; j<parkingtracks.size(); j++){
			//check for the first track with maxdrivebacklength > size block if it fits on the correct side
			//if: block can drive back at track
			if (parkingtracks.get(j).getMaxDriveBackLength()>=arrivalevent.getEventblock().getLength()){
				//if: enough room on track
				if (parkingtracks.get(j).getTracklength() - parkingtracks.get(j).getCompositionLengthOnTrack()>=arrivalevent.getEventblock().getLength()){
					//if: no compositions on track yet
					if (parkingtracks.get(j).getCompositionlist().size() == 0){
						//add compositions and events to track
						arrivalASideSimple(arrivalevent, parkingtracks.get(j));
						parked = true; //set parked to true
						break; //go on to next arrival
					}
					//else: track not empty
					else{
						//if: we need to enter at the A side
						if (arrivalevent.getSidestart() == 0){ //A side (left side)
							//if: we need to leave via the A side
							if (arrivalevent.getSideend() == 0){ //A side (left side)
								//if: the eventblock to the right leaves via the A side
								if (parkingtracks.get(j).getEventlist().get(0).getSideend() == 0){ //A side (left side)
									//if: the eventblock to the right leaves after arrivalblock
									if (parkingtracks.get(j).getEventlist().get(0).getEndtime() >= arrivalevent.getEndtime()){
										//add compositions and events to track
										arrivalASideSimple(arrivalevent, parkingtracks.get(j));
										parked = true; //set parked to true
										break; //go on to next arrival
									}
								}
								//else if: the eventblock to the right leaves via the B side
								else if (parkingtracks.get(j).getEventlist().get(0).getSideend() ==1){ //B side (right side)
									//add compositions and events to track
									arrivalASideSimple(arrivalevent, parkingtracks.get(j));
									parked = true; //set parked to true
									break; //go on to next arrival
								}
								//else: IOException
								else{
									throw new IOException("Eventblock at A side of track "+parkingtracks.get(j).getLabel()+" has side end "+parkingtracks.get(j).getEventlist().get(0).getSideend()+" and should be 0 (A side) or 1 (B side)");
								}
							}
							//else if: we need to leave via the B side
							else if (arrivalevent.getSideend()==1){ //B side (right side)
								//if: the eventblock to the right leaves via the A side (NB: A side not possible here, earlier or later)
								if (parkingtracks.get(j).getEventlist().get(0).getSideend()==0){
									//cannot park here!
								}
								//else if: the eventblock to the right leaves via the B side
								else if (parkingtracks.get(j).getEventlist().get(0).getSideend()==1){
									//if: the eventblock to the right leaves before arrivalblock
									if (parkingtracks.get(j).getEventlist().get(0).getEndtime() <= arrivalevent.getEndtime()){
										//add compositions and events to track
										arrivalASideSimple(arrivalevent, parkingtracks.get(j));
										parked = true; //set parked to true
										break; //go on to next arrival
									}
								}
								//else: IOException
								else{
									throw new IOException("Eventblock at A side of track "+parkingtracks.get(j).getLabel()+" has side end "+parkingtracks.get(j).getEventlist().get(0).getSideend()+" and should be 0 (A side) or 1 (B side)");
								}
							}
							//else: IOException
							else {
								throw new IOException("Side end of event "+i+" is "+arrivalevent.getSideend()+ " and should be 0 (A side) or 1 (B side)");
							}
						}
						//else if: we need to enter at the B side
						if (arrivalevent.getSidestart()==1){ //B side (right side)
							//if: we need to leave via the B side
							if (arrivalevent.getSideend() == 1){ //B side (right side)
								//if: the eventblock to the left leaves via the B side
								if (parkingtracks.get(j).getEventlist().get(parkingtracks.get(j).getEventlist().size()-1).getSideend() == 1){ //B side (right side)
									//if: the eventblock to the left leaves after arrivalblock
									if (parkingtracks.get(j).getEventlist().get(parkingtracks.get(j).getEventlist().size()-1).getEndtime() >= arrivalevent.getEndtime()){
										//add compositions and events to track
										arrivalBSideSimple(arrivalevent, parkingtracks.get(j));
										parked = true; //set parked to true
										break; //go on to next arrival
									}
								}
								//else if: the eventblock to the left leaves via the A side
								else if (parkingtracks.get(j).getEventlist().get(parkingtracks.get(j).getEventlist().size()-1).getSideend() ==0){ //A side (left side)
									//add compositions and events to track
									arrivalBSideSimple(arrivalevent, parkingtracks.get(j));
									parked = true; //set parked to true
									break; //go on to next arrival
								}
								//else: IOException
								else{
									throw new IOException("Eventblock at B side of track "+parkingtracks.get(j).getLabel()+" has side end "+parkingtracks.get(j).getEventlist().get(parkingtracks.get(j).getEventlist().size()-1).getSideend()+" and should be 0 (A side) or 1 (B side)");
								}
							}
							//else if: we need to leave via the A side
							else if (arrivalevent.getSideend()==0){ //A side (left side)
								//if: the eventblock to the right leaves via the B side (NB: B side not possible here, earlier or later)
								if (parkingtracks.get(j).getEventlist().get(parkingtracks.get(j).getEventlist().size()-1).getSideend()==1){
									//cannot park here!
								}
								//else if: the eventblock to the right leaves via the A side
								else if (parkingtracks.get(j).getEventlist().get(parkingtracks.get(j).getEventlist().size()-1).getSideend()==0){
									//if: the eventblock to the right leaves before arrivalblock
									if (parkingtracks.get(j).getEventlist().get(parkingtracks.get(j).getEventlist().size()-1).getEndtime() <= arrivalevent.getEndtime()){
										//add compositions and events to track
										arrivalBSideSimple(arrivalevent, parkingtracks.get(j));
										parked = true; //set parked to true
										break; //go on to next arrival
									}
								}
								//else: IOException
								else{
									throw new IOException("Eventblock at B side of track "+parkingtracks.get(j).getLabel()+" has side end "+parkingtracks.get(j).getEventlist().get(parkingtracks.get(j).getEventlist().size()-1).getSideend()+" and should be 0 (A side) or 1 (B side)");
								}
							}
							//else: IOException
							else {
								throw new IOException("Side end of event "+i+" is "+arrivalevent.getSideend()+ " and should be 0 (A side) or 1 (B side)");
							}
						}
						//else: IOException
						else{
							throw new IOException("Side start of event "+i+" is "+arrivalevent.getSidestart()+" and should be 0 (A side) or 1 (B side)");
						}
					}
				}
			}
		}
		if (!parked){
			System.out.println("Arrival "+i+" cannot be parked simply");
		}
		else {
			System.out.println("Event "+i+" is parked at track "+arrivalevent.getEventTrack().getLabel());
		}
		return parked;
	}

	public void arrivalASideSimple(Event arrivalevent, Track parkingtrack) throws TrackNotFreeException{
		arrivalevent.setEventTrack(parkingtrack);
		arrivalevent.getRelatedEvent().setEventTrack(parkingtrack);
		System.out.println(arrivalevent.getRelatedEvent().getEventTrack().getLabel());
		parkingtrack.addCompositiontoTrackLeft(arrivalevent.getEventblock());
		parkingtrack.addEventtoTrackLeft(arrivalevent);
	}

	public void arrivalBSideSimple(Event arrivalevent, Track parkingtrack) throws TrackNotFreeException{
		arrivalevent.setEventTrack(parkingtrack);
		arrivalevent.getRelatedEvent().setEventTrack(parkingtrack);
		System.out.println(arrivalevent.getRelatedEvent().getEventTrack().getLabel());
		parkingtrack.addCompositiontoTrackRight(arrivalevent.getEventblock());
		parkingtrack.addEventtoTrackRight(arrivalevent);
	}

	public void departure(Event departureevent, int i) throws MethodFailException, TrackNotFreeException, IOException{
		//reverse leave
		//not reverse leave

		//if we leave in reverse
		if (departureevent.getReverseLeave() == 1){
			reverseDeparture(departureevent, i);
		}
		else if (departureevent.getReverseLeave() == 0){
			normalDeparture(departureevent, i);
		}
		else{
			throw new IOException("GetReverseLeave for event "+i+" not equal to 0 or 1");
		}
	}

	public void reverseDeparture(Event departureevent, int i) throws MethodFailException, IOException, TrackNotFreeException{
		//throw exception if free track needed is false (we leave in reverse, so free track should be toggled)
		if (freetracktimes.size() == 0){
			throw new MethodFailException("Freetracktime is empty for event "+i+" but we require it to leave in reverse so should be larger than 1");
		}
		else{
			boolean check = false;
			for (int j = 0; j<freetracktimes.size(); j++){
				if (departureevent.getTime() == freetracktimes.get(j)[1]){ //if departure time coincides with freetracktime //TODO: check for final events!!!!!!
					check = true;
					freetracktimes.remove(j);
					break;
				}
			}
			if (!check){
				throw new MethodFailException("Freetracktime is not defined for event "+i+" at departuretime "+departureevent.getTime());
			}
		}

		//throw exception if no free track available or if it is the only event on the track
		if (departureevent.getEventTrack().getCompositionlist().size() == 0){
			throw new MethodFailException("We require event "+i+" to leave in reverse, but it is the only event on the track");
		}
		boolean available = false; //set correct freetrack available to false
		for (int k = 0; k<parkingtracks.size(); k++){
			if (parkingtracks.get(k).getCompositionlist().size() == 0){//free track available
				//if the free track is at a lower position
				if (parkingtracks.get(k).getMaxDriveBackLength()>=departureevent.getEventTrack().getMaxDriveBackLength()){
				available = true;
				break;
				}
				//if the free track is higher, but has enough max drive back length
				else if (parkingtracks.get(k).getMaxDriveBackLength()>= departureevent.getEventblock().getLength()){
					available = true;
					break;
				}
			}
		}
		if (available == false){ // if no free track available, throw exception
			throw new MethodFailException("No free track available and event "+i+" must leave in reverse");
		}

		//throw exception if composition not at the right side
		if (departureevent.getSideend() == 0){ //if we leave via A side, but reverse, so B side
			//throw exception if eventblock not at the B side of the track
			if (departureevent.getEventTrack().getCompositionlist().get(departureevent.getEventTrack().getCompositionlist().size()-1) != departureevent.getEventblock()){
				throw new MethodFailException("Event "+i+" must leave via B side (A side reverse), but is not at the B side");
			}
		}
		else if (departureevent.getSideend() == 1){ //if we leave via B side, but reverse, so A side
			//throw exception if eventblock not at the B side of the track
			if (departureevent.getEventTrack().getCompositionlist().get(0) != departureevent.getEventblock()){
				throw new MethodFailException("Event "+i+" must leave via A side (B side reverse), but is not at the A side");
			}
		}
		else { //sideend is neither 0 nor 1, IOException!
			throw new IOException("Sideend of event "+i+" is "+timeline.get(i).getSideend()+" and should be 0 or 1");
		}

		//we can leave in reverse, now actually leave
		//remove event from track, remove composition from track
		departureevent.getEventTrack().removeEventfromTrack(departureevent.getRelatedEvent());
		departureevent.getEventTrack().removeCompositionfromTrack(departureevent.getRelatedEvent().getEventblock());
	}

	public void normalDeparture(Event departureevent, int i) throws MethodFailException, IOException, TrackNotFreeException{		
		//throw exception if composition not at the right side
		if (departureevent.getSideend() == 0){ //if we leave via A side
			//throw exception if eventblock not at the A side of the track
			if (departureevent.getEventTrack().getCompositionlist().get(0) != departureevent.getEventblock()){
				throw new MethodFailException("Event "+i+" must leave via A side, but is not at the A side");
			}
		}
		else if (departureevent.getSideend() == 1){ //if we leave via B side
			//throw exception if eventblock not at the B side of the track
			if (departureevent.getEventTrack().getCompositionlist().get(departureevent.getEventTrack().getCompositionlist().size()-1) != departureevent.getEventblock()){
				throw new MethodFailException("Event "+i+" must leave via B side, but is not at the B side");
			}
		}
		else { //sideend is neither 0 nor 1, IOException!
			throw new IOException("Sideend of event "+i+" is "+timeline.get(i).getSideend()+" and should be 0 or 1");
		}

		//we can leave, now actually leave
		//remove event from track, remove composition from track
		departureevent.getEventTrack().removeCompositionfromTrack(departureevent.getEventblock());
		departureevent.getEventTrack().removeEventfromTrack(departureevent);		
	}

	
	public ArrayList<Track> getParkingTracks(){
		return parkingtracks;
	}
	
	public ArrayList<Event> getTimeline(){
		return timeline;
	}
}