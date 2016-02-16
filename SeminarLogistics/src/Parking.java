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
	
	private Track[] parkingtracks;
	private int[][] parkingpositions;
	private ArrayList<Parking> previousparkings;
	private ArrayList<Event> timeline;

	public Parking(ArrayList<Event> eventlist, Track[] tracks) {
		// input: eventlist met per compositie op welke tijd hij aankomt en weggaat en waarheen/waarvandaan
		
		//sort eventlist into timeline of events
		timeline = new ArrayList<Event>();
		timeline.add(eventlist.get(0));
		if (eventlist.get(1).getTime() >= timeline.get(0).getTime()){
			timeline.add(eventlist.get(1));
		}
		else {
			timeline.add(0,eventlist.get(1));
		}
		for (int i = 2; i<= eventlist.size(); i++){
			if (eventlist.get(i).getTime()<timeline.get(0).getTime()){
				
			}
			for (int j = 1; j< timeline.size()-1; j++){
				if (eventlist.get(i).getTime()>=timeline.get(j).getTime() && eventlist.get(i).getTime() < timeline.get(j+1).getTime()){
					timeline.add(j+1,eventlist.get(i));
					break;
				}
			}
		}
	}

}
