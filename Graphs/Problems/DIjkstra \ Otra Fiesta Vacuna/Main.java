import java.util.*;
import java.io.*;

// Link : https://dmoj.uclv.edu.cu/problem/sparty

public class Main {

    public static void main(String args[]) throws IOException {
        _Scanner reader = new _Scanner(System.in);
        FastWriter writer = new FastWriter();

        int n = reader.nextInt();
        int m = reader.nextInt();
        int x = reader.nextInt();


        List<List<Edge> > graph = new ArrayList<>();

        for(int i = 0 ;i<n;i++)
        {
            graph.add(new ArrayList<>());
        }

        for (int p = 0; p < m; p++) {
            int a = reader.nextInt() - 1;
            int b = reader.nextInt() - 1;
            int c = reader.nextInt();
            graph.get(a).add(new Edge(b,c)); 
        }

        AdjacencyList ady = new AdjacencyList(graph,n);

        try
        {
            long[] dist = ady.Dijsktra(""+(x-1) );

            long ans = 0;

            for(int i=0;i<n;i++)
            {
                long[] dist2 = ady.Dijsktra(""+i);

                ans = Math.max(ans,dist[i]+dist2[x-1]);
            }

            writer.print(ans);

        }
        catch(Exception e)
        {
            writer.print("IMPOSSIBLE");
        }
        
        
        writer.flush();
        writer.close();
    }
}
 
class _Scanner {
    InputStream is;

    _Scanner(InputStream is) {
        this.is = is;
    }

    byte[] bb = new byte[1 << 15];
    int k, l;

    byte getc() throws IOException {
        if (k >= l) {
            k = 0;
            l = is.read(bb);
            if (l < 0) return -1;
        }
        return bb[k++];
    }

    byte skip() throws IOException {
        byte b;
        while ((b = getc()) <= 32) ;
        return b;
    }

    int nextInt() throws IOException {
        int n = 0;
        for (byte b = skip(); b > 32; b = getc()) n = n * 10 + b - '0';
        return n;
    }

    float nextFloat() throws IOException {
        int integerPart = 0;
        float decimalPart = 0.0f;
        int divisor = 1;
        boolean isNegative = false;

        byte b = skip();
        if (b == '-') {
            isNegative = true;
            b = getc();
        }

        while ('0' <= b && b <= '9') {
            integerPart = integerPart * 10 + b - '0';
            b = getc();
        }

        if (b == '.') {
            b = getc();
            while ('0' <= b && b <= '9') {
                decimalPart = decimalPart * 10 + b - '0';
                divisor *= 10;
                b = getc();
            }
        }

        float result = integerPart + decimalPart / divisor;
        return isNegative ? -result : result;
    }

    String nextString() throws IOException {
        StringBuilder sb = new StringBuilder();
        byte b = skip();
        while (b > 32) {
            sb.append((char) b);
            b = getc();
        }
        return sb.toString();
    }
}

class FastWriter {
    BufferedWriter bw;

    public FastWriter() {
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public void print(Object o) throws IOException {
        bw.write(o.toString());
    }

    public void println(Object o) throws IOException {
        print(o);
        bw.newLine();
    }

    public void flush() throws IOException {
        bw.flush();
    }

    public void close() throws IOException {
        bw.close();
    }
}
 
class AdjacencyList implements Graph {
    /* Constant representing infinity */
    private final static long INF = Long.MAX_VALUE;
 
    /* Number of vertices */
    private int vertexCount;
 
    /* Adjacency List */
    private List<List<Edge>> adj;
 
    /* List of Node Names */
    private List<String> vertexNames;
    
 
    public AdjacencyList() {
        this.vertexCount = 0;
        vertexNames = new ArrayList<>();
        adj = new ArrayList<>();
    }
    
    public AdjacencyList(List<List<Edge> > adj,int vertexCount)
    {
        this.adj = adj;
        this.vertexCount = vertexCount;
       
    }
 
    @Override
    public int search(String name) throws VertexNotFoundException{
        int id = 0;
        for(String node: vertexNames){
            if (node.equals(name))
                return id;
            id++;
        }
        throw new VertexNotFoundException();
    }
 
