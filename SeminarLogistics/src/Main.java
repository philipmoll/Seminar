import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */

public class Main {

	public static void main(String args[])
	{
		try {
			Matrix compositiondata = new DataSet("compositiondata.dat").DataToMatrix();
			
			ArrayList<Composition> arrivingcompositions = new ArrayList<>();
			ArrayList<Integer> arrivingtimes = new ArrayList<>(); //This arraylist should be as long as arrivingcompositions
			
			Train T1 = new Train(1,2);
			Train T2 = new Train(2,2);
			Train T3 = new Train(3,1);
			Train T4 = new Train(4,2);
			Train T5 = new Train(5,2);
			Train T6 = new Train(6,1);
			
			System.out.print(T1.getID() +" " + T1.getType() +" " + T1.getLength());
			
			Composition C1 = new Composition(new ArrayList<Train>(){{add(T1);add(T2);}});
			
			//System.out.print(C1.getCompositionLength());
			
			//ArrayList<Composition> currentcompositions = new ArrayList<>();
			ArrayList<Composition> currentcompositions = arrivingcompositions; //Is this allowed?
			ArrayList<Composition> leavingcompositions = new ArrayList<>();
			
			
			
			
			
			
			/* Om te testen!!
			ArrayList<Train> testest = new ArrayList<>();
			Train a = new Train(1,2);
			Train b = new Train(2,1);
			testest.add(a);
			testest.add(b);
			a = null;
			System.out.print(testest.get(0).getID());
			*/
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MatrixIncompleteException e) {
			e.printStackTrace();
		}
	}
}
