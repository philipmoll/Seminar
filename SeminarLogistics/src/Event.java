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
	private int time;
	private int typeevent; //0: arrival, 1: departure
	private int starttime;
	private int endtime;
	private int sidestart; //0: a
	private int sideend; //1: b

	public Event(FinalBlock eventblock, int typeevent, int starttime, int endtime, int sidestart, int sideend) {
		this.eventblock = eventblock;
		this.typeevent = typeevent;
		this.starttime = starttime;
		this.endtime = endtime;
		if (typeevent == 0){
			time = starttime;
		}
		else{
			time = endtime;
		}
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
	 * Returns the time that is linked to the event
	 * 
	 * @return the time
	 */
	public int getTime() {
		return time;
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
