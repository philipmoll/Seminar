//SIMPLEST FORM, no couple/decouple, simple priority rules
//FINAL FORM

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

		//TODO: zetuit!!!
		ArrayList<Track> parkingtracks2 = new ArrayList<Track>();
		parkingtracks2 = (ArrayList<Track>) DeepCopy.copy(parkingtracks);
		parkingtracks.add(parkingtracks2.get(0));
		parkingtracks.add(parkingtracks2.get(1));
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


				}
				System.out.println();
				//		System.out.println(timeline.get(2).getEventblock().getCutpositionarr1());
				//		System.out.println(timeline.get(3).getEventblock().getCutpositionarr1());

				for (int i = 0; i< timeline.size(); i++){
					System.gc();
					System.out.println("event "+i+ " "+timeline.get(i));
					if (timeline.get(i).getType()==1){ //if it is a departure
						departure(timeline.get(i), i);
						System.out.println("Departure from track "+timeline.get(i).getEventTrack().getLabel()+" at time "+timeline.get(i).getTime());
						for (int x = 0; x<timeline.get(i).getEventTrack().getEventlist().size(); x++){
							System.out.println(timeline.get(i).getEventTrack().getEventlist().get(x)+" dep side: "+timeline.get(i).getEventTrack().getEventlist().get(x).getDepartureSide()+" arr time: "+timeline.get(i).getEventTrack().getEventlist().get(x).getStarttime()+" dep time: "+timeline.get(i).getEventTrack().getEventlist().get(x).getEndtime());
						}
					}
					else if (timeline.get(i).getType()==0) { //if it is an arrival
						boolean parked = arrival(timeline.get(i), i);
						System.out.println("Arrival at track "+timeline.get(i).getEventTrack().getLabel()+" at time "+timeline.get(i).getTime());
						if (parked){
						for (int x = 0; x<timeline.get(i).getEventTrack().getEventlist().size(); x++){
							System.out.println(timeline.get(i).getEventTrack().getEventlist().get(x)+" dep side: "+timeline.get(i).getEventTrack().getEventlist().get(x).getDepartureSide()+" dep time: "+timeline.get(i).getEventTrack().getEventlist().get(x).getEndtime());
						}
						}
						else {
							System.out.println("WARNING: Event "+i+", "+timeline.get(i)+" cannot be parked");
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
				if (parkingtrack.getEventlist().get(0).getEndtime()>checkevent.getEndtime()){
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
				if (parkingtrack.getEventlist().get(0).getEndtime()<checkevent.getEndtime()){
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
				if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime()<checkevent.getEndtime()){
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
				if (parkingtrack.getEventlist().get(0).getEndtime()>checkevent.getEndtime()){
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
		if (checkevent.getEventblock().getLength() < checkevent.getEventTrack().getMaxDriveBackLength()){
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

		//if we leave in reverse
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
}

