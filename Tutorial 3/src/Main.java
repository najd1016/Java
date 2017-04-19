import java.util.Iterator;
import java.util.Random;

public class Main {

    private static <T> double countAvgDepth(BinarySearchTree<T> bst){
        int sum = 0;

        Iterator iterator = bst.iterator();

        while(iterator.hasNext()){
            sum += bst.getDepth(iterator.next());
        }

        return ((double)sum)/bst.size();
    }

    public static void main(String[] args){

        for(int mul = 1; mul <= 10; mul++) {
            BinarySearchTree<Integer> tree = generateRandomTree(100 * mul);

            double[] times = new double[10];

            for(int rep = 0; rep < 10; rep++)
                times[rep] = countAvgDepth(tree);


            System.out.println("Size "+tree.size()+ " "+median(times));
        }
    }

    private static BinarySearchTree<Integer> generateRandomTree(int size){
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        Random ran = new Random();

        while(bst.size() < size)
            bst.add(ran.nextInt());

        return bst;
    }

    private static double median(double[] array){
        return array[array.length/2];
    }
}