    @Override
    public int insertVertex(String name){
        try{
            int id = search(name);
            return id;
        } catch (VertexNotFoundException e) {
            vertexNames.add(name);
            adj.add(new LinkedList<>());
            vertexCount++;
            return vertexCount - 1;
        }
    }
 
    @Override
    public void insertEdge(String source, String destination, long weight){
        int sourceId = insertVertex(source);
        int destinationId = insertVertex(destination);
 
        Edge edge = new Edge(destinationId, weight);
 
        adj.get(sourceId).add(edge);
    }
 
    @Override
    public void insertEdge(String source, String destination) {
        insertEdge(source, destination, 1);
    }
 
    @Override
    public void removeVertex(String name) throws VertexNotFoundException {
        int nodeId = search(name);
        List<String> tmpVertexNames = new ArrayList<>();
        List<List<Edge>> tmpAdj = new ArrayList<>();
 
        for (int i = 0; i < vertexNames.size(); i++) {
            if (!name.equals(vertexNames.get(i))) {
                tmpVertexNames.add(vertexNames.get(i));
            }
        }
        vertexNames = tmpVertexNames;
 
        for (int i = 0; i < vertexCount; i++) {
            if (i != nodeId) {
                tmpAdj.add(new LinkedList<>());
 
                for (Edge edge: adj.get(i)){
                    int destinationId = edge.getDestination();
 
                    if (destinationId != nodeId){
                        if(destinationId >= nodeId){
                            edge.setDestination(destinationId - 1);
                        }
                        tmpAdj.get(tmpAdj.size() - 1).add(edge);
                    }
                }
            }
        }
        adj = tmpAdj;
        vertexCount--;
    }
 
    @Override
    public void removeEdge(String source, String destination) throws VertexNotFoundException {
        int sourceId = search(source);
        int destinationId = search(destination);
 
        List<Edge> tmp = new LinkedList<>();
        Iterator it = adj.get(sourceId).iterator();
 
        while (it.hasNext()) {
            Edge edge = (Edge) it.next();
 
            if (edge.getDestination() != destinationId)
                tmp.add(edge);
        }
        adj.set(sourceId, tmp);
    }
 
    @Override
    public boolean isAdjacent(String source, String destination) throws VertexNotFoundException {
        int sourceId = search(source);
        int destinationId = search(destination);
 
        for(Edge edge: adj.get(sourceId)){
            if (edge.getDestination() == destinationId)
                return true;            
        }
        return false;
    }
 
    @Override
    public List<Integer> BFS(String originVertex) throws VertexNotFoundException {
        int sourceId = search(originVertex);
        Queue<Integer> Q = new LinkedList<>();
        List<Integer> order = new LinkedList<>();
        boolean[] visited = new boolean[vertexCount];
 
        Q.add(sourceId);
        visited[sourceId] = true;
        while (!Q.isEmpty()) {
            int node = Q.remove();
            order.add(node);
 
            for (Edge edge: adj.get(node)){
                int newNodeId = edge.getDestination();
 
                if (!visited[newNodeId]) {
                    visited[newNodeId] = true;
                    Q.add(newNodeId);
                }
            }
        }
        return order;
    }
 
    @Override
    public List<Integer> DFS(String originVertex) throws VertexNotFoundException {
        boolean[] visited = new boolean[vertexCount];
        List<Integer> order = new LinkedList<>();
 
        DFSRecursive(search(originVertex), order, visited);
        return order;
    }
    
    
    private void DFSRecursive(int nodeId, List<Integer> order, boolean[] visited) {
        visited[nodeId] = true;
        order.add(nodeId);
        
        for(Edge edge: adj.get(nodeId)){
            int newNodeId = edge.getDestination();
            
            if (!visited[newNodeId]) {
                DFSRecursive(newNodeId, order, visited);
            }
        }
    }
    
    /*Shortest Path Section*/  
    
