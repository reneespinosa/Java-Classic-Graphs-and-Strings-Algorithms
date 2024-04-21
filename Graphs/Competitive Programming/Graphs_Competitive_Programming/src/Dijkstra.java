import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
 
public class Dijkstra {
    
    //TESTED ON https://cses.fi/problemset/task/1671/
    
    private static int cities;
    private static int connections;
 
    public static void main(String[] args) {
        List<Edge>[] graph = readInput();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        long[] distance = new long[cities + 1];
        boolean[] visited = new boolean[cities + 1];
        Arrays.fill(distance, Long.MAX_VALUE);
        distance[1] = 0;
        pq.add(new Edge(1, 0));
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            if (visited[edge.to]) {
                continue;
            }
            visited[edge.to] = true;
            List<Edge> adj = graph[edge.to];
            if (adj == null) {
                continue;
            }
            for (Edge connectingEdge : adj) {
                if (visited[connectingEdge.to]) {
                     continue;
                }
                if (distance[edge.to] + connectingEdge.weight < distance[connectingEdge.to]) {
                    distance[connectingEdge.to] = distance[edge.to] + connectingEdge.weight;
                    connectingEdge.weight = distance[connectingEdge.to];
                    pq.add(connectingEdge);
                }
            }
        }
 
        PrintWriter out = new PrintWriter(System.out);
        StringBuilder ans = new StringBuilder();
        for (int i = 1; i <= cities; i++) {
            ans.append(distance[i]).append(" ");
        }
        out.print(ans);
        out.flush();
    }
 
    private static class Edge implements Comparable<Edge> {
        private int to;
        private long weight;
 
        public Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
 
        public int compareTo(Edge o) {
            return Double.compare(this.weight, o.weight);
        }
    }
 
    private static List<Edge>[] readInput() {
        Reader in = new Reader();
        List<Edge>[] graph;
        try {
            cities = in.nextInt();
            connections = in.nextInt();
            graph = new ArrayList[cities + 1];
            for (int i = 0; i < connections; i++) {
                int source = in.nextInt();
                int target = in.nextInt();
                long weight = in.nextInt();
                Edge edge = new Edge(target, weight);
                List<Edge> edges = graph[source];
                if (edges == null) {
                    edges = new ArrayList<>();
                }
                edges.add(edge);
                graph[source] = edges;
            }
            return graph;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;
 
        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
 
            if (neg)
                return -ret;
            return ret;
        }
 
        public long[] readArray(int n) throws IOException{
            long[] arr = new long[n];
            for(int i = 0;i<n;i++)
                arr[i] = nextInt();
 
            return arr;
        }
 
        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }
 
        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }
 
        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }
 
        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}