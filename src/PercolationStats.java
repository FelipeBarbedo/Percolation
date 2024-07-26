import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private Percolation percolate;
    private double percolationThreshold;
    private double mean;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        percolate = new Percolation(n);
        percolationThreshold = 0.0;

        for (int i = 0; i < trials; i++) {

            while (!percolate.percolates()) {
                int randomSiteNumber = StdRandom.uniformInt(n * n);
                int row = randomSiteNumber / n;
                int col = randomSiteNumber % n;

                percolate.open(row, col);
            }

            percolationThreshold += (double) percolate.numberOfOpenSites() / (n * n);
        }

        mean = percolationThreshold / trials;
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0.0;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0.0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0.0;
    }

    // test client (see below)
    public static void main(String[] args) {

        PercolationStats stats = new PercolationStats(200, 200);

        System.out.println(stats.mean());
    }

}
