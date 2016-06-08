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
        
        Set<List<Integer>> combinations = new HashSet<List<Integer>>();
        List<Integer> combination;

        for(int vertex: vertices)
        {
            combination = new ArrayList<Integer>();
            combination.add(vertex);
            if(countFocus <= 2 && subgraph.coverAllFocus(combination)){
                return combination;
            }
            combinations.add(combination);
        }

        int lastVertex;

        for(int combinationSize = 2 ; combinationSize <= countVertices; combinationSize++)
        {
            Set<List<Integer>> combinationsAux = new HashSet<List<Integer>>();

            for(List<Integer> comb: combinations)
            {
                if(comb.size() == combinationSize -1){
                    lastVertex = comb.get(comb.size() - 1);
                    for(int vertex: graph.getAdjacency(lastVertex))
                    {
                        if(vertex > lastVertex){
                            List<Integer> newComb = new ArrayList<Integer>(comb);
                            newComb.add(vertex);
                            if(countFocus <= (newComb.size() * 2) && subgraph.coverAllFocus(newComb)){
                                return newComb;
                            }
                            combinationsAux.add(newComb);
                        }
                    }
                }
            }
            combinations.addAll(combinationsAux);
        }

        return new ArrayList<Integer>(vertices);
    }
}
