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
	
	private Event event1;
	private Event event2;
	private Event event3;
	private Event event4;
	private Event event5;
	private Event event6;
	private Event event7;
	private Event event8;
	
	private FinalBlock block1;
	private FinalBlock block2;
	
	

	@Before
	public void setUp() {
		
		track1 = new Track("track1", length, 1, 0, 0, 0, 0, backwardlength);
		track2 = new Track("track2", length, 1, 0, 0, 0, 0, backwardlength);
		track3 = new Track("track3", length, 1, 0, 0, 0, 0, backwardlength);
		
		int train_ID, int type, int carriages, int length
		train1 = new Train(01, 1, 4, length);
		train2 = new Train();
		train3 = new Train();
		train4 = new Train();
		
		composition1 = new Composition(new ArrayList(){{add(train1);add(train2);}}, arrivaltime, -1);
		composition2 = new Composition(new ArrayList(){{add(train3);add(train4);}}, arrivaltime, -1);
		composition3 = new Composition(new ArrayList(){{add(train1);add(train2);}}, -1, departuretime);
		composition4 = new Composition(new ArrayList(){{add(train3);add(train4);}}, -1, departuretime);
		
			
		block1 = new FinalBlock(null, 0, 0, block1, block1, 0, 0, 0, 0);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
