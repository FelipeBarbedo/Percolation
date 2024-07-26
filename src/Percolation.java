import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF gridConnectionTree;
    private int openSites;
    private final int[][] grid;
    private final int n;
    private final int virtualUpSite;
    private final int virtualDownSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        gridConnectionTree = new WeightedQuickUnionUF((n * n) + 2);
        virtualUpSite = n * n;
        virtualDownSite = (n * n) + 1;
        grid = new int[n][n];
        openSites = 0;
        this.n = n;

        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid.length; col++)
                grid[row][col] = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int p;

        if (this.grid[row][col] == 0) {
            p = row * this.n + col;
            this.grid[row][col] = 1;
            this.openSites++;
        } else {
            return;
        }

        // first row
        if (row == 0) {
            gridConnectionTree.union(p, virtualUpSite);
        }

        // last row
        if (row == this.n - 1) {
            gridConnectionTree.union(p, virtualDownSite);
        }

        // up
        if (row > 0) {
            if (this.isOpen(row - 1, col)) {
                int q = (row - 1) * this.n + col;
                gridConnectionTree.union(p, q);
            }
        }

        // down
        if (row < this.n - 1) {
            if (this.isOpen(row + 1, col)) {
                int q = (row + 1) * this.n + col;
                gridConnectionTree.union(p, q);
            }
        }

        // left
        if (col > 0) {
            if (this.isOpen(row, col - 1)) {
                int q = row * this.n + (col - 1);
                gridConnectionTree.union(p, q);
            }
        }

        // right
        if (col < this.n - 1) {
            if (this.isOpen(row, col + 1)) {
                int q = row * this.n + (col + 1);
                gridConnectionTree.union(p, q);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // calculate the number that represents the element (row, col) on the gridConnectionTree
        int p = row * this.n + col;

        return gridConnectionTree.find(virtualUpSite) == gridConnectionTree.find(p);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return gridConnectionTree.find(virtualUpSite) == gridConnectionTree.find(virtualDownSite);
    }
}