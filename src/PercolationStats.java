import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] percolationThresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        percolationThresholds = new double[trials];
        Percolation percolate;

        for (int i = 0; i < trials; i++) {
            percolate = new Percolation(n);

            while (!percolate.percolates()) {
                int randomSiteNumber = StdRandom.uniformInt(n * n);
                int row = (randomSiteNumber / n) + 1;
                int col = (randomSiteNumber % n) + 1;

                percolate.open(row, col);
            }

            percolationThresholds[i] = (double) percolate.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double stdDeviation = this.stddev();
        double mean = this.mean();

        return mean - ((CONFIDENCE_95 * stdDeviation) / Math.sqrt(percolationThresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double stdDeviation = this.stddev();
        double mean = this.mean();

        return mean + ((CONFIDENCE_95 * stdDeviation) / Math.sqrt(percolationThresholds.length));
    }

    // test client (see below)
    public static void main(String[] args) {

        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        // PercolationStats stats = new PercolationStats(200, 100);

        StdOut.println("mean \t\t\t\t\t= " + stats.mean());
        StdOut.println("stddev \t\t\t\t\t= " + stats.stddev());
        StdOut.print("95% confidence interval = [");
        StdOut.print(stats.confidenceLo() + ", ");
        StdOut.print(stats.confidenceHi());
        StdOut.print("]");

    }
}
