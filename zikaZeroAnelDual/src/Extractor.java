import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Extractor {

	public List<Integer> extractSolutionBruteForce(Graph graph){
		Set<Integer>vertices = graph.getVertices();
		Subgraph subgraph = new Subgraph(graph);
		int countVertices = graph.getVertexCount();
		int countFocus = graph.getFocusCount();

		Set<List<Integer>> subgraphs = new HashSet<List<Integer>>();
		List<Integer> extractedSubgraph;

		for(int vertex: vertices)
		{
			extractedSubgraph = new ArrayList<Integer>();
			extractedSubgraph.add(vertex);
			if(countFocus <= 2 && subgraph.coverAllFocus(extractedSubgraph)){
				return extractedSubgraph;
			}
			subgraphs.add(new ArrayList<Integer>(vertex));
			subgraphs.add(extractedSubgraph);
		}

		int lastVertex;

		Set<List<Integer>> subgraphsAux;

		for(int subgraphSize = 2 ; subgraphSize <= countVertices; subgraphSize++)
		{
			subgraphsAux = new HashSet<List<Integer>>();

			for(List<Integer> sub: subgraphs)
			{
				lastVertex = sub.get(sub.size() - 1);
				for(int vertex: graph.getAdjacency(lastVertex))
				{
					if(vertex > lastVertex){
						List<Integer> newSubgraph = new ArrayList<Integer>(sub);
						newSubgraph.add(vertex);
						if(countFocus <= (newSubgraph.size() * 2) && subgraph.coverAllFocus(newSubgraph)){
							return newSubgraph;
						}
						subgraphsAux.add(newSubgraph);
					}
				}
			}
			subgraphs = subgraphsAux;
		}

		return new ArrayList<Integer>(vertices);
	}
	public List<Integer> extractSolutionDynamic(Graph graph){
		
//		Map<Integer, Set<Integer>> focusList = new HashMap<Integer, Set<Integer>>();
//		int count = 0;
//		Set<Integer>vertices = graph.getVertices();
//		int countVertices = graph.getVertexCount();
//		int countFocus = graph.getFocusCount();
//
//		Set<List<Integer>> subgraphs = new HashSet<List<Integer>>();
//		List<Integer> extractedSubgraph;
//
//		for(int vertex: vertices)
//		{
//			extractedSubgraph = new ArrayList<Integer>();
//			extractedSubgraph.add(vertex);
//			focusList.put(count, graph.getFocus(vertex));
//			if(countFocus <= 2 && focusList.size() == countFocus){
//				return extractedSubgraph;
//			}
//			subgraphs.add(new ArrayList<Integer>(vertex));
//			subgraphs.add(extractedSubgraph);
//			count++;
//		}
//		count = 0;
//		int lastVertex;
//
//		Set<List<Integer>> subgraphsAux;
//
//		for(int subgraphSize = 2 ; subgraphSize <= countVertices; subgraphSize++)
//		{
//			subgraphsAux = new HashSet<List<Integer>>();
//
//			for(List<Integer> sub: subgraphs)
//			{
//				lastVertex = sub.get(sub.size() - 1);
//				for(int vertex: graph.getAdjacency(lastVertex))
//				{
//					if(vertex > lastVertex){
//						List<Integer> newSubgraph = new ArrayList<Integer>(sub);
//						newSubgraph.add(vertex);
//						if(focusList.get(count) == null){
//							focusList.put(count, new HashSet<Integer>());
//						}
//						focusList.get(count).addAll(graph.getFocus(vertex));
//						if(countFocus <= (newSubgraph.size() * 2) && (focusList.size() == countFocus)){
//							return newSubgraph;
//						}
//						subgraphsAux.add(newSubgraph);
//					}
//					count ++;
//				}
//			}
//			count = 0;
//			subgraphs = subgraphsAux;
//		}

		return null;//new ArrayList<Integer>(vertices);
	}
}
