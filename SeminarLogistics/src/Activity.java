import java.io.Serializable;
import java.util.ArrayList;



public class Activity implements Serializable{
	private int plannedtime;
	private Composition composition;
	private int activity;
	private Track trackassigned;
	private int duration;
	private int totalduration;
	private int ultimatetime;
	private Track previoustrack;
	private int movetime;
	private int movetime2;

	public Activity(int plannedtime, int duration, int ultimatetime, Composition composition, int activity, Track trackassigned){
		this.plannedtime = plannedtime;
		this.composition = composition;
		this.activity = activity;
		this.trackassigned = trackassigned;
		this.duration = duration;
		this.ultimatetime = ultimatetime;
		previoustrack = null;
		movetime = Main.moveduration;
		movetime2 = Main.moveduration;
		totalduration = duration + movetime + movetime2;
	}
	public Activity(int duration, int ultimatetime, Composition composition, int activity, boolean move){
		this.duration = duration;
		this.ultimatetime = ultimatetime;
		this.composition = composition;
		this.activity = activity;
		plannedtime = -1;
		trackassigned = null;
		previoustrack = null;
		movetime = Main.moveduration;
		movetime2 = Main.moveduration;
		totalduration = duration + movetime + movetime2;
	}
	public Activity(int duration, int ultimatetime, Composition composition, int activity){
		this.duration = duration;
		this.ultimatetime = ultimatetime;
		this.composition = composition;
		this.activity = activity;
		plannedtime = -1;
		trackassigned = null;
		previoustrack = null;
		movetime = Main.moveduration;
		movetime2 = Main.moveduration;
		totalduration = duration + movetime + movetime2;
	}
	public Activity(int plannedtime, int duration, int ultimatetime, Composition composition, int activity, Track trackassigned, boolean move){
		this.plannedtime = plannedtime;
		this.composition = composition;
		this.activity = activity;
		this.trackassigned = trackassigned;
		this.duration = duration;
		this.ultimatetime = ultimatetime;

		//Only relevant for the moving activity;
		movetime = Main.moveduration;
		movetime2 = Main.moveduration;
		totalduration = duration + movetime + movetime2;
	}

	public boolean differenttrack(){
		if(previoustrack.equals(trackassigned)){
			return false;
		}
		else{
			return true;
		}
	}
	public double getPlannedTime(){
		return plannedtime;
	}
	public Composition getComposition(){
		return composition;
	}
	public int getActivity(){
		return activity;
	}
	public Track getTrackAssigned(){
		return trackassigned;
	}

	public Track getPreviousTrack(){
		return previoustrack;
	}

	public double getDuration(){
		return duration;
	}
	public double getUltimateTime(){
		return ultimatetime;
	}
	public void setPlannedTime(int abcd){
		plannedtime = abcd;
	}
	public void setComposition(Composition abcd){
		composition = abcd;
	}
	public void setTrackAssigned(Track abcd){
		//trackassigned.removeBusyTime(this);
		trackassigned = abcd;
		//abcd.setBusyTime(this); //TODO: ONLY IF FEASIBLE, SO TIME MUST BE ADJUSTED AD FIRST???
	}
	public void setDuration(int abcd){
		duration = abcd;
	}
	public void setUltimateTime(int abcd){
		ultimatetime = abcd;
	}
	public void removeTimes(){
		trackassigned.removeBusyTime(this);
		composition.removeBusyTime(this);
	}
	public void setUpdate(int newplannedtime, Track newtrack){
		//TODO: THROW EXCEPTION IF NOT FEASIBLE!!!!!!!!!!!!!!!!
		if(plannedtime != -1){
			trackassigned.removeBusyTime(this);
			composition.removeBusyTime(this);
		}
		trackassigned = newtrack;
		plannedtime = newplannedtime;
		totalduration = duration + movetime + movetime2;
		trackassigned.setBusyTime(this);
		composition.setBusyTime(this);
	}
	public void setUpdate(int newplannedtime, Track newtrack, Track prevtrack){
		//TODO: THROW EXCEPTION IF NOT FEASIBLE!!!!!!!!!!!!!!!!
		if(plannedtime != -1){
			trackassigned.removeBusyTime(this);
			composition.removeBusyTime(this);
		}
		trackassigned = newtrack;
		plannedtime = newplannedtime;
		previoustrack = prevtrack;
		totalduration = duration + movetime;
		trackassigned.setBusyTime(this);
		composition.setBusyTime(this);
	}
	//TODO: ROND DIE DIT GOED AF???
	public int getPlannedTimeInteger(){
		return (int) plannedtime;
	}
	public int getDurationInteger(){
		return (int) duration;
	}
	public int getTotalDurationInteger(){
		return (int) totalduration;
	}
	public double getUltimateTimeInteger(){
		return (int) ultimatetime;
	}
	public int getMarginInteger(){
		return (int) (ultimatetime - plannedtime - movetime-movetime2);
	}
	public int getMoveTime(){
		return (int) movetime;
	}
	public int getMoveTime2(){
		return (int) movetime2;
	}
	public void setCurrentTrack(Track abc){
		previoustrack = abc;
	}
}