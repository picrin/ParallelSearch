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
			//System.out.println("finished at: " + currentNode.filename);

			return;
		}
		//System.out.println("putting to hashmap: " + currentNode.filename);
		startNode.dependencies.put(currentNode, 0);
		
		ArrayList<Worker> workers = new ArrayList<Worker>(startNode.children.size());
		//System.out.println(currentNode.filename + "has got" + currentNode.children.get(0).filename);
		for (Node child: currentNode.children){
			//System.out.println("adding children: " + child.filename);			
			workers.add(new Worker(startNode, child));
		}
		invokeAll(workers);
	}
	
}
