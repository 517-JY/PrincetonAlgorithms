/* *****************************************************************************
 *  Name:              Iris Li
 *  Coursera User ID:  007517
 *  Last modified:     July 14, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    // occupied[i][j] = false [i][j] is blocked
    // openSite[i][j] = true  [i][j] is open
    private boolean[][] openSite;
    private int numberOfOpenSites; // number of open sites


    // Creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Constructor parameter " + n + " is less than 0");
        }
        uf = new WeightedQuickUnionUF((n + 2) * (n + 2));
        openSite = new boolean[n + 2][n + 2];
        numberOfOpenSites = 0;
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < n + 2; j++) {
                // initialize all sites to be blocked
                openSite[i][j] = false;
            }
        }

        openSite[0][0] = true;
        openSite[n + 1][0] = true;
        // make all sites in the first valid row connected to the virtual top site (0)
        for (int i = 1; i <= n; i++) {
            uf.union(0, n + 2 + i);
        }

        // make all sites in the first valid row connected to the virtual bottom site (n+1)*(n+2)
        for (int i = 1; i <= n; i++) {
            uf.union((n + 1) * (n + 2), n * (n + 2) + i);
        }
    }


    // opens the site(row, col) if it is not open already
    public void open(int row, int col) {
        // StdOut.println("Testing: Inside open");
        validateRange(row, col);
        int index = row * openSite.length + col;
        // StdOut.println("Testing: current index is " + index);
        if (!isOpen(row, col)) {
            openSite[row][col] = true;
            numberOfOpenSites++;

            int leftIdx = index - 1;
            int rightIdx = index + 1;
            int upIdx = index - openSite.length;
            int downIdx = index + openSite.length;

            // if the site to its left is open then makes a connection
            if (isOpen(row, col - 1)) {
                uf.union(index, leftIdx);
            }
            // if the site to its right is open then makes a connection
            if (isOpen(row, col + 1)) {
                uf.union(index, rightIdx);
            }

            // if the site at its above is open then makes a connection
            if (isOpen(row - 1, col)) {
                uf.union(index, upIdx);
            }

            // if the site at its below is open then makes a connection
            if (isOpen(row + 1, col)) {
                uf.union(index, downIdx);
            }
        }
    }

    // is the site(row, col) open?
    public boolean isOpen(int row, int col) {
        // StdOut.println("Testing: Inside isOpen");
        // validateRange(row, col);
        return openSite[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // StdOut.println("Testing: Inside isFull");
        // validateRange(row, col);
        int index = row * openSite.length + col;
        if (isOpen(row, col)) {
            // StdOut.println("Testing:");
            // StdOut.println("uf.find(0) = " + uf.find(0));
            // StdOut.println("uf.find(index)  = " + uf.find(index));
            return uf.find(0) == uf.find(index);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    // Check wehther the virtual top site and virtual bottom site are in the same componenet

    /**
     * Checks whether the virtual top site and the virtual bottom site are in the same component
     *
     * @return true, if virtual top site and virtual bottom site are in the same component.
     * Otherwise, return false
     */
    public boolean percolates() {
        // StdOut.println("Testing whether the virtual top/bottom connected " +
        //                        (uf.find(0) == uf
        //                                .find((openSite.length - 1) * openSite.length)));
        return isFull(openSite.length - 1, 0);
    }


    /**
     * Validate both row and col is valid index
     *
     * @param row the given row index
     * @param col the given col index
     */
    private void validateRange(int row, int col) {
        if (row < 1 || row > openSite.length - 2) {
            throw new IllegalArgumentException(
                    "row index " + row + " is outside the provided range");
        }

        if (col < 1 || col > openSite[row].length - 2) {
            throw new IllegalArgumentException(
                    "col index " + col + " is outside the provided range");
        }
    }

    // test client
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation perc = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            perc.open(row, col);
            // StdOut.println("Once open site at row: " + row + " col: " + col + ".");
            // StdOut.println("The system is percolate or not ? " + perc.percolates());
        }
        // StdOut.println("Open site number is " + perc.numberOfOpenSites());
        // StdOut.println("The system is percolate or not ? " + perc.percolates());
    }
}
