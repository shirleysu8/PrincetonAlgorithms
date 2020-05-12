import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private static final double CONFIDENCE_FACTOR = 1.96;
	private final int trials;
	private final double[] fractions;
	
	public PercolationStats(final int n, final int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("Out of Bounds");
		}
		this.trials = trials;
		fractions = new double[trials];
		for (int i = 0; i < trials; i++) {
			final Percolation percolation = new Percolation(n);
			while (!percolation.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				if (!percolation.isOpen(row, col)) {
					percolation.open(row, col);
				}

			}
			fractions[i] = (double) percolation.numberOfOpenSites() / (n * n);
		}
	}

	public double mean() {
		return StdStats.mean(fractions);
	}

	public double stddev() {
		return StdStats.stddev(fractions);
	}

	public double confidenceLo() {
		return mean() - (CONFIDENCE_FACTOR / Math.sqrt(trials));
	}

	public double confidenceHi() {
		return mean() + (CONFIDENCE_FACTOR / Math.sqrt(trials));
	}

	public static void main(String[] args) {
		PercolationStats test1 = new PercolationStats(50, 100);
		System.out.println(test1.mean());
		System.out.println(test1.stddev());
		System.out.println("[" + test1.confidenceLo() + ", " + test1.confidenceHi() + "]");
//		PercolationStats test2 = new PercolationStats(100, 50);
//		System.out.println(test2.mean());
//		System.out.println(test2.stddev());
//		System.out.println("[" + test2.confidenceLo() + ", " + test2.confidenceHi() + "]");
	}

}
