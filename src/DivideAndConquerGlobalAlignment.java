public class DivideAndConquerGlobalAlignment {

    static final double MATCH = 1.0;
    static final double MISMATCH = -1.0;
    static final double GAP = -0.5;

    public static class Alignment {
        public final String alignedA;
        public final String alignedB;
        public final double score;

        public Alignment(String a, String b, double sc) {
            this.alignedA = a;
            this.alignedB = b;
            this.score = sc;
        }
    }

    // Public entry point
    public static Alignment align(String A, String B) {
        return hirschberg(A, B);
    }

    /**
     * Hirschberg's divide-and-conquer global alignment.
     * Uses recursion and linear-space forward/backward scoring.
     */
    private static Alignment hirschberg(String A, String B) {
        int n = A.length();
        int m = B.length();

        // Base cases (if sequences are short, do simple Needleman–Wunsch)
        if (n == 0) {
            return new Alignment("-".repeat(m), B, GAP * m);
        } else if (m == 0) {
            return new Alignment(A, "-".repeat(n), GAP * n);
        } else if (n == 1 || m == 1) {
            return needlemanWunschSmall(A, B);
        }

        // Divide step: split A at midpoint
        int mid = n / 2;

        // Compute forward and backward alignment scores
        double[] scoreL = nwScore(A.substring(0, mid), B);
        double[] scoreR = nwScoreReverse(A.substring(mid), B);

        // Find split point in B maximizing total score
        int splitB = 0;
        double maxScore = Double.NEGATIVE_INFINITY;
        for (int j = 0; j <= m; j++) {
            double score = scoreL[j] + scoreR[m - j];
            if (score > maxScore) {
                maxScore = score;
                splitB = j;
            }
        }

        // Conquer step: recursively align subproblems
        Alignment left = hirschberg(A.substring(0, mid), B.substring(0, splitB));
        Alignment right = hirschberg(A.substring(mid), B.substring(splitB));

        return new Alignment(left.alignedA + right.alignedA,
                left.alignedB + right.alignedB,
                left.score + right.score);
    }

    /**
     * Computes last row of Needleman–Wunsch DP matrix (linear space).
     * Used by Hirschberg’s forward and backward passes.
     */
    private static double[] nwScore(String A, String B) {
        int n = A.length();
        int m = B.length();

        double[] prev = new double[m + 1];
        double[] curr = new double[m + 1];

        // Initialize first row
        for (int j = 0; j <= m; j++) prev[j] = GAP * j;

        // Fill in DP rows
        for (int i = 1; i <= n; i++) {
            curr[0] = GAP * i;
            for (int j = 1; j <= m; j++) {
                double match = (A.charAt(i - 1) == B.charAt(j - 1)) ? MATCH : MISMATCH;
                curr[j] = Math.max(
                        curr[j - 1] + GAP,
                        Math.max(prev[j] + GAP, prev[j - 1] + match)
                );
            }
            double[] tmp = prev;
            prev = curr;
            curr = tmp;
        }
        return prev;
    }

    /**
     * Reverse version of nwScore for backward pass.
     */
    private static double[] nwScoreReverse(String A, String B) {
        String revA = new StringBuilder(A).reverse().toString();
        String revB = new StringBuilder(B).reverse().toString();
        return nwScore(revA, revB);
    }

    /**
     * Base case for small sequences: full DP Needleman–Wunsch (small memory ok).
     */
    private static Alignment needlemanWunschSmall(String A, String B) {
        int n = A.length();
        int m = B.length();
        double[][] dp = new double[n + 1][m + 1];

        for (int i = 0; i <= n; i++) dp[i][0] = i * GAP;
        for (int j = 0; j <= m; j++) dp[0][j] = j * GAP;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double match = (A.charAt(i - 1) == B.charAt(j - 1)) ? MATCH : MISMATCH;
                dp[i][j] = Math.max(
                        dp[i - 1][j - 1] + match,
                        Math.max(dp[i - 1][j] + GAP, dp[i][j - 1] + GAP)
                );
            }
        }

        // Traceback
        StringBuilder aAlign = new StringBuilder();
        StringBuilder bAlign = new StringBuilder();
        int i = n, j = m;
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 &&
                    dp[i][j] == dp[i - 1][j - 1] + ((A.charAt(i - 1) == B.charAt(j - 1)) ? MATCH : MISMATCH)) {
                aAlign.append(A.charAt(i - 1));
                bAlign.append(B.charAt(j - 1));
                i--; j--;
            } else if (i > 0 && dp[i][j] == dp[i - 1][j] + GAP) {
                aAlign.append(A.charAt(i - 1));
                bAlign.append('-');
                i--;
            } else {
                aAlign.append('-');
                bAlign.append(B.charAt(j - 1));
                j--;
            }
        }

        return new Alignment(aAlign.reverse().toString(),
                bAlign.reverse().toString(),
                dp[n][m]);
    }
}