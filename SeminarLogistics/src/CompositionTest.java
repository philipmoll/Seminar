import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class CompositionTest {

	private Train a;
	private Train b;
	private Train c;
	private Composition d;
	private Composition e;

	@Before
	public void setUp(){
		a = new Train(1,2,1,6);
		b = new Train(2,2,2,4);
		c = new Train(3,1,3,4);
		d = new Composition(new ArrayList<Train>(){{add(a);}});
		e = new Composition(new ArrayList<Train>(){{add(b); add(c);}});
	}

	@Test
	public void testConstructor() {
		assertEquals(1,1);
	}
	@Test
	public void testCoupleComposition(){
		d.coupleComposition(e);
		assertEquals(6,d.getLength());
		assertEquals(0,d.getTrain(0).getPosition());
		assertEquals(1,d.getTrain(1).getPosition());
		assertEquals(2,d.getTrain(2).getPosition());
		
		//TODO: THE ONE BELOW SHOULD NOT WORK. THE OBJECT IS NOT DELETED BUT THE TRAINS HAVE THE UPDATED POSITION.
		//BECAUSE IN THE FUNCTION, THE OBJECT IS INPUT, THE OBJECT CANNOT BE DELETED IN THAT FUNCTION SELF.
		assertEquals(2,e.getTrain(1).getPosition());
	}
	@Test
	public void testDecoupleComposition(){
		
	}
	@Test
	public void testGetLength(){
		assertEquals(1, d.getLength());
		assertEquals(5, e.getLength());
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
		assertEquals(1,d.getTrain(0).getLength());
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
		assertEquals(3,e.getTrain(1).getLength());
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
	@Test
	public void testCompositionList(){
		assertEquals(new ArrayList<Train>(){{add(a);}},d.getCompositionList());
	}
	@Test
	public void testUpdateComposition(){
		//I don't know how to test this function as it constantly done in all the functions above already.
	}
}
