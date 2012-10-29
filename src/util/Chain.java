package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Chain<E> implements Iterable<E>{

	private List<Iterable<E>> iterables;
	
	public Chain(Iterable<E>... iterables){
		this.iterables = new ArrayList<Iterable<E>>(iterables.length);
		for (Iterable<E> i : iterables){
			this.iterables.add(i);
		}
	}
	
	public Chain(List<Iterable<E>> iterables){
		this.iterables = iterables;
	}
	
	private class ChainIterator implements Iterator<E> {
	
		private List<Iterator<E>> iterators = new ArrayList<Iterator<E>>(iterables.size());
		private int idx = 0;
		
		public ChainIterator(){
			for (Iterable<E> iterable : iterables){
				
				iterators.add(iterable.iterator());
			}
		
		}
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return idx < iterators.size() && iterators.get(idx).hasNext();
		}
	
		@Override
		public E next() {
			if (!iterators.get(idx).hasNext()){
				idx++;
			} 
			return iterators.get(idx).next();
		}
	
		@Override
		public void remove() {
			iterators.get(idx).remove();
		}
	
	}

	@Override
	public Iterator<E> iterator() {
		return new ChainIterator();
	}

}