    @Override
    public long[] BFS_SP(String originVertex) throws VertexNotFoundException {
        int sourceId;
        try {
            sourceId = search(originVertex);
        } catch (VertexNotFoundException e) {
            throw e;
        }

        Queue<Integer> Q = new LinkedList<>();
        long[] distances = new long[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        // Initialize distances array with infinity values
        Arrays.fill(distances, Long.MAX_VALUE);

        Q.add(sourceId);
        distances[sourceId] = 0;
        visited[sourceId] = true;

        while (!Q.isEmpty()) {
            int node = Q.remove();

            for (Edge edge : adj.get(node)) {
                int newDestinationId = edge.getDestination();
                long weight = edge.getWeight();

                if (!visited[newDestinationId]) {
                    visited[newDestinationId] = true;
                    distances[newDestinationId] = distances[node] + weight;
                    Q.add(newDestinationId);
                }
            }
        }
        return distances;
    }
 
    
    
    /**
     * Dijkstra's algorithm in O(|V|^2)
     * @param originVertex The vertex from which the shortest paths are calculated
     * @return Returns an array of type long with the distances from originVertex to each of the nodes.
     * @throws VertexNotFoundException
     * Tested on: https://dmoj.uclv.cu/problem/bparty
    */
    @Override
    public long[] DijsktraDenseGraph(String originVertex) throws VertexNotFoundException {
        int sourceId = search(originVertex);
        long[] dist = new long[vertexCount];
        boolean[] visited = new boolean[vertexCount];
 
        for(int i=0; i < vertexCount; i++)
            dist[i] = INF;
        dist[sourceId] = 0;
 
        for (int i=0; i < vertexCount; i++){
            int nodeId = -1;
 
            for(int j=0; j < vertexCount; j++){
                if( !visited[j] && (nodeId == -1 || dist[j] < dist[nodeId]) )
                    nodeId = j;
            }
            visited[nodeId] = true;
 
            for(Edge edge: adj.get(nodeId)){
                int destination = edge.getDestination();
                long weight = edge.getWeight();
 
                if( dist[destination] > dist[nodeId] + weight )
                    dist[destination] = dist[nodeId] + weight;
            }
        }
        return dist;
    }
  
 
    @Override
    public long[] Dijsktra(String originVertex) throws VertexNotFoundException {
        //int sourceId = search(originVertex);
        int sourceId = Integer.parseInt(originVertex);
        long[] dist = new long[vertexCount];
        PriorityQueue<Path> Q = new PriorityQueue<>();
 
        for(int i=0; i < vertexCount; i++)
            dist[i] = INF;
        dist[sourceId] = 0;
 
        Q.add(new Path(sourceId, 0));
        while( !Q.isEmpty() ){
            int nodeId = Q.peek().getDestination();
 
            if( dist[nodeId] < Q.peek().getWeight() ){
                Q.poll();
                continue;
            }
            Q.poll();
 
            for(Edge edge: adj.get(nodeId)){
                int destination = edge.getDestination();
                long weight = edge.getWeight();
 
                if( dist[destination] > dist[nodeId] + weight ){
                    dist[destination] = dist[nodeId] + weight;
                    Q.add(new Path(destination, dist[destination]));
                }
            }
        }
        return dist;
    }
 
    int outDegree(String vertex) throws VertexNotFoundException {
        int nodeId = search(vertex);
 
        return adj.get(nodeId).size();
    }
 
    int inDegree(String vertex) throws VertexNotFoundException {
        int nodeId = search(vertex);
 
        int degree = 0;
        for(int i=0; i < vertexCount; i++){
            for(Edge edge: adj.get(i)){
                if( edge.getDestination() == nodeId )
                    degree++;
            }
        }
        return degree;
    }
 
    @Override
    public long[] BellmanFord(String originVertex) throws VertexNotFoundException, NegativeCycleException {
        int sourceId = search(originVertex);
        long[] dist = new long[vertexCount];
 
        for(int i=0; i < vertexCount; i++)
            dist[i] = INF;
        dist[sourceId] = 0;
 
        for(int phase=1; phase<=vertexCount; phase++){
            for(int node=0; node < vertexCount; node++){
                for(Edge edge: adj.get(node)){
                    int destination = edge.getDestination();
                    long weight = edge.getWeight();
 
                    if( dist[destination] > dist[node] + weight ){
                        dist[destination] = dist[node] + weight;
 
                        if( phase == vertexCount )
                            throw new NegativeCycleException();
                    }
                }
            }
        }
        return dist;
    }
 
    public List<Integer> topologicalOrder() throws CycleDetectedException {
        int[] degree = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];
 
        for(int node=0; node < vertexCount; node++)
            for(Edge edge: adj.get(node))
                degree[edge.getDestination()]++;
 
        Queue<Integer> Q = new LinkedList<>();
        for(int node=0; node < vertexCount; node++)
            if( degree[node] == 0 )
                Q.add(node);
 
        List<Integer> order = new ArrayList<>();
        while( !Q.isEmpty() ){
            int node = Q.poll();
            order.add(node);
 
            visited[node] = true;
            for(Edge edge: adj.get(node)){
                int destination = edge.getDestination();
                degree[destination]--;
 
                if( degree[destination] == 0 && !visited[destination] )
                    Q.add(destination);
            }
        }
 
        if( order.size() != vertexCount )
            throw new CycleDetectedException();
 
        return order;
    }
 
