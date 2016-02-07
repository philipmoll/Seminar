import java.io.IOException;
import java.util.ArrayList;

public class Todo {

	private ArrayList<Activity> activities;

	ArrayList<Track> platforms = new ArrayList<>();
	ArrayList<Track> washareas = new ArrayList<>();

	public Todo(ArrayList<Track> tracks){
		activities = new ArrayList<>();

		for(int i=0;i<tracks.size();i++){
			if (tracks.get(i).getInspectionposition() ==1){
				platforms.add(tracks.get(i));
			}
			if(tracks.get(i).getWashingposition()== 1){
				washareas.add(tracks.get(i));
			}
		}
	}
	/**
	 * 
	 * @param addedcomp
	 * @throws IOException 
	 */	
	public void addComposition(Composition addedcomp) throws IOException{
		int durationactivity;
		int temp;
		int temptemp;
		int amount = 0;
		int mintemp = (int) addedcomp.getArrivaltime()*24*60; //TODO: AFRONDEN GOED?
		Track temp1;
		Track temptemp1;
		int margin1 = 2432;
		int time11 = -1;
		int time12 = -1;
		int time13 = -1;
		Track track11 = null;
		Track track12 = null;
		Track track13 = null;
		int margin2 = 2432;

		for(int j = 0; j<4; j++){
			temp = 12412;
			temptemp = 12312;
			temp1 = null;
			temptemp1 = null;
			durationactivity = 0;
			for(int i = 0; i<addedcomp.getSize(); i++){
				if(addedcomp.getTrain(i).getActivity(j)){
					durationactivity += addedcomp.getTrain(i).getActivityTimeInteger(j);
				}
			}
			if(durationactivity>0){
				if(j == 0 || j == 1 || j == 2){
					activities.add(new Activity(durationactivity, (int) addedcomp.getDepartureTimeInteger()-durationactivity, addedcomp, j));

					for(int k = 0; k<platforms.size(); k++){
						for(int l = mintemp; l<activities.get(activities.size()-1).getUltimateTimeInteger(); l++){

							if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1), l)){
									temptemp = l;

									temptemp1 = platforms.get(k);
									break;
								}
							}
						}

						if(temptemp < temp){
							temp = temptemp;
							temp1 = temptemp1;
						}

						/*if(platforms.get(k).getFreeTime()<temp){
							temp = platforms.get(k).getFreeTime();
							temp1 = platforms.get(k);
						}*/
					}

					activities.get(activities.size()-1).setUpdate(temp, temp1);
					//TODO: DECOUPLE AND COUPLE TO REDUCE TIME WITHIN AN ACTIVITY
					addedcomp.setBusyTime(activities.get(activities.size()-1));

