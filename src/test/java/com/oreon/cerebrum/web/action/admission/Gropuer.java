package com.oreon.cerebrum.web.action.admission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class Gropuer {

	public static void main(String[] args) {
		
		String[][] arr = { { "ON", "Toronto", "F", "1" },
				{ "ON", "York", "M", "3" },
				
				{ "AB", "Calgary", "F", "1", }};

		/*
		String[][] arr = { { "ON", "Toronto", "F", "1" },
				{ "ON", "Toronto", "M", "1" },
				{ "ON", "Brampton", "M", "1" },
				{ "ON", "scarborough", "F", "1", },
				{ "AB", "Calgary", "F", "1", },
				
		};*/

		//Node root = new Node("root");

		TreeNode root = new DefaultTreeNode("", null);
		
		List<List<String>> tuples = new ArrayList<List<String>>();
		
		for (String[] strings : arr) {
			 tuples.add(Arrays.asList(strings));
			 //addto(myroot, Arrays.asList(strings));
			 //myroot.put(strings[0], strings[1] +  strings[2]);
		}
		
		
		
		ListMultimap myroot = ArrayListMultimap.create();
		
		for (List<String> list : tuples) {
			tradd(list, root);
		}

		System.out.println(root);
		
		
		for (Object firstName : myroot.keySet()) {
			Object lastNames = myroot.get(firstName);
			System.out.println(firstName + ": " + lastNames);
		}

	}
	
	
	private static void  tradd(List<String> list, TreeNode parent){
		if(list.size() >= 2){
			System.out.println(" adding " + list.get(0) + " " + parent);
			TreeNode child = new DefaultTreeNode( list.get(0) , parent);
			tradd(list.subList(1, list.size()), child);
		}
	}
	
	//@SuppressWarnings(value=)
	private static ListMultimap addto(ListMultimap myroot, List<String> asList) {
		if(asList.size() > 2){
			
			String first = asList.get(0);
			ListMultimap child =  ArrayListMultimap.create();//(ListMultimap) myroot.get(first).get(0);
			//child.put(arg0, arg1)
			 
			ListMultimap result = addto(child , asList.subList(1, asList.size()) );
			if(result != null)
				myroot.put(first,  result);
			return child;
		}
		return null;
	}

	public static void add(ListMultimap myroot, List<String[]> tuples, int start){
		if(tuples.isEmpty())
			return;
		
		ListMultimap child = ArrayListMultimap.create();
		
		int newstart = start + 1;
		if( newstart < tuples.get(0).length - 1 )
			add (child, tuples, newstart);
		
		for (String[] strings : tuples) {
			for(int i = start; i < strings.length; i++){
				//if(!child.isEmpty())
				myroot.put(strings[start],  child);
			}
		}
		
		//System.out.println(myroot);
		
		
		
	}
	
	/*
	private static void add(ListMultimap<String, String> map,  String[][] children){
		
		
		for (String child : children) {
			map.put(root, child);
			ListMultimap<String, String> chldmap = ArrayListMultimap.create();
			add(chldmap , children);
		}
		
		

	}*/

}

class Node {
	public Node(String name) {
		this.name = name;
	}

	public void add(Node node) {
		children.add(node);
	}

	String name;
	List<Node> children = new ArrayList<Node>();
}
