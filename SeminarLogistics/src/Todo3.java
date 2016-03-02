import java.io.IOException; 
import java.util.ArrayList;

public class Todo3 {

	private ArrayList<Activity> activities;

	ArrayList<Composition> arrivingcompositions;
	ArrayList<Composition> departurecompostions;
	ArrayList<FinalBlock> finalblockss;
	ArrayList<FinalBlock> finalblockssshallow;
	Integer[] sequence = new Integer[3];

	//An activity representing any incoming/outgoing composition movement
	Activity arrordepmove = new Activity(-1, -1, null, 4, true);

	ArrayList<Track> platforms = new ArrayList<>();
	ArrayList<Track> washareas = new ArrayList<>();
	Activity[] movelist = new Activity[60*24];

	public Todo3(Track[] tracks, ArrayList<Composition> arrivingcompositions, ArrayList<Composition> departurecompositions, ArrayList<FinalBlock> finalblocks) throws IOException{
		activities = new ArrayList<>();
		finalblockss = new ArrayList<>();
		finalblockssshallow = new ArrayList<>();

		for(int i=0;i<tracks.length;i++){
			if (tracks[i].getInspectionposition() ==1){
				platforms.add(tracks[i]);
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
		int k;
		int a = this.finalblockss.size();
		for(int i = 0; i<a; i++){
			temp = 14124;
			k = -1;
			for(int j = 0; j<this.finalblockss.size(); j++){
				if(this.finalblockss.get(j).getShuntTime()<temp){
					temp = this.finalblockss.get(j).getShuntTime();
					k = j;
				}
			}
			this.addComposition(this.finalblockss.get(k));
			finalblockssshallow.add(this.finalblockss.get(k));
			this.finalblockss.remove(k);
		}

		for(int i = 0; i<platforms.size(); i++){
			System.out.print("Platform " + i + "  ");
			platforms.get(i).printTimeLine();
			System.out.print("\n");
		}
		for(int i = 0; i<washareas.size(); i++){
			System.out.print("Washarea " + i + "  ");
			washareas.get(i).printTimeLine();
			System.out.print("\n");
		}
		System.out.print("Movelist    " );
		this.printTimeLine();
		System.out.print("\n");
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
		int mintemp = addedcomp.getArrivalTimeInteger(); //TODO: AFRONDEN GOED?
		Track temp1;
		Track temptemp1;
		int margin1 = 123456;
		int bestmargin = 123456;
		int time10 = -1;
		int time11 = -1;
		int time12 = -1;
		int time13 = -1;
		int time20 = -1;
		int time21 = -1;
		int time22 = -1;
		int time23 = -1;
		Track track10 = null;
		Track track11 = null;
		Track track12 = null;
		Track track13 = null;
		Track track20 = null;
		Track track21 = null;
		Track track22 = null;
		Track track23 = null;
		int margin2 = 123456;
		int margin3 = 123456;
		int margin4 = 123456;
		int margin5 = 123456;
		int margin6 = 123456;
		int margin7 = 123456;
		int margin8 = 123456;
		int margin9 = 123456;
		int margin10 = 123456;
		int margin11 = 123456;
		int margin12 = 123456;
		int margin13 = 123456;
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
		boolean feasible11= true;
		boolean feasible12= true;
		boolean feasible13= true;


		//reset the sequence array
		for (int i = 0; i<sequence.length; i++){
			sequence[i] = null;
		}

		//It checks for all possible activities
		for(int j = 0; j<4; j++){
			temp = 12412;
			temptemp = 12412;
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
						if (platforms.get(k).equals(currenttrack)){

							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
						else {
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}

					}

					if(temp == 12412){
						feasible1 = false;
					}
					else{
						activities.get(activities.size()-1).setUpdate(temp, temp1);

						addedcomp.setBusyTime(activities.get(activities.size()-1));

						temp1.setBusyTime(activities.get(activities.size()-1)); 
						this.setBusyTime(activities.get(activities.size()-1));
						//Minimum time to loop from must be update since inspection cannot be moved later
						if(j == 0){	
							time10 = activities.get(activities.size()-1).getPlannedTimeInteger();
							track10 = activities.get(activities.size()-1).getTrackAssigned();
							mintemp += durationactivity;
							if(activities.get(activities.size()-1).getMarginInteger()<margin1){
								margin1 = activities.get(activities.size()-1).getMarginInteger();
							}
						}
						//Storing the first solution and keeping track of how many activities are done on the composition, except for inspection though
						else if(j == 1){
							time11 = activities.get(activities.size()-1).getPlannedTimeInteger();
							track11 = activities.get(activities.size()-1).getTrackAssigned();
							//System.out.print(time11 + " " + track11 + " 1 \n" );

							if(activities.get(activities.size()-1).getMarginInteger()<margin1){
								margin1 = activities.get(activities.size()-1).getMarginInteger();
							}
							amount += 1;
						}

						//Storing the first solution ............. see above.
						else if(j == 2){
							time12 = activities.get(activities.size()-1).getPlannedTimeInteger();
							track12 = activities.get(activities.size()-1).getTrackAssigned();
							if(activities.get(activities.size()-1).getMarginInteger()<margin1){
								margin1 = activities.get(activities.size()-1).getMarginInteger();
							}
							amount += 1;
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
						if (washareas.get(k).equals(currenttrack)){

							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
						else {
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
					}
					if(temp == 12412){
						feasible1 = false;
					}
					else{
						//System.out.print(temp + " " + temp1);
						activities.get(activities.size()-1).setUpdate(temp, temp1);
						currenttrack = temp1;

						//Storing first solution
						addedcomp.setBusyTime(activities.get(activities.size()-1));
						temp1.setBusyTime(activities.get(activities.size()-1)); 
						this.setBusyTime(activities.get(activities.size()-1));

						time13 = activities.get(activities.size()-1).getPlannedTimeInteger();
						track13 = activities.get(activities.size()-1).getTrackAssigned();

						if(activities.get(activities.size()-1).getMarginInteger()<margin1){
							margin1 = activities.get(activities.size()-1).getMarginInteger();
						}

					}
				}
			}

		}	

		if (feasible1==false){
			margin1 = -1;
		}
		
			bestmargin = margin1;
		
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
		//Same system as above, yet the order of activities is different! We look now backwards from 3 to 2 to 1. 0 remains untouched
		for(int j = 0; j<amount; j++){
			activities.get(activities.size()-1-j).setCurrentTrack(currenttrack);
			temp = 12412;
			temptemp = 12412;
			temp1 = null;
			temptemp1 = null;
			if(activities.get(activities.size()-1-j).getActivity() == 3){

				for(int k = 0; k<washareas.size(); k++){
					for(int l = mintemp; l<activities.get(activities.size()-1-j).getUltimateTimeInteger(); l++){
						if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-j), l)){
							if(addedcomp.checkFeasibility(activities.get(activities.size()-1-j), l)){
								if(this.checkFeasibilityMove(activities.get(activities.size()-1),l)){

									temptemp = l;
									temptemp1 = washareas.get(k);
									break;
								}
							}
						}
					}
					//Update if we find better solution than the previous ones at a different track.
					if (washareas.get(k).equals(currenttrack)){

						if(temptemp <= temp){
							temp = temptemp;
							temp1 = temptemp1;
						}
					}
					else {
						if(temptemp < temp){
							temp = temptemp;
							temp1 = temptemp1;
						}
					}
				}
				if(temp == 12412){
					feasible2 = false;
				}

				else {
					activities.get(activities.size()-1-j).setUpdate(temp, temp1);
					this.setBusyTime(activities.get(activities.size()-1-j));
					currenttrack = temp1;
					time23 = activities.get(activities.size()-1-j).getPlannedTimeInteger();
					track23 = activities.get(activities.size()-1-j).getTrackAssigned();

					//addedcomp.setBusyTime(activities.get(activities.size()-1-j));
					//temp1.setBusyTime(activities.get(activities.size()-1-j)); 


					if(activities.get(activities.size()-1-j).getMarginInteger()<margin2){
						margin2 = activities.get(activities.size()-1-j).getMarginInteger();
					}
				}
			}
			else if(activities.get(activities.size()-1-j).getActivity() == 1 || activities.get(activities.size()-1-j).getActivity() == 2){

				for(int k = 0; k<platforms.size(); k++){

					for(int l = mintemp; l<activities.get(activities.size()-1-j).getUltimateTimeInteger(); l++){
						if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-j), l)){
							if(addedcomp.checkFeasibility(activities.get(activities.size()-1-j), l)){
								if(this.checkFeasibilityMove(activities.get(activities.size()-1-j),l)){

									temptemp = l;
									temptemp1 = platforms.get(k);
									break;
								}
							}
						}
					}
					//Update if we find better solution than the previous ones at a different track.
					if (platforms.get(k).equals(currenttrack)){

						if(temptemp <= temp){
							temp = temptemp;
							temp1 = temptemp1;
						}
					}
					else {
						if(temptemp < temp){
							temp = temptemp;
							temp1 = temptemp1;
						}
					}
				}

