import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractGraph<NodeType extends DeNode<?, ?>> implements SCCGraph<NodeType> {
	
	private <T extends DeNode<?, ?>> TreeMap<Long, T>
			unorderedToOrdered(NonBlockingHashMap<Long, T> map){
		return new TreeMap<>(map);
	}
	
	//@Override
	public int compareTo(SCCGraph<? extends DeNode<?, ?>> otherGraph) {
		System.out.println("hello equals!");
		// TODO Auto-generated method stub
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Iterator<NonBlockingHashMap<Long, ? extends DeNode<?, ?>>> iterOther =
				(Iterator) otherGraph.getSolutions().iterator();

		Iterator<NonBlockingHashMap<Long, NodeType>> iterThis =
				this.getSolutions().iterator();
		
		while (iterOther.hasNext()){
			System.out.println("hello");
			System.out.println(unorderedToOrdered(iterThis.next()));
			
		}
		return -1;
	}

}
