
/* T07n02 - A bit more traditional implementation of binary search trees,
 * this time with a separate BinaryNode class.
 *
 * Just to keep other programmer's grubby fingers off of our node class,
 * we'll make it a nested class (aka inner class) of the BinarySearchTree
 * class.  We can poke our big nose into a BinaryNode object to peek at
 * its data (which saves us from having to implement getters and setters),
 * but no one else can.
 *
 * In this implementation, a BST maintains only a reference to the
 * root node of the tree (no data, as in the previous example).  However,
 * this does address a problem we noted with the "nodeless" 
 * implementation in T07n01.java; namely, that we couldn't have an 
 * empty tree.  Now we can.
 *
 * Another change from the design of the last example:  The use of
 * overloaded operations.  When the "outside world" asks a tree to do
 * something to itself, they don't have access to the tree's root, so 
 * they can't pass it in.  And it would be silly to do that anyway,
 * as the tree certainly knows how to find its own root.
 * However, to perform the operations recursively, we need to reference
 * a subtree with each call.  We solve this problem with a simple
 * 'gatekeeper' routine for each operation that we make available to the 
 * users of this class.  These routines just invoke the more general
 * methods that share their names, and these general methods are 
 * private; no reason to let anyone else try to use them.
 *
 * If you're into OO design, you might be annoyed by the use
 * of nulls to mark the fringe of the tree.  If so, see the next
 * example.  If not, see it anyway; you need to see the idea whether you
 * find nulls to be evil or not.
 *
 * 2024/11/24: Updated Java 8 code to more recent generic syntax.
 */

import java.io.*;
import java.util.*;

public class T07n02
{
    public static void main (String[] args)
    {
        BinarySearchTree<Integer> tree = null;

        tree = new BinarySearchTree<Integer>();
        tree.insert(50);
        tree.insert(40);
        tree.insert(60);
        tree.insert(45);
        tree.insert(42);
        tree.inOrder();
        System.out.println("");
    }
}

class BinarySearchTree<T extends Comparable<? super T>>
{
    protected BinaryNode<T> root;

    public BinarySearchTree ()
    {
        root = null;
    }

    public void insert (T newData)
    {
        root = insert(root, newData);
    }

    private BinaryNode<T> insert (BinaryNode<T> node, T newData)
    {
        if (node == null) {              // need to create a new subtree
            return new BinaryNode<T>(newData);
        } else if (newData.compareTo(node.data) < 0) {   // insert on left
            node.leftChild = insert(node.leftChild, newData); 
        } else {                                         // insert on right
            node.rightChild = insert(node.rightChild, newData);
        }
        return node;
    } 

    public void inOrder ()
    {
        inOrder(root);
    }

    private void inOrder (BinaryNode<T> node)
    {
        if (node != null) {
            inOrder(node.leftChild);
            System.out.print((node.data).toString() + " ");
            inOrder(node.rightChild);
        }
    }


    class BinaryNode<T extends Comparable<? super T>>
    {
        T data;
        BinaryNode<T> leftChild,  
                      rightChild; 

        public BinaryNode (T newData)
        {
            data = newData;
            leftChild = rightChild = null;
        }
    }

}
