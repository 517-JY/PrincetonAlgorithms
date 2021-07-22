/* *****************************************************************************
 *  Name:              Iris Li
 *  Coursera User ID:  007517
 *  Last modified:     July 14, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // The private instance (or static) variable 'uf' can be made 'final';
    private final WeightedQuickUnionUF uf;
    private boolean[][] openSite; // occupied[i][j] = false [i][j] is blocked, true if is open
    private int numberOfOpenSites; // number of open sites

    // Creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Constructor parameter " + n + " is less than 0");
        }
        // Adds an outer layer that wraps the valid n * n grid
        uf = new WeightedQuickUnionUF((n + 2) * (n + 2));
        openSite = new boolean[n + 2][n + 2];
        numberOfOpenSites = 0;
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < n + 2; j++) {
                openSite[i][j] = false; // initialize all sites to be blocked
            }
        }
        openSite[0][0] = true;
        openSite[n + 1][0] = true;

        // makes all sites in the first valid row connected to the virtual top site (0)
        for (int i = 1; i <= n; i++) {
            uf.union(0, n + 2 + i);
        }
        // makes all sites in the last valid row connected to the virtual bottom site (n+1)*(n+2)
        for (int i = 1; i <= n; i++) {
            uf.union((n + 1) * (n + 2), n * (n + 2) + i);
        }
    }


    /**
     * Opens the site(row, col) if it is not open already
     *
     * @param row the given row index
     * @param col the given col index
     */
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


    /**
     * Checks whether the site is open with the given index
     *
     * @param row the given row index
     * @param col the given col index
     * @return true if the site is open, false otherwise
     */
    public boolean isOpen(int row, int col) {
        // StdOut.println("Testing: Inside isOpen");
        isFullValidateRange(row, col);
        return openSite[row][col];
    }


    /**
     * Checks whether the site is full or not
     *
     * @param row the given row index
     * @param col the given col index
     * @return true if there is a path between the virtual top site and the current site
     */
    public boolean isFull(int row, int col) {
        // StdOut.println("Testing: Inside isFull");
        isFullValidateRange(row, col);
        int index = row * openSite.length + col;
        if (isOpen(row, col) && numberOfOpenSites != 0) {
            // StdOut.println("Testing:");
            // StdOut.println("uf.find(0) = " + uf.find(0));
            // StdOut.println("uf.find(index)  = " + uf.find(index));
            return uf.find(0) == uf.find(index);
        }
        return false;
    }


    /**
     * Returns the number of open sites
     *
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }
    

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
            throw new IllegalArgumentException("row index " + row + " out of bounds");
        }

        if (col < 1 || col > openSite[row].length - 2) {
            throw new IllegalArgumentException("col index " + col + " out of bounds");
        }
    }


    /**
     * Validates both row and col is valid index
     *
     * @param row the given row index
     * @param col the given col index
     */
    private void isFullValidateRange(int row, int col) {
        if (row < 0 || row > openSite.length - 1) {
            throw new IllegalArgumentException("row index " + row + " out of bounds");
        }

        if (col < 0 || col > openSite[0].length - 1) {
            throw new IllegalArgumentException("col index " + col + " out of bounds");
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
        StdOut.println("Open site number is " + perc.numberOfOpenSites());
        StdOut.println("The system is percolate or not ? " + perc.percolates());
    }
}
