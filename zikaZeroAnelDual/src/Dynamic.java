import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dynamic {

	public static void main(String[] args) {
		Graph graph = new Graph();
		
		if(args.length == 2){

			try {
				graph.readGraphIn(args[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(graph.getVertexCount() > 0){
				Set<Integer> path = extractSolutionDynamic(graph);

				try {
					graph.saveOut(args[1], path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else{
			System.out.println("Incorrect Parameters!");
		}
	}

	public static Set<Integer> extractSolutionDynamic(Graph graph){
		int countVertices = graph.getVertexCount();
		int countFocus = graph.getFocusCount();
		int min = countFocus/2;

		List<Integer> subgraphAux = new ArrayList<Integer>();
		int lastVertex = graph.getListVertices().get(0);
		int newVertex;

		if(countVertices < min){
			return new HashSet<Integer>();
		}

		subgraphAux.add(lastVertex);

		for(int t = 1; t < countVertices; t++){
			newVertex = graph.getAdjacency(lastVertex);
			subgraphAux.add(newVertex);
			lastVertex = newVertex;
		}

		Map<Integer, Set<Integer>> focusSolutions = new HashMap<Integer, Set<Integer>>();
		int i, lastIndex;
		for(int subgraphSize = 1 ; subgraphSize < countVertices - 1; subgraphSize++){
			lastIndex = subgraphSize - 1;
			for(i = 0; i < countVertices; i++){

				if(focusSolutions.get(i) == null){
					focusSolutions.put(i, new HashSet<Integer>()); 
				}

				if(lastIndex == countVertices){
					lastIndex = 0;
				}

				focusSolutions.get(i).addAll(graph.getFocus(subgraphAux.get(lastIndex)));

				if(subgraphSize >= min && (focusSolutions.get(i).size() == countFocus)){
					List<Integer>extractedSubgraph = new ArrayList<Integer>();
					int last = subgraphAux.get(i);
					extractedSubgraph.add(last);
					int newV;
					for(int v = 1; v < subgraphSize; v++){
						newV = graph.getAdjacency(last);
						extractedSubgraph.add(newV);
						last = newV;
					}
					subgraphAux = null;
					Collections.sort(extractedSubgraph);
					return new LinkedHashSet<Integer>(extractedSubgraph);
				}

				lastIndex ++;
			}
		}

		focusSolutions.get(0).addAll(graph.getFocus(subgraphAux.get(countVertices - 1)));

		if(focusSolutions.get(0).size() == countFocus){
			List<Integer>extractedSubgraph = new ArrayList<Integer>(subgraphAux);
			subgraphAux = null;
			Collections.sort(extractedSubgraph);
			return new LinkedHashSet<Integer>(extractedSubgraph);
		}

		return new HashSet<Integer>();
	}
}