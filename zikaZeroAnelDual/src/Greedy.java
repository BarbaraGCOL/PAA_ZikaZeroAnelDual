import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Greedy {

	public static void main(String[] args) {

		GraphGreedy graph = new GraphGreedy();
		if(args.length == 2){

			try {
				graph.readGraphIn(args[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(graph.getVertexCount() > 0){
				Set<Integer> path = extractSolutionGreedy(graph);

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

	public static Set<Integer> extractSolutionGreedy(GraphGreedy graph){

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
				return new LinkedHashSet<Integer>(extractedSubgraph);
			}
		}		
		return new HashSet<Integer>();
	}
}