    @Override
    public long[][] DAG_SP(String originVertex) throws CycleDetectedException, VertexNotFoundException,ImpossiblePathException {
        //int sourceId = search(originVertex);
        int sourceId = Integer.parseInt(originVertex);
        long[] dist = new long[vertexCount];
        long[][] pairs = new long[2][vertexCount];

 
        for(int i=0; i < vertexCount; i++){
            dist[i] = -INF;
            pairs[1][i] = -1;
        }
        dist[sourceId] = 0;
 
        List<Integer> order = topologicalOrder();
        for(Integer node: order){
            for(Edge edge: adj.get(node)){
                int destination = edge.getDestination();
                long weight = edge.getWeight();

                if(dist[node]==-INF)continue;

                if( dist[destination] < dist[node] + weight ){
                    dist[destination] = dist[node] + weight;
                    pairs[0][destination] = dist[destination];
                    pairs[1][destination] = node;
                }    
            }
        }
        if(dist[vertexCount-1]==-INF){
            throw new ImpossiblePathException();
        }
        return pairs;
    }
    
    public long[] countPathsInDAG(String originVertex) throws VertexNotFoundException, CycleDetectedException {
        //int sourceId = search(originVertex);
        int sourceId = Integer.parseInt(originVertex);
        long[] count = new long[vertexCount];
        
        
        List<List<Integer>> incomingEdges = new ArrayList<>();
        for(int i=0; i < vertexCount; i++)
            incomingEdges.add(new ArrayList<>());
        
        for(int node=0; node < vertexCount; node++)
            for(Edge edge: adj.get(node))
                incomingEdges.get(edge.getDestination()).add(node);
        
        count[sourceId] = 1;
        List<Integer> order = topologicalOrder();
        
        for(Integer node: order){
            for(Integer previousNode: incomingEdges.get(node))
                count[node] = (count[node] + count[previousNode])%1000000007;
        }
        
        return count;
    }
 
   
    @Override
    public long minimumSpanningTreeKruskal() throws GraphNotConnectedException {
        List<EdgeExt> EdgeList = new ArrayList<>();
        int c_sets=vertexCount;
        for (int nod=0; nod < vertexCount; nod++){
            for(Edge edge: adj.get(nod)){
                EdgeList.add(new EdgeExt(nod, edge.getDestination(), edge.getWeight()));
            }
        }
 
        Collections.sort(EdgeList);
        DisjointSet S;
        S = new DisjointSet(vertexCount);
 
        long mst = 0;
        int conjIni;
        int conjDest;

        for(EdgeExt edge: EdgeList){
            conjIni = S.Find(edge.getStart());
            conjDest = S.Find(edge.getDestination());
 
            if( conjIni != conjDest ){
                mst += edge.getWeight();
                S.Union(conjIni, conjDest);
            }
        }
        int set1 = S.Find(0);
        for(int i = 0;i<vertexCount; i++){
            conjIni = S.Find(i);
            if( set1 != conjIni ){
                throw new GraphNotConnectedException();
            }
        }


        return mst;
    }
    