					temp1.setBusyTime(activities.get(activities.size()-1)); //TODO: MOVING TIME MUST BE INCLUDED
					if(j == 0){	
						mintemp += durationactivity;
					}
					else if(j == 1){
						time11 = activities.get(activities.size()-1).getPlannedTimeInteger();
						track11 = activities.get(activities.size()-1).getTrackAssigned();
						if(activities.get(activities.size()-1).getMargin()<margin1){
							margin1 = activities.get(activities.size()-1).getMarginInteger();
						}
						amount += 1;
					}
					else if(j == 2){
						time12 = activities.get(activities.size()-1).getPlannedTimeInteger();
						track12 = activities.get(activities.size()-1).getTrackAssigned();
						if(activities.get(activities.size()-1).getMargin()<margin1){
							margin1 = activities.get(activities.size()-1).getMarginInteger();
						}
						amount += 1;
					}
				}

				else if(j == 3){
					activities.add(new Activity(durationactivity, addedcomp.getDepartureTimeInteger()-durationactivity, addedcomp, j));
					amount += 1;

					for(int k = 0; k<washareas.size(); k++){

						for(int l = mintemp; l<activities.get(activities.size()-1).getUltimateTime(); l++){
							if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1), l)){
									temptemp = l;
									temptemp1 = washareas.get(k);
									break;
								}
							}
						}
						if(temptemp < temp){
							temp = temptemp;
							temp1 = temptemp1;
						}
					}

					activities.get(activities.size()-1).setUpdate(temp, temp1);

					addedcomp.setBusyTime(activities.get(activities.size()-1));
					temp1.setBusyTime(activities.get(activities.size()-1)); //TODO: MOVING TIME MUST BE INCLUDED
					time13 = activities.get(activities.size()-1).getPlannedTimeInteger();
					track13 = activities.get(activities.size()-1).getTrackAssigned();

					if(activities.get(activities.size()-1).getMargin()<margin1){
						margin1 = activities.get(activities.size()-1).getMarginInteger();
					}

				}
			}
		}
		
		for(int i = 0; i<amount; i++){
			activities.get(activities.size()-1-i).removeTimes();
		}
		
		for(int j = 0; j<amount; j++){

			temp = 124123123;
			temptemp = 123124;
			temp1 = null;
			temptemp1 = null;
			if(activities.get(activities.size()-1-j).getActivity() == 3){

				for(int k = 0; k<washareas.size(); k++){
					for(int l = mintemp; l<activities.get(activities.size()-1-j).getUltimateTimeInteger(); l++){
						if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-j), l)){
							if(addedcomp.checkFeasibility(activities.get(activities.size()-1-j), l)){
								temptemp = l;
								temptemp1 = washareas.get(k);
								break;
							}
						}
					}
					if(temptemp < temp){
						temp = temptemp;
						temp1 = temptemp1;
					}
				}

				activities.get(activities.size()-1-j).setUpdate(temp, temp1);

				//addedcomp.setBusyTime(activities.get(activities.size()-1-j));
				//temp1.setBusyTime(activities.get(activities.size()-1-j)); //TODO: MOVING TIME MUST BE INCLUDED

				if(activities.get(activities.size()-1-j).getMargin()<margin2){
					margin2 = activities.get(activities.size()-1-j).getMarginInteger();
				}


			}
			else if(activities.get(activities.size()-1-j).getActivity() == 1 || activities.get(activities.size()-1-j).getActivity() == 2){

				for(int k = 0; k<platforms.size(); k++){

					for(int l = mintemp; l<activities.get(activities.size()-1-j).getUltimateTimeInteger(); l++){
						if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-j), l)){
							if(addedcomp.checkFeasibility(activities.get(activities.size()-1-j), l)){
								temptemp = l;
								temptemp1 = platforms.get(k);
								break;
							}
						}
					}
					if(temptemp < temp){
						temp = temptemp;
						temp1 = temptemp1;
					}

				}
				activities.get(activities.size()-1-j).setUpdate(temp, temp1);
				//TODO: DECOUPLE AND COUPLE TO REDUCE TIME WITHIN AN ACTIVITY
				addedcomp.setBusyTime(activities.get(activities.size()-1-j));
				temp1.setBusyTime(activities.get(activities.size()-1-j)); //TODO: MOVING TIME MUST BE INCLUDED
				if(j == 1){
					if(activities.get(activities.size()-1-j).getMargin()<margin2){
						margin2 = activities.get(activities.size()-1-j).getMarginInteger();
					}
				}
				else if(j == 2){
					if(activities.get(activities.size()-1-j).getMargin()<margin2){
						margin2 = activities.get(activities.size()-1-j).getMarginInteger();
					}
				}
			}
		}

		if(margin1 >= margin2){
			for(int i = 0; i<amount; i++){
				if(activities.get(activities.size()-1-i).getActivity()==1){
					activities.get(activities.size()-1-i).setUpdate(time11, track11);
				}
				if(activities.get(activities.size()-1-i).getActivity()==2){
					activities.get(activities.size()-1-i).setUpdate(time12, track12);
				}
				if(activities.get(activities.size()-1-i).getActivity()==3){
					activities.get(activities.size()-1-i).setUpdate(time13, track13);
				}
			}

		}
		
		// Hier */ zetten!

	}

	public static ArrayList<int[]> getTODO(Train[] trainlist){
		ArrayList<int[]> temp = new ArrayList<>();
		for(int i = 0; i<trainlist.length;i++){

			if(trainlist[i].getInspecting()){
				temp.add(new int[] {trainlist[i].getID(), 0});
			}
			if(trainlist[i].getCleaning()){
				temp.add(new int[] {trainlist[i].getID(), 1});
			}
			if(trainlist[i].getRepairing()){
				temp.add(new int[] {trainlist[i].getID(), 2});
			}
			if(trainlist[i].getWashing()){
				temp.add(new int[] {trainlist[i].getID(), 3});
			}
		}
		return temp;

	}
	private int getMin(){
		double temp = 33299004;
		int temptemp = -1;
		for(int i = 0; i<activities.size(); i++){
			if(activities.get(i).getPlannedTime()<temp){
				temp = activities.get(i).getPlannedTime();
				temptemp = i;
			}
		}
		return temptemp;
	}
	public double getMinTime(){
		return activities.get(this.getMin()).getPlannedTime();
	}
	public Composition getMinTrain(){
		return activities.get(this.getMin()).getComposition();
	}
	public Activity getMinActivity(){
		return activities.get(this.getMin());
	}
	public Track getMinTrack(){
		return activities.get(this.getMin()).getTrackAssigned();
	}
	public boolean getEmpty(){
		if(activities.size() == 0){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean checkFeasibility(){
		boolean abcd = true;
		for(int i = 0; i<activities.size(); i++){
			if(activities.get(i).getPlannedTime()>activities.get(i).getUltimateTime()){
				abcd = false;
			}	
		}
		return abcd;
	}
	public int getLeastMargin(){
		double temp;
		int temp1 = -1;

		for(int i = 0; i< 20; i++){
			temp = 1123;
			temp1 = -1;
			for(int j = 0; j< activities.size(); j++){

				if(activities.get(i).getMargin() < temp){
					temp = activities.get(i).getMargin();
					temp1 = j;
				}

			}
		}
		return temp1;
	}
	/*public int[] getMaxMargin(double timespan, int amount, int currentactivity){
		double[] temptimes = new double[plannedtimes.size()];
		for(int i = 0; i<temptimes.length; i++){
			temptimes[i] = plannedtimes.get(i);
		}
		int[] indices = new int[amount];
		int index = -1;
		double temp = 0;
		for(int j = 0; j<amount; j++){
			index = -1;
			temp = 0;
			for(int i = 0; i<temptimes.length; i++){
				if(plannedtimes.get(currentactivity)-plannedtimes.get(i)<=timespan && plannedtimes.get(currentactivity)-plannedtimes.get(i)>0){
					if(temptimes[i]>temp){
						temp = temptimes[i];
						index = i;
					}
				}
			}
			temptimes[index] = 0;
			indices[j] = index;
		}
		return indices;
	}*/
	public int getSwap(int currentactivity){



		return 5;
	}
	/*
	 * Arrival of train with the current activity must be before the swapped activity time
	 * Inspection must remain the first activity to be done
	 * If current activity has a longer duration than the swapped activity, than we must shift all activities later of the swapped activity to a later time. Then check if all activities later remain feasible and check their margins.
	 * If current activity has a shorter duration than the swapped activity, than we must shift all activities later earlier. We must check if this is possible due to arrival times.
	 */
	public boolean getFeasibilitySwap(int activity1, int activity2){
		boolean feasible = true;
		//INCLUDE OVERLAP WITH DIFFERENT ACTIVITY AND INSPECTION AVAILABILITY (TRIANGLE APPROACH???)
		if(activities.get(activity1).getComposition().getArrivaltime() > activities.get(activity2).getPlannedTime()){
			feasible = false;
		}
		else if(activities.get(activity1).getPlannedTime()+activities.get(activity2).getDuration() > activities.get(activity2).getComposition().getDeparturetime()){
			feasible = false;
		}
		else if(activities.get(activity1).getDuration() == activities.get(activity2).getDuration()){

		}
		else if(activities.get(activity1).getDuration() > activities.get(activity2).getDuration()){
			for(int i = 0; i<activities.size(); i++){
				if(activities.get(i).getTrackAssigned() == activities.get(activity2).getTrackAssigned()){
					if(activities.get(i).getPlannedTime() > activities.get(activity2).getPlannedTime()){
						if(activities.get(i).getPlannedTime()+(activities.get(activity1).getDuration()-activities.get(activity2).getDuration())>activities.get(i).getUltimateTime()){
							feasible = false;
							break;
						}
					}
				}
			}
		}
		else if(activities.get(activity1).getDuration() < activities.get(activity2).getDuration()){
			for(int i = 0; i<activities.size(); i++){
				if(activities.get(i).getTrackAssigned() == activities.get(activity1).getTrackAssigned()){
					if(activities.get(i).getPlannedTime() > activities.get(activity1).getPlannedTime()){
						if(activities.get(i).getPlannedTime()+(activities.get(activity2).getDuration()-activities.get(activity1).getDuration())>activities.get(i).getUltimateTime()){
							feasible = false;
							break;
						}
					}
				}
			}
		}

		return feasible;
	}

	public void swapActivities(int activity1, int activity2){

	}
	public void improveTODO(){
		int temp1;

		for(int i = 0; i< 20; i++){

			temp1 = this.getLeastMargin();



		}
	}
}