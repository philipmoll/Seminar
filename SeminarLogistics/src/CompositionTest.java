import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class CompositionTest {

	private Train a;
	private Train b;
	private Train c;
	private Composition d;
	private Composition e;
	private Track f;
	//private Track g;

	@SuppressWarnings("serial")
	@Before
	public void setUp(){
		try {
			a = new Train(1,2,1,6);
			b = new Train(2,2,2,4);
			c = new Train(3,1,3,4);
			
			f = new Track("testtrack",500,1,0,0,1,1,0);

			d = new Composition(new ArrayList<Train>(){{add(a);}}, f, 9);
			e = new Composition(new ArrayList<Train>(){{add(b); add(c);}},f, 1);
			
		} catch (IndexOutofBoundsException | TrackNotFreeException | IOException e2) {
			e2.printStackTrace();
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(f.getCompositionlist().get(0),e);
		assertEquals(f.getCompositionlist().get(1),d);
	}
	
	@Test
	public void testCoupleComposition(){
		try {
			d.coupleComposition(e);
		} catch (MisMatchException | IndexOutofBoundsException | IOException e1) {
			e1.printStackTrace();
		}
		assertEquals(14,d.getLength());
		assertEquals(0,d.getTrain(0).getPosition());
		assertEquals(1,d.getTrain(1).getPosition());
		assertEquals(2,d.getTrain(2).getPosition());
		assertEquals(1,d.getLocationOnTrack());
		for (int i = 0; i <f.getCompositionlist().size();i++){
			assertNotEquals(f.getCompositionlist().get(i),e);
		}
	}
	@Test
	public void testDecoupleComposition(){
		try {
			d.coupleComposition(e);
			e = null;
			d.decoupleComposition(1);
		} catch (MisMatchException | IndexOutofBoundsException | IOException | TrackNotFreeException e1) {
			e1.printStackTrace();
		}
		
		
			
	}
	@Test
	public void testGetLength(){
		assertEquals(6, d.getLength());
		assertEquals(8, e.getLength());
	}
	@Test
	public void testGetSize(){
		assertEquals(1, d.getSize());
		assertEquals(2, e.getSize());
	}
	@Test
	public void testGetTrain(){
		assertEquals(a, d.getTrain(0));

		assertEquals(1, d.getTrain(0).getID());
		assertEquals(2, d.getTrain(0).getType());
		assertEquals(6,d.getTrain(0).getLength());
		assertEquals(10,d.getTrain(0).getInspectionTime());
		assertEquals(10,d.getTrain(0).getCleaningTime());
		assertEquals(10,d.getTrain(0).getRepairingTime());
		assertEquals(10,d.getTrain(0).getWashingTime());
		assertEquals(false,d.getTrain(0).getInterchangeable());
		assertEquals(false,d.getTrain(0).getInspecting());
		assertEquals(false,d.getTrain(0).getCleaning());
		assertEquals(false,d.getTrain(0).getRepairing());
		assertEquals(false,d.getTrain(0).getWashing());
		assertEquals(0,d.getTrain(0).getPosition());

		assertEquals(c, e.getTrain(1));
		assertEquals(3, e.getTrain(1).getID());
		assertEquals(1, e.getTrain(1).getType());
		assertEquals(4,e.getTrain(1).getLength());
		assertEquals(10,e.getTrain(1).getInspectionTime());
		assertEquals(10,e.getTrain(1).getCleaningTime());
		assertEquals(10,e.getTrain(1).getRepairingTime());
		assertEquals(10,e.getTrain(1).getWashingTime());
		assertEquals(false,e.getTrain(1).getInterchangeable());
		assertEquals(false,e.getTrain(1).getInspecting());
		assertEquals(false,e.getTrain(1).getCleaning());
		assertEquals(false,e.getTrain(1).getRepairing());
		assertEquals(false,e.getTrain(1).getWashing());
		assertEquals(1,e.getTrain(1).getPosition());
	}
	@SuppressWarnings("serial")
	@Test
	public void testCompositionList(){
		assertEquals(new ArrayList<Train>(){{add(a);}},d.getCompositionList());
	}
	@Test
	public void testUpdateComposition(){
		//I don't know how to test this function as it constantly done in all the functions above already.
	}

	@Test
	public void testMoveComposition(){

	}

	@Test
	public void testGetPositionOnTrack(){
		try {
			assertEquals(e.getPositionOnTrack(),0);
			assertEquals(d.getPositionOnTrack(),1);
		} catch (MisMatchException e1) {
			e1.printStackTrace();
		}
	}
}
