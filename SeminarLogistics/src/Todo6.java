import java.io.IOException; 
import java.util.ArrayList;

public class Todo6 {
//INSPECTING MAG ALLEEN OP PLATFORMS
	private ArrayList<Activity> activities;

	ArrayList<Composition> arrivingcompositions;
	ArrayList<Composition> departurecompostions;
	ArrayList<FinalBlock> finalblockss;
	ArrayList<FinalBlock> finalblockssshallow;
	int[] sequence = new int[3];
	int option;
	int compare = 14123;
	boolean feasible = true;


	//An activity representing any incoming/outgoing composition movement
	Activity arrordepmove = new Activity(-1, -1, null, 4, true);

	ArrayList<Track> platforms = new ArrayList<>();
	ArrayList<Track> platformsreserve = new ArrayList<>();
	ArrayList<Track> washareas = new ArrayList<>();
	Activity[] movelist = new Activity[60*24];

	public Todo6(Track[] tracks, ArrayList<Composition> arrivingcompositions, ArrayList<Composition> departurecompositions, ArrayList<FinalBlock> finalblocks, int option) throws IOException{
		if(option < 1 || option > 12){
			throw new IOException("Not that many options exist");
		}
//		System.out.println("Option: " + option);

		activities = new ArrayList<>();
		finalblockss = new ArrayList<>();
		finalblockssshallow = new ArrayList<>();
		this.option = option;


		for(int i=0;i<tracks.length;i++){
			if (tracks[i].getInspectionposition() ==1){
				platforms.add(tracks[i]);
				platformsreserve.add((Track) DeepCopy.copy(tracks[i]));
			}
			if(tracks[i].getWashingposition()== 1){
				washareas.add(tracks[i]);
			}
		}

		this.arrivingcompositions = arrivingcompositions;
		this.departurecompostions = departurecompositions;

		for(int i = 0; i<arrivingcompositions.size(); i++){
			arrordepmove.setPlannedTime(arrivingcompositions.get(i).getArrivalTimeInteger());
			this.setBusyTimeMove(arrordepmove);
		}
		for(int i = 0; i<departurecompositions.size(); i++){
			arrordepmove.setPlannedTime(departurecompositions.get(i).getDepartureTimeInteger()-Main.moveduration);
			this.setBusyTimeMove(arrordepmove);
		}

		for(int i = 0; i< finalblocks.size(); i++){
			this.finalblockss.add((FinalBlock) DeepCopy.copy(finalblocks.get(i)));

			arrordepmove.setPlannedTime(finalblockss.get(i).getOrigincomposition().getArrivalTimeInteger());
			finalblockss.get(i).setBusyTimeMove(arrordepmove);
			arrordepmove.setPlannedTime(finalblockss.get(i).getDestinationcomposition().getDepartureTimeInteger()-Main.moveduration);
			finalblockss.get(i).setBusyTimeMove(arrordepmove);
		}



		int temp;
		double temp2;
		int k;
		int a = this.finalblockss.size();

		if(option == 1 || option == 2){
			for(int i = 0; i<a; i++){
				temp = 14124;
				temp2 = -1.0;
				k = -1;
				for(int j = 0; j<this.finalblockss.size(); j++){
					if(this.finalblockss.get(j).getShuntTime()-(int)(this.finalblockss.get(j).getTotalServiceTime()*60*24) == temp){
						if(this.finalblockss.get(j).getTotalServiceTime()*60*24/(double) this.finalblockss.get(j).getShuntTime() > temp2){
							temp = this.finalblockss.get(j).getShuntTime()-((int) (this.finalblockss.get(j).getTotalServiceTime()*60*24));
							k = j;
							temp2 = this.finalblockss.get(j).getTotalServiceTime()*60*24/(double) this.finalblockss.get(j).getShuntTime() ;
						}
					}
					else if(this.finalblockss.get(j).getShuntTime()-(int)(this.finalblockss.get(j).getTotalServiceTime()*60*24)<temp){
						temp = this.finalblockss.get(j).getShuntTime()-((int) (this.finalblockss.get(j).getTotalServiceTime()*60*24));
						k = j;
						temp2 = this.finalblockss.get(j).getTotalServiceTime()*60*24/(double) this.finalblockss.get(j).getShuntTime() ;
					}
					
				}
				//System.out.println(this.finalblockss.get(k).getSize() + " " + this.finalblockss.get(k).getLength() + " " + this.finalblockss.get(k).getTrain(0).getType());
				//System.out.println(this.finalblockss.get(k).getTotalServiceTime() + " " + this.finalblockss.get(k).getArrivalTimeInteger() + " " + this.finalblockss.get(k).getDepartureTimeInteger());
				this.addComposition(this.finalblockss.get(k));
				finalblockssshallow.add(this.finalblockss.get(k));
				this.finalblockss.remove(k);
			}
		}
		else if(option == 3 || option == 4){
			for(int i = 0; i<a; i++){
				temp = 14124;
				temp2 = -1.0;
				k = -1;
				for(int j = 0; j<this.finalblockss.size(); j++){
					if(this.finalblockss.get(j).getShuntTime()-(int)(this.finalblockss.get(j).getTotalServiceTime()*60*24) == temp){
						if(this.finalblockss.get(j).getTotalServiceTime()*60*24/(double) this.finalblockss.get(j).getShuntTime() > temp2){
							temp = this.finalblockss.get(j).getShuntTime()-((int) (this.finalblockss.get(j).getTotalServiceTime()*60*24));
							k = j;
							temp2 = this.finalblockss.get(j).getTotalServiceTime()*60*24/(double) this.finalblockss.get(j).getShuntTime() ;
						}
					}
					else if(this.finalblockss.get(j).getShuntTime()-(int)(this.finalblockss.get(j).getTotalServiceTime()*60*24)<temp){
						temp = this.finalblockss.get(j).getShuntTime()-((int) (this.finalblockss.get(j).getTotalServiceTime()*60*24));
						k = j;
						temp2 = this.finalblockss.get(j).getTotalServiceTime()*60*24/(double) this.finalblockss.get(j).getShuntTime() ;
					}
					
				}
				//System.out.println(this.finalblockss.get(k).getSize() + " " + this.finalblockss.get(k).getLength() + " " + this.finalblockss.get(k).getTrain(0).getType());
				//System.out.println(this.finalblockss.get(k).getTotalServiceTime() + " " + this.finalblockss.get(k).getArrivalTimeInteger() + " " + this.finalblockss.get(k).getDepartureTimeInteger());
				this.addComposition(this.finalblockss.get(k));
				finalblockssshallow.add(this.finalblockss.get(k));
				this.finalblockss.remove(k);
			}
		}
		else if(option == 9 || option == 10){

			for(int i = 0; i<a; i++){
				temp = 14124;
				k = -1;
				for(int j = 0; j<this.finalblockss.size(); j++){
					if(this.finalblockss.get(j).getShuntTime()<temp){
						temp = this.finalblockss.get(j).getShuntTime();
						k = j;
					}
				}
				//System.out.println(this.finalblockss.get(k).getSize() + " " + this.finalblockss.get(k).getLength() + " " + this.finalblockss.get(k).getTrain(0).getType());
				//System.out.println(this.finalblockss.get(k).getTotalServiceTime() + " " + this.finalblockss.get(k).getArrivalTimeInteger() + " " + this.finalblockss.get(k).getDepartureTimeInteger());
				this.addComposition(this.finalblockss.get(k));
				finalblockssshallow.add(this.finalblockss.get(k));
				this.finalblockss.remove(k);
			}

		}
		else if(option == 11 || option == 12){
			for(int i = 0; i<a; i++){
				k = -1;
				temp = -1;
				for(int j = 0; j<this.finalblockss.size(); j++){
					if((int) this.finalblockss.get(j).getTotalServiceTime()>temp){
						temp = (int) this.finalblockss.get(j).getTotalServiceTime();		
						k = j;
					}
				}
				this.addComposition(this.finalblockss.get(k));
				finalblockssshallow.add(this.finalblockss.get(k));
				this.finalblockss.remove(k);
			}
		}
		else if(option == 5 || option == 6){
			for(int i = 0; i<a; i++){
				k = -1;
				temp = 14124;
				for(int j = 0; j<this.finalblockss.size(); j++){
					if((int) this.finalblockss.get(j).getDepartureTimeInteger()<temp){
						temp = (int) this.finalblockss.get(j).getDepartureTimeInteger();	
						k = j;
					}
				}
				this.addComposition(this.finalblockss.get(k));
				finalblockssshallow.add(this.finalblockss.get(k));
				this.finalblockss.remove(k);
			}
		}
		else if(option == 7 || option == 8){
			for(int i = 0; i<a; i++){
				k = -1;
				temp = 14124;
				for(int j = 0; j<this.finalblockss.size(); j++){
					if((int) this.finalblockss.get(j).getArrivalTimeInteger()<temp){
						temp = (int) this.finalblockss.get(j).getArrivalTimeInteger();		
						k = j;
					}
				}
				this.addComposition(this.finalblockss.get(k));
				finalblockssshallow.add(this.finalblockss.get(k));
				this.finalblockss.remove(k);
			}
		}
		
		

//		for(int i = 0; i<platforms.size(); i++){
//			System.out.print("Platform " + i + "  ");
//			platforms.get(i).printTimeLine();
//			System.out.print("\n");
//			System.out.print("Reserve " + i + "   ");
//			platformsreserve.get(i).printTimeLine();
//			System.out.print("\n");
//		}
		/*for(int i = 0; i<platformsreserve.size(); i++){
			System.out.print("Reserve " + i + "   ");
			platformsreserve.get(i).printTimeLine();
			System.out.print("\n");
		}*/
//		for(int i = 0; i<washareas.size(); i++){
//			System.out.print("Washarea " + i + "  ");
//			washareas.get(i).printTimeLine();
//			System.out.print("\n");
//		}
//		System.out.print("Movelist    " );
//		this.printTimeLine();
//		System.out.print("\n");
	}
	/**
	 * Adds a composotion with all its required activities to the activity list.
	 * @param addedcomp
	 * @throws IOException 
	 */	
	public void addComposition(FinalBlock addedcomp) throws IOException{
		int durationactivity;
		int temp;
		int temptemp;
		int amount = 0;
		int mintemp = addedcomp.getArrivalTimeInteger(); 
		Track temp1;
		Track temptemp1;
		int margin1 = 2432;
		int margin2 = 2432;
		int margin3 = 2432;
		int margin4 = 2432;
		int margin5 = 2432;
		int margin6 = 2432;
		int margin7 = 2432;
		int margin8 = 2432;
		int margin9 = 2432;
		int margin10 = 2432;
		int margin11 = 2432;
		int margin12 = 2432;
		int margin13 = 2432;
		int bestoption = -1;
		int bestmargin = -1;
		int time10 = -1;
		int time11 = -1;
		int time12 = -1;
		int time13 = -1;
		int time15 = -1;
		int time16 = -1;
		int time17 = -1;
		int time18 = -1;
		int time20 = -1;
		int time21 = -1;
		int time22 = -1;
		int time23 = -1;
		int time25 = -1;
		int time26 = -1;
		int time27 = -1;
		int time28 = -1;
		int index = 0;
		Track track10 = null;
		Track track11 = null;
		Track track12 = null;
		Track track13 = null;
		Track track15 = null;
		Track track16 = null;
		Track track17 = null;
		Track track18 = null;
		Track track20 = null;
		Track track21 = null;
		Track track22 = null;
		Track track23 = null;
		Track track25 = null;
		Track track26 = null;
		Track track27 = null;
		Track track28 = null;
		int count;
		int acttime;
		Track currenttrack = null;
		boolean feasible1 = true;
		boolean feasible2 = true;
		boolean feasible3 = true;
		boolean feasible4 = true;
		boolean feasible5 = true;
		boolean feasible6 = true;
		boolean feasible7 = true;
		boolean feasible8 = true;
		boolean feasible9 = true;
		boolean feasible10 = true;
		boolean feasible11 = true;
		boolean feasible12 = true;
		boolean feasible13 = true;
		boolean trackfree = true;



		//reset the sequence array
		for (int i = 0; i<sequence.length; i++){
			sequence[i] = -1;
		}


		//It checks for all possible activities
		for(int j = 0; j<4; j++){
			temp = 1440;
			temptemp = 1440;
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
				if (j!=3){
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
				else{
					for (int i=0; i<addedcomp.getSize(); i++){
						if (addedcomp.getTrain(i).getActivity(j)){
							durationactivity += addedcomp.getTrain(i).getActivityTimeInteger(j);
						}
					}
				}
			}

			//Only if the activity must really be done
			if(durationactivity>0){

				//We always start with j == 0, because this activity must always be performed first.
				if(j == 0 || j == 1 || j == 2){

					//Add an activity

					activities.add(new Activity(durationactivity, (int) addedcomp.getDepartureTimeInteger()-durationactivity, addedcomp, j));
					if (j==1 || j==2){
						amount += 1;
					}

					//We find the soonest possible time to start the activity looking at each possible track the activity can be done.
					for(int k = 0; k<platforms.size(); k++){
						for(int l = mintemp; l<activities.get(activities.size()-1).getUltimateTimeInteger(); l++){

							if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1), l)){
									if(this.checkFeasibilityMove(activities.get(activities.size()-1),l)){
										temptemp = l;

										temptemp1 = platforms.get(k);
										break;
									}
								}
							}
						}

						if(temptemp < temp){
							temp = temptemp;
							temp1 = temptemp1;
						}

					}


