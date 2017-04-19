import junit.framework.TestCase;

import java.util.Iterator;

public class Test extends TestCase {

    public void testAdd(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();

        for(int i = 1; i < 12; i++){
            binarySearchTree.add(i);
            assertEquals("Tree is wrong size", i, binarySearchTree.size());
        }
    }

    public void testContains(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();

        binarySearchTree.add(11);

        assertTrue("Tree should contain element", binarySearchTree.contains(11));

        assertFalse("Tree should not contain element", binarySearchTree.contains(7));
    }

    public void testRemove(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();

        binarySearchTree.add(11);

        assertTrue("Tree should contain element", binarySearchTree.contains(11));

        assertFalse("Tree should not contain element", binarySearchTree.contains(7));
    }

    public void testManyAddRemove()
    {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();

        int cnt = 1000;

        for( int i = 0; i < 1000; ++i )
        {
            binarySearchTree.add( i );
            if( Math.random() > 0.5 )
            {
                if( !binarySearchTree.isEmpty() )
                {
                    binarySearchTree.remove(i);
                    cnt--;
                }
            }
        }

        assertEquals( "Correct number of items", cnt, binarySearchTree.size());
    }

    public void testIterator(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();

        for( int i = 0; i < 100; ++i ) {
            binarySearchTree.add(i);
        }

        Iterator i = binarySearchTree.iterator();

        int x = 0;

        while (i.hasNext()){
            assertEquals("Should be equal", x, i.next());
            i.remove();
            x++;
        }

        assertFalse("Iterator should be empty", i.hasNext());

        assertTrue("Tree should be empty", binarySearchTree.isEmpty());
    }

}
