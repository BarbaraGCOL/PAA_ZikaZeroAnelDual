import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Extractor {

    public List<Integer> extractSolutionDynamic(Graph graph){



        return null;
    }

    public List<Integer> extractSolutionGreedy(Graph graph){



        return null;
    }

    public List<Integer> extractSolutionBruteForceSentinel(Graph graph){

        List<Integer>vertices = graph.getListVertices();
        List<Integer> subgraphAux = new LinkedList<Integer>();
        List<Integer> extractedSubgraph;
        int countVertices = graph.getVertexCount();
        int countFocus = graph.getFocusCount();
        int min = countFocus/2;
        int lastVertex = vertices.get(0);
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

        int first, last;

        for(int subgraphSize = min ; subgraphSize <= countVertices; subgraphSize++)
        {
            extractedSubgraph = new LinkedList<Integer>();

            first = 0; last = min; 
            extractedSubgraph = subgraphAux.subList(first, last);     

            if(verify(countFocus, extractedSubgraph, graph)){
                return extractedSubgraph;
            }

            for(int i = 0; i < countVertices - 1 ; i++){

                if(last == countVertices){
                    extractedSubgraph = subgraphAux.subList(first + 1, last);
                    extractedSubgraph.add(subgraphAux.get(0)); 
                }
                else{
                    first++; 
                    last++;
                    extractedSubgraph = subgraphAux.subList(first, last); 
                }

                if(verify(countFocus, extractedSubgraph, graph)){
                    return extractedSubgraph;
                }
            }
        }

        return vertices;
    }

    public List<Integer> extractSolutionBruteForce(Graph graph){

        List<Integer>vertices = graph.getListVertices();
        int countVertices = graph.getVertexCount();
        int countFocus = graph.getFocusCount();
        int lastVertex, newVertex, min = countFocus/2;

        List<Integer> extractedSubgraph;

        for(int subgraphSize = min ; subgraphSize <= countVertices; subgraphSize++)
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
        return vertices;
    }

    public boolean verify(int countFocus, List<Integer> extractedSubgraph, Graph graph){
        if(countFocus <= (extractedSubgraph.size() * 2)){
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
        return false;
    }
}
