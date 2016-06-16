import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Extractor {

	public Set<Integer> extractSolutionDynamic(Graph graph){
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

		Iterator<Integer> it;

		for(int t = 1; t < countVertices; t++){
			it = graph.getAdjacency(lastVertex).iterator();
			newVertex = it.next();
			if(newVertex == lastVertex || subgraphAux.contains(newVertex)){
				newVertex = it.next();
			}
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

					List<Integer>extractedSubgraph = new ArrayList<Integer>(subgraphAux.subList(i, subgraphSize));
					subgraphAux = null;
					Collections.sort(extractedSubgraph);
					return new HashSet<Integer>(extractedSubgraph);
				}

				lastIndex ++;
			}
		}

		focusSolutions.get(0).addAll(graph.getFocus(subgraphAux.get(countVertices - 1)));

		if(focusSolutions.get(0).size() == countFocus){
			List<Integer>extractedSubgraph = new ArrayList<Integer>(subgraphAux);
			subgraphAux = null;
			Collections.sort(extractedSubgraph);
			return new HashSet<Integer>(extractedSubgraph);
		}

		return new HashSet<Integer>();
	}

	public Set<Integer> extractSolutionGreedy(Graph graph){

		Set<Integer> subgraph = new HashSet<Integer>();
		int countVertices = graph.getVertexCount();
		int countFocus = graph.getFocusCount();
		int min = countFocus/2 + countFocus % 2;

		int[]count = new int[countVertices];
		Set<Integer> focusV;
		Iterator<Integer> it;
		int f1, f2;
		
		if(min == 1 && (graph.getFocus(1).size() == countFocus)){
			subgraph.add(1);
			return subgraph;
		}
		
		if(countVertices < min){
			return new HashSet<Integer>();
		}

		// Calculates number of conflicts on each vertex
		for(int i = 0; i < countVertices; i++){
			focusV = graph.getFocus(i + 1);
			it = focusV.iterator();
			f1 = it.next(); f2 = it.next();

			for(int v = 0; v < countVertices; v++){
				if(v != i){
					focusV = graph.getFocus(v + 1); 
					if(focusV.contains(f1)){
						count[i] = count[i]+1;
					}
					if(focusV.contains(f2)){
						count[i] = count[i]+1;
					}
				}
			}
		}

		int shortest = count[0], firstVertex = 1;

		// Search the vertex with lower number of conflicts
		for (int i = 1; i<countVertices; i++) {
			if (count[i] < shortest){
				shortest = count[i];
				firstVertex = i + 1;
			}
		}

		Set<Integer> coverFocus = new HashSet<Integer>();
		int lastVertex = firstVertex;

		//Add the vertex with lower number of conflicts
		subgraph.add(firstVertex);

		coverFocus.addAll(graph.getFocus(firstVertex));

		int adj1, adj2;

		for(int size = 2; size <= countVertices; size++){
			it = graph.getAdjacency(firstVertex).iterator();

			adj1 = it.next();
			if(subgraph.contains(adj1)){
				adj1 = it.next();
			}

			it = graph.getAdjacency(lastVertex).iterator();

			adj2 = it.next();
			if(adj1 == adj2 || subgraph.contains(adj2)){
				adj2 = it.next();
			}

			boolean adj1Add = !coverFocus.containsAll(graph.getFocus(adj1));
			boolean adj2Add = !coverFocus.containsAll(graph.getFocus(adj2));

			int menor = adj1;

			if(count[adj2 - 1] < count[adj1 - 1]){
				menor = adj2;
			}

			if((menor == adj1 && adj1Add) || !adj2Add){
				subgraph.add(adj1);
				firstVertex = adj1;
				coverFocus.addAll(graph.getFocus(firstVertex));
			}
			else{
				subgraph.add(adj2);
				lastVertex = adj2;
				coverFocus.addAll(graph.getFocus(lastVertex));
			}

			//Verify if solution covers all vertex
			if(size >= min && coverFocus.size() == countFocus){
				List<Integer>extractedSubgraph = new ArrayList<Integer>(subgraph);
				subgraph = null;
				Collections.sort(extractedSubgraph);
				return new HashSet<Integer>(extractedSubgraph);
			}
		}		
		return new HashSet<Integer>();
	}

	public Set<Integer> extractSolutionBruteForce(Graph graph){

		List<Integer>vertices = graph.getListVertices();
		int countVertices = graph.getVertexCount();
		int countFocus = graph.getFocusCount();
		int lastVertex, newVertex, min = countFocus/2;

		Set<Integer> extractedSubgraph;
		Set<Integer> coverFocus;
		Iterator<Integer>it;

		if(countVertices < min){
			return new HashSet<Integer>();
		}

		for(int subgraphSize = min ; subgraphSize < countVertices - 1; subgraphSize++)
		{
			for(int i = 0; i < countVertices; i++){
				coverFocus = new HashSet<Integer>();
				extractedSubgraph = new HashSet<Integer>();
				lastVertex = vertices.get(i);
				extractedSubgraph.add(lastVertex);
				coverFocus.addAll(graph.getFocus(lastVertex));

				for(int size = 1; size < subgraphSize; size++){
					it = graph.getAdjacency(lastVertex).iterator();

					newVertex = it.next();
					if(extractedSubgraph.contains(newVertex)){
						newVertex = it.next();
					}
					extractedSubgraph.add(newVertex);
					coverFocus.addAll(graph.getFocus(newVertex));
					lastVertex = newVertex;
				}

				if(coverFocus.size() == graph.getFocusCount()){
					List<Integer>subgraph = new ArrayList<Integer>(extractedSubgraph);
					extractedSubgraph = null;
					Collections.sort(subgraph);
					return new HashSet<Integer>(subgraph);
				}
			}
		}

		extractedSubgraph = null;

		coverFocus = new HashSet<Integer>();
		for(int node: vertices){
			coverFocus.addAll(graph.getFocus(node));
		}
		if(coverFocus.size() == graph.getFocusCount()){
			Collections.sort(vertices);
			return new HashSet<Integer>(vertices);
		}

		return new HashSet<Integer>();
	}
}
