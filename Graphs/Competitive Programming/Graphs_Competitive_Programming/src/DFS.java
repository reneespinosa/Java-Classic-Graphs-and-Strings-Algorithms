import java.util.*;
import java.io.*;
 

// TESTED ON CSES: https://cses.fi/problemset/task/1666/

public class DFS {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;
 
        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
 
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int nextInt() {
            return Integer.parseInt(next());
        }
 
        long nextLong() {
            return Long.parseLong(next());
        }
 
        double nextDouble() {
            return Double.parseDouble(next());
        }
 
        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
 
    private static FastReader sc = new FastReader();
    static final PrintWriter PW = new PrintWriter(new OutputStreamWriter(System.out));
 
    public static void findConnectedComponent(ArrayList<ArrayList<Integer>> graph, int n, StringBuilder sb) {
        // for all node check whther they are connected
        boolean vis[] = new boolean[n + 1];
        ArrayList<Integer> roads = new ArrayList<>();
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (vis[i] == false) {
                dfs(graph, vis, i);
                roads.add(i);
                count++;
            }
        }
 
        sb.append(count - 1).append("\n");
        if (roads.size() != 1) {
            for (int i = 1; i < roads.size(); i++) {
                sb.append(roads.get(i - 1)).append(" ").append(roads.get(i)).append("\n");
            }
        }
 
    }
 
    public static void dfs(ArrayList<ArrayList<Integer>> graph, boolean vis[], int src) {
        vis[src] = true;
        // for all adjacent
        for (int v : graph.get(src)) {
            if (vis[v] == false) {
                dfs(graph, vis, v);
            }
        }
    }
 
    public static void main(String[] args) {
 
        StringBuilder sb = new StringBuilder();
        int n = sc.nextInt();
        int m = sc.nextInt();
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
 
        }
 
        while (m-- > 0) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
 
        findConnectedComponent(graph, n, sb);
 
        PW.print(sb.toString());
        PW.close();
    }
}