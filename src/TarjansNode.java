import java.util.ArrayList;


public class TarjansNode implements DeNode<TarjansNode, TarjansGraph> {

	protected ArrayList<TarjansNode> parents = new ArrayList<>();
	protected ArrayList<TarjansNode> children = new ArrayList<>();
	protected boolean visit;
	protected boolean childExists = true;
	protected Long id;
	TarjansGraph graph;
	
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
	
	public TarjansNode getChild(){
		childExists = false;
		for(TarjansNode child:children){
			if(!child.visited()){
				childExists = true;
				return child;
			}
		}
		return null;
	}
	public TarjansNode getParent(TarjansNode node, int index){
		return this.parents.get(index);
	}
	
	
	public boolean allChildrenVisited(TarjansNode node){
		for(TarjansNode child:node.getChildren()){
			if(child.visited()==false){return true;}
		}
		return false;
	}
	public void setVisited(boolean visited){
		this.visit=visited;
	}
	public boolean visited(){
		return this.visit;
	}	
	public boolean doesChildExist(){
		return this.childExists;
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
	public ArrayList<TarjansNode> getChildren() {
		return this.children;
	}

	@Override
	public ArrayList<TarjansNode> getParents() {
		return this.parents;
	}

	@Override
	public void connectChild(TarjansNode child) {
		child.parents.add(this);
		this.children.add(child);		
	}

	@Override
	public Long getID() {
		return this.id;
	}

	@Override
	public int compareTo(DeNode<?, ?> o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
