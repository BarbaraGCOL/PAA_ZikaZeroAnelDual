import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to Represent a Subgraph
 * @author barbara.lopes
 *
 */
public class Subgraph {
	
	private Graph graph;
	
	public Subgraph(Graph graph){
		this.graph = graph;
	}
	
	/**
	 * See if the nodes of the subgraph cover all existent focus of the original graph
	 * @param combination
	 * @return true if the subgraph cover all focus, false if it doesn't
	 */
	public boolean coverAllFocus(List<Integer> combination){
		
		Set<Integer> coverFocus = new HashSet<Integer>();
		
		for(int node: combination){
			coverFocus.addAll(graph.getFocus(node));
		}
		if(coverFocus.size() == graph.getFocusCount())
			return true;
		return false;
	}
}
