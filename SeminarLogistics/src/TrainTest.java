import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TrainTest {

	private Train a;
	private Train b;

	@Before
	public void setUp() {
		a = new Train(3,2,4,5);
		b = new Train(2,1,4);
	}

	@Test
	public void testConstructor() {
		assertEquals(3, a.getID());
		assertEquals(2, a.getType());
		assertEquals(4,a.getLength());
		assertEquals(10,a.getInspectionTime());
		assertEquals(10,a.getCleaningTime());
		assertEquals(10,a.getRepairingTime());
		assertEquals(10,a.getWashingTime());
		assertEquals(false,a.getInterchangeable());
		assertEquals(false,a.getInspecting());
		assertEquals(false,a.getCleaning());
		assertEquals(false,a.getRepairing());
		assertEquals(false,a.getWashing());
		assertEquals(0,a.getPosition());
	}
	@Test 
	public void testConstructor2() {
		assertEquals(2, b.getID());
		assertEquals(1, b.getType());
		assertEquals(109,b.getLength());
		assertEquals(10,b.getInspectionTime());
		assertEquals(10,b.getCleaningTime());
		assertEquals(10,b.getRepairingTime());
		assertEquals(10,b.getWashingTime());
		assertEquals(false,b.getInterchangeable());
		assertEquals(false,b.getInspecting());
		assertEquals(true,b.getCleaning());
		assertEquals(false,b.getRepairing());
		assertEquals(true,b.getWashing());
		assertEquals(1,b.getPosition());
	}
	@Test
	public void testGetID() {
		assertEquals(3, a.getID());
	}
	@Test
	public void testGetType(){
		assertEquals(2, a.getType());
	}
	@Test
	public void testGetLength(){
		assertEquals(4,a.getLength());
	}
	@Test
	public void testGetInspectionTime(){
		assertEquals(10,a.getInspectionTime());
	}
	@Test
	public void testGetCleaningTime(){
		assertEquals(10,a.getCleaningTime());	
	}
	@Test
	public void testGetRepairingTime(){
		assertEquals(10,a.getRepairingTime());
	}
	@Test
	public void testGetWashingTime(){
		assertEquals(10,a.getWashingTime());
	}
	@Test
	public void testGetInterchangeable(){
		assertEquals(false,a.getInterchangeable());
	}
	@Test
	public void testGetInspecting(){
		assertEquals(false,a.getInspecting());
	}
	@Test
	public void testGetCleaning(){
		assertEquals(false,a.getCleaning());
	}
	@Test
	public void testGetRepairing(){
		assertEquals(false,a.getRepairing());
	}
	@Test
	public void testGetWashing(){
		assertEquals(false,a.getWashing());
	}
	@Test
	public void testGetPosition(){
		assertEquals(0,a.getPosition());
	}
	@Test
	public void testNumberCarriages(){
		assertEquals(5,a.getCarriages());
	}
	@Test
	public void testToggleInterchangeable(){
		a.toggleInterchangeable();
		assertEquals(true,a.getInterchangeable());
	}
	@Test
	public void testToggleInspecting(){
		a.toggleInspecting();
		assertEquals(true,a.getInspecting());
	}
	@Test
	public void TestToggleCleaning(){
		a.toggleCleaning();
		assertEquals(true,a.getCleaning());
	}
	@Test
	public void testToggleRepairing(){
		a.toggleRepairing();
		assertEquals(true,a.getRepairing());
	}
	@Test
	public void testToggleWashing(){
		a.toggleWashing();
		assertEquals(true,a.getWashing());
	}
	@Test
	public void testChangePosition(){
		a.setPosition(5);
		assertEquals(5,a.getPosition());
	}
}
