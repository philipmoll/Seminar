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
		double durationactivity;
		double temp;
		double temptemp;
		int mintemp = (int) addedcomp.getArrivaltime()*24*60; //TODO: AFRONDEN GOED?
		Track temp1;
		Track temptemp1;
		for(int j = 0; j<4; j++){
			temp = 124123123;
			temptemp = 123124;
			temp1 = null;
			temptemp1 = null;
			durationactivity = 0;
			for(int i = 0; i<addedcomp.getSize(); i++){
				if(addedcomp.getTrain(i).getActivity(j)){
					durationactivity += addedcomp.getTrain(i).getActivityTime(j);
				}
			}
			if(durationactivity>0){
				if(j == 0 || j == 1 || j == 2){
					activities.add(new Activity(durationactivity, addedcomp.getDeparturetime()-durationactivity, addedcomp, j));
					for(int k = 0; k<platforms.size(); k++){

						for(int l = mintemp; l<activities.get(activities.size()-1).getUltimateTime(); l++){
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
					if(j==0){
						mintemp += durationactivity;
					}
				}
			}
			else if(j == 3){
				activities.add(new Activity(durationactivity, addedcomp.getDeparturetime()-durationactivity, addedcomp, j));
				boolean needinspection = false;
				for (int m = 0; m<addedcomp.getSize();m++){
					if (addedcomp.getTrain(m).getActivity(0));
					needinspection = true;
					break;
				}
				if (needinspection){
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
					mintemp = (int) (temp + durationactivity);

					addedcomp.setBusyTime(activities.get(activities.size()-1));
					temp1.setBusyTime(activities.get(activities.size()-1)); //TODO: MOVING TIME MUST BE INCLUDED
				}
				else {
					mintemp = (int) addedcomp.getArrivaltime()*24*60;
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
					mintemp = (int) (temp + durationactivity);

					addedcomp.setBusyTime(activities.get(activities.size()-1));
					temp1.setBusyTime(activities.get(activities.size()-1)); //TODO: MOVING TIME MUST BE INCLUDED
				}
			}
		}
	}

	/*public void addComposition(Composition addedcomp){
		boolean locallydecouple;
		boolean[] trainactivity = new boolean[addedcomp.getSize()];
		int[] amountactivities = new int[4];
		int totaltrainactivity = 0;

		for(int i = 0; i<addedcomp.getSize(); i++){
			if(addedcomp.getTrain(i).getInspecting()){
				trainactivity[i] = true;
				amountactivities[0] += 1;
			}
			if(addedcomp.getTrain(i).getCleaning()){
				trainactivity[i] = true;
				amountactivities[1] += 1;
			}
			if(addedcomp.getTrain(i).getRepairing()){
				trainactivity[i] = true;
				amountactivities[2] += 1;
			}
			if(addedcomp.getTrain(i).getWashing()){
				trainactivity[i] = true;
				amountactivities[3] += 1;
			}
		}

		for(int i = 0; i<addedcomp.getSize(); i++){
			if(trainactivity[i]){
				totaltrainactivity += 1;
			}
		}
		if(totaltrainactivity>1){
			locallydecouple = true;
		}
		else{
			for(int i = 0; i<amountactivities.length; i++){
				if(amountactivities[i] > 1){
					locallydecouple = true;
				}
			}
		}
	}
	 */

	/*public void addComposition(Composition addedcomp){
		double temp;
		Track temp1;
		for(int i = 0; i<addedcomp.getSize(); i++){
			temp = 32299004;
			temp1 = null;
			if(addedcomp.getTrain(i).getInspecting()){
				trains.add(addedcomp.getTrain(i));
				activities.add(0);



				for(int j = 0; j<platforms.size(); j++){
					if(platforms.get(j).getFreeTime()<temp){
						temp = platforms.get(j).getFreeTime();
						temp1 = platforms.get(j);
					}
				}

				times.add(temp);
				tracksassigned.add(temp1);
				temp1.setFreeTime(temp1.getFreeTime()+addedcomp.getTrain(i).getInspectionTime());
			}
			else if(addedcomp.getTrain(i).getCleaning()){
				trains.add(addedcomp.getTrain(i));
				activities.add(1);



				for(int j = 0; j<platforms.size(); j++){
					if(platforms.get(j).getFreeTime()<temp){
						temp = platforms.get(j).getFreeTime();
						temp1 = platforms.get(j);
					}
				}

				times.add(temp);
				tracksassigned.add(temp1);
				temp1.setFreeTime(temp1.getFreeTime()+addedcomp.getTrain(i).getCleaningTime());
			}
			else if(addedcomp.getTrain(i).getRepairing()){
				trains.add(addedcomp.getTrain(i));
				activities.add(2);



				for(int j = 0; j<platforms.size(); j++){
					if(platforms.get(j).getFreeTime()<temp){
						temp = platforms.get(j).getFreeTime();
						temp1 = platforms.get(j);
					}
				}

				times.add(temp);
				tracksassigned.add(temp1);
				temp1.setFreeTime(temp1.getFreeTime()+addedcomp.getTrain(i).getRepairingTime());
			}
			else if(addedcomp.getTrain(i).getWashing()){
				trains.add(addedcomp.getTrain(i));
				activities.add(3);

				for(int j = 0; j<washareas.size(); j++){
					if(washareas.get(j).getFreeTime()<temp){
						temp = washareas.get(j).getFreeTime();
						temp1 = washareas.get(j);
					}
				}

				times.add(temp);
				tracksassigned.add(temp1);
				temp1.setFreeTime(temp1.getFreeTime()+addedcomp.getTrain(i).getWashingTime());
			}
		}
	}
	 */

	/*public void assignActivityToTrack(int activity, int whichtrack, int kindoftrack, int starttime){
		//TODO: IF whichplatform IS OUT OF BOUNDS EXCEPTION!
		//TODO: Exception for starttime out of bounds (may be 0 up to 1439)!!!!!!

		if(kindoftrack == 0){// 0 refers to a platform
			for(int i = starttime; i<starttime+activities.get(activity).getDuration(); i++){
				busytimesplatforms.get(whichtrack)[i] = activities.get(activity);
			}
		}
		else if(kindoftrack == 1){// 1 refers to a wash area
			for(int i = starttime; i<starttime+activities.get(activity).getDuration(); i++){
				busytimeswashareas.get(whichtrack)[i] = activities.get(activity);
			}
		}
	}
	public void removeActivityFromTrack(int activity){
		//TODO: Exception out of bounds

	}*/

	public static ArrayList<int[]> getTODO(Train[] trainlist){
		ArrayList<int[]> temp = new ArrayList<>();
		for(int i = 0; i<trainlist.length;i++){

			if(trainlist[i].getInspecting()){
				temp.add(new int[] {trainlist[i].getID(), 0});
			}
			if(trainlist[i].getCleaning()){
				temp.add(new int[] {trainlist[i].getID(), 1}); System.out.print(trainlist[i].getCleaning() + " " + i + "\n");
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
	
	public double getMinimumMarginsAfterSwap(int activity1, int activity2){
		double margin = 223423;
		
		if(activities.get(activity1).getDuration()==activities.get(activity2).getDuration()){
			
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
	
	public Activity getBestSwap(Activity activity){
		double bestmargin = activity.getMargin();
		double currentmargin;
		for(int i = 0; i<activities.size(); i++){
			currentmargin
			if(activities.get(i).getComposition().getDeparturetime()-activity.getPlannedTime()+activities.get(i).getDuration() > ){
				
			}
			
			
			
		}
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