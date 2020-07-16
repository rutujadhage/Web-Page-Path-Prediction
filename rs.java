package seminar;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class rs {
	public static void main(String[] args) throws IOException {
		File code = new File("logfile.txt");
		ArrayList<String>[] sessions = new ArrayList[7];
		for (int i = 0; i < 7; i++) {
			sessions[i] = new ArrayList<String>();
		}
		ArrayList<String> list = new ArrayList<String>();
		HashMap<String, Integer> ip_address = new HashMap<>();
		HashMap<String, Integer> page_freq = new HashMap<>();
		HashMap<Integer, Integer> no_of_pages = new HashMap<>();
		for (int i = 1; i <= 7; i++) {
			no_of_pages.put(i, 0);
		}
		Scanner sc = new Scanner(code);
		int i = 1;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String var[] = line.split(",");
			if (!page_freq.containsKey(var[3])) {
				page_freq.put(var[3], 1);
			} else {
				int val = page_freq.get(var[3]);
				val = val + 1;
				page_freq.replace(var[3], val);
			}
			if (!ip_address.containsKey(var[0])) {
				ip_address.put(var[0], i);
				i++;
				int index = ip_address.get(var[0]);
				int val = no_of_pages.get(index);
				val++;
				no_of_pages.replace(index, val);
				index = index - 1;
				sessions[index].add(var[3]);
			} else {
				int index = ip_address.get(var[0]);
				int val = no_of_pages.get(index);
				val++;
				no_of_pages.replace(index, val);
				index = index - 1;
				sessions[index].add(var[3]);
			}
		}
		int n = ip_address.size();
		for (int x = 0; x < n; x++) {
			int j ;
			System.out.print("Session"+(x+1)+": ");
			for (j = 0; j < sessions[x].size()-1; j++) {
				System.out.print(sessions[x].get(j) + " -> ");
			}
			System.out.print(sessions[x].get(j));
			System.out.println();
		}
		// FREQUENCY CALCULATION
		System.out.println();
		System.out.println();
		System.out.println("FREQUENCY OF EACH PAGE:");
		System.out.println(page_freq);
		for (String name : page_freq.keySet()) {
			int f = page_freq.get(name);
			if (f < 3) {
				list.add(name);
			}
		}
		System.out.println();
		System.out.println();
		System.out.println("DELETED PAGES");
		System.out.println(list);
		// REMOVING THE PAGES WHOSE FREQ < 3
		for (int x = 0; x < n; x++) {
			for(int j=0;j < list.size();j++) {
				String val = list.get(j);
				if(sessions[x].contains(val)) {
					sessions[x].remove(val);
				}
			}
		}
		
		
		HashMap<String, Integer> list_of_new_pages = new HashMap<String, Integer>();
		// PRINTING AFTER DELETION
		int count = 0;
		System.out.println();
		System.out.println();
		System.out.println("NEW SESSIONS AFTER DELETING PAGES");
		for (int x = 0; x < n; x++) {
			int j;
			for (j = 0; j < sessions[x].size()-1; j++) {
				String a = sessions[x].get(j);
				if (!list_of_new_pages.containsKey(a)) {
					list_of_new_pages.put(a, count);
					count = count + 1;
				}
				System.out.print(sessions[x].get(j) + " -> ");
			}
			String a = sessions[x].get(j);
			if (!list_of_new_pages.containsKey(a)) {
				list_of_new_pages.put(a, count);
				count = count + 1;
			}
			System.out.print(sessions[x].get(j));
			System.out.println();
		}
		
		int size = list_of_new_pages.size();
		
		
		int count2 = 0;
		String page_array[] = new String[size];
		for(String op : list_of_new_pages.keySet()) {
			list_of_new_pages.replace(op, count2);
			page_array[count2]=op;
			count2++;
		}
		System.out.println();
		
		
		int arr[][] = new int[size][size];
		for (int z = 0; z < size; z++) {
			for (int y = 0; y < size; y++) {
				arr[z][y] = 0;
			}
		}
		
		
		for (int x = 0; x < n; x++) {
			for (int j = 0; j < sessions[x].size()-1; j++) {
					String a = sessions[x].get(j);
					String b = sessions[x].get(j + 1);
					arr[list_of_new_pages.get(a)][ list_of_new_pages.get(b)]++;
				}
			System.out.println();
		}
		
		
		System.out.println("Matrix Created Using 2nd order Markov Model: ");
		System.out.println();
		System.out.println();
		System.out.print("\t");
		for (int in = 0; in < size; in++) {
			System.out.print(page_array[in]+"\t");
		}
		System.out.println();
		
		
		for (int z = 0; z < size; z++) {
			System.out.print(page_array[z]+"\t");
		for (int y = 0; y < size; y++) {
			System.out.print(arr[z][y]+"\t");
		}
		System.out.println();
	}
		
		int max=0;
		int pos1=-1,pos2=-1;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				int val=arr[x][y];
				if(val>max)
				{
					max=val;
					pos1=x;
					pos2=y;
				}
			}
		}
		String src=page_array[pos1];
		String dest=page_array[pos2];
		System.out.println();
		System.out.println();
		System.out.println("Using Markov 2nd order, most frequently accessed pages are:");
		System.out.print(src+"->");
		System.out.println(dest);
		
		
		int sd_cnt[]=new int[n];
		for(int x=0;x<n;x++)
		{
			int cntr=0;
			if(sessions[x].contains(src) && sessions[x].contains(dest))
			{
				for(int y=0;y<sessions[x].size();y++)
				{
					String val=sessions[x].get(y);
					if(val.equals(src))
					{
						if(y+1<sessions[x].size())
						{
							val=sessions[x].get(y+1);
							if(val.equals(dest))
							{
								cntr++;
								sd_cnt[x]=cntr;
							}
						}
					}
				}
			}
		}
		
		
		int sum=0;
		for(int x=0;x<n;x++)
		{
			sum=sum+sd_cnt[x];
		}
		ArrayList<String>[] seq = new ArrayList[sum];
		for (int x = 0; x < sum; x++) {
			seq[x] = new ArrayList<String>();
		}
		int cnt=0;
		for(int x=0;x<n;x++)
		{
			if(sd_cnt[x]>0)
			{
				for(int y=0;y<sessions[x].size();y++)
				{
					if(sd_cnt[x]>0)
					{
						String val=sessions[x].get(y);
						if(val.equals(src))
						{
							if(y+2<sessions[x].size())
							{
								val=sessions[x].get(y+1);
								if(val.equals(dest))
								{
									sd_cnt[x]--;
									y=y+2;
									val=sessions[x].get(y);
									seq[cnt].add(val);
									cnt++;
								}
								else
								{
									val=sessions[x].get(y);
									seq[cnt].add(val);
								}
							}
						}
						else
						{
							seq[cnt].add(val);
						}
					}
				}
			}
		}
		
		for(int x=0;x<cnt;x++)
		{
			int y;
			for(y=0;y<seq[x].size()-1;y++)
			{
				System.out.print(seq[x].get(y)+"\t");
			}
			System.out.print(src+""+dest+"\t"+seq[x].get(y));
			System.out.println();
		}
		HashMap<String, Integer> pre = new HashMap<>();
		HashMap<String, Integer> post = new HashMap<>();
		for(int x=0;x<cnt;x++)
		{ int y;
			for(y=0;y<seq[x].size()-1;y++)
			{
				String val=seq[x].get(y);
				if(!pre.containsKey(val))
				{
					pre.put(val, 1);
				}
				else
				{
					int c=pre.get(val);
					c++;
					pre.replace(val, c);
				}
			}
			String val=seq[x].get(y);
			if(!post.containsKey(val))
			{
				post.put(val, 1);
			}
			else
			{
				int c=post.get(val);
				c++;
				post.replace(val, c);
			}
		}
		
		
		int s1=pre.size();
		int s2=post.size();
		
		
		int calc[][]=new int [s2][s1];
		
		int counter1=0,counter=0;
		for(String op : post.keySet()) {
			counter=0;
			for(String oq: pre.keySet())
			{
				int cf=0;
				for(int x=0;x<cnt;x++)
				{
					int index=seq[x].size()-1;
					String val=seq[x].get(index);
					if(seq[x].contains(op) && val.equals(op) && seq[x].contains(oq) && !op.equals(oq))
					{
						cf++;
					}
				}
				calc[counter1][counter]=cf;
				counter++;
			}
			counter1++;
		}
		
		
		int copy[]=new int[s1];
		int counters=0;
		for(String op : pre.keySet()) 
		{
			int val=pre.get(op);
			copy[counters]=val;
			counters++;
		}
		
		int perc[][]=new int[s2][s1];
		for(int x=0;x<s2;x++)
		{
			for(int y=0;y<s1;y++)
			{
				perc[x][y]=(calc[x][y]*100)/copy[y];
			}
		}
		
		System.out.println();
		System.out.println();
		counter1=0;
		for(String op : post.keySet()) {
			System.out.println("Confidence of accessing page "+op+" using subsequence association rules:");
			System.out.println();
			counter=0;
			for(String oq: pre.keySet())
			{
				System.out.println(oq+"->"+op+"\t"+oq+""+op+"/"+oq+"\t"+calc[counter1][counter]+"/"+copy[counter]+"\t"+perc[counter1][counter]+"%");
				counter++;
			}
			counter1++;
			System.out.println();
		}
		
		System.out.println("If the user accesses page G before pages E and F then there is a 100% confidence that the user will access page H next. Whereas, if the user visits page H before visiting pages E and F, then there is a 100% confidence that the user will access page I next.");
		sc.close();
	}
}