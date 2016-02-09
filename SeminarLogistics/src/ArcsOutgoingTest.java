import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class ArcsOutgoingTest implements Serializable{
	private Train a;
	private Train x;
	private Train y;
	private Train z;
	private Composition d;
	private Composition f;
	private ArcsOutgoing g;
	private ArcsOutgoing h;
	private ArcsOutgoing i;
	private ArcsOutgoing j;
	private int[][] k;
	private int[][] l;
	private int[][] m;
	private int[][] n;
	private ArrayList<Composition> arrivingcompositions;
	private ArrayList<Block> arrivingblocklist;
	private Block o;
	private Block p;
	private Block q;
	private Block r;
	private Block s;
	private Block t;
	private Block u;
	private Block[] gg;
	private Block[] hh;
	private Block[] ii;
	private Block[] jj;

	@Before
	public void setUp() {
		try {
			a = new Train(1,2,1,6);;
			x = new Train(4,2,1,6);
			y = new Train(5,2,2,4);
			z = new Train(6,1,3,4);

			d = new Composition(new ArrayList<Train>(){{add(a);}},0,-1);
			f = new Composition(new ArrayList<Train>(){{add(x); add(y); add(z);}},0.5,-1);
			
			
			arrivingcompositions = new ArrayList<Composition>(){{add(d); add(f);}};
			arrivingblocklist = Matching.makeblocks(arrivingcompositions);
			o = arrivingblocklist.get(0); //complete d
			p = arrivingblocklist.get(1); //complete f
			q = arrivingblocklist.get(2); //x
			r = arrivingblocklist.get(3); //y,z
			s = arrivingblocklist.get(4); //y
			t = arrivingblocklist.get(5); //z
			u = arrivingblocklist.get(6); //x,y
			
			g = new ArcsOutgoing(d,-1,arrivingblocklist);
			h = new ArcsOutgoing(f,-1,arrivingblocklist);
			i = new ArcsOutgoing(f,0,arrivingblocklist);
			j = new ArcsOutgoing(f,1,arrivingblocklist);
			
			for (int i = 0; i < arrivingblocklist.size(); i++){
				System.out.println(arrivingblocklist.get(i).getCutPosition1()+" "+arrivingblocklist.get(i).getCutPosition2());
				System.out.println(arrivingblocklist.get(i));
			}

			
			gg = new Block[1];
			gg[0]=o;
			hh = new Block[3]; //pqu, uqp, qpu, qup, upq
			hh[0]=q;
			hh[1]=u;
			hh[2]=p;
			ii = new Block[2];
			ii[0]=s;
			ii[1]=r;
			jj = new Block[1];
			jj[0]=t;
			
			k = new int[1][2];
			k[0][0]=-1;
			k[0][1]=0;
			l = new int[3][2];
			l[0][0]=-1;
			l[0][1]=0;
			l[1][0]=-1;
			l[1][1]=1;
			l[2][0]=-1;
			l[2][1]=2;
			m = new int[2][2];
			m[0][0]=0;
			m[0][1]=1;
			m[1][0]=0;
			m[1][1]=2;
			n = new int[1][2];
			n[0][0]=1;
			n[0][1]=2;
			
		} catch (IOException | IndexOutOfBoundsException | MisMatchException | TrackNotFreeException | CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConstructor(){
		assertEquals(d,g.getParent());
		assertEquals(f,h.getParent());
		assertEquals(f,i.getParent());
		assertEquals(f,j.getParent());
		assertEquals(-1,g.getNode());
		assertEquals(-1,h.getNode());
		assertEquals(0,i.getNode());
		assertEquals(1,j.getNode());
		assertArrayEquals(k,g.getArcsOutgoing());
		assertArrayEquals(l,h.getArcsOutgoing());
		assertArrayEquals(m,i.getArcsOutgoing());
		assertArrayEquals(n,j.getArcsOutgoing());
		assertArrayEquals(gg,g.getBlocks());
		assertArrayEquals(hh,h.getBlocks());
		assertArrayEquals(ii,i.getBlocks());
		assertArrayEquals(jj,j.getBlocks());
	}
	
	@Test
	public void testGetParent(){
		assertEquals(d,g.getParent());
		assertEquals(f,h.getParent());
		assertEquals(f,i.getParent());
		assertEquals(f,j.getParent());
	}
	
	@Test
	public void testGetNode(){
		assertEquals(-1,g.getNode());
		assertEquals(-1,h.getNode());
		assertEquals(0,i.getNode());
		assertEquals(1,j.getNode());
	}
	
	@Test
	public void testGetArcsOutgoing(){
		assertArrayEquals(k,g.getArcsOutgoing());
		assertArrayEquals(l,h.getArcsOutgoing());
		assertArrayEquals(m,i.getArcsOutgoing());
		assertArrayEquals(n,j.getArcsOutgoing());
	}
	
	@Test
	public void testGetBlocks(){
		assertArrayEquals(gg,g.getBlocks());
		assertArrayEquals(hh,h.getBlocks());
		assertArrayEquals(ii,i.getBlocks());
		assertArrayEquals(jj,j.getBlocks());
	}
	
}

