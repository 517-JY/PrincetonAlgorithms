/* *****************************************************************************
 *  Name: Jiayin Li
 *  Date: 210728
 *  Description: Permutation implementation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        // int i = 0;
        int j = 0;

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
            // i++;
            // StdOut.print(" i = " + i + " ");
            // StdOut.println("s = " + s);
        }

        // StdOut.println("Size of queue is " + queue.size());

        for (String s : queue) {
            if (j < k) {
                StdOut.println(s);
                j++;
            }
            else {
                break;
            }
        }


    }
}
