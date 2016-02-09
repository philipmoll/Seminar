import java.io.IOException; 
import java.util.ArrayList;

public class Todo {

	private ArrayList<Activity> activities;

	private ArrayList<Track> platforms = new ArrayList<>();
	private ArrayList<Track> washareas = new ArrayList<>();
	private static Activity[] movelist;

	public Todo(ArrayList<Track> tracks){
		activities = new ArrayList<>();
		movelist = new Activity[60*24];
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
	 * Adds a composotion with all its required activities to the activity list.
	 * @param addedcomp
	 * @throws IOException 
	 */	
	public void addComposition(Composition addedcomp) throws IOException{
		int durationactivity;
		int count;
		int acttime;
		int temp;
		int temptemp;
		int amount = 0;
		boolean firstactivity = true;
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

		//It checks for all possible activities
		for(int j = 0; j<4; j++){
			temp = 12412;
			temptemp = 12312;
			temp1 = null;
			temptemp1 = null;
			durationactivity = 0;

			//Calculating the required time to perform the activity on the whole composition
			if (addedcomp.getSize()==1){
				if (addedcomp.getTrain(0).getActivity(j)){
					durationactivity = addedcomp.getTrain(0).getActivityTimeInteger(j);
				}
			}
			else if (addedcomp.getSize()>1){
				count = 0;
				acttime = 0;
				for (int i=0; i<addedcomp.getSize(); i++){
					if (addedcomp.getTrain(i).getActivity(j)){
						count++;
						if(acttime < addedcomp.getTrain(i).getActivityTimeInteger(j)){
							acttime = addedcomp.getTrain(i).getActivityTimeInteger(j);
						}
					}
				}
				if (count==1){
					durationactivity = acttime;
				}
				//Here we decouple the composition, so that the trains can be handled simultaneously and add 5 minutes for coupling/decoupling
				else if (count>1){
					durationactivity = acttime + 5;
				}
			}

			//Only if the activity must really be done
			if(durationactivity>0){

				//We always start with j == 0, because this activity must always be performed first.
				if(j == 0 || j == 1 || j == 2){

					//Add an activity
					activities.add(new Activity(durationactivity, (int) addedcomp.getDepartureTimeInteger()-durationactivity, addedcomp, j));

					//We find the soonest possible time to start the activity looking at each possible track the activity can be done.
					if (firstactivity==false){
						for(int k = 0; k<platforms.size(); k++){			
							if (platforms.get(k) == activities.get(activities.size()-2).getTrackAssigned()){
								for(int l = mintemp; l<activities.get(activities.size()-1).getUltimateTimeInteger(); l++){

									if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1), l)){
											temptemp = l;

											temptemp1 = platforms.get(k);
											break;
										}
									}
								}

								//Update if we find better solution than the previous ones at a different track.
								if(temptemp < temp){
									temp = temptemp;
									temp1 = temptemp1;
								}
							}

							else{ //in case you have to go to other track, take movingtime of 2 minutes into account
								for(int l = mintemp+2; l<activities.get(activities.size()-1).getUltimateTimeInteger(); l++){

									if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1), l)){
											temptemp = l;

											temptemp1 = platforms.get(k);
											break;
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp < temp){
									temp = temptemp;
									temp1 = temptemp1;
								}
							}
						}
					}

					else //when it is first activity, you take the moving time of going to the platform as preceding activity as the actual first activity
					{
						for(int k = 0; k<platforms.size(); k++){
							for(int l = mintemp+2; l<activities.get(activities.size()-1).getUltimateTimeInteger(); l++){

								if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1), l)){
										temptemp = l;

										temptemp1 = platforms.get(k);
										break;
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
					}
					//when it is the very first activity, you cannot compare it with a previous activity
					if (activities.size()<2){
						activities.add(activities.size()-2, new Activity(temp-2, 2, addedcomp.getDepartureTimeInteger()-durationactivity, addedcomp, 4, temp1));
						activities.get(activities.size()-1).setUpdate(temp, temp1);
						addedcomp.setBusyTime(activities.get(activities.size()-2));
						Todo.setBusyMoveTime(activities.get(activities.size()-2));
						addedcomp.setBusyTime(activities.get(activities.size()-1));
					}
					else if (activities.size()>=2){
						//if it is first activity of composition, moving always precedes this activity
						if (firstactivity){
							activities.add(activities.size()-2, new Activity(temp-2, 2, addedcomp.getDepartureTimeInteger()-durationactivity, addedcomp, 4, temp1));
							activities.get(activities.size()-1).setUpdate(temp, temp1);
							addedcomp.setBusyTime(activities.get(activities.size()-2));
							Todo.setBusyMoveTime(activities.get(activities.size()-2));
							addedcomp.setBusyTime(activities.get(activities.size()-1));
						}
						else if (activities.get(activities.size()-1).getComposition()==activities.get(activities.size()-2).getComposition() && temp1 != activities.get(activities.size()-2).getTrackAssigned()){
							activities.add(activities.size()-2, new Activity(temp-2, 2, addedcomp.getDepartureTimeInteger()-durationactivity, addedcomp, 4, temp1, activities.get(activities.size()-2).getTrackAssigned()));
							amount += 1; //the moving activities after the first one counts for the amount of activities which needs to be reconsidered
							activities.get(activities.size()-1).setUpdate(temp, temp1);
							addedcomp.setBusyTime(activities.get(activities.size()-2));
							Todo.setBusyMoveTime(activities.get(activities.size()-2));
							addedcomp.setBusyTime(activities.get(activities.size()-1));			
						}

						else {
							activities.get(activities.size()-1).setUpdate(temp, temp1);
							addedcomp.setBusyTime(activities.get(activities.size()-1));
						}
					}


					temp1.setBusyTime(activities.get(activities.size()-1)); 
					//Minimum time to loop from must be update since inspection cannot be moved later
					if(j == 0){	
						mintemp += durationactivity;
						firstactivity = false;
					}

					//Storing the first solution and keeping track of how many activities are done on the composition, except for inspection though
					else if(j == 1){
						time11 = activities.get(activities.size()-1).getPlannedTimeInteger();
						track11 = activities.get(activities.size()-1).getTrackAssigned();
						if(activities.get(activities.size()-1).getMargin()<margin1){
							margin1 = activities.get(activities.size()-1).getMarginInteger();
						}
						amount += 1;
						firstactivity = false;
					}

					//Storing the first solution ............. see above.
					else if(j == 2){
						time12 = activities.get(activities.size()-1).getPlannedTimeInteger();
						track12 = activities.get(activities.size()-1).getTrackAssigned();
						if(activities.get(activities.size()-1).getMargin()<margin1){
							margin1 = activities.get(activities.size()-1).getMarginInteger();
						}
						amount += 1;
						firstactivity = false;
					}
				}

				//If activity is washing, we have to look at other tracks, i.e. wash areas.
				else if(j == 3){
					activities.add(new Activity(durationactivity, addedcomp.getDepartureTimeInteger()-durationactivity, addedcomp, j));

					//Keeping track of amount of activities done except for inspection, the moving activity counts as well
					amount += 1;

					//We find the soonest possible time to start the activity looking at each possible track the activity can be done.
					for(int k = 0; k<washareas.size(); k++){
						for(int l = mintemp+2; l<activities.get(activities.size()-1).getUltimateTime(); l++){
							if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1), l)){
									temptemp = l;
									temptemp1 = washareas.get(k);
									break;
								}
							}
						}

						//Update if we find better solution than the previous ones at a different track.
						if(temptemp < temp){
							temp = temptemp;
							temp1 = temptemp1;
						}
					}
					activities.add(activities.size()-2, new Activity(temp-2, 2, addedcomp.getDepartureTimeInteger()-durationactivity, addedcomp, 4, temp1)); 
					activities.get(activities.size()-1).setUpdate(temp, temp1);

					//Storing first solution
					Todo.setBusyMoveTime(activities.get(activities.size()-2));
					addedcomp.setBusyTime(activities.get(activities.size()-2));
					addedcomp.setBusyTime(activities.get(activities.size()-1));
					temp1.setBusyTime(activities.get(activities.size()-1));
					time13 = activities.get(activities.size()-1).getPlannedTimeInteger();
					track13 = activities.get(activities.size()-1).getTrackAssigned();

					if(activities.get(activities.size()-1).getMargin()<margin1){
						margin1 = activities.get(activities.size()-1).getMarginInteger();
					}
					if (firstactivity==false){
						amount += 1;
					}
					firstactivity = false;

				}
			}
		}

		//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
		for(int i = 0; i<amount; i++){
			activities.get(activities.size()-1-i).removeTimes();
		}

		//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
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

		//If the first solution is better, we use the first solution. We choose the first solution because it is more likely that it will not need to be moved to another track (saving time)
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

	public static void setBusyMoveTime(Activity activity){
		for(int i = activity.getPlannedTimeInteger(); i<activity.getPlannedTimeInteger()+activity.getDurationInteger(); i++){
			movelist[i] = activity;
		}
	}
	
	public static void removeBusyMoveTime(Activity activity){
		for(int i = 0; i<movelist.length; i++){
			if(movelist[i] != null && movelist[i].equals(activity)){
				movelist[i] = null; 
			}
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

	/*
	 * Arrival of train with the current activity must be before the swapped activity time
	 * Inspection must remain the first activity to be done
	 * If current activity has a longer duration than the swapped activity, than we must shift all activities later of the swapped activity to a later time. Then check if all activities later remain feasible and check their margins.
	 * If current activity has a shorter duration than the swapped activity, than we must shift all activities later earlier. We must check if this is possible due to arrival times.
	 */
	/*
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
	 */

}