import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CompatibleArrivingBlocksTest implements Serializable{
	private Train a;
	private Train b;
	private Train c;
	private Train x;
	private Train y;
	private Train z;
	
	private Composition d;
	private Composition e;
	private Composition f;
	private Composition g;
	private Composition h;
	private Composition i;
	
	private CompatibleArrivingBlocks k;
	private CompatibleArrivingBlocks l;
	private CompatibleArrivingBlocks m;
	
	private ArrayList<Composition> arrivingcompositions;
	private ArrayList<Composition> departingcompositions;
	
	private ArrayList<Block> arrivingblocks;
	private ArrayList<Block> departingblocks;

	private Block dd;
	private Block ee;
	private Block ff;
	private Block gg;
	private Block hh;
	private Block ii;
	
	private CompatibleArrivingBlocks test1;
	private CompatibleArrivingBlocks test2;
	private CompatibleArrivingBlocks test3;
	
	
	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		try {
			a = new Train(1,2,1,6);
			b = new Train(2,2,2,4);
			c = new Train(3,1,3,4);
			x = new Train(4,2,1,6);
			y = new Train(5,2,2,4);
			z = new Train(6,1,3,4);

			d = new Composition(new ArrayList<Train>(){{add(a);}},0,-1);
			e = new Composition(new ArrayList<Train>(){{add(b);}},0.3,-1);
			f = new Composition(new ArrayList<Train>(){{add(c);}},0.5,-1);
			g = new Composition(new ArrayList<Train>(){{add(x);}},-1,0.15);
			h = new Composition(new ArrayList<Train>(){{add(y);}},-1,0.38);
			i = new Composition(new ArrayList<Train>(){{add(z);}},-1,0.7);
			
			dd = new Block(new ArrayList<Train>(){{add(a);}},0,-1,d,-1,0);
			ee = new Block(new ArrayList<Train>(){{add(b);}},0.3,-1,e,-1,0);
			ff = new Block(new ArrayList<Train>(){{add(c);}},0.5,-1,f,-1,0);
			gg = new Block(new ArrayList<Train>(){{add(x);}},-1,0.15,g,-1,0);
			hh = new Block(new ArrayList<Train>(){{add(y);}},-1,0.38,h,-1,0);
			ii = new Block(new ArrayList<Train>(){{add(z);}},-1,0.7,i,-1,0);
			
			arrivingcompositions = new ArrayList<Composition>(){{add(d);add(e);add(f);}};
			departingcompositions = new ArrayList<Composition>(){{add(g);add(h);add(i);}};
			
			arrivingblocks = Matching.makeblocks(arrivingcompositions);
			departingblocks = Matching.makeblocks(departingcompositions);
			
			test1 = new CompatibleArrivingBlocks(gg, arrivingblocks);
			test2 = new CompatibleArrivingBlocks(hh, arrivingblocks);
			test3 = new CompatibleArrivingBlocks(ii, arrivingblocks);
		} catch (IndexOutOfBoundsException | MisMatchException | TrackNotFreeException
				| CloneNotSupportedException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConstructor(){
		//NB: tests may fail when we change c
		assertEquals(1,test1.getCompatibleArrivingBlocks().size());
		assertEquals(1,test2.getCompatibleArrivingBlocks().size());
		assertEquals(3,test3.getCompatibleArrivingBlocks().size());

		assertEquals(true,dd.checkEqual(test1.getCompatibleArrivingBlocks().get(0)));
		assertEquals(true,dd.checkEqual(test2.getCompatibleArrivingBlocks().get(0)));
		assertEquals(true,dd.checkEqual(test3.getCompatibleArrivingBlocks().get(0)));
		assertEquals(true,ee.checkEqual(test3.getCompatibleArrivingBlocks().get(1)));
		assertEquals(true,ff.checkEqual(test3.getCompatibleArrivingBlocks().get(2)));
		
		assertEquals(gg,test1.getDepartingBlock());
		assertEquals(hh,test2.getDepartingBlock());
		assertEquals(ii,test3.getDepartingBlock());
	}
	
	@Test
	public void testGetDepartingBlock(){
		assertEquals(gg,test1.getDepartingBlock());
		assertEquals(hh,test2.getDepartingBlock());
		assertEquals(ii,test3.getDepartingBlock());
	}
	
	@Test
	public void testGetCompatibleArrivingBlocks(){
		assertEquals(1,test1.getCompatibleArrivingBlocks().size());
		assertEquals(1,test2.getCompatibleArrivingBlocks().size());
		assertEquals(3,test3.getCompatibleArrivingBlocks().size());

		assertEquals(true,dd.checkEqual(test1.getCompatibleArrivingBlocks().get(0)));
		assertEquals(true,dd.checkEqual(test2.getCompatibleArrivingBlocks().get(0)));
		assertEquals(true,dd.checkEqual(test3.getCompatibleArrivingBlocks().get(0)));
		assertEquals(true,ee.checkEqual(test3.getCompatibleArrivingBlocks().get(1)));
		assertEquals(true,ff.checkEqual(test3.getCompatibleArrivingBlocks().get(2)));
	}
	
}
