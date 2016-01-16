
/**
 * 
 * @author Philip Moll 431983
 * @author Friso Tigchelaar 360024
 * @author Robin Timmerman 344870
 * @author Floor Wolfhagen 362063
 *
 */

public class Matrix {
	private double[][] MatrixTo;
	private int nRows;
	private int nCols;
	private double value;
/**
 * @PRECOND: 
 */
	public Matrix(int nRows, int nCols)
	{
		//input larger than 0!!
		this.nRows = nRows;
		this.nCols = nCols;

		MatrixTo = new double[nRows][nCols];
		//must define that the rest is 0?
	}

	public Matrix(int size)
	{
		//input larger than 0!!
		this.nRows = size;
		this.nCols = size;

		MatrixTo = new double[size][size];
		for (int i=0; i<size; i++)
		{
			MatrixTo[i][i]=1;
			//must define that the rest is 0?
		}
	}

	public Matrix(Matrix from)
	{
		this.nRows = from.getNrRows();
		this.nCols = from.getNrColumns();

		MatrixTo = new double[from.getNrRows()][from.getNrColumns()];
		//MatrixTo = from; mag niet want dan zijn de classes gelijk en we willen alleen dat ze gelijke waarden hebben.
		
		for (int i=0; i<from.getNrRows(); i++)
		{
			for (int j=0; j<from.getNrColumns();j++)
			{
				MatrixTo[i][j] = from.getElement(i, j);
			}
		}
	}

	public int getNrRows()
	{
		return nRows;
	}
	public int getNrColumns()
	{
		return nCols;
	}
	public void setElement(int row, int column, double element)
	{
		MatrixTo[row][column] = element;
	}
	public double getElement(int row, int column)
	{
		return MatrixTo[row][column];
	}
	public Matrix transpose()
	{
		Matrix MatrixTransposed = new Matrix(MatrixTo[0].length, MatrixTo.length);
		for (int i=0; i<MatrixTransposed.getNrRows();i++)
		{
			for (int j=0; j<MatrixTransposed.getNrColumns(); j++)
			{
				MatrixTransposed.setElement(i, j, MatrixTo[j][i]);
			}
		}
		return MatrixTransposed;
	}
	public Matrix multiply(Matrix other)
	{
		Matrix MatrixMultiplied = new Matrix(MatrixTo.length,other.getNrColumns());

		//throw if matrices are not corresponding format!
		for(int k=0; k<MatrixTo.length;k++)
		{
			for(int i=0;i<other.getNrColumns();i++)
			{
				for(int j=0; j<other.getNrRows();j++) /*Is similar to MatrixTo[0].length*/
				{		
					value += MatrixTo[k][j]*other.getElement(j,i);
				}
				MatrixMultiplied.setElement(k, i, value);
				value = 0;
			}
		}
		return MatrixMultiplied;
	}
	//TODO: YET TO BE CHECKED
	public Matrix dotmultiply(Matrix other){
		Matrix MatrixMultiplied = new Matrix(MatrixTo.length,other.getNrColumns());
		for(int i=0; i<this.getNrColumns(); i++){
			for(int j=0; j<this.getNrRows();j++){
				MatrixMultiplied.setElement(j, i, other.getElement(j, i)*this.getElement(j, i));
			}
		}
		return MatrixMultiplied;	
	}
	public Matrix subtract(Matrix other)
	{
		//throw if matrices do not match. Does 'this' work in this case?!? Or should we replace it with 'other'?
		Matrix MatrixSubtracted = new Matrix(this);
		for (int i=0; i<MatrixTo.length;i++)
		{
			for (int j=0; j<MatrixTo[0].length; j++)
			{
				value = MatrixTo[i][j]-other.getElement(i, j);
				MatrixSubtracted.setElement(i, j, value);
			}
		}
		return MatrixSubtracted;
	}
	public Matrix inverse() {
		if (getNrRows() != getNrColumns()) {
			throw new IllegalStateException("inverse() pre-condition violation: non-square matrix");
		}
			
	    int n = getNrRows();
			
	    Matrix x = new Matrix(n, n);
	    Matrix b = new Matrix(n); // identity matrix
	    int index[] = new int[n];
			
	    Matrix a = new Matrix(this); // copy this to another one
	    
	    // Transform the matrix into an upper triangle
	    gaussian(a, index);

	    // Update the matrix b with the ratios stored
	    for (int i=0; i<n-1; ++i) {
		for (int j=i+1; j<n; ++j) {
		    for (int k=0; k<n; ++k) {
			double val = b.getElement(index[j], k) - a.getElement(index[j], i)*b.getElement(index[i], k);
			b.setElement(index[j], k, val);
		    }
		}
	    }
			
	    // Perform backward substitutions
	    for (int i=0; i<n; ++i) {
		x.setElement(n-1,i, b.getElement(index[n-1], i) / a.getElement(index[n-1], n-1));
		for (int j=n-2; j>=0; --j) {
		    x.setElement(j,i, b.getElement(index[j],i));
		    for (int k=j+1; k<n; ++k) {
			double val = x.getElement(j, i) - a.getElement(index[j], k)*x.getElement(k,i);
			x.setElement(j,i, val);
		    }
		    x.setElement(j, i, x.getElement(j, i) / a.getElement(index[j], j));
		}
	    }
	    return x;
	}

	// Method to carry out the partial-pivoting Gaussian
	// elimination.  Here index[] stores pivoting order.

	/**
	 * Performs partial-pivoting Gaussian elimination on a, storing the pivoting order.
	 *
	 * @param a the matrix to perform the elimination on. Modifies the Matrix as a side effect.
	 * @param index an array to store the pivoting order in. Modifies the array as a side effect. PRECOND: index.length == a.getNrRows()
	 */
	private void gaussian(Matrix a, int index[]) {
	    int n = index.length;
	    double c[] = new double[n];

	    // Initialize the index
	    for (int i=0; i<n; ++i) {
		index[i] = i;
	    }

	    // Find the rescaling factors, one from each row
	    for (int i=0; i<n; ++i) {
		double c1 = 0;
		for (int j=0; j<n; ++j) {
		    double c0 = Math.abs(a.getElement(i, j));
		    if (c0 > c1) {
			c1 = c0;
		    }
		}
		c[i] = c1;
	    }

	    // Search the pivoting element from each column
	    int k = 0;
	    for (int j=0; j<n-1; ++j) {
		double pi1 = 0.0;
		for (int i=j; i<n; ++i) {
		    double pi0 = Math.abs(a.getElement(index[i], j));
		    pi0 /= c[index[i]];
		    if (pi0 > pi1) {
			pi1 = pi0;
			k = i;
		    }
		}

		// Interchange rows according to the pivoting order
		int itmp = index[j];
		index[j] = index[k];
		index[k] = itmp;
		for (int i=j+1; i<n; ++i) {
		    double pj = a.getElement(index[i], j) / a.getElement(index[j], j);

		    // Record pivoting ratios below the diagonal
		    a.setElement(index[i], j, pj);

		    // Modify other elements accordingly
		    for (int l=j+1; l<n; ++l) {
			double val = a.getElement(index[i], l) - (pj*a.getElement(index[j], l));
			a.setElement(index[i], l, val);
		    }
		}
	    }
	}
}