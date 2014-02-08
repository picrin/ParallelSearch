import java.util.ArrayList;


public class TarjansNode implements DeNode<TarjansNode, TarjansGraph> {

	
	
	protected ArrayList<TarjansNode> parents = new ArrayList<>();
	protected ArrayList<TarjansNode> children = new ArrayList<>();
	protected boolean visit;
	protected Long id;
	protected long IDtarjans = 0;
	protected boolean hasLowlink = false;
	protected long lowlink;
	TarjansGraph graph;
	
	
	
	// CONSTRUCTORS:
	public TarjansNode(){}
	
	public TarjansNode(ArrayList<TarjansNode> parents, ArrayList<TarjansNode> children){
		this.id+=1;
		this.children = children;
		this.parents = parents;
		this.visit = false;
	}
	
	public TarjansNode(long id){
		this.id = id;
	}
	
	// GETTER and SETTER methods:
	public void setIDtarjans(long value){
		this.IDtarjans=value;
	}
	
	public long getIDtarjans(){ return this.IDtarjans; }
	
	public TarjansNode getChild(){
		for(TarjansNode child:children){
			if(!child.visited()){
				return child;
			}
		}
		return null;
	}
	
	@Override
	public ArrayList<TarjansNode> getChildren() {
		return this.children;
	}
	
	@Override
	public void connectChild(TarjansNode child) {
		child.parents.add(this);
		this.children.add(child);		
	}
	
	public TarjansNode getParent(TarjansNode node, int index){
		return this.parents.get(index);
	}
	
	public void setVisited(boolean visited){
		this.visit=visited;
	}
	
	public boolean visited(){
		return this.visit;
	}
	
	public void setLOWLINK(long value){
		this.lowlink=value;
	}
	
	public long getLOWLINK(){
		return this.lowlink;
	}
	
	@Override
	public TarjansGraph getGraph() {
		return this.graph;
	}

	@Override
	public void setGraph(TarjansGraph graph) {
		this.graph = graph;
	}

	@Override
	public ArrayList<TarjansNode> getParents() {
		return this.parents;
	}

	@Override
	public Long getID() {
		return this.id;
	}

	@Override
	public int compareTo(DeNode<?, ?> o) {
		// TODO Auto-generated method stub
		return -1;
	}
	
	@Override
	public String toString(){
		return "node" + id;
	}
	
	
	
	
}
