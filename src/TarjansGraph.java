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
}

