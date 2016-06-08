import java.io.IOException;
import java.util.List;

public class ZikaZeroAnelDual {

    /**
     * Calculates the smallest number of volunteers so that all 
     * focus are accessed by, at least, one volunteer and all the 
     * volunteers have Friendship bonds between each other.  
     * Paradigm: Brute Force
     * @param graph
     * @return set of volunteers that satisfies the preconditions
     */
    public List<Integer> volunteersSearch(Graph graph, Paradigm paradigm){
        
        switch(paradigm){
            case BRUTE_FORCE:
                Extractor extractor = new Extractor();
                List<Integer> volunteers = extractor.extractSubgraphs(graph);
                return volunteers;
            default:
                break;
        }
        
        return null;
    }

    public void runVolunteersSearch(Paradigm paradigm, String[]args) {

        Graph graph = new Graph();
        
        args = new String[2];
        args[0]="in0";
        args[1]="out0";
        
        if(args.length == 2){

            try {
                graph.readGraphIn(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Integer> path = volunteersSearch(graph, paradigm);

            try {
                graph.saveOut(args[1], path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Incorrect Parameters!");
        }
    }
}