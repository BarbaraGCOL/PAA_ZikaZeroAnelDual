import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to represent a undirected and connected 
 * Graph (from a input file)
 * @author barbara.lopes
 *
 */
public class Graph {

	private HashMap<Integer, Integer> adjacencyList;
	private Set<Integer>[] focusList;
	private Set<Integer> focus;
	private int vertexCount, focusCount, edgesCount;

	/**
	 * Constructor
	 */
	public Graph(){
		focus = new HashSet<Integer>();
		adjacencyList = new HashMap<Integer, Integer>();
	}

	/**
	 * Add a edge on the AdjacencyList
	 * @param i - vertex 1
	 * @param j - vertex 2
	 */
	public void addEdge(int i, int j) {
		
		if (i > 0 && i <= vertexCount && j > 0 && j <= vertexCount) {
			adjacencyList.put(i, j);
		}
	}

	/**
	 * Add focus of the vertex on the list 
	 * @param i - vertex
	 * @param focus - vector of focus
	 */
	public void addFocus(int i, Set<Integer> focusV) {
		if (i >= 0 && i < vertexCount) {
			// Add all focus of the vertex
			focusList[i] = focusV;
		}
		focus.addAll(focusV);
	}

	/**
	 * Read Graph structure of file 
	 * @param path - file path
	 * @return Graph Class instance
	 * @throws IOException
	 */
	public void readGraphIn(String nomArq) throws IOException{

		int countLines, index = 0;
		String[]values;
		int v1, v2;
		Set<Integer> focusV = null;

		try { 

			FileReader arq = new FileReader(nomArq); 
			BufferedReader lerArq = new BufferedReader(arq); 
			String line = lerArq.readLine(); 
			countLines = 1;
			
			while (line != null) { 
				values = line.split(" ");
				
				// the first line of the file - vertices and edges size (m and n values)
				if(countLines == 1){
					vertexCount = Integer.parseInt(values[0]);
					edgesCount = Integer.parseInt(values[1]);
				}
				else
					// If still are edges to read
					if(countLines <= edgesCount + 1){
					
						v1 = Integer.parseInt(values[0]);
						v2 = Integer.parseInt(values[1]);
						addEdge(v1, v2);
					}
					else{
						// First line of focus - focus size (r value)
						if(countLines == edgesCount + 2){
							focusCount = Integer.parseInt(values[0]);
							focusList = new HashSet[vertexCount];
						}
						else{
							// If is reading Focus
							focusV = new HashSet<Integer>();
							for(String value: values){
								focusV.add(Integer.parseInt(value));
							}
							addFocus(index, focusV);
							index ++;
						}
				}

				line = lerArq.readLine(); 
				countLines ++;
			} 
			arq.close(); 
		} catch (IOException e) { 
			System.err.printf("Unable to open file: %s.\n", e.getMessage()); 
		} 
	}

	/**
	 * Save Graph output on file
	 * @param path - output file path
	 * @param out - output to be save
	 * @throws IOException
	 */
	public void saveOut(String path, Set<Integer> out) throws IOException{

		FileWriter arq = new FileWriter(path); 
		PrintWriter gravarArq = new PrintWriter(arq); 

		// Save out
		for(int value: out){
			gravarArq.print(value+" "); 
		}

		arq.close(); 
		System.out.println(path+" file saved with success!!!");
	}

	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public int getFocusCount() {
		return focusCount;
	}

	public List<Integer> getListVertices(){
        return new ArrayList<Integer>(adjacencyList.keySet());
    }
	
	public Set<Integer> getVertices(){
		return adjacencyList.keySet();
	}
	
	public Integer getAdjacency(int vertex){
		return adjacencyList.get(vertex);
	}
	
	public Set<Integer> getFocus(int vertex){
		return focusList[vertex - 1];
	}
	
	public Set<Integer> getListFocus(){
		return focus;
	}
}