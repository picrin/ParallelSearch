import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;


public class TarjansGraph extends AbstractGraph<TarjansNode>{
	
	ArrayList<TarjansNode> nodes = new ArrayList<>();
	ConcurrentLinkedQueue<NonBlockingHashMap<Long, TarjansNode>> solutions = new ConcurrentLinkedQueue<NonBlockingHashMap<Long, TarjansNode>>();
	
	// CONSTRUCTORS:
	public TarjansGraph(){}; 
	
	public TarjansGraph(ArrayList<TarjansNode> nodes){
		this.nodes = nodes;
	}
	
	// GETTER and SETTER methods:
	public ArrayList<TarjansNode> getNodes(){
		return this.nodes;
	}
	
	@Override
	public void addNode(TarjansNode node) {
		node.setGraph(this);
		this.nodes.add(node);
	}

	@Override
	public ConcurrentLinkedQueue<NonBlockingHashMap<Long, TarjansNode>> getSolutions() {
		return this.solutions;
	}

	@Override
	public void reportSolution(NonBlockingHashMap<Long, TarjansNode> solution) {}
	
	
	/**
	
	public ArrayList<TarjansNode> DFS(TarjansNode previousNode, ArrayList<TarjansNode> nodes){
		Stack<TarjansNode> stack = new Stack<TarjansNode>();
		stack.push(previousNode);
		ArrayList<TarjansNode> result = new ArrayList<>();
		result.add(previousNode);
		previousNode.setVisited(true);
		while(!stack.isEmpty()){
			TarjansNode node = (TarjansNode)stack.peek();
			TarjansNode child = node.getChild();
			if(child!=null){
				child.setVisited(true);
				stack.push(child);
				result.add(child);
			}
			else{ stack.pop(); }
		}
		for(TarjansNode node: result){ node.setVisited(false);}
		System.out.println("here " + result);
		System.out.println("************");
		return result;
	}

	public ConcurrentLinkedQueue<NonBlockingHashMap<Long, TarjansNode>> strongconnect(TarjansNode node){
		long lowlinkVal = node.LOWLINK(node,DFS(node,this.nodes));
		if(lowlinkVal==node.getID()){
			NonBlockingHashMap<Long, TarjansNode> result = new NonBlockingHashMap<Long, TarjansNode>();
			result.put(node.getID(), node);
			solution.add(result);
		}
		for(long key: solution.peek().keySet()){
			if( lowlinkVal==key ){ 
				solution.peek().put(node.getID(), node); 
			}
		}
		return solution;
	}
	
	public void TarjansAlgo(ArrayList<TarjansNode> nodes){
		for(TarjansNode node: nodes){
			if(!node.hasLowlink){ strongconnect(node); }
		}

	}
	**/
}

