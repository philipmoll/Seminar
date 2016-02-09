import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class MatchingTest implements Serializable{

	private Train a;
	private Train b;
	private Train c;
	private Train x;
	private Train y;
	private Train z;
	private Composition d;
	private Composition e;
	private Composition f;
	private ArrayList<Composition> compositionlist;
	private ArrayList<Block> testblocklist;
	
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
			compositionlist.add(d);
			compositionlist.add(e);
			compositionlist.add(f);
			
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
			
			testblocklist = new ArrayList<Block>(){{add(aa);add(bb);add(cc);add(dd);add(ee);add(ff);add(gg);add(hh);add(ii);add(jj);}};

		} catch (IndexOutOfBoundsException | IOException e2) {
			e2.printStackTrace();
		}
	}
	
	@Test
	public void testMakeblocks(){
		try {
			ArrayList<Block> blocklist = new ArrayList<Block>();
			blocklist = Matching.makeblocks(compositionlist);
			
			for (int i=0; i<blocklist.size();i++){
				boolean check = false;
				for (int j = 0; j<testblocklist.size();j++){
					if (blocklist.get(i).checkEqual(testblocklist.get(j)) == true){
						//System.out.println("blocklist "+i+" equal to testblocklist "+j);
						check = true;
					}
					else{
						//System.out.println("blocklist "+i+" not equal to testblocklist "+j);

					}
				}
				assertEquals(true,check);
				System.out.println(" ");
			}
			
			
		} catch (IndexOutOfBoundsException | MisMatchException | TrackNotFreeException | IOException | CloneNotSupportedException e) {
			e.printStackTrace();
		}
		

	}

}
