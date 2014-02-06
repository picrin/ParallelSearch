import java.util.ArrayList;

/**
 * DeNode stands for double ended Node -- a Node which stores access to 
 * its parents and children
 * FamilyNode is to be interpreted as the type of children and parents.
 */

public interface DeNode<FamilyNode> extends Comparable<DeNode<?>>{

	public Long getID();
	
	public DataGraph getGraph();
	public void setGraph(DataGraph graph);

	public ArrayList<FamilyNode> getChildren();
	public ArrayList<FamilyNode> getParents();
	
	public void connectChild(FamilyNode node);
	
	/*
	 * these two methods are crucial from the point of view of
	 * the SCDC algorithm. They mark a node as belonging to the
	 * predecessors of another, currently explored node.
	 *
	 * public boolean markPredecessor(DataGraph graph);
	 * public boolean markDescendant(DataGraph graph);
	 */
}
