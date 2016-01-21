import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
public class TrackTest {

	private Train a;
	private Train b;
	private Train c;
	private Train d;
	private Composition e;
	private Composition f;
	private Composition g;
	private Track h;
	private Track i;
		
	
	@Before
	public void setUp(){
		a = new Train(1,2,1,2);
		b = new Train(2,2,1,2);
		c = new Train(3,1,1,2);
		d = new Train(4,2,1,2);
		e = new Composition(new ArrayList<Train>(){{add(a);}});
		f = new Composition(new ArrayList<Train>(){{add(b); add(c);}});
		g = new Composition(new ArrayList<Train>(){{add(d);}});
		h = new Track("tracktest1", 500, 2, false, true, false, true, 1);
		i = new Track("tracktest2", 400, 0, true, false, true, false, 2);
	}
	
	@Test
	public void testConstructor(){
		//TODO: hoe doe je dit ook al weer voor een constructor
	}
	
	@Test
	public void testGetLabel() {
		assertEquals(h.getLabel(), "tracktest1");
		assertEquals(i.getLabel(), "tracktest2");
	}
	
	@Test
	public void testGetTracklength(){
		assertEquals(h.getTracklength(),500);
		assertEquals(i.getTracklength(),400);
	}
	
	@Test
	public void testGetParktrain(){
		assertEquals(h.getParktrain(),2);
		assertEquals(i.getParktrain(),0);
	}
	
	@Test
	public void testGetInspectionposition(){
		assertEquals(h.getInspectionposition(),false);
		assertEquals(i.getInspectionposition(),true);
	}
	
	@Test
	public void testGetCleaningposition(){
		assertEquals(h.getCleaningposition(),true);
		assertEquals(i.getCleaningposition(),false);
	}
	
	@Test
	public void testGetRepairingposition(){
		assertEquals(h.getRepairingposition(),false);
		assertEquals(i.getRepairingposition(),true);
	}
	
	@Test
	public void testGetWashingposition(){
		assertEquals(h.getWashingposition(),true);
		assertEquals(i.getWashingposition(),false);
	}
	
	@Test
	public void testGetTracktype(){
		assertEquals(h.getTracktype(),1);
		assertEquals(i.getTracktype(),2);
	}
	
	@Test
	public void testGetSetOccupiedFree(){
		i.setOccupied(4);
		i.setOccupied(20,100);
		
		assertEquals(h.getOccupied(20),0);
		h.setOccupied(20);
		assertEquals(h.getOccupied(20),1);
		h.setFree(20);
		assertEquals(h.getOccupied(20),0);
		for (int x = 0; x <= 2; x++){
			assertEquals(h.getOccupied(x), 0);
		}
		h.setOccupied(0, 2);
		for (int x = 0; x <= 2; x++){
			assertEquals(h.getOccupied(x), 1);
		}
		h.setFree(0,2);
		for (int x = 0; x <= 2; x++){
			assertEquals(h.getOccupied(x), 0);
		}
		
		assertEquals(i.getOccupied(4),0);
		i.setOccupied(4);
		assertEquals(i.getOccupied(4),1);
		i.setFree(4);
		assertEquals(i.getOccupied(4),0);
		for (int x = 20; x <= 100; x++){
			assertEquals(i.getOccupied(x), 0);
		}
		i.setOccupied(20, 100);
		for (int x = 20; x <= 100; x++){
			assertEquals(i.getOccupied(x), 1);
		}
		i.setFree(20,100);
		for (int x = 20; x <= 100; x++){
			assertEquals(i.getOccupied(x), 0);
		}
	}
	/**
	@Test
	public void testGetCompositionlist(){
		h.addCompositiontoTrackLeft(e);
		i.addCompositiontoTrackLeft(f);
	}

	public ArrayList<Composition> getCompositionlist(){
		return compositionlist;
	}

	public void addCompositiontoTrackLeft(Composition composition){ //LEFT: links op de map, index 0 //TODO: check total length
		this.compositionlist.add(0,composition);
	}

	public void addCompositiontoTrackRight(Composition composition){ //RIGHT: rechts op de map, index max lengte arraylist
		this.compositionlist.add(compositionlist.size(),composition);
	}

*/

}
