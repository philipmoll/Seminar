import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ArcsTest {
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
			e = new Composition(new ArrayList<Train>(){{add(b);add(c);}},0.3,-1);
			f = new Composition(new ArrayList<Train>(){{add(x);add(y);add(z);}},0.5,-1);
			
			g = new Arcs(d);
			h = new Arcs(e);
			i = new Arcs(f);
			
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
			
		} catch (IOException e) {
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
	
}
