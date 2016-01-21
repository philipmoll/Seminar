import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.StreamTokenizer;

/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */

public class DataSet {

	/**
	 * @param bestand
	 * @param columnNumber
	 * 
	 * @throws FileNotFoundException if file cannot be found
	 * @throws IOException if file is cannot be read / format is invalid
	 * @throws MatrixIncompleteException if data in data file is not rectangular
	 * 
	 * @return column_values
	 */

	//Generate ArrayList, containing vectors, to store the data. The ArrayList will be a matrix.
	private ArrayList <Double[]> values = new ArrayList <Double[]>();

	//This array will be stored into "values" once a line of data is read.
	private Double[] rowvalues;

	//Constructor
	public DataSet(String bestand) throws FileNotFoundException, IOException, MatrixIncompleteException
	{

		//Create new Reader.
		BufferedReader datareader = null;

		//Read file.
		datareader = new BufferedReader(new FileReader(bestand));	

		//Initialize tokenizer.
		StreamTokenizer str = new StreamTokenizer(datareader);

		//Temporary will be an Array List in which one by one the numbers in a particular row of the data file will be stored.
		ArrayList <Double> temporary = new ArrayList <Double>();

		//Activate TT.EOL.
		str.eolIsSignificant(true);

		//While the end of the file is not yet reached.
		while (str.nextToken() != StreamTokenizer.TT_EOF) {

			//Distinguish the different types the StreamTokenizer reads.
			switch(str.ttype) {

			//In case of "End of Line" token, transform the current temporary ArrayList to an array and store it in the final ArrayList "values".
			case StreamTokenizer.TT_EOL:

				rowvalues = temporary.toArray(new Double[0]);
				
				values.add(rowvalues);

				//If the size of the just added array is not equal to the size of the previous added array (matrix is not rectangular), throw MatrixIncompleteException.
				if(values.size()>1){
					if (values.get(values.size()-2).length != values.get(values.size()-1).length)
					{throw new MatrixIncompleteException("The amount of columns in row " + values.size() + " is not equal to the previous rows.");}
				}

				//After adding, remove the temporary ArrayList for the next line.
				temporary.removeAll(temporary);
				break;

				//In case of "Word" token, do not do anything.
			case StreamTokenizer.TT_WORD:
				//temporary.add(str.sval); Cannot have strings as input in a matrix as it consists of doubles.
				break;

				//In case of "Number" token, store this number to the temporary ArrayList.
			case StreamTokenizer.TT_NUMBER:
				temporary.add(str.nval);
				break;

				//Break by default.
			default:
				break;
			}
		}

		//Close the data reader to prevent errors.
		datareader.close();

	}

	//Accessor method to obtain a certain columns
	public double[] getColumns(int columnNumber){
		double[] column_values = new double[values.size()];

		for (int i=0; i<values.size(); i++) {
			Double[] row_values = values.get(i);
			column_values[i] = row_values[columnNumber];
		}
		return column_values;
	}

	public Matrix DataToMatrix()
	{
		Matrix temp = new Matrix(values.size(), (values.get(0)).length);
		for (int i=0; i<values.size(); i++)
		{
			for (int j=0; j<(values.get(0)).length; j++)
			{
				temp.setElement(i, j, (values.get(i))[j]);
			}
		}
		return temp;
	}

}