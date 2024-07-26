import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF gridConnectionTree;
    private final int virtualDownSite;
    private final int virtualUpSite;
    private final boolean[][] grid;
    private int openSites;
    private final int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        gridConnectionTree = new WeightedQuickUnionUF((n * n) + 2);
        virtualDownSite = (n * n) + 1;
        virtualUpSite = (n * n);
        grid = new boolean[n][n];
        openSites = 0;
        this.n = n;

        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid.length; col++)
                grid[row][col] = false;
    }

    private void setGridValue(int row, int col) {
        this.grid[row - 1][col - 1] = true;
    }

    private boolean getGridValue(int row, int col) {
        return this.grid[row - 1][col - 1];
    }

    private int numberOnGridConTree(int row, int col) {
        return (row - 1) * this.n + (col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int p;

        if (row <= 0 || col <= 0 || row > this.n || col > this.n)
            throw new IllegalArgumentException();

        if (!getGridValue(row, col)) {
            p = numberOnGridConTree(row, col);
            setGridValue(row, col);
            this.openSites++;
        } else {
            return;
        }

        // first row
        if (row == 1)
            this.gridConnectionTree.union(p, virtualUpSite);

        // last row
        if (row == this.n)
            this.gridConnectionTree.union(p, virtualDownSite);

        // up
        if (row > 1)
            if (this.isOpen(row - 1, col))
                this.gridConnectionTree.union(p, numberOnGridConTree(row - 1, col));

        // down
        if (row < this.n)
            if (this.isOpen(row + 1, col))
                this.gridConnectionTree.union(p, numberOnGridConTree(row + 1, col));

        // left
        if (col > 1)
            if (this.isOpen(row, col - 1))
                this.gridConnectionTree.union(p, numberOnGridConTree(row, col - 1));

        // right
        if (col < this.n)
            if (this.isOpen(row, col + 1))
                this.gridConnectionTree.union(p, numberOnGridConTree(row, col + 1));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.n || col > this.n)
            throw new IllegalArgumentException();

        return getGridValue(row, col);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.n || col > this.n)
            throw new IllegalArgumentException();
        // calculate the number that represents the element (row, col) on the gridConnectionTree
        int p = numberOnGridConTree(row, col);

        return this.gridConnectionTree.find(virtualUpSite) == this.gridConnectionTree.find(p);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.gridConnectionTree.find(virtualUpSite) == this.gridConnectionTree.find(virtualDownSite);
    }
}