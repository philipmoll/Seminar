import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
public class NetworkTest {

	private Network testnetwork;
	
	@Before
	public void setUp()  {
		testnetwork = new Network();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetNetwork() {
		int test1 = (int) testnetwork.getConnections().getElement(0,0); 
		assertEquals(test1,1);
		int test2 = (int) testnetwork.getConnections().getElement(5,2);
		assertEquals(test2,0);
		int test3 = (int) testnetwork.getConnections().getElement(9,8);
		assertEquals(test3,1);
		int test4 = (int) testnetwork.getConnections().getElement(15,15);
		assertEquals(test4,1);
	}

}
