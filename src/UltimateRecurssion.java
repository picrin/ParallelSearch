import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

class UltimateRecurssion extends RecursiveAction{
	private static final long serialVersionUID = 8144351985196522347L;
	final protected DataGraph dg;
	public UltimateRecurssion(DataGraph dg){
		this.dg = dg;
	}
		@Override
	protected void compute(){
		try{
			if(dg == null) return;
			else if(dg.remainder.size() == 0) return;
			else if(dg.remainder.size() == 1){
				Node lastMohican = dg.remainder.values().iterator().next();
				NonBlockingHashMap<Long, Node> map = new NonBlockingHashMap<Long, Node>();
				map.put(lastMohican.id, lastMohican);
				DataGraph.solutions.add(map);
				return;
			}
			else {
				Node firstCome = dg.remainder.values().iterator().next();
				System.out.println(firstCome);
				System.out.println(firstCome.graph);
				System.out.println(dg);
				ExplorePredecessors predecessors = new ExplorePredecessors(GraphFactory.locales.threads, firstCome, dg);
				ExploreDescendants descendants = new ExploreDescendants(GraphFactory.locales.threads, firstCome, dg);
				
				predecessors.startExploration();
				
				predecessors.awaitTermination();
				
				descendants.startExploration();
				descendants.awaitTermination();
				ArrayList<UltimateRecurssion> tasks = new ArrayList<UltimateRecurssion>(3);
				
				dg.solutions.add(dg.scc);
				
				DataGraph remainingPredecessors = new DataGraph(dg.predecessors);
				DataGraph remainingDescendants = new DataGraph(dg.descendants);
				DataGraph remainder = new DataGraph(dg.remainder);
				
				System.out.println(dg.predecessors);
				System.out.println(dg.descendants);
				System.out.println(dg.remainder);

				tasks.add(new UltimateRecurssion(remainingPredecessors));
				tasks.add(new UltimateRecurssion(remainingDescendants));
				tasks.add(new UltimateRecurssion(remainder));
				
				invokeAll(tasks);
				return;
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
}
