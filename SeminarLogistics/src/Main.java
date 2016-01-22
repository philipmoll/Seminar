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
		Train[] trains = readInTrains();
		Track[] tracks =  readInTracks();
		Network ournetwork = new Network(); //TODO: consider deleting the Networkclass and reading the network in similarly to trains and tracks
		Matrix connectionmatrix = ournetwork.getConnections();
		

		ArrayList<Composition> arrivingcompositions = new ArrayList<>();
		ArrayList<Integer> arrivingtimes = new ArrayList<>(); //This ArrayList should be as long as arrivingcompositions

		ArrayList<Composition> currentcompositions = new ArrayList<>();

		ArrayList<Composition> leavingcompositions = new ArrayList<>();
		ArrayList<Composition> leavingtimes = new ArrayList<>(); //This ArrayList should be as long as leavingcompositions
		
	}
	public static ArrayList<Composition> setUpCompositions(){
		ArrayList<Composition> compositions = new ArrayList<>();
		return compositions;
	}
	public static Train[] readInTrains(){
		try {
			int length;
			//Voor ieder een eigen path
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			Matrix compositiondata = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();

			//Matrix compositiondata2 = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata2.dat").DataToMatrix();
			Matrix compositiondata2 = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/compositiondata2.dat").DataToMatrix();
			//Matrix compositiondata2 = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata2 = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();

			Train[] trains = new Train[compositiondata.getNrRows()];
			
			for(int i = 0; i< compositiondata.getNrRows();i++){
				length = 0;
				
				for(int j = 0; j<compositiondata2.getNrRows();j++){
					if(compositiondata2.getArray(0)[j] == compositiondata.getElement(i,1) && compositiondata2.getArray(1)[j] == compositiondata.getElement(i,2)){
						length = (int) compositiondata2.getElement(j, 5);
					}
				}
				trains[i] = new Train(i+1, (int) compositiondata.getElement(i, 1), (int) compositiondata.getElement(i, 2), length);
			}
			
			return trains;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MatrixIncompleteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Track[] readInTracks(){
		try {
			//Voor ieder een eigen path
			//Matrix trackdata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();
			Matrix trackdata = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();
			//Matrix trackdata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();
			//Matrix trackdata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();


			Track[] tracks = new Track[trackdata.getNrRows()];
			
			for(int i = 0; i<trackdata.getNrRows();i++){
					//combine label1 and label2 to get a labelname:
					String labelname = Integer.toString((int) trackdata.getElement(i, 0));
					int subtype = (int) trackdata.getElement(i, 1);
					switch (subtype){
					//case 0:
						//labelname = labelname;
						//break;
					case 1:
						labelname = labelname + "a";
						break;
					case 2:
						labelname = labelname + "b";
						break;
					case 3:
						labelname = labelname + "c";
						break;
					case 4:
						labelname = labelname + "d";
						break;
					}
					tracks[i]= new Track(labelname,(int) trackdata.getElement(i, 2), (int) trackdata.getElement(i, 3), (int) trackdata.getElement(i, 4),  (int) trackdata.getElement(i, 5), (int) trackdata.getElement(i, 6), (int) trackdata.getElement(i, 7));
			}
			
			return tracks;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MatrixIncompleteException e) {
			e.printStackTrace();
		}
		return null;
	}
}

//voor testen inlezen:
/*
Matrix test = trackdata;
			System.out.println(test.getNrRows());
			for (int i=0; i<test.getNrRows(); i++){
				System.out.println("");
				for (int j=0; j<test.getNrColumns();j++){
					System.out.print(test.getElement(i, j)+" ");
				}
			}
*/
