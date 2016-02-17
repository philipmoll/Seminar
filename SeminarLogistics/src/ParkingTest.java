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
	
	 

	@Before
	public void setUp() {
		
		track1 = new Track("track1", length, 1, 0, 0, 0, 0, backwardlength);
		track2 = new Track("track2", length, 1, 0, 0, 0, 0, backwardlength);
		track3 = new Track("track3", length, 1, 0, 0, 0, 0, backwardlength);
		
		train1 = new Train(01, 1, 4, length);
		train2 = new Train(02, 2, 4, length);
		train3 = new Train(03, 1, 4, length);
		train4 = new Train(04, 2, 4, length);
		
		composition1 = new Composition(new ArrayList(){{add(train1);add(train2);}}, arrivaltime, -1);
		composition2 = new Composition(new ArrayList(){{add(train3);add(train4);}}, arrivaltime, -1);
		composition3 = new Composition(new ArrayList(){{add(train1);add(train2);}}, -1, departuretime);
		composition4 = new Composition(new ArrayList(){{add(train3);add(train4);}}, -1, departuretime);
		
		fblock1 = new FinalBlock(composition1.getTrainList(), composition1.getArrivaltime(), composition3.getDeparturetime(), composition1, composition3, -1, 1, -1, 1);
		fblock2 = new FinalBlock(composition2.getTrainList(), composition2.getArrivaltime(), composition4.getDeparturetime(), composition2, composition4, -1, 1, -1, 1);

	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
