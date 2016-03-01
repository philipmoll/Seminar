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
	//private ArrayList<int[]> freetracktimes;

	public Parking(ArrayList<Event> eventlist, Track[] tracks) throws MethodFailException, TrackNotFreeException, IOException{
		// input: eventlist met per compositie op welke tijd hij aankomt en weggaat en waarheen/waarvandaan, tracklist


		//take parktracks out of tracks and order by maxbackwardlength
		parkingtracks = new ArrayList<Track>();
		sortTracks(tracks);

		//TODO: optional, ensures there is always a free track with enough drive back length
		parkingtracks.remove(parkingtracks.size()-1);

		//		for (int i = 0; i<parkingtracks.size(); i++){
		//			System.out.println("Label: "+parkingtracks.get(i).getLabel()+" and length: "+parkingtracks.get(i).getTracklength()+" and maxbackward: "+parkingtracks.get(i).getMaxDriveBackLength());
		//		}

		//sort eventlist into timeline
		timeline = new ArrayList<Event>();
		sortEvents(eventlist);
		for (int i = 0; i<timeline.size(); i++){
			System.out.println("i: "+i+" Event: "+timeline.get(i)+" Final block: "+timeline.get(i).getEventblock()+" finalevent: "+timeline.get(i).getFinalType()+" beginside: "+timeline.get(i).getSidestart()+" endside: "+timeline.get(i).getSideend()+" starttime this: "+timeline.get(i).getStarttime()+" starttime related: "+timeline.get(i).getRelatedEvent().getStarttime()+ " endtime this: "+timeline.get(i).getEndtime()+" endtime related: "+timeline.get(i).getRelatedEvent().getEndtime());
		}
		System.out.println();
		//		System.out.println(timeline.get(2).getEventblock().getCutpositionarr1());
		//		System.out.println(timeline.get(3).getEventblock().getCutpositionarr1());

		//TODO: optional
		//freetracktimes = new ArrayList<>();
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
				int nrincomposition = 1;
				ArrayList<Event> finalevents = new ArrayList<>();
				if (timeline.get(i).getFinalType()==1){ //if it is a final arrival
					finalevents.add(timeline.get(i));
					for (int j = 1; j<=2; j++){ //assumption: max 3 in one composition
						//						System.out.println("j: "+j+" finaltype: "+timeline.get(i+j).getFinalType()+" time_i: "+timeline.get(i).getTime()+" time_i+j: "+timeline.get(i+j).getTime()+" origin_i: "+timeline.get(i).getEventblock().getOrigincomposition()+" origin_i+j: "+timeline.get(i+j).getEventblock().getOrigincomposition());
						if (timeline.get(i+j).getFinalType()==1 && timeline.get(i).getTime() == timeline.get(i+j).getTime() /*&& timeline.get(i).getEventblock().getOrigincomposition()==timeline.get(i+j).getEventblock().getOrigincomposition()*/){ //if they are the same final arrivingcomposition
							nrincomposition++;
							finalevents.add(timeline.get(i+j));
						}
						else{
							break;
						}
					}
				}
				if (nrincomposition>1){ //final and nr greater than 1
					arrival(finalevents, i, nrincomposition);
					i = i+(nrincomposition-1); //skip all that have already been added
				}
				else{
					arrival(timeline.get(i), i);
					System.out.println("Arrival at track "+timeline.get(i).getEventTrack().getLabel());
				}
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

	//order from most occupied to least occupied
	public ArrayList<Track> sortTracksOccupied(ArrayList<Track> tracks) throws MethodFailException{
		ArrayList<Track> sortedtracks = new ArrayList<Track>();
		sortedtracks.add(tracks.get(0));
		if (tracks.get(1).getCompositionlist().size()<= sortedtracks.get(0).getCompositionlist().size()){
			sortedtracks.add(tracks.get(1));
		}
		else {
			sortedtracks.add(0,tracks.get(1));
		}
		for (int i = 2; i<tracks.size(); i++){
			if (tracks.get(i).getCompositionlist().size()>sortedtracks.get(0).getCompositionlist().size()){
				sortedtracks.add(0,tracks.get(i));
			}
			else if (tracks.get(i).getCompositionlist().size()<=sortedtracks.get(sortedtracks.size()-1).getCompositionlist().size()){
				sortedtracks.add(tracks.get(i));
			}
			else{
				for (int k = 0; k<sortedtracks.size()-1; k++){
					if (tracks.get(i).getCompositionlist().size()<=sortedtracks.get(k).getCompositionlist().size() && tracks.get(i).getCompositionlist().size()>sortedtracks.get(k+1).getCompositionlist().size()){
						sortedtracks.add(k+1,tracks.get(i));
					}
				}
			}
		}
		//throw exception if sortedtracks ordered incorrectly
		for (int i = 0; i<sortedtracks.size()-1; i++){
			if (sortedtracks.get(i).getCompositionlist().size() < sortedtracks.get(i+1).getCompositionlist().size()){
				throw new MethodFailException("Occupied track ordering in parking failed at position i = "+i+": sortedtracks.get(i).getCompositionlist().size() = "+sortedtracks.get(i).getCompositionlist().size()+" and sortedtracks.get(i+1).getCompositionlist().size() = "+sortedtracks.get(i+1).getCompositionlist().size());
			}
		}
		if (sortedtracks.size() != tracks.size()){
			throw new MethodFailException("Tracks contains a different number of elements than sorted tracks in function sortTracksOccupied in Parking");
		}
		return sortedtracks;		
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

	public void arrival(Event arrivalevent, int i) throws TrackNotFreeException, MethodFailException, IOException{
		boolean parked = arrivalNormal(arrivalevent, i);
		if (parked == false){
			//			parked = arrivalReverse(arrivalevent, i);
			//			//TODO: try arrival reverse, only possible if not only one track free left and track need not to be free at time of event, and of 
		}
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
			ArrayList<Track> trackssortedoccupied = sortTracksOccupied(parkingtracks);
			for (int j = 0; j<trackssortedoccupied.size(); j++){
				if (trackssortedoccupied.get(j).getMaxDriveBackLength()<arrivalevent.getEventblock().getLength()){
					parked = simplePark(arrivalevent, trackssortedoccupied.get(j), i);
				}
				if (parked == true){
					break;
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

	//	public boolean arrivalReverse(Event arrivalevent, int i) throws TrackNotFreeException, IOException, MethodFailException{
	//		boolean parked = false;
	//		for (int j = 0; j<parkingtracks.size(); j++){
	//			//check for the first track with maxdrivebacklength > size block if it fits on the correct side
	//			//if: block can drive back at track
	//			if (parkingtracks.get(j).getMaxDriveBackLength()>=arrivalevent.getEventblock().getLength()){
	//				parked = reversePark(arrivalevent, parkingtracks.get(j), i);
	//			}
	//			if (parked == true){
	//				break;
	//			}
	//		}
	//		if (!parked){
	//			System.out.println("Arrival "+i+" cannot be parked in reverse");
	//		}
	//		else {
	//			System.out.println("Event "+i+" is parked at track "+arrivalevent.getEventTrack().getLabel());
	//		}
	//		return parked;
	//	}

	public boolean simplePark(Event arrivalevent, Track parkingtrack, int i) throws TrackNotFreeException, IOException{
		boolean parked = false;
		//if: enough room on track
		if (parkingtrack.getTracklength() - parkingtrack.getCompositionLengthOnTrack()>=arrivalevent.getEventblock().getLength()){
			//if: no compositions on track yet
			if (parkingtrack.getCompositionlist().size() == 0){
				//add compositions and events to track
				arrivalASide(arrivalevent, parkingtrack);
				parked = true; //set parked to true
			}
			//else: track not empty
			else{
				//if: we need to enter at the A side
				if (arrivalevent.getSidestart() == 0){ //A side (left side)
					//if: we need to leave via the A side
					if (arrivalevent.getSideend() == 0){ //A side (left side)
						//if: the eventblock to the right leaves via the A side (either directly or in reverse)
						if ((parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() == 0) || (parkingtrack.getEventlist().get(0).getSideend() == 1 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){ //A side (left side)
							//if: the eventblock to the right leaves after arrivalblock
							if (parkingtrack.getEventlist().get(0).getEndtime() > arrivalevent.getEndtime()){
								//add compositions and events to track
								arrivalASide(arrivalevent, parkingtrack);
								parked = true; //set parked to true
							}
							//else: the eventblock to the right leaves before arrivalblcok
							else{
								//if: it is the only one on the track
								if (parkingtrack.getEventlist().size()<=1){
									//if: it can drive back, set it to reverse leave and park
									if (parkingtrack.getEventlist().get(0).getEventblock().getLength() <= parkingtrack.getMaxDriveBackLength()){
										//TODO: if reverseleave is not true yet
										parkingtrack.getEventlist().get(0).setReverseLeave(1);
										parkingtrack.getEventlist().get(0).getRelatedEvent().setReverseLeave(1);
										arrivalASide(arrivalevent,parkingtrack);
										parked = true; //set parked to true
									}
								}
								else{
									//else: it is not the only one on the track
									boolean feasible = false;
									for (int j = 1; j<parkingtrack.getEventlist().size(); j++){
										//if: the others leave via the right (either directly or in reverse)
										if ((parkingtrack.getEventlist().get(j).getSideend()==0 && parkingtrack.getEventlist().get(j).getReverseLeave()==1) || (parkingtrack.getEventlist().get(j).getSideend()==1 && parkingtrack.getEventlist().get(j).getReverseLeave()==0)){
											//if: the others leave via the right, after the eventblock to the right
											if (parkingtrack.getEventlist().get(j).getEndtime()>parkingtrack.getEventlist().get(0).getEndtime()){
												feasible = false;
												break;
											}
											//else: the others leave via the right, before the eventblock to the right
											else{
												feasible = true;
											}
										}
										//else: the others leave via the left
										else{
											feasible = false;
											break;
										}
									}
									if (feasible){
										//if: it can drive back, set it to reverse leave and park
										if (parkingtrack.getEventlist().get(0).getEventblock().getLength() <= parkingtrack.getMaxDriveBackLength()){
											//TODO: if reverse leave is not true yet
											parkingtrack.getEventlist().get(0).setReverseLeave(1);
											parkingtrack.getEventlist().get(0).getRelatedEvent().setReverseLeave(1);
											arrivalASide(arrivalevent,parkingtrack);
											parked = true; //set parked to true
										}
									}
								}
							}
						}
						//else if: the eventblock to the right leaves via the B side (either directly or in reverse
						else if ((parkingtrack.getEventlist().get(0).getSideend() ==1&& parkingtrack.getEventlist().get(0).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){ //B side (right side)
							//add compositions and events to track
							arrivalASide(arrivalevent, parkingtrack);
							parked = true; //set parked to true
						}
						//else: IOException
						else{
							throw new IOException("Eventblock at A side of track "+parkingtrack.getLabel()+" has side end "+parkingtrack.getEventlist().get(0).getSideend()+" and should be 0 (A side) or 1 (B side)");
						}
					}
					//else if: we need to leave via the B side
					else if (arrivalevent.getSideend()==1){ //B side (right side)
						//if: the eventblock to the right leaves via the A side (NB: A side not possible here, earlier or later)
						if ((parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() == 0) || (parkingtrack.getEventlist().get(0).getSideend() == 1 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){
							//if: it is the only one on the track
							if (parkingtrack.getEventlist().size()<=1){
								//if: it can drive back and departs earlier, set it to reverse leave and park
								if (parkingtrack.getEventlist().get(0).getEventblock().getLength() <= parkingtrack.getMaxDriveBackLength() && parkingtrack.getEventlist().get(0).getEndtime()<= arrivalevent.getEndtime()){
									//TODO: if reverseleave is not true yet
									parkingtrack.getEventlist().get(0).setReverseLeave(1);
									parkingtrack.getEventlist().get(0).getRelatedEvent().setReverseLeave(1);
									arrivalASide(arrivalevent,parkingtrack);
									parked = true; //set parked to true
								}
							}
							else{
								//else: it is not the only one on the track
								boolean feasible = false;
								for (int j = 1; j<parkingtrack.getEventlist().size(); j++){
									//if: the others leave via the right (either directly or in reverse)
									if ((parkingtrack.getEventlist().get(j).getSideend()==0 && parkingtrack.getEventlist().get(j).getReverseLeave()==1) || (parkingtrack.getEventlist().get(j).getSideend()==1 && parkingtrack.getEventlist().get(j).getReverseLeave()==0)){
										//if: the others leave via the right, after the eventblock to the right
										if (parkingtrack.getEventlist().get(j).getEndtime()>parkingtrack.getEventlist().get(0).getEndtime()){
											feasible = false;
											break;
										}
										//else: the others leave via the right, before the eventblock to the right
										else{
											feasible = true;
										}
									}
									//else: the others leave via the left
									else{
										feasible = false;
										break;
									}
								}
								if (feasible){
									//if: it can drive back, set it to reverse leave and park
									if (parkingtrack.getEventlist().get(0).getEventblock().getLength() <= parkingtrack.getMaxDriveBackLength()){
										//TODO: if reverse leave is not true yet
										parkingtrack.getEventlist().get(0).setReverseLeave(1);
										parkingtrack.getEventlist().get(0).getRelatedEvent().setReverseLeave(1);
										arrivalASide(arrivalevent,parkingtrack);
										parked = true; //set parked to true
									}
								}
							}
						}
						//else if: the eventblock to the right leaves via the B side (either directly or in reverse)
						else if ((parkingtrack.getEventlist().get(0).getSideend() ==1&& parkingtrack.getEventlist().get(0).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){ //B side (right side)
							//if: the eventblock to the right leaves before arrivalblock
							if (parkingtrack.getEventlist().get(0).getEndtime() < arrivalevent.getEndtime()){
								//add compositions and events to track
								arrivalASide(arrivalevent, parkingtrack);
								parked = true; //set parked to true
							}
						}
						//else: IOException
						else{
							throw new IOException("Eventblock at A side of track "+parkingtrack.getLabel()+" has side end "+parkingtrack.getEventlist().get(0).getSideend()+" and should be 0 (A side) or 1 (B side)");
						}
					}
					//else: IOException
					else {
						throw new IOException("Side end of event "+i+" is "+arrivalevent.getSideend()+ " and should be 0 (A side) or 1 (B side)");
					}
				}
				//else if: we need to enter at the B side
				else if (arrivalevent.getSidestart()==1){ //B side (right side)
					//if: we need to leave via the B side
					if (arrivalevent.getSideend() == 1){ //B side (right side)
						//if: the eventblock to the left leaves via the B side (either directly or in reverse)
						if ((parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend() ==1&& parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend() == 0 && parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() ==1)){ //B side (right side)
							//if: the eventblock to the left leaves after arrivalblock
							if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime() > arrivalevent.getEndtime()){
								//add compositions and events to track
								arrivalBSide(arrivalevent, parkingtrack);
								parked = true; //set parked to true
							}
							//else: the eventblock to the left leaves before arrivalblcok
							else{
								//if: it is the only one on the track
								if (parkingtrack.getEventlist().size()<=1){
									//if: it can drive back, set it to reverse leave and park
									if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEventblock().getLength() <= parkingtrack.getMaxDriveBackLength()){
										//TODO: if reverseleave is not true yet
										parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).setReverseLeave(1);
										parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getRelatedEvent().setReverseLeave(1);
										arrivalBSide(arrivalevent,parkingtrack);
										parked = true; //set parked to true
									}
								}
								else{
									//else: it is not the only one on the track
									boolean feasible = false;
									for (int j = 0; j<parkingtrack.getEventlist().size()-1; j++){
										//if: the others leave via the left (either directly or in reverse)
										if ((parkingtrack.getEventlist().get(j).getSideend()==0 && parkingtrack.getEventlist().get(j).getReverseLeave()==0) || (parkingtrack.getEventlist().get(j).getSideend()==1 && parkingtrack.getEventlist().get(j).getReverseLeave()==1)){
											//if: the others leave via the right, after the eventblock to the right
											if (parkingtrack.getEventlist().get(j).getEndtime()>parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime()){
												feasible = false;
												break;
											}
											//else: the others leave via the right, before the eventblock to the right
											else{
												feasible = true;
											}
										}
										//else: the others leave via the left
										else{
											feasible = false;
											break;
										}
									}
									if (feasible){
										//if: it can drive back, set it to reverse leave and park
										if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEventblock().getLength() <= parkingtrack.getMaxDriveBackLength()){
											//TODO: if reverse leave is not true yet
											parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).setReverseLeave(1);
											parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getRelatedEvent().setReverseLeave(1);
											arrivalASide(arrivalevent,parkingtrack);
											parked = true; //set parked to true
										}
									}
								}
							}
						}
						//else if: the eventblock to the left leaves via the A side (either directly or in reverse)
						else if ((parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() == 0) || (parkingtrack.getEventlist().get(0).getSideend() == 1 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){
							//add compositions and events to track
							arrivalBSide(arrivalevent, parkingtrack);
							parked = true; //set parked to true
						}
						//else: IOException
						else{
							throw new IOException("Eventblock at B side of track "+parkingtrack.getLabel()+" has side end "+parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend()+" and should be 0 (A side) or 1 (B side)");
						}
					}

					//TODO: HIERGEBLEVEN
					//else if: we need to leave via the A side
					else if (arrivalevent.getSideend()==0){ //A side (left side)
						//if: the eventblock to the right leaves via the B side (either directly or in reverse) (NB: B side not possible here, earlier or later)
						if ((parkingtrack.getEventlist().get(0).getSideend() ==1&& parkingtrack.getEventlist().get(0).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){ //B side (right side)
							//if: the eventblock to the left leaves after arrivalblock
							if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime() > arrivalevent.getEndtime()){
								//add compositions and events to track
								arrivalBSide(arrivalevent, parkingtrack);
								parked = true; //set parked to true
							}
							//else: the eventblock to the left leaves before arrivalblcok
							else{
								//if: it is the only one on the track
								if (parkingtrack.getEventlist().size()<=1){
									//if: it can drive back, set it to reverse leave and park
									if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEventblock().getLength() <= parkingtrack.getMaxDriveBackLength()){
										//TODO: if reverseleave is not true yet
										parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).setReverseLeave(1);
										parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getRelatedEvent().setReverseLeave(1);
										arrivalBSide(arrivalevent,parkingtrack);
										parked = true; //set parked to true
									}
								}
								else{
									//else: it is not the only one on the track
									boolean feasible = false;
									for (int j = 0; j<parkingtrack.getEventlist().size()-1; j++){
										//if: the others leave via the left (either directly or in reverse)
										if ((parkingtrack.getEventlist().get(j).getSideend()==0 && parkingtrack.getEventlist().get(j).getReverseLeave()==0) || (parkingtrack.getEventlist().get(j).getSideend()==1 && parkingtrack.getEventlist().get(j).getReverseLeave()==1)){
											//if: the others leave via the right, after the eventblock to the right
											if (parkingtrack.getEventlist().get(j).getEndtime()>parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime()){
												feasible = false;
												break;
											}
											//else: the others leave via the right, before the eventblock to the right
											else{
												feasible = true;
											}
										}
										//else: the others leave via the left
										else{
											feasible = false;
											break;
										}
									}
									if (feasible){
										//if: it can drive back, set it to reverse leave and park
										if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEventblock().getLength() <= parkingtrack.getMaxDriveBackLength()){
											//TODO: if reverse leave is not true yet
											parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).setReverseLeave(1);
											parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getRelatedEvent().setReverseLeave(1);
											arrivalASide(arrivalevent,parkingtrack);
											parked = true; //set parked to true
										}
									}
								}
							}
						}
						//else if: the eventblock to the right leaves via the A side (either directly or in reverse)
						else if ((parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() == 0) || (parkingtrack.getEventlist().get(0).getSideend() == 1 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){
							//if: the eventblock to the right leaves before arrivalblock
							if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime() <= arrivalevent.getEndtime()){
								//add compositions and events to track
								arrivalBSide(arrivalevent, parkingtrack);
								parked = true; //set parked to true
							}
						}
						//else: IOException
						else{
							throw new IOException("Eventblock at B side of track "+parkingtrack.getLabel()+" has side end "+parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend()+" and should be 0 (A side) or 1 (B side)");
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
		return parked;
	}

	//	public boolean reversePark(Event arrivalevent, Track parkingtrack, int i) throws TrackNotFreeException, IOException{
	//		boolean parked = false;
	//		//if: enough room on track
	//		if (parkingtrack.getTracklength() - parkingtrack.getCompositionLengthOnTrack()>=arrivalevent.getEventblock().getLength()){
	//			//track cannot be empty
	//			//if: we need to enter at the A side
	//			if (arrivalevent.getSidestart() == 0){ //A side (left side)
	//				//if: we need to leave via the A side
	//				if (arrivalevent.getSideend() == 0){ //A side (left side)
	//					//CHECK ONLY BOTH REVERSE ARRIVE AND DEPARTURE, SINCE ONLY ONE OF TWO DOES NOT MAKE SENSE (see notes Floor)
	//					//we try reverse arrival, via the B side, and reverse departure, via the B side
	//					//if: the eventblock to the left leaves via the A side (either directly or in reverse)
	//					if ((parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend() == 0 && parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() == 0) || (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend() == 1 && parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() ==1)){ //A side (left side)
	//						//add compositions and events to track, set reverse arrive and reverse leave to 1 for this and related
	//						arrivalBSide(arrivalevent, parkingtrack);
	//						arrivalevent.setReverseArrive(1);
	//						arrivalevent.getRelatedEvent().setReverseLeave(1);
	//						parked = true; //set parked to true
	//					}
	//					//else if: the eventblock to the left leaves via the B side (either directly or in reverse)
	//					else if ((parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend() ==1&& parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend() == 0 && parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() ==1)){ //B side (right side)
	//						//if: the eventblock to the left leaves after arrivalblock
	//						if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime() >= arrivalevent.getEndtime()){
	//							//add compositions and events to track
	//							arrivalBSide(arrivalevent, parkingtrack);
	//							arrivalevent.setReverseArrive(1);
	//							arrivalevent.getRelatedEvent().setReverseLeave(1);
	//							parked = true; //set parked to true
	//						}
	//					}
	//					//else: IOException
	//					else{
	//						throw new IOException("Eventblock at B side of track "+parkingtrack.getLabel()+" has side end "+parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend()+" and should be 0 (A side) or 1 (B side)");
	//					}
	//				}
	//				//else if: we need to leave via the B side
	//				else if (arrivalevent.getSideend()==1){ //B side (right side)
	//					//try: reverse arrive (B side)
	//					//if eventblock to the left leaves via the A side (either directly or in reverse)
	//					if ((parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend() == 0 && parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() == 0) || (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend() == 1 && parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() ==1)){ //A side (left side)
	//						arrivalBSide(arrivalevent, parkingtrack);
	//						arrivalevent.setReverseArrive(1);
	//						parked = true; //set parked to true
	//					}
	//					//if eventblock to the left leaves via the B side (either directly or in reverse
	//					else if ((parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend() ==1&& parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend() == 0 && parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() ==1)){ //B side (right side)
	//						//if: the eventblock to the left leaves after arrivalblock
	//						if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime() >= arrivalevent.getEndtime()){
	//							//add compositions and events to track in reverse arrive
	//							arrivalBSide(arrivalevent, parkingtrack);
	//							arrivalevent.setReverseArrive(1);
	//							parked = true; //set parked to true
	//						}
	//						//else: the eventblock to the left leaves before arrivalblock
	//						else {
	//							//no parking possible in reverse arrive
	//							//TODO: try reverse eventblock to the left (see code at the bottom)
	//						}
	//					}
	//					//else: IO Exception
	//					else{
	//						throw new IOException("nog invullen"); //TODO: nog invullen
	//					}
	////					//if not parked yet:
	////					if (parked == false){
	////						//try: reverse depart (A side)
	////						//if: eventblock to the right leaves via the B side (either directly or in reverse)
	////						if ((parkingtrack.getEventlist().get(0).getSideend() ==1&& parkingtrack.getEventlist().get(0).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){ //B side (right side)
	////							arrivalASide(arrivalevent, parkingtrack);
	////							arrivalevent.setReverseArrive(1);
	////							parked = true; //set parked to true
	////						}
	////						//else if: eventblock to the right leaves via the A side (either directly or in reverse)
	////						else if ((parkingtrack.getEventlist().get(0).getSideend() ==0&& parkingtrack.getEventlist().get(0).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(0).getSideend() == 1 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){ //A side (left side)
	////							//if: the eventblock to the left leaves after arrivalblock
	////							if (parkingtrack.getEventlist().get(0).getEndtime() >= arrivalevent.getEndtime()){
	////								arrivalASide(arrivalevent, parkingtrack);
	////								arrivalevent.setReverseArrive(1);
	////								parked = true; //set parked to true
	////							}
	////							//else: the eventblock to the right leaves before arrivablock
	////							else {
	////								//no parking possible in reverse arrive
	////								//TODO: try reverse eventblock to the left (see code at the bottom)
	////							}
	////						}
	////						//else: IO Exception
	////						else{
	////							throw new IOException("nog invullen"); //TODO: nog invullen
	////						}
	////					}
	//
	//
	//					//if: the eventblock to the right leaves via the A side
	//					if ((parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() == 0) || (parkingtrack.getEventlist().get(0).getSideend() == 1 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){
	//						//cannot park here!
	//					}
	//					//else if: the eventblock to the right leaves via the B side (either directly or in reverse)
	//					else if ((parkingtrack.getEventlist().get(0).getSideend() ==1&& parkingtrack.getEventlist().get(0).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){ //B side (right side)
	//						//if: the eventblock to the right leaves before arrivalblock
	//						if (parkingtrack.getEventlist().get(0).getEndtime() <= arrivalevent.getEndtime()){
	//							//add compositions and events to track
	//							arrivalASide(arrivalevent, parkingtrack);
	//							parked = true; //set parked to true
	//						}
	//					}
	//					//else: IOException
	//					else{
	//						throw new IOException("Eventblock at A side of track "+parkingtrack.getLabel()+" has side end "+parkingtrack.getEventlist().get(0).getSideend()+" and should be 0 (A side) or 1 (B side)");
	//					}
	//				}
	//				//else: IOException
	//				else {
	//					throw new IOException("Side end of event "+i+" is "+arrivalevent.getSideend()+ " and should be 0 (A side) or 1 (B side)");
	//				}
	//			}
	//			//else if: we need to enter at the B side
	//			else if (arrivalevent.getSidestart()==1){ //B side (right side)
	//				//if: we need to leave via the B side
	//				if (arrivalevent.getSideend() == 1){ //B side (right side)
	//					//if: the eventblock to the left leaves via the B side (either directly or in reverse)
	//					if ((parkingtrack.getEventlist().get(0).getSideend() ==1&& parkingtrack.getEventlist().get(0).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){ //B side (right side)
	//						//if: the eventblock to the left leaves after arrivalblock
	//						if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime() >= arrivalevent.getEndtime()){
	//							//add compositions and events to track
	//							arrivalBSide(arrivalevent, parkingtrack);
	//							parked = true; //set parked to true
	//						}
	//					}
	//					//else if: the eventblock to the left leaves via the A side (either directly or in reverse)
	//					else if ((parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() == 0) || (parkingtrack.getEventlist().get(0).getSideend() == 1 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){
	//						//add compositions and events to track
	//						arrivalBSide(arrivalevent, parkingtrack);
	//						parked = true; //set parked to true
	//					}
	//					//else: IOException
	//					else{
	//						throw new IOException("Eventblock at B side of track "+parkingtrack.getLabel()+" has side end "+parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend()+" and should be 0 (A side) or 1 (B side)");
	//					}
	//				}
	//				//else if: we need to leave via the A side
	//				else if (arrivalevent.getSideend()==0){ //A side (left side)
	//					//if: the eventblock to the right leaves via the B side (either directly or in reverse) (NB: B side not possible here, earlier or later)
	//					if ((parkingtrack.getEventlist().get(0).getSideend() ==1&& parkingtrack.getEventlist().get(0).getReverseLeave() == 0)|| (parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){ //B side (right side)
	//						//cannot park here!
	//					}
	//					//else if: the eventblock to the right leaves via the A side (either directly or in reverse)
	//					else if ((parkingtrack.getEventlist().get(0).getSideend() == 0 && parkingtrack.getEventlist().get(0).getReverseLeave() == 0) || (parkingtrack.getEventlist().get(0).getSideend() == 1 && parkingtrack.getEventlist().get(0).getReverseLeave() ==1)){
	//						//if: the eventblock to the right leaves before arrivalblock
	//						if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime() <= arrivalevent.getEndtime()){
	//							//add compositions and events to track
	//							arrivalBSide(arrivalevent, parkingtrack);
	//							parked = true; //set parked to true
	//						}
	//					}
	//					//else: IOException
	//					else{
	//						throw new IOException("Eventblock at B side of track "+parkingtrack.getLabel()+" has side end "+parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getSideend()+" and should be 0 (A side) or 1 (B side)");
	//					}
	//				}
	//				//else: IOException
	//				else {
	//					throw new IOException("Side end of event "+i+" is "+arrivalevent.getSideend()+ " and should be 0 (A side) or 1 (B side)");
	//				}
	//			}
	//			//else: IOException
	//			else{
	//				throw new IOException("Side start of event "+i+" is "+arrivalevent.getSidestart()+" and should be 0 (A side) or 1 (B side)");
	//			}
	//		}
	//		return parked;
	//	}

	public void arrival(ArrayList<Event> arrivalevents, int i, int nrevents) throws TrackNotFreeException, IOException, MethodFailException{ //for final arrival
		boolean parked = arrivalNormal(arrivalevents, i, nrevents);
		if (parked == false){
			//TODO: try arrival reverse, only possible if not only one track free left and track need not to be free at time of event, and of 
		}
	}

	public boolean arrivalNormal(ArrayList<Event> arrivalevents1, int i, int nrevents) throws TrackNotFreeException, IOException, MethodFailException{
		boolean parked = false;
		int totallength = 0;
		for (int j = 0; j<nrevents; j++){
			totallength += arrivalevents1.get(j).getEventblock().getLength();
		}
		ArrayList<Event> arrivalevents = new ArrayList<>();
		//if A side arrival, sort from end to beginning
		if (arrivalevents1.get(0).getSidestart()==0){
			if (nrevents == 2){
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
				throw new MethodFailException("Nrevents is "+nrevents+" and can only be 2 or 3 (i = "+i+")");
			}
		}
		//else if B side arrival, sort from beginning to end
		else if (arrivalevents1.get(0).getSidestart()==1){
			if (nrevents == 2){
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
		}
		//else methodfail exception
		else{
			throw new MethodFailException("Sidestart of event is "+arrivalevents1.get(0)+" and can only be 0 or 1 (A or B, respectively)");
		}

		for (int j = 0; j<arrivalevents.size();j++){
			System.out.println(arrivalevents.get(j));
		}
		for (int j = 0; j<parkingtracks.size(); j++){
			//check for the first track with maxdrivebacklength > size block if it fits on the correct side
			//if: entire composition can drive back at track
			if (parkingtracks.get(j).getMaxDriveBackLength()>=totallength){
				for (int k = 0; k < nrevents; k++){
					parked = simplePark(arrivalevents.get(k), parkingtracks.get(j), i);
					//if one of them cannot be parked, remove previous actions
					if (parked == false){
						for (int l = 0; l<k; l++){
							arrivalevents.get(l).setEventTrack(null);
							arrivalevents.get(l).getRelatedEvent().setEventTrack(null);
							parkingtracks.get(j).removeEventfromTrack(arrivalevents.get(l));
							parkingtracks.get(j).removeCompositionfromTrack(arrivalevents.get(l).getEventblock());			
						}
						break; //continue on to next track
					}
				}
			}
			if (parked == true){
				break;
			}
		}
		//if: parking failed, try to park at the most occupied track that we cannot drive back on
		if (!parked){
			ArrayList<Track> trackssortedoccupied = sortTracksOccupied(parkingtracks);
			for (int j = 0; j<trackssortedoccupied.size(); j++){
				if (trackssortedoccupied.get(j).getMaxDriveBackLength()<totallength){
					for (int k = 0; k < nrevents; k++){
						parked = simplePark(arrivalevents.get(k), parkingtracks.get(j), i);
						if (parked == false){
							//if one of them cannot be parked, remove previous actions
							for (int l = 0; l<k; l++){
								arrivalevents.get(l).setEventTrack(null);
								arrivalevents.get(l).getRelatedEvent().setEventTrack(null);
								parkingtracks.get(j).removeEventfromTrack(arrivalevents.get(l));
								parkingtracks.get(j).removeCompositionfromTrack(arrivalevents.get(l).getEventblock());			
							}
							break; //continue on to next track
						}
					}
				}
				if (parked == true){
					break;
				}
			}
		}
		if (!parked){
			System.out.println("Arrival "+i+" and associated finals cannot be parked simply");
		}
		else {
			System.out.println("Event "+i+" and associated finals are parked at track "+arrivalevents.get(0).getEventTrack().getLabel());
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
		//TODO: check if we still want to work with freetrack time arraylist

		//		//throw exception if free track needed is false (we leave in reverse, so free track should be toggled)
		//		if (freetracktimes.size() == 0){
		//			throw new MethodFailException("Freetracktime is empty for event "+i+" but we require it to leave in reverse so should be larger than 1");
		//		}
		//		else{
		//			boolean check = false;
		//			for (int j = 0; j<freetracktimes.size(); j++){
		//				if (departureevent.getTime() == freetracktimes.get(j)[1]){ //if departure time coincides with freetracktime //TODO: check for final events!!!!!!
		//					check = true;
		//					freetracktimes.remove(j);
		//					break;
		//				}
		//			}
		//			if (!check){
		//				throw new MethodFailException("Freetracktime is not defined for event "+i+" at departuretime "+departureevent.getTime());
		//			}
		//		}
		//
		//		//throw exception if no free track available or if it is the only event on the track
		//		if (departureevent.getEventTrack().getCompositionlist().size() == 0){
		//			throw new MethodFailException("We require event "+i+" to leave in reverse, but it is the only event on the track");
		//		}
		//		boolean available = false; //set correct freetrack available to false
		//		for (int k = 0; k<parkingtracks.size(); k++){
		//			if (parkingtracks.get(k).getCompositionlist().size() == 0){//free track available
		//				//if the free track is at a lower position
		//				if (parkingtracks.get(k).getMaxDriveBackLength()>=departureevent.getEventTrack().getMaxDriveBackLength()){
		//					available = true;
		//					break;
		//				}
		//				//if the free track is higher, but has enough max drive back length
		//				else if (parkingtracks.get(k).getMaxDriveBackLength()>= departureevent.getEventblock().getLength()){
		//					available = true;
		//					break;
		//				}
		//			}
		//		}
		//		if (available == false){ // if no appropriate free track available, throw exception
		//			throw new MethodFailException("No free track available and event "+i+" must leave in reverse");
		//		}

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
				System.out.println("Event track: "+departureevent.getEventTrack().getLabel());
				for (int j = 0; j<departureevent.getEventTrack().getCompositionlist().size(); j++){
					System.out.println(departureevent.getEventTrack().getCompositionlist().get(j));
				}
				throw new MethodFailException("Event "+i+" with eventblock "+departureevent.getEventblock()+" must leave via A side at track "+departureevent.getEventTrack().getLabel()+", but is not at the A side");
			}
		}
		else if (departureevent.getSideend() == 1){ //if we leave via B side
			//throw exception if eventblock not at the B side of the track
			if (departureevent.getEventTrack().getCompositionlist().get(departureevent.getEventTrack().getCompositionlist().size()-1) != departureevent.getEventblock()){
				throw new MethodFailException("Event "+i+" must leave via B side at track "+departureevent.getEventTrack().getLabel()+", but is not at the B side");
			}
		}
		else { //sideend is neither 0 nor 1, IOException!
			throw new IOException("Sideend of event "+i+" is "+timeline.get(i).getSideend()+" and should be 0 or 1");
		}

		//we can leave, now actually leave
		//remove event from track, remove composition from track
		departureevent.getEventTrack().removeCompositionfromTrack(departureevent.getEventblock());
		departureevent.getRelatedEvent().getEventTrack().removeEventfromTrack(departureevent.getRelatedEvent());		
	}

	public ArrayList<Track> getParkingTracks(){
		return parkingtracks;
	}

	public ArrayList<Event> getTimeline(){
		return timeline;
	}
}

/*
//if: enough room for eventblock to the left to drive back
if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEventblock().getLength() <= parkingtrack.getMaxDriveBackLength()){
	//if: eventblock to the left can leave via the A side in reverse because it is the only eventblock on the track
	if (parkingtrack.getEventlist().size()<=1){
		if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() == 0) {
			parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).setReverseLeave(1);
		}
		else {
			parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).setReverseLeave(0);
		}
		arrivalBSide(arrivalevent,parkingtrack);
		arrivalevent.setReverseArrive(1);
	}
	//else: it is not the only eventblock on the track
	else {
		boolean feasible = false;
		for (int j = 0; j<parkingtrack.getEventlist().size()-1; i++){
			//if other eventblocks on the track leave via the B side, feasible is false
			if ((parkingtrack.getEventlist().get(j).getSideend() == 1 && parkingtrack.getEventlist().get(j).getReverseLeave()==0)||((parkingtrack.getEventlist().get(j).getSideend() == 0 && parkingtrack.getEventlist().get(j).getReverseLeave()==1))){
				feasible = false;
				break;
			}
			//else if other eventblocks on the track leave via the A side, but later than the eventblock to the left
			else{
				if (parkingtrack.getEventlist().get(j).getEndtime()<=parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getEndtime()){
					feasible = true;
				}
				else{
					feasible = false;
				}
			}
		}
		//if possible, set event to the left and current on reverse
		if (feasible == true){
			if (parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).getReverseLeave() == 0) {
				parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).setReverseLeave(1);
			}
			else {
				parkingtrack.getEventlist().get(parkingtrack.getEventlist().size()-1).setReverseLeave(0);
			}
			arrivalBSide(arrivalevent,parkingtrack);
			arrivalevent.setReverseArrive(1);
		}
	}
}
 */