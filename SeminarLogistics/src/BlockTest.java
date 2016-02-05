import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BlockTest {
	private Train a;
	private Train x;
	private Train y;
	private Train z;
	private Composition d;
	private Composition f;
	
	private Block aa;
	private Block bb;
	

	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		try {
			a = new Train(1,2,1,6);;
			x = new Train(4,2,1,6);
			y = new Train(5,2,2,4);
			z = new Train(6,1,3,4);

			d = new Composition(new ArrayList<Train>(){{add(a);}},0,-1);
			f = new Composition(new ArrayList<Train>(){{add(x); add(y); add(z);}},0.5,-1);
			
			aa = new Block(new ArrayList<Train>(){{add(a);}}, 0, -1, d, -1, 0);
			bb = new Block(new ArrayList<Train>(){{add(x); add(y);}},0.5,-1,f,-1,1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	@Test
	public void testConstructor() {
		assertEquals(new ArrayList<Train>(){{add(a);}},aa.getTrainList()); //unnecessary: function of Composition, not Block
		assertEquals(new ArrayList<Train>(){{add(x); add(y);}},bb.getTrainList()); //unnecessary: function of Composition, not Block
		assertEquals(d,aa.getOriginComposition());
		assertEquals(f,bb.getOriginComposition());
		assertEquals(0,aa.getArrivaltime(),.00001);
		assertEquals(0.5,bb.getArrivaltime(),.00001);
		assertEquals(-1,aa.getDeparturetime(),.00001);
		assertEquals(-1,bb.getDeparturetime(),.00001);
		assertEquals(-1,aa.getCutPosition1());
		assertEquals(0,aa.getCutPosition2());
		assertEquals(-1,bb.getCutPosition1());
		assertEquals(1,bb.getCutPosition2());
	}
	
	@Test
	public void testGetOriginComposition(){
		assertEquals(d,aa.getOriginComposition());
		assertEquals(f,bb.getOriginComposition());
	}
	
	@Test
	public void testGetArrivaltime(){
		assertEquals(0,aa.getArrivaltime(),.00001);
		assertEquals(0.5,bb.getArrivaltime(),.00001);
	}
	
	@Test
	public void testGetDeparturetime(){
		assertEquals(-1,aa.getDeparturetime(),.00001);
		assertEquals(-1,bb.getDeparturetime(),.00001);
	}
	
	@Test
	public void testGetCutPosition1(){
		assertEquals(-1,aa.getCutPosition1());
		assertEquals(-1,bb.getCutPosition1());
	}
	
	@Test
	public void testGetCutPosition2(){
		assertEquals(0,aa.getCutPosition2());
		assertEquals(1,bb.getCutPosition2());
	}

}
