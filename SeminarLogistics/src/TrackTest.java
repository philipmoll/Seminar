import static org.junit.Assert.*;

import java.io.IOException;
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


	@SuppressWarnings("serial")
	@Before
	public void setUp(){
		try {
			a = new Train(1,2,40,3);
			b = new Train(2,2,40,3);
			c = new Train(3,1,40,3);
			d = new Train(4,2,40,3);
			

			e = new Composition(new ArrayList<Train>(){{add(a);}});
			f = new Composition(new ArrayList<Train>(){{add(b); add(c);}});
			g = new Composition(new ArrayList<Train>(){{add(d);}});
			
			

			h = new Track("track1", 500, 2, 0, 1, 0, 1, 1);
			i = new Track("track2", 400, 0, 1, 0, 1, 0, 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testGetLabel() {
		assertEquals(h.getLabel(), "track1");
		assertEquals(i.getLabel(), "track2");
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
		assertEquals(h.getInspectionposition(),0);
		assertEquals(i.getInspectionposition(),1);
	}

	@Test
	public void testGetCleaningposition(){
		assertEquals(h.getCleaningposition(),1);
		assertEquals(i.getCleaningposition(),0);
	}

	@Test
	public void testGetRepairingposition(){
		assertEquals(h.getRepairingposition(),0);
		assertEquals(i.getRepairingposition(),1);
	}

	@Test
	public void testGetWashingposition(){
		assertEquals(h.getWashingposition(),1);
		assertEquals(i.getWashingposition(),0);
	}

	@Test
	public void testGetTracktype(){
		assertEquals(h.getTracktype(),1);
		assertEquals(i.getTracktype(),2);
	}

	@Test
	public void testGetSetOccupiedFree(){	
		try {
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
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetCompositionlist(){
		try {
			h.addCompositiontoTrackLeft(e);
			h.addCompositiontoTrackRight(g);
			i.addCompositiontoTrackLeft(f);

			ArrayList<Composition> test1 = new ArrayList<Composition>();
			test1.add(e);
			test1.add(g);

			ArrayList<Composition> test2 = new ArrayList<Composition>();
			test2.add(f);

			assertEquals(h.getCompositionlist(),test1);
			assertEquals(i.getCompositionlist(),test2);
			assertEquals(h.getCompositionlist().size(),2);
			assertEquals(i.getCompositionlist().size(),1);
		} catch (TrackNotFreeException e1) {
			e1.printStackTrace();
		}
	}

	@Test
	public void testAddCompositiontoTrackLeftRight(){
		try {
			h.addCompositiontoTrackLeft(e);

			h.addCompositiontoTrackLeft(g);
			h.addCompositiontoTrackRight(f);
			h.addCompositiontoTrackRight(e);;

			ArrayList<Composition> test1 = new ArrayList<Composition>();
			test1.add(g);
			test1.add(e);
			test1.add(f);
			test1.add(e);

			assertEquals(h.getCompositionlist(),test1);
		} catch (TrackNotFreeException e1) {
			e1.printStackTrace();
		}
	}

	@Test
	public void testAddCompositiontoTrack(){
		try {
			h.addCompositiontoTrack(e, 0);
			h.addCompositiontoTrack(f, 1);
			h.addCompositiontoTrack(g, 1);
		} catch (IndexOutOfBoundsException e1) {
			e1.printStackTrace();
		}


		assertEquals(3,h.getCompositionlist().size());
		assertEquals(e, h.getCompositionlist().get(0));
		assertEquals(f, h.getCompositionlist().get(2));
		assertEquals(g, h.getCompositionlist().get(1));
	}
	@Test
	public void testRemoveComposition(){
		try {
			h.addCompositiontoTrack(e, 0);
			h.addCompositiontoTrack(f, 1);
			h.addCompositiontoTrack(g, 1);

			h.removeComposition(1);
			assertEquals(2, h.getCompositionlist().size());
			assertEquals(e, h.getCompositionlist().get(0));
			assertEquals(f, h.getCompositionlist().get(1));
		} catch (IndexOutOfBoundsException e1) {
			e1.printStackTrace();
		}

	}
	
	@Test
	public void testGetCompositionLengthOnTrack(){ 
		try {
			h.addCompositiontoTrackRight(g);
			assertEquals(3,h.getCompositionLengthOnTrack());
			h.addCompositiontoTrackLeft(e);
			assertEquals(6,h.getCompositionLengthOnTrack());
			h.addCompositiontoTrack(f, 0);
			assertEquals(12,h.getCompositionLengthOnTrack());
		} catch (TrackNotFreeException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void testGetPartialCompositionList(){
		
	}
	
}



public ArrayList<Composition> getPartialCompositionList(int location, String aorb, Composition enteringcomposition) throws IOException{
	ArrayList<Composition> partialcompositionlist = new ArrayList<Composition>();
	if (aorb == "a"){
		boolean stop = false;
		int i = 0;
		while (stop==false){
			Composition currentcomposition = compositionlist.get(i);
			if (currentcomposition.getLocationOnTrack()+currentcomposition.getLength()-1<location){
				partialcompositionlist.add(currentcomposition);
			}
			else{
				stop = true;
			}
			i++;
		}
	}
	else if (aorb == "b"){
		boolean stop = false;
		int i = compositionlist.size()-1;
		while (stop==false){
			Composition currentcomposition = compositionlist.get(i);
			if (currentcomposition.getLocationOnTrack()>location+enteringcomposition.getLength()-1){
				partialcompositionlist.add(0,currentcomposition);
			}
			else{
				stop = true;
			}
			i--;
		}
	}
	else{
		throw new IOException("Input in function moveComposition must be a or b, and is "+aorb);
	}
	
	return partialcompositionlist;
}


