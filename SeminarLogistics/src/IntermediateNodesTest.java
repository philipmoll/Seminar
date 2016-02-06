import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class IntermediateNodesTest {
	private Train a;
	private Train x;
	private Train y;
	private Train z;
	private Composition d;
	private Composition f;
	private IntermediateNodes g;
	private IntermediateNodes h;
	private int[] k;
	private int[] l;

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
			
			g = new IntermediateNodes(d);
			h = new IntermediateNodes(f);

			k = new int[0];
			l = new int[2];
			l[0]=0;
			l[1]=1;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConstructor(){
		assertEquals(d,g.getParent());
		assertEquals(f,h.getParent());
		assertArrayEquals(k,g.getIntermediateNodes());
		assertArrayEquals(l,h.getIntermediateNodes());
	}
	
	@Test
	public void testGetParent(){
		assertEquals(d,g.getParent());
		assertEquals(f,h.getParent());
	}
	
	@Test
	public void testGetIntermediateNodes(){
		assertArrayEquals(k,g.getIntermediateNodes());
		assertArrayEquals(l,h.getIntermediateNodes());
	}
	
}
