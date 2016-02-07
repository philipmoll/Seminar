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

	private boolean interchangeable;
	private boolean inspecting;
	private boolean cleaning;
	private boolean repairing;
	private boolean washing;

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
	public Train(int train_ID, int type, int carriages, int length){
		this.train_ID = train_ID;
		this.type = type;
		this.length = length;
		this.carriages = carriages;
		this.composition = null;

		position = -1; //If you make a new train, it is the only train in its composition

		interchangeable = false;
		inspecting = false;
		cleaning = false;
		repairing = false;
		washing = false;

		switch (type){
		case 1:
			inspectiontime = 10; //Minutes
			cleaningtime = 10; //Minutes
			repairingtime = 10; //Minutes
			washingtime = 10; //Minutes
			break;
		case 2:
			inspectiontime = 10; //Minutes
			cleaningtime = 10; //Minutes
			repairingtime = 10; //Minutes
			washingtime = 10; //Minutes
			break;
		case 3:
			inspectiontime = 10; //Minutes
			cleaningtime = 10; //Minutes
			repairingtime = 10; //Minutes
			washingtime = 10; //Minutes

			break;
		case 4:
			inspectiontime = 10; //Minutes
			cleaningtime = 10; //Minutes
			repairingtime = 10; //Minutes
			washingtime = 10; //Minutes

			break;
		case 5:
			inspectiontime = 10; //Minutes
			cleaningtime = 10; //Minutes
			repairingtime = 10; //Minutes
			washingtime = 10; //Minutes

			break;
		case 6:
			inspectiontime = 10; //Minutes
			cleaningtime = 10; //Minutes
			repairingtime = 10; //Minutes
			washingtime = 10; //Minutes

			break;
		default: 
			inspectiontime = -1; //Minutes
			cleaningtime = -1; //Minutes
			repairingtime = -1; //Minutes
			washingtime = -1; //Minutes

			break;

		}
	}

	

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
	public Train(int train_ID, int type, boolean interchangeable, boolean inspecting, boolean cleaning, boolean repairing, boolean washing){
		this.train_ID = train_ID;
		this.type = type;
		this.inspecting = inspecting;
		this.cleaning = cleaning;
		this.repairing = repairing;
		this.washing = washing;

		position = 1; //If you make a new train, it is the only train in its composition

		switch (type){
		case 1:length = 109; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 20; //Minutes
		carriages = 4;
		break;
		case 2:length = 162; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 20; //Minutes
		carriages = 6;
		break;
		case 3:length = 101; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 20; //Minutes
		carriages = 4;
		break;
		case 4:length = 154; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 20; //Minutes
		carriages = 6;
		break;
		case 5:length = 70; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 20; //Minutes
		carriages = 4;
		break;
		case 6:length = 101; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 20; //Minutes
		carriages = 6;
		break;
		default: length = 0 ; //Meters
		inspectiontime = 0; //Minutes
		cleaningtime = 0; //Minutes
		repairingtime = 0; //Minutes
		washingtime = 0; //Minutes
		carriages = 0;
		break;

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
	 * @param activitynumber : (0 = inspecting, 1 = cleaning, 2 = repairing, 3 = washing)
	 * @return activitytime : time needed to perform activity activitynumber on this train
	 * @throws IOException : when activitynumber is not 0, 1, 2, or 3
	 */
	public double getActivityTime(int activitynumber) throws IOException { //TODO: testfunctie
		if (activitynumber < 0 || activitynumber > 3){
			throw new IOException("Activity number can only be 0, 1, 2, or 3 in function getActivityTime, and is "+activitynumber);
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
			throw new IOException("Activity number can only be 0, 1, 2, or 3 in function getActivityTime, and is "+activitynumber);
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
	public boolean getInterchangeable(){
		return interchangeable;
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
	 * @param activitynumber : (0 = inspecting, 1 = cleaning, 2 = repairing, 3 = washing)
	 * @return boolean : whether activity number activitynumber still needs to be performed (true) or not (false)
	 * @throws IOException : when activitynumber is not 0, 1, 2, or 3
	 */
	public boolean getActivity(int activitynumber)throws IOException { //TODO: testfunctie
		if (activitynumber < 0 || activitynumber > 3){
			throw new IOException("Activity number can only be 0, 1, 2, or 3 in function getActivity, and is "+activitynumber);
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
	public void toggleInterchangeable(){
		interchangeable = !interchangeable; 
	}
	public void toggleInspecting(){
		inspecting = !inspecting; 
	}
	public void toggleCleaning(){
		cleaning = !cleaning; 
	}
	public void toggleRepairing(){
		repairing = !repairing; 
	}
	public void toggleWashing(){
		washing = !washing; 
	}
	public void setPosition(int newposition){
		this.position = newposition;
	}
	public void setComposition(Composition newcomposition){
		this.composition = newcomposition;
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