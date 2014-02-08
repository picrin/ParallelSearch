import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TarjansAlgorithm extends TarjansGraph{

	NonBlockingHashMap<Long, TarjansNode> SCCs = new NonBlockingHashMap<Long, TarjansNode>();
	protected Stack<TarjansNode> stack;
	protected long lowlink;
	protected long index;
	
	
	public TarjansAlgorithm(ArrayList<TarjansNode> nodes){
		this.index = 0;
		for(TarjansNode node: nodes){
			if( !node.visited() ){
				strongconnect(node);
				node.setVisited(true);
			}
		}
	}
	
	public void strongconnect(TarjansNode node){
		node.setIDtarjans(index);
		node.setLOWLINK(index);
		index+=1;
		stack.push(node);
		for( TarjansNode child: node.getChildren() ){
			child = node.getChild();
			if(!child.visited()){
				strongconnect(child);
				node.setLOWLINK(Math.min(node.getLOWLINK(),child.getLOWLINK()));
			}
			if(stack.contains(child)){
				node.setLOWLINK(Math.min(node.getLOWLINK(),child.getLOWLINK()));
			}
		}
		
	    // If v is a root node, pop the stack and generate an SCC
		if(node.IDtarjans==node.getLOWLINK()){
			while(node.IDtarjans==node.getLOWLINK()){
				TarjansNode w = stack.pop();
				SCCs.put(w.getID(), w);
			}
			solutions.add(SCCs);
		}
	}
	
	/**
	public void DFS(TarjansNode previousNode, ArrayList<TarjansNode> nodes){
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
	}
	
	public boolean isItStronglyConnected(long v, long w){
		return v==w;
	}
	**/
	
	
	
	
	
	
}