					for(int k = 0; k<platformsreserve.size(); k++){
						trackfree = true;
						index = 0;
						for(int l = mintemp; l<activities.get(activities.size()-1).getUltimateTimeInteger(); l++){
							if (platforms.get(k).getBusy(l)){
								index = l;
								trackfree = false;
								break;
							}
						}

						if (trackfree == false){
							if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

								for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
									if(l >= mintemp){

										if(platformsreserve.get(k).checkFeasibility(activities.get(activities.size()-1), l)){
											if(addedcomp.checkFeasibility(activities.get(activities.size()-1), l)){
												if(this.checkFeasibilityMove(activities.get(activities.size()-1),l)){
													if(getFeasibleReserve( l,  k, 0, addedcomp)){
														temptemp = l;

														temptemp1 = platformsreserve.get(k);
														break;
													}
												}
											}
										}
									}
								}
							}
						}

						if(temptemp<temp){
							temp = temptemp;
							temp1 = temptemp1;
						}	

					}

					if(temp == 1440){
						feasible1 = false;
					}
					else{
						activities.get(activities.size()-1).setUpdate(temp, temp1);
						//System.out.println("asdfafasdfa : " + temp1);
						addedcomp.setBusyTime(activities.get(activities.size()-1));

						temp1.setBusyTime(activities.get(activities.size()-1)); 
						this.setBusyTime(activities.get(activities.size()-1));
						//Minimum time to loop from must be update since inspection cannot be moved later
						if(j == 0){	
							time10 = activities.get(activities.size()-1).getPlannedTimeInteger();
							track10 = activities.get(activities.size()-1).getTrackAssigned();


							mintemp = time10 + durationactivity;

							margin1 = this.getObjective(margin1, 0);
							/*if(activities.get(activities.size()-1).getMarginInteger()<margin1){
								margin1 = activities.get(activities.size()-1).getMarginInteger();
							}*/
						}
						//Storing the first solution and keeping track of how many activities are done on the composition, except for inspection though
						else if(j == 1){
							time11 = activities.get(activities.size()-1).getPlannedTimeInteger();
							track11 = activities.get(activities.size()-1).getTrackAssigned();

							margin1 = this.getObjective(margin1, 0);
							/*
							if(activities.get(activities.size()-1).getMarginInteger()<margin1){
								margin1 = activities.get(activities.size()-1).getMarginInteger();
							}
							 */
						}

						//Storing the first solution ............. see above.
						else if(j == 2){
							time12 = activities.get(activities.size()-1).getPlannedTimeInteger();
							track12 = activities.get(activities.size()-1).getTrackAssigned();
							margin1 = this.getObjective(margin1, 0);

							/*if(activities.get(activities.size()-1).getMarginInteger()<margin1){
								margin1 = activities.get(activities.size()-1).getMarginInteger();
							}*/
						}
						currenttrack = temp1;
					}

				}

				//If activity is washing, we have to look at other tracks, i.e. wash areas.
				else if(j == 3){
					activities.add(new Activity(durationactivity, addedcomp.getDepartureTimeInteger()-durationactivity, addedcomp, j));

					//Keeping track of amount of activities done except for inspection
					amount += 1;

					//We find the soonest possible time to start the activity looking at each possible track the activity can be done.
					for(int k = 0; k<washareas.size(); k++){

						for(int l = mintemp; l<activities.get(activities.size()-1).getUltimateTime(); l++){
							if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1), l)){
									if(this.checkFeasibilityMove(activities.get(activities.size()-1),l)){

										temptemp = l;
										temptemp1 = washareas.get(k);
										break;
									}
								}
							}
						}

						//Update if we find better solution than the previous ones at a different track.
						if(temptemp <= temp){
							temp = temptemp;
							temp1 = temptemp1;
						}
					}
					if(temp == 1440){
						feasible1 = false;
					}
					else{
						activities.get(activities.size()-1).setUpdate(temp, temp1);
						currenttrack = temp1;

						//Storing first solution
						addedcomp.setBusyTime(activities.get(activities.size()-1));
						temp1.setBusyTime(activities.get(activities.size()-1)); 
						this.setBusyTime(activities.get(activities.size()-1));

						time13 = activities.get(activities.size()-1).getPlannedTimeInteger();
						track13 = activities.get(activities.size()-1).getTrackAssigned();

						margin1 = this.getObjective(margin1, 0);

						/*
						if(activities.get(activities.size()-1).getMarginInteger()<margin1){
							margin1 = activities.get(activities.size()-1).getMarginInteger();
						}*/

					}
				}
			}
		}	

		//System.out.println(feasible1 + " " + activities.get(activities.size()-1-amount).getActivity() + " " + activities.get(activities.size()-1-amount).getTotalDurationInteger() + " " + activities.get(activities.size()-amount).getActivity() + " " + activities.get(activities.size()-amount).getTotalDurationInteger());

		if(feasible1 == false){
			margin1 = -1;
		}
		else{
			bestoption = 1;
			bestmargin = margin1;
		}
		if(track10 != null){




			//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
			for(int i = 0; i<amount; i++){
				if(activities.get(activities.size()-1-i).getTrackAssigned()!= null){
					activities.get(activities.size()-1-i).removeTimes();
					this.removeBusyTime(activities.get(activities.size()-1-i));
				}
			}
			if(addedcomp.getInspection()){
				currenttrack = activities.get(activities.size()-1-amount).getTrackAssigned();
			}
			else{
				currenttrack = null;
			}

			//reset the sequence array
			for (int i = 0; i<sequence.length; i++){
				sequence[i] = -1;
			}

			//determine the right sequence of how the activities should be planned, in this case: 1, 3, 2.
			for (int j = 0; j<amount; j++){
				for(int i = 1; i<4; i++){
					if (activities.get(activities.size()-1-j).getActivity() == i){
						if (i == 3){
							sequence[0] = j;
						}
						else if (i == 2){
							sequence[1] = j;
						}
						else if (i == 1){
							sequence[2] = j;
						}
						break;
					}
				}
			}





			//ADDING OPTION 2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!





			//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
			for(int j = 0; j<sequence.length; j++){
				if (sequence[j]!=-1){
					activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
					temp = 1440;
					temptemp = 1440;
					temp1 = null;
					temptemp1 = null;
					if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

						for(int k = 0; k<washareas.size(); k++){
							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

											temptemp = l;
											temptemp1 = washareas.get(k);
											break;
										}
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}

						}
						if(temp == 1440){
							feasible2 = false;
						}
						else{
							activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
							this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							currenttrack = temp1;
							time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
							track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();

							margin2 = this.getObjective(margin2, sequence[j]);
							/*
						if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin2){
							margin2 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
						}*/
						}
					}
					else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1 || activities.get(activities.size()-1-sequence[j]).getActivity() == 2){

						for(int k = 0; k<platforms.size(); k++){

							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

											temptemp = l;
											temptemp1 = platforms.get(k);
											break;
										}
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}

						for(int k = 0; k<platformsreserve.size(); k++){
							trackfree = true;
							index = 0;
							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if (platforms.get(k).getBusy(l)){
									index = l;
									trackfree = false;
									break;
								}
							}

							if (trackfree == false){
								if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

									for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
										if(l >= mintemp){

											if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
															temptemp = l;

															temptemp1 = platformsreserve.get(k);
															break;
											}
										}
									}
								}
							}

							if(temptemp<temp){
								temp = temptemp;
								temp1 = temptemp1;
							}	

						}


						if(temp == 1440){
							feasible2 = false;
						}
						else{
							activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
							currenttrack = temp1;

							addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
							this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1){
								time21 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track21 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin2 = this.getObjective(margin2, sequence[j]);

								/*if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin2){
								margin2 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}*/
							}
							else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 2){
								time22 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track22 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin2 = this.getObjective(margin2, sequence[j]);


								/*if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin2){
								margin2 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

							}*/
							}
						}
					}
				}
			}
			//		System.out.println(option);
			//		System.out.println(time20 + " " + track20);
			//		System.out.println(time21 + " " + track21);
			//		System.out.println(time22 + " " + track22);
			//		System.out.println(time23 + " " + track23);
			//		System.out.println(feasible1);
			//		System.out.println(feasible2);
			if(feasible2 == false){
				margin2 = -1;
			}
			else if(margin2 > bestmargin){
				time11 = time21;
				time12 = time22;
				time13 = time23;
				track11 = track21;
				track12 = track22;
				track13 = track23;
				bestmargin = margin2;
				bestoption = 2;
			}
			//		System.out.println("This is the best option!");
			//		System.out.println(time10 + " " + track10);
			//		System.out.println(time11 + " " + track11);
			//		System.out.println(time12 + " " + track12);
			//		System.out.println(time13 + " " + track13);

			//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
			for(int i = 0; i<amount; i++){
				if(activities.get(activities.size()-1-i).getTrackAssigned()!= null){
					activities.get(activities.size()-1-i).removeTimes();
					this.removeBusyTime(activities.get(activities.size()-1-i));
				}
			}
			if(addedcomp.getInspection()){
				currenttrack = activities.get(activities.size()-1-amount).getTrackAssigned();
			}
			else{
				currenttrack = null;
			}

			//reset the sequence array
			for (int i = 0; i<sequence.length; i++){
				sequence[i] = -1;
			}

			//determine the right sequence of how the activities should be planned, in this case: 1, 3, 2.
			for (int j = 0; j<amount; j++){
				for(int i = 1; i<4; i++){
					if (activities.get(activities.size()-1-j).getActivity() == i){
						if (i == 3){
							sequence[0] = j;
						}
						else if (i == 1){
							sequence[1] = j;
						}
						else if (i == 2){
							sequence[2] = j;
						}
						break;
					}
				}
			}




			//ADDING OPTION 3!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!





			//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
			for(int j = 0; j<sequence.length; j++){
				if (sequence[j]!=-1){
					activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
					temp = 1440;
					temptemp = 1440;
					temp1 = null;
					temptemp1 = null;
					if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

						for(int k = 0; k<washareas.size(); k++){
							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

											temptemp = l;
											temptemp1 = washareas.get(k);
											break;
										}
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}

						}
						if(temp == 1440){
							feasible3 = false;
						}
						else{
							activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
							this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							currenttrack = temp1;
							time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
							track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned(); 

							margin3 = this.getObjective(margin3, sequence[j]);
							/*
						if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin3){
							margin3 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
						}*/
						}
					}
					else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1 || activities.get(activities.size()-1-sequence[j]).getActivity() == 2){

						for(int k = 0; k<platforms.size(); k++){

							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

											temptemp = l;
											temptemp1 = platforms.get(k);
											break;
										}
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}

						for(int k = 0; k<platformsreserve.size(); k++){
							trackfree = true;
							index = 0;
							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if (platforms.get(k).getBusy(l)){
									index = l;
									trackfree = false;
									break;
								}
							}

							if (trackfree == false){
								if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

									for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
										if(l >= mintemp){

											if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
															temptemp = l;

															temptemp1 = platformsreserve.get(k);
															break;
												
											}
										}
									}
								}
							}

							if(temptemp<temp){
								temp = temptemp;
								temp1 = temptemp1;
							}	

						}


						if(temp == 1440){
							feasible3 = false;
						}
						else{
							activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
							currenttrack = temp1;

							addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
							this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1){
								time21 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track21 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin3 = this.getObjective(margin3, sequence[j]);

								/*
							if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin3){
								margin3 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}*/
							}
							else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 2){
								time22 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track22 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin3 = this.getObjective(margin3, sequence[j]);

								/*if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin3){
								margin3 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

							}*/
							}
						}
					}
				}
			}
			if(feasible3 == false){
				margin3 = -1;
			}
			else if(margin3 > bestmargin){
				time11 = time21;
				time12 = time22;
				time13 = time23;
				track11 = track21;
				track12 = track22;
				track13 = track23;
				bestmargin = margin3;
				bestoption = 3;
			}


			//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
			for(int i = 0; i<amount; i++){
				if(activities.get(activities.size()-1-i).getTrackAssigned()!= null){
					activities.get(activities.size()-1-i).removeTimes();
					this.removeBusyTime(activities.get(activities.size()-1-i));
				}
			}
			if(addedcomp.getInspection()){
				currenttrack = activities.get(activities.size()-1-amount).getTrackAssigned();
			}
			else{
				currenttrack = null;
			}

			//reset the sequence array
			for (int i = 0; i<sequence.length; i++){
				sequence[i] = -1;
			}

			//determine the right sequence of how the activities should be planned, in this case: 1, 3, 2.
			for (int j = 0; j<amount; j++){
				for(int i = 1; i<4; i++){
					if (activities.get(activities.size()-1-j).getActivity() == i){
						if (i == 1){
							sequence[0] = j;
						}
						else if (i == 3){
							sequence[1] = j;
						}
						else if (i == 2){
							sequence[2] = j;
						}
						break;
					}
				}
			}




			//ADDING OPTION 4!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!




			//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
			for(int j = 0; j<sequence.length; j++){
				if (sequence[j]!=-1){
					activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
					temp = 1440;
					temptemp = 1440;
					temp1 = null;
					temptemp1 = null;
					if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

						for(int k = 0; k<washareas.size(); k++){
							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

											temptemp = l;
											temptemp1 = washareas.get(k);
											break;
										}
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}

						}
						if(temp == 1440){
							feasible4 = false;
						}
						else{
							activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
							this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							currenttrack = temp1;
							time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
							track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
							margin4 = this.getObjective(margin4, sequence[j]);

							/*
						if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin4){
							margin4 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
						}*/
						}
					}
					else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1 || activities.get(activities.size()-1-sequence[j]).getActivity() == 2){

						for(int k = 0; k<platforms.size(); k++){

							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

											temptemp = l;
											temptemp1 = platforms.get(k);
											break;
										}
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}

						for(int k = 0; k<platformsreserve.size(); k++){
							trackfree = true;
							index = 0;
							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if (platforms.get(k).getBusy(l)){
									index = l;
									trackfree = false;
									break;
								}
							}

							if (trackfree == false){
								if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

									for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
										if(l >= mintemp){

											if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
															temptemp = l;

															temptemp1 = platformsreserve.get(k);
															break;
											}
										}
									}
								}
							}

							if(temptemp<temp){
								temp = temptemp;
								temp1 = temptemp1;
							}	

						}


						if(temp == 1440){
							feasible4 = false;
						}
						else{
							activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
							currenttrack = temp1;

							addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
							this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1){
								time21 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track21 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin4 = this.getObjective(margin4, sequence[j]);


								/*if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin4){
								margin4 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}*/
							}
							else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 2){
								time22 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track22 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin4 = this.getObjective(margin4, sequence[j]);

								/*if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin4){
								margin4 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

							}*/
							}
						}
					}
				}
			}
			if(feasible4 == false){
				margin4 = -1;
			}
			else if(margin4 > bestmargin){
				time11 = time21;
				time12 = time22;
				time13 = time23;
				track11 = track21;
				track12 = track22;
				track13 = track23;
				bestmargin = margin4;
				bestoption = 4;
			}



			//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
			for(int i = 0; i<amount; i++){
				if(activities.get(activities.size()-1-i).getTrackAssigned()!= null){
					activities.get(activities.size()-1-i).removeTimes();
					this.removeBusyTime(activities.get(activities.size()-1-i));
				}
			}
			if(addedcomp.getInspection()){
				currenttrack = activities.get(activities.size()-1-amount).getTrackAssigned();
			}
			else{
				currenttrack = null;
			}

			//reset the sequence array
			for (int i = 0; i<sequence.length; i++){
				sequence[i] = -1;
			}

			//determine the right sequence of how the activities should be planned, in this case: 1, 3, 2.
			for (int j = 0; j<amount; j++){
				for(int i = 1; i<4; i++){
					if (activities.get(activities.size()-1-j).getActivity() == i){
						if (i == 2){
							sequence[0] = j;
						}
						else if (i == 1){
							sequence[1] = j;
						}
						else if (i == 3){
							sequence[2] = j;
						}
						break;
					}
				}
			}




			//ADDING OPTION 5!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!




			//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
			for(int j = 0; j<sequence.length; j++){
				if (sequence[j]!=-1){
					activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
					temp = 1440;
					temptemp = 1440;
					temp1 = null;
					temptemp1 = null;
					if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

						for(int k = 0; k<washareas.size(); k++){
							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

											temptemp = l;
											temptemp1 = washareas.get(k);
											break;
										}
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}

						}

						for(int k = 0; k<platformsreserve.size(); k++){
							trackfree = true;
							index = 0;
							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if (platforms.get(k).getBusy(l)){
									index = l;
									trackfree = false;
									break;
								}
							}

							if (trackfree == false){
								if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

									for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
										if(l >= mintemp){

											if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
															temptemp = l;

															temptemp1 = platformsreserve.get(k);
															break;
												
											}
										}
									}
								}
							}

							if(temptemp<temp){
								temp = temptemp;
								temp1 = temptemp1;
							}	

						}


						if(temp == 1440){
							feasible5 = false;
						}
						else{
							activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
							this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							currenttrack = temp1;
							time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
							track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
							margin5 = this.getObjective(margin5, sequence[j]);

							/*
						if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin5){
							margin5 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
						}*/
						}
					}
					else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1 || activities.get(activities.size()-1-sequence[j]).getActivity() == 2){

						for(int k = 0; k<platforms.size(); k++){

							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

											temptemp = l;
											temptemp1 = platforms.get(k);
											break;
										}
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}

						if(temp == 1440){
							feasible5 = false;
						}
						else{
							activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
							currenttrack = temp1;

							addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
							this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1){
								time21 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track21 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin5 = this.getObjective(margin5, sequence[j]);

								/*
							if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin5){
								margin5 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}*/
							}
							else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 2){
								time22 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track22 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin5 = this.getObjective(margin5, sequence[j]);


								/*if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin5){
								margin5 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

							}*/
							}
						}
					}
				}
			}
			if(feasible5 == false){
				margin5 = -1;
			}
			else if(margin5 > bestmargin){
				time11 = time21;
				time12 = time22;
				time13 = time23;
				track11 = track21;
				track12 = track22;
				track13 = track23;
				bestmargin = margin5;
				bestoption = 5;
			}



			//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
			for(int i = 0; i<amount; i++){
				if(activities.get(activities.size()-1-i).getTrackAssigned()!= null){
					activities.get(activities.size()-1-i).removeTimes();
					this.removeBusyTime(activities.get(activities.size()-1-i));
				}
			}
			if(addedcomp.getInspection()){
				currenttrack = activities.get(activities.size()-1-amount).getTrackAssigned();
			}
			else{
				currenttrack = null;
			}

			//reset the sequence array
			for (int i = 0; i<sequence.length; i++){
				sequence[i] = -1;
			}

			//determine the right sequence of how the activities should be planned, in this case: 1, 3, 2.
			for (int j = 0; j<amount; j++){
				for(int i = 1; i<4; i++){
					if (activities.get(activities.size()-1-j).getActivity() == i){
						if (i == 2){
							sequence[0] = j;
						}
						else if (i == 3){
							sequence[1] = j;
						}
						else if (i == 1){
							sequence[2] = j;
						}
						break;
					}
				}
			}




			//ADDING OPTION 6!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!




			//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
			for(int j = 0; j<sequence.length; j++){
				if (sequence[j]!=-1){
					activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
					temp = 1440;
					temptemp = 1440;
					temp1 = null;
					temptemp1 = null;
					if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

						for(int k = 0; k<washareas.size(); k++){
							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

											temptemp = l;
											temptemp1 = washareas.get(k);
											break;
										}
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}

						}
						if(temp == 1440){
							feasible6 = false;
						}
						else{
							activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
							this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							currenttrack = temp1;
							time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
							track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
							margin6 = this.getObjective(margin6, sequence[j]);

							/*
						if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin6){
							margin6 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
						}
							 */
						}
					}
					else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1 || activities.get(activities.size()-1-sequence[j]).getActivity() == 2){

						for(int k = 0; k<platforms.size(); k++){

							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

											temptemp = l;
											temptemp1 = platforms.get(k);
											break;
										}
									}
								}
							}
							//Update if we find better solution than the previous ones at a different track.
							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}

						for(int k = 0; k<platformsreserve.size(); k++){
							trackfree = true;
							index = 0;
							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
								if (platforms.get(k).getBusy(l)){
									index = l;
									trackfree = false;
									break;
								}
							}

							if (trackfree == false){
								if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

									for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
										if(l >= mintemp){

											if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
															temptemp = l;

															temptemp1 = platformsreserve.get(k);
															break;
							
											}
										}
									}
								}
							}

							if(temptemp<temp){
								temp = temptemp;
								temp1 = temptemp1;
							}	

						}


						if(temp == 1440){
							feasible6 = false;
						}
						else{
							activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
							currenttrack = temp1;

							addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
							this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
							if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1){
								time21 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track21 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin6 = this.getObjective(margin6, sequence[j]);

								/*if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin6){
								margin6 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}*/
							}
							else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 2){
								time22 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track22 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin6 = this.getObjective(margin6, sequence[j]);


								/*if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin6){
								margin6 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

							}*/
							}
						}
					}
				}
			}
			if(feasible6 == false){
				margin6 = -1;
			}
			else if(margin6 > bestmargin){
				time11 = time21;
				time12 = time22;
				time13 = time23;
				track11 = track21;
				track12 = track22;
				track13 = track23;
				bestmargin = margin6;
				bestoption = 6;
			}


			//ADDING THE INSPECTION ACTIVITY
			if(addedcomp.getInspection()){
				amount += 1;
			}










			//ADDING OPTION 7!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!









			if(addedcomp.getInspection() && addedcomp.getCleaning()){
				//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
						activities.get(activities.size()-1-i).removeTimes();
						this.removeBusyTime(activities.get(activities.size()-1-i));
					}
				}
				//			if(addedcomp.getInspection()){
				//				currenttrack = activities.get(activities.size()-amount).getTrackAssigned();
				//			}
				//			else{
				currenttrack = null;
				//			}

				//reset the sequence array
				for (int i = 0; i<sequence.length; i++){
					sequence[i] = -1;
				}

				mintemp = addedcomp.getArrivalTimeInteger();



				// add the merged activity 5 which consists of activities 0 and 1. 
				activities.add(this.mergeActivities(addedcomp, 0, 1, 5));
				amount += 1;

				//System.out.println(addedcomp.getTrain(0).getActivityTimeInteger(0) + " " + addedcomp.getTrain(0).getActivityTimeInteger(1) + " " + activities.get(activities.size()-1).getTotalDurationInteger());


				for (int j = 0; j<amount; j++){
					for(int i = 0; i<9; i++){
						if (activities.get(activities.size()-1-j).getActivity() == i){			
							if (i == 5){
								sequence[0] = j;
							}
							else if (i == 2){
								sequence[1] = j;
							}
							else if (i == 3){
								sequence[2] = j;
							}
							break;
						}
					}
				}

				//System.out.println(sequence[0] + " " + sequence[1] + " " + sequence[2]);

				//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
				for(int j = 0; j<sequence.length; j++){
					if (sequence[j]!=-1){
						activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
						temp = 1440;
						temptemp = 1440;
						temp1 = null;
						temptemp1 = null;
						if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

							for(int k = 0; k<washareas.size(); k++){
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = washareas.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}

							}
							if(temp == 1440){
								feasible7 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								currenttrack = temp1;
								time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin7 = this.getObjective(margin7, sequence[j]);

								/*
							if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin7){
								margin7 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}*/
							}
						}
						else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 5 || activities.get(activities.size()-1-sequence[j]).getActivity() == 2){

							for(int k = 0; k<platforms.size(); k++){

								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = platforms.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}
							}

							for(int k = 0; k<platformsreserve.size(); k++){
								trackfree = true;
								index = 0;
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if (platforms.get(k).getBusy(l)){
										index = l;
										trackfree = false;
										break;
									}
								}

								if (trackfree == false){
									if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

										for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
											if(l >= mintemp){

												if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
																temptemp = l;

																temptemp1 = platformsreserve.get(k);
																break;
												}
											}
										}
									}
								}

								if(temptemp<temp){
									temp = temptemp;
									temp1 = temptemp1;
								}	

							}

							if(temp == 1440){
								feasible7 = false;
								//System.out.println("IM NOT FEASIBLE" + feasible7);
							}
							else{
								//System.out.println("IM FEASIBLE" + feasible7);
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								currenttrack = temp1;

								addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								if(activities.get(activities.size()-1-sequence[j]).getActivity() == 5){
									//System.out.println("JOEHOEEEE");

									time25 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track25 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();

									mintemp = time25 + activities.get(activities.size()-1-sequence[j]).getTotalDurationInteger();
									margin7 = this.getObjective(margin7, sequence[j]);

									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin7){
									margin7 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
								}*/
								}
								else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 2){
									time22 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track22 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
									margin7 = this.getObjective(margin7, sequence[j]);

									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin7){
									margin7 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

								}*/
								}
							}
						}
					}
				}
				//System.out.println(feasible7 + " " + activities.get(activities.size()-1).getActivity() + " " + activities.get(activities.size()-1).getTotalDurationInteger());

				if(feasible7 == false){
					margin7 = -1;
				}
				else if(margin7 >= bestmargin){
					time15 = time25;
					time12 = time22;
					time13 = time23;
					track15 = track25;
					track12 = track22;
					track13 = track23;
					bestmargin = margin7;
					bestoption = 7;
				}

			}












			//ADDING OPTION 8!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!











			if(addedcomp.getInspection() && addedcomp.getCleaning()){
				//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
						activities.get(activities.size()-1-i).removeTimes();
						this.removeBusyTime(activities.get(activities.size()-1-i));
					}
				}
				//			if(addedcomp.getInspection()){
				//				currenttrack = activities.get(activities.size()-amount).getTrackAssigned();
				//			}
				//			else{
				currenttrack = null;
				//			}

				//reset the sequence array
				for (int i = 0; i<sequence.length; i++){
					sequence[i] = -1;
				}

				mintemp = addedcomp.getArrivalTimeInteger();


				//System.out.println(addedcomp.getTrain(0).getActivityTimeInteger(0) + " " + addedcomp.getTrain(0).getActivityTimeInteger(1) + " " + activities.get(activities.size()-1).getTotalDurationInteger());


				for (int j = 0; j<amount; j++){
					for(int i = 0; i<9; i++){
						if (activities.get(activities.size()-1-j).getActivity() == i){			
							if (i == 5){
								sequence[0] = j;
							}
							else if (i == 3){
								sequence[1] = j;
							}
							else if (i == 2){
								sequence[2] = j;
							}
							break;
						}
					}
				}

				//System.out.println(sequence[0] + " " + sequence[1] + " " + sequence[2]);

				//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
				for(int j = 0; j<sequence.length; j++){
					if (sequence[j]!=-1){
						activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
						temp = 1440;
						temptemp = 1440;
						temp1 = null;
						temptemp1 = null;
						if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

							for(int k = 0; k<washareas.size(); k++){
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = washareas.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}

							}
							if(temp == 1440){
								feasible8 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								currenttrack = temp1;
								time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin8 = this.getObjective(margin8, sequence[j]);

								/*
							if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin8){
								margin8 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}*/
							}
						}
						else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 5 || activities.get(activities.size()-1-sequence[j]).getActivity() == 2){

							for(int k = 0; k<platforms.size(); k++){

								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = platforms.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}
							}

							for(int k = 0; k<platformsreserve.size(); k++){
								trackfree = true;
								index = 0;
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if (platforms.get(k).getBusy(l)){
										index = l;
										trackfree = false;
										break;
									}
								}

								if (trackfree == false){
									if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

										for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
											if(l >= mintemp){

												if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
																temptemp = l;

																temptemp1 = platformsreserve.get(k);
																break;
												}
											}
										}
									}
								}

								if(temptemp<temp){
									temp = temptemp;
									temp1 = temptemp1;
								}	

							}


							if(temp == 1440){
								feasible8 = false;
								//System.out.println("IM NOT FEASIBLE" + feasible7);
							}
							else{
								//System.out.println("IM FEASIBLE" + feasible7);
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								currenttrack = temp1;

								addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								if(activities.get(activities.size()-1-sequence[j]).getActivity() == 5){
									//System.out.println("JOEHOEEEE");

									time25 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track25 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();

									mintemp = time25 + activities.get(activities.size()-1-sequence[j]).getTotalDurationInteger();
									margin8 = this.getObjective(margin8, sequence[j]);

									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin8){
									margin8 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
								}*/
								}
								else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 2){
									time22 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track22 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
									margin8 = this.getObjective(margin8, sequence[j]);

									/*if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin8){
									margin8 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

								}*/
								}
							}
						}
					}
				}
				//System.out.println(feasible8 + " " + activities.get(activities.size()-1).getActivity() + " " + activities.get(activities.size()-1).getTotalDurationInteger());

				if(feasible8 == false){
					margin8 = -1;
				}
				else if(margin8 >= bestmargin){
					time15 = time25;
					time12 = time22;
					time13 = time23;
					track15 = track25;
					track12 = track22;
					track13 = track23;
					bestmargin = margin8;
					bestoption = 8;
				}

			}

















			//ADDING OPTION 9!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!









			if(addedcomp.getCleaning() && addedcomp.getRepairing()){
				//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
						activities.get(activities.size()-1-i).removeTimes();
						this.removeBusyTime(activities.get(activities.size()-1-i));
					}
				}
				//			if(addedcomp.getInspection()){
				//				currenttrack = activities.get(activities.size()-amount).getTrackAssigned();
				//			}
				//			else{
				currenttrack = null;
				//			}

				//reset the sequence array
				for (int i = 0; i<sequence.length; i++){
					sequence[i] = -1;
				}

				mintemp = addedcomp.getArrivalTimeInteger();


				// add the merged activity 5 which consists of activities 0 and 1. 
				activities.add(this.mergeActivities(addedcomp, 1, 2, 6));
				amount += 1;

				//System.out.println(addedcomp.getTrain(0).getActivityTimeInteger(0) + " " + addedcomp.getTrain(0).getActivityTimeInteger(1) + " " + activities.get(activities.size()-1).getTotalDurationInteger());


				for (int j = 0; j<amount; j++){
					for(int i = 0; i<9; i++){
						if (activities.get(activities.size()-1-j).getActivity() == i){			
							if (i == 0){
								sequence[0] = j;
							}
							else if (i == 6){
								sequence[1] = j;
							}
							else if (i == 3){
								sequence[2] = j;
							}
							break;
						}
					}
				}

				//System.out.println(sequence[0] + " " + sequence[1] + " " + sequence[2]);

				//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
				for(int j = 0; j<sequence.length; j++){
					if (sequence[j]!=-1){
						activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
						temp = 1440;
						temptemp = 1440;
						temp1 = null;
						temptemp1 = null;
						if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

							for(int k = 0; k<washareas.size(); k++){
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = washareas.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}

							}

							for(int k = 0; k<platformsreserve.size(); k++){
								trackfree = true;
								index = 0;
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if (platforms.get(k).getBusy(l)){
										index = l;
										trackfree = false;
										break;
									}
								}

								if (trackfree == false){
									if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

										for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
											if(l >= mintemp){

												if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
																temptemp = l;

																temptemp1 = platformsreserve.get(k);
																break;
												}
											}
										}
									}
								}

								if(temptemp<temp){
									temp = temptemp;
									temp1 = temptemp1;
								}	

							}


							if(temp == 1440){
								feasible9 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								currenttrack = temp1;
								time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin9 = this.getObjective(margin9, sequence[j]);

								/*
							if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin9){
								margin9 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}
								 */
							}
						}
						else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 0 || activities.get(activities.size()-1-sequence[j]).getActivity() == 6){

							for(int k = 0; k<platforms.size(); k++){

								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = platforms.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}
							}

							if(temp == 1440){
								feasible9 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								currenttrack = temp1;

								addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								if(activities.get(activities.size()-1-sequence[j]).getActivity() == 6){

									time26 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track26 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
									margin9 = this.getObjective(margin9, sequence[j]);


									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin9){
									margin9 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
								}
									 */
								}
								else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 0){
									time20 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track20 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();

									mintemp = time20 + activities.get(activities.size()-1-sequence[j]).getTotalDurationInteger();
									margin9 = this.getObjective(margin9, sequence[j]);

									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin9){
									margin9 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

								}
									 */
								}
							}
						}
					}
				}
				//System.out.println(margin + " " + bestmargin);

				if(feasible9 == false){
					margin9 = -1;
				}
				else if(margin9 >= bestmargin){
					time10 = time20;
					time16 = time26;
					time13 = time23;
					track10 = track20;
					track16 = track26;
					track13 = track23;
					bestmargin = margin9;
					bestoption = 9;
				}

			}

















			//ADDING OPTION 10!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!












			if(addedcomp.getCleaning() && addedcomp.getRepairing()){
				//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
						activities.get(activities.size()-1-i).removeTimes();
						this.removeBusyTime(activities.get(activities.size()-1-i));
					}
				}
				//			if(addedcomp.getInspection()){
				//				currenttrack = activities.get(activities.size()-amount).getTrackAssigned();
				//			}
				//			else{
				currenttrack = null;
				//			}

				//reset the sequence array
				for (int i = 0; i<sequence.length; i++){
					sequence[i] = -1;
				}

				mintemp = addedcomp.getArrivalTimeInteger();


				//System.out.println(addedcomp.getTrain(0).getActivityTimeInteger(0) + " " + addedcomp.getTrain(0).getActivityTimeInteger(1) + " " + activities.get(activities.size()-1).getTotalDurationInteger());


				for (int j = 0; j<amount; j++){
					for(int i = 0; i<9; i++){
						if (activities.get(activities.size()-1-j).getActivity() == i){			
							if (i == 0){
								sequence[0] = j;
							}
							else if (i == 3){
								sequence[1] = j;
							}
							else if (i == 6){
								sequence[2] = j;
							}
							break;
						}
					}
				}

				//System.out.println(sequence[0] + " " + sequence[1] + " " + sequence[2]);

				//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
				for(int j = 0; j<sequence.length; j++){
					if (sequence[j]!=-1){
						activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
						temp = 1440;
						temptemp = 1440;
						temp1 = null;
						temptemp1 = null;
						if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

							for(int k = 0; k<washareas.size(); k++){
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = washareas.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}

							}
							if(temp == 1440){
								feasible10 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								currenttrack = temp1;
								time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin10 = this.getObjective(margin10, sequence[j]);

								/*
							if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin10){
								margin10 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}
								 */
							}
						}
						else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 0 || activities.get(activities.size()-1-sequence[j]).getActivity() == 6){

							for(int k = 0; k<platforms.size(); k++){

								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = platforms.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}
							}

							for(int k = 0; k<platformsreserve.size(); k++){
								trackfree = true;
								index = 0;
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if (platforms.get(k).getBusy(l)){
										index = l;
										trackfree = false;
										break;
									}
								}

								if (trackfree == false){
									if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

										for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
											if(l >= mintemp){

												if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
																temptemp = l;

																temptemp1 = platformsreserve.get(k);
																break;
															}
											}
										}
									}
								}

								if(temptemp<temp){
									temp = temptemp;
									temp1 = temptemp1;
								}	

							}


							if(temp == 1440){
								feasible10 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								currenttrack = temp1;

								addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								if(activities.get(activities.size()-1-sequence[j]).getActivity() == 6){

									time26 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track26 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();

									margin10 = this.getObjective(margin10, sequence[j]);

									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin10){
									margin10 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
								}*/
								}
								else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 0){
									time20 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track20 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();

									mintemp = time20 + activities.get(activities.size()-1-sequence[j]).getTotalDurationInteger();
									margin10 = this.getObjective(margin10, sequence[j]);

									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin10){
									margin10 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

								}*/
								}
							}
						}
					}
				}
				//System.out.println(margin + " " + bestmargin);

				if(feasible10 == false){
					margin10 = -1;
				}
				else if(margin10 >= bestmargin){
					time10 = time20;
					time16 = time26;
					time13 = time23;
					track10 = track20;
					track16 = track26;
					track13 = track23;
					bestmargin = margin10;
					bestoption = 10;
				}

			}

























			//ADDING OPTION 11!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!









			if(addedcomp.getInspection() && addedcomp.getRepairing()){
				//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
						activities.get(activities.size()-1-i).removeTimes();
						this.removeBusyTime(activities.get(activities.size()-1-i));
					}
				}
				//			if(addedcomp.getInspection()){
				//				currenttrack = activities.get(activities.size()-amount).getTrackAssigned();
				//			}
				//			else{
				currenttrack = null;
				//			}

				//reset the sequence array
				for (int i = 0; i<sequence.length; i++){
					sequence[i] = -1;
				}

				mintemp = addedcomp.getArrivalTimeInteger();


				// add the merged activity 5 which consists of activities 0 and 1. 
				activities.add(this.mergeActivities(addedcomp, 0, 2, 7));
				amount += 1;

				//System.out.println(addedcomp.getTrain(0).getActivityTimeInteger(0) + " " + addedcomp.getTrain(0).getActivityTimeInteger(1) + " " + activities.get(activities.size()-1).getTotalDurationInteger());


				for (int j = 0; j<amount; j++){
					for(int i = 0; i<9; i++){
						if (activities.get(activities.size()-1-j).getActivity() == i){			
							if (i == 7){
								sequence[0] = j;
							}
							else if (i == 1){
								sequence[1] = j;
							}
							else if (i == 3){
								sequence[2] = j;
							}
							break;
						}
					}
				}

				//System.out.println(sequence[0] + " " + sequence[1] + " " + sequence[2]);

				//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
				for(int j = 0; j<sequence.length; j++){
					if (sequence[j]!=-1){
						activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
						temp = 1440;
						temptemp = 1440;
						temp1 = null;
						temptemp1 = null;
						if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

							for(int k = 0; k<washareas.size(); k++){
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = washareas.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}

							}
							if(temp == 1440){
								feasible11 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								currenttrack = temp1;
								time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin11 = this.getObjective(margin11, sequence[j]);

								/*
							if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin11){
								margin11 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}*/
							}
						}
						else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1 || activities.get(activities.size()-1-sequence[j]).getActivity() == 7){

							for(int k = 0; k<platforms.size(); k++){

								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = platforms.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}
							}

							for(int k = 0; k<platformsreserve.size(); k++){
								trackfree = true;
								index = 0;
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if (platforms.get(k).getBusy(l)){
										index = l;
										trackfree = false;
										break;
									}
								}

								if (trackfree == false){
									if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

										for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
											if(l >= mintemp){

												if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
																temptemp = l;

																temptemp1 = platformsreserve.get(k);
																break;

												}
											}
										}
									}
								}

								if(temptemp<temp){
									temp = temptemp;
									temp1 = temptemp1;
								}	

							}


							if(temp == 1440){
								feasible11 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								currenttrack = temp1;

								addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1){

									time21 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track21 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
									margin11 = this.getObjective(margin11, sequence[j]);


									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin11){
									margin11 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
								}
									 */
								}
								else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 7){
									time27 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track27 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();

									mintemp = time27 + activities.get(activities.size()-1-sequence[j]).getTotalDurationInteger();
									margin11 = this.getObjective(margin11, sequence[j]);

									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin11){
									margin11 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

								}
									 */
								}
							}
						}
					}
				}
				//System.out.println(margin + " " + bestmargin);

				if(feasible11 == false){
					margin11 = -1;
				}
				else if(margin11 >= bestmargin){
					time17 = time27;
					time11 = time21;
					time13 = time23;
					track17 = track27;
					track11 = track21;
					track13 = track23;
					bestmargin = margin11;
					bestoption = 11;
				}

			}
























			//ADDING OPTION 12!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!









			if(addedcomp.getInspection() && addedcomp.getRepairing()){
				//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
						activities.get(activities.size()-1-i).removeTimes();
						this.removeBusyTime(activities.get(activities.size()-1-i));
					}
				}
				//			if(addedcomp.getInspection()){
				//				currenttrack = activities.get(activities.size()-amount).getTrackAssigned();
				//			}
				//			else{
				currenttrack = null;
				//			}

				//reset the sequence array
				for (int i = 0; i<sequence.length; i++){
					sequence[i] = -1;
				}

				mintemp = addedcomp.getArrivalTimeInteger();


				//System.out.println(addedcomp.getTrain(0).getActivityTimeInteger(0) + " " + addedcomp.getTrain(0).getActivityTimeInteger(1) + " " + activities.get(activities.size()-1).getTotalDurationInteger());


				for (int j = 0; j<amount; j++){
					for(int i = 0; i<9; i++){
						if (activities.get(activities.size()-1-j).getActivity() == i){			
							if (i == 7){
								sequence[0] = j;
							}
							else if (i == 3){
								sequence[1] = j;
							}
							else if (i == 1){
								sequence[2] = j;
							}
							break;
						}
					}
				}

				//System.out.println(sequence[0] + " " + sequence[1] + " " + sequence[2]);

				//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
				for(int j = 0; j<sequence.length; j++){
					if (sequence[j]!=-1){
						activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
						temp = 1440;
						temptemp = 1440;
						temp1 = null;
						temptemp1 = null;
						if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

							for(int k = 0; k<washareas.size(); k++){
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = washareas.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}

							}
							if(temp == 1440){
								feasible12 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								currenttrack = temp1;
								time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin12 = this.getObjective(margin12, sequence[j]);

								/*
							if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin12){
								margin12 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}*/
							}
						}
						else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1 || activities.get(activities.size()-1-sequence[j]).getActivity() == 7){

							for(int k = 0; k<platforms.size(); k++){

								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = platforms.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}
							}

							for(int k = 0; k<platformsreserve.size(); k++){
								trackfree = true;
								index = 0;
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if (platforms.get(k).getBusy(l)){
										index = l;
										trackfree = false;
										break;
									}
								}

								if (trackfree == false){
									if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

										for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
											if(l >= mintemp){

												if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
																temptemp = l;

																temptemp1 = platformsreserve.get(k);
																break;
															}
											}
										}
									}
								}

								if(temptemp<temp){
									temp = temptemp;
									temp1 = temptemp1;
								}	

							}


							if(temp == 1440){
								feasible12 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								currenttrack = temp1;

								addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								if(activities.get(activities.size()-1-sequence[j]).getActivity() == 1){

									time21 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track21 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
									margin12 = this.getObjective(margin12, sequence[j]);


									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin12){
									margin12 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
								}*/
								}
								else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 7){
									time27 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track27 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();

									mintemp = time27 + activities.get(activities.size()-1-sequence[j]).getTotalDurationInteger();
									margin12 = this.getObjective(margin12, sequence[j]);

									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin12){
									margin12 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();

								}*/
								}
							}
						}
					}
				}
				//System.out.println(margin + " " + bestmargin);

				if(feasible12 == false){
					margin12 = -1;
				}
				else if(margin12 >= bestmargin){
					time17 = time27;
					time11 = time21;
					time13 = time23;
					track17 = track27;
					track11 = track21;
					track13 = track23;
					bestmargin = margin12;
					bestoption = 12;
				}

			}
























			//ADDING OPTION 13!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!









			if(addedcomp.getInspection() && addedcomp.getRepairing() && addedcomp.getCleaning()){
				//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
						activities.get(activities.size()-1-i).removeTimes();
						this.removeBusyTime(activities.get(activities.size()-1-i));
					}
				}
				//			if(addedcomp.getInspection()){
				//				currenttrack = activities.get(activities.size()-amount).getTrackAssigned();
				//			}
				//			else{
				currenttrack = null;
				//			}

				//reset the sequence array
				for (int i = 0; i<sequence.length; i++){
					sequence[i] = -1;
				}

				mintemp = addedcomp.getArrivalTimeInteger();


				// add the merged activity 5 which consists of activities 0 and 1. 
				activities.add(this.mergeActivities(addedcomp, 0, 1, 2, 8));
				amount += 1;

				//System.out.println(addedcomp.getTrain(0).getActivityTimeInteger(0) + " " + addedcomp.getTrain(0).getActivityTimeInteger(1) + " " + activities.get(activities.size()-1).getTotalDurationInteger());


				for (int j = 0; j<amount; j++){
					for(int i = 0; i<9; i++){
						if (activities.get(activities.size()-1-j).getActivity() == i){			
							if (i == 8){
								sequence[0] = j;
							}
							else if (i == 3){
								sequence[1] = j;
							}
							break;
						}
					}
				}

				//System.out.println(sequence[0] + " " + sequence[1] + " " + sequence[2]);

				//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
				for(int j = 0; j<sequence.length; j++){
					if (sequence[j]!=-1){
						activities.get(activities.size()-1-sequence[j]).setCurrentTrack(currenttrack);
						temp = 1440;
						temptemp = 1440;
						temp1 = null;
						temptemp1 = null;
						if(activities.get(activities.size()-1-sequence[j]).getActivity() == 3){

							for(int k = 0; k<washareas.size(); k++){
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = washareas.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}

							}
							if(temp == 1440){
								feasible13 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								currenttrack = temp1;
								time23 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
								track23 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();
								margin13 = this.getObjective(margin13, sequence[j]);

								/*
							if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin13){
								margin13 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
							}*/
							}
						}
						else if(activities.get(activities.size()-1-sequence[j]).getActivity() == 8){

							for(int k = 0; k<platforms.size(); k++){

								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
										if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[j]), l)){
											if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[j]),l)){

												temptemp = l;
												temptemp1 = platforms.get(k);
												break;
											}
										}
									}
								}
								//Update if we find better solution than the previous ones at a different track.
								if(temptemp <= temp){
									temp = temptemp;
									temp1 = temptemp1;
								}
							}

							for(int k = 0; k<platformsreserve.size(); k++){
								trackfree = true;
								index = 0;
								for(int l = mintemp; l<activities.get(activities.size()-1-sequence[j]).getUltimateTimeInteger(); l++){
									if (platforms.get(k).getBusy(l)){
										index = l;
										trackfree = false;
										break;
									}
								}

								if (trackfree == false){
									if (addedcomp.getLength() <= (platforms.get(k).getTracklength()-platforms.get(k).getActivity(index).getComposition().getLength())){

										for(int l = platforms.get(k).getActivity(index).getPlannedTimeInteger(); l<(platforms.get(k).getActivity(index).getPlannedTimeInteger()+platforms.get(k).getActivity(index).getTotalDurationInteger()); l++){
											if(l >= mintemp){

															if(getFeasibleReserve(l, k, sequence[j], addedcomp)){
																temptemp = l;

																temptemp1 = platformsreserve.get(k);
																break;
															}
											}
										}
									}
								}

								if(temptemp<temp){
									temp = temptemp;
									temp1 = temptemp1;
								}	

							}


							if(temp == 1440){
								feasible13 = false;
							}
							else{
								activities.get(activities.size()-1-sequence[j]).setUpdate(temp, temp1);
								currenttrack = temp1;

								addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								temp1.setBusyTime(activities.get(activities.size()-1-sequence[j])); 
								this.setBusyTime(activities.get(activities.size()-1-sequence[j]));
								if(activities.get(activities.size()-1-sequence[j]).getActivity() == 8){

									time28 = activities.get(activities.size()-1-sequence[j]).getPlannedTimeInteger();
									track28 = activities.get(activities.size()-1-sequence[j]).getTrackAssigned();

									mintemp = time28 + activities.get(activities.size()-1-sequence[j]).getTotalDurationInteger();
									margin13 = this.getObjective(margin13, sequence[j]);


									/*
								if(activities.get(activities.size()-1-sequence[j]).getMarginInteger()<margin13){
									margin13 = activities.get(activities.size()-1-sequence[j]).getMarginInteger();
								}*/
								}
							}
						}
					}
				}
				//System.out.println(margin + " " + bestmargin);

				if(feasible13 == false){
					margin13 = -1;
				}
				else if(margin13 >= bestmargin){
					time18 = time28;
					time13 = time23;
					track18 = track28;
					track13 = track23;
					bestmargin = margin13;
					bestoption = 13;
				}

			}




		}




		//		System.out.println("Bestoption" + bestoption);
		//		System.out.println(time10 + " " + track10 + " " + amount + " " + activities.get(activities.size()-1-0).getActivity());
		//		System.out.println(time11 + " " + track11);
		//		System.out.println(time12 + " " + track12);
		//		System.out.println(time13 + " " + track13);


		//		//check if feasible solution exist, if so, then update with the best solution
		if (bestmargin != -1){
			for(int i = 0; i<amount; i++){
				if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){

					activities.get(activities.size()-1-i).removeTimes();
					this.removeBusyTime(activities.get(activities.size()-1-i));
				}
			}

			if (bestoption == 7 || bestoption == 8){
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getActivity()==5){
						activities.get(activities.size()-1-i).setUpdate(time15, track15);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
					if(activities.get(activities.size()-1-i).getActivity()==2){
						activities.get(activities.size()-1-i).setUpdate(time12, track12);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
					if(activities.get(activities.size()-1-i).getActivity()==3){
						activities.get(activities.size()-1-i).setUpdate(time13, track13);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
				}
			}
			else if(bestoption == 9 || bestoption == 10){
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getActivity()==6){
						activities.get(activities.size()-1-i).setUpdate(time16, track16);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
					if(activities.get(activities.size()-1-i).getActivity()==0){
						activities.get(activities.size()-1-i).setUpdate(time10, track10);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
					if(activities.get(activities.size()-1-i).getActivity()==3){
						activities.get(activities.size()-1-i).setUpdate(time13, track13);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
				}

			}
			else if(bestoption == 11 || bestoption == 12){
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getActivity()==7){
						activities.get(activities.size()-1-i).setUpdate(time17, track17);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
					if(activities.get(activities.size()-1-i).getActivity()==1){
						activities.get(activities.size()-1-i).setUpdate(time11, track11);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
					if(activities.get(activities.size()-1-i).getActivity()==3){
						activities.get(activities.size()-1-i).setUpdate(time13, track13);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
				}
			}
			else if(bestoption == 13){
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getActivity()==8){
						activities.get(activities.size()-1-i).setUpdate(time18, track18);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
					if(activities.get(activities.size()-1-i).getActivity()==3){
						activities.get(activities.size()-1-i).setUpdate(time13, track13);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
				}
			}
			else{
				for(int i = 0; i<amount; i++){
					if(activities.get(activities.size()-1-i).getActivity()==0){
						//System.out.println(time10 + " " + track10 + " " + bestoption);
						activities.get(activities.size()-1-i).setUpdate(time10, track10);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
					else if(activities.get(activities.size()-1-i).getActivity()==1){
						activities.get(activities.size()-1-i).setUpdate(time11, track11);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
					else if(activities.get(activities.size()-1-i).getActivity()==2){
						activities.get(activities.size()-1-i).setUpdate(time12, track12);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
					else if(activities.get(activities.size()-1-i).getActivity()==3){
						activities.get(activities.size()-1-i).setUpdate(time13, track13);
						this.setBusyTime(activities.get(activities.size()-1-i));
					}
				}
			}
		}
		else{
			feasible = false;
		}




		//If the first solution is better, we use the first solution. We choose the first solution because it is more likely that it will not need to be moved to another track (saving time)		
		//		if(feasible1 && feasible2){
		//			if(margin1 >= margin2){
		//				for(int i = 0; i<amount; i++){
		//					this.removeBusyTime(activities.get(activities.size()-1-i));
		//				}
		//				for(int i = 0; i<amount; i++){
		//					if(activities.get(activities.size()-1-i).getActivity()==1){
		//						activities.get(activities.size()-1-i).setUpdate(time11, track11);
		//						this.setBusyTime(activities.get(activities.size()-1-i));
		//					}
		//					if(activities.get(activities.size()-1-i).getActivity()==2){
		//						activities.get(activities.size()-1-i).setUpdate(time12, track12);
		//						this.setBusyTime(activities.get(activities.size()-1-i));
		//					}
		//					if(activities.get(activities.size()-1-i).getActivity()==3){
		//						activities.get(activities.size()-1-i).setUpdate(time13, track13);
		//						this.setBusyTime(activities.get(activities.size()-1-i));
		//					}
		//				}
		//
		//			}
		//		}
		//		else if(feasible1 && ! feasible2){
		//			for(int i = 0; i<amount; i++){
		//				this.removeBusyTime(activities.get(activities.size()-1-i));
		//			}
		//			for(int i = 0; i<amount; i++){
		//				if(activities.get(activities.size()-1-i).getActivity()==1){
		//					activities.get(activities.size()-1-i).setUpdate(time11, track11);
		//					this.setBusyTime(activities.get(activities.size()-1-i));
		//				}
		//				if(activities.get(activities.size()-1-i).getActivity()==2){
		//					activities.get(activities.size()-1-i).setUpdate(time12, track12);
		//					this.setBusyTime(activities.get(activities.size()-1-i));
		//				}
		//				if(activities.get(activities.size()-1-i).getActivity()==3){
		//					activities.get(activities.size()-1-i).setUpdate(time13, track13);
		//					this.setBusyTime(activities.get(activities.size()-1-i));
		//				}
		//			}
		//		}
		//		else if(!feasible1 && feasible2){
		//
		//		}
		//		else{
		//			throw new IOException("No feasible solution found for job-shop");
		//		}

//		System.out.print("Composition ");
//		addedcomp.printTimeLine();
//		System.out.print("\n");
	}

	public boolean getConsecutive(Activity activity1, Activity activity2){		
		if(activity1!=null && activity2!=null && !activity1.equals(activity2) && activity1.getTrackAssigned().equals(activity2.getTrackAssigned()) && movelist[activity2.getPlannedTimeInteger()] == activity2 && movelist[activity2.getPlannedTimeInteger()+activity2.getTotalDurationInteger()-Main.moveduration*2] == null && movelist[activity2.getPlannedTimeInteger()+activity2.getTotalDurationInteger()-Main.moveduration*2-1] == null){
			return true;
		}
		else{
			return false;
		}
	}
	public void improveActivities(Activity activity1, Activity activity2) throws IOException{

		this.removeBusyTimeMoveLeave(activity1);
		this.removeBusyTimeMoveArrive(activity2);



		//		activity2.removeTimes();
		//		this.removeBusyTime(activity2);
		//		activity1.getTrackAssigned().removeBusyTimeMove(activity1);
		//		activity1.getComposition().removeBusyTimeMove(activity1);
		//		this.removeBusyTimeMove(activity1);
		//		activity2.setUpdate(activity1.getPlannedTimeInteger()+activity1.getTotalDurationInteger()-Main.moveduration, activity1.getTrackAssigned());
		//		this.setBusyTime(activity2);
		//		activity2.getTrackAssigned().removeBusyTimeMove(activity2);
		//		activity2.getComposition().removeBusyTimeMove(activity2);
		//		this.removeBusyTimeMove(activity2);
	}

	public Activity mergeActivities(FinalBlock comp1, int act1, int act2, int actnr) throws IOException{
		int acttimepertrain = 0;
		int maxduration = -1;
		for (int i=0; i<comp1.getSize(); i++){
			acttimepertrain = 0;
			if (comp1.getTrain(i).getActivity(act1)){
				acttimepertrain += comp1.getTrain(i).getActivityTimeInteger(act1);
			}
			if (comp1.getTrain(i).getActivity(act2)){
				acttimepertrain += comp1.getTrain(i).getActivityTimeInteger(act2);
			}
			if (acttimepertrain>maxduration){
				maxduration = acttimepertrain;
			}
		}
		Activity merged = new Activity(maxduration, (int) comp1.getDepartureTimeInteger()-(maxduration),comp1, actnr);
		return merged;
	}

	public Activity mergeActivities(FinalBlock comp1, int act1, int act2, int act3, int actnr) throws IOException{
		int acttimepertrain = 0;
		int maxduration = -1;
		for (int i=0; i<comp1.getSize(); i++){
			acttimepertrain = 0;
			if (comp1.getTrain(i).getActivity(act1)){
				acttimepertrain += comp1.getTrain(i).getActivityTimeInteger(act1);
			}
			if (comp1.getTrain(i).getActivity(act2)){
				acttimepertrain += comp1.getTrain(i).getActivityTimeInteger(act2);
			}
			if (comp1.getTrain(i).getActivity(act3)){
				acttimepertrain += comp1.getTrain(i).getActivityTimeInteger(act3);
			}
			if (acttimepertrain>maxduration){
				maxduration = acttimepertrain;
			}
		}
		Activity merged = new Activity(maxduration, (int) comp1.getDepartureTimeInteger()-(maxduration),comp1, actnr);
		return merged;
	}

	public int getObjective(int margin, int a){
		int abc = 0;
		if(option == 1 || option == 3 || option == 5 || option == 7 || option == 9 || option == 11){
			if(activities.get(activities.size()-1-a).getMarginInteger()<margin){
				abc = activities.get(activities.size()-1-a).getMarginInteger();
			}
		}
		else if(option == 2 || option == 4 || option == 6 || option == 8 || option == 10 || option == 12){
			int temp = 0;
			for(int i = 0; i<activities.size(); i++){
				for(int j = i+1; j<activities.size(); j++){
					//					System.out.println(i + " " + j + " " + activities.size());
					//					System.out.println(activities.get(i).getTrackAssigned() + " " + activities.get(j).getTrackAssigned());
					if(activities.get(i).getTrackAssigned() != null && activities.get(j).getTrackAssigned() !=null){
						if(activities.get(i).getTrackAssigned().equals(activities.get(j).getTrackAssigned())){
							if(activities.get(i).getPlannedTimeInteger() < activities.get(j).getPlannedTimeInteger()){
								if(activities.get(j).getPlannedTimeInteger() - (activities.get(i).getPlannedTimeInteger()+activities.get(i).getTotalDurationInteger()-1) < 11){
									temp += activities.get(j).getPlannedTimeInteger() - (activities.get(i).getPlannedTimeInteger()+activities.get(i).getTotalDurationInteger()-1);
								}
							}
						}
					}
				}
			}
			if(temp > -margin){
				abc = -temp;
			}
		}
		return abc;
	}
	/*
	public boolean getObjective(int l, int k, int b){
		boolean abc = false;
		if(option == 1 || option == 3 || option == 5 || option == 7){
			if(){
				abc = true;
			}
		}
		else if(option == 2 || option == 4 || option == 6 || option == 8){
			int temp = 0;
			for(int i = 0; i<activities.size(); i++){
				for(int j = i+1; j<activities.size(); j++){
					if(activities.get(i).getTrackAssigned().equals(activities.get(j).getTrackAssigned())){
						if(activities.get(i).getPlannedTimeInteger() < activities.get(j).getPlannedTimeInteger()){
							if(activities.get(j).getPlannedTimeInteger() - (activities.get(i).getPlannedTimeInteger()+activities.get(i).getTotalDurationInteger()-1) < 11){
								temp += activities.get(j).getPlannedTimeInteger() - (activities.get(i).getPlannedTimeInteger()+activities.get(i).getTotalDurationInteger()-1);
							}
						}
					}
				}
			}
			if(temp < compare){
				abc = true;
				compare = temp;
			}
		}
		return abc;
	}
	 */

	public void setBusyTime(Activity activity){
		for(int i = activity.getPlannedTimeInteger(); i<activity.getPlannedTimeInteger()+activity.getMoveTime(); i++){
			movelist[i] = activity;
		}
		for(int i = activity.getPlannedTimeInteger() + activity.getDurationInteger() + activity.getMoveTime(); i<activity.getPlannedTimeInteger() + activity.getDurationInteger() + activity.getMoveTime() + activity.getMoveTime2(); i++){
			movelist[i] = activity;
		}
	}

	public void setBusyTimeMove(Activity activity) throws IOException {
		for(int i = activity.getPlannedTimeInteger(); i<activity.getPlannedTimeInteger()+activity.getMoveTime(); i++){
			if(movelist[i] != null){
				throw new IOException("It is impossible to have 2 trains arriving or leaving at the same time!");
			}
			else{
				movelist[i] = activity;
			}
		}

	}
	public void removeBusyTime(Activity activity){
		for(int i = 0; i<movelist.length; i++){
			if(movelist[i] != null && movelist[i].equals(activity)){
				movelist[i] = null; 
			}
		}
	}
	public boolean checkFeasibilityMove(Activity activity, int timetobechecked){

		boolean feasible = true;

		for(int i = timetobechecked; i<timetobechecked+activity.getMoveTime(); i++){
			if(movelist[i]!=null){
				feasible = false;
				break;
			}
		}
		for(int i = timetobechecked + activity.getDurationInteger() + activity.getMoveTime(); i<timetobechecked + activity.getDurationInteger() + activity.getMoveTime2() + activity.getMoveTime(); i++){
			if(movelist[i]!=null){
				feasible = false;
				break;
			}
		}
		return feasible;
	}

	public void removeBusyTimeMoveLeave(Activity activity){
		for(int i = activity.getPlannedTimeInteger()+activity.getTotalDurationInteger()-Main.moveduration; i<activity.getPlannedTimeInteger()+activity.getTotalDurationInteger(); i++){			
			movelist[i] = null;
		}
	}
	public void removeBusyTimeMoveArrive(Activity activity){
		for(int i = activity.getPlannedTimeInteger(); i<activity.getPlannedTimeInteger()+Main.moveduration; i++){
			movelist[i] = null;
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

	public boolean checkFeasibility(){
		boolean abcd = true;
		for(int i = 0; i<activities.size(); i++){
			if(activities.get(i).getPlannedTime()>activities.get(i).getUltimateTime()){
				abcd = false;
			}	
		}
		return abcd;
	}
	public void printTimeLine(){
		for(int i = 0; i<60*24; i++){
			if(movelist[i]!=null){
				System.out.print(movelist[i].getActivity());
			}
			else{
				System.out.print(" ");
			}
		}
	}

	/*
	 * TODO: IF DIFFERENT DATA, EDIT 0 AND 1 FOR SIDESTART EN SIDEEND
	 */
	public ArrayList<Event> getEvents(){
		ArrayList<Event> abcd = new ArrayList<>();
		for(int i = 0; i<finalblockssshallow.size(); i++){
			boolean first = true;
			finalblockssshallow.get(i).getOrigincomposition().getArrivalTimeInteger();
			for(int j = 0; j<60*24; j++){
				if(finalblockssshallow.get(i).getActivity(j) != null && finalblockssshallow.get(i).getActivity(j) == movelist[j]){
					if(first){
						if(finalblockssshallow.get(i).getActivity(j).getActivity()==4){
							abcd.add(new Event(finalblockssshallow.get(i), 0, 1, j+Main.moveduration, -1, finalblockssshallow.get(i).getArrivalSide(), -1, null));
							first = false;
							j += Main.moveduration-1;
						}
						else{
							abcd.add(new Event(finalblockssshallow.get(i), 0, 0, j+Main.moveduration, -1, 0, -1, null));
							first = false;
							j += Main.moveduration-1;
						}
					}
					else{
						if(finalblockssshallow.get(i).getActivity(j).getActivity()==4){
							abcd.add(new Event(finalblockssshallow.get(i), 1, 1, abcd.get(abcd.size()-1).getStarttime(), j, abcd.get(abcd.size()-1).getSidestart(), finalblockssshallow.get(i).getDepartureSide(), abcd.get(abcd.size()-1)));
							abcd.get(abcd.size()-2).setRelatedEvent(abcd.get(abcd.size()-1));
							abcd.get(abcd.size()-2).setEndTime(j);
							abcd.get(abcd.size()-2).setSideEnd(finalblockssshallow.get(i).getDepartureSide());
							first = true;
							j += Main.moveduration-1;
						}
						else{
							abcd.add(new Event(finalblockssshallow.get(i), 1, 0, abcd.get(abcd.size()-1).getStarttime(), j, abcd.get(abcd.size()-1).getSidestart(), 0, abcd.get(abcd.size()-1)));
							abcd.get(abcd.size()-2).setRelatedEvent(abcd.get(abcd.size()-1));
							abcd.get(abcd.size()-2).setEndTime(j);
							abcd.get(abcd.size()-2).setSideEnd(0);
							first = true;
							j += Main.moveduration-1;
						}

					}
				}


			}
		}


		return abcd;
	}
	public boolean getFeasibleReserve(int l, int k, int a, FinalBlock addedcomp){
		//System.out.println(l + (activities.get(activities.size()-1)).getTotalDurationInteger() + " " + (platforms.get(k).getActivity(l).getPlannedTimeInteger() + platforms.get(k).getActivity(l).getTotalDurationInteger() - Main.moveduration));
		boolean abc = false;
		if(platformsreserve.get(k).checkFeasibility(activities.get(activities.size()-1-a), l)){
			if(addedcomp.checkFeasibility(activities.get(activities.size()-1-a), l)){
				if(this.checkFeasibilityMove(activities.get(activities.size()-1-a),l)){
					if((l+(activities.get(activities.size()-1-a)).getTotalDurationInteger()) < (platforms.get(k).getActivity(l).getPlannedTimeInteger()+platforms.get(k).getActivity(l).getTotalDurationInteger())-Main.moveduration){
						abc = true;
					}
				}
			}
		}
		return abc;
	}
	public boolean getFeasible(){
		return feasible; 
	}
}