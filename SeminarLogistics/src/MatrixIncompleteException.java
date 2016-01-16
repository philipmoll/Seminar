@SuppressWarnings("serial")
/**
 * Exception thrown when data file contains a non rectangular matrix
 * 
 * @author 360024 Friso Tigchelaar
 * @author 362063 Floor Wolfhagen
 *
 */

public class MatrixIncompleteException extends Exception{
	/**
	 * 
	 * @param reason
	 */
	
	public MatrixIncompleteException(String reason){
		super(reason);
	}
}
