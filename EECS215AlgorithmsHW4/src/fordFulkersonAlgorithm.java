import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class fordFulkersonAlgorithm {

	static int matrixSize = 7;

	static boolean hasAugmentedPath(int[][] graph, int start, int path[], int end) {

		boolean[] visited = new boolean[matrixSize];
		for (int i = 0; i < matrixSize; i++) {
			visited[i] = false;
			path[i] = i;
		}
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(start);
		visited[start] = true;
		while (!q.isEmpty()) {
			int vertex = q.poll();
			for (int j = 0; j < matrixSize; j++) {
				if (graph[vertex][j] != 0 && visited[j] == false) {
					q.add(j);
					visited[j] = true;
					path[j] = vertex;
				}
			}
		}
		return (visited[end] == true);
	}

	static void dfs(int[][] graph, boolean[] visited, int start) {
		for (int i = 0; i < matrixSize; i++) {
			if (graph[start][i] != 0 && visited[i] == false) {
				visited[i] = true;
				dfs(graph, visited, i);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		int[][] originalGraph = null;
		boolean maxNodesEdgesFlag = true;

		BufferedReader s = null;
		s = new BufferedReader(new FileReader("FFInput1.txt"));
		String line;
		while ((line = s.readLine()) != null) {
			Scanner br = new Scanner(line);
			while (br.hasNext()) {
				if (maxNodesEdgesFlag && br.hasNextInt()) {
					maxNodesEdgesFlag = false;
					matrixSize = br.nextInt();
					line = s.readLine();
					br = new Scanner(line);
					br.nextInt();
					originalGraph = new int[matrixSize][matrixSize];
				} else if (maxNodesEdgesFlag == false && br.hasNextInt()) {
					originalGraph[br.nextInt()][br.nextInt()] = br.nextInt();
				}
			}
		}

		int[][] inputGraph = new int[matrixSize][matrixSize];
		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++)
				inputGraph[i][j] = originalGraph[i][j];
		}

		int[] path = new int[matrixSize];
		int source = 0, sink = matrixSize - 1;
		int maxFLow = 0;

		while (hasAugmentedPath(originalGraph, source, path, sink)) {
			int minPath = Integer.MAX_VALUE;

			for (int i = matrixSize - 1; i != 0; i = path[i]) {
				int j = path[i];
				minPath = Math.min(minPath, originalGraph[j][i]);
			}
			maxFLow += minPath;

			for (int i = matrixSize - 1; i != 0; i = path[i]) {
				int j = path[i];
				originalGraph[i][j] += minPath;
				originalGraph[j][i] -= minPath;
			}
		}
		System.out.println("The max flow is: " + maxFLow);

		boolean[] visited = new boolean[matrixSize];

		visited[0] = true;
		dfs(originalGraph, visited, 0);

		System.out.println("The mincut edges are: ");
		for (int i = 0; i < matrixSize; i++) {
			if (visited[i]) {
				for (int j = 0; j < matrixSize; j++)
					if (!visited[j] && inputGraph[i][j] != 0)
						System.out.println(i + "->" + j);
			}
		}

		// Select the head and tail of the updated edge as per the original
		// graph (Eg: Tail(1)----2---->Head(5)).
		int updatedHead = 5, updatedTail = 1;
		int tempDiff = originalGraph[updatedHead][updatedTail];
		// Add the updated value here.
		originalGraph[updatedHead][updatedTail] = 2;

		int diff = tempDiff - originalGraph[updatedHead][updatedTail], temp = diff;

		if (diff > 0) {

			while (diff != 0 && hasAugmentedPath(originalGraph, updatedTail, path, source)) {
				int minPath = Integer.MAX_VALUE;

				for (int i = source; i != path[i]; i = path[i]) {
					int j = path[i];
					minPath = Math.min(minPath, originalGraph[j][i]);
				}

				if (diff < minPath) {
					minPath = diff;
					diff = 0;
				} else {
					diff = diff - minPath;
				}

				for (int i = source; i != path[i]; i = path[i]) {
					int j = path[i];
					originalGraph[i][j] += minPath;
					originalGraph[j][i] -= minPath;
				}
			}
		} else {
			diff = Math.abs(diff);
			while (diff != 0 && hasAugmentedPath(originalGraph, source, path, updatedTail)) {
				int minPath = Integer.MAX_VALUE;

				for (int i = updatedTail; i != path[i]; i = path[i]) {
					int j = path[i];
					minPath = Math.min(minPath, originalGraph[j][i]);
				}

				if (diff < minPath) {
					minPath = diff;
					diff = 0;
				} else {
					diff = diff - minPath;
				}

				for (int i = updatedTail; i != path[i]; i = path[i]) {
					int j = path[i];
					originalGraph[i][j] += minPath;
					originalGraph[j][i] -= minPath;
				}
			}
		}

		diff = temp;
		// From updated Head to sink
		if (diff > 0) {

			while (diff != 0 && hasAugmentedPath(originalGraph, sink, path, updatedHead)) {
				int minPath = Integer.MAX_VALUE;

				for (int i = updatedHead; i != path[i]; i = path[i]) {
					int j = path[i];
					minPath = Math.min(minPath, originalGraph[j][i]);
				}

				if (diff < minPath) {
					minPath = diff;
					diff = 0;
				} else {
					diff = diff - minPath;
				}

				for (int i = updatedHead; i != path[i]; i = path[i]) {
					int j = path[i];
					originalGraph[i][j] += minPath;
					originalGraph[j][i] -= minPath;
				}
			}
		} else {
			diff = Math.abs(diff);
			while (diff != 0 && hasAugmentedPath(originalGraph, updatedHead, path, sink)) {
				int minPath = Integer.MAX_VALUE;

				for (int i = updatedTail; i != path[i]; i = path[i]) {
					int j = path[i];
					minPath = Math.min(minPath, originalGraph[j][i]);
				}

				if (diff < minPath) {
					minPath = diff;
					diff = 0;
				} else {
					diff = diff - minPath;
				}

				for (int i = updatedTail; i != path[i]; i = path[i]) {
					int j = path[i];
					originalGraph[i][j] += minPath;
					originalGraph[j][i] -= minPath;
				}
			}
		}
		maxFLow = 0;
		for (int i = 0; i < matrixSize; i++) {
			maxFLow += originalGraph[i][0];
		}
		System.out.println("The updated MaxFlow: " + maxFLow);

		for (int i = 0; i < matrixSize; i++)
			visited[i] = false;

		visited[0] = true;
		dfs(originalGraph, visited, 0);

		System.out.println("The mincut edges are: ");
		for (int i = 0; i < matrixSize; i++) {
			if (visited[i]) {
				for (int j = 0; j < matrixSize; j++)
					if (!visited[j] && inputGraph[i][j] != 0)
						System.out.println(i + "->" + j);
			}
		}
	}
}
