import java.util.*;

public class Composition {
	
	private ArrayList<Train> compositiontrains;
	private int compositionlength;
	private int compositionsize;
	//private int time; Because it is not relevant anymore once the train is in our system/shunting yard
	
	public Composition(ArrayList<Train> compositiontrains){
		this.compositiontrains = compositiontrains;
		this.compositionlength = 0;
		this.compositionsize = compositiontrains.size();
		
		for(int i=0;i<this.compositionlength;i++){ //TODO: What if composition = 0?
			this.compositionlength += this.compositiontrains.get(i).getLength();
		}
	}
	
	//TODO: Do not forget to change the position for all functions!
	//Splitting a composition of size 2, returns 2 compositions of size 1 train
	
	public Composition coupleCompositionBack(Composition addcomposition){
		this.compositiontrains.addAll(addcomposition.getCompositionList());
		
		addcomposition = null; /* I think this is to be done in Main, because we are going to store the compositions in an ArrayList?*/
		//What happens if: In an ArrayList of objects, the objects is set to null but is not removed from the list?
		for(int i=1;i<=this.getCompositionSize();i++){
			addcomposition.getTrain(i).changePosition(i);
		}
		
		return this;
	}
	
	public int getCompositionLength(){
		return compositionlength;
	}
	
	public int getCompositionSize(){
		return compositionsize;
	}
	
	public Train getTrain(int i){
		return this.compositiontrains.get(i);
	}
	
	public void decoupleCompBack(int nrdecouple){
		
		for(int i=1;i<=nrdecouple;i++){
			this.compositiontrains.remove(this.compositiontrains.size()-1);
		}
		
		
	}
	
	public Composition decoupleCompBack2(int nrdecouple){
		ArrayList<Train> newcompositionlist = new ArrayList<>();
				
		for(int i=1;i<=nrdecouple;i++){
			newcompositionlist.add(0, this.getTrain(this.getCompositionSize()-1));
			this.compositiontrains.remove(this.getCompositionSize()-1);
		}
		Composition newcomposition = new Composition(newcompositionlist);
		return newcomposition;
	}
	
	
	
	public ArrayList<Train> getCompositionList(){
		return compositiontrains;
	}
}