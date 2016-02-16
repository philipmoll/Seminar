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
	private int[][] parkingpositions;
	private ArrayList<Parking> previousparkings;
	private ArrayList<Event> timeline;

	public Parking(ArrayList<Event> eventlist, Track[] tracks) throws MethodFailException, TrackNotFreeException{
		// input: eventlist met per compositie op welke tijd hij aankomt en weggaat en waarheen/waarvandaan, tracklist

		//take parktracks out of tracks and order by maxbackwardlength
		parkingtracks = new ArrayList<Track>();
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
					for (int j = 1; j< parkingtracks.size()-1; j++){
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


		//sort eventlist into timeline
		timeline = new ArrayList<Event>();
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
				for (int j = 1; j< timeline.size()-1; j++){
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

		for (int i = 0; i< timeline.size(); i++){
			if (int)
			for (int j = 0; j<parkingtracks.size(); j++){
				//check for the first track with maxdrivebacklength > size block if it fits on the correct side
				if (parkingtracks.get(j).getMaxDriveBackLength()>=timeline.get(i).getEventblock().getLength()){
					if (parkingtracks.get(j).getTracklength() - parkingtracks.get(j).getCompositionLengthOnTrack()>=timeline.get(i).getEventblock().getLength()){
						if (parkingtracks.get(j).getCompositionlist().size() == 0){
							parkingtracks.get(j).addCompositiontoTrackRight(timeline.get(i).getEventblock());
						}
						else if (timeline.get(i).get)
					}
				}
			}
		}
	}

}
