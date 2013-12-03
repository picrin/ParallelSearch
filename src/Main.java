import java.util.ArrayList;


public class Main{
	
	public static void main(String[] args){
		GraphFactory.setProblemLocales(ProblemLocales.exampleLocales());
		DataGraph dg = GraphFactory.makeSanityCheckGraph();
		
		//System.out.println(dg.remainder.get(1L).id);
		
		ExplorePredecessors predecessors = new ExplorePredecessors(4, dg.remainder.get(3L), dg);
		ExploreDescendants descendants = new ExploreDescendants(4, dg.remainder.get(3L), dg);
		
		predecessors.startExploration();
		descendants.startExploration();
		
		predecessors.awaitTermination();
		descendants.awaitTermination();
		
		System.out.println(dg.scc);
		
		//System.out.println(dg.remainder);
	}
	
	
}