				if(temp == 12412){
					feasible2 = false;
				}

				else{
					activities.get(activities.size()-1-j).setUpdate(temp, temp1);
					currenttrack = temp1;

					addedcomp.setBusyTime(activities.get(activities.size()-1-j));
					temp1.setBusyTime(activities.get(activities.size()-1-j)); 
					this.setBusyTime(activities.get(activities.size()-1-j));
					if(activities.get(activities.size()-1-j).getActivity() == 1){
						time21 = activities.get(activities.size()-1-j).getPlannedTimeInteger();
						track21 = activities.get(activities.size()-1-j).getTrackAssigned();
						if(activities.get(activities.size()-1-j).getMarginInteger()<margin2){
							margin2 = activities.get(activities.size()-1-j).getMarginInteger();
						}
					}
					else if(activities.get(activities.size()-1-j).getActivity() == 2){
						time22 = activities.get(activities.size()-1-j).getPlannedTimeInteger();
						track22 = activities.get(activities.size()-1-j).getTrackAssigned();
						if(activities.get(activities.size()-1-j).getMarginInteger()<margin2){
							margin2 = activities.get(activities.size()-1-j).getMarginInteger();

						}
					}
				}
			}
		}

		if (feasible2 == false){
			margin2 = -1;
		}

		if (margin2 > bestmargin){
			time11 = time21;
			time12 = time22;
			time13 = time23;
			track11 = track21;
			System.out.print(time11 + " " + track11 + " 2 \n");

			track12 = track22;
			track13 = track23;
			bestmargin = margin2;
		}

		//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
		for(int i = 0; i<amount; i++){
			if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
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

		for (int i = 0; i<sequence.length; i++){
			if (sequence[i]!=null){
				activities.get(activities.size()-1-sequence[i]).setCurrentTrack(currenttrack);
				temp = 12412;
				temptemp = 12412;
				temp1 = null;
				temptemp1 = null;
				if (activities.get(activities.size()-1-sequence[i]).getActivity()==1 || activities.get(activities.size()-1-sequence[i]).getActivity()==2){
					for(int k = 0; k<platforms.size(); k++){

						for(int l = mintemp; l<activities.get(activities.size()-1-sequence[i]).getUltimateTimeInteger(); l++){
							if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
									if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[i]),l)){

										temptemp = l;
										temptemp1 = platforms.get(k);
										break;
									}
								}
							}
						}
						//Update if we find better solution than the previous ones at a different track.
						if (platforms.get(k).equals(currenttrack)){

							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
						else {
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
					}

					if(temp == 12412){
						feasible3 = false;
					}

					else{
						activities.get(activities.size()-1-sequence[i]).setUpdate(temp, temp1);
						currenttrack = temp1;

						addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						temp1.setBusyTime(activities.get(activities.size()-1-sequence[i])); 
						this.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						if(activities.get(activities.size()-1-sequence[i]).getActivity() == 1){
							time21 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
							track21 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
							if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin3){
								margin3 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();	
							}
						}
						else if(activities.get(activities.size()-1-sequence[i]).getActivity()== 2){
							time22 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
							track22 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
							if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin3){
								margin3 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();
							}
						}
					}

				}

				else if(activities.get(activities.size()-1-sequence[i]).getActivity() == 3){

					for(int k = 0; k<washareas.size(); k++){
						for(int l = mintemp; l<activities.get(activities.size()-1-sequence[i]).getUltimateTimeInteger(); l++){
							if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
									if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[i]),l)){

										temptemp = l;
										temptemp1 = washareas.get(k);
										break;
									}
								}
							}
						}
						//Update if we find better solution than the previous ones at a different track.
						if (washareas.get(k).equals(currenttrack)){

							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
						else {
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}

					}
					if(temp == 12412){
						feasible3 = false;
					}

					else{
						activities.get(activities.size()-1-sequence[i]).setUpdate(temp, temp1);
						this.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						currenttrack = temp1;

						//addedcomp.setBusyTime(activities.get(activities.size()-1-j));
						//temp1.setBusyTime(activities.get(activities.size()-1-j)); 

						time23 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
						track23 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
						if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin3){
							margin3 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();
						}
					}
				}
			}
		}

		if (feasible3 == false){
			margin3 = -1;
		}

		if (margin3 > bestmargin){
			time11 = time21;
			time12 = time22;
			time13 = time23;
			track11 = track21;
			System.out.print(time11 + " " + track11 + "3\n");

			track12 = track22;
			track13 = track23;
			bestmargin = margin3;
		}

		//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
		for(int i = 0; i<amount; i++){
			if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
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
			sequence[i] = null;
		}

		//determine the right sequence of how the activities should be planned, in this case: 2, 1, 3.
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

		for (int i = 0; i<sequence.length; i++){
			if (sequence[i]!=null){
				activities.get(activities.size()-1-sequence[i]).setCurrentTrack(currenttrack);
				temp = 12412;
				temptemp = 12412;
				temp1 = null;
				temptemp1 = null;
				if (activities.get(activities.size()-1-sequence[i]).getActivity()==1 || activities.get(activities.size()-1-sequence[i]).getActivity()==2){
					for(int k = 0; k<platforms.size(); k++){

						for(int l = mintemp; l<activities.get(activities.size()-1-sequence[i]).getUltimateTimeInteger(); l++){
							if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
									if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[i]),l)){

										temptemp = l;
										temptemp1 = platforms.get(k);
										break;
									}
								}
							}
						}
						//Update if we find better solution than the previous ones at a different track.
						if (platforms.get(k).equals(currenttrack)){

							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
						else {
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
					}

					if(temp == 12412){
						feasible4 = false;
					}

					else{
						activities.get(activities.size()-1-sequence[i]).setUpdate(temp, temp1);
						currenttrack = temp1;

						addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						temp1.setBusyTime(activities.get(activities.size()-1-sequence[i])); 
						this.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						if(activities.get(activities.size()-1-sequence[i]).getActivity() == 1){
							time21 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
							track21 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
							if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin4){
								margin4 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();	
							}
						}
						else if(activities.get(activities.size()-1-sequence[i]).getActivity()== 2){
							time22 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
							track22 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
							if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin4){
								margin4 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();
							}
						}
					}

				}

				else if(activities.get(activities.size()-1-sequence[i]).getActivity() == 3){

					for(int k = 0; k<washareas.size(); k++){
						for(int l = mintemp; l<activities.get(activities.size()-1-sequence[i]).getUltimateTimeInteger(); l++){
							if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
									if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[i]),l)){

										temptemp = l;
										temptemp1 = washareas.get(k);
										break;
									}
								}
							}
						}
						//Update if we find better solution than the previous ones at a different track.
						if (washareas.get(k).equals(currenttrack)){

							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
						else {
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}

					}
					if(temp == 12412){
						feasible4 = false;
					}

					else{
						activities.get(activities.size()-1-sequence[i]).setUpdate(temp, temp1);
						this.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						currenttrack = temp1;

						//addedcomp.setBusyTime(activities.get(activities.size()-1-j));
						//temp1.setBusyTime(activities.get(activities.size()-1-j)); 

						time23 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
						track23 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
						if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin4){
							margin4 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();
						}
					}
				}
			}
		}

		if (feasible4 == false){
			margin4 = -1;
		}

		if (margin4 > bestmargin){
			time11 = time21;
			time12 = time22;
			time13 = time23;
			track11 = track21;
			System.out.print(time11 + " " + track11 + "4 \n" );

			track12 = track22;
			track13 = track23;
			bestmargin = margin4;
		}

		//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
		for(int i = 0; i<amount; i++){
			if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
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
			sequence[i] = null;
		}

		//OPTION 5: determine the right sequence of how the activities should be planned, in this case: 2, 3, 1.
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

		for (int i = 0; i<sequence.length; i++){
			if (sequence[i]!=null){
				activities.get(activities.size()-1-sequence[i]).setCurrentTrack(currenttrack);
				temp = 12412;
				temptemp = 12412;
				temp1 = null;
				temptemp1 = null;
				if (activities.get(activities.size()-1-sequence[i]).getActivity()==1 || activities.get(activities.size()-1-sequence[i]).getActivity()==2){
					for(int k = 0; k<platforms.size(); k++){

						for(int l = mintemp; l<activities.get(activities.size()-1-sequence[i]).getUltimateTimeInteger(); l++){
							if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
									if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[i]),l)){

										temptemp = l;
										temptemp1 = platforms.get(k);
										break;
									}
								}
							}
						}
						//Update if we find better solution than the previous ones at a different track.
						if (platforms.get(k).equals(currenttrack)){

							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
						else {
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
					}

					if(temp == 12412){
						feasible5 = false;
					}

					else{
						activities.get(activities.size()-1-sequence[i]).setUpdate(temp, temp1);
						currenttrack = temp1;

						addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						temp1.setBusyTime(activities.get(activities.size()-1-sequence[i])); 
						this.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						if(activities.get(activities.size()-1-sequence[i]).getActivity() == 1){
							time21 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
							track21 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
							if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin5){
								margin5 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();	
							}
						}
						else if(activities.get(activities.size()-1-sequence[i]).getActivity()== 2){
							time22 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
							track22 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
							if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin5){
								margin5 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();
							}
						}
					}

				}

				else if(activities.get(activities.size()-1-sequence[i]).getActivity() == 3){

					for(int k = 0; k<washareas.size(); k++){
						for(int l = mintemp; l<activities.get(activities.size()-1-sequence[i]).getUltimateTimeInteger(); l++){
							if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
									if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[i]),l)){

										temptemp = l;
										temptemp1 = washareas.get(k);
										break;
									}
								}
							}
						}
						//Update if we find better solution than the previous ones at a different track.
						if (washareas.get(k).equals(currenttrack)){

							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
						else {
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}

					}
					if(temp == 12412){
						feasible5 = false;
					}

					else{
						activities.get(activities.size()-1-sequence[i]).setUpdate(temp, temp1);
						this.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						currenttrack = temp1;

						//addedcomp.setBusyTime(activities.get(activities.size()-1-j));
						//temp1.setBusyTime(activities.get(activities.size()-1-j)); 

						time23 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
						track23 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
						if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin5){
							margin5 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();
						}
					}
				}
			}
		}

		if (feasible5 == false){
			margin5 = -1;
		}

		if (margin5 > bestmargin){
			time11 = time21;
			time12 = time22;
			time13 = time23;
			track11 = track21;
			System.out.print(time11 + " " + track11 + "5 \n" );

			track12 = track22;
			track13 = track23;
			bestmargin = margin5;
		}

		//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
		for(int i = 0; i<amount; i++){
			if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
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
			sequence[i] = null;
		}

		//OPTION 6: determine the right sequence of how the activities should be planned, in this case: 3, 1, 2.
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

		for (int i = 0; i<sequence.length; i++){
			if (sequence[i]!=null){
				activities.get(activities.size()-1-sequence[i]).setCurrentTrack(currenttrack);
				temp = 12412;
				temptemp = 12412;
				temp1 = null;
				temptemp1 = null;
				if (activities.get(activities.size()-1-sequence[i]).getActivity()==1 || activities.get(activities.size()-1-sequence[i]).getActivity()==2){
					for(int k = 0; k<platforms.size(); k++){

						for(int l = mintemp; l<activities.get(activities.size()-1-sequence[i]).getUltimateTimeInteger(); l++){
							if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
									if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[i]),l)){

										temptemp = l;
										temptemp1 = platforms.get(k);
										break;
									}
								}
							}
						}
						//Update if we find better solution than the previous ones at a different track.
						if (platforms.get(k).equals(currenttrack)){

							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
						else {
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
					}

					if(temp == 12412){
						feasible6 = false;
					}

					else{
						activities.get(activities.size()-1-sequence[i]).setUpdate(temp, temp1);
						currenttrack = temp1;

						addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						temp1.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						this.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						if(activities.get(activities.size()-1-sequence[i]).getActivity() == 1){
							time21 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
							track21 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
							if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin6){
								margin6 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();	
							}
						}
						else if(activities.get(activities.size()-1-sequence[i]).getActivity()== 2){
							time22 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
							track22 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
							if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin6){
								margin6 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();
							}
						}
					}

				}

				else if(activities.get(activities.size()-1-sequence[i]).getActivity() == 3){

					for(int k = 0; k<washareas.size(); k++){
						for(int l = mintemp; l<activities.get(activities.size()-1-sequence[i]).getUltimateTimeInteger(); l++){
							if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
								if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
									if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[i]),l)){

										temptemp = l;
										temptemp1 = washareas.get(k);
										break;
									}
								}
							}
						}
						//Update if we find better solution than the previous ones at a different track.
						if (washareas.get(k).equals(currenttrack)){

							if(temptemp <= temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}
						else {
							if(temptemp < temp){
								temp = temptemp;
								temp1 = temptemp1;
							}
						}

					}
					if(temp == 12412){
						feasible6 = false;
					}

					else{
						activities.get(activities.size()-1-sequence[i]).setUpdate(temp, temp1);
						this.setBusyTime(activities.get(activities.size()-1-sequence[i]));
						currenttrack = temp1;

						//addedcomp.setBusyTime(activities.get(activities.size()-1-j));
						//temp1.setBusyTime(activities.get(activities.size()-1-j)); 

						time23 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
						track23 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
						if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin6){
							margin6 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();
						}
					}
				}
			}
		}

		if (feasible6 == false){
			margin6 = -1;
		}

		if (margin6 > bestmargin){
			time11 = time21;
			time12 = time22;
			time13 = time23;
			track11 = track21;
			System.out.print(time11 + " " + track11 + "6 \n" );

			track12 = track22;
			track13 = track23;
			bestmargin = margin6;
		}

		//Remove all times which have been set at the previous solution, so we can use the available times for the next solution(s).
		for(int i = 0; i<amount; i++){
			if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){
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
			sequence[i] = null;
		}

		//OPTION 7: determine the right sequence of how the activities should be planned, in this case: 5 (=0 and 1), 2, 3.

		//		if (addedcomp.getInspection() && addedcomp.getCleaning()){
		//			
		//			mintemp = addedcomp.getArrivalTimeInteger();
		//			
		//			// add the merged activity 5 which consists of activities 0 and 1. 
		//			activities.add(this.mergeActivities(addedcomp, 0, 1, 5));
		//			amount += 1;
		//
		//			currenttrack = null;
		//			if(activities.get(activities.size()-1-amount).getTrackAssigned()!=null){
		//				activities.get(activities.size()-1-amount).removeTimes();
		//				this.removeBusyTime(activities.get(activities.size()-1-amount));
		//			}

		//			for (int j = 0; j<amount; j++){
		//				for(int i = 0; i<9; i++){
		//					if (activities.get(activities.size()-1-j).getActivity() == i){			
		//						if (i == 5){
		//							sequence[0] = j;
		//						}
		//						else if (i == 2){
		//							sequence[1] = j;
		//						}
		//						else if (i == 3){
		//							sequence[2] = j;
		//						}
		//						break;
		//					}
		//				}
		//			}

		//			for (int i = 0; i<sequence.length; i++){
		//				if (sequence[i]!=null){
		//					activities.get(activities.size()-1-sequence[i]).setCurrentTrack(currenttrack);
		//					temp = 12412;
		//					temptemp = 12412;
		//					temp1 = null;
		//					temptemp1 = null;
		//					if (activities.get(activities.size()-1-sequence[i]).getActivity()==5 || activities.get(activities.size()-1-sequence[i]).getActivity()==2){
		//						for(int k = 0; k<platforms.size(); k++){
		//
		//							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[i]).getUltimateTimeInteger(); l++){
		//								if(platforms.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
		//									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
		//										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[i]),l)){
		//
		//											temptemp = l;
		//											temptemp1 = platforms.get(k);
		//											break;
		//										}
		//									}
		//								}
		//							}
		//							//Update if we find better solution than the previous ones at a different track.
		//							if (platforms.get(k).equals(currenttrack)){
		//
		//								if(temptemp <= temp){
		//									temp = temptemp;
		//									temp1 = temptemp1;
		//								}
		//							}
		//							else {
		//								if(temptemp < temp){
		//									temp = temptemp;
		//									temp1 = temptemp1;
		//								}
		//							}
		//						}
		//
		//						if(temp == 12412){
		//							feasible7 = false;
		//						}
		//
		//						else{
		//							activities.get(activities.size()-1-sequence[i]).setUpdate(temp, temp1);
		//							currenttrack = temp1;
		//
		//							addedcomp.setBusyTime(activities.get(activities.size()-1-sequence[i]));
		//							temp1.setBusyTime(activities.get(activities.size()-1-sequence[i]));
		//							this.setBusyTime(activities.get(activities.size()-1-sequence[i]));
		//							if(activities.get(activities.size()-1-sequence[i]).getActivity() == 5){
		//								mintemp += activities.get(activities.size()-1-sequence[i]).getTotalDurationInteger();
		//								time20 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
		//								track20 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
		//								if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin7){
		//									margin7 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();	
		//								}
		//							}
		//							else if(activities.get(activities.size()-1-sequence[i]).getActivity()== 2){
		//								time22 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
		//								track22 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
		//								if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin7){
		//									margin7 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();
		//								}
		//							}
		//						}
		//
		//					}
		//
		//					else if(activities.get(activities.size()-1-sequence[i]).getActivity() == 3){
		//
		//						for(int k = 0; k<washareas.size(); k++){
		//							for(int l = mintemp; l<activities.get(activities.size()-1-sequence[i]).getUltimateTimeInteger(); l++){
		//								if(washareas.get(k).checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
		//									if(addedcomp.checkFeasibility(activities.get(activities.size()-1-sequence[i]), l)){
		//										if(this.checkFeasibilityMove(activities.get(activities.size()-1-sequence[i]),l)){
		//
		//											temptemp = l;
		//											temptemp1 = washareas.get(k);
		//											break;
		//										}
		//									}
		//								}
		//							}
		//							//Update if we find better solution than the previous ones at a different track.
		//							if (washareas.get(k).equals(currenttrack)){
		//
		//								if(temptemp <= temp){
		//									temp = temptemp;
		//									temp1 = temptemp1;
		//								}
		//							}
		//							else {
		//								if(temptemp < temp){
		//									temp = temptemp;
		//									temp1 = temptemp1;
		//								}
		//							}
		//
		//						}
		//						if(temp == 12412){
		//							feasible7 = false;
		//						}
		//
		//						else{
		//							activities.get(activities.size()-1-sequence[i]).setUpdate(temp, temp1);
		//							this.setBusyTime(activities.get(activities.size()-1-sequence[i]));
		//							currenttrack = temp1;
		//
		//							//addedcomp.setBusyTime(activities.get(activities.size()-1-j));
		//							//temp1.setBusyTime(activities.get(activities.size()-1-j)); 
		//
		//							time23 = activities.get(activities.size()-1-sequence[i]).getPlannedTimeInteger();
		//							track23 = activities.get(activities.size()-1-sequence[i]).getTrackAssigned();
		//							if(activities.get(activities.size()-1-sequence[i]).getMarginInteger()<margin7){
		//								margin7 = activities.get(activities.size()-1-sequence[i]).getMarginInteger();
		//							}
		//						}
		//					}
		//				}
		//			}
		//
		//			if (feasible7 == false){
		//				margin7 = -1;
		//			}
		//
		//			if (margin7 > bestmargin){
		//				time10 = time20;
		//				time11 = -1;
		//				time12 = time22;
		//				time13 = time23;
		//				track10 = track20;
		//				track11 = null;
		//				track12 = track22;
		//				track13 = track23;
		//				bestmargin = margin7;
		//			}
		//		}




		//		//check if feasible solution exist, if so, then update with the best solution
		if (bestmargin != -1){
			for(int i = 0; i<amount; i++){
				if(activities.get(activities.size()-1-i).getTrackAssigned()!=null){

					activities.get(activities.size()-1-i).removeTimes();
					this.removeBusyTime(activities.get(activities.size()-1-i));
					if (addedcomp.getInspection()){
						activities.get(activities.size()-1-amount).removeTimes();
						this.removeBusyTime(activities.get(activities.size()-1-amount));
					}
				}
			}

			//					if (bestmargin == margin7){
			//						for(int i = 0; i<amount; i++){
			//							if(activities.get(activities.size()-1-i).getActivity()==5){
			//								activities.get(activities.size()-1-i).setUpdate(time10, track10);
			//								this.setBusyTime(activities.get(activities.size()-1-i));
			//							}
			//							if(activities.get(activities.size()-1-i).getActivity()==2){
			//								activities.get(activities.size()-1-i).setUpdate(time12, track12);
			//								this.setBusyTime(activities.get(activities.size()-1-i));
			//							}
			//							if(activities.get(activities.size()-1-i).getActivity()==3){
			//								activities.get(activities.size()-1-i).setUpdate(time13, track13);
			//								this.setBusyTime(activities.get(activities.size()-1-i));
			//							}
			//						}
			//					}
			//					else{
			for(int i = 0; i<=amount; i++){
				if(activities.get(activities.size()-1-i).getActivity()==0){
					activities.get(activities.size()-1-i).setUpdate(time10, track10);
					this.setBusyTime(activities.get(activities.size()-1-i));
				}
				else if(activities.get(activities.size()-1-i).getActivity()==1){
					//System.out.print(time11 + " " + track11 + "\n" );
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
			//}
		}
		else{
			throw new IOException("No feasible solution found for job-shop");
		}



		System.out.print("Composition ");
		addedcomp.printTimeLine();
		System.out.print("\n");


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



		//				activity2.removeTimes();
		//		
		//				this.removeBusyTime(activity2);
		//				activity1.getTrackAssigned().removeBusyTimeMove(activity1);
		//				activity1.getComposition().removeBusyTimeMove(activity1);
		//				this.removeBusyTimeMoveLeave(activity1);
		//				activity2.setUpdate(activity1.getPlannedTimeInteger()+activity1.getTotalDurationInteger()-Main.moveduration, activity1.getTrackAssigned());
		//				this.setBusyTime(activity2);
		//				activity2.getTrackAssigned().removeBusyTimeMove(activity2);
		//				activity2.getComposition().removeBusyTimeMove(activity2);
		//				this.removeBusyTimeMoveArrive(activity2);
	}

	public Activity mergeActivities(Composition comp1, int act1, int act2, int actnr) throws IOException{
		int acttimepertrain = 0;
		int maxduration = -1;
		for (int i=0; i<comp1.getSize(); i++){
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
		Activity merged = new Activity(maxduration, (int) comp1.getDepartureTimeInteger()-(maxduration+2*Main.moveduration),comp1, actnr);
		return merged;
	}

	public Activity mergeActivities(Composition comp1, int act1, int act2, int act3, int actnr) throws IOException{
		int acttimepertrain = 0;
		int maxduration = -1;
		for (int i=0; i<comp1.getSize(); i++){
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
		Activity merged = new Activity(maxduration, (int) comp1.getDepartureTimeInteger()-(maxduration+2*Main.moveduration),comp1, actnr);
		return merged;
	}


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
			movelist[i] = activity;
		}

	}
	public void removeBusyTime(Activity activity){
		for(int i = 0; i<movelist.length; i++){
			if(movelist[i] != null && movelist[i].equals(activity)){
				movelist[i] = null; //TODO: TEST WHETHER THIS IS ALLOWED, ONLY REMOVE REFERENCE TO OBJECT, NOT OBJECT SELF
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
							j += Main.moveduration;
						}
						else{
							abcd.add(new Event(finalblockssshallow.get(i), 0, 0, j+Main.moveduration, -1, 0, -1, null));
							first = false;
							j += Main.moveduration;
						}
					}
					else{
						if(finalblockssshallow.get(i).getActivity(j).getActivity()==4){
							abcd.add(new Event(finalblockssshallow.get(i), 1, 1, abcd.get(abcd.size()-1).getStarttime(), j, abcd.get(abcd.size()-1).getSidestart(), finalblockssshallow.get(i).getDepartureSide(), abcd.get(abcd.size()-1)));
							abcd.get(abcd.size()-2).setRelatedEvent(abcd.get(abcd.size()-1));
							abcd.get(abcd.size()-2).setEndTime(j);
							abcd.get(abcd.size()-2).setSideEnd(finalblockssshallow.get(i).getDepartureSide());
							first = true;
							j += Main.moveduration;
						}
						else{
							abcd.add(new Event(finalblockssshallow.get(i), 1, 0, abcd.get(abcd.size()-1).getStarttime(), j, abcd.get(abcd.size()-1).getSidestart(), 0, abcd.get(abcd.size()-1)));
							abcd.get(abcd.size()-2).setRelatedEvent(abcd.get(abcd.size()-1));
							abcd.get(abcd.size()-2).setEndTime(j);
							abcd.get(abcd.size()-2).setSideEnd(0);
							first = true;
							j += Main.moveduration;
						}

					}
				}


			}
		}


		return abcd;
	}
}