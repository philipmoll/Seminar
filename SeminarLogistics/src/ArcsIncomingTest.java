import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ArcsIncomingTest {
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
			
			g = new ArcsIncoming(d,0);
			h = new ArcsIncoming(f,0);
			i = new ArcsIncoming(f,1);
			j = new ArcsIncoming(f,2);
			
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
			
		} catch (IOException e) {
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
	
	
}
