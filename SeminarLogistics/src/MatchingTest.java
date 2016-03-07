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
//public class MatchingTest implements Serializable{
//
//	private Train a;
//	private Train b;
//	private Train c;
//	private Train x;
//	private Train y;
//	private Train z;
//	private Composition d;
//	private Composition e;
//	private Composition f;
//	private ArrayList<Composition> compositionlist;
//	private ArrayList<Block> testblocklist;
//	
//	private Block aa;
//	private Block bb;
//	private Block cc;
//	private Block dd;
//	private Block ee;
//	private Block ff;
//	private Block gg;
//	private Block hh;
//	private Block ii;
//	private Block jj;
//	
//	
//	private Train a0;
//	private Train a1;
//	private Train a2;
//	private Train a3;
//	private Train a4;
//	private Train a5;
//	private Train a6;
//	private Train a7;
//	private Train d0;
//	private Train d1;
//	private Train d2;
//	private Train d3;
//	private Train d4;
//	private Train d5;
//	private Train d6;
//	private Train d7;
//	
//	private Composition ac1;
//	private Composition ac2;
//	private Composition ac3;
//	private Composition dc1;
//	private Composition dc2;
//	private Composition dc3;
//	private Composition dc4;
//	
//	private ArrayList<Composition> arrivingcompositions;
//	private ArrayList<Composition> departingcompositions;
//	
//	//private ArrayList<Block> arrivingblocks;
//	//private ArrayList<Block> departingblocks;
//	
//	private boolean[][] z_test;
//
//	@Before
//	public void setUp(){
//		try {
//			a = new Train(1,2,1,6);
//			b = new Train(2,2,2,4);
//			c = new Train(3,1,3,4);
//			x = new Train(4,2,1,6);
//			y = new Train(5,2,2,4);
//			z = new Train(6,1,3,4);
//
//			d = new Composition(new ArrayList<Train>(){{add(a);}},0,-1);
//			e = new Composition(new ArrayList<Train>(){{add(b); add(c);}},0.1,-1);
//			f = new Composition(new ArrayList<Train>(){{add(x); add(y); add(z);}},0.5,-1);
//
//			compositionlist = new ArrayList<>();
//			compositionlist.add(d);
//			compositionlist.add(e);
//			compositionlist.add(f);
//			
//			aa = new Block(new ArrayList<Train>(){{add(a);}},0,-1,d,-1,0);
//			bb = new Block(new ArrayList<Train>(){{add(b); add(c);}},0.1,-1,e,-1,1);
//			cc = new Block(new ArrayList<Train>(){{add(b);}},0.1,-1,e,-1,0);
//			dd = new Block(new ArrayList<Train>(){{add(c);}},0.1,-1,e,0,1);
//			ee = new Block(new ArrayList<Train>(){{add(x); add(y); add(z);}},0.5,-1,f,-1,2);
//			ff = new Block(new ArrayList<Train>(){{add(x);}},0.5,-1,f,-1,0);
//			gg = new Block(new ArrayList<Train>(){{add(y); add(z);}},0.5,-1,f,0,2);
//			hh = new Block(new ArrayList<Train>(){{add(y);}},0.5,-1,f,0,1);
//			ii = new Block(new ArrayList<Train>(){{add(z);}},0.5,-1,f,1,2);
//			jj = new Block(new ArrayList<Train>(){{add(x); add(y);}},0.5,-1,f,-1,1);
//			
//			testblocklist = new ArrayList<Block>(){{add(aa);add(bb);add(cc);add(dd);add(ee);add(ff);add(gg);add(hh);add(ii);add(jj);}};
//
//			
//			
//			
//			a0 = new Train(0,1,1,6); //a: 1,1
//			a1 = new Train(0,2,2,6); //b: 2,2
//			a2 = new Train(0,3,1,6); //c: 3,1
//			a3 = new Train(0,1,2,6); //d: 1,2
//			a4 = new Train(0,3,1,6); //c: 3,1
//			a5 = new Train(0,3,1,6);
//			a6 = new Train(0,3,1,6);
//			a7 = new Train(0,3,1,6);
//			
//			d0 = new Train(0,3,1,6); 
//			d1 = new Train(0,1,2,6);
//			d2 = new Train(0,1,1,6);
//			d3 = new Train(0,3,1,6);
//			d4 = new Train(0,2,2,6);
//			d5 = new Train(0,3,1,6);
//			d6 = new Train(0,3,1,6);
//			d7 = new Train(0,3,1,6);
//			
//			ac1 = new Composition(new ArrayList<Train>(){{add(a0);add(a1);}},0,-1);
//			ac2 = new Composition(new ArrayList<Train>(){{add(a2);add(a3);add(a4);}},0,-1);
//			ac3 = new Composition(new ArrayList<Train>(){{add(a5);add(a6);add(a7);}},0,-1);
//			
//			dc1 = new Composition(new ArrayList<Train>(){{add(d0);add(d1);}},-1,1);
//			dc2 = new Composition(new ArrayList<Train>(){{add(d2);add(d3);}},-1,1);
//			dc3 = new Composition(new ArrayList<Train>(){{add(d4);}},-1,1);
//			dc4 = new Composition(new ArrayList<Train>(){{add(d5);add(d6);add(d7);}},-1,1);
//			
//			arrivingcompositions = new ArrayList<Composition>(){{add(ac1);add(ac2);add(ac3);}};
//			departingcompositions = new ArrayList<Composition>(){{add(dc1);add(dc2);add(dc3);add(dc4);}};
//			
//			z_test = new boolean[15][13];
//			z_test[1][4]=true;
//			z_test[2][6]=true;
//			z_test[8][0]=true;
//			z_test[7][5]=true;
//			z_test[9][7]=true;
//			
//		} catch (IndexOutOfBoundsException | IOException e2) {
//			e2.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testConstructor(){
//		try {
//			Matching matchtest = new Matching(arrivingcompositions,departingcompositions);
//			boolean[][] z_matchtest = matchtest.getZ();
//			assertEquals(15,z_matchtest.length);
//			assertEquals(13,z_matchtest[0].length);
//			
//			for (int i = 0; i<15; i++){
//				for (int j = 0; j<13; j++){
////					System.out.println("z("+i+","+j+") should be "+z_test[i][j]+" and is "+z_matchtest[i][j]);
//					assertEquals(z_test[i][j],z_matchtest[i][j]);
//				}
//			}
//			
//		} catch (IndexOutOfBoundsException | MisMatchException | TrackNotFreeException | IOException
//				| CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	@Test
//	public void testMakeblocks(){
//		try {
//			ArrayList<Block> blocklist = new ArrayList<Block>();
//			blocklist = Matching.makeblocks(compositionlist);
//			
//			for (int i=0; i<blocklist.size();i++){
//				boolean check = false;
//				for (int j = 0; j<testblocklist.size();j++){
//					if (blocklist.get(i).checkEqual(testblocklist.get(j)) == true){
//						//System.out.println("blocklist "+i+" equal to testblocklist "+j);
//						check = true;
//					}
//					else{
//						//System.out.println("blocklist "+i+" not equal to testblocklist "+j);
//
//					}
//				}
//				assertEquals(true,check);
//			}
//			
//			
//		} catch (IndexOutOfBoundsException | MisMatchException | TrackNotFreeException | IOException | CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
//		
//
//	}
//
//}