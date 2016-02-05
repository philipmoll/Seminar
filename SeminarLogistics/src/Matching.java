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
	 * @throws CloneNotSupportedException 
	 * */

	public static ArrayList<Block> makeblocks(ArrayList<Composition>compositionlist) throws IndexOutOfBoundsException, MisMatchException, TrackNotFreeException, IOException, CloneNotSupportedException{ //TODO: testfunctie
		ArrayList<Block> blocklist = new ArrayList<>();
		for(int i = 0; i<compositionlist.size();i++){
			Composition currentcomposition = compositionlist.get(i);
			Composition temp = (Composition) currentcomposition.clone();
			if (currentcomposition.getSize()>=4){
				throw new IndexOutOfBoundsException("Composition with index "+i+" has length "+temp.getSize()+" and for function makeblocks in Matching a max of 3 is assumed.");
			}
			ArrayList<Train> trainlist = currentcomposition.getTrainList();
			Block test = new Block(trainlist,currentcomposition.getArrivaltime(),currentcomposition.getDeparturetime(),currentcomposition,-1,currentcomposition.getSize()-1);
			blocklist.add(test);
			System.out.println("trainlist blocklist 0"+blocklist.get(0).getTrainList());
			System.out.println("size temp = "+temp.getSize()+" size trainlist = "+temp.getTrainList().size());
			if(currentcomposition.getSize()==2){
				System.out.println("trainlist blocklist 0 right before decouple"+blocklist.get(0).getTrainList());
				System.out.println("trainlist var right before decouple"+trainlist);
				System.out.println("trainlist temp right before decouple"+temp.getTrainList());
				System.out.println("trainlist currentcomp right before decouple"+currentcomposition.getTrainList());

				Composition temp2 = temp.decoupleComposition(0);
				System.out.println("trainlist blocklist 0 right after decouple"+blocklist.get(0).getTrainList());
				System.out.println("trainlist var right after decouple"+trainlist);
				System.out.println("trainlist temp right after decouple"+temp.getTrainList());
				System.out.println("trainlist currentcomp right after decouple"+currentcomposition.getTrainList());
				ArrayList<Train> trainlist2 = temp.getTrainList();
				ArrayList<Train> trainlist3 = temp2.getTrainList();
				blocklist.add(new Block(trainlist2,temp.getArrivaltime(),temp.getDeparturetime(),currentcomposition,-1,0));
				blocklist.add(new Block(trainlist3,temp2.getArrivaltime(),temp2.getDeparturetime(),currentcomposition,0,1));
				System.out.println("trainlist blocklist 0"+blocklist.get(0).getTrainList());
				System.out.println("trainlist blocklist 1"+blocklist.get(1).getTrainList());
				System.out.println("size temp = "+temp.getSize()+" size trainlist = "+temp.getTrainList().size());
				System.out.println("trainlist blocklist 2"+blocklist.get(2).getTrainList());
				System.out.println("size temp2 = "+temp2.getSize()+" size trainlist = "+temp2.getTrainList().size());
			}
			else if(currentcomposition.getSize()==3){
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