import java.util.ArrayList;


public class TarjanNode implements DeNode<TarjanNode, TarjanGraph> {
	/*
	 * General Attributes to make the interface happy
	 */
	protected long id;
	protected TarjanGraph graph;
	protected ArrayList<TarjanNode> children;
	protected ArrayList<TarjanNode> parents;
	/*
	 * Tarjan's Algorithm attributes.
	 */
	protected long lowlink = -1;
	protected long index = -1;
	protected boolean explored;
	protected boolean SCCd;
	//protected boolean stacked;
	
	@Override
	public String toString(){
		return "tarjanNode" + this.id; 
	}

	
	public TarjanNode(){
		children = new ArrayList<TarjanNode>();
		parents = new ArrayList<TarjanNode>();
		explored  = false;
		//stacked = false;
		SCCd = false;
	}
	
	public TarjanNode(long id){
		this();
		this.id = id;
	}
	
	public void markExplored(){
		explored = true;
	}
	
	/*public void markStacked(){
		stacked = true;
	}*/
	
	
	public void markSCCd(){
		SCCd = true;
	}
	public boolean isSCCd(){
		return SCCd;
	}
	public boolean isExplored(){
		return explored;
	}
	/*public boolean isStacked(){
		return stacked;
	}*/

	@Override
	public int compareTo(DeNode<?, ?> otherNode) {
		return Long.compare(id, otherNode.getID());
	}

	@Override
	public Long getID() {
		return this.id;
	}

	@Override
	public TarjanGraph getGraph() {
		return this.graph;
	}

	@Override
	public ArrayList<TarjanNode> getChildren() {
		return this.children;
	}

	@Override
	public ArrayList<TarjanNode> getParents() {
		return this.parents;
	}

	@Override
	public void connectChild(TarjanNode node) {
		this.children.add(node);
		node.getParents().add(this);
	}

	@Override
	public void setGraph(TarjanGraph graph) {
		this.graph = graph;
	}

}