    @Override
    public long[][] floydWarshall() {
        long INF = 0x3f3f3f3f3f3f3f3fL;
        long[][] distances = new long[vertexCount][vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            for(int j = 0 ; j <vertexCount ;j++)
            {
                distances[i][j]=INF;
            }
            distances[i][i] = 0;
        }

        for (int i = 0; i < vertexCount; i++) {
           
            distances[i][i] = 0;
            for (Edge edge : adj.get(i)) {
            
                if(distances[i][edge.destination]>edge.weight)
                {
                    distances[i][edge.destination]=distances[edge.destination][i]=edge.weight;

                }

            }
             
        }

        for (int k = 0; k < vertexCount; k++) 
        {
             for (int i = 0; i < vertexCount; i++)
            {
                 for (int j = i + 1; j < vertexCount; j++) {
                    long a = distances[i][k] + distances[k][j];
                    if (distances[i][j] > a) 
                    {
                        distances[j][i] = distances[i][j] = a;
                    }
                }
            }
        }

        return distances;
    }
 
 
    /* Helper Method */
    public void printAdjacencyList(){
        for(int i=0; i < vertexCount; i++){
            System.out.printf("%s (%d) => ", vertexNames.get(i), i);
 
            for(Edge edge: adj.get(i)){
                int destination = edge.getDestination();
                System.out.printf("%s (%d) | ",     vertexNames.get(destination), destination);
            }
            System.out.println();
        }
    }
 
     @Override
    public String getName(int nodeId) {
        return vertexNames.get(nodeId);
    }
 
    public int getVertexCount() {
        return vertexCount;
    }
 
    public List<List<Edge>> getAdjacencyList() {
        return adj;
    }
 
    public List<String> getVertexNames() {
        return vertexNames;
    }
  
}
 
class CycleDetectedException extends Exception { 
}
 
class NegativeCycleException extends Exception {
}
 
 
class VertexNotFoundException extends Exception {
}
 
class ImpossiblePathException extends Exception{
}

class GraphNotConnectedException extends Exception{
}

class Edge {
    protected int destination;
    protected long weight;
 
    public Edge(int destination, long weight) {
        this.destination = destination;
        this.weight = weight;
    }
 
    public int getDestination() {
        return destination;
    }
 
    public void setDestination(int destination) {
        this.destination = destination;
    }
 
    public long getWeight() {
        return weight;
    }
 
    public void setWeight(long weight) {
        this.weight = weight;
    }
}
 
class EdgeExt extends Edge implements Comparable<EdgeExt>{
  //private final static long EPS = 1e-9f;
 
  private int start;
 
  public EdgeExt(int start, int destination, long weight) {
    super(destination, weight);
    this.start = start;
  }
 
  public int getStart() {
    return start;
  }
 
  @Override
  public int compareTo(EdgeExt other) {
    if( (this.weight == other.weight)  )
      return 0;
    else if( this.weight < other.weight )
      return -1;
    else
      return 1;
  }
}
 
class Path implements Comparable<Path>{
    //private final static long EPS = 1e-9f;
 
    private int destination;
    private long weight;
 
    public Path(int destination, long weight) {
        this.destination = destination;
        this.weight = weight;
    }
 
    @Override
    public int compareTo(Path path) {
        if((this.weight == path.weight) )
            return 0;
        else if( this.weight < path.weight )
            return -1;
        else
            return 1;
    }
 
    public int getDestination() {
        return destination;
    }
 
    public long getWeight() {
        return weight;
    }
}
 
class DisjointSet {
  private int[] parent, rank;
 
  public DisjointSet (int cantSets) {
    parent = new int[cantSets];
    rank = new int[cantSets];
 
    for(int i=0; i < cantSets; i++){
      parent[i] = i;
      rank[i] = 1;
    }
  }
 
  public int Find(int nod) {
    if( parent[nod] != nod )
      return parent[nod] = Find(parent[nod]);
    return nod;
  }
 
  public void Union(int a, int b) {
    if( rank[a] > rank[b] ){
      parent[b] = a;
      rank[a]++;
    }
    else{
      parent[a] = b;
      rank[b]++;
    }
  }
}
 
 
interface Graph {
    
    /**
     * Insert a node in the graph, if it does not exist
     * @param name This is the name of the node
     * @return Returns the id of the node
     */
    int insertVertex(String name);
 
    /**
     * Add an edge WITH weight in the graph
     * @param source This is the source node of the edge
     * @param destination This is the destination node of the edge
     * @param weight This is the weight of the edge
     */
    void insertEdge(String source, String destination, long weight);
 
    /**
    * Adds an edge WITHOUT weight to the graph
    * @param source This is the source node of the edge
    * @param destination This is the destination node of the edge
    */
    void insertEdge(String source, String destination);
 
