import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

//This code implements the distance vector protocol by reading the input from a file.
public class DistanceVectorProtocol {

	public static void main(String c[]) throws IOException {

		int maxNodes = 0, maxEdges = 0;
		boolean maxNodesEdgesFlag = true;
		int[][] hop = null;
		int[][] input = null;

		BufferedReader s = null;
		s = new BufferedReader(new FileReader("DV_example_input.txt"));
		String line;
		while ((line = s.readLine()) != null) {
			Scanner br = new Scanner(line);
			while (br.hasNext()) {
				if (maxNodesEdgesFlag && br.hasNextInt()) {
					maxNodesEdgesFlag = false;
					maxNodes = br.nextInt();
					line = s.readLine();
					br = new Scanner(line);
					maxEdges = br.nextInt();
					hop = new int[maxNodes][maxNodes];
					input = new int[maxNodes][maxNodes];
				} else if (maxNodesEdgesFlag == false && br.hasNextInt()) {
					input[br.nextInt()][br.nextInt()] = br.nextInt();
				}
			}
		}

		for (int i = 0; i < maxNodes; i++) {
			for (int j = 0; j < maxNodes; j++) {
				if (i != j && input[i][j] == 0)
					input[i][j] = Integer.MAX_VALUE;
			}
		}

		for (int i = 0; i < maxNodes; i++)
			for (int j = 0; j < maxNodes; j++)
				if (input[i][j] != Integer.MAX_VALUE)
					hop[i][j] = j;
				else
					hop[i][j] = -1;

		int[][] iter1 = new int[maxNodes][maxNodes];
		int[][] iter2 = new int[maxNodes][maxNodes];

		for (int i = 0; i < maxNodes; i++) {
			for (int j = 0; j < maxNodes; j++)
				iter1[i][j] = iter2[i][j] = input[i][j];
		}

		for (int p = 0; p < maxNodes; p++) {

			for (int i = 0; i < maxNodes; i++) {
				if (p == maxNodes - 1)
					System.out.print("Node " + i + "\n");
				for (int j = 0; j < maxNodes; j++) {
					if (i == j) {
						hop[i][j] = i;
						iter2[i][j] = 0;
					} else {
						for (int k = 0; k < maxNodes; k++) {
							if (input[i][k] == Integer.MAX_VALUE || i == k || k == j)
								continue;
							if (iter2[i][k] == Integer.MAX_VALUE || iter1[k][j] == Integer.MAX_VALUE)
								continue;
							if (iter2[i][k] + iter1[k][j] <= iter2[i][j]) {
								iter2[i][j] = iter2[i][k] + iter1[k][j];
								hop[i][j] = k;
							}
						}
					}
					if (p == maxNodes - 1 && i != j) {
						if (iter2[i][j] != Integer.MAX_VALUE)
							System.out.println(j + " " + hop[i][j] + " " + iter2[i][j]);
						else
							System.out.println(j + " " + hop[i][j] + " INF");

					}

				}

			}
			for (int a = 0; a < maxNodes; a++)
				for (int b = 0; b < maxNodes; b++)
					iter1[a][b] = iter2[a][b];
			if (p == 3) {
				System.out.println("Iteration 4");
				for (int a = 0; a < maxNodes; a++) {
					System.out.println();
					for (int b = 0; b < maxNodes; b++)
						System.out.print(iter1[a][b] + "  ");
				}
			}
		}
	}
}
