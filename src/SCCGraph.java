import java.util.concurrent.ConcurrentLinkedQueue;

public interface SCCGraph<NodeType extends DeNode<?, ? extends SCCGraph<?>>>
                          extends Comparable<SCCGraph<? extends DeNode<?, ?>>>{

	public void addNode(NodeType node);
	ConcurrentLinkedQueue<NonBlockingHashMap<Long, NodeType>> getSolutions();
	public void reportSolution(NonBlockingHashMap<Long, NodeType> solution);
	
}
