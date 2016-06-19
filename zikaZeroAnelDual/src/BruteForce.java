import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BruteForce {

    public static void main(String[] args) {
    	
    	Graph graph = new Graph();
    	
        if(args.length == 2){

            try {
                graph.readGraphIn(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(graph.getVertexCount() > 0){
            
            	System.gc();
            	Set<Integer> path = extractSolutionBruteForce(graph);
    
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
    
	public static Set<Integer> extractSolutionBruteForce(Graph graph){

		List<Integer>vertices = graph.getListVertices();
		int countVertices = graph.getVertexCount();
		int countFocus = graph.getFocusCount();
		int newVertex, min = countFocus/2;

		Set<Integer> extractedSubgraph;
		Set<Integer> coverFocus;

		if(countVertices < min){
			return new HashSet<Integer>();
		}

		for(int subgraphSize = min ; subgraphSize < countVertices - 1; subgraphSize++)
		{
			for(int i = 0; i < countVertices; i++){
				coverFocus = new HashSet<Integer>();
				extractedSubgraph = new HashSet<Integer>();
				newVertex = vertices.get(i);
				extractedSubgraph.add(newVertex);
				coverFocus.addAll(graph.getFocus(newVertex));

				newVertex = graph.getAdjacency(newVertex);
				
				for(int size = 1; size < subgraphSize; size++){
					extractedSubgraph.add(newVertex);
					coverFocus.addAll(graph.getFocus(newVertex));
					newVertex = graph.getAdjacency(newVertex);
				}

				if(coverFocus.size() == graph.getFocusCount()){
					List<Integer>subgraph = new ArrayList<Integer>(extractedSubgraph);
					extractedSubgraph = null;
					Collections.sort(subgraph);
					return new LinkedHashSet<Integer>(subgraph);
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
