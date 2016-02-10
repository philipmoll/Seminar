import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */

/**
 * @author
 *
 */
public class TodoTest {

	private Train t1;
	private Train t2;
	private Train t3; 
	private Train t4;
	private Composition c1;
	private Composition c2;
	private Composition c3;
	private Todo todo;
	private Track p1;
	private Track p2;
	private Track w1;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		t1 = new Train(1, 1, true, true, true, true, true);
		t2 = new Train(2, 2, true, false, true, true, true);
		t3 = new Train(3, 1, true, true, true, true, true);
		t4 = new Train(4, 2, true, true, true, true, true);
		c1 = new Composition(new ArrayList<Train>(){{add(t1);}}, 0, 1);
		c2 = new Composition(new ArrayList<Train>(){{add(t2);}}, 0, 1);
		c3 = new Composition(new ArrayList<Train>(){{add(t3);add(t4);}}, 0, 1);
		p1 = new Track("p1", 500, 1, 1, 1, 1, 0);
		p2 = new Track("p2", 500, 1, 1, 1, 1, 0);
		w1 = new Track("p3", 500, 1, 0, 0, 0, 1);
		todo = new Todo(new ArrayList<Track>(){{add(p1);add(p2);add(w1);}});
	}

	@Test
	public void test() throws IOException {

		todo.addComposition(c1);
		todo.addComposition(c2);
//		todo.addComposition(c3);
		System.out.print("Composition 1: ");
		c1.printTimeLine();
		System.out.print("\n");
		System.out.print("Composition 2: ");
		c2.printTimeLine();
		System.out.print("\n");
//		System.out.print("Composition 3: ");
//		c3.printTimeLine();
//		System.out.print("\n");
		System.out.print("Platform 1:    ");
		p1.printTimeLine();
		System.out.print("\n");
		System.out.print("Platform 2:    ");
		p2.printTimeLine();
		System.out.print("\n");
		System.out.print("Wash area 1:   ");
		w1.printTimeLine();
		System.out.print("\n");
		System.out.print("Move list:     ");
		todo.printTimeLine();
	}
}
