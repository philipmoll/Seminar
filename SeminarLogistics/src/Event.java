import java.io.IOException;
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
	private int finaltypeevent; //0: if it is an overall arrival of a composition, 1: departure
	private int starttime;
	private int endtime;
	private int sidestart; //0: a
	private int sideend; //1: b
	private int reverseleave; //1: it will leave in reverse via a free track, 0 otherwise

	public Event(FinalBlock eventblock, int typeevent, int finaltypeevent, int starttime, int endtime, int sidestart, int sideend) {
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
		setReverseLeave(0);
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
	 * Returns the type that is linked to the event
	 * 
	 * @return the typeevent, 0 if arrival, 1 if departure
	 */
	public int getType() {
		return typeevent;
	}
	
	/**
	 * Returns if an event is an overall arrival of departure in shunting yard
	 * 
	 * @return the finaltypeevent, 0 if false, 1 if true
	 */
	public int getFinalType() {
		return finaltypeevent;
	}

	/**
	 * Returns the start time of an event
	 * 
	 * @return the starttime
	 */
	public int getStarttime() {
		return starttime;
	}

	/**
	 * Returns the end time of an event
	 * 
	 * @return the endtime
	 */
	public int getEndtime() {
		return endtime;
	}

	/**
	 * Returns the side where the composition comes from (A/B)
	 * 
	 * @return the sidestart 0 if A, 1 if B
	 */
	public int getSidestart() {
		return sidestart;
	}

	/**
	 * Returns the side where the composition goes to (A/B)
	 * 
	 * @return the sideend 0 if A, 1 if B
	 */
	public int getSideend() {
		return sideend;
	}

	/**
	 * Returns whether a block leaves in reverse
	 * 
	 * @return the reverseleave
	 */
	public int getReverseLeave() {
		return reverseleave;
	}

	/**
	 * Sets a block to reverseleave
	 * 
	 * @param reverseleave the reverseleave to set
	 * @throws IOException 
	 */
	public void setReverseLeave(int reverseleave) throws IOException {
		if (reverseleave != 0 && reverseleave != 1){
			throw new IOException("Reverseleave should be 0 or 1 in method setReverseLeave() in class Event, but is "+reverseleave);
		}
		this.reverseleave = reverseleave;
	}

}
