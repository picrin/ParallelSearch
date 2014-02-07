import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AbstractGraph<NodeType extends DeNode<?, ?>> implements SCCGraph<NodeType> {
	
	private <T extends DeNode<?, ?>> TreeMap<Long, T>
			unorderedToOrdered(NonBlockingHashMap<Long, T> map){
		return new TreeMap<>(map);
	}
	
	@Override
	public int compareTo(SCCGraph<DeNode<?, SCCGraph<?>>> otherGraph) {
		// TODO Auto-generated method stub
		Iterator<NonBlockingHashMap<Long, DeNode<?, SCCGraph<?>>>> iterOther =
				otherGraph.getSolutions().iterator();

		Iterator<NonBlockingHashMap<Long, NodeType>> iterThis =
				this.getSolutions().iterator();
		int value = 0;
		while (iterThis.hasNext()){
			System.out.println(unorderedToOrdered(iterThis.next()));
			value += 1
		}
		return value;
	}

	@Override
	public void addNode(NodeType node) {
		// TODO Auto-generated method stub
	}

	@Override
	public ConcurrentLinkedQueue<NonBlockingHashMap<Long, NodeType>> getSolutions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reportSolution(NonBlockingHashMap<Long, NodeType> solution) {
		// TODO Auto-generated method stub
		
	}
}
