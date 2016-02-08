import java.io.IOException;
import java.util.ArrayList;

import ilog.concert.*;
import ilog.cplex.*;

public class Matching {
	public static final double c = 0.1;
	public static final int minplatformlength = 200;
	public static final int M = 1000;

	private ArrayList<Block> arrivingblocklist; //set I
	private ArrayList<Block> departingblocklist; //set J
	private boolean[][] z_ij; //matchings i and j

	private ArrayList<Composition> arrivingcompositions;
	private ArrayList<Composition> departingcompositions;
	private CompatibleArrivingBlocks[] compatiblearrivingblocksset;
	private CompatibleDepartingBlocks[] compatibledepartingblocksset;
	private Arcs[] arcsarrivingcompositionsset;
	private Arcs[] arcsdepartingcompositionsset;
	private ArcsOutgoing[][] arcsoutofnodearrivingcompositionsset;
	private ArcsOutgoing[][] arcsoutofnodedepartingcompositionsset;
	private ArcsIncoming[][] arcsintonodearrivingcompositionsset;
	private ArcsIncoming[][] arcsintonodedepartingcompositionsset;
	private IntermediateNodes[] intermediatenodesarrivingcompositionsset;
	private IntermediateNodes[] intermediatenodesdepartingcompositionsset;

	/**
	 * Constructor method for a matching
	 * 
	 * T_a: 	arrivingcompositions
	 * T_d: 	departingcompositions
	 * I:		arrivingblocklist
	 * J:		departingblocklist
	 * I_j:		compatiblearrivingblocksset[j]
	 * J_i: 	compatibledepartingblockset[i]
	 * A^t_a: 	arcsarrivingcompositionsset[t] (merge with A^t_d)
	 * A^t_d:	arcsdepartingcompositionsset[t] (merge with A^t_a)
	 * A_h^t+_a:arcsoutofnodearrivingcompositionsset[t][h] (merge with A_h^t+_d)
	 * A_h^t+_d:arcsoutofnodedepartingcompositionsset[t][h] (merge with A_h^t+_a)
	 * A_h^t-_a:arcsintonodearrivingcompositionsset[t][h] (merge with A_h^t-_d)
	 * A_h^t-_d:arcsintonodedepartingcompositionsset[t][h] (merge with A_h^t-_a)
	 * C^-_t_a:	intermediatenodesarrivingcompositionsset[t] (merge with C^-_t_d)
	 * C^-_t_d:	intermediatenodesdepartingcompositionsset[t] (merge with C^-_t_a)
	 * 
	 * @param arrivingcompositions
	 * @param departingcompositions
	 * @throws IndexOutOfBoundsException
	 * @throws MisMatchException
	 * @throws TrackNotFreeException
	 * @throws IOException
	 * @throws CloneNotSupportedException
	 */
	public Matching(ArrayList<Composition> arrivingcompositions /*set T_a*/, ArrayList<Composition> departingcompositions /*set T_d*/) throws IndexOutOfBoundsException, MisMatchException, TrackNotFreeException, IOException, CloneNotSupportedException{
		//set I
		arrivingblocklist = makeblocks(arrivingcompositions);

		//set J
		departingblocklist = makeblocks(departingcompositions);

		//set T_a
		arrivingcompositions = this.arrivingcompositions;

		int nrarrivingblocks = arrivingblocklist.size();
		int nrdepartingblocks = departingblocklist.size();
		int nrarrivingcompositions = arrivingcompositions.size();
		int nrdepartingcompositions = departingcompositions.size();

		//set J_i at index i for every i in I
		compatibledepartingblocksset = new CompatibleDepartingBlocks[nrarrivingblocks];
		for (int i = 0; i<nrarrivingblocks;i++){
			compatibledepartingblocksset[i] = new CompatibleDepartingBlocks(arrivingblocklist.get(i),departingblocklist);
		}

		//set I_j at index j for every j in J
		compatiblearrivingblocksset = new CompatibleArrivingBlocks[nrdepartingblocks];
		for (int i = 0; i<nrdepartingblocks;i++){
			compatiblearrivingblocksset[i] = new CompatibleArrivingBlocks(departingblocklist.get(i),arrivingblocklist);
		}

		//Set A_h^t+_a at index t,h for every t in T_a
		arcsoutofnodearrivingcompositionsset = new ArcsOutgoing[nrarrivingcompositions][3]; //max 3 trains per composition, so max 4 nodes
		for (int i = 0; i<nrarrivingcompositions; i++){
			if (arrivingcompositions.get(i).getSize() == 1){
				arcsoutofnodearrivingcompositionsset[i][0]=new ArcsOutgoing(arrivingcompositions.get(i), -1);
			}
			else if (arrivingcompositions.get(i).getSize()==2){
				arcsoutofnodearrivingcompositionsset[i][0]=new ArcsOutgoing(arrivingcompositions.get(i), -1);
				arcsoutofnodearrivingcompositionsset[i][1]=new ArcsOutgoing(arrivingcompositions.get(i), 0);
			}
			else if (arrivingcompositions.get(i).getSize()==3){
				arcsoutofnodearrivingcompositionsset[i][0]=new ArcsOutgoing(arrivingcompositions.get(i), -1);
				arcsoutofnodearrivingcompositionsset[i][1]=new ArcsOutgoing(arrivingcompositions.get(i), 0);
				arcsoutofnodearrivingcompositionsset[i][2]=new ArcsOutgoing(arrivingcompositions.get(i), 1);
			}
			else{
				throw new IOException("Arrivingcompositions ("+i+" is of size "+arrivingcompositions.get(i).getSize()+ "and max is 3");
			}
		}

		//Set A_h^t+_d at index t,h for every t in T_d
		arcsoutofnodedepartingcompositionsset = new ArcsOutgoing[nrdepartingcompositions][3]; //max 3 trains per composition, so max 4 nodes
		for (int i = 0; i<nrdepartingcompositions; i++){
			if (departingcompositions.get(i).getSize() == 1){
				arcsoutofnodedepartingcompositionsset[i][0]=new ArcsOutgoing(departingcompositions.get(i), -1);
			}
			else if (departingcompositions.get(i).getSize()==2){
				arcsoutofnodedepartingcompositionsset[i][0]=new ArcsOutgoing(departingcompositions.get(i), -1);
				arcsoutofnodedepartingcompositionsset[i][1]=new ArcsOutgoing(departingcompositions.get(i), 0);
			}
			else if (departingcompositions.get(i).getSize()==3){
				arcsoutofnodedepartingcompositionsset[i][0]=new ArcsOutgoing(departingcompositions.get(i), -1);
				arcsoutofnodedepartingcompositionsset[i][1]=new ArcsOutgoing(departingcompositions.get(i), 0);
				arcsoutofnodedepartingcompositionsset[i][2]=new ArcsOutgoing(departingcompositions.get(i), 1);
			}
			else{
				throw new IOException("Arrivingcompositions ("+i+" is of size "+arrivingcompositions.get(i).getSize()+ "and max is 3");
			}
		}

		//Set A_h^t-_a at index t,h for every t in T_a
		arcsintonodearrivingcompositionsset = new ArcsIncoming[nrarrivingcompositions][3]; //max 3 trains per composition, so max 4 nodes
		for (int i = 0; i<nrarrivingcompositions; i++){
			if (arrivingcompositions.get(i).getSize() == 1){
				arcsintonodearrivingcompositionsset[i][0]=new ArcsIncoming(arrivingcompositions.get(i), 0);
			}
			else if (arrivingcompositions.get(i).getSize()==2){
				arcsintonodearrivingcompositionsset[i][0]=new ArcsIncoming(arrivingcompositions.get(i), 0);
				arcsintonodearrivingcompositionsset[i][1]=new ArcsIncoming(arrivingcompositions.get(i), 1);
			}
			else if (arrivingcompositions.get(i).getSize()==3){
				arcsintonodearrivingcompositionsset[i][0]=new ArcsIncoming(arrivingcompositions.get(i), 0);
				arcsintonodearrivingcompositionsset[i][1]=new ArcsIncoming(arrivingcompositions.get(i), 1);
				arcsintonodearrivingcompositionsset[i][2]=new ArcsIncoming(arrivingcompositions.get(i), 2);
			}
			else{
				throw new IOException("Arrivingcompositions ("+i+" is of size "+arrivingcompositions.get(i).getSize()+ "and max is 3");
			}
		}

		//Set A_h^t-_d at index t,h for every t in T_d
		arcsintonodedepartingcompositionsset = new ArcsIncoming[nrdepartingcompositions][3]; //max 3 trains per composition, so max 4 nodes
		for (int i = 0; i<nrdepartingcompositions; i++){
			if (departingcompositions.get(i).getSize() == 1){
				arcsintonodedepartingcompositionsset[i][0]=new ArcsIncoming(departingcompositions.get(i), 0);
			}
			else if (departingcompositions.get(i).getSize()==2){
				arcsintonodedepartingcompositionsset[i][0]=new ArcsIncoming(departingcompositions.get(i), 0);
				arcsintonodedepartingcompositionsset[i][1]=new ArcsIncoming(departingcompositions.get(i), 1);
			}
			else if (departingcompositions.get(i).getSize()==3){
				arcsintonodedepartingcompositionsset[i][0]=new ArcsIncoming(departingcompositions.get(i), 0);
				arcsintonodedepartingcompositionsset[i][1]=new ArcsIncoming(departingcompositions.get(i), 1);
				arcsintonodedepartingcompositionsset[i][2]=new ArcsIncoming(departingcompositions.get(i), 2);
			}
			else{
				throw new IOException("Arrivingcompositions ("+i+" is of size "+arrivingcompositions.get(i).getSize()+ "and max is 3");
			}
		}

		//Set C^-_t_a at index t for every t in T_a
		intermediatenodesarrivingcompositionsset = new IntermediateNodes[nrarrivingcompositions];
		for (int i = 0; i<nrarrivingcompositions; i++){
			intermediatenodesarrivingcompositionsset[i] = new IntermediateNodes(arrivingcompositions.get(i));
		}

		//Set C^-_t_d at index t for every t in T_d
		intermediatenodesdepartingcompositionsset = new IntermediateNodes[nrdepartingcompositions];
		for (int i = 0; i<nrdepartingcompositions; i++){
			intermediatenodesdepartingcompositionsset[i] = new IntermediateNodes(departingcompositions.get(i));
		}

		//Set A^t_a at index t for every t in T_a
		arcsarrivingcompositionsset = new Arcs[nrarrivingcompositions];
		for (int i=0; i<nrarrivingcompositions; i++){
			arcsarrivingcompositionsset[i] = new Arcs(arrivingcompositions.get(i));
		}

		//Set A^t_d at index t for every t in T_d
		arcsdepartingcompositionsset = new Arcs[nrdepartingcompositions];
		for (int i=0; i<nrdepartingcompositions; i++){
			arcsdepartingcompositionsset[i] = new Arcs(departingcompositions.get(i));
		}

	}

