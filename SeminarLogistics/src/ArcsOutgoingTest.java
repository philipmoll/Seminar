import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ArcsOutgoingTest {
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
			
			g = new ArcsOutgoing(d,-1);
			h = new ArcsOutgoing(f,-1);
			i = new ArcsOutgoing(f,0);
			j = new ArcsOutgoing(f,1);
			
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
		assertEquals(-1,g.getNode());
		assertEquals(-1,h.getNode());
		assertEquals(0,i.getNode());
		assertEquals(1,j.getNode());
		assertArrayEquals(k,g.getArcsOutgoing());
		assertArrayEquals(l,h.getArcsOutgoing());
		assertArrayEquals(m,i.getArcsOutgoing());
		assertArrayEquals(n,j.getArcsOutgoing());
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
	
	
}

