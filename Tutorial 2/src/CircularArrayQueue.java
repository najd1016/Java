import java.util.NoSuchElementException;

public class CircularArrayQueue implements MyQueue {

    private Integer[] array;
    private int tailPosition;
    private int headPosition;

    public CircularArrayQueue(){
        array = new Integer[10];
        tailPosition = 0;
        headPosition = 0;
    }

    @Override
    public void enqueue(int in) {
        if(getCapacityLeft() > 0) {

            if(tailPosition == array.length)
                tailPosition = 0;

            array[tailPosition] = in;

            tailPosition++;
        }else{

            Integer[] tempArray = array;

            array = new Integer[tempArray.length*2];

            int startIndex = tailPosition;

            for (int index = 0; index < tempArray.length; index++) {

                if(startIndex == tempArray.length)
                    startIndex = 0;

                array[index] = tempArray[startIndex];

                startIndex++;
            }

            headPosition = 0;
            tailPosition = tempArray.length;

            array[tailPosition] = in;
            tailPosition++;
        }

        //for (Integer element: array) {
        //    System.out.print(element + ",");
        //}
        //System.out.println();
    }

    @Override
    public int dequeue() throws NoSuchElementException {
        if(array[headPosition] != null){

            int element = array[headPosition];

            array[headPosition] = null;
            headPosition++;

            if(headPosition == array.length)
                headPosition = 0;

            //for (Integer x: array) {
            //    System.out.print(x + ",");
            // }
            // System.out.println();

            return element;
        }else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public int noItems() {
        int count = 0;

        for (Integer element: array) {
            if(element != null)
                count++;
        }

        return count;
    }

    @Override
    public boolean isEmpty() {
        return (getCapacityLeft() == array.length);
    }

    public int getCapacityLeft(){
        return (array.length - noItems());
    }
}
