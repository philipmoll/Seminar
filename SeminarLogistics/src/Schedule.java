import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Schedule {

	public Train[] trains;
	private double additionalinspprob = 0.0;
	private double additionalrepprob = 0.0;
	private double additionalwashprob = 0.0;
	private double additionalcleanprob = 0.0;

	public Schedule(Train[] trains) throws IOException{
		this.trains = trains;
		
		Random randomGenerator = new Random();
		boolean b;
		
		for(int i = 0; i<trains.length; i++){
			double a = randomGenerator.nextDouble();
			if(a <= trains[i].getInspprob()+additionalinspprob){
				b = true;
			}
			else{
				b = false;
			}
			trains[i].setInspecting(b);
			
			a = randomGenerator.nextDouble();
			if(a <= trains[i].getCleanprob()+additionalcleanprob){
				b = true;
			}
			else{
				b = false;
			}
			trains[i].setCleaning(b);
			
			a = randomGenerator.nextDouble();
			if(a <= trains[i].getWashprob()+additionalwashprob){
				b = true;
			}
			else{
				b = false;
			}
			trains[i].setWashing(b);
			
			a = randomGenerator.nextDouble();
			if(a <= trains[i].getRepprob()+additionalrepprob){
				b = true;
			}
			else{
				b = false;
			}
			trains[i].setRepairing(b);
		}


		/*Writer writer = new FileWriter("/Users/frisotigchelaar/git/Seminar/SeminarLogistics/src/abc.dat");

		writer.write("Hello World Writer");
		writer.close();*/
	}
	public void increaseComplexity(){
		additionalrepprob += 0.01;
		additionalwashprob += 0.01;
		additionalcleanprob += 0.1;
		additionalinspprob += 0.4;
	}
	public Train[] getTrains(){
		return trains;
	}

}
