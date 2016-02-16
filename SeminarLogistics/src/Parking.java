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
	private Event[] timeline;

	public Parking(ArrayList<Event> eventlist, Track[] tracks) {
		// input: eventlist met per compositie op welke tijd hij aankomt en weggaat en waarheen/waarvandaan
		timeline = new Event[2*eventlist.size()];
		for (int i = 0; i<= timeline)
	}

}
