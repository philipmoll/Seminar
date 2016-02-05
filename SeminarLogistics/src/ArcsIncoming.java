//TODO: documentation, test, exceptions
public class ArcsIncoming {

	private Composition parent;
	private int node;
	private int[][] arcs;
	
	public ArcsIncoming(Composition parent, int node){
		this.parent=parent;
		this.node=node;
		arcs = new int[node-(-1)][2];
		for (int i = 0; i< node-(-1); i++){
			arcs[i][0]=i-1;
			arcs[i][1]=node;
		}
	}

	public Composition getParent() {
		return parent;
	}

	public int getNode() {
		return node;
	}

	public int[][] getArcsIncoming(){
		return arcs;
	}

}