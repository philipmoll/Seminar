import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class MatrixTest {
	private Matrix m;
	private Matrix n;
	private Matrix z;
	
	private Matrix k;
	private Matrix x;
	
	@Before
	public void setUp() {
		m = new Matrix(2);
		n = new Matrix(2,3);
		k = new Matrix(m);

	}

	@Test
	public void testConstructorI() 
	{
		assertEquals(2, m.getNrRows());
		assertEquals(2, m.getNrColumns());

		for (int i=0; i<m.getNrRows(); i++)
		{
			for (int j=0; j<m.getNrColumns(); j++)
			{
				if(i==j)
				{
					assertEquals(1.0, m.getElement(i,j), 0.00001);
				}
				else
				{
					assertEquals(0.0, m.getElement(i, j), 0.00001);
				}
			}
		}

	}

	@Test
	public void testConstructorO()
	{
		assertEquals(2, n.getNrRows());
		assertEquals(3, n.getNrColumns());

		for (int i=0; i<n.getNrRows(); i++)
		{
			for (int j=0; j<n.getNrColumns(); j++)
			{
				assertEquals(0.0, n.getElement(i,j), 0.00001);
			}
		}
	}

	@Test
	public void testConstructorC()
	{
		assertEquals(m.getNrRows(), k.getNrRows());
		assertEquals(m.getNrColumns(), k.getNrColumns());

		for (int i=0; i<k.getNrRows(); i++)
		{
			for (int j=0; j<k.getNrColumns(); j++)
			{
				assertEquals(m.getElement(i,j), k.getElement(i,j), 0.00001);
			}
		}
	}
	
	
	public void testDataToMatrix()
	{
		try {
			z = new DataSet("datasetje.dat").DataToMatrix();
		
		
		assertEquals(3.69, z.getElement(0,0), 0.00001);
		assertEquals(5.0, z.getElement(0,1), 0.00001);
		assertEquals(98.0, z.getElement(0,2), 0.00001);
		assertEquals(4.39, z.getElement(1,0), 0.00001);
		assertEquals(6.0, z.getElement(1,1), 0.00001);
		assertEquals(98.0, z.getElement(1,2), 0.00001);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MatrixIncompleteException e) {
			e.printStackTrace();
		}
	}

	//This test also partly tests constructor since the constructor also sets elements.
	@Test
	public void testSetGetElement()
	{
		assertEquals(1.0, m.getElement(1,1), 0.00001);
		m.setElement(1, 1, 0.1);
		assertEquals(0.1, m.getElement(1,1), 0.00001);
	}

	@Test
	public void testGetNrRows()
	{
		assertEquals(2, n.getNrRows());
	}

	@Test
	public void testGetNrColumns()
	{
		assertEquals(3, n.getNrColumns());
	}

	@Test
	public void testTranspose()
	{
		n.setElement(1, 2, 10.0);
		n.setElement(0, 1, 5.0);
		x = n.transpose();

		for (int i=0; i<x.getNrRows(); i++)
		{
			for (int j=0; j<x.getNrColumns(); j++)
			{
				assertEquals(x.getElement(i, j), n.getElement(j,i), 0.00001);
			}
		}

	}

	@Test
	public void testMultiply()
	{
		n.setElement(1, 2, 10.0);
		n.setElement(0, 1, 5.0);
		m.setElement(1, 0, 0.1);
		x = m.multiply(n);
		assertEquals(0.0, x.getElement(0,0), 0.00001);
		assertEquals(5.0, x.getElement(0,1), 0.00001);
		assertEquals(0.0, x.getElement(0,2), 0.00001);
		assertEquals(0.0, x.getElement(1,0), 0.00001);
		assertEquals(0.5, x.getElement(1,1), 0.00001);
		assertEquals(10.0, x.getElement(1,2), 0.00001);
	}

	public void testSubtract()
	{
		x = m.subtract(k);
		assertEquals(0.0, x.getElement(0,0), 0.00001);
		assertEquals(0.1, x.getElement(0,1), 0.00001);
		assertEquals(0.0, x.getElement(1,0), 0.00001);
		assertEquals(0.0, x.getElement(1,1), 0.00001);
	}
	
	public void testInverse()
	{
		x = m.inverse();
		assertEquals(1.0, x.getElement(0,0), 0.00001);
		assertEquals(-0.1, x.getElement(0,1), 0.00001);
		assertEquals(0.0, x.getElement(1,0), 0.00001);
		assertEquals(1.0, x.getElement(1,1), 0.00001);
	}
	@Test
	public void testGetArray(){
		n.setElement(1, 0, 10.0);
		System.out.print(n.getArray(0)[0] +" ");
		System.out.print(n.getArray(0)[1] +" ");
		assertEquals(new double[] {0.0, 10.0}, n.getArray(0));
	}
}
