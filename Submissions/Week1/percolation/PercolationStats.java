/* *****************************************************************************
 *  Name:              Iris Li
 *  Coursera User ID:  123456
 *  Last modified:     7/20/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // Because 1.96 will be used more than once
    private static final double CONFIDENCE_95 = 1.96;
    // perform independent trails on an n-by-n grid

    // The private instance (or static) variable 'threasholds' can be made 'final';
    private final double[] threasholds;

    // The private instance (or static) variable 'trials' can be made 'final';
    private final int trials;

    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("Parameter n " + n + " should be greater than 0");
        }

        if (trials <= 0) {
            throw new IllegalArgumentException(
                    "Parameter trails " + trials + " should be greather than 0");
        }

        threasholds = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                // Returns a random integer uniformly in [1, n + 1). as row index
                int row = StdRandom.uniform(n) + 1;
                // Returns a random integer uniformly in [1, n + 1). as col index
                int col = StdRandom.uniform(n) + 1;
                perc.open(row, col);
            }
            threasholds[i] = perc.numberOfOpenSites() * 1.0 / (n * n);
        }
    }

    // sample mean of percolation threashold
    public double mean() {
        return StdStats.mean(threasholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threasholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }


    // test client
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trails);

        StdOut.println("mean                     = " + stats.mean());
        StdOut.println("stddev                   = " + stats.stddev());
        StdOut.println("95% confidence interval  = [" + stats.confidenceLo() + ", " + stats
                .confidenceHi() + "]");

    }
}
