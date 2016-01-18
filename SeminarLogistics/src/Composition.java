import java.util.*;

/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */

public class Composition {

	private ArrayList<Train> compositiontrains;
	private int compositionlength;
	private int compositionsize;
	//private int time; Because it is not relevant anymore once the train is in our system/shunting yard

	public Composition(ArrayList<Train> compositiontrains){
		this.compositiontrains = compositiontrains;

		this.updateComposition();
	}

	//TODO: Do not forget to change the position for all functions!
	//Splitting a composition of size 2, returns 2 compositions of size 1 train

	public Composition coupleComposition(Composition addcomposition){
		this.compositiontrains.addAll(addcomposition.getCompositionList());

		//addcomposition = null; If this does not work well, we need to make the function in Main.
		//What happens if: In an ArrayList of objects, the objects is set to null but is not removed from the list?
		// The objects is then removed from the ArrayList completely.
		this.updateComposition();
		return this;
	}
	
	public Composition[] decoupleComposition(int nrdecouple){
		Composition[] newcomposition1 = new Composition[2];
		
		ArrayList<Train> newcompositionlist = new ArrayList<>();

		for(int i=1;i<=nrdecouple;i++){
			newcompositionlist.add(0, this.getTrain(this.getSize()-1));
			this.compositiontrains.remove(this.getSize()-1);
		}
		
		this.updateComposition();
		
		Composition newcomposition = new Composition(newcompositionlist);
		
		newcomposition1[0] = this;
		newcomposition1[1] = newcomposition;

		return newcomposition1;
	}

	public int getLength(){
		return compositionlength;
	}
	public int getSize(){
		return compositionsize;
	}

	public Train getTrain(int i){
		return this.compositiontrains.get(i);
	}

	public ArrayList<Train> getCompositionList(){
		return compositiontrains;
	}

	private void updateComposition(){
		int templength = 0;
		for(int i=0;i<this.getCompositionList().size();i++){ //TODO: What if composition = 0?
			templength += this.compositiontrains.get(i).getLength();
			this.getTrain(i).changePosition(i);
		}
		this.compositionsize = compositiontrains.size();
		this.compositionlength = templength;
	}
}