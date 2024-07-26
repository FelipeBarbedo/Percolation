import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

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
                int row = randomSiteNumber / n;
                int col = randomSiteNumber % n;

                percolate.open(row, col);
            }

            percolationThresholds[i] = (double) percolate.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0.0;

        for (double threshold : percolationThresholds)
            sum += threshold;

        return sum / percolationThresholds.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = this.mean();
        double sum = 0.0;

        for (double threshold : percolationThresholds)
            sum += Math.pow((threshold - mean), 2);

        return Math.sqrt(sum / (percolationThresholds.length - 1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double stdDeviation = this.stddev();
        double mean = this.mean();

        return mean - ((1.96 * stdDeviation) / Math.sqrt(percolationThresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double stdDeviation = this.stddev();
        double mean = this.mean();

        return mean + ((1.96 * stdDeviation) / Math.sqrt(percolationThresholds.length));
    }

    // test client (see below)
    public static void main(String[] args) {

        // PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        PercolationStats stats = new PercolationStats(200, 100);

        StdOut.println("mean \t\t\t\t\t= " + stats.mean());
        StdOut.println("stddev \t\t\t\t\t= " + stats.stddev());
        StdOut.print("95% confidence interval = [");
        StdOut.print(stats.confidenceLo() + ", ");
        StdOut.print(stats.confidenceHi());
        StdOut.print("]");

    }
}
