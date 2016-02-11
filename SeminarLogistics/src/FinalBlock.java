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
public class FinalBlock extends Block { //TODO: test

	private Composition destinationcomposition;
	
	/**
	 * @param compositiontrains
	 * @param arrivaltime
	 * @param departuretime
	 * @param origincomposition
	 * @param cutposition1
	 * @param cutposition2
	 * @throws IOException
	 */
	public FinalBlock(ArrayList<Train> compositiontrains, double arrivaltime, double departuretime,
			Composition origincomposition, Composition destinationcomposition, int cutposition1, int cutposition2) throws IOException {
		super(compositiontrains, arrivaltime, departuretime, origincomposition, cutposition1, cutposition2);
	
		this.destinationcomposition = destinationcomposition;
	}

	/**
	 * Returns destinationcomposition
	 * @return the destinationcomposition
	 */
	public Composition getDestinationcomposition() {
		return destinationcomposition;
	}

}
