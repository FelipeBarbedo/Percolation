import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF gridConnectionTree;
    private int[][] grid;
    private int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        gridConnectionTree = new WeightedQuickUnionUF((n * n) + 2);
        grid = new int[n][n];
        this.n = n;

        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid[row].length; col++)
                grid[row][col] = -1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        boolean up = false, down = false;
        boolean left = false, right = false;

        if (row > 0)
            up = this.isFull(row - 1, col);

        if (row < n)
            down = this.isFull(row + 1, col);

        if (col > 0)
            left = this.isFull(row, col - 1);

        if (col < n)
            right = this.isFull(row, col + 1);

        if (row == 0)
            gridConnectionTree.union(col, this.n + 1);

        if (up) {
            gridConnectionTree.union(row * (n + 1) + col, (row - 1) * (n + 1) + col);
            grid[row][col] = 1;
        } else if (down) {
            gridConnectionTree.union(row * (n + 1) + col, (row + 1) * (n + 1) + col);
            grid[row][col] = 1;
        } else if (left) {
            gridConnectionTree.union(row * (n + 1) + col, row * (n + 1) + (col - 1));
            grid[row][col] = 1;
        } else if (right) {
            gridConnectionTree.union(row * (n + 1) + col, row * (n + 1) + (col + 1));
            grid[row][col] = 1;
        }
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
        return gridConnectionTree.find(this.n + 1) == gridConnectionTree.find(this.n + 2);
    }
}