	/**
	 * Returns arriving block list
	 * 
	 * @return arrivingblocklist
	 */
	public ArrayList<Block> getArrivingBlockList(){
		return arrivingblocklist;
	}

	/**
	 * Returns departing block list
	 * 
	 * @return departingblocklist
	 */
	public ArrayList<Block> getDepartingBlockList(){
		return departingblocklist;
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


	public void model1(){
		int nrnodes = 4;
		int nrdepnodes = nrnodes-1;
		int Q = 1; //Penalty for splitting

		ArrayList<IloNumVar[][]> u = null; //TODO: WAAROM MAAK JE HIER EEN ARRAY VAN --> ARRAYLIST EN WAAROM TWEE DIMENSIES?
		ArrayList<IloLinearNumExpr[]> UsedArcsRow = null;
		ArrayList<IloLinearNumExpr[]> UsedArcsCol = null;

		try {	
			// define new model
			IloCplex cplex = new IloCplex();

			// variables

			for(int j = 0; j< arrivingcompositions.size(); j++){
				u.add(new IloNumVar[nrdepnodes][]);
				for(int i = 0; i < nrnodes; i++) {
					u.get(j)[i] = cplex.boolVarArray(nrnodes);
				}
			}
			
			//IloNumVar[] u = cplex.boolVarArray(arrivingblocklist.size());
			//IloNumVar[] v = cplex.boolVarArray(departingblocklist.size());

			IloNumVar[][] z = new IloNumVar[arrivingblocklist.size()][];
			for (int i = 0; i<arrivingblocklist.size(); i++){
				z[i] = cplex.boolVarArray(departingblocklist.size());
			}


			/* EXAMPLES
			 * 
			 * //Two dimensional x_ij
			 * IloNumVar [][] x = new IloNumVar[n][];
			 * for(int i<0;i<n.;i++){
			 * x[i] = cplex.numVarArray(m,0,Double.MAX_VALUE);
			 * }
			 * 
			 * boolean variables
			 * IloNumVar x = cplex.boolVar("X");
			 * IloNumVar y = cplex.boolVar("Y");
			 * 
			 * double variables
			 * IloNumVar x = cplex.boolVar(0,Double.MAX_VALUE,"X");
			 * IloNumVar y = cplex.numVar(0,Double.MAX_VALUE, "Y");
			 * */

			// expressions			
			for(int k = 0; k< arrivingcompositions.size(); k++){
				if(arrivingcompositions.get(k).getSize()==1){
					UsedArcsRow.add(new IloLinearNumExpr[nrnodes]);
					UsedArcsCol.add(new IloLinearNumExpr[nrdepnodes]);
					for(int j = 0; j<nrnodes; j++){
						UsedArcsRow.get(k)[j] = cplex.linearNumExpr();
						for(int i = 0; i<nrdepnodes; i++){
							if(j==3 && i==0){
								UsedArcsRow.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else{
								UsedArcsRow.get(k)[j].addTerm(0.0, u.get(k)[i][j]);
							}
						}
					}
					for(int j = 0; j<nrdepnodes; j++){
						UsedArcsCol.get(k)[j] = cplex.linearNumExpr();
						for(int i = 0; i<nrnodes; i++){
							if (i==3&&j==0){
								UsedArcsCol.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else{
								UsedArcsCol.get(k)[j].addTerm(0.0, u.get(k)[i][j]);
							}
						}
					}
				}
				else if(arrivingcompositions.get(k).getSize()==2){
					UsedArcsRow.add(new IloLinearNumExpr[nrnodes]);
					UsedArcsCol.add(new IloLinearNumExpr[nrdepnodes]);
					for(int j = 0; j<nrnodes; j++){
						UsedArcsRow.get(k)[j] = cplex.linearNumExpr();
						for(int i = 0; i<nrdepnodes; i++){
							if(j==3 && i==0){
								UsedArcsRow.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else if(((j==1||j==3)&&i==0)||(j==3&&i==1)){
								UsedArcsRow.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else{
								UsedArcsRow.get(k)[j].addTerm(0.0, u.get(k)[i][j]);
							}
						}
					}
					for(int j = 0; j<nrdepnodes; j++){
						UsedArcsCol.get(k)[j] = cplex.linearNumExpr();
						for(int i = 0; i<nrnodes; i++){
							if (i==3&&j==0){
								UsedArcsCol.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else if(((i==1||i==3)&&j==0)||(i==3&&j==1)){
								UsedArcsCol.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else{
								UsedArcsCol.get(k)[j].addTerm(0.0, u.get(k)[i][j]);
							}
						}
					}
				}
				else if(arrivingcompositions.get(k).getSize()==3){
					UsedArcsRow.add(new IloLinearNumExpr[nrnodes]);
					UsedArcsCol.add(new IloLinearNumExpr[nrdepnodes]);
					for(int j = 0; j<nrnodes; j++){
						UsedArcsRow.get(k)[j] = cplex.linearNumExpr();
						for(int i = 0; i<nrdepnodes; i++){
							if(j==3 && i==0){
								UsedArcsRow.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else if(((j==1||j==3)&&i==0)||(j==3&&i==1)){
								UsedArcsRow.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else if(((j==1||j==2||j==3)&&i==0)||((j==2||j==3)&&i==1)||(j==3&&i==2)){
								UsedArcsRow.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else{
								UsedArcsRow.get(k)[j].addTerm(0.0, u.get(k)[i][j]);
							}
						}
					}
					for(int j = 0; j<nrdepnodes; j++){
						UsedArcsCol.get(k)[j] = cplex.linearNumExpr();
						for(int i = 0; i<nrnodes; i++){
							if (i==3&&j==0){
								UsedArcsCol.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else if(((i==1||i==3)&&j==0)||(i==3&&j==1)){
								UsedArcsCol.get(k)[j].addTerm(1.0, u.get(k)[i][j]);
							}
							else if(((i==1||i==2||i==3)&&j==0)||((i==2||i==3)&&j==1)||(i==3&&j==2)){

							}
							else{
								UsedArcsCol.get(k)[j].addTerm(0.0, u.get(k)[i][j]);
							}
						}
					}
				}
				else{

				}
			}

			//expressions for constraints (6)
			IloLinearNumExpr[] summatchingsmade6 = new IloLinearNumExpr[arrivingblocklist.size()];
			for (int i = 0; i<arrivingblocklist.size(); i++){
				summatchingsmade6[i] = cplex.linearNumExpr();
				for (int j = 0; j<departingblocklist.size(); j++){
					for (int k = 0; k<compatibledepartingblocksset[i].getCompatibleDepartingBlocks().size(); k++){
						if (compatibledepartingblocksset[i].getCompatibleDepartingBlocks().get(k) == departingblocklist.get(j)){
							summatchingsmade6[i].addTerm(1.0, z[i][j]);
						}
					}
				}
			}
			
			
			//expressions for constraints (7)
			IloLinearNumExpr[] summatchingsmade7 = new IloLinearNumExpr[departingblocklist.size()];
			for (int j = 0; j<departingblocklist.size(); j++){
				summatchingsmade7[j] = cplex.linearNumExpr();
				for (int i = 0; i<arrivingblocklist.size(); i++){
					for (int k = 0; k<compatiblearrivingblocksset[j].getCompatibleArrivingBlocks().size(); k++){
						if (compatiblearrivingblocksset[j].getCompatibleArrivingBlocks().get(k) == arrivingblocklist.get(i)){
							summatchingsmade7[j].addTerm(1.0,z[i][j]); //only add z(i,j) if i is in the compatiblearrivingblocksset of j
						}
					}
				}
			}
			//expressions for constraints (8) (max blocklength)
			IloLinearNumExpr[] weightexpr = new IloLinearNumExpr[arrivingblocklist.size()];
			for (int i =0; i<arrivingblocklist.size(); i++){
				weightexpr[i] = cplex.linearNumExpr();
				weightexpr[i] = Matching.M*(1-u[i])+Matching.minplatformlength;
				//TODO ^werkt dit zo?? moet dit niet met addterm? (weet niet hoe dat moet met een constante M
			}

			IloLinearNumExpr objective = cplex.linearNumExpr();
			for(int k = 0; k<arrivingcompositions.size(); k++){
				for(int j = 0; j<nrnodes; j++){
					for(int i = 0; i<nrdepnodes; i++ ){
						objective.addTerm(Q, u.get(k)[i][j]);
					}
				}
			}
			/* EXAMPLES 
			 * IloLinearNumExpr objective = cplex.linearNumExpr();
			 * objective.addTerm(13,x);
			 * objective.addTerm(15,y);
			 * 
			 * for(i=0;i<u.getSize();i++){
			 * objective.addTerm(Q,u[i]);
			 * for(j=0;j<z.getSize();j++){
			 * objective.addTerm(w[i][j], z[i][j]);
			 * }
			 * }

			 */

			// define objective 
			cplex.addMinimize(objective);

			// define constraints. the amount of constraints can be reduced though!
			for (int k=0;k<arrivingcompositions.size();k++){
				cplex.addEq(UsedArcsCol.get(k)[0],  1);
				cplex.addEq(UsedArcsCol.get(k)[1], UsedArcsRow.get(k)[1]);
				cplex.addEq(UsedArcsCol.get(k)[2], UsedArcsRow.get(k)[2]);
			}

			for (int k=0;k<arrivingcompositions.size();k++){

			}
			
			//constraints (6)
			for (int i=0;i<arrivingblocklist.size();i++){
				cplex.addEq(summatchingsmade6[i],u[i]); //TODO: u[i] nog niet gedefinieerd
			}

			//constraints (7)
			for (int j=0;j<departingblocklist.size();j++){
				cplex.addEq(summatchingsmade7[j],v[j]); //TODO: v[j] nog niet gedefinieerd
			}
			
			//constraints (length)
			//TODO: check: hoeft toch alleen voor u_i gedefinieerd, want dan automatisch ook voor v_j?
			for (int i = 0; i<arrivingblocklist.size(); i++){
				cplex.addLe(arrivingblocklist.get(i).getLength(),weightexpr[i]);
			}

			//cplex.addEq(cplex.sum(arg0), 1)
			/* EXAMPLES
				cplex.addGe(cplex.sum(cplex.prod(60,x),cplex.prod(60, y)), 30);
				cplex.addGe(cplex.sum(cplex.prod(12,x),cplex.prod(6, y)), 3);
				cplex.addGe(cplex.sum(cplex.prod(10,x),cplex.prod(30, y)), 9);
			 */

			cplex.setParam(IloCplex.Param.Simplex.Display, 1);


			// solve
			if(cplex.solve()){
				System.out.println("obj = "+cplex.getObjValue());
			}
			else {
				System.out.println("Model not solved");
			}

			cplex.end();
		}
		catch (IloException exc){
			exc.printStackTrace();
		}
	}

}

/**
 * Class of arcs for a composition
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
class Arcs {

	private Composition parent;
	private int[][] arcs;

	/**
	 * Constructor for class Arcs
	 * 
	 * @param parent
	 * @throws IOException
	 */
	public Arcs(Composition parent) throws IOException{
		this.parent = parent;
		if (parent.getSize() != 1 && parent.getSize() != 2 && parent.getSize() != 3){
			throw new IOException("Constructor of arcs can only handle compositions of max size 3, and this.parent is of size "+parent.getSize());
		}
		if (parent.getSize()==1){
			arcs = new int[1][2];
			arcs[0][0]=-1;
			arcs[0][1]=0;
		}
		else if (parent.getSize()==2){
			arcs = new int[3][2];
			arcs[0][0]=-1;
			arcs[0][1]=0;
			arcs[1][0]=-1;
			arcs[1][1]=1;
			arcs[2][0]=0;
			arcs[2][1]=1;
		}
		else{ //parent.getSize()==3
			arcs = new int[6][2];
			arcs[0][0]=-1;
			arcs[0][1]=0;
			arcs[1][0]=-1;
			arcs[1][1]=1;
			arcs[2][0]=-1;
			arcs[2][1]=2;
			arcs[3][0]=0;
			arcs[3][1]=1;
			arcs[4][0]=0;
			arcs[4][1]=2;
			arcs[5][0]=1;
			arcs[5][1]=2;
		}
	}

	/**
	 * Returns parent composition
	 * 
	 * @return parent
	 */
	public Composition getParent(){
		return parent;
	}

	/**
	 * Returns arcs
	 * 
	 * @return arcs
	 */
	public int[][] getArcs(){
		return arcs;
	}


}


/**
 * Class of incoming arcs for a composition and node
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
class ArcsIncoming {

	private Composition parent;
	private int node;
	private int[][] arcs;

	/**
	 * Constructor for class ArcsIncoming
	 * 
	 * @param parent - Composition of which we want the incoming arcs
	 * @param node - Node of which we want the incoming arcs
	 * @throws IndexOutOfBoundsException 
	 */
	public ArcsIncoming(Composition parent, int node) throws IndexOutOfBoundsException{
		this.parent=parent;
		this.node=node;

		//throw exception if node is out of range
		if (node < 0 || node > parent.getSize()-1){
			throw new IndexOutOfBoundsException("For incoming arcs of parent of size "+parent.getSize()+" node should be minimum 0 and maximum "+(parent.getSize()-1)+"and is "+node);
		}

		//update incoming arcs
		arcs = new int[node-(-1)][2];
		for (int i = 0; i< node-(-1); i++){
			arcs[i][0]=i-1;
			arcs[i][1]=node;
		}
	}

	/**
	 * Returns parent composition
	 * 
	 * @return parent
	 */
	public Composition getParent() {
		return parent;
	}

	/**
	 * Returns node
	 * 
	 * @return node
	 */
	public int getNode() {
		return node;
	}

	/**
	 * Returns incoming arcs
	 * 
	 * @return arcs
	 */
	public int[][] getArcsIncoming(){
		return arcs;
	}

}

/**
 * Class of outgoing arcs for a composition and node
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
class ArcsOutgoing {
	private Composition parent;
	private int node;
	private int[][] arcs;

	/**
	 * Constructor for class ArcsOutgoing
	 * 
	 * @param parent - Composition of which we want the outgoing arcs
	 * @param node - Node of which we want the outgoing arcs
	 * @throws IndexOutOfBoundsException 
	 */
	public ArcsOutgoing(Composition parent, int node) throws IndexOutOfBoundsException{
		this.parent=parent;
		this.node=node;

		//throw exception if node is out of range
		if (node < -1 || node >= parent.getSize()-1){
			throw new IndexOutOfBoundsException("For outgoing arcs of parent of size "+parent.getSize()+" node should be minimum -1 and maximum "+(parent.getSize()-1-1)+"and is "+node);
		}

		//update outgoing arcs
		arcs = new int[(parent.getSize()-1)-node][2];
		for (int i = 0; i< (parent.getSize()-1)-node; i++){
			arcs[i][0]=node;
			arcs[i][1]=node+i+1;
		}
	}

	/** Returns parent composition
	 * 
	 * @return parent
	 */
	public Composition getParent() {
		return parent;
	}

	/**
	 * Returns node
	 * 
	 * @return node
	 */
	public int getNode() {
		return node;
	}

	/**
	 * Returns outgoing arcs
	 * 
	 * @return arcs
	 */
	public int[][] getArcsOutgoing(){
		return arcs;
	}

}

/**
 * Class of intermediate nodes for a composition
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
class IntermediateNodes {
	private Composition parent;
	private int[] nodes;

	/**
	 * Constructor for class IntermediateNodes
	 * 
	 * @param parent - Composition of which we want the intermediate nodes
	 */
	public IntermediateNodes(Composition parent){
		this.parent=parent;
		nodes = new int[parent.getSize()-1];

		//determine intermediate nodes
		for (int i = 0; i <= parent.getSize()-1-1; i++){
			nodes[i]=i;
		}
	}

	/**
	 * Returns parent composition
	 * 
	 * @return parent
	 */
	public Composition getParent(){
		return parent;
	}

	/**
	 * Returns intermediate nodes
	 * 
	 * @return nodes
	 */	
	public int[] getIntermediateNodes(){
		return nodes;
	}
}



/**
 * Class of departing blocks that depart after a certain block arrives (+ some extra time for service activities, shunting, and a buffer)
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
class CompatibleDepartingBlocks {
	private Block arrivingblock;
	private ArrayList<Block> compatibledepartingblocks;

	/**
	 * Constructor for class CompatibleDepartingBlocks
	 * 
	 * @param arrivingblock - Arriving block (element of I) of which we want the compatible departing blocks
	 * @throws IOException 
	 */
	public CompatibleDepartingBlocks(Block arrivingblock, ArrayList<Block> alldepartingblocks) throws IOException{
		//throw exception if arrivingblock has an infeasible arrivaltime or departuretime not equal to -1 (-1 indicates it is an arrivingblock)
		if (arrivingblock.getArrivaltime() <0 || arrivingblock.getArrivaltime() > 1){
			throw new IOException("Arrival time of arrivingblock in class CompatibleDepartingBlocks is "+arrivingblock.getArrivaltime()+" and should be between 0 and 1");
		}
		if (arrivingblock.getDeparturetime()!=-1){
			throw new IOException("Departure time of arrivingblock in class CompatibleDepartingBlocks is "+arrivingblock.getDeparturetime()+" and should be -1");
		}

		this.arrivingblock = arrivingblock;
		compatibledepartingblocks = new ArrayList<>();

		//determine compatibledepartingblocks
		for (int i = 0; i<alldepartingblocks.size(); i++){
			//TODO: check if we want this c and if we want the total service time included here
			//TODO: check if we want the total service time of the arriving of departing block (is there a difference?) NB is now switched off

			//throw exception if departing blocks arrival time not equal to -1 (-1 indicates it is an departing block)
			//throw exception if departing block has an infeasible departure time
			if (alldepartingblocks.get(i).getDeparturetime() <0 || alldepartingblocks.get(i).getDeparturetime() > 1){
				throw new IOException("Departure time of alldepartingblocks("+i+") in class CompatibleDepartingBlocks is "+alldepartingblocks.get(i).getDeparturetime()+" and should be between 0 and 1");
			}
			if (alldepartingblocks.get(i).getArrivaltime()!=-1){
				throw new IOException("Arrival time of alldepartingblocks("+i+") in class CompatibleDepartingBlocks is "+alldepartingblocks.get(i).getArrivaltime()+" and should be -1");
			}
			if (alldepartingblocks.get(i).getDeparturetime() > arrivingblock.getArrivaltime() + Matching.c /*+ arrivingblock.getTotalServiceTime()*/ && arrivingblock.checkEqual(alldepartingblocks.get(i))==true){
				compatibledepartingblocks.add(alldepartingblocks.get(i));
			}
		}	
	}

	/**
	 * Returns arriving block for which we determined the compatible departing blocks
	 * 
	 * @return arrivingblock
	 */
	public Block getArrivingBlock() {
		return arrivingblock;
	}

	/**
	 * Returns compatible departing blocks of arrivingblock
	 * 
	 * @return compatibledepartingblocks
	 */
	public ArrayList<Block> getCompatibleDepartingBlocks() {
		return compatibledepartingblocks;
	}
}


/**
 * Class of arriving blocks that arrive before a certain block departs (+ some extra time for service activities, shunting, and a buffer)
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
class CompatibleArrivingBlocks {
	private Block departingblock;
	private ArrayList<Block> compatiblearrivingblocks;

	/**
	 * Constructor for class CompatibleArrivingBlocks
	 * 
	 * @param departingblock - Departing block (element of J) of which we want the compatible arriving blocks
	 * @throws IOException 
	 */
	public CompatibleArrivingBlocks(Block departingblock, ArrayList<Block> allarrivingblocks) throws IOException{
		//throw exception if departing blocks arrival time not equal to -1 (-1 indicates it is an departing block)
		//throw exception if departing block has an infeasible departure time
		if (departingblock.getDeparturetime() <0 || departingblock.getDeparturetime() > 1){
			throw new IOException("Departure time of departingblock in class CompatibleArrivingBlocks is "+departingblock.getDeparturetime()+" and should be between 0 and 1");
		}
		if (departingblock.getArrivaltime()!=-1){
			throw new IOException("Arrival time of departingblock in class CompatibleArrivingBlocks is "+departingblock.getArrivaltime()+" and should be -1");
		}

		this.departingblock = departingblock;
		compatiblearrivingblocks = new ArrayList<>();
		for (int i = 0; i<allarrivingblocks.size(); i++){
			//TODO: check if we want this c and if we want the total service time included here
			//TODO: check if we want the total service time of the arriving of departing block (is there a difference?) NB is now switched off

			//throw exception if arrivingblock has an infeasible arrivaltime or departuretime not equal to -1 (-1 indicates it is an arrivingblock)
			if (allarrivingblocks.get(i).getArrivaltime() <0 || allarrivingblocks.get(i).getArrivaltime() > 1){
				throw new IOException("Arrival time of allarrivingblocks("+i+") in class CompatibleDepartingBlocks is "+allarrivingblocks.get(i).getArrivaltime()+" and should be between 0 and 1");
			}
			if (allarrivingblocks.get(i).getDeparturetime()!=-1){
				throw new IOException("Departure time of allarrivingblocks("+i+") in class CompatibleDepartingBlocks is "+allarrivingblocks.get(i).getDeparturetime()+" and should be -1");
			}
			if (allarrivingblocks.get(i).getArrivaltime()+ Matching.c /*+ allarrivingblocks.get(i).getTotalServiceTime()*/ < departingblock.getDeparturetime() && departingblock.checkEqual(allarrivingblocks.get(i))==true){
				compatiblearrivingblocks.add(allarrivingblocks.get(i));
			}
		}	
	}

	/**
	 * Returns departing block for which we determined the compatible arriving blocks
	 * 
	 * @return departingblock
	 */
	public Block getDepartingBlock() {
		return departingblock;
	}

	/**
	 * Returns compatible arriving blocks of departingblock
	 * 
	 * @return compatiblearrivingblocks
	 */
	public ArrayList<Block> getCompatibleArrivingBlocks() {
		return compatiblearrivingblocks;
	}
}






