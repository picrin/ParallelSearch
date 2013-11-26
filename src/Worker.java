import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class Worker extends RecursiveAction{
	private static final long serialVersionUID = -5283490868734796087L;
	Node startNode;
	Node currentNode;
	
	public Worker(Node startNode, Node currentNode){
		this.startNode = startNode;
		this.currentNode = currentNode;
	}
	
	@Override
	protected void compute(){
		if (startNode.dependencies.containsKey(currentNode)){
			return;
		}
		
		startNode.dependencies.put(currentNode, null);
		
		ArrayList<Worker> workers = new ArrayList<Worker>(startNode.children.size());
		
		for (Node child: startNode.children){
			workers.add(new Worker(startNode, child));
		}
		invokeAll(workers);
	}
	
}
