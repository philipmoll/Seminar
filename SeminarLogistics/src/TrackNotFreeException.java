@SuppressWarnings("serial")
/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */
public class TrackNotFreeException extends Exception {

	public TrackNotFreeException(String reason){
		/**
		 * 
		 * @param reason
		 */
		super(reason);
	}
}
