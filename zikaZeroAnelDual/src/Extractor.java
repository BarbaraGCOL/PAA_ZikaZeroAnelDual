import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Extractor {

    public List<Integer> extractSubgraphs(Graph graph){
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
            subgraphs.add(extractedSubgraph);
        }

        int lastVertex;

        for(int subgraphSize = 2 ; subgraphSize <= countVertices; subgraphSize++)
        {
            Set<List<Integer>> subgraphsAux = new HashSet<List<Integer>>();

            for(List<Integer> sub: subgraphs)
            {
                if(sub.size() == subgraphSize -1){
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
            }
            subgraphs.addAll(subgraphsAux);
        }

        return new ArrayList<Integer>(vertices);
    }
}
