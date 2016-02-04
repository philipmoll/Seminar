import java.io.IOException;
import java.util.ArrayList;

//import ilog.concert.*;
//import ilog.cplex.*;

public class Matching { //TODO: testclass
	
	private ArrayList<Block> newblocklist;
	
	//constructor:
	public Matching(){
		newblocklist = new ArrayList<Block>();
	}
	
	
	/** Find set of all possible parts for all arriving or departing trains
	 * @return blocklist : complete set of blocks.
	 * */

	public static ArrayList<Block> makeblocks(ArrayList<Composition>compositionlist) throws IndexOutOfBoundsException, MisMatchException, TrackNotFreeException, IOException{ //TODO: testfunctie
		ArrayList<Block> blocklist = new ArrayList<>();
		for(int i = 0; i<compositionlist.size();i++){
			Composition currentcomposition = compositionlist.get(i);
			Composition temp = compositionlist.get(i);
			if (currentcomposition.getSize()>=4){
				throw new IndexOutOfBoundsException("Composition with index "+i+" has length "+temp.getSize()+" and for function makeblocks in Matching a max of 3 is assumed.");
			}
			Block test = new Block(temp.getTrainList(),temp.getArrivaltime(),temp.getDeparturetime(),temp,-1,temp.getSize()-1);
			blocklist.add(test);
			System.out.println(blocklist.get(blocklist.size()-1).getTrainList());
			System.out.println("size temp = "+temp.getSize()+" size trainlist = "+temp.getTrainList().size());
			/*if(currentcomposition.getSize()==2){
				Composition temp2 = temp.decoupleComposition(0);
				blocklist.add(new Block(temp.getTrainList(),temp.getArrivaltime(),temp.getDeparturetime(),currentcomposition,-1,0));
				blocklist.add(new Block(temp2.getTrainList(),temp2.getArrivaltime(),temp2.getDeparturetime(),currentcomposition,0,1));
			}*/
			/*else*/ if(currentcomposition.getSize()==3){
				Composition temp2 = temp.decoupleComposition(0);
				blocklist.add(new Block(temp.getTrainList(),temp.getArrivaltime(),temp.getDeparturetime(),currentcomposition,-1,0));
				blocklist.add(new Block(temp2.getTrainList(),temp2.getArrivaltime(),temp2.getDeparturetime(),currentcomposition,0,2));
				Composition temp3 = temp2.decoupleComposition(0);
				blocklist.add(new Block(temp2.getTrainList(),temp2.getArrivaltime(),temp2.getDeparturetime(),currentcomposition,0,1));
				blocklist.add(new Block(temp3.getTrainList(),temp3.getArrivaltime(),temp3.getDeparturetime(),currentcomposition,1,2));
				temp.coupleComposition(temp2);
				blocklist.add(new Block(temp.getTrainList(),temp.getArrivaltime(),temp.getDeparturetime(),currentcomposition,-1,1));
			}
		}
		return blocklist;
	}
}