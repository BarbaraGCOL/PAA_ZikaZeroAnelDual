import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Extractor {

	public List<Integer> extractSolutionDynamic(Graph graph){

		return new LinkedList<Integer>();
	}

	public List<Integer> extractSolutionGreedy(Graph graph){

		return null;
	}

	public List<Integer> extractSolutionBruteForceSentinel(Graph graph){

		List<Integer> subgraphAux = new LinkedList<Integer>();
		List<Integer> extractedSubgraph;
		int countVertices = graph.getVertexCount();
		int countFocus = graph.getFocusCount();
		int min = countFocus/2;
		int lastVertex = graph.getListVertices().get(0);
		int newVertex;

		subgraphAux.add(lastVertex);

		for(int t = 1; t < countVertices; t++){
			newVertex = graph.getAdjacency(lastVertex).get(0);
			if(newVertex == lastVertex){
				newVertex = graph.getAdjacency(lastVertex).get(1);
			}
			subgraphAux.add(newVertex);
			lastVertex = newVertex;
		}

		System.gc();

		int first, last;

		for(int subgraphSize = min ; subgraphSize < countVertices - 1; subgraphSize++)
		{
			System.gc();

			extractedSubgraph = new LinkedList<Integer>();

			first = 0; last = subgraphSize - 1; 
//			extractedSubgraph = subgraphAux.subList(first, last);     

//			if(verify(countFocus, extractedSubgraph, graph)){
			extractedSubgraph = verifyWithSentinel(countFocus, first, last, countVertices, subgraphAux, graph);
			if(extractedSubgraph != null){
				
				return extractedSubgraph;
			}

			System.gc();

			for(int i = 0; i < countVertices; i++){

//				if(last >= countVertices){
//					extractedSubgraph = subgraphAux.subList(first, countVertices);
//					extractedSubgraph.addAll(subgraphAux.subList(0, last - (countVertices - 1))); 
//				}
//				else{
//					extractedSubgraph = subgraphAux.subList(first, last); 
//				}

//				if(verify(countFocus, extractedSubgraph, graph)){
				extractedSubgraph = verifyWithSentinel(countFocus, first, last, countVertices, subgraphAux, graph);
				if(extractedSubgraph != null){
					
					return extractedSubgraph;
				}

				first++; 
				last++;

				System.gc();
			}
		}

		if(verify(countFocus, subgraphAux, graph)){
			return subgraphAux;
		}

		return new LinkedList<Integer>();
	}

	
	public List<Integer> extractSolutionBruteForce(Graph graph){

		List<Integer>vertices = graph.getListVertices();
		int countVertices = graph.getVertexCount();
		int countFocus = graph.getFocusCount();
		int lastVertex, newVertex, min = countFocus/2;

		List<Integer> extractedSubgraph;

		for(int subgraphSize = min ; subgraphSize < countVertices - 1; subgraphSize++)
		{
			extractedSubgraph = new LinkedList<Integer>();
			lastVertex = vertices.get(0);
			extractedSubgraph.add(lastVertex);

			for(int t = 1; t < subgraphSize; t++){
				newVertex = graph.getAdjacency(lastVertex).get(0);
				if(newVertex == lastVertex){
					newVertex = graph.getAdjacency(lastVertex).get(1);
				}
				extractedSubgraph.add(newVertex);
				lastVertex = newVertex;
			}

			if(verify(countFocus, extractedSubgraph, graph)){
				return extractedSubgraph;
			}

			for(int i = 0; i < countVertices - 1 ; i++){

				newVertex = graph.getAdjacency(lastVertex).get(0);
				if(newVertex==lastVertex || extractedSubgraph.contains(newVertex)){
					newVertex = graph.getAdjacency(lastVertex).get(1);
				}

				extractedSubgraph.remove(0);
				extractedSubgraph.add(newVertex);
				lastVertex = newVertex;

				if(verify(countFocus, extractedSubgraph, graph)){
					return extractedSubgraph;
				}
			}
		}
		
		if(verify(countFocus, vertices, graph)){
			return vertices;
		}
		
		return new LinkedList<Integer>();
	}

	public List<Integer> verifyWithSentinel(int countFocus, int start, int stop, int countVertices, 
			List<Integer> subgraph, Graph graph){
		
		int count = stop - start + 1;
				
		Set<Integer> coverFocus = new HashSet<Integer>();
		int i, v, index = start;
		
		List<Integer> finalSubgraph = new ArrayList<Integer>();
		
		for(i = 0; i < count; i ++){
			v = subgraph.get(index);
			coverFocus.addAll(graph.getFocus(v));
			finalSubgraph.add(v);
			
			if(index == countVertices - 1){
				index = 0;
			}
			else{
				index ++;
			}
		}
		
		if(coverFocus.size() == graph.getFocusCount()){
			if(stop >= countVertices){
				finalSubgraph = subgraph.subList(start, countVertices);
				finalSubgraph.addAll(subgraph.subList(0, stop - (countVertices - 1))); 
			}
			else{
				finalSubgraph = subgraph.subList(start, stop); 
			}
			Collections.sort(finalSubgraph);
			return finalSubgraph;
		}
		return null;
	}
	
	public boolean verify(int countFocus, List<Integer> extractedSubgraph, Graph graph){
		Set<Integer> coverFocus = new HashSet<Integer>();
		for(int node: extractedSubgraph){
			coverFocus.addAll(graph.getFocus(node));
		}
		if(coverFocus.size() == graph.getFocusCount()){
			Collections.sort(extractedSubgraph);
			return true;
		}
		return false;
	}
}
