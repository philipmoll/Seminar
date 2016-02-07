import java.util.ArrayList;



public class Activity {
	private int plannedtime;
	private Composition composition;
	private int activity;
	private Track trackassigned;
	private int duration;
	private int ultimatetime;

	public Activity(int plannedtime, int duration, int ultimatetime, Composition composition, int activity, Track trackassigned){
		this.plannedtime = plannedtime;
		this.composition = composition;
		this.activity = activity;
		this.trackassigned = trackassigned;
		this.duration = duration;
		this.ultimatetime = ultimatetime;
	}
	public Activity(int duration, int ultimatetime, Composition composition, int activity){
		this.duration = duration;
		this.ultimatetime = ultimatetime;
		this.composition = composition;
		this.activity = activity;
		plannedtime = -1;
		trackassigned = null;
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
		//TODO: THROW EXCEPTION IF NOT FEASIBLE!!!!!!!!!!!!!!
		if(plannedtime != -1){
			trackassigned.removeBusyTime(this);
			composition.removeBusyTime(this);
		}
		trackassigned = newtrack;
		plannedtime = newplannedtime;
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
	public double getUltimateTimeInteger(){
		return (int) ultimatetime;
	}
	public double getMargin(){
		return (ultimatetime-plannedtime);
	}
	public int getMarginInteger(){
		return (int) (ultimatetime-plannedtime);
	}
}
