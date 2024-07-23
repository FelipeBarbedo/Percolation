import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {

    private WeightedQuickUnionUF gridConnectionTree;
    private int[][] grid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        gridConnectionTree = new WeightedQuickUnionUF((n * n) + 2);
        grid = new int[n][n];

        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid[row].length; col++)
                grid[row][col] = -1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        grid[row][col] = 0;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col] == 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return grid[row][col] == 1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;

        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid[row].length; col++)
                if (this.isOpen(row, col))
                    count++;

        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int col = 0; col < grid.length; col++)
            if (this.isFull(grid.length - 1, col))
                return true;

        return false;
    }

    public static void main(String[] args) {
        StdOut.printf("Hello");
    }
}