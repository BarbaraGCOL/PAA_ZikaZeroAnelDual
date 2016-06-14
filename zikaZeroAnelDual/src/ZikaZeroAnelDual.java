import java.io.IOException;
import java.util.Set;

public class ZikaZeroAnelDual {

    /**
     * Calculates the smallest number of volunteers so that all 
     * focus are accessed by, at least, one volunteer and all the 
     * volunteers have Friendship bonds between each other.  
     * Paradigm: Brute Force
     * @param graph
     * @return set of volunteers that satisfies the preconditions
     */
    public Set<Integer> volunteersSearch(Graph graph, ParadigmEnum paradigm){

    	Extractor extractor = new Extractor();
    	Set<Integer> volunteers;
    	long startTime, endTime;
    	
        switch(paradigm){
            case BRUTE_FORCE:
                startTime = System.currentTimeMillis();
                volunteers = extractor.extractSolutionBruteForce(graph);
                endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
                System.gc();
                return volunteers;
            case GREEDY:
                startTime = System.currentTimeMillis();
                volunteers = extractor.extractSolutionGreedy(graph);
                endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
                System.gc();
                return volunteers;    
            case DYNAMIC:
                startTime = System.currentTimeMillis();
                volunteers = extractor.extractSolutionDynamic(graph);
                endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
                System.gc();
                return volunteers;    
            default:
                startTime = System.currentTimeMillis();
                volunteers = extractor.extractSolutionBruteForce(graph);
                endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
                System.gc();
                startTime = System.currentTimeMillis();
                volunteers = extractor.extractSolutionGreedy(graph);
                endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
                System.gc();
                startTime = System.currentTimeMillis();
                volunteers = extractor.extractSolutionDynamic(graph);
                endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
                System.gc();
                return volunteers;    
        }
    }

    public void runVolunteersSearch(ParadigmEnum paradigm, String[]args) {

        Graph graph = new Graph();
        
        args = new String[2];
        args[0]="in_n1000_r250";
        args[1]="out_n1000_r250";
        
//        args[0]="in0";
//        args[1]="out0";
        
        if(args.length == 2){

            try {
                graph.readGraphIn(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(graph.getVertexCount() > 0){
            
                Set<Integer> path = volunteersSearch(graph, paradigm);
    
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
}
