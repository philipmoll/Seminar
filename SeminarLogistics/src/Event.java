import java.util.ArrayList;

/**
 *
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */

public class Event { //TODO: test
	
	private FinalBlock eventblock;
	private double starttime;
	private double endtime;
	private String sidestart;
	private String sideend;

	public Event(FinalBlock eventblock, double starttime, double endtime, String sidestart, String sideend) {
		this.eventblock = eventblock;
		this.starttime = starttime;
		this.endtime = endtime;
		this.sidestart = sidestart;
		this.sideend = sideend;
	}

	/**
	 * Returns the block that is linked to the event
	 * 
	 * @return the eventblock
	 */
	public FinalBlock getEventblock() {
		return eventblock;
	}

	/**
	 * Returns the start time of an event
	 * 
	 * @return the starttime
	 */
	public double getStarttime() {
		return starttime;
	}

	/**
	 * Returns the end time of an event
	 * 
	 * @return the endtime
	 */
	public double getEndtime() {
		return endtime;
	}

	/**
	 * Returns the side where the composition comes from (A/B)
	 * 
	 * @return the sidestart
	 */
	public String getSidestart() {
		return sidestart;
	}

	/**
	 * Returns the side where the composition goes to (A/B)
	 * 
	 * @return the sideend
	 */
	public String getSideend() {
		return sideend;
	}

}
