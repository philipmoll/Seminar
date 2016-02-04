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

	public Composition getOriginComposition(){ //TODO: test
		return origincomposition;
	}

	public boolean checkEqual(Block block2){ //TODO: test
		boolean equal = true;
		if (this.getSize() == block2.getSize() && this.getArrivaltime() == block2.getArrivaltime() && this.getDeparturetime() == block2.getDeparturetime() && cutposition1 == block2.getCutPosition1() && cutposition2 == block2.getCutPosition2() && origincomposition == block2.getOriginComposition() && this.getLocationOnTrack()==block2.getLocationOnTrack() && this.getTrack() == block2.getTrack() && this.getLength() == block2.getLength()){
			System.out.println("All equal, size this/block2: "+ this.getTrainList().size()+" "+block2.getTrainList().size());
			System.out.println("This trainlist: "+this.getTrainList());
			System.out.println("Block2 trainlist: "+block2.getTrainList());
			System.out.println(" ");
			for (int i = 0; i < this.getTrainList().size(); i++){ //we already know the sizes for this.getTrainList() and block2.getTrainList() are equal
				if(this.getTrainList().get(i) != block2.getTrainList().get(i)){
					//System.out.println("Train "+i+" is not equal");
					equal = false;
				}
			}
		}
		else{// /*
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
			/*
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
