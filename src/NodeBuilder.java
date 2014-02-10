
public class NodeBuilder<NodeType extends DeNode<?, ?>> {
	private Class<NodeType> nodeClass;
	public NodeBuilder(Class<NodeType> node){
		this.nodeClass = node;
	}
	/*public GraphType newGraph(){
		try {
			return graphClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	public NodeType newNode(long id){
		NodeType node = null;
		try {
			node = nodeClass.newInstance();
			node.setID(id);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return node;
	}
}
