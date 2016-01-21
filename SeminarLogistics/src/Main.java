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
		readInTrains();
		
		ArrayList<Composition> arrivingcompositions = new ArrayList<>();
		ArrayList<Integer> arrivingtimes = new ArrayList<>(); //This ArrayList should be as long as arrivingcompositions

		ArrayList<Composition> currentcompositions = new ArrayList<>();

		ArrayList<Composition> leavingcompositions = new ArrayList<>();
		ArrayList<Composition> leavingtimes = new ArrayList<>(); //This ArrayList should be as long as leavingcompositions
		
	}
	public static void readInTrains(){
		try {
			int length;
			//Voor ieder een eigen path
			Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();

			Matrix compositiondata2 = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata2.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();

			ArrayList<Train> trains = new ArrayList<>();
			for(int i = 0; i< compositiondata.getNrRows();i++){
				length = 0;
				
				for(int j = 0; j<compositiondata2.getNrRows();j++){
					if(compositiondata2.getArray(0)[j] == compositiondata.getElement(i,1) && compositiondata2.getArray(1)[j] == compositiondata.getElement(i,2)){
						length = (int) compositiondata2.getElement(j, 5);
					}
				}
				
				trains.add(new Train(i+1, (int) compositiondata.getElement(i, 1), (int) compositiondata.getElement(i, 2), length));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MatrixIncompleteException e) {
			e.printStackTrace();
		}
	}
}
