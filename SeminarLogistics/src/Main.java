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

	public static void main(String args[])
	{
		try {
			//int user = 1; //Friso
			int user = 2; //Floor
			//int user = 3; //Robin
			//int user = 4; //Philip

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
			for (int i = 0; i<tracks.length; i++){
				System.out.println(tracks[i].getLabel()+" has parkpos: "+tracks[i].getParktrain()+" and length "+tracks[i].getTracklength()+" and maxbackward "+tracks[i].getMaxDriveBackLength()); 
			}
			Train[] trainsarr = readInTrains(0, compositiondata, compositiondata2, compositiondata3);
			Train[] trainsdep = readInTrains(1, compositiondata, compositiondata2, compositiondata3);

						new Schedule(trainsarr);
			
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

						double y = 4;
						Matching onzeMatching = new Matching(arrivingcompositions, leavingcompositions, y);
						boolean[][] z = onzeMatching.getZ();
			
						ArrayList<Block> arrivingblocks = onzeMatching.getArrivingBlockList();
						ArrayList<Block> departingblocks = onzeMatching.getDepartingBlockList();
						ArrayList<FinalBlock> finalcompositionblocks = new ArrayList<>();
						for (int i = 0; i<z.length; i++){
							for (int j = 0; j<z[0].length; j++){
								if (z[i][j]==true){
//									System.out.println("z("+i+","+j+") = "+z[i][j]);
									finalcompositionblocks.add(new FinalBlock(arrivingblocks.get(i).getTrainList(), arrivingblocks.get(i).getArrivaltime(), departingblocks.get(j).getDeparturetime(), arrivingblocks.get(i).getOriginComposition(), departingblocks.get(j).getOriginComposition(), arrivingblocks.get(i).getOriginComposition().getArrivalDepartureSide(), departingblocks.get(j).getOriginComposition().getArrivalDepartureSide(), arrivingblocks.get(i).getCutPosition1(), arrivingblocks.get(i).getCutPosition2(), departingblocks.get(j).getCutPosition1(), departingblocks.get(j).getCutPosition2()));
									//						System.out.println(arrivingblocks.get(i).getArrivaltime());
									//throw exception if blocks not compatible in time after all or if arrivaltime or departure time is not within range 0 and 1
									if (finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()+onzeMatching.getc() +finalcompositionblocks.get(finalcompositionblocks.size()-1).getTotalServiceTime()>finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime() ){
										throw new MisMatchException("Arrivalblock "+i+" and departureblock "+j+" are not compatible in time after all in Main");
									}	
								}
							}
						}
			
			
						Todo5 JobShop = new Todo5(tracks, arrivingcompositions, leavingcompositions, finalcompositionblocks, 1);
						ArrayList<Event> events = JobShop.getEvents();
						System.gc();
			
						Parking5 ourparking5 = new Parking5(events,tracks,1);
						System.out.println("Did not park: "+ourparking5.getNotParked());
						if (ourparking5.getNotParked()>0){
							System.out.println("NO FEASIBLE PARKING SOLUTION FOUND");
						}

			


//			double y = 0;
//
//			new Schedule(trainsarr);
//
//			ArrayList<Composition> arrivingcompositions = setUpCompositions(0, trainsarr, compositiondata, compositiondata3);
//			ArrayList<Double> arrivingtimes = setUpTimes(0, compositiondata3);
//			ArrayList<Track> arrivingtracks = setUpTracks(0, tracks, compositiondata3);
//
//			//ArrayList<Composition> arrivingcompositionswitharrtime = new ArrayList<>();
//			for (int i = 0; i< arrivingcompositions.size();i++){
//				if (arrivingtimes.get(i)<begintime){
//					arrivingcompositions.get(i).setArrivaltime(arrivingtimes.get(i)+1-begintime);
//				}
//				else{
//					arrivingcompositions.get(i).setArrivaltime(arrivingtimes.get(i)-begintime);
//				}
//			}
//
//			ArrayList<Composition> leavingcompositions = setUpCompositions(1, trainsdep, compositiondata, compositiondata3); //TODO: THIS SHOULD ALSO BE A DUPLICATE OF THE OBJECTS OTHERWISE THE TIMES AND POSITION OF A TRAIN IS BEING CHANGES IN ARRIVINGCOMPOSITIONS!!!!
//			ArrayList<Double> leavingtimes = setUpTimes(1, compositiondata3);
//			ArrayList<Track> leavingtracks = setUpTracks(1, tracks, compositiondata3);
//
//			for (int i = 0; i< leavingcompositions.size();i++){
//				if (leavingtimes.get(i)<begintime){
//					leavingcompositions.get(i).setDeparturetime(leavingtimes.get(i)+1-begintime);
//				}
//				else{
//					leavingcompositions.get(i).setDeparturetime(leavingtimes.get(i)-begintime);
//				}
//			}
//
//			//				ArrayList<Composition> arrival1 = new ArrayList<>();
//			//				ArrayList<Composition> departure1 = new ArrayList<>();
//			//				for (int i = 0; i<6; i++){
//			//					arrival1.add(arrivingcompositions.get(i));
//			//				}
//			//				for (int j = 0; j<7; j++){
//			//					departure1.add(leavingcompositions.get(j));
//			//				}
//
//			//			for (int i = 0; i<arrivingcompositions.size(); i++){
//			//				System.out.println(arrivingcompositions.get(i).getArrivaltime());
//			//			}
//
//			Matching onzeMatching = new Matching(arrivingcompositions, leavingcompositions, y);
//			boolean feas = onzeMatching.returnSolved();
//			boolean[][] z = onzeMatching.getZ();
//
//			ArrayList<Block> arrivingblocks = onzeMatching.getArrivingBlockList();
//			ArrayList<Block> departingblocks = onzeMatching.getDepartingBlockList();
//			ArrayList<FinalBlock> finalcompositionblocks = new ArrayList<>();
//			for (int i = 0; i<z.length; i++){
//				for (int j = 0; j<z[0].length; j++){
//					if (z[i][j]==true){
//						//System.out.println("z("+i+","+j+") = "+z[i][j]);
//						finalcompositionblocks.add(new FinalBlock(arrivingblocks.get(i).getTrainList(), arrivingblocks.get(i).getArrivaltime(), departingblocks.get(j).getDeparturetime(), arrivingblocks.get(i).getOriginComposition(), departingblocks.get(j).getOriginComposition(), arrivingblocks.get(i).getOriginComposition().getArrivalDepartureSide(), departingblocks.get(j).getOriginComposition().getArrivalDepartureSide(), arrivingblocks.get(i).getCutPosition1(), arrivingblocks.get(i).getCutPosition2(), departingblocks.get(j).getCutPosition1(), departingblocks.get(j).getCutPosition2()));
//						//						System.out.println(arrivingblocks.get(i).getArrivaltime());
//						//throw exception if blocks not compatible in time after all or if arrivaltime or departure time is not within range 0 and 1
//						if (finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()+onzeMatching.getc() +finalcompositionblocks.get(finalcompositionblocks.size()-1).getTotalServiceTime()>finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime() ){
//							throw new MisMatchException("Arrivalblock "+i+" and departureblock "+j+" are not compatible in time after all in Main");
//						}	
//					}
//				}
//			}
//
//
//			Todo5 JobShop = new Todo5(tracks, arrivingcompositions, leavingcompositions, finalcompositionblocks, 1);
//			boolean feasjobshop = JobShop.getFeasible(); //TODO: create method, do not throw error
//			while (!feasjobshop){
//				y = y+1;
//				onzeMatching = new Matching(arrivingcompositions, leavingcompositions, y);
//				boolean quit = false;
//				z = onzeMatching.getZ();
//
//				arrivingblocks = onzeMatching.getArrivingBlockList();
//				departingblocks = onzeMatching.getDepartingBlockList();
//				finalcompositionblocks = new ArrayList<>();
//				for (int i = 0; i<z.length; i++){
//					for (int j = 0; j<z[0].length; j++){
//						if (z[i][j]==true){
//							//System.out.println("z("+i+","+j+") = "+z[i][j]);
//							finalcompositionblocks.add(new FinalBlock(arrivingblocks.get(i).getTrainList(), arrivingblocks.get(i).getArrivaltime(), departingblocks.get(j).getDeparturetime(), arrivingblocks.get(i).getOriginComposition(), departingblocks.get(j).getOriginComposition(), arrivingblocks.get(i).getOriginComposition().getArrivalDepartureSide(), departingblocks.get(j).getOriginComposition().getArrivalDepartureSide(), arrivingblocks.get(i).getCutPosition1(), arrivingblocks.get(i).getCutPosition2(), departingblocks.get(j).getCutPosition1(), departingblocks.get(j).getCutPosition2()));
//							//						System.out.println(arrivingblocks.get(i).getArrivaltime());
//							//throw exception if blocks not compatible in time after all or if arrivaltime or departure time is not within range 0 and 1
//							if (finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()+onzeMatching.getc() +finalcompositionblocks.get(finalcompositionblocks.size()-1).getTotalServiceTime()>finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime() ){
//								throw new MisMatchException("Arrivalblock "+i+" and departureblock "+j+" are not compatible in time after all in Main");
//							}	
//						}
//					}
//				}
//				JobShop = new Todo5(tracks, arrivingcompositions, leavingcompositions, finalcompositionblocks,0);
//				feasjobshop = JobShop.getFeasible(); //TODO: create method, do not throw error
//				if (y > 30 && !feasjobshop){
//					quit = true;
//					break;
//				}
//			}
//
//			if (!feasjobshop){
//				System.out.println("NO FEASIBLE JOBSHOP SOLUTION FOUND");
//			}
//			if (feasjobshop){
//				ArrayList<Event> events = JobShop.getEvents();
//				System.gc();
//
//				//			for(int i = 0; i<events.size(); i++){
//				//				System.out.println("Event: " + events.get(i) + " Relatedevent: " + events.get(i).getRelatedEvent() + " Starttime: " + events.get(i).getStarttime() + " Endtime: " + events.get(i).getEndtime() + " Block " + events.get(i).getEventblock() + " Type " + events.get(i).getType() + " FinalType " + events.get(i).getFinalType() + " Sideend "+ events.get(i).getSideend() + " Sidestart " + events.get(i).getSidestart() + "\n");
//				//			}
//
//				//Parking3 ourparking3 = new Parking3(events, tracks);
//				Parking5 ourparking5 = new Parking5(events,tracks,1);//1: maxdrivebackorder
//				System.out.println("Did not park: "+ourparking5.getNotParked());
//				if (ourparking5.getNotParked()>0){
//					System.out.println("NO FEASIBLE PARKING SOLUTION FOUND");
//				}
//			}
//						for (int i = 0; i< arrivingcompositions.size();i++){
//				if (arrivingtimes.get(i)<begintime){
//					arrivingcompositions.get(i).setArrivaltime(arrivingtimes.get(i)+1-begintime);
//				}
//				else{
//					arrivingcompositions.get(i).setArrivaltime(arrivingtimes.get(i)-begintime);
//				}
//			}
//
//			ArrayList<Composition> leavingcompositions = setUpCompositions(1, trainsdep, compositiondata, compositiondata3); //TODO: THIS SHOULD ALSO BE A DUPLICATE OF THE OBJECTS OTHERWISE THE TIMES AND POSITION OF A TRAIN IS BEING CHANGES IN ARRIVINGCOMPOSITIONS!!!!
//			ArrayList<Double> leavingtimes = setUpTimes(1, compositiondata3);
//			ArrayList<Track> leavingtracks = setUpTracks(1, tracks, compositiondata3);
//
//			for (int i = 0; i< leavingcompositions.size();i++){
//				if (leavingtimes.get(i)<begintime){
//					leavingcompositions.get(i).setDeparturetime(leavingtimes.get(i)+1-begintime);
//				}
//				else{
//					leavingcompositions.get(i).setDeparturetime(leavingtimes.get(i)-begintime);
//				}
//			}
//
//			//				ArrayList<Composition> arrival1 = new ArrayList<>();
//			//				ArrayList<Composition> departure1 = new ArrayList<>();
//			//				for (int i = 0; i<6; i++){
//			//					arrival1.add(arrivingcompositions.get(i));
//			//				}
//			//				for (int j = 0; j<7; j++){
//			//					departure1.add(leavingcompositions.get(j));
//			//				}
//
//			//			for (int i = 0; i<arrivingcompositions.size(); i++){
//			//				System.out.println(arrivingcompositions.get(i).getArrivaltime());
//			//			}
//
//			Matching onzeMatching = new Matching(arrivingcompositions, leavingcompositions, y);
//			boolean feas = onzeMatching.returnSolved();
//			boolean[][] z = onzeMatching.getZ();
//
//			ArrayList<Block> arrivingblocks = onzeMatching.getArrivingBlockList();
//			ArrayList<Block> departingblocks = onzeMatching.getDepartingBlockList();
//			ArrayList<FinalBlock> finalcompositionblocks = new ArrayList<>();
//			for (int i = 0; i<z.length; i++){
//				for (int j = 0; j<z[0].length; j++){
//					if (z[i][j]==true){
//						//System.out.println("z("+i+","+j+") = "+z[i][j]);
//						finalcompositionblocks.add(new FinalBlock(arrivingblocks.get(i).getTrainList(), arrivingblocks.get(i).getArrivaltime(), departingblocks.get(j).getDeparturetime(), arrivingblocks.get(i).getOriginComposition(), departingblocks.get(j).getOriginComposition(), arrivingblocks.get(i).getOriginComposition().getArrivalDepartureSide(), departingblocks.get(j).getOriginComposition().getArrivalDepartureSide(), arrivingblocks.get(i).getCutPosition1(), arrivingblocks.get(i).getCutPosition2(), departingblocks.get(j).getCutPosition1(), departingblocks.get(j).getCutPosition2()));
//						//						System.out.println(arrivingblocks.get(i).getArrivaltime());
//						//throw exception if blocks not compatible in time after all or if arrivaltime or departure time is not within range 0 and 1
//						if (finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()+onzeMatching.getc() +finalcompositionblocks.get(finalcompositionblocks.size()-1).getTotalServiceTime()>finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime() ){
//							throw new MisMatchException("Arrivalblock "+i+" and departureblock "+j+" are not compatible in time after all in Main");
//						}	
//					}
//				}
//			}
//
//
//			Todo5 JobShop = new Todo5(tracks, arrivingcompositions, leavingcompositions, finalcompositionblocks, 1);
//			boolean feasjobshop = JobShop.getFeasible(); //TODO: create method, do not throw error
//			while (!feasjobshop){
//				y = y+1;
//				onzeMatching = new Matching(arrivingcompositions, leavingcompositions, y);
//				boolean quit = false;
//				z = onzeMatching.getZ();
//
//				arrivingblocks = onzeMatching.getArrivingBlockList();
//				departingblocks = onzeMatching.getDepartingBlockList();
//				finalcompositionblocks = new ArrayList<>();
//				for (int i = 0; i<z.length; i++){
//					for (int j = 0; j<z[0].length; j++){
//						if (z[i][j]==true){
//							//System.out.println("z("+i+","+j+") = "+z[i][j]);
//							finalcompositionblocks.add(new FinalBlock(arrivingblocks.get(i).getTrainList(), arrivingblocks.get(i).getArrivaltime(), departingblocks.get(j).getDeparturetime(), arrivingblocks.get(i).getOriginComposition(), departingblocks.get(j).getOriginComposition(), arrivingblocks.get(i).getOriginComposition().getArrivalDepartureSide(), departingblocks.get(j).getOriginComposition().getArrivalDepartureSide(), arrivingblocks.get(i).getCutPosition1(), arrivingblocks.get(i).getCutPosition2(), departingblocks.get(j).getCutPosition1(), departingblocks.get(j).getCutPosition2()));
//							//						System.out.println(arrivingblocks.get(i).getArrivaltime());
//							//throw exception if blocks not compatible in time after all or if arrivaltime or departure time is not within range 0 and 1
//							if (finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()<0 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime()>1 || finalcompositionblocks.get(finalcompositionblocks.size()-1).getArrivaltime()+onzeMatching.getc() +finalcompositionblocks.get(finalcompositionblocks.size()-1).getTotalServiceTime()>finalcompositionblocks.get(finalcompositionblocks.size()-1).getDeparturetime() ){
//								throw new MisMatchException("Arrivalblock "+i+" and departureblock "+j+" are not compatible in time after all in Main");
//							}	
//						}
//					}
//				}
//				JobShop = new Todo5(tracks, arrivingcompositions, leavingcompositions, finalcompositionblocks,0);
//				feasjobshop = JobShop.getFeasible(); //TODO: create method, do not throw error
//				if (y > 30 && !feasjobshop){
//					quit = true;
//					break;
//				}
//			}
//
//			if (!feasjobshop){
//				System.out.println("NO FEASIBLE JOBSHOP SOLUTION FOUND");
//			}
//			if (feasjobshop){
//				ArrayList<Event> events = JobShop.getEvents();
//				System.gc();
//
//				//			for(int i = 0; i<events.size(); i++){
//				//				System.out.println("Event: " + events.get(i) + " Relatedevent: " + events.get(i).getRelatedEvent() + " Starttime: " + events.get(i).getStarttime() + " Endtime: " + events.get(i).getEndtime() + " Block " + events.get(i).getEventblock() + " Type " + events.get(i).getType() + " FinalType " + events.get(i).getFinalType() + " Sideend "+ events.get(i).getSideend() + " Sidestart " + events.get(i).getSidestart() + "\n");
//				//			}
//
//				//Parking3 ourparking3 = new Parking3(events, tracks);
//				Parking5 ourparking5 = new Parking5(events,tracks,1);//1: maxdrivebackorder
//				System.out.println("Did not park: "+ourparking5.getNotParked());
//				if (ourparking5.getNotParked()>0){
//					System.out.println("NO FEASIBLE PARKING SOLUTION FOUND");
//				}
//			}
		} catch (IOException| MatrixIncompleteException |IndexOutOfBoundsException | MisMatchException | TrackNotFreeException | CloneNotSupportedException | MethodFailException e) {
			e.printStackTrace();
		}
	} 

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
						trains[abcd] = new Train(abcd+1, (int) compositiondata.getElement(i, 1), (int) compositiondata.getElement(i, 2), (int) compositiondata.getElement(i,3), (compositiondata.getElement(i,5)==1.0), (compositiondata.getElement(i,6)==1.0), (compositiondata.getElement(i,7)==1.0), (compositiondata.getElement(i,8)==1.0));
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