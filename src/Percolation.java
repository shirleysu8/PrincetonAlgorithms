import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int[][] id;
	private final boolean[][] checkSite;
	private final WeightedQuickUnionUF uf;
	private int count;
	private final int n;
	private final int top;
	private final int bottom;

	public Percolation(final int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		uf = new WeightedQuickUnionUF((n * n) + 2);
		top = 0;
		this.n = n;
		bottom = (n * n) + 1;
		id = new int[n][n];
		checkSite = new boolean[n][n];
		count = 0;
		int gridCount = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				id[i][j] = gridCount++;
				checkSite[i][j] = false;
			}
		}
	}

	// opens the site(row, col) if it is not open
	public void open(final int argRow, final int argCol) {
		int row = argRow - 1;
		int col = argCol - 1;
		
		if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
			throw new IllegalArgumentException("error out of bounds in open " +  row + " " + col + " " + n);
		}
		checkSite[row][col] = true;
		count++;
		// System.out.println(row);
		// System.out.println(col);
		int lastRow = n - 1;
		if (row == 0) {
			uf.union(id[row][col], top);
		}
		if (row == lastRow) {
			uf.union(id[row][col], bottom);
		}
		if (row != 0 && checkSite[row - 1][col]) {
			uf.union(id[row][col], id[row - 1][col]);
		}
		if (row < lastRow && checkSite[row + 1][col]) {
			uf.union(id[row][col], id[row + 1][col]);
		}
		if (col != 0 && checkSite[row][col - 1]) {
			uf.union(id[row][col], id[row][col - 1]);
		}
		if (col < lastRow && checkSite[row][col + 1]) {
			uf.union(id[row][col], id[row][col + 1]);
		}

	}

	// returns if a site is open or not
	public boolean isOpen(final int argRow, final int argCol) {
		int row = argRow - 1;
		int col = argCol - 1;
		if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
			throw new IllegalArgumentException("error out of bounds in isOpen " + row + " " + col + " " + n);
		}
		return checkSite[row][col];
	}

	// is site full?
	public boolean isFull(final int argRow, final int argCol) {
		int row = argRow - 1;
		int col = argCol - 1;
		if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
			throw new IllegalArgumentException("error out of bounds in isFull " + row + " " + col + " " + n);
		}
		return uf.find(id[row][col]) == uf.find(top);
	}

	public int numberOfOpenSites() {
		return count;
	}

	public boolean percolates() {
		return uf.find(bottom) == uf.find(top);
	}

	public static void main(String[] args) {

	}
}
