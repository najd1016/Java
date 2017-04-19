import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

import java.lang.reflect.Array;
import java.util.*;

public class CircularArrayRing<E> extends AbstractCollection<E> implements Ring<E>{

    private ArrayList<E> arrayList;
    private int maxSize;
    private int currentPosition;

    public CircularArrayRing(){
        arrayList = new ArrayList<>(10);
        maxSize = 10;
        currentPosition = 0;
    }

    public CircularArrayRing(int size){
        arrayList = new ArrayList<>(size);
        maxSize = size;
        currentPosition = 0;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {

        if(index >= size() || index < 0)
            throw new IndexOutOfBoundsException();

        int positionLastElement = currentPosition-index-1;

        if(positionLastElement < 0 && size() == maxSize)
            return arrayList.get(maxSize + positionLastElement);

        return (arrayList.get(positionLastElement));
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayRingIterator();
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    public boolean add(E e){
        System.out.println("Added "+e.toString());

        if(size() == maxSize) {
            arrayList.set(currentPosition, e);
        }else {
            arrayList.add(currentPosition, e);
        }

        currentPosition++;

        if(currentPosition == maxSize)
            currentPosition = 0;

        return false;
    }

    private class ArrayRingIterator implements Iterator<E>{

        private int index;
        private int iterations;

        private ArrayRingIterator(){
            index = currentPosition;
            iterations = 0;
        }

        @Override
        public boolean hasNext() {
            if(iterations < arrayList.size()) {
                return true;
            }else{
                return false;
            }
        }

        @Override
        public E next() {
            if(!hasNext())
                throw new NoSuchElementException();

            index = getNextIndex(index);

            E toReturn =  arrayList.get(index);

            iterations++;

            return toReturn;
        }


        public int getNextIndex(int i){
            if(i == 0){
                if(arrayList.size() == maxSize)
                    i = maxSize - 1;
            }else{
                i--;
            }
            return i;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
