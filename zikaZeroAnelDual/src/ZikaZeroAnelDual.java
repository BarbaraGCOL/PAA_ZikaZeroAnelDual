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
    public List<Integer> volunteersSearch(Graph graph, ParadigmEnum paradigm){

    	Extractor extractor = new Extractor();
    	List<Integer> volunteers;
    	
        switch(paradigm){
            case BRUTE_FORCE:
                volunteers = extractor.extractSolutionBruteForceSentinel(graph);
                return volunteers;
            case GREEDY:
                volunteers = extractor.extractSolutionGreedy(graph);
                return volunteers;    
            case DYNAMIC:
                volunteers = extractor.extractSolutionDynamic(graph);
                return volunteers;    
            default:
                break;
        }
        
        return null;
    }

    public void runVolunteersSearch(ParadigmEnum paradigm, String[]args) {

        Graph graph = new Graph();
        
        args = new String[2];
        args[0]="in1000";
        args[1]="out1000";
        
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
