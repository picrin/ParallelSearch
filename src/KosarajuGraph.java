import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;


public class KosarajuGraph extends AbstractGraph<TarjanNode>{

	// fields:
	protected LinkedList<TarjanNode> stack2;
	protected HashSet<TarjanNode> nodes;
	protected ConcurrentLinkedQueue<NonBlockingHashMap<Long, TarjanNode>> solutions;
	NonBlockingHashMap<Long, TarjanNode> solution = new NonBlockingHashMap<>();
	
	// constructor:
	KosarajuGraph(){
		stack2 = new LinkedList<>();
		nodes = new HashSet<TarjanNode>();
		solutions = new ConcurrentLinkedQueue<>();
	}
	
	// the algo starts from here 
	@Override
	public long start() {
		long startTime = System.currentTimeMillis();
		for(TarjanNode current: nodes){
			if(!current.isExplored()){ 
				explore(current);
			}
		}
		System.out.println(stack2);
		while(!stack2.isEmpty()){
			if(!solution.isEmpty()) { solutions.add(solution); }
			solution = new NonBlockingHashMap<>();
			TarjanNode current = stack2.pollFirst();
			if(!current.isSCCd()){
				sccConnect(current);
			}
		}
		System.out.println(getSolutions());
		long stopTime = System.currentTimeMillis();
		return stopTime - startTime; 
	}
	
	protected void explore(TarjanNode node){
		node.markExplored();
		stack2.push(node);
		for(TarjanNode child: node.children){
			if(!child.isExplored()){
				explore(child);
			}
		}
	}
	
	protected void sccConnect(TarjanNode node){
		node.markSCCd();
		solution.put(node.getID(), node);
		for(TarjanNode parent: node.getParents()){
			if(!parent.isSCCd()){
				sccConnect(parent);
			}
		}		
	}

	@Override
	public void addNode(TarjanNode node) {
		nodes.add(node);
	}

	@Override
	public ConcurrentLinkedQueue<NonBlockingHashMap<Long, TarjanNode>> getSolutions() {
		return solutions;
	}

	@Override
	public void reportSolution(NonBlockingHashMap<Long, TarjanNode> solution) {
		solutions.add(solution);
		
	}

	@Override
	public NonBlockingHashMap<Long, TarjanNode> getNodes() {
		NonBlockingHashMap<Long, TarjanNode> returned = new NonBlockingHashMap<>();
		for(TarjanNode node: nodes){
			returned.put(node.getID(), node);
		}
		return returned;
	}

}
