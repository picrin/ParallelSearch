import java.util.concurrent.ExecutorService;

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

    final class ExplorePredecessors extends GraphExplorator{
        protected ExplorePredecessors(Node start, DataGraph dg){
            super(start, dg);
        }
        public ExplorePredecessors(ExecutorService executor, /*Callable<?> voidToCall,*/Node start, DataGraph dg){
        	super(executor, start, dg);
        }
        @Override
        public void run(){
            Node current = start;
            while(current.visit()){
                for(int i = 1; i < current.index; i++){
                    Node nextChild = current.children[i];
                    if (!nextChild.visited.get()){
                    	counter.incrementAndGet();
                    	executor.execute(new ExplorePredecessors(current.children[i], dg));
                    }
                }
                if (current.index > 0){
                    current = current.children[0];
                }
            }
            if (counter.decrementAndGet() == 0) this.whenFinished();
        }
    }
