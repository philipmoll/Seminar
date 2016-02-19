import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class ParkingTest {

	//tracks, events, blocks
	private Track track1;
	private Track track2;
	private Track track3;
	private Track track4;
	private Track[] alltracks;

	private Train train1;
	private Train train2;
	private Train train3;
	private Train train4;

	private Composition composition1;
	private Composition composition2;
	private Composition composition3;
	private Composition composition4;

	private FinalBlock fblock1;
	private FinalBlock fblock2;

	private Event event1;
	private Event event2;
	private Event event3;
	private Event event4;
	private Event event5;
	private Event event6;
	private Event event7;
	private Event event8;
	private ArrayList<Event> eventlist;

	private Parking park1;

	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		try {
			track1 = new Track("track1", 30, 1, 0, 0, 0, 0, 20);
			track2 = new Track("track2", 50, 1, 0, 0, 0, 0, 30);
			track3 = new Track("track3", 20, 1, 0, 0, 0, 0, 10);
			track4 = new Track("track4", 100, 0, 1, 1, 1, 1, 30);
			
			alltracks = new Track[4];
			alltracks[0] = track1;
			alltracks[1] = track2;
			alltracks[2] = track3;
			alltracks[3] = track4;
			
			train1 = new Train(01, 1, 4, 10);
			train2 = new Train(02, 1, 4, 10);
			train3 = new Train(03, 2, 4, 10);
			train4 = new Train(04, 2, 6, 20);

			double arrival1 = .1;
			double arrival2 = .2;
			double departure1 = .7;
			double departure2 = .6;

			composition1 = new Composition(new ArrayList<Train>(){{add(train1);add(train2);}}, arrival1, -1);
			composition2 = new Composition(new ArrayList<Train>(){{add(train3);add(train4);}}, arrival2, -1);
			composition3 = new Composition(new ArrayList<Train>(){{add(train1);add(train2);}}, -1, departure1);
			composition4 = new Composition(new ArrayList<Train>(){{add(train3);add(train4);}}, -1, departure2);

			fblock1 = new FinalBlock(composition1.getTrainList(), composition1.getArrivaltime(), composition3.getDeparturetime(), composition1, composition3, 1,1, -1, 1, -1, 1);
			fblock2 = new FinalBlock(composition2.getTrainList(), composition2.getArrivaltime(), composition4.getDeparturetime(), composition2, composition4, 1,0, -1, 1, -1, 1);

			int endtime12 = 300;
			int starttime34 = 800;
			int endtime56 = 295;
			int starttime78 = 400;
			
			event1 = new Event(fblock1, 0, 1, fblock1.getArrivalTimeInteger(), endtime12, 1, 0, event2);
			event2 = new Event(fblock1, 1, 0, event1.getStarttime(), event1.getEndtime(), event1.getSidestart(), event1.getSideend(), event1);
			event1.setRelatedEvent(event2);
			event3 = new Event(fblock1, 0, 0, starttime34, fblock1.getDepartureTimeInteger(), event2.getSideend(), 1, event4);
			event4 = new Event(fblock1, 1, 1, event3.getStarttime(), event3.getEndtime(), event3.getSidestart(), event3.getSideend(), event3);
			event3.setRelatedEvent(event4);
			event5 = new Event(fblock2, 0, 1, fblock2.getArrivalTimeInteger(), endtime56, 1, 1, event6);
			event6 = new Event(fblock2, 1, 0, event5.getStarttime(), event5.getEndtime(), event5.getSidestart(), event5.getSideend(), event5);
			event5.setRelatedEvent(event6);
			event7 = new Event(fblock2, 0, 0, starttime78, fblock2.getDepartureTimeInteger(), event6.getSideend(), 0, event8);
			event8 = new Event(fblock2, 1, 1, event7.getStarttime(), event7.getEndtime(), event7.getSidestart(), event7.getSideend(), event7);
			event7.setRelatedEvent(event8);
			
			eventlist = new ArrayList<Event>(){{add(event1);add(event2);add(event3);add(event4);add(event5);add(event6);add(event7);add(event8);}};
			
			park1 = new Parking(eventlist, alltracks);
//			ArrayList<Event> timeline = park1.getTimeline();
			
//			for (int i = 0; i<timeline.size(); i++){
//				System.out.println(timeline.get(i));
//			}

		} catch (IOException | MethodFailException | TrackNotFreeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSortTracks() {
		assertEquals(3,park1.getParkingTracks().size());
		assertEquals(track3,park1.getParkingTracks().get(0));
		assertEquals(track1,park1.getParkingTracks().get(1));
		assertEquals(track2,park1.getParkingTracks().get(2));
	}
	
	@Test
	public void testSortEvents() {
		assertEquals(8,park1.getTimeline().size());
		assertEquals(event1,park1.getTimeline().get(0));
		assertEquals(event5,park1.getTimeline().get(1));
		assertEquals(event6,park1.getTimeline().get(2));
		assertEquals(event2,park1.getTimeline().get(3));
		assertEquals(event7,park1.getTimeline().get(4));
		assertEquals(event3,park1.getTimeline().get(5));
		assertEquals(event8,park1.getTimeline().get(6));
		assertEquals(event4,park1.getTimeline().get(7));
	}
//	
//	@Test
//	public void testArrivalASideSimple() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testArrivalBSideSimple() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testArrivalNormal() { //changes when more functionality added
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testArrival() { //changes when more functionality added
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testNormalDeparture() { //changes when more functionality added
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testReverseDeparture() { //changes when more functionality added
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testDeparture() { //changes when more functionality added
//		fail("Not yet implemented");
//	}

}
