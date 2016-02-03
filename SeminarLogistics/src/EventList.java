import java.util.ArrayList;

public class EventList {
	Todo todo;
	ArrayList<Double> arrivingtimes;
	ArrayList<Double> departuretimes;
	
	//TODO: WHAT IF 2 OR MORE ACTIVITIES OCCUR AT THE SAME TIME? WHICH ONE FIRST?
	public EventList(Todo todo, ArrayList<Double> arrivingtimes, ArrayList<Double> departuretimes, ArrayList<Double> finishtimes){
		this.todo = todo;
		this.arrivingtimes = arrivingtimes;
		this.departuretimes = departuretimes;
	}
	
	public int findNextEvent(){
		return 5;
	}
	
}
