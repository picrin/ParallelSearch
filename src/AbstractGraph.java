/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 by Adam Kurkiewicz
 * You can contact me by e-mail at: adam /at\ kurkiewicz /dot\ pl
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software. 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractGraph<NodeType extends DeNode<?, ?>> implements SCCGraph<NodeType> {
	protected TreeMapComparator comparator = new TreeMapComparator();
	private <T extends DeNode<?, ?>> TreeMap<Long, T>
			unorderedToOrdered(NonBlockingHashMap<Long, T> map){
		return new TreeMap<>(map);
	}
	
	private ArrayList<TreeMap<Long, ? extends DeNode<?, ?>>>
			queueToArrayList(ConcurrentLinkedQueue<NonBlockingHashMap<Long, ? extends DeNode<?, ?>>> queue){
		int size = queue.size();
		Iterator<NonBlockingHashMap<Long, ? extends DeNode<?, ?>>> iter = queue.iterator();
		ArrayList<TreeMap<Long, ? extends DeNode<?, ?>>> arrayList = new ArrayList<>(size);
		while (iter.hasNext()){
			arrayList.add(unorderedToOrdered(iter.next()));
		}
		Collections.sort(arrayList, comparator);
		return arrayList;
	}
	
	//public void magia(SCCGraph<? extends DeNode<?, ?>> t) {
	//}
	//@Override
	public int compareTo(SCCGraph<? extends DeNode<?, ?>> otherGraph) {
		
		/*
		 * TODO This is really bad, and I wouldn't exclude a remote possibilty of
		 * this being a java bug. In any case, it should be quite carefully checked,
		 * once there is more time.
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayList<TreeMap<Long, ? extends DeNode<?, ?>>> array1 =
				queueToArrayList( (ConcurrentLinkedQueue<NonBlockingHashMap<Long, ? extends DeNode<?, ?>>>)
						(ConcurrentLinkedQueue) otherGraph.getSolutions());
		
		/*
		 * TODO vide supra. 
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayList<TreeMap<Long, ? extends DeNode<?, ?>>> array2 = 
				queueToArrayList( (ConcurrentLinkedQueue) this.getSolutions());
		
		//System.out.println(array1);
		//System.out.println(array2);
		if (array1.size() < array2.size()) return -1;
		if (array1.size() > array2.size()) return 1;
		else{
			int size = array1.size();
			for(int i = 0; i < size; i++){
				int comparatorValue = comparator.compare(array1.get(i), array2.get(i));
				if (comparatorValue != 0) return comparatorValue; 
			}
		}

		//while

		//ConcurrentLinkedQueue<NonBlockingHashMap<Long, ? extends DeNode<?, ?>>> concurrentLinkedQueue = this.getSolutions();
		//ConcurrentLinkedQueue<NonBlockingHashMap<Long, ? extends DeNode<?, ?>>> array2 =
				//concurrentLinkedQueue;
		
		return 0;
	}

}
