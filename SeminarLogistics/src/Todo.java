import java.util.ArrayList;


public class Todo {
	private ArrayList<Train> trains;
	private ArrayList<Integer> activities;
	private ArrayList<Double> times;
	private ArrayList<Track> tracks;

	public Todo(ArrayList<Track> tracks){
		trains = new ArrayList<>();
		activities = new ArrayList<>();
		times = new ArrayList<>();
		this.tracks = tracks;
	}
	public void addComposition(Composition addedcomp){
		for(int i = 0; i<addedcomp.getSize(); i++){
			if(addedcomp.getTrain(i).getInspecting()){
				trains.add(addedcomp.getTrain(i));
				activities.add(0);
				times.add(0.0);
			}
			else if(addedcomp.getTrain(i).getCleaning()){
				trains.add(addedcomp.getTrain(i));
				activities.add(1);
				times.add(0.0);
			}
			else if(addedcomp.getTrain(i).getRepairing()){
				trains.add(addedcomp.getTrain(i));
				activities.add(2);
				times.add(0.0);
			}
			else if(addedcomp.getTrain(i).getWashing()){
				trains.add(addedcomp.getTrain(i));
				activities.add(3);
				times.add(0.0);
			}
		}
	}
	public void removeActivity(int i){
		times.remove(i);
		trains.remove(i);
		activities.remove(i);
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
