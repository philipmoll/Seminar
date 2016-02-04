import java.io.IOException;
import java.util.ArrayList;

public class Block extends Composition { //TODO: test class
	
	private Composition origincomposition;
	private int cutposition1;
	private int cutposition2;

	public Block(ArrayList<Train> compositiontrains, double arrivaltime, double departuretime, Composition origincomposition, int cutposition1, int cutposition2) throws IOException {
		super(compositiontrains, arrivaltime, departuretime);
		this.origincomposition=origincomposition;
		this.cutposition1=cutposition1;
		this.cutposition2=cutposition2;
	}
	
	public int getCutPosition1(){ //TODO: test
		return cutposition1;
	}
	
	public int getCutPosition2(){ //TODO: test
		return cutposition2;
	}
	
	public Composition getOriginComposition(){
		return origincomposition;
	}


}
