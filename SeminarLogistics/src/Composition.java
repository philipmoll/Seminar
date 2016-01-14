import java.util.*;

public class Composition {
	
	private ArrayList<Train> compositiontrains;
	//private int time; Because it is not relevant anymore once the train is in our system/shunting yard
	
	public Composition(ArrayList<Train> compositiontrains){
		this.compositiontrains = compositiontrains;
	}
	
	//TODO: Do not forget to change the position for all functions!
	//Splitting a composition of size 2, returns 2 compositions of size 1 train
	
	public void coupleCompositionFront(Composition addcomposition){
		
	}
	public void coupleCompositionBack(Composition addcomposition){
		this.compositiontrains.addAll(addcomposition.getCompositionList());
		addcomposition = null;
	}
	public void decoupleCompFront(int nrdecouple){
		
	}
	public void decoupleCompBack(int nrdecouple){
		
	}
	public ArrayList<Train> getCompositionList(){
		return compositiontrains;
	}
	
}