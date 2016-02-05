import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MatchingTest {

	private Train a;
	private Train b;
	private Train c;
	private Train x;
	private Train y;
	private Train z;
	private Composition d;
	private Composition e;
	private Composition f;
	private Composition v;
	private Composition w;
	private ArrayList<Composition> compositionlist;
	private ArrayList<Block> testblocklist;
	//private Matching matchtest;
	
	private Block aa;
	private Block bb;
	private Block cc;
	private Block dd;
	private Block ee;
	private Block ff;
	private Block gg;
	private Block hh;
	private Block ii;
	private Block jj;

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

			d = new Composition(new ArrayList<Train>(){{add(a);}},0,-1);
			e = new Composition(new ArrayList<Train>(){{add(b); add(c);}},0.1,-1);
			f = new Composition(new ArrayList<Train>(){{add(x); add(y); add(z);}},0.5,-1);

			compositionlist = new ArrayList<>();
			//compositionlist.add(d);
			compositionlist.add(e);
			//compositionlist.add(f);
			
			aa = new Block(new ArrayList<Train>(){{add(a);}},0,-1,d,-1,0);
			bb = new Block(new ArrayList<Train>(){{add(b); add(c);}},0.1,-1,e,-1,1);
			cc = new Block(new ArrayList<Train>(){{add(b);}},0.1,-1,e,-1,0);
			dd = new Block(new ArrayList<Train>(){{add(c);}},0.1,-1,e,0,1);
			ee = new Block(new ArrayList<Train>(){{add(x); add(y); add(z);}},0.5,-1,f,-1,2);
			ff = new Block(new ArrayList<Train>(){{add(x);}},0.5,-1,f,-1,0);
			gg = new Block(new ArrayList<Train>(){{add(y); add(z);}},0.5,-1,f,0,2);
			hh = new Block(new ArrayList<Train>(){{add(y);}},0.5,-1,f,0,1);
			ii = new Block(new ArrayList<Train>(){{add(z);}},0.5,-1,f,1,2);
			jj = new Block(new ArrayList<Train>(){{add(x); add(y);}},0.5,-1,f,-1,1);
			
			testblocklist = new ArrayList<>();
			testblocklist.add(aa);
			testblocklist.add(bb);
			testblocklist.add(cc);
			testblocklist.add(dd);
			testblocklist.add(ee);
			testblocklist.add(ff);
			testblocklist.add(gg);
			testblocklist.add(hh);
			testblocklist.add(ii);
			testblocklist.add(jj);
			//matchtest = new Matching();
			
			v = new Composition(new ArrayList<Train>(){{add(x);}});
			w = new Composition(new ArrayList<Train>(){{add(y); add(z);}});

		} catch (IndexOutOfBoundsException | IOException e2) {
			e2.printStackTrace();
		}
	}
	
	@Test
	public void testMakeblocks(){
		try {
			ArrayList<Block> blocklist = new ArrayList<Block>();
			blocklist = Matching.makeblocks(compositionlist);
			for (int i = 0; i <blocklist.size(); i++){
				System.out.println(blocklist.get(i).getTrainList());
			}
			System.out.println("hoi");
			for (int i = 0; i <testblocklist.size(); i++){
				System.out.println(testblocklist.get(i).getTrainList());
			}
						
			System.out.println("hoi2");
			for (int i=0; i<3;i++){
				boolean check = false;
				for (int j = 0; j<testblocklist.size();j++){
					if (blocklist.get(i).checkEqual(testblocklist.get(j)) == true){
						System.out.println("blocklist "+i+" equal to testblocklist "+j);
						check = true;
					}
					else{
						System.out.println("blocklist "+i+" not equal to testblocklist "+j);

					}
				}
				//assertEquals(true,check);
				System.out.println(" ");
			}
			
			
		} catch (IndexOutOfBoundsException | MisMatchException | TrackNotFreeException | IOException | CloneNotSupportedException e) {
			e.printStackTrace();
		}
		

	}

}
