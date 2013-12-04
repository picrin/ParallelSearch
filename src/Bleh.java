import static org.junit.Assert.assertTrue;

import java.util.ArrayList;


import org.junit.Assert;


public class Bleh {


	public static void main(String args[]){
		GraphFactory.setProblemLocales(ProblemLocales.exampleLocales());
		DataGraph dg = GraphFactory.makeSanityCheckGraph();
		Node firstCome = dg.remainder.values().iterator().next();
		System.out.println("focused node : " + firstCome);
		//System.out.println(firstCome.graph);
		//System.out.println(dg);
		ExplorePredecessors predecessors = new ExplorePredecessors(GraphFactory.locales.threads, firstCome, dg);
		ExploreDescendants descendants = new ExploreDescendants(GraphFactory.locales.threads, firstCome, dg);
		
		predecessors.startExploration();
		descendants.startExploration();

		predecessors.awaitExploration();				
		descendants.awaitExploration();
		ArrayList<UltimateRecurssion> tasks = new ArrayList<UltimateRecurssion>(3);
		//System.out.println(dg.remainder);
		//System.out.println(dg.scc);
		//System.out.println(dg.predecessors);
		//System.out.println(dg.descendants);
		
		dg.solutions.add(dg.scc);
		
		DataGraph remainingPredecessors = new DataGraph(dg.predecessors);
		DataGraph remainingDescendants = new DataGraph(dg.descendants);
		DataGraph remainder = new DataGraph(dg.remainder);
		//System.out.println(remainder.remainder);
		//System.out.println(remainingPredecessors.remainder);
		System.out.println(remainingDescendants.remainder);
		/*Node secondCome = remainingDescendants.remainder.values().iterator().next();
		System.out.println("focused node : " + secondCome);
		ExplorePredecessors predecessors = new ExplorePredecessors(GraphFactory.locales.threads, secondCome, remai);
		ExploreDescendants descendants = new ExploreDescendants(GraphFactory.locales.threads, firstCome, dg);
		
		predecessors.startExploration();
		descendants.startExploration();

		predecessors.awaitExploration();				
		descendants.awaitExploration();
		*/
		
	}
	
}
