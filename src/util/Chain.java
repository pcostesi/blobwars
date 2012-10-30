package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
		private int lastIdx = 0;
		
		public ChainIterator(){
			for (Iterable<E> iterable : iterables){	
				iterators.add(iterable.iterator());
			}
			lastIdx = iterators.size() - 1;
		
		}
		
		@Override
		public boolean hasNext() {
			while (idx < lastIdx && !iterators.get(idx).hasNext()){
				idx++;
			}
			if (idx == lastIdx){
				return iterators.get(lastIdx).hasNext();
			} else if (idx < lastIdx && idx >= 0){
				return true;
			}
			return false;
		}
	
		@Override
		public E next() {
			if (!hasNext()){
				throw new NoSuchElementException();
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