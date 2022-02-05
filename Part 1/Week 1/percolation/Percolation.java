import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final byte CLOSE = 0;
    private static final byte OPEN = 1;
    private static final byte TOP = OPEN << 1;
    private static final byte BOTTOM = OPEN << 2;
    private static final byte PERCOLATE = (TOP | BOTTOM);

    private final WeightedQuickUnionUF id;
    private byte[] open;
    private final int length;
    private int nopen;
    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        length = n;
        int size = n * n;
        nopen = 0;
        id = new WeightedQuickUnionUF(size);
        open = new byte[size]; // initialize all open states to 0
    }

    private int[] adjust(int row, int col) {
        if (row > length || col > length || row < 1 || col < 1) {
            throw new IllegalArgumentException(); // validates before adjusting
        }
        int[] r = { row - 1, col - 1 };
        return r;
    }

    private int index(int row, int col) {
        return row * length + col;
    }

    private byte connect(int idx, int idxnear) {
        int findnear = id.find(idxnear); // idxnear: index of the neighbour of idx
        if (open[findnear] != CLOSE) {
            id.union(idx, findnear);
            return open[findnear];
        }
        return CLOSE;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int[] pos = adjust(row, col);
        row = pos[0];
        col = pos[1];
        int index = index(row, col);
        if ((open[index] & OPEN) == 0) {
            byte status = OPEN;
            if (col - 1 >= 0)
                status |= connect(index, index - 1);
            if (col + 1 < length)
                status |= connect(index, index + 1);
            if (row - 1 >= 0)
                status |= connect(index, index - length);
            if (row + 1 < length)
                status |= connect(index, index + length);
            int f = id.find(index);
            if (row == 0)
                open[f] |= TOP; // connect to virtual top at the top row
            if (row == length - 1) {
                open[f] |= BOTTOM;
            } // connect to virtual bottom at the bottom row (bottom = 4)
            open[f] |= status;
            if ((open[f] & PERCOLATE) == PERCOLATE) { // (TOP|BOTTOM) = (2|4) = 6
                percolates = true;
            }
            open[index] = open[f];
            nopen++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int[] pos = adjust(row, col);
        if (open[index(pos[0], pos[1])] != 0) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int[] pos = adjust(row, col);
        int index = index(pos[0], pos[1]);
        if (open[index] != 0) {
            return (open[id.find(index)] & TOP) == TOP;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nopen;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    // test client (optional)
    public static void main(String[] args) {
        // Empty
    }
}