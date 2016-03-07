//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//
//import org.junit.Before;
//import org.junit.Test;
//
//@SuppressWarnings("serial")
//public class CompatibleArrivingBlocksTest implements Serializable{
//	private Train a;
//	private Train b;
//	private Train c;
//	private Train x;
//	private Train y;
//	private Train z;
//	
//	private Composition d;
//	private Composition e;
//	private Composition f;
//	private Composition g;
//	private Composition h;
//	private Composition i;
//	
//	private ArrayList<Composition> arrivingcompositions;
//	//private ArrayList<Composition> departingcompositions;
//	
//	private ArrayList<Block> arrivingblocks;
//	//private ArrayList<Block> departingblocks;
//
//	private Block dd;
//	private Block ee;
//	private Block ff;
//	private Block gg;
//	private Block hh;
//	private Block ii;
//	
//	private CompatibleArrivingBlocks test1;
//	private CompatibleArrivingBlocks test2;
//	private CompatibleArrivingBlocks test3;
//	
//	@Before
//	public void setUp() {
//		try {
//			a = new Train(1,2,1,6);
//			b = new Train(2,2,2,4);
//			c = new Train(3,2,2,4);
//			x = new Train(4,2,1,6);
//			y = new Train(5,2,2,4);
//			z = new Train(6,2,2,4);
//
//			d = new Composition(new ArrayList<Train>(){{add(a);}},0,-1);
//			e = new Composition(new ArrayList<Train>(){{add(b);}},0.3,-1);
//			f = new Composition(new ArrayList<Train>(){{add(c);}},0.5,-1);
//			g = new Composition(new ArrayList<Train>(){{add(x);}},-1,0.15);
//			h = new Composition(new ArrayList<Train>(){{add(y);}},-1,0.38);
//			i = new Composition(new ArrayList<Train>(){{add(z);}},-1,0.7);
//			
//			dd = new Block(new ArrayList<Train>(){{add(a);}},0,-1,d,-1,0);
//			ee = new Block(new ArrayList<Train>(){{add(b);}},0.3,-1,e,-1,0);
//			ff = new Block(new ArrayList<Train>(){{add(c);}},0.5,-1,f,-1,0);
//			gg = new Block(new ArrayList<Train>(){{add(x);}},-1,0.15,g,-1,0);
//			hh = new Block(new ArrayList<Train>(){{add(y);}},-1,0.38,h,-1,0);
//			ii = new Block(new ArrayList<Train>(){{add(z);}},-1,0.7,i,-1,0);
//			
//			arrivingcompositions = new ArrayList<Composition>(){{add(d);add(e);add(f);}};
//			//departingcompositions = new ArrayList<Composition>(){{add(g);add(h);add(i);}};
//			
//			arrivingblocks = Matching.makeblocks(arrivingcompositions);
//			//departingblocks = Matching.makeblocks(departingcompositions);
////			System.out.println(dd.getTrainList());
////			System.out.println(ee.getTrainList());
//			test1 = new CompatibleArrivingBlocks(gg, arrivingblocks);
//			test2 = new CompatibleArrivingBlocks(hh, arrivingblocks);
////			System.out.println(test1.getCompatibleArrivingBlocks().get(0).getTrainList());
////			System.out.println(test2.getCompatibleArrivingBlocks().get(0).getTrainList());
//			test3 = new CompatibleArrivingBlocks(ii, arrivingblocks);
//		} catch (IndexOutOfBoundsException | MisMatchException | TrackNotFreeException
//				| CloneNotSupportedException | IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testConstructor(){
//		//NB: tests may fail when we change c
//		assertEquals(1,test1.getCompatibleArrivingBlocks().size());
//		assertEquals(0,test2.getCompatibleArrivingBlocks().size());
//		assertEquals(2,test3.getCompatibleArrivingBlocks().size());
//
//		assertEquals(a,test1.getCompatibleArrivingBlocks().get(0).getTrainList().get(0));
//		assertEquals(b,test3.getCompatibleArrivingBlocks().get(0).getTrainList().get(0));
//		assertEquals(c,test3.getCompatibleArrivingBlocks().get(1).getTrainList().get(0));
//		
//		
//		assertEquals(gg,test1.getDepartingBlock());
//		assertEquals(hh,test2.getDepartingBlock());
//		assertEquals(ii,test3.getDepartingBlock());
//	}
//	
//	@Test
//	public void testGetDepartingBlock(){
//		assertEquals(gg,test1.getDepartingBlock());
//		assertEquals(hh,test2.getDepartingBlock());
//		assertEquals(ii,test3.getDepartingBlock());
//	}
//	
//	@Test
//	public void testGetCompatibleArrivingBlocks(){
//		assertEquals(1,test1.getCompatibleArrivingBlocks().size());
//		assertEquals(0,test2.getCompatibleArrivingBlocks().size());
//		assertEquals(2,test3.getCompatibleArrivingBlocks().size());
//
//		assertEquals(a,test1.getCompatibleArrivingBlocks().get(0).getTrainList().get(0));
//		assertEquals(b,test3.getCompatibleArrivingBlocks().get(0).getTrainList().get(0));
//		assertEquals(c,test3.getCompatibleArrivingBlocks().get(1).getTrainList().get(0
//				));
//		
//		
//	}
//	
//}
