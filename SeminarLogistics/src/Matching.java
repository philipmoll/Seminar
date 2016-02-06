import java.io.IOException;
import java.util.ArrayList;

//import ilog.concert.*;
//import ilog.cplex.*;

public class Matching {
	
	@SuppressWarnings("unused")
	private ArrayList<Block> newblocklist;
	
	//constructor:
	public Matching(){
		newblocklist = new ArrayList<Block>();
	}
	
	
	/** Find set of all possible parts for all arriving or departing trains
	 * @return blocklist : complete set of blocks.
	 * @throws CloneNotSupportedException 
	 * */
	public static ArrayList<Block> makeblocks(ArrayList<Composition>compositionlist) throws IndexOutOfBoundsException, MisMatchException, TrackNotFreeException, IOException, CloneNotSupportedException{ 
		ArrayList<Block> blocklist = new ArrayList<>();
		for(int i = 0; i<compositionlist.size();i++){
			Composition currentcomposition = compositionlist.get(i);
			//deepcopy currentcompositiion, but make sure the trainlist still points to the original trains
			Composition temp = (Composition) DeepCopy.copy(currentcomposition);
			temp.coupleComposition(currentcomposition);
			temp = temp.decoupleComposition(currentcomposition.getSize()-1);
			
			//throw exception if composition consists of more than 3 trains
			if (currentcomposition.getSize()>=4){
				throw new IndexOutOfBoundsException("Composition with index "+i+" has length "+temp.getSize()+" and for function makeblocks in Matching a max of 3 is assumed.");
			}
			ArrayList<Train> trainlist = currentcomposition.getTrainList();
			Block test = new Block(trainlist,currentcomposition.getArrivaltime(),currentcomposition.getDeparturetime(),currentcomposition,-1,currentcomposition.getSize()-1);
			blocklist.add(test);
			//System.out.println("trainlist blocklist 0"+blocklist.get(0).getTrainList());
			//System.out.println("size temp = "+temp.getSize()+" size trainlist = "+temp.getTrainList().size());
			if(currentcomposition.getSize()==2){
				Composition temp2 = temp.decoupleComposition(0);
				ArrayList<Train> trainlist2 = temp.getTrainList();
				ArrayList<Train> trainlist3 = temp2.getTrainList();
				blocklist.add(new Block(trainlist2,temp.getArrivaltime(),temp.getDeparturetime(),currentcomposition,-1,0));
				blocklist.add(new Block(trainlist3,temp2.getArrivaltime(),temp2.getDeparturetime(),currentcomposition,0,1));
				}
			else if(currentcomposition.getSize()==3){
				Composition temp2 = temp.decoupleComposition(0);
				//save current version of temp2 as temp5
				Composition temp5 = (Composition) DeepCopy.copy(temp2);
				temp5.coupleComposition(temp2);
				temp5 = temp5.decoupleComposition(temp2.getSize()-1);
				//temp now final, will not be changed anymore so can be used:
				blocklist.add(new Block(temp.getTrainList(),temp.getArrivaltime(),temp.getDeparturetime(),currentcomposition,-1,0));
				blocklist.add(new Block(temp5.getTrainList(),temp5.getArrivaltime(),temp5.getDeparturetime(),currentcomposition,0,2));
				Composition temp3 = temp2.decoupleComposition(0);
				//temp2 now final, will not be changed anymore so can be used:
				blocklist.add(new Block(temp2.getTrainList(),temp2.getArrivaltime(),temp2.getDeparturetime(),currentcomposition,0,1));
				//temp3 now final, will not be changed anymore so can be used:
				blocklist.add(new Block(temp3.getTrainList(),temp3.getArrivaltime(),temp3.getDeparturetime(),currentcomposition,1,2));
				Composition temp4 = (Composition) DeepCopy.copy(temp);
				temp4.coupleComposition(temp);
				temp4 = temp4.decoupleComposition(0);
				temp4.coupleComposition(temp2);
				blocklist.add(new Block(temp4.getTrainList(),temp4.getArrivaltime(),temp4.getDeparturetime(),currentcomposition,-1,1));
			}
		}
		return blocklist;
	}
}

