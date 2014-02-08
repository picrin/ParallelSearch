import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;


public class TreeMapComparator implements Comparator<TreeMap<Long, ? extends DeNode<?,?>>> {

	@Override
	public int compare(TreeMap<Long, ? extends DeNode<?, ?>> hashTree1,
			TreeMap<Long, ? extends DeNode<?, ?>> hashTree2) {
		/*
		 * TODO this should be either made much simpler, or much more robust.
		 * In either case, completely rewritten. 
		 */
		if(hashTree1 == hashTree2) return 0;
		if(hashTree1.size() < hashTree2.size()) return -1;
		else if(hashTree1.size() > hashTree2.size())return 1;
		else{
			Iterator<Long> iter1 = hashTree1.navigableKeySet().iterator();
			Iterator<Long> iter2 = hashTree2.navigableKeySet().iterator();
			while(iter1.hasNext()){
				int nodeCompared = iter1.next().compareTo(iter2.next());
				//System.out.println("bleh" + nodeCompared);
				if(nodeCompared != 0){
					return nodeCompared;
				}
			}
			return 0;
		}
	}
	
}
