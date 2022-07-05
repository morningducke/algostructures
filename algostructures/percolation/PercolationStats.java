/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] testFrac;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        testFrac = new double[trials];
        for(int i = 0; i < trials; i++) {
            int x = 0;
            Percolation grid = new Percolation(n);
            while (!grid.percolates()) {
                int rnd = StdRandom.uniform(n * n);
                int col = rnd % n + 1;
                int row = rnd / n + 1;
                while (grid.isOpen(row, col)) {
                    rnd = StdRandom.uniform(n * n);
                    col = rnd % n + 1;
                    row = rnd / n + 1;

                }
                grid.open(row, col);
                x++;
            }
            testFrac[i] = (double) x /(n*n);

        }



    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(testFrac);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(testFrac);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96/Math.sqrt(testFrac.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96/Math.sqrt(testFrac.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean                    =" + ps.mean());
        StdOut.println("stddev                  =" + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}
