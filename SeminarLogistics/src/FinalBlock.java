import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Floor Wofhagen
 *
 */
@SuppressWarnings("serial")
public class FinalBlock extends Composition { //TODO: test

	private Composition origincomposition;
	private Composition destinationcomposition;
	private int cutpositionarr1;
	private int cutpositionarr2;
	private int cutpositiondep1;
	private int cutpositiondep2;
	
	/**
	 * @param compositiontrains
	 * @param arrivaltime
	 * @param departuretime
	 * @param origincomposition
	 * @param destinationcomposition
	 * @param cutpositionarr1
	 * @param cutpositionarr2
	 * @param cutpositiondep1
	 * @param cutpositiondep2 
	 * @param cutpositiondep2
	 * @throws IOException
	 */
	public FinalBlock(ArrayList<Train> compositiontrains, double arrivaltime, double departuretime,
			Composition origincomposition, Composition destinationcomposition, int cutpositionarr1, int cutpositionarr2, int cutpositiondep1, int cutpositiondep2) throws IOException {
		super(compositiontrains, arrivaltime, departuretime);
	
		this.origincomposition = origincomposition;
		this.destinationcomposition = destinationcomposition;
		this.cutpositionarr1 = cutpositionarr1;
		this.cutpositionarr2 = cutpositionarr2;
		this.cutpositiondep1 = cutpositiondep1;
		this.cutpositiondep2 = cutpositiondep2;
	}

	/**
	 * @return the origincomposition
	 */
	public Composition getOrigincomposition() {
		return origincomposition;
	}

	/**
	 * Returns destinationcomposition
	 * @return the destinationcomposition
	 */
	public Composition getDestinationcomposition() {
		return destinationcomposition;
	}

	/**
	 * @return the cutpositionarr1
	 */
	public int getCutpositionarr1() {
		return cutpositionarr1;
	}

	/**
	 * @return the cutpositionarr2
	 */
	public int getCutpositionarr2() {
		return cutpositionarr2;
	}


	/**
	 * @return the cutpositiondep1
	 */
	public int getCutpositiondep1() {
		return cutpositiondep1;
	}

	/**
	 * @return the cutpositiondep2
	 */
	public int getCutpositiondep2() {
		return cutpositiondep2;
	}

}
