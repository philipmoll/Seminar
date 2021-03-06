import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class CompositionTest {

	private Train a;
	private Train b;
	private Train c;
	private Train x;
	private Train y;
	private Train z;
	private Composition d;
	private Composition e;
	private Composition v;
	private Composition w;
	private Track f;
	private Track g;

	@SuppressWarnings("serial")
	@Before
	public void setUp(){
		try {
			a = new Train(1,2,1,6);
			b = new Train(2,2,2,4);
			c = new Train(3,1,3,4);
			x = new Train(4,2,1,6);
			y = new Train(5,2,2,4);
			z = new Train(6,1,3,4);

			f = new Track("testtrack",500,1,0,0,1,1,0);
			g = new Track("testtrack2",470,1,0,1,0,0,1);

			d = new Composition(new ArrayList<Train>(){{add(a);}}, f, 9);
			e = new Composition(new ArrayList<Train>(){{add(b); add(c);}},f, 1);

			v = new Composition(new ArrayList<Train>(){{add(x);}}, g, 9);
			w = new Composition(new ArrayList<Train>(){{add(y); add(z);}},g, 1);

		} catch (IndexOutOfBoundsException | TrackNotFreeException | IOException e2) {
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
		assertEquals(2,d.getTrack().getCompositionlist().size());
		assertEquals(1,d.getSize());
		try {
			d.coupleComposition(e);
		} catch (MisMatchException | IndexOutOfBoundsException | IOException e1) {
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
		assertEquals(3,d.getSize());
		assertEquals(1,d.getTrack().getCompositionlist().size());
	}
	@Test
	public void testDecoupleComposition(){
		Composition x = null;
		try {
			d.coupleComposition(e);
			assertEquals(1,d.getTrack().getCompositionlist().size());
			e = null;
			x = d.decoupleComposition(1);
		} catch (MisMatchException | IndexOutOfBoundsException | IOException | TrackNotFreeException e1) {
			e1.printStackTrace();
		}
		//check
		assertEquals(10,d.getLength());
		assertEquals(4,x.getLength());
		
		assertEquals(2,d.getSize());
		assertEquals(1,x.getSize());

		assertEquals(2,d.getTrack().getCompositionlist().size());
		assertEquals(d.getTrack().getCompositionlist().get(0),d);
		assertEquals(x.getTrack().getCompositionlist().get(1),x);

		assertEquals(0,d.getTrainList().get(0).getPosition());
		assertEquals(0,x.getTrainList().get(0).getPosition());		


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
		assertEquals(new ArrayList<Train>(){{add(a);}},d.getTrainList());
	}
	@Test
	public void testUpdateComposition(){
		//I don't know how to test this function as it constantly done in all the functions above already.
	}

	/**
	@Test
	public void testMoveComposition1(){
		try {
				
			int locationold = d.getLocationOnTrack();
			Track trackold = d.getTrack();
			d.moveComposition(g, 400, "b");
			for (int i = locationold;i<locationold+d.getLength();i++)
			{
				assertEquals(0,trackold.getOccupied(i));
			}
			for (int i = d.getLocationOnTrack();i<d.getLocationOnTrack()+d.getLength();i++)
			{
				assertEquals(1,d.getTrack().getOccupied(i));
			}

			assertEquals(1,f.getCompositionlist().size());
			for (int i = 0; i < f.getCompositionlist().size();i++){
				assertNotEquals(d,f.getCompositionlist().get(i));
			}
			assertEquals(e,f.getCompositionlist().get(0));

			assertEquals(3,g.getCompositionlist().size());
			assertEquals(w,g.getCompositionlist().get(0));
			assertEquals(v,g.getCompositionlist().get(1));
			assertEquals(d,g.getCompositionlist().get(2));

			assertEquals(400, d.getLocationOnTrack());
			assertEquals(g, d.getTrack());

			assertNotEquals(1, d.getLocationOnTrack());
			assertNotEquals(f, d.getTrack());
			//System.out.println(w.getLocationOnTrack());
		} catch (TrackNotFreeException | IndexOutOfBoundsException | IOException | MisMatchException e) {
			e.printStackTrace();
		}
	}
	*/
	@Test
	public void testMoveComposition2(){
		try {
			assertEquals(2,g.getCompositionlist().size());
			e.moveComposition(g, 10, "a", 1);
			
			assertEquals(18,w.getLocationOnTrack());
			assertEquals(26,v.getLocationOnTrack());
			assertEquals(10,e.getLocationOnTrack());
			assertEquals(g,e.getTrack());
			assertEquals(1,f.getCompositionlist().size());
			for (int i = 1; i<9; i++){
				assertEquals(0,f.getOccupied(i));
			}
			for (int i = 10; i<32; i++){
				assertEquals(1,g.getOccupied(i));
			}
			assertEquals(e,g.getCompositionlist().get(0));
			assertEquals(w,g.getCompositionlist().get(1));
			assertEquals(v,g.getCompositionlist().get(2));
			
			v.moveComposition(g, 460, "b");
			assertEquals(460,v.getLocationOnTrack());
			w.moveComposition(g, 50, "a");
			assertEquals(50,w.getLocationOnTrack());
			d.moveComposition(g, 40, "a", 1);
			
			assertEquals(54,w.getLocationOnTrack());
			assertEquals(460,v.getLocationOnTrack());
			assertEquals(46,e.getLocationOnTrack());
			assertEquals(40,d.getLocationOnTrack());
			assertEquals(g,d.getTrack());
			assertEquals(0,f.getCompositionlist().size());
			for (int i = 0; i<f.getTracklength(); i++){
				assertEquals(0,f.getOccupied(i));
			}
			for (int i = 0; i<40; i++){
				assertEquals(0,g.getOccupied(i));
			}
			for (int i = 40; i<62; i++){
				assertEquals(1,g.getOccupied(i));
			}
			for (int i = 62; i<460; i++){
				assertEquals(0,g.getOccupied(i));
			}
			for (int i = 460; i<466; i++){
				assertEquals(1,g.getOccupied(i));
			}
			for (int i = 466; i<g.getTracklength(); i++){
				assertEquals(0,g.getOccupied(i));
			}
			assertEquals(d,g.getCompositionlist().get(0));
			assertEquals(e,g.getCompositionlist().get(1));
			assertEquals(w,g.getCompositionlist().get(2));
			assertEquals(v,g.getCompositionlist().get(3));
			
			setUp();
			v.moveComposition(g, 400, "a");
			d.moveComposition(g, 14, "b",1);
			assertEquals(0,w.getLocationOnTrack());
			assertEquals(8,v.getLocationOnTrack());
			assertEquals(14,d.getLocationOnTrack());
			assertEquals(g,d.getTrack());
			assertEquals(1,f.getCompositionlist().size());
			for (int i = 9; i<f.getTracklength(); i++){
				assertEquals(0,f.getOccupied(i));
			}
			for (int i = 0; i<19; i++){
				assertEquals(1,g.getOccupied(i));
			}
			for (int i = 20; i<g.getTracklength(); i++){
				assertEquals(0,g.getOccupied(i));
			}
			assertEquals(d,g.getCompositionlist().get(2));
			assertEquals(w,g.getCompositionlist().get(0));
			assertEquals(v,g.getCompositionlist().get(1));
	
			setUp();
			d.moveComposition(g, 14, "b",1);
			assertEquals(0,w.getLocationOnTrack());
			assertEquals(8,v.getLocationOnTrack());
			assertEquals(14,d.getLocationOnTrack());
			assertEquals(g,d.getTrack());
			assertEquals(1,f.getCompositionlist().size());
			for (int i = 9; i<f.getTracklength(); i++){
				assertEquals(0,f.getOccupied(i));
			}
			for (int i = 0; i<20; i++){
				assertEquals(1,g.getOccupied(i));
			}
			for (int i = 20; i<g.getTracklength(); i++){
				assertEquals(0,g.getOccupied(i));
			}
			assertEquals(d,g.getCompositionlist().get(2));
			assertEquals(w,g.getCompositionlist().get(0));
			assertEquals(v,g.getCompositionlist().get(1));
			
			setUp();
			v.moveComposition(g, 11, "a");
			d.moveComposition(g, 15, "b",1);
			assertEquals(1,w.getLocationOnTrack());
			assertEquals(9,v.getLocationOnTrack());
			assertEquals(15,d.getLocationOnTrack());
			assertEquals(g,d.getTrack());
			assertEquals(1,f.getCompositionlist().size());
			for (int i = 9; i<f.getTracklength(); i++){
				assertEquals(0,f.getOccupied(i));
			}
			assertEquals(0,g.getOccupied(0));
			for (int i = 1; i<21; i++){
				assertEquals(1,g.getOccupied(i));
			}
			for (int i = 21; i<g.getTracklength(); i++){
				assertEquals(0,g.getOccupied(i));
			}
			assertEquals(d,g.getCompositionlist().get(2));
			assertEquals(w,g.getCompositionlist().get(0));
			assertEquals(v,g.getCompositionlist().get(1));
			
		} catch (IndexOutOfBoundsException | TrackNotFreeException | IOException | MisMatchException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPositionOnTrack(){
		try {
			assertEquals(0,e.getPositionOnTrack());
			assertEquals(1,d.getPositionOnTrack());


			Composition x = e.decoupleComposition(0);

			assertEquals(3,f.getCompositionlist().size());
			assertEquals(e,f.getCompositionlist().get(0));
			assertEquals(x,f.getCompositionlist().get(1));
			assertEquals(d,f.getCompositionlist().get(2));

			assertEquals(2,d.getPositionOnTrack());
			assertEquals(1,x.getPositionOnTrack());
			assertEquals(0,e.getPositionOnTrack());
		} catch (MisMatchException | IndexOutOfBoundsException | IOException | TrackNotFreeException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void testSetGetarrivaltime(){
		d.setArrivaltime(0.06);
		assertEquals(0.06,d.getArrivaltime(),0.000001);
	}
	
	@Test
	public void testSetGetdeparturetime(){
		d.setDeparturetime(0.06);
		assertEquals(0.06,d.getDeparturetime(),0.000001);
	}
	
	@Test
	public void testGetTotalServiceTime(){
		
	}
}
/**
System.out.println("label: " +compositiontrack.getLabel()+ " this.locationontrack: " + this.locationontrack + " this.lcoationontrack+compositionlength: " + (this.locationontrack+compositionlength-1));
		for (int i = this.locationontrack; i<locationontrack+compositionlength; i++){
			System.out.println("i: "+i+" occupied: " + compositiontrack.getOccupied(i));
		}
*/