import java.lang.*;

/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */

public class Train {
	private final int train_ID;
	private final int type;
	private final int length;
	private final int inspectiontime;
	private final int cleaningtime;
	private final int repairingtime;
	private final int washingtime;
	
	private boolean interchangeable;
	private boolean inspecting;
	private boolean cleaning;
	private boolean repairing;
	private boolean washing;
	
	private int position;
	
	public Train(int train_ID, int type){
		this.train_ID = train_ID;
		this.type = type;
		
		position = 1; //If you make a new train, it is the only train in its composition
		
		interchangeable = false;
		inspecting = false;
		cleaning = false;
		repairing = false;
		washing = false;
		
		switch (type){
		case 1:length = 50 ; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 10; //Minutes
		break;
		case 2:length = 100 ; //Meters
		inspectiontime = 20; //Minutes
		cleaningtime = 20; //Minutes
		repairingtime = 20; //Minutes
		washingtime = 20; //Minutes ;
		break;
		default: length = 0 ; //Meters
		inspectiontime = 0; //Minutes
		cleaningtime = 0; //Minutes
		repairingtime = 0; //Minutes
		washingtime = 0; //Minutes ;;
		break;
		
		
		}
	}
	
	public Train(int train_ID, int type, boolean interchangeable, boolean inspecting, boolean cleaning, boolean repairing, boolean washing){
		this.train_ID = train_ID;
		this.type = type;
		this.inspecting = inspecting;
		this.cleaning = cleaning;
		this.repairing = repairing;
		this.washing = washing;
		
		position = 1; //If you make a new train, it is the only train in its composition
		
		switch (type){
		case 1:length = 50 ; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 10; //Minutes
		break;
		case 2:length = 100 ; //Meters
		inspectiontime = 20; //Minutes
		cleaningtime = 20; //Minutes
		repairingtime = 20; //Minutes
		washingtime = 20; //Minutes ;
		break;
		default: length = 0 ; //Meters
		inspectiontime = 0; //Minutes
		cleaningtime = 0; //Minutes
		repairingtime = 0; //Minutes
		washingtime = 0; //Minutes ;;
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
	public int getPosition(){
		return position;
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
	
	public void changePosition(int newposition){
		this.position = newposition;
	}
}