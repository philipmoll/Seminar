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
	private Track compositiontrack;
	private int locationontrack;
	private double time;
	private int arrordeptrack;
	//private int time; Because it is not relevant anymore once the train is in our system/shunting yard

	public Composition(ArrayList<Train> compositiontrains, double time, int arrordeptrack){
		this.compositiontrains = compositiontrains;
		this.time = time;
		this.arrordeptrack = arrordeptrack;
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
	
	public Composition decoupleComposition(int locationdecouple){
		
		ArrayList<Train> newcompositionlist = new ArrayList<>();
		int a = this.getSize();
		for(int i=0;i<a-1-locationdecouple;i++){
			newcompositionlist.add(0, this.getTrain(a-1-i));
			this.compositiontrains.remove(a-1-i);
		}
		
		this.updateComposition(); //TODO: TIJD EN VERTREKPLEK TOEVOEGEN!!!!
		
		Composition newcomposition = new Composition(newcompositionlist, 1.0 , 1); //TODO: 1.0, 1 ZEKER NOG VERANDEREN!!!!!!!!!!!
		return newcomposition;
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