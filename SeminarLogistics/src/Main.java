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
			Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();

			Matrix compositiondata2 = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata2.dat").DataToMatrix();
			//Matrix compositiondata2 = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/compositiondata2.dat").DataToMatrix();
			//Matrix compositiondata2 = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata2 = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();

			Matrix compositiondata3 = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata3.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/carpenter37/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
			//Matrix compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();		

			Matrix trackdata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();
			//Matrix trackdata = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();
			//Matrix trackdata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();
			//Matrix trackdata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();


			Track[] tracks =  readInTracks(trackdata);
			Train[] trainsarr = readInTrains(0, compositiondata, compositiondata2, compositiondata3);
			Train[] trainsdep = readInTrains(1, compositiondata, compositiondata2, compositiondata3);
			
			Network ournetwork = new Network(); //TODO: consider deleting the Networkclass and reading the network in similarly to trains and tracks
			Matrix connectionmatrix = ournetwork.getConnections();

			ArrayList<Composition> arrivingcompositions = setUpCompositions(0, trainsarr, compositiondata, compositiondata3);			
			ArrayList<Double> arrivingtimes = new ArrayList<>();
			ArrayList<Track> arrivingtracks = new ArrayList<>();
			
			ArrayList<Composition> currentcompositions = new ArrayList<>();

			/*ArrayList<Composition> leavingcompositions = setUpCompositions(1, trainsdep, compositiondata, compositiondata3); //TODO: THIS SHOULD ALSO BE A DUPLICATE OF THE OBJECTS OTHERWISE THE TIMES AND POSITION OF A TRAIN IS BEING CHANGES IN ARRIVINGCOMPOSITIONS!!!!
			ArrayList<Double> leavingtimes = new ArrayList<>();
			ArrayList<Track> leavingtracks = new ArrayList<>();
			*/


			//This is how we should write a couple function, N.B.: with the .remove function.
			//arrivingcompositions.get(0).coupleComposition(arrivingcompositions.get(1));
			//arrivingcompositions.remove(1);

			//TODO: CURRENT SHOULD BE A DUPLICATE OF THE ARRIVING AND NOT A DIRECT REFERENCE TO THEIR OBEJCTS!!!!!!!
			//This is how we should write a decouple function, N.B.: with the.add function.
			//arrivingcompositions.add(arrivingcompositions.get(14).decoupleComposition(0));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MatrixIncompleteException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<Composition> setUpCompositions(int arrordep, Train[] trains1, Matrix compositiondata, Matrix compositiondata3){
		int abcd = 0;
		ArrayList<Composition> compositions = new ArrayList<>();
		for(int i = 0; i<compositiondata3.getNrRows();i++){
			if(compositiondata3.getElement(i,1)==arrordep){
				ArrayList<Train> templist = new ArrayList<>();
				for(int j = 0; j<compositiondata.getNrRows(); j++){
					if(compositiondata3.getElement(i, 0)==compositiondata.getElement(j,0)){
						templist.add(trains1[abcd]);
						abcd += 1;
					}
				}
				compositions.add(new Composition(templist));
			}
		}
		return compositions;
	}

	public static Train[] readInTrains(int arrordep, Matrix compositiondata, Matrix compositiondata2, Matrix compositiondata3){
		int abcd = 0;
		int length;

		Train[] trains = new Train[compositiondata.getNrRows()/2];

		for(int k = 0; k<compositiondata3.getNrRows();k++){
			if(compositiondata3.getElement(k,1) ==arrordep){
				for(int i = 0; i< compositiondata.getNrRows();i++){
					if(compositiondata3.getElement(k,0) == compositiondata.getElement(i,0)){
						length = 0;

						for(int j = 0; j<compositiondata2.getNrRows();j++){
							if(compositiondata2.getArray(0)[j] == compositiondata.getElement(i,1) && compositiondata2.getArray(1)[j] == compositiondata.getElement(i,2)){
								length = (int) compositiondata2.getElement(j, 5);
							}
						}
						trains[abcd] = new Train(abcd+1, (int) compositiondata.getElement(i, 1), (int) compositiondata.getElement(i, 2), length);
						abcd += 1;
					}
				}
			}
		}
		return trains;
	}

	public static Track[] readInTracks(Matrix trackdata){
		//Voor ieder een eigen path

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
