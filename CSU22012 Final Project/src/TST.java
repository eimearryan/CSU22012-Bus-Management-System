/*
 * 2. Searching for a bus stop by full name or by the first few characters in the name, using a
 *	ternary search tree (TST), returning the full stop information for each stop matching the
 *	search criteria (which can be zero, one or more stops)
 *	In order for this to provide meaningful search functionality please move keywords flagstop, wb, nb,
 *	sb, eb from start of the names to the end of the names of the stops when reading the file into a TST
 *	(eg “WB HASTINGS ST FS HOLDOM AVE” becomes “HASTINGS ST FS HOLDOM AVE WB”) 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class TST<Value> {

	private int n;              
	private Node<Value> root;  

	private static class Node<Value> {
		private char c;                        
		private Node<Value> left, mid, right;  
		private Value val;                     
	}


	public static void BusStopTST(String inputStop) throws FileNotFoundException
	{
		TST<String> tree = new TST<String>();
		String line = "";
		File file = new File("stops.txt");

		Scanner input = new Scanner(new FileReader(file));
		input.nextLine();
		while(input.hasNextLine())
		{
			line = input.nextLine();
			String[] stopDetails = line.split(",");
			String stopName = stopDetails[2];

			String meaningfulStop = meaningfulStopName(stopName);

			String stopInformation = "\nStop id: " + stopDetails[0] + ",\nStop Code: " + stopDetails[1] + ",\nStop Desc: " + stopDetails[3] +
					 ",\nStop Lat: " + stopDetails[4] + ",\nStop Lon: " + stopDetails[5] + ",\nZone id: " + stopDetails[6] + "\n";
	
			tree.put(meaningfulStop, stopInformation);
		}
		

			String info = "";
			for (String s : tree.keysWithPrefix(inputStop)) {
				info += s+tree.get(s) + "\n";
			}

			if (info == "")
			{
				System.out.println("Sorry, there is no stop information to match your entered stop,\nplease enter a valid bus stop next time. ");
			}
			else {
				System.out.println("Information for entered bus stop:\n" + info);
			}
	}

	public static String meaningfulStopName(String stopName) {

		String keyword = stopName.substring(0, 2).strip().toUpperCase();
		String FlagStop = stopName.substring(0, 8).strip().toUpperCase();

		if (keyword.equals("WB") || keyword.equals("NB") || keyword.equals("SB") || keyword.equals("EB")) {
			String start = stopName.substring(0, 2);
			String end = stopName.substring(3); 
			String meaningfulName = end.concat(" ").concat(start);
			return meaningfulStopName(meaningfulName);
		}
		if (FlagStop.equals("FLAGSTOP")) {
			String start = stopName.substring(0, 8);
			String end = stopName.substring(9);
			String meaningfulName = end.concat(" ").concat(start);
			return meaningfulStopName(meaningfulName);
		} else
			return stopName;
	}

	public TST() {

	}

	public int size() {
		return n;
	}

	public boolean contains(String key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to contains() is null");
		}
		return get(key) != null;
	}


	public Value get(String key) {
		if (key == null) {
			throw new IllegalArgumentException("calls get() with null argument");
		}
		if (key.length() == 0)
			throw new IllegalArgumentException("key must have length >= 1");
		Node<Value> x = get(root, key, 0);
		if (x == null)
			return null;
		return x.val;
	}


	private Node<Value> get(Node<Value> x, String key, int d) {
		if (x == null) return null;
		if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
		char c = key.charAt(d);
		if      (c < x.c)
			return get(x.left,  key, d);
		else if (c > x.c)
			return get(x.right, key, d);
		else if (d < key.length() - 1)
			return get(x.mid,   key, d+1);
		else   
			return x;
	}


	public void put(String key, Value val) {
		if (key == null) {
			throw new IllegalArgumentException("calls put() with null key");
		}
		if (!contains(key))
			n++;
		else if(val == null)
			n--;       // delete existing key
		root = put(root, key, val, 0);
	}

	private Node<Value> put(Node<Value> x, String key, Value val, int d) {
		char c = key.charAt(d);
		if (x == null) {
			x = new Node<Value>();
			x.c = c;
		}
		if      (c < x.c)
			x.left  = put(x.left,  key, val, d);
		else if (c > x.c)
			x.right = put(x.right, key, val, d);
		else if (d < key.length() - 1)
			x.mid   = put(x.mid,   key, val, d+1);
		else 
			x.val   = val;
		return x;
	}


	public Iterable<String> keysWithPrefix(String prefix) {
		if (prefix == null) {
			throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
		}
		Queue<String> queue = new Queue<String>();
		Node<Value> x = get(root, prefix, 0);
		if (x == null)
			return queue;
		if (x.val != null)
			queue.enqueue(prefix);
		collect(x.mid, new StringBuilder(prefix), queue);
		return queue;
	}


	private void collect(Node<Value> x, StringBuilder prefix, Queue<String> queue) {
		if (x == null)
			return;
		collect(x.left,  prefix, queue);
		if (x.val != null)
			queue.enqueue(prefix.toString() + x.c);
		collect(x.mid,   prefix.append(x.c), queue);
		prefix.deleteCharAt(prefix.length() - 1);
		collect(x.right, prefix, queue);
	}

}