import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ParkingTest {

	//tracks, events, blocks
	private Track track1;
	private Track track2;
	private Track track3;

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



	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		try {
			track1 = new Track("track1", 30, 1, 0, 0, 0, 0, 20);
			track2 = new Track("track2", 50, 1, 0, 0, 0, 0, 30);
			track3 = new Track("track3", 20, 1, 0, 0, 0, 0, 10);

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

			fblock1 = new FinalBlock(composition1.getTrainList(), composition1.getArrivaltime(), composition3.getDeparturetime(), composition1, composition3, -1, 1, -1, 1);
			fblock2 = new FinalBlock(composition2.getTrainList(), composition2.getArrivaltime(), composition4.getDeparturetime(), composition2, composition4, -1, 1, -1, 1);

			int endtime12 = 300;
			int starttime34 = 800;
			int endtime56 = 290;
			int starttime78 = 400;
			event1 = new Event(fblock1, 0, 1, fblock1.getArrivalTimeInteger(), endtime12, 1, 0, event2);
			event2 = new Event(fblock1, 1, 0, event1.getStarttime(), event1.getEndtime(), event1.getSidestart(), event1.getSideend(), event1);
			event3 = new Event(fblock1, 0, 0, starttime34, fblock1.getDepartureTimeInteger(), event2.getSideend(), 1, event4);
			event4 = new Event(fblock1, 1, 1, event3.getStarttime(), event3.getEndtime(), event3.getSidestart(), event3.getSideend(), event3);
			event5 = new Event(fblock2, 0, 1, fblock2.getArrivalTimeInteger(), endtime56, 1, 1, event6);
			event6 = new Event(fblock2, 1, 0, event5.getStarttime(), event5.getEndtime(), event5.getSidestart(), event5.getSideend(), event5);
			event7 = new Event(fblock2, 0, 0, starttime78, fblock2.getDepartureTimeInteger(), event6.getSideend(), 0, event8);
			event8 = new Event(fblock2, 1, 1, event7.getStarttime(), event7.getEndtime(), event7.getSidestart(), event7.getSideend(), event7);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
