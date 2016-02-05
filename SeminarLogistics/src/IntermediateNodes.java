//TODO: documentation, test, exceptions
public class IntermediateNodes {

	private Composition parent;
	private int[] nodes;
	
	public IntermediateNodes(Composition parent){
		this.parent=parent;
		nodes = new int[parent.getSize()-1];
		for (int i = 0; i <= parent.getSize()-1-1; i++){
			nodes[i]=i;
		}
	}
	
	public Composition getParent(){
		return parent;
	}
	
	public int[] getNodes(){
		return nodes;
	}
}
