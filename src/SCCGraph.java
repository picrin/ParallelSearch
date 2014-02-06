import java.util.concurrent.ConcurrentLinkedQueue;

public interface SCCGraph<NodeType extends DeNode<?>> extends Comparable<SCCGraph<DeNode<?>>>{

	public void addNode(NodeType node);
	ConcurrentLinkedQueue<NonBlockingHashMap<Long, NodeType>> getSolutions();
	public void reportSolution(NonBlockingHashMap<Long, NodeType> solution);

}
