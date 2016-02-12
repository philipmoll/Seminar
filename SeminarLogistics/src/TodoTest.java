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
	private FinalBlock fb1;
	private FinalBlock fb2;
	private FinalBlock fb3;

	private Todo todo;
	private Track p1;
	private Track p2;
	private Track w1;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		t1 = new Train(1, 1, 4);
		t2 = new Train(2, 2, 6);
		t3 = new Train(3, 1, 6);
		t4 = new Train(4, 3, 4);
		t1.toggleCleaning();t1.toggleInspecting();t1.toggleWashing();
		t2.toggleCleaning();t2.toggleInspecting();
		t3.toggleCleaning();t3.toggleInspecting();
		t4.toggleCleaning();t4.toggleWashing();
		c1 = new Composition(new ArrayList<Train>(){{add(t1);}}, 0.006, 1);
		c2 = new Composition(new ArrayList<Train>(){{add(t2);}}, 0 , 0.5);
		c3 = new Composition(new ArrayList<Train>(){{add(t3);add(t4);}}, 0.02, 0.8);
		p1 = new Track("p1", 500, 1, 1, 1, 1, 0);
		p2 = new Track("p2", 500, 1, 1, 1, 1, 0);
		w1 = new Track("p3", 500, 1, 0, 0, 0, 1);
		ArrayList<Composition> list = new ArrayList(){{add(c1);add(c2);add(c3);}};
		todo = new Todo(new ArrayList<Track>(){{add(p1);add(p2);add(w1);}}, list, list);
		
		fb1 = new FinalBlock(new ArrayList<Train>(){{add(t1);}}, 0.006, 1, c1, c1, -1, -1, -1, -1);
		fb2 = new FinalBlock(new ArrayList<Train>(){{add(t2);}}, 0, 0.5, c1, c1, -1, -1, -1, -1);
		fb3 = new FinalBlock(new ArrayList<Train>(){{add(t3);add(t4);}}, 0.02, 0.8, c1, c1, -1, -1, -1, -1);

	}

	@Test
	public void test() throws IOException {

		todo.addComposition(fb1);
		todo.addComposition(fb2);
		todo.addComposition(fb3);
		System.out.print("Block 1:       ");
		fb1.printTimeLine();
		System.out.print("\n");
		System.out.print("Block 2:       ");
		fb2.printTimeLine();
		System.out.print("\n");
		System.out.print("Block 3:       ");
		fb3.printTimeLine();
		System.out.print("\n");
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
