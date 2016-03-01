//NEW, COMPLETELY CHANGED

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
public class Parking3 implements Serializable{ //TODO: test!

	private ArrayList<Track> parkingtracks;
	//private int[][] parkingpositions;
	//private ArrayList<Parking> previousparkings;
	private ArrayList<Event> timeline;
	//private ArrayList<int[]> freetracktimes;

	public Parking3(ArrayList<Event> eventlist, Track[] tracks) throws MethodFailException, TrackNotFreeException, IOException{
		// input: eventlist met per compositie op welke tijd hij aankomt en weggaat en waarheen/waarvandaan, tracklist

		//take parktracks out of tracks and order by maxbackwardlength
		parkingtracks = new ArrayList<Track>();
		sortTracks(tracks);

		//sort eventlist into timeline
		timeline = new ArrayList<Event>();
		sortEvents(eventlist);
		for (int i = 0; i<timeline.size(); i++){
			System.out.println("i: "+i+" Event: "+timeline.get(i)+" Final block: "+timeline.get(i).getEventblock()+" finalevent: "+timeline.get(i).getFinalType()+" beginside: "+timeline.get(i).getSidestart()+" endside: "+timeline.get(i).getSideend()+" starttime this: "+timeline.get(i).getStarttime()+" starttime related: "+timeline.get(i).getRelatedEvent().getStarttime()+ " endtime this: "+timeline.get(i).getEndtime()+" endtime related: "+timeline.get(i).getRelatedEvent().getEndtime());
		}
		System.out.println();
		//		System.out.println(timeline.get(2).getEventblock().getCutpositionarr1());
		//		System.out.println(timeline.get(3).getEventblock().getCutpositionarr1());

		for (int i = 0; i< timeline.size(); i++){
			System.gc();
			System.out.println("event "+i+ " "+timeline.get(i));
			if (timeline.get(i).getType()==1){ //if it is a departure
				departure(timeline.get(i), i);
				System.out.println("Departure from track "+timeline.get(i).getEventTrack().getLabel());
				for (int x = 0; x<timeline.get(i).getEventTrack().getEventlist().size(); x++){
					System.out.println(timeline.get(i).getEventTrack().getEventlist().get(x));
				}
			}
			else if (timeline.get(i).getType()==0) { //if it is an arrival
				boolean parked = arrival(timeline.get(i), i);
				System.out.println("Arrival at track "+timeline.get(i).getEventTrack().getLabel());
				for (int x = 0; x<timeline.get(i).getEventTrack().getEventlist().size(); x++){
					System.out.println(timeline.get(i).getEventTrack().getEventlist().get(x));
				}
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
			if (timeline.get(i).getTime() > timeline.get(i+1).getTime()){
				throw new MethodFailException("Timeline ordering in Parking constructor failed at position i = "+i+": timeline.get(i).getTime() = "+timeline.get(i).getTime()+" and timeline.get(i+1).getTime() = "+timeline.get(i+1).getTime());
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
		boolean parked = arrivalNormal(arrivalevent, i);
		return parked;
	}

	public boolean arrivalNormal(Event arrivalevent, int i) throws TrackNotFreeException, IOException, MethodFailException{
		boolean parked = false;
		for (int j = 0; j<parkingtracks.size(); j++){
			//check for the first track with maxdrivebacklength > size block if it fits on the correct side
			//if: block can drive back at track
			if (parkingtracks.get(j).getMaxDriveBackLength()>=arrivalevent.getEventblock().getLength()){
				parked = simplePark(arrivalevent, parkingtracks.get(j), i);
			}
			if (parked == true){
				break;
			}
		}
		//if: parking failed, try to park at the most occupied track that we cannot drive back on
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



	public boolean simplePark(Event arrivalevent, Track parkingtrack, int i) throws TrackNotFreeException, IOException{
		boolean parked = false;
		//if: enough room on track
		if (parkingtrack.getTracklength() - parkingtrack.getCompositionLengthOnTrack()>=arrivalevent.getEventblock().getLength()){
			parked = park1(arrivalevent, parkingtrack, i); //no reverse arrival/departure itself
			if (!parked){
				parked = park2(arrivalevent,parkingtrack, i);//reverse arrival
				if (!parked){
					parked = park3(arrivalevent,parkingtrack,i);//reverse departure
					if (!parked){
						parked = park4(arrivalevent,parkingtrack,i);//reverse arrival and departure
					}
				}
			}
		}
		return parked;
	}


	public boolean park1(Event arrivalevent, Track parkingtrack){
		boolean parked = false;
		//if: A side arrival
		if ((arrivalevent.getSidestart() == 0 && arrivalevent.getReverseArrive() == 0) || (arrivalevent.getSidestart() == 1 && arrivalevent.getReverseArrive() == 1)){
			
		}
		//else if: B side arrival
		else if ((arrivalevent.getSidestart() == 1 && arrivalevent.getReverseArrive() == 0) || (arrivalevent.getSidestart() == 0 && arrivalevent.getReverseArrive() == 1)){
			
		}
		//else: methodfail
		else{
			throw new Exception; //TODO
		}
		return parked;
	}


	public void arrivalASide(Event arrivalevent, Track parkingtrack) throws TrackNotFreeException{
		arrivalevent.setEventTrack(parkingtrack);
		arrivalevent.getRelatedEvent().setEventTrack(parkingtrack);
		parkingtrack.addEventtoTrackLeft(arrivalevent);
		parkingtrack.addCompositiontoTrackLeft(arrivalevent.getEventblock());
	}

	public void arrivalBSide(Event arrivalevent, Track parkingtrack) throws TrackNotFreeException{
		arrivalevent.setEventTrack(parkingtrack);
		arrivalevent.getRelatedEvent().setEventTrack(parkingtrack);
		parkingtrack.addEventtoTrackRight(arrivalevent);
		parkingtrack.addCompositiontoTrackRight(arrivalevent.getEventblock());
	}

	public void departure(Event departureevent, int i) throws MethodFailException, TrackNotFreeException, IOException{
		//reverse leave
		//not reverse leave

		//if we leave in reverse
		if (departureevent.getReverseLeave() == 1){
			//reverseDeparture(departureevent, i);
		}
		else if (departureevent.getReverseLeave() == 0){
			//normalDeparture(departureevent, i);
		}
		else{
			throw new IOException("GetReverseLeave for event "+i+" not equal to 0 or 1");
		}
	}
	

	public ArrayList<Track> getParkingTracks(){
		return parkingtracks;
	}

	public ArrayList<Event> getTimeline(){
		return timeline;
	}
}

