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
		//try {
			//Load data and store as OurData.
			//Matrix abc = new DataSet("abc.dat").DataToMatrix();
			
			ArrayList<Composition> arrivingcompositions = new ArrayList<>();
			ArrayList<Integer> arrivingtimes = new ArrayList<>(); //This arraylist should be as long as arrivingcompositions
			
			
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
			
			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (MatrixIncompleteException e) {
//			e.printStackTrace();
//		}
	}
}
