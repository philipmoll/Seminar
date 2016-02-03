import java.util.ArrayList;

import ilog.concert.*;
import ilog.cplex.*;

public class Matching {

	private ArrayList<Composition> makeblocks(ArrayList<Composition>compositionlist){
		ArrayList<int[]> temp = new ArrayList<>();
		for(int i = 0; i<compositionlist.getSize();i++){

			if(compositionlist[i].getLength()){
				temp.add(new int[] {compositionlist[i].getID(), 0});
			}
			if(compositionlist[i].getCleaning()){
				temp.add(new int[] {compositionlist[i].getID(), 1}); System.out.print(trainlist[i].getCleaning() + " " + i + "\n");
			}
			if(compositionlist[i].getRepairing()){
				temp.add(new int[] {trainlist[i].getID(), 2});
			}
			if(trainlist[i].getWashing()){
				temp.add(new int[] {trainlist[i].getID(), 3});
			}
		}
		return temp;

	}
	
}
