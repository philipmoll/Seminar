import java.io.IOException;
import java.io.Serializable;

/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */

@SuppressWarnings("serial")
public class Train implements Serializable{
	//////
	private final int train_ID;
	private final int type;
	private final int length;
	private final int inspectiontime;
	private final int cleaningtime;
	private final int repairingtime;
	private final int washingtime;
	private final int carriages;

	private int interchangeable_ID;
	private boolean inspecting;
	private boolean cleaning;
	private boolean repairing;
	private boolean washing;
	
	private double inspprob;
	private double cleanprob;
	private double washprob;
	private double repprob;

	private int position;
	private Composition composition;

	/**
	 * Constructor for train
	 * 
	 * @param train_ID
	 * @param type
	 * @param carriages
	 * @param length
	 */
	//WE DO NOT USE THIS ONE EXCEPT FOR THE TEST CLASSEES
	public Train(int train_ID, int type, int carriages, int length){
		this.train_ID = train_ID;
		this.type = type;
		this.carriages = carriages;
		this.composition = null;

		position = -1; //If you make a new train, it is the only train in its composition

		interchangeable_ID = -1;
		inspecting = false;
		cleaning = false;
		repairing = false;
		washing = false;
		this.length = length;

		if(type == 1 && carriages == 4){ //SLT 4 carr
			inspectiontime = 24; //Minutes
			cleaningtime = 15; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 23; //Minutes
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.1;	//probability
			repprob = 0.1;	//probability
		}
		else if(type == 1 && carriages == 6){ //SLT 6 carr
			inspectiontime = 26; //Minutes
			cleaningtime = 20; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 24; //Minutes
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.1;	//probability
			repprob = 0.1;	//probability
			}
		else if(type == 2 && carriages == 4){ //VIRM 4 carr
			inspectiontime = 11; //Minutes
			cleaningtime = 37; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 24; //Minutes
			inspprob = 0.8;	//probability
			cleanprob = 1;	//probability
			washprob = 0.1;	//probability
			repprob = 0.1;	//probability
		}
		else if(type == 2 && carriages == 6){ //VIRM 6 carr
			inspectiontime = 14; //Minutes
			cleaningtime = 56; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 26; //Minutes
			inspprob = 0.8;	//probability
			cleanprob = 1;	//probability
			washprob = 0.1;	//probability
			repprob = 0.1;	//probability
		}
		else if(type == 3 && carriages == 4){ //DDZ 4 carr
			inspectiontime = 15; //Minutes
			cleaningtime = 49; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 24; //Minutes
			inspprob = 0.8;	//probability
			cleanprob = 1;	//probability
			washprob = 0.1;	//probability
			repprob = 0.1;	//probability
		}
		else if(type == 3 && carriages == 6){ //DDZ 6 carr
			inspectiontime = 18; //Minutes
			cleaningtime = 56; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 26; //Minutes
			inspprob = 0.8;	//probability
			cleanprob = 1;	//probability
			washprob = 0.1;	//probability
			repprob = 0.1;	//probability
		}
		else{
			inspectiontime = -1; //Minutes
			cleaningtime = -1; //Minutes
			repairingtime = -1; //Minutes
			washingtime = -1; //Minutes
			inspprob = 0;	//probability
			cleanprob = 0;	//probability
			washprob = 0;	//probability
			repprob = 0;	//probability

		}
	}
	
