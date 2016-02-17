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
	private Track arrivaltrack;
	private Track departuretrack;
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
			Composition origincomposition, Composition destinationcomposition, Track arrivaltrack, Track departuretrack, int cutpositionarr1, int cutpositionarr2, int cutpositiondep1, int cutpositiondep2) throws IOException {
		super(compositiontrains, arrivaltime, departuretime);
		if (origincomposition.getArrivaltime() == -1){
			throw new IOException("Origincomposition should be arrivingcomposition with arrivaltime not equal to -1");
		}
		this.origincomposition = origincomposition;
		if (destinationcomposition.getDeparturetime()==-1){
			throw new IOException("Destinationcomposition should be departingcomposition with departuretime not equal to -1");			
		}
		this.destinationcomposition = destinationcomposition;
		if (cutpositionarr2-cutpositionarr1 != cutpositiondep2-cutpositiondep1){
			throw new IOException("Length of two cuts should be the same");
		}
		this.arrivaltrack = arrivaltrack;
		this.departuretrack = departuretrack;
		if (cutpositionarr1 <-1 || cutpositionarr2 < -1 || cutpositionarr1 > origincomposition.getSize()-1 || cutpositionarr2 > origincomposition.getSize()-1 || cutpositionarr1 >= cutpositionarr2){
			throw new IOException("Cutpositions arrivals are out of bounds or cutpositionarr1>cutpositionarr2");
		}
		this.cutpositionarr1 = cutpositionarr1;
		this.cutpositionarr2 = cutpositionarr2;
		if (cutpositiondep1 <-1 || cutpositiondep2 < -1 || cutpositiondep1 > destinationcomposition.getSize()-1 || cutpositionarr2 > origincomposition.getSize()-1 || cutpositionarr1 >= cutpositionarr2){
			throw new IOException("Cutpositions arrivals are out of bounds or cutpositionarr1>cutpositionarr2");
		}
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
	
	public int getShuntTime() {
		return (int) ((this.getDeparturetime() - this.getArrivaltime())*60*24);
	}

}
