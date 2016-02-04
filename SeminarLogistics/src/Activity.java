import java.util.ArrayList;


public class Activity {
	private double plannedtime;
	private Composition composition;
	private int activity;
	private Track trackassigned;
	private double duration;
	private double ultimatetime;
	
	public Activity(double plannedtime, double duration, double ultimatetime, Composition composition, int activity, Track trackassigned){
		this.plannedtime = plannedtime;
		this.composition = composition;
		this.activity = activity;
		this.trackassigned = trackassigned;
		this.duration = duration;
		this.ultimatetime = ultimatetime;
	}
	public Activity(double duration, double ultimatetime, Composition composition, int activity){
		this.duration = duration;
		this.ultimatetime = ultimatetime;
		this.composition = composition;
		this.activity = activity;
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
	public void setPlannedTime(double abcd){
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
	public void setDuration(double abcd){
		duration = abcd;
	}
	public void setUltimateTime(double abcd){
		ultimatetime = abcd;
	}
	public void setUpdate(double newplannedtime, Track newtrack){
		//TODO: THROW EXCEPTION IF NOT FEASIBLE!!!!!!!!!!!!!!
		trackassigned.removeBusyTime(this);
		trackassigned = newtrack;
		plannedtime = newplannedtime;
		trackassigned.setBusyTime(this);
	}
	//TODO: ROND DIE DIT GOED AF???
	public int getPlannedTimeInteger(){
		return (int) plannedtime*24*60;
	}
	public int getDurationInteger(){
		return (int) duration*24*60;
	}
	public double getUltimateTimeInteger(){
		return (int) ultimatetime*24*60;
	}
}
