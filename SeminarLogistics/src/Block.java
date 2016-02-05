import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Block extends Composition { //TODO: test class

	private Composition origincomposition;
	private int cutposition1;
	private int cutposition2;

	public Block(ArrayList<Train> compositiontrains, double arrivaltime, double departuretime, Composition origincomposition, int cutposition1, int cutposition2) throws IOException {
		super(compositiontrains, arrivaltime, departuretime);
		this.origincomposition=origincomposition;
		//throw exception if cutpositions are wrongly defined
		if (cutposition1>=cutposition2){
			throw new IOException("Cutposition 1 ("+cutposition1+") is larger than cutposition 2 ("+cutposition2+")");
		}
		if (cutposition1 < -1 || cutposition2 > origincomposition.getTrainList().size()-1){
			throw new IndexOutOfBoundsException("Cutposition 1 is "+cutposition1+" and should be at least -1; cutposition 2 is "+cutposition2+" and should be at most "+(compositiontrains.size()-1));
		}
		//throw exception if cutpositions do not correspond to trainlist of originalcomposition
		for (int i = cutposition1 + 1; i<=cutposition2; i++){
			if (origincomposition.getTrainList().get(i) != compositiontrains.get(i-cutposition1-1)){
				throw new IOException("Trains between cutpositions "+cutposition1+" and "+cutposition2+" of origincomposition are not equal to trains in compositiontrains in Block constructor");
			}
		}
		//throw exception if arrival time of block is not equal to arrival time of origincomposition
		if (arrivaltime != origincomposition.getArrivaltime()){
			throw new IOException("Conflict: arrivaltime of new block is "+arrivaltime+" and arrivaltime of origincomposition is "+origincomposition.getArrivaltime());
		}
		/*
		//throw exception if departure time of block is not equal to departure time of origincomposition
		if (departuretime != origincomposition.getDeparturetime()){
			throw new IOException("Conflict: departuretime of new block is "+departuretime+" and departuretime of origincomposition is "+origincomposition.getDeparturetime());
		}
		*/
		this.cutposition1=cutposition1;
		this.cutposition2=cutposition2;
	}

	public int getCutPosition1(){
		return cutposition1;
	}

	public int getCutPosition2(){
		return cutposition2;
	}

	public Composition getOriginComposition(){
		return origincomposition;
	}

	public boolean checkEqual(Block block2){
		boolean equal = true;
		if (this.getSize() == block2.getSize() && this.getArrivaltime() == block2.getArrivaltime() && this.getDeparturetime() == block2.getDeparturetime() && cutposition1 == block2.getCutPosition1() && cutposition2 == block2.getCutPosition2() && origincomposition == block2.getOriginComposition() && this.getLocationOnTrack()==block2.getLocationOnTrack() && this.getTrack() == block2.getTrack() && this.getLength() == block2.getLength()){
			for (int i = 0; i < this.getTrainList().size(); i++){ //we already know the sizes for this.getTrainList() and block2.getTrainList() are equal
				if(this.getTrainList().get(i)!=block2.getTrainList().get(i)){
					equal = false;
				}
			}
		}
		else{ /*
			if (this.getSize() != block2.getSize()){
				System.out.println("Size is not equal");
			}
			if (this.getArrivaltime() != block2.getArrivaltime()){
				System.out.println("Arrivaltime is not equal");
			}
			if (this.getDeparturetime() != block2.getDeparturetime()){
				System.out.println("Departuretime is not equal");
			}
			if (cutposition1 != block2.getCutPosition1()){
				System.out.println("Cutposition1 is not equal");
			}
			if (cutposition2 != block2.getCutPosition2()){
				System.out.println("Cutposition2 is not equal");
			}
			if (origincomposition != block2.getOriginComposition()){
				System.out.println("origincomposition is not equal");
			}
			if (this.getLocationOnTrack()!=block2.getLocationOnTrack()){
				System.out.println("locationontrack is not equal");
			}
			if (this.getTrack() != block2.getTrack()){
				System.out.println("track is not equal");
			}
			if (this.getLength() != block2.getLength()){
				System.out.println("length is not equal");
			}
			
			if (this.getTrainList() != block2.getTrainList()){
				System.out.println("trainlist is not equal");
				System.out.println(this.getTrainList() + " " + block2.getTrainList());
				for (int i = 0; i<this.getTrainList().size(); i++){
					if (this.getTrainList().get(i) != block2.getTrainList().get(i)){
						System.out.println("Train "+i+" is not equal");
					}
					System.out.println("ID this: "+this.getTrainList().get(i).getID()+" ID block2: "+block2.getTrainList().get(i).getID());
				}
			}
			 */
			equal = false;
		}
		return equal;
	}



}
