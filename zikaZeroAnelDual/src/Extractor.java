import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Extractor {

    public List<Integer> extractSolutionBruteForce(Graph graph){
        
        List<Integer>vertices = graph.getListVertices();
        int countVertices = graph.getVertexCount();
        int countFocus = graph.getFocusCount();
        int lastVertex, newVertex;
        
        List<Integer> extractedSubgraph;

        for(int subgraphSize = 1 ; subgraphSize <= countVertices; subgraphSize++)
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
            
//            extractedSubgraph.addAll(vertices.subList(0, subgraphSize));
//            lastVertex =  extractedSubgraph.get(subgraphSize - 1);
                    
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
