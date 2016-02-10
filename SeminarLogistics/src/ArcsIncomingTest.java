import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("serial")
public class ArcsIncomingTest implements Serializable{
	private Train a;
	private Train x;
	private Train y;
	private Train z;
	private Composition d;
	private Composition f;
	private ArcsIncoming g;
	private ArcsIncoming h;
	private ArcsIncoming i;
	private ArcsIncoming j;
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
	public void setUp(){
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
			
			g = new ArcsIncoming(d,0,arrivingblocklist);
			h = new ArcsIncoming(f,0,arrivingblocklist);
			i = new ArcsIncoming(f,1,arrivingblocklist);
			j = new ArcsIncoming(f,2,arrivingblocklist);
			
//			System.out.println("Blocklist: ");
//			for (int i = 0; i<arrivingblocklist.size(); i++){
//				System.out.println(arrivingblocklist.get(i));
//			}
//			
//			System.out.println("g get blocks: ");
//			for (int i = 0; i<g.getBlocks().length; i++){
//				System.out.println(g.getBlocks()[i]);
//			}
//			System.out.println("h get blocks: ");
//			for (int i = 0; i<h.getBlocks().length; i++){
//				System.out.println(h.getBlocks()[i]);
//			}
//			System.out.println("i get blocks: ");
//			for (int j = 0; j<i.getBlocks().length; j++){
//				System.out.println(i.getBlocks()[j]);
//			}
//			System.out.println("j get blocks: ");
//			for (int i = 0; i<j.getBlocks().length; i++){
//				System.out.println(j.getBlocks()[i]);
//			}
			
			gg = new Block[1];
			gg[0]=o;
			hh = new Block[1];
			hh[0]=q;
			ii = new Block[2];
			ii[0]=u;
			ii[1]=s;
			jj = new Block[3];
			jj[0]=p;
			jj[1]=r;
			jj[2]=t;
			
			k = new int[1][2];
			k[0][0]=-1;
			k[0][1]=0;
			l = new int[1][2];
			l[0][0]=-1;
			l[0][1]=0;
			m = new int[2][2];
			m[0][0]=-1;
			m[0][1]=1;
			m[1][0]=0;
			m[1][1]=1;
			n = new int[3][2];
			n[0][0]=-1;
			n[0][1]=2;
			n[1][0]=0;
			n[1][1]=2;
			n[2][0]=1;
			n[2][1]=2;
			
			
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
		assertEquals(0,g.getNode());
		assertEquals(0,h.getNode());
		assertEquals(1,i.getNode());
		assertEquals(2,j.getNode());
		assertArrayEquals(k,g.getArcsIncoming());
		assertArrayEquals(l,h.getArcsIncoming());
		assertArrayEquals(m,i.getArcsIncoming());
		assertArrayEquals(n,j.getArcsIncoming());
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
		assertEquals(0,g.getNode());
		assertEquals(0,h.getNode());
		assertEquals(1,i.getNode());
		assertEquals(2,j.getNode());
	}
	
	@Test
	public void testGetArcsIncoming(){
		assertArrayEquals(k,g.getArcsIncoming());
		assertArrayEquals(l,h.getArcsIncoming());
		assertArrayEquals(m,i.getArcsIncoming());
		assertArrayEquals(n,j.getArcsIncoming());
	}
	
	@Test
	public void testGetBlocks(){
		assertArrayEquals(gg,g.getBlocks());
		assertArrayEquals(hh,h.getBlocks());
		assertArrayEquals(ii,i.getBlocks());
		assertArrayEquals(jj,j.getBlocks());
	}
	
}
