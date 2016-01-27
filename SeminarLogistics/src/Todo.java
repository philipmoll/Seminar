import java.util.ArrayList;


public class Todo {
	private ArrayList<Train> trains;
	private ArrayList<Integer> activities;
	private ArrayList<Double> times;
	private ArrayList<Track> tracksassigned;
	ArrayList<Track> platforms = new ArrayList<>();
	ArrayList<Track> washareas = new ArrayList<>();
	
	public Todo(ArrayList<Track> tracks){
		trains = new ArrayList<>();
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
		
	public void addComposition(Composition addedcomp){
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
	public void removeActivity(int i){
		times.remove(i);
		trains.remove(i);
		activities.remove(i);
		tracksassigned.remove(i);
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
	
}