    /**
    * Removes a node from the graph
    * @param name This is the name of the node
    * @throws Graphs.Exception.VertexNotFoundException
    */
    void removeVertex(String name) throws VertexNotFoundException;
 
    /**
     * Removes an edge from the graph
     * @param source This is the source node of the edge
     * @param destination This is the destination node of the edge
     * @throws Graphs.Exception.VertexNotFoundException
     */
    void removeEdge(String source, String destination) throws VertexNotFoundException;
 
    /**
    * Searches for the id of a vertex
    * @param name This is the name of the vertex
    * @return Returns the id of the searched vertex
    * @throws Graphs.Exception.VertexNotFoundException
    */
    int search(String name) throws VertexNotFoundException;
 
    /**
    * Returns the name of a node, given its id
    * @param nodeId This is the node id
    * @return Returns a string with the name of the node
    */
    String getName(int nodeId);
 
    /**
    * Checks if two nodes are connected by an edge
    * @param source Source node
    * @param destination Destination node
    * @return Returns true if the nodes are connected by an edge. Otherwise, returns false.
    * @throws Graphs.Exception.VertexNotFoundException
    */
    boolean isAdjacent(String source, String destination) throws VertexNotFoundException;
 
    /**
     * Performs a breadth-first traversal in the graph
     * @param originVertex Vertex where the traversal begins
     * @return Returns a list with the ids of the vertices, in the order they were traversed
     * @throws Graphs.Exception.VertexNotFoundException
     */
    List<Integer> BFS(String originVertex) throws VertexNotFoundException;
 
    /**
     * Performs a depth-first traversal in the graph
     * @param originVertex Vertex where the traversal begins
     * @return Returns a list with the ids of the vertices, in the order they were traversed
     * @throws Graphs.Exception.VertexNotFoundException
     */
    List<Integer> DFS(String originVertex) throws VertexNotFoundException;
 
    /**
    * Finds the shortest path between nodes in the graph without considering weights
    * @param originVertex Vertex where the traversal begins
    * @return Returns an array with the distances from originVertex to each of the nodes.
    * @throws Graphs.Exception.VertexNotFoundException
    */
    long[] BFS_SP(String originVertex) throws VertexNotFoundException;
 
    
    /**
     * Finds the shortest path between nodes in the graph with positive weights
     * @param originVertex Vertex where the traversal begins
     * @return Returns an array with the distances from originVertex to each of the nodes.
     * @throws VertexNotFoundException
     */
    long[] Dijsktra(String originVertex) throws VertexNotFoundException;
 
    /**
    * Finds the shortest path between nodes in the dense graph with positive weights
    * using Dijkstra's algorithm.
    * @param originVertex Vertex where the traversal begins
    * @return Returns an array with the distances from originVertex to each of the nodes.
    * @throws VertexNotFoundException If the specified vertex is not found in the graph.
    */
    
    long[] DijsktraDenseGraph(String originVertex) throws VertexNotFoundException;
    
    
    /**
     * Finds the shortest path between nodes in the graph with negative weights
     * @param originVertex Vertex where the traversal begins
     * @return Returns an array with the distances from originVertex to each of the nodes.
     * @throws VertexNotFoundException
     * @throws NegativeCycleException
     */
    long[] BellmanFord(String originVertex) throws VertexNotFoundException, NegativeCycleException;
 
    /**
     * Finds the shortest path between nodes in the graph without cycles
     * @param originVertex Vertex where the traversal begins
     * @return Returns an array with the distances from originVertex to each of the nodes.
     * @throws CycleDetectedException
     * @throws VertexNotFoundException
     */
    long[][] DAG_SP(String originVertex) throws CycleDetectedException, VertexNotFoundException,ImpossiblePathException;
    
    /**
     * Finds the shortest paths between all pairs of nodes using the Floyd-Warshall algorithm.
     * @return Returns a 2D array representing the shortest distances between all pairs of nodes.
     */
    
    long[][] floydWarshall();
   
    /**
     * Finds the minimum spanning tree of the graph using Kruskal algorithm
     * @return Returns the weight of the minimum spanning tree
     */
    long minimumSpanningTreeKruskal() throws GraphNotConnectedException;
}