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
				dg.reportSolution(map);
				return;
			}
			else {
				
				Visitor visitor = new Visitor(dg.remainder, dg, GraphFactory.locales.threads);
				visitor.visitAllInParallel();
				
				Node firstCome = dg.remainder.values().iterator().next();
				//System.out.println("focused node : " + firstCome);
				//System.out.println(firstCome.graph);
				//System.out.println(dg);
				ExplorePredecessors predecessors = new ExplorePredecessors(GraphFactory.locales.threads, firstCome, dg);
				ExploreDescendants descendants = new ExploreDescendants(GraphFactory.locales.threads, firstCome, dg);
				
				predecessors.startExploration();
				descendants.startExploration();
				
				predecessors.awaitExploration();				
				descendants.awaitExploration();
				ArrayList<UltimateRecurssion> tasks = new ArrayList<UltimateRecurssion>(3);
				//System.out.println(dg.scc);
				dg.reportSolution(dg.scc);
				
				DataGraph remainingPredecessors = new DataGraph(dg.predecessors, dg.rootGraph);
				DataGraph remainingDescendants = new DataGraph(dg.descendants, dg.rootGraph);
				DataGraph remainder = new DataGraph(dg.remainder, dg.rootGraph);
				
				//System.out.println("scc = " + dg.scc);
				//System.out.println("pred = " + dg.predecessors);
				//System.out.println("desc = " + dg.descendants);
				//System.out.println("rem = " + dg.remainder);
				tasks.add(new UltimateRecurssion(remainingPredecessors));
				tasks.add(new UltimateRecurssion(remainingDescendants));
				tasks.add(new UltimateRecurssion(remainder));
				
				invokeAll(tasks);
				return;
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
}
