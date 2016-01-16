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

		this.updateLength();
	}

	//TODO: Do not forget to change the position for all functions!
	//Splitting a composition of size 2, returns 2 compositions of size 1 train

	public Composition coupleCompositionBack(Composition addcomposition){
		this.compositiontrains.addAll(addcomposition.getCompositionList());

		addcomposition = null; //If this does not work well, we need to make the function in Main.
		//TODO: What happens if: In an ArrayList of objects, the objects is set to null but is not removed from the list?
		for(int i=1;i<=this.getCompositionSize();i++){
			addcomposition.getTrain(i).changePosition(i);
		}
		this.updateLength();
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

	public Composition[] decoupleCompBack2(int nrdecouple){
		Composition[] newcomposition1 = new Composition[2];
		
		ArrayList<Train> newcompositionlist = new ArrayList<>();

		for(int i=1;i<=nrdecouple;i++){
			newcompositionlist.add(0, this.getTrain(this.getCompositionSize()-1));
			this.compositiontrains.remove(this.getCompositionSize()-1);
		}
		
		this.updateLength();
		
		Composition newcomposition = new Composition(newcompositionlist);
		
		newcomposition1[0] = this;
		newcomposition1[1] = newcomposition;

		return newcomposition1;
	}

	public ArrayList<Train> getCompositionList(){
		return compositiontrains;
	}

	private void updateLength(){
		this.compositionlength = 0;
		for(int i=0;i<this.compositionlength;i++){ //TODO: What if composition = 0?
			this.compositionlength += this.compositiontrains.get(i).getLength();
		}
		this.compositionsize = compositiontrains.size();
	}
}