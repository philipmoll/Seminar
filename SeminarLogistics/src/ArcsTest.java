import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class ArcsTest implements Serializable{
	private Train a;
	private Train b;
	private Train c;
	private Train x;
	private Train y;
	private Train z;
	private Composition d;
	private Composition e;
	private Composition f;
	private Arcs g;
	private Arcs h;
	private Arcs i;
	private int[][] k;
	private int[][] l;
	private int[][] m;
	private ArrayList<Composition> arrivingcompositions;
	private ArrayList<Block> arrivingblocklist;
	private Block o;
	private Block p;
	private Block q;
	private Block r;
	private Block s;
	private Block t;
	private Block u;
	private Block v;
	private Block w;
	private Block ww;
	private Block[] gg;
	private Block[] hh;
	private Block[] ii;


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
			e = new Composition(new ArrayList<Train>(){{add(b);add(c);}},0.3,-1);
			f = new Composition(new ArrayList<Train>(){{add(x);add(y);add(z);}},0.5,-1);
			
			arrivingcompositions = new ArrayList<Composition>(){{add(d); add(f); add(e);}};
			arrivingblocklist = Matching.makeblocks(arrivingcompositions);
			o = arrivingblocklist.get(0); //complete d
			p = arrivingblocklist.get(1); //complete f
			q = arrivingblocklist.get(2); //x
			r = arrivingblocklist.get(3); //y,z
			s = arrivingblocklist.get(4); //y
			t = arrivingblocklist.get(5); //z
			u = arrivingblocklist.get(6); //x,y
			v = arrivingblocklist.get(7); //complete e
			w = arrivingblocklist.get(8); //b
			ww = arrivingblocklist.get(9); //c
			
			g = new Arcs(d,arrivingblocklist);
			h = new Arcs(e,arrivingblocklist);
			i = new Arcs(f,arrivingblocklist);
			
			gg = new Block[1];
			gg[0]=o;
			hh = new Block[3];
			hh[0]=v;
			hh[1]=w;
			hh[2]=ww;
			ii = new Block[6];
			ii[0]=p;
			ii[1]=q;
			ii[2]=r;
			ii[3]=s;
			ii[4]=t;
			ii[5]=u;
			
			k = new int[1][2];
			k[0][0]=-1;
			k[0][1]=0;
			
			l = new int[3][2];
			l[0][0]=-1;
			l[0][1]=0;
			l[1][0]=-1;
			l[1][1]=1;
			l[2][0]=0;
			l[2][1]=1;
			
			m = new int[6][2];
			m[0][0]=-1;
			m[0][1]=0;
			m[1][0]=-1;
			m[1][1]=1;
			m[2][0]=-1;
			m[2][1]=2;
			m[3][0]=0;
			m[3][1]=1;
			m[4][0]=0;
			m[4][1]=2;
			m[5][0]=1;
			m[5][1]=2;
			
		} catch (IOException | IndexOutOfBoundsException | MisMatchException | TrackNotFreeException | CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConstructor(){
		assertEquals(d,g.getParent());
		assertEquals(e,h.getParent());
		assertEquals(f,i.getParent());
		
		assertArrayEquals(k,g.getArcs());
		assertArrayEquals(l,h.getArcs());
		assertArrayEquals(m,i.getArcs());
		
		assertArrayEquals(gg,g.getBlocks());
		assertArrayEquals(hh,h.getBlocks());
		assertArrayEquals(ii,i.getBlocks());
		
	}
	
	@Test
	public void testGetParent(){
		assertEquals(d,g.getParent());
		assertEquals(e,h.getParent());
		assertEquals(f,i.getParent());
	}
	
	@Test
	public void testGetArcs(){
		assertArrayEquals(k,g.getArcs());
		assertArrayEquals(l,h.getArcs());
		assertArrayEquals(m,i.getArcs());       
	}
	
	@Test
	public void testGetBlocks(){
		assertArrayEquals(gg,g.getBlocks());
		assertArrayEquals(hh,h.getBlocks());
		assertArrayEquals(ii,i.getBlocks());
	}
	
}
