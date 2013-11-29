import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Main {
	public static void main(String[] args) throws InterruptedException{
		LinkedList<Node> nodes = new LinkedList<Node>();
		int bigNumber = 1_000_000;
		for(int i = 0; i<bigNumber; i++){
			nodes.add(new Node(i, GraphFactory.maxChildren));
		}
		final long start = System.currentTimeMillis();
		for(Node node: nodes){
			node.visit();
		}
		final long stop = System.currentTimeMillis();
		GraphFactory.nodesNo = bigNumber;
        GraphFactory.threadsNoEnd = 2;
        GraphFactory.maxChildren = 10;
		GraphFactory.threadsNoStart = 1;
		GraphFactory.measurmentsPerThread = 1;
        GraphFactory.results = new LinkedHashMap<Integer, ArrayList<Long>>();
        System.out.println("linkedList returned in " + (stop - start));
        PerformanceRoR.measurePerThread(1);
		PerformanceRoR.prettyPrint();
	}
}
