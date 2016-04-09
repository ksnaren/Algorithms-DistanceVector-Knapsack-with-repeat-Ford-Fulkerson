import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class KnapsackRepeat {
	public static void main(String c[]) throws IOException {
		int max_weight = 0;
		boolean weightFlag = true;
		ArrayList<Integer> weight = new ArrayList();
		ArrayList<Integer> value = new ArrayList();
		BufferedReader s = null;
		s = new BufferedReader(new FileReader("KP_input_4.txt"));
		String line;
		while ((line = s.readLine()) != null) {
			Scanner br = new Scanner(line);
			while (br.hasNext()) {
				if (weightFlag && br.hasNextInt()) {
					weightFlag = false;
					max_weight = br.nextInt()+1;
					} else {
					br.nextInt();
					weight.add(br.nextInt());
					value.add(br.nextInt());
				}
			}
		}
		s.close();
		
		int[] opt = new int[max_weight];
		opt[0] = 0;
		for (int i = 0; i < max_weight; i++) 
		{
			for (int j = 0; j < weight.size(); j++)
			{
				if (weight.get(j) <= i)
					opt[i] = Math.max(opt[i], value.get(j) + opt[i - weight.get(j)]);
			}
		}
		System.out.println(opt[max_weight-1]);
		int i = 4, w = max_weight - 1;
		while (i > 0)
		{
			for (int j = 0; j < weight.size(); j++) 
			{
				if (w >= weight.get(j) && opt[w] - opt[w - weight.get(j)] == value.get(j)) 
				{
					System.out.print((j+1) + "  ");
					w = w - weight.get(j);
				}
			}
			i--;
		}
	}
}
