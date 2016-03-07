import java.io.IOException;
import java.util.*;

/*
 * TODO: BIJ MATCHING ARRIVING MOVE TIJD WEGHALEN EN KOPPELTIJD CHECKEN!
 * TODO: maxdrivebacklength inlezen
 * 
 */



/**
 *
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
public class Main {

	public static final int decoupletime = 2; //MINUTES
	public static final int coupletime = 3; //MINUTES
	public final static int moveduration = 1; //MINUTES
	public final static double begintime = .33333333333; //MINUTES //TODO: make flexible!!!!!!!!!!! begintime nu 8AM, maar kan later veranderen

	private final static int nrjobshops = 8;
	private final static int nrparkings = 2;

	//	private static int fromjobshop;
	//	private static int fromparking;

	public static void main(String args[])
	{
		try {
			//int user = 1; //Friso
			int user = 2; //Floor
			//int user = 3; //Robin
			//int user = 4; //Philip
			int a = 0;

			Matrix compositiondata;
			Matrix compositiondata2;
			Matrix compositiondata3;
			Matrix trackdata;
			Matrix connections;
			if (user == 1) //Friso
			{
				compositiondata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/testcompositiondata.dat").DataToMatrix();
				compositiondata2 = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/compositiondata2.dat").DataToMatrix();
				compositiondata3 = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/testcompositiondata3.dat").DataToMatrix();
				trackdata = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();
				connections = new DataSet("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/networkdata.dat").DataToMatrix();
			}
			else if (user == 2){ //Floor
				compositiondata = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
				compositiondata2 = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/compositiondata2.dat").DataToMatrix();
				compositiondata3 = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/compositiondata3.dat").DataToMatrix();
				trackdata = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();
				connections = new DataSet("C:/Users/Floor Wofhagen/Documents/Econometrie/Master/Blok 3/Seminar Logistics/Workspace/Seminar/SeminarLogistics/src/networkdata.dat").DataToMatrix();

			}
			else if (user == 3){ //Robin
				compositiondata = new DataSet("/Users/carpenter37/git/Seminar/SeminarLogistics/src/compositiondata.dat").DataToMatrix();
				compositiondata2 = new DataSet("/Users/carpenter37/git/Seminar/SeminarLogistics/src/compositiondata2.dat").DataToMatrix();
				compositiondata3 = new DataSet("/Users/carpenter37/git/Seminar/SeminarLogistics/src/compositiondata3.dat").DataToMatrix();
				trackdata = new DataSet("/Users/carpenter37/git/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();
				connections = new DataSet("/Users/carpenter37/git/Seminar/SeminarLogistics/src/networkdata.dat").DataToMatrix();

			}
			else { //user == 4, Philip
				compositiondata = new DataSet("C:/Users/Philip Moll/git/Seminar/SeminarLogistics/src/testcompositiondata.dat").DataToMatrix();
				compositiondata2 = new DataSet("C:/Users/Philip Moll/git/Seminar/SeminarLogistics/src/compositiondata2.dat").DataToMatrix();
				compositiondata3 = new DataSet("C:/Users/Philip Moll/git/Seminar/SeminarLogistics/src/testcompositiondata3.dat").DataToMatrix();
				trackdata = new DataSet("C:/Users/Philip Moll/git/Seminar/SeminarLogistics/src/trackdata.dat").DataToMatrix();
				connections = new DataSet("C:/Users/Philip Moll/git/Seminar/SeminarLogistics/src/networkdata.dat").DataToMatrix();
			}

			Track[] tracks =  readInTracks(trackdata);

//			for (int i = 0; i<tracks.length; i++){
//				System.out.println(tracks[i].getLabel()+" has parkpos: "+tracks[i].getParktrain()+" and length "+tracks[i].getTracklength()+" and maxbackward "+tracks[i].getMaxDriveBackLength()); 
//			}
			Train[] trainsarr = readInTrains(0, compositiondata, compositiondata2, compositiondata3);
			Train[] trainsdep = readInTrains(1, compositiondata, compositiondata2, compositiondata3);

//			for (int i = 0; i<trainsarr.length; i++){
//				System.out.println("interch"+trainsarr[i].getInterchangeable());
//			}
			Train[] trainstoadd = new Train[16];
			trainstoadd[0] = new Train(1001,2,4);
			trainstoadd[1] = new Train(1002,1,4);
			trainstoadd[2] = new Train(1003,3,4);
			trainstoadd[3] = new Train(1004,2,4);
			trainstoadd[4] = new Train(1005,1,4);
			trainstoadd[5] = new Train(1006,2,4);
			trainstoadd[6] = new Train(1007,2,4);

			trainstoadd[7] = new Train(1008,2,4);
			trainstoadd[8] = new Train(1009,3,4);
			trainstoadd[9] = new Train(1010,2,4);
			trainstoadd[10] = new Train(1011,2,4);
			trainstoadd[11] = new Train(1012,1,4);

			trainstoadd[12] = new Train(1013,2,4);
			trainstoadd[13] = new Train(1014,1,4);
			trainstoadd[14] = new Train(1015,2,4);
			trainstoadd[15] = new Train(1016,2,4);

			int[] arrivingpositions = new int[16];
			arrivingpositions[0] = 11;
			arrivingpositions[1] = 8;
			arrivingpositions[2] = 10;
			arrivingpositions[3] = 12;
			arrivingpositions[4] = 0;
			arrivingpositions[5] = 13;
			arrivingpositions[6] = 18;
			arrivingpositions[7] = 4;
			arrivingpositions[8] = 2;
			arrivingpositions[9] = 1;
			arrivingpositions[10] = 15;
			arrivingpositions[11] = 6;
			arrivingpositions[12] = 16;
			arrivingpositions[13] = 5;
			arrivingpositions[14] = 3;
			arrivingpositions[15] = 9;

			int[] departingpositions = new int[16];
			departingpositions[0] = 18;
			departingpositions[1] = 9;
			departingpositions[2] = 11;
			departingpositions[3] = 17;
			departingpositions[4] = 4;
			departingpositions[5] = 15;
			departingpositions[6] = 20;
			departingpositions[7] = 6;
			departingpositions[8] = 1;
			departingpositions[9] = 2;
			departingpositions[10] = 17;
			departingpositions[11] = 8;
			departingpositions[12] = 12;
			departingpositions[13] = 3;
			departingpositions[14] = 0;
			departingpositions[15] = 7;

			int nrtrainstoadd = 1; //TODO: zelf invullen
			Train[] trainstoaddnow = new Train[nrtrainstoadd];
			int[] arrivingpositionsnow = new int[nrtrainstoadd];
			int[] departingpositionsnow = new int[nrtrainstoadd];
			for (int i = 0; i<nrtrainstoadd; i++){
				trainstoaddnow[i]=trainstoadd[i];
				arrivingpositionsnow[i]=arrivingpositions[i];
				departingpositionsnow[i]=departingpositions[i];
			}
			new Schedule(trainsarr);
			new Schedule(trainstoaddnow);

			//			for(int l = 0; l<trainsarr.length; l++){
			//				System.out.println(trainsarr[l].getActivity(0) + " " + trainsarr[l].getActivity(1) + " " + trainsarr[l].getActivity(2) + " " + trainsarr[l].getActivity(3));
			//			}

			ArrayList<Composition> arrivingcompositions = setUpCompositions(0, trainsarr, compositiondata, compositiondata3);
			ArrayList<Double> arrivingtimes = setUpTimes(0, compositiondata3);
			ArrayList<Track> arrivingtracks = setUpTracks(0, tracks, compositiondata3);

			//ArrayList<Composition> arrivingcompositionswitharrtime = new ArrayList<>();
			for (int i = 0; i< arrivingcompositions.size();i++){
				if (arrivingtimes.get(i)<begintime){
					arrivingcompositions.get(i).setArrivaltime(arrivingtimes.get(i)+1-begintime);
				}
				else{
					arrivingcompositions.get(i).setArrivaltime(arrivingtimes.get(i)-begintime);
				}
			}



			ArrayList<Composition> leavingcompositions = setUpCompositions(1, trainsdep, compositiondata, compositiondata3); //TODO: THIS SHOULD ALSO BE A DUPLICATE OF THE OBJECTS OTHERWISE THE TIMES AND POSITION OF A TRAIN IS BEING CHANGES IN ARRIVINGCOMPOSITIONS!!!!
			ArrayList<Double> leavingtimes = setUpTimes(1, compositiondata3);
			ArrayList<Track> leavingtracks = setUpTracks(1, tracks, compositiondata3);

			for (int i = 0; i< leavingcompositions.size();i++){
				if (leavingtimes.get(i)<begintime){
					leavingcompositions.get(i).setDeparturetime(leavingtimes.get(i)+1-begintime);
				}
				else{
					leavingcompositions.get(i).setDeparturetime(leavingtimes.get(i)-begintime);
				}
			}

			Composition[] addarriving = new Composition[nrtrainstoadd];
			Composition[] adddeparting = new Composition[nrtrainstoadd];
			for (int i = 0; i<nrtrainstoadd; i++){
				Train addnow = trainstoaddnow[i];
				addarriving[i]=new Composition(new ArrayList<Train>(){{add(addnow);}},arrivingcompositions.get(arrivingpositionsnow[i]).getArrivaltime(),arrivingcompositions.get(arrivingpositionsnow[i]).getDeparturetime(),arrivingcompositions.get(arrivingpositionsnow[i]).getArrivalDepartureSide());
				adddeparting[i]=new Composition(new ArrayList<Train>(){{add(addnow);}},leavingcompositions.get(departingpositionsnow[i]).getArrivaltime(),leavingcompositions.get(departingpositionsnow[i]).getDeparturetime(),leavingcompositions.get(departingpositionsnow[i]).getArrivalDepartureSide());
				arrivingcompositions.get(arrivingpositionsnow[i]).coupleComposition(addarriving[i]);
				//				System.out.println("hoi "+arrivingcompositions.get(arrivingpositionsnow[i]).getTrainList().size());
				leavingcompositions.get(departingpositionsnow[i]).coupleComposition(adddeparting[i]);
				//				System.out.println("hoi2 "+leavingcompositions.get(arrivingpositionsnow[i]).getTrainList().size());
			}

			//			for (int i = 0; i < arrivingcompositions.size(); i++){
			//				System.out.println("size: "+arrivingcompositions.get(i).getTrainList().size());
			//			}
			//			for (int i = 0; i < leavingcompositions.size(); i++){
			//				System.out.println("size2: "+leavingcompositions.get(i).getTrainList().size());
			//			}

			for (int i = 0; i<arrivingcompositions.size(); i++){
				for (int j = 0; j<arrivingcompositions.get(i).getTrainList().size(); j++){
					if (arrivingcompositions.get(i).getTrainList().get(j).getInterchangeable() > 0){
						arrivingcompositions.get(i).getTrainList().get(j).setType(arrivingcompositions.get(i).getTrainList().get(j).getInterchangeable()+3);
					}
				}
			}
			for (int i = 0; i<leavingcompositions.size(); i++){
				for (int j = 0; j<leavingcompositions.get(i).getTrainList().size(); j++){
					if (leavingcompositions.get(i).getTrainList().get(j).getInterchangeable() > 0){
						leavingcompositions.get(i).getTrainList().get(j).setType(leavingcompositions.get(i).getTrainList().get(j).getInterchangeable()+3);
					}
				}
			}

			double y = 0;
			boolean solutionfound = false;
			while (solutionfound == false){ // &&y<50
				System.out.println("Iteration "+y);
				Matching onzeMatching = new Matching(arrivingcompositions, leavingcompositions, y);
				System.gc();
				if (onzeMatching.returnSolved() == false){
					solutionfound = false;
					break;
				}
				else{
					System.out.println("Feasible matching found");
					boolean[][] z = onzeMatching.getZ();
					ArrayList<Block> arrivingblocks = onzeMatching.getArrivingBlockList();
					ArrayList<Block> departingblocks = onzeMatching.getDepartingBlockList();
					ArrayList<FinalBlock> finalcompositionblocks = new ArrayList<>();
					for (int i = 0; i<z.length; i++){
						for (int j = 0; j<z[0].length; j++){
							if (z[i][j]==true){
								finalcompositionblocks.add(new FinalBlock(arrivingblocks.get(i).getTrainList(), arrivingblocks.get(i).getArrivaltime(), departingblocks.get(j).getDeparturetime(), arrivingblocks.get(i).getOriginComposition(), departingblocks.get(j).getOriginComposition(), arrivingblocks.get(i).getOriginComposition().getArrivalDepartureSide(), departingblocks.get(j).getOriginComposition().getArrivalDepartureSide(), arrivingblocks.get(i).getCutPosition1(), arrivingblocks.get(i).getCutPosition2(), departingblocks.get(j).getCutPosition1(), departingblocks.get(j).getCutPosition2()));
								//throw exception if blocks not compatible in time after all or if arrivaltime or departure time is not within range 0 and 1
								if (finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()+onzeMatching.getc() +finalcompositionblocks.get(finalcompositionblocks.size()-1).getTotalServiceTime()>finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime() ){
									throw new MisMatchException("Arrivalblock "+i+" and departureblock "+j+" are not compatible in time after all in Main");
								}	
							}
						}
					}
					Todo6 jobshop;
					Parking5 parking;
					for (int i = 1; i<=8; i++){
//						a += 1;
//						System.out.println(a);
						for(int p = 0; p<tracks.length; p++){
							for(int j = 0; j<60*24; j++){
								tracks[p].setFreeBusyTime(j);
							}
						}
						jobshop = null;
						parking = null;
						jobshop = new Todo6(tracks,arrivingcompositions,leavingcompositions,finalcompositionblocks,i);
						if (jobshop.getFeasible()){
							System.out.println("Feasible jobshop option "+i);
							ArrayList<Event> events = jobshop.getEvents();
							for (int j=1; j<=nrparkings; j++){
								parking = new Parking5(events,tracks,j);
								if (parking.getFeasible()){
									System.out.println("Feasible parking");
									solutionfound = true;
									break;
								}
							}
							if (solutionfound){
								break;
							}
							else{
								System.out.println("No feasible parking");
							}
						}
						else {
							System.out.println("No feasible jobshop option "+i);
						}
					}
					if (!solutionfound){
						jobshop = null;
						parking = null;
						for(int i = 0; i<tracks.length; i++){
							for(int j = 0; j<60*24; j++){
								tracks[i].setFreeBusyTime(j);
							}
						}
						finalcompositionblocks = null;
						System.gc();
						y = y+1;
					}
				}
			}




			//					double y=4;
			//						Matching onzeMatching = new Matching(arrivingcompositions, leavingcompositions, y);
			//						boolean[][] z = onzeMatching.getZ();
			//			
			//						ArrayList<Block> arrivingblocks = onzeMatching.getArrivingBlockList();
			//						ArrayList<Block> departingblocks = onzeMatching.getDepartingBlockList();
			//						ArrayList<FinalBlock> finalcompositionblocks = new ArrayList<>();
			//						for (int i = 0; i<z.length; i++){
			//							for (int j = 0; j<z[0].length; j++){
			//								if (z[i][j]==true){
			//									//									System.out.println("z("+i+","+j+") = "+z[i][j]);
			//									//									System.out.println("Arriving Block: "+i);
			//									//									System.out.println("Trainlist: ");
			//									//									arrivingblocks.get(i).printTrains();
			//									//									System.out.println("Arriving time: "+arrivingblocks.get(i).getOriginComposition().getArrivaltime());
			//									//									System.out.println("Departing Block: "+j);
			//									//									System.out.println("Trainlist: ");
			//									//									departingblocks.get(j).printTrains();
			//									//									System.out.println("Departing time: "+departingblocks.get(j).getOriginComposition().getDeparturetime());
			//									finalcompositionblocks.add(new FinalBlock(arrivingblocks.get(i).getTrainList(), arrivingblocks.get(i).getArrivaltime(), departingblocks.get(j).getDeparturetime(), arrivingblocks.get(i).getOriginComposition(), departingblocks.get(j).getOriginComposition(), arrivingblocks.get(i).getOriginComposition().getArrivalDepartureSide(), departingblocks.get(j).getOriginComposition().getArrivalDepartureSide(), arrivingblocks.get(i).getCutPosition1(), arrivingblocks.get(i).getCutPosition2(), departingblocks.get(j).getCutPosition1(), departingblocks.get(j).getCutPosition2()));
			//									//						System.out.println(arrivingblocks.get(i).getArrivaltime());
			//									//throw exception if blocks not compatible in time after all or if arrivaltime or departure time is not within range 0 and 1
			//									if (finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()+onzeMatching.getc() +finalcompositionblocks.get(finalcompositionblocks.size()-1).getTotalServiceTime()>finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime() ){
			//										throw new MisMatchException("Arrivalblock "+i+" and departureblock "+j+" are not compatible in time after all in Main");
			//									}	
			//								}
			//							}
			//						}
			//			
			//			
			//			
			//						Todo5 JobShop = new Todo5(tracks, arrivingcompositions, leavingcompositions, finalcompositionblocks, 1);
			//						ArrayList<Event> events = JobShop.getEvents();
			//						System.gc();
			//			
			//						Parking5 ourparking5 = new Parking5(events,tracks,1);
			//						System.out.println("Did not park: "+ourparking5.getNotParked());
			//						if (ourparking5.getNotParked()>0){
			//							System.out.println("NO FEASIBLE PARKING SOLUTION FOUND");
			//						}
		} catch (IOException| MatrixIncompleteException |IndexOutOfBoundsException | MisMatchException | TrackNotFreeException | CloneNotSupportedException | MethodFailException e) {
			e.printStackTrace();
		}
	}

	//	public static Todo5 runJobShopParking(Track[] tracks, ArrayList<Composition> arrivingcompositions, ArrayList<Composition> leavingcompositions, ArrayList<FinalBlock> finalcompositionblocks) throws IOException, MethodFailException, TrackNotFreeException{
	//		Todo5 jobshop = null;
	//		for (int i = fromjobshop; i<=nrjobshops; i++){
	//			jobshop = new Todo5(tracks, arrivingcompositions, leavingcompositions, finalcompositionblocks, i);
	//			System.gc();
	//			if (jobshop.getFeasible()){
	//				fromjobshop = i+1;
	//				break;
	//			}
	//		}
	//		if (jobshop != null){
	//			if (!jobshop.getFeasible()){
	//				fromjobshop = 1;
	//				jobshop = null;
	//			}
	//			else{
	//				ArrayList<Event> events = jobshop.getEvents();
	//				Parking5 parking = runParking(tracks,events);
	//				if (parking == null){
	//					jobshop = runJobShopParking(tracks,arrivingcompositions,leavingcompositions, finalcompositionblocks);
	//				}
	//				else{
	//					System.out.println("FEASIBLE SOLUTION FOUND");
	//				}
	//			}
	//		}
	//		return jobshop;
	//	}
	//
	//	public static Parking5 runParking(Track[] tracks, ArrayList<Event> events) throws MethodFailException, TrackNotFreeException, IOException{
	//		Parking5 parking = null;
	//		for (int i = fromparking; i<=nrparkings; i++){
	//			parking = new Parking5(events,tracks,i);
	//			System.gc();
	//			if (parking.getFeasible()){
	//				break;
	//			}
	//		}
	//		if (parking != null){
	//			if (!parking.getFeasible()){
	//				parking = null;
	//			}
	//		}
	//		return parking;
	//	}

	public static ArrayList<Composition> setUpCompositions(int arrordep, Train[] trains1, Matrix compositiondata, Matrix compositiondata3){
		int abcd = 0;
		ArrayList<Composition> compositions = new ArrayList<>();
		try {
			for(int i = 0; i<compositiondata3.getNrRows();i++){
				if(compositiondata3.getElement(i,1)==arrordep){
					ArrayList<Train> templist = new ArrayList<>();
					for(int j = 0; j<compositiondata.getNrRows(); j++){
						if(compositiondata3.getElement(i, 0)==compositiondata.getElement(j,0)){
							templist.add(trains1[abcd]);
							abcd += 1;
						}
					}
					compositions.add(new Composition(templist, (int) compositiondata3.getElement(i, 3)));
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return compositions;
	}

	public static ArrayList<Double> setUpTimes(int arrordep, Matrix compositiondata3){
		ArrayList<Double> times = new ArrayList<>();
		for (int i = 0; i<compositiondata3.getNrRows();i++){
			if(compositiondata3.getElement(i,1)==arrordep){
				times.add(compositiondata3.getElement(i,2));
			}
		}
		return times;
	}

	public static ArrayList<Track> setUpTracks(int arrordep, Track[] tracks1, Matrix compositiondata3){
		ArrayList<Track> arrordeptracks = new ArrayList<>();
		for (int i = 0; i<compositiondata3.getNrRows();i++){
			if(compositiondata3.getElement(i, 1)==arrordep){
				if(compositiondata3.getElement(i, 3)==0){
					arrordeptracks.add(tracks1[0]);
				}
				else if(compositiondata3.getElement(i, 3)==1){
					arrordeptracks.add(tracks1[tracks1.length-1]);
				}
			}
		}
		return arrordeptracks;
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
						trains[abcd] = new Train(abcd+1, (int) compositiondata.getElement(i, 1), (int) compositiondata.getElement(i, 2), (int) compositiondata.getElement(i,4), (compositiondata.getElement(i,5)==1.0), (compositiondata.getElement(i,6)==1.0), (compositiondata.getElement(i,7)==1.0), (compositiondata.getElement(i,8)==1.0));
						//trains[abcd] = new Train(abcd+1, (int) compositiondata.getElement(i, 1), (int) compositiondata.getElement(i, 2), length, (compositiondata.getElement(i,4)== 1.0), (compositiondata.getElement(i,5)== 1.0), (compositiondata.getElement(i,6)== 1.0), (compositiondata.getElement(i,7)== 1.0), (compositiondata.getElement(i,8)== 1.0));
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
			tracks[i]= new Track(labelname,(int) trackdata.getElement(i, 2), (int) trackdata.getElement(i, 3), (int) trackdata.getElement(i, 4),  (int) trackdata.getElement(i, 5), (int) trackdata.getElement(i, 6), (int) trackdata.getElement(i, 7), (int) trackdata.getElement(i,8));
		}

		return tracks;
	}
}

//voor testen inlezen:

/*Matrix test = trackdata;
                        System.out.println(test.getNrRows());
                        for (int i=0; i<test.getNrRows(); i++){
                                System.out.println("");
                                for (int j=0; j<test.getNrColumns();j++){
                                        System.out.print(test.getElement(i, j)+" ");
                                }
                        }
 */