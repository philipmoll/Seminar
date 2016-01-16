import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TrainTest {

	private Train a;
	private Train b;

	@Before
	public void setUp() {
		a = new Train(3,2);
		b = new Train(2,1,false,false,true,false,true);
	}

	@Test
	public void testConstructor() {
		assertEquals(3, a.getID());
		assertEquals(2, a.getType());
		assertEquals(100,a.getLength());
		assertEquals(20,a.getInspectionTime());
		assertEquals(20,a.getCleaningTime());
		assertEquals(20,a.getRepairingTime());
		assertEquals(20,a.getWashingTime());
		assertEquals(false,a.getInterchangeable());
		assertEquals(false,a.getInspecting());
		assertEquals(false,a.getCleaning());
		assertEquals(false,a.getRepairing());
		assertEquals(false,a.getWashing());
		assertEquals(1,a.getPosition());
	}
	@Test 
	public void testConstructor2() {
		assertEquals(2, b.getID());
		assertEquals(1, b.getType());
		assertEquals(50,b.getLength());
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
		assertEquals(100,a.getLength());
	}
	@Test
	public void testGetInspectionTime(){
		assertEquals(20,a.getInspectionTime());
	}
	@Test
	public void testGetCleaningTime(){
		assertEquals(20,a.getCleaningTime());	
	}
	@Test
	public void testGetRepairingTime(){
		assertEquals(20,a.getRepairingTime());
	}
	@Test
	public void testGetWashingTime(){
		assertEquals(20,a.getWashingTime());
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
		assertEquals(1,a.getPosition());
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
		a.changePosition(5);
		assertEquals(5,a.getPosition());
	}
}
