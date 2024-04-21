import java.io.*;
import java.util.*;
 
public class BFS {
    
    //TSTED ON https://cses.fi/problemset/task/1667/
 
	public static void main(String[] args) {
		int n = ni(), m = ni();
		
		Vertex[] vertices = new Vertex[n + 1];
		
		for (int i = 1; i <= n; i++) {
			vertices[i] = new Vertex(i);
		}
		
		for (int i = 0; i < m; i++) {
			int u = ni(), v = ni();
			
			vertices[u].add(vertices[v]);
			vertices[v].add(vertices[u]);
		}
		
		StringBuilder sb = new StringBuilder();
		
		if (bfs(vertices[1], vertices[n])) {
			Stack<Integer> stack = new Stack<>();
			Vertex v = vertices[n];
			
			while (v != null) {
				stack.push(v.id);
				v = v.parent;
			}
			sb.append(stack.size()).append('\n');
			
			while (stack.size() != 0) {
				sb.append(stack.pop()).append(' ');
			}
		} else {
			sb.append("IMPOSSIBLE");
		}
		System.out.print(sb);
 
	}
	
	static boolean bfs(Vertex v, Vertex target) {
		v.visited = true;
		Queue<Vertex> queue = new ArrayDeque<>();
		
		while (v.adjacents.size() != 0) {
			Vertex next = v.adjacents.poll();
			
			if (!next.visited) {
				queue.offer(next);
				next.visited = true;
				next.parent = v;
				
				if (next.equals(target)) {
					return true;
				}
			}
		}
		
		while (queue.size() != 0) {
			v = queue.poll();
			
			while (v.adjacents.size() != 0) {
				Vertex next = v.adjacents.poll();
				
				if (!next.visited) {
					queue.offer(next);
					next.visited = true;
					next.parent = v;
					
					if (next.equals(target)) {
						return true;
					}
				}
			}
			
		}
		return false;
		
	}
	
	static class Vertex {
		int id;
		boolean visited = false;
		Queue<Vertex> adjacents = new ArrayDeque<>();
		Vertex parent;
		
		public Vertex(int id) {
			this.id = id;
		}
		
		public void add(Vertex v) {
			adjacents.add(v);
		}
		
	}
	
	static InputStream is = System.in;
	static byte[] inbuf = new byte[1 << 24];
	static int lenbuf = 0, ptrbuf = 0;
 
	static int readByte() {
		if (lenbuf == -1)
			throw new InputMismatchException();
		if (ptrbuf >= lenbuf) {
			ptrbuf = 0;
			try {
				lenbuf = is.read(inbuf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (lenbuf <= 0)
				return -1;
		}
		return inbuf[ptrbuf++];
	}
 
	static boolean isSpaceChar(int c) {
		return !(c >= 33 && c <= 126);
	}
	
	static boolean isSpaceChar2(int c) {
		return !(c >= 32 && c <= 126);
	}
 
	static int skip() {
		int b;
		while ((b = readByte()) != -1 && isSpaceChar(b))
			;
		return b;
	}
 
	static double nd() {
		return Double.parseDouble(ns());
	}
 
	static char nc() {
		return (char) skip();
	}
 
	static String ns() {
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while (!(isSpaceChar(b))) {
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}
	
	static String nextLine() {
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while (!(isSpaceChar2(b))) {
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}
 
	static char[] ns(int n) {
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while (p < n && !(isSpaceChar(b))) {
			buf[p++] = (char) b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}
 
	static int ni() {
		int num = 0, b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = readByte();
		}
		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}
 
	static long nl() {
		long num = 0;
		int b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = readByte();
		}
		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}
 
}

