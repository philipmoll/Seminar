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
	private final int carriages;
	
	private boolean interchangeable;
	private boolean inspecting;
	private boolean cleaning;
	private boolean repairing;
	private boolean washing;
	
	private int position;
	private Composition composition;
	
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
		washingtime = 10; //Minutes
		carriages = 4;
		break;
		case 2:length = 162; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 10; //Minutes
		carriages = 6;
		break;
		case 3:length = 101; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 10; //Minutes
		carriages = 4;
		break;
		case 4:length = 154; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 10; //Minutes
		carriages = 6;
		break;
		case 5:length = 70; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 10; //Minutes
		carriages = 4;
		break;
		case 6:length = 101; //Meters
		inspectiontime = 10; //Minutes
		cleaningtime = 10; //Minutes
		repairingtime = 10; //Minutes
		washingtime = 10; //Minutes
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
	public int getCarriages(){
		return carriages;
	}
	public Composition getComposition(){
		return composition;
	}
	public void toggleInterchangeable(){
		interchangeable = !interchangeable; //TODO: why not this.
	}
	public void toggleInspecting(){
		inspecting = !inspecting; //TODO: why not this
	}
	public void toggleCleaning(){
		cleaning = !cleaning; //TODO: why not this
	}
	public void toggleRepairing(){
		repairing = !repairing; //TODO: why not this
	}
	public void toggleWashing(){
		washing = !washing; //TODO: why not this
	}
	public void setPosition(int newposition){
		this.position = newposition;
	}
	public void setComposition(Composition newcomposition){
		this.composition = newcomposition;
	}
	public boolean getSameClass(Train trainisequal){
		boolean issameclass = true;
		if(type == trainisequal.getType() && length == trainisequal.getLength() && carriages == trainisequal.getCarriages()){
			
		}
		else{
			issameclass = false;
		}
		return issameclass;
	}
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
}