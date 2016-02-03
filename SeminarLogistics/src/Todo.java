import java.util.ArrayList;


public class Todo {
	private ArrayList<Composition> compositions;
	private ArrayList<Integer> activities;
	private ArrayList<Double> duration;
	private ArrayList<Double> times;
	private ArrayList<Track> tracksassigned;

	ArrayList<Track> platforms = new ArrayList<>();
	ArrayList<Track> washareas = new ArrayList<>();

	public Todo(ArrayList<Track> tracks){
		compositions = new ArrayList<>();
		activities = new ArrayList<>();
		times = new ArrayList<>();

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
	 */
	public void addComposition(Composition addedcomp){
		double durationactivity;
		double temp;
		Track temp1;
		for(int j = 0; j<4; j++){
			temp = 124123123;
			temp1 = null;
			durationactivity = 0;
			for(int i = 0; i<addedcomp.getSize(); i++){
				if(addedcomp.getTrain(i).getActivity(j)){
					durationactivity += addedcomp.getTrain(i).getActivityTime(j);
				}
			}
			if(durationactivity>0){
				compositions.add(addedcomp);
				activities.add(j);
				duration.add(durationactivity);
				
				for(int k = 0; k<platforms.size(); k++){
					if(platforms.get(k).getFreeTime()<temp){
						temp = platforms.get(k).getFreeTime();
						temp1 = platforms.get(k);
					}
				}
				times.add(temp);
				tracksassigned.add(temp1);
				temp1.setFreeTime(temp1.getFreeTime()+durationactivity);
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
	public void removeActivity(int i){
		times.remove(i);
		compositions.remove(i);
		activities.remove(i);
		tracksassigned.remove(i);
		localdecouple.remove(i);
		duration.remove(i);
	}


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
		for(int i = 0; i<compositions.size(); i++){
			if(times.get(i)<temp){
				temp = times.get(i);
				temptemp = i;
			}
		}
		return temptemp;
	}
	public double getMinTime(){
		return times.get(this.getMin());
	}
	public Composition getMinTrain(){
		return compositions.get(this.getMin());
	}
	public int getMinActivity(){
		return activities.get(this.getMin());
	}
	public Track getMinTrack(){
		return tracksassigned.get(this.getMin());
	}
	public boolean getEmpty(){
		if(compositions.size() == 0){
			return true;
		}
		else{
			return false;
		}
	}
}