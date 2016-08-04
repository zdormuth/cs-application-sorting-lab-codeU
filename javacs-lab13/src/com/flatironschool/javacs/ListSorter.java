/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		/*System.out.println("mergeSort alg");
		for (T element: list) {
			System.out.println(element);
		}
		System.out.println("done");
		*/
		// split list in half
		int size = list.size();
		// base case - no elements to merge
		if (size <= 1) {
			return list;
		}
		// sort halves and merge sorted halfs into a complete sorted list
		List<T> firstHalf = mergeSort(new LinkedList<T>(list.subList(0, size/2)), comparator);
		List<T> secondHalf = mergeSort(new LinkedList<T>(list.subList(size/2, size)), comparator);
		/*System.out.println("1st half");
		for (T element: firstHalf) {
			System.out.println(element);
		}
		System.out.println("2nd half");
		for (T element: secondHalf) {
			System.out.println(element);
		}*/

		//mergeSort(firstHalf, comparator);
		//mergeSort(secondHalf, comparator);
		// merge sorted halfs into a complete sorted list
		return mergeHalfs(firstHalf, secondHalf, comparator);
	}
	
	public List<T> mergeHalfs(List<T> firstHalf, List<T> secondHalf, Comparator<T> comparator) {
		List<T> mergedList = new LinkedList<T>();
		int iterate = firstHalf.size() + secondHalf.size();
		List<T> add;
		// merge results into LL
		for (int i = 0; i < iterate; i++) {
			add = actualMerge(firstHalf, secondHalf, comparator);
			mergedList.add(add.remove(0));
			/*// make sure there are elements in each list to compare
			// if first is empty return second
			if (firstHalf.isEmpty()) {
				mergedList.add(secondHalf.remove(0));
				
				break;
				//secondHalf.remove(i);
			}
			if (secondHalf.isEmpty()) {
				mergedList.add(firstHalf.remove(0));
				break;
				//firstHalf.remove(i);
			}
			//if both lists have elements 
			// check which element from which list to add (ascending order)
			compare = comparator.compare(firstHalf.get(i), secondHalf.get(i)); // returns - ls, 0 =, + gt, first __ second
			// first is less
			if (compare < 0) {
				mergedList.add(firstHalf.remove(0));
				break;
				//firstHalf.remove(i);
			}
			if (compare > 0) {
				mergedList.add(secondHalf.remove(0));
				break;
				//secondHalf.remove(i);*/
		}
		return mergedList;
	}
	
	public List<T> actualMerge(List<T> firstHalf, List<T> secondHalf, Comparator<T> comparator) {
		if (firstHalf.isEmpty()) {
			return secondHalf;
		}
		if (secondHalf.isEmpty()) {
			return firstHalf;
		}
		int compare = comparator.compare(firstHalf.get(0), secondHalf.get(0));
		if (compare < 0) {
			return firstHalf;
		}
		if (compare > 0) {
			return secondHalf;
		}
		// else equal to
		return firstHalf;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		// add all elements to a priority queue
		PriorityQueue<T> pq = new PriorityQueue<T>(list.size(), comparator);
		for (T element: list) {
			pq.offer(element);
		}
		// empty the list so we can add elements in ascending order
		list.clear();
		// add elements back into list from queue so they are sorted in ascending order - only until pq is empty
		while (pq.size() != 0) {
			list.add(pq.poll());
		}
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> pq = new PriorityQueue<T>(list.size(), comparator);
		for (T element: list) {
			// branch 1 - heap is not full
			if (pq.size() < k) {
				pq.offer(element);
				continue;
			}
			// branch 2 & 3
			// compare element to smallest element in heap
			int compare = comparator.compare(element, pq.peek()); // returns - lt, 0 =, + gt if element __ pq.peek
			// branch 3 - element greater than smallest element in heap
			if (compare > 0) {
				pq.poll();
				pq.offer(element);
			}
			// branch 2 - element less than or equal to smallest element in heap --> do nothing 
			else {
				continue;
			}
		}
		// add to list
		list.clear();
		while (pq.size() != 0) {
			list.add(pq.poll());
		}
        return list;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