	//THIS IS THE CONSTRUCTOR WE ARE GOING TO USE!
	public Train(int train_ID, int type, int carriages){
		this.train_ID = train_ID;
		this.type = type;
		this.carriages = carriages;
		this.composition = null;

		position = -1; //If you make a new train, it is the only train in its composition

		interchangeable_ID = -1;
		inspecting = false;
		cleaning = false;
		repairing = false;
		washing = false;

		if(type == 1 && carriages == 4){ //SLT 4 carr
			inspectiontime = 24; //Minutes
			cleaningtime = 15; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 23; //Minutes
			length = 70;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else if(type == 1 && carriages == 6){ //SLT 6 carr
			inspectiontime = 26; //Minutes
			cleaningtime = 20; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 24; //Minutes
			length = 101;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else if(type == 2 && carriages == 4){ //VIRM 4 carr
			inspectiontime = 11; //Minutes
			cleaningtime = 37; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 24; //Minutes
			length = 109;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else if(type == 2 && carriages == 6){ //VIRM 6 carr
			inspectiontime = 14; //Minutes
			cleaningtime = 56; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 26; //Minutes
			length = 162;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else if(type == 3 && carriages == 4){ //DDZ 4 carr
			inspectiontime = 15; //Minutes
			cleaningtime = 49; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 24; //Minutes
			length = 101;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else if(type == 3 && carriages == 6){ //DDZ 6 carr
			inspectiontime = 18; //Minutes
			cleaningtime = 56; //Minutes
			repairingtime = 0; //Minutes
			washingtime = 26; //Minutes
			length = 154;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else{
			inspectiontime = -1; //Minutes
			cleaningtime = -1; //Minutes
			repairingtime = -1; //Minutes
			washingtime = -1; //Minutes
			length = -1;
			inspprob = 0;	//probability
			cleanprob = 0;	//probability
			washprob = 0;	//probability
			repprob = 0;	//probability

		}
	}
	//THIS IS THE CONSTRUCTOR WE ARE GOING TO USE!
	/**
	 * 
	 * @param train_ID
	 * @param type
	 * @param interchangeable
	 * @param inspecting
	 * @param cleaning
	 * @param repairing
	 * @param washing
	 */
	public Train(int train_ID, int type, int carriages, int interchangeable_ID, boolean inspecting, boolean cleaning, boolean repairing, boolean washing){
		this.train_ID = train_ID;
		this.type = type;
		this.carriages = carriages;
		this.composition = null;

		position = -1; //If you make a new train, it is the only train in its composition

		this.interchangeable_ID = interchangeable_ID;
		this.inspecting = inspecting;
		this.cleaning = cleaning;
		this.repairing = repairing;
		this.washing = washing;

		if(type == 1 && carriages == 4){ //SLT 4 carr
			inspectiontime = 24; //Minutes
			cleaningtime = 15; //Minutes
			repairingtime = 90; //Minutes
			washingtime = 23; //Minutes
			length = 70;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else if(type == 1 && carriages == 6){ //SLT 6 carr
			inspectiontime = 26; //Minutes
			cleaningtime = 20; //Minutes
			repairingtime = 90; //Minutes
			washingtime = 24; //Minutes
			length = 101;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else if(type == 2 && carriages == 4){ //VIRM 4 carr
			inspectiontime = 11; //Minutes
			cleaningtime = 37; //Minutes
			repairingtime = 90; //Minutes
			washingtime = 24; //Minutes
			length = 109;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else if(type == 2 && carriages == 6){ //VIRM 6 carr
			inspectiontime = 14; //Minutes
			cleaningtime = 56; //Minutes
			repairingtime = 90; //Minutes
			washingtime = 26; //Minutes
			length = 162;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else if(type == 3 && carriages == 4){ //DDZ 4 carr
			inspectiontime = 15; //Minutes
			cleaningtime = 49; //Minutes
			repairingtime = 90; //Minutes
			washingtime = 24; //Minutes
			length = 101;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else if(type == 3 && carriages == 6){ //DDZ 6 carr
			inspectiontime = 18; //Minutes
			cleaningtime = 56; //Minutes
			repairingtime = 90; //Minutes
			washingtime = 26; //Minutes
			length = 154;
			inspprob = 1;	//probability
			cleanprob = 1;	//probability
			washprob = 0.05;	//probability
			repprob = 0.05;	//probability
		}
		else{
			inspectiontime = -1; //Minutes
			cleaningtime = -1; //Minutes
			repairingtime = -1; //Minutes
			washingtime = -1; //Minutes
			length = -1;
			inspprob = 0;	//probability
			cleanprob = 0;	//probability
			washprob = 0;	//probability
			repprob = 0;	//probability
		}
	}

	public int getID(){
		return train_ID;
	}
	public int getType(){
		return type;
	}
	public int getLength(){
		return length;
	}
	public int getInspectionTime(){
		return inspectiontime;
	}
	public int getCleaningTime(){
		return cleaningtime;
	}
	public int getRepairingTime(){
		return repairingtime;
	}
	public int getWashingTime(){
		return washingtime;
	}


	/**
	 * This function returns the time it takes for a certain activity to be performed
	 * 
	 * @param activitynumber : (0 = inspecting, 1 = cleaning, 2 = repairing, 3 = washing, 4 = moving)
	 * @return activitytime : time needed to perform activity activitynumber on this train
	 * @throws IOException : when activitynumber is not 0, 1, 2, 3 or 4
	 */
	public double getActivityTime(int activitynumber) throws IOException { //TODO: testfunctie
		if (activitynumber < 0 || activitynumber > 3){
			throw new IOException("Activity number can only be 0, 1, 2, 3 or 4 in function getActivityTime, and is "+activitynumber);
		}
		else if(activitynumber == 0){
			return (double) this.getInspectionTime()/24/60;
		}
		else if(activitynumber == 1){
			return (double) this.getCleaningTime()/24/60;
		}
		else if(activitynumber == 2){
			return (double) this.getRepairingTime()/24/60;
		}
		else if(activitynumber == 3){
			return (double) this.getWashingTime()/24/60;
		}
		else{
			return 0.0;
		}
	}
	public int getActivityTimeInteger(int activitynumber) throws IOException { //TODO: testfunctie
		if (activitynumber < 0 || activitynumber > 3){
			throw new IOException("Activity number can only be 0, 1, 2, 3 or 4 in function getActivityTime, and is "+activitynumber);
		}
		else if(activitynumber == 0){
			return (int) this.getInspectionTime();
		}
		else if(activitynumber == 1){
			return (int) this.getCleaningTime();
		}
		else if(activitynumber == 2){
			return (int) this.getRepairingTime();
		}
		else if(activitynumber == 3){
			return (int) this.getWashingTime();
		}
		else{
			return 0;
		}
	}
	public int getInterchangeable(){
		return interchangeable_ID;
	}
	public boolean getInspecting(){
		return inspecting;
	}
	public boolean getCleaning(){
		return cleaning;
	}
	public boolean getRepairing(){
		return repairing;
	}
	public boolean getWashing(){
		return washing;
	}


	/**
	 * This function returns a boolean whether a certain activity still needs to be performed (true) or not (false).
	 * 
	 * @param activitynumber : (0 = inspecting, 1 = cleaning, 2 = repairing, 3 = washing, 4 = moving)
	 * @return boolean : whether activity number activitynumber still needs to be performed (true) or not (false)
	 * @throws IOException : when activitynumber is not 0, 1, 2, 3 or 4
	 */
	public boolean getActivity(int activitynumber)throws IOException { //TODO: testfunctie
		if (activitynumber < 0 || activitynumber > 8){
			throw new IOException("Activity number can only be 0, 1, 2, 3 or 4 in function getActivity, and is "+activitynumber);
		}
		if(activitynumber == 0){
			return this.getInspecting();
		}
		else if(activitynumber == 1){
			return this.getCleaning();
		}
		else if(activitynumber == 2){
			return this.getRepairing();
		}
		else if(activitynumber == 3){
			return this.getWashing();
		}

		else{
			return false;
		}
	}
	public int getPosition(){
		return position;
	}
	public int getCarriages(){
		return carriages;
	}
	public Composition getComposition(){
		return composition;
	}
	public void setInspecting(boolean a){
		inspecting = a; 
	}
	public void setCleaning(boolean a){
		cleaning = a; 
	}
	public void setRepairing(boolean a){
		repairing = a; 
	}
	public void setWashing(boolean a){
		washing = a; 
	}
	public void setPosition(int newposition){
		this.position = newposition;
	}
	public void setComposition(Composition newcomposition){
		this.composition = newcomposition;
	}
	public double getInspprob(){
		return inspprob;
	}
	public double getWashprob(){
		return washprob;
	}
	public double getCleanprob(){
		return cleanprob;
	}
	public double getRepprob(){
		return repprob;
	}
	/**
	 * Returns true if a train is of the same class and same number of carriages
	 * 
	 * @param trainisequal
	 * @return true if same class/nr carriages, false otherwise
	 */
	public boolean getSameClass(Train trainisequal){
		boolean issameclass = true;
		if(type == trainisequal.getType() && length == trainisequal.getLength() && carriages == trainisequal.getCarriages()){

		}
		else{
			issameclass = false;
		}
		return issameclass;
	}
	/**
	 * Function that returns an array with a 1 at index i if train at index i of trainsareequal is of same class/nr carriages as this, 0 otherwise
	 * 
	 * @param trainsareequal - array with trains to be compared
	 * @return temptrains, array with 1 at index i if trainsareequal has train with same class/nr carriages at index i, 0 otherwise
	 */
	public int[] getSameClass(Train[] trainsareequal){
		int[] temptrains = new int[trainsareequal.length];

		for(int i = 0; i<temptrains.length;i++){
			if(this.getSameClass(trainsareequal[i])){
				temptrains[i] = 1;
			}
			else{
				temptrains[i] = 0;
			}
		}
		return temptrains;
	}
	/**
	 * Returns true if trainisequal is of same class and has same ID as this, false otherwise
	 * 
	 * @param trainisequal - train to be compared
	 * @return true if equal, false otherwise
	 */
	public boolean getSameClassAndID(Train trainisequal){
		boolean issameclassandid = true;
		if (this.getSameClass(trainisequal) == false){
			issameclassandid = false;
		}
		if (train_ID != trainisequal.getID()){
			issameclassandid = false;
		}
		return issameclassandid;

	}
}