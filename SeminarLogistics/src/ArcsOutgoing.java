//TODO: documentation, test, exceptions
public class ArcsOutgoing {

	private Composition parent;
	private int node;
	private int[][] arcs;
	
	public ArcsOutgoing(Composition parent, int node){
		this.parent=parent;
		this.node=node;
		arcs = new int[(parent.getSize()-1)-node][2];
		for (int i = 0; i< (parent.getSize()-1)-node; i++){
			arcs[i][0]=node;
			arcs[i][1]=node+i+1;
		}
	}

	public Composition getParent() {
		return parent;
	}

	public int getNode() {
		return node;
	}

	public int[][] getArcsOutgoing(){
		return arcs;
	}

}
