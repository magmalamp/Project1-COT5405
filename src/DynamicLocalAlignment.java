public class DynamicLocalAlignment {

    static final double MATCH = 1.0;
    static final double MISMATCH = -1.0;
    static final double GAP = -0.5;

    public static class Alignment {
        public String alignedA;
        public String alignedB;
        public int startA, endA;
        public int startB, endB;
        public double score;

        public Alignment(String a, String b, int sA, int eA, int sB, int eB, double sc) {
            alignedA = a;
            alignedB = b;
            startA = sA;
            endA = eA;
            startB = sB;
            endB = eB;
            score = sc;
        }
    }

    public static Alignment align(String A, String B) {
        int n = A.length();
        int m = B.length();

        double[][] dp = new double[n + 1][m + 1];

        double maxScore = 0;
        int iMax = 0, jMax = 0;

        // Build SW DP matrix
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double matchScore = (A.charAt(i - 1) == B.charAt(j - 1)) ? MATCH : MISMATCH;

                dp[i][j] = Math.max(0,
                        Math.max(
                                dp[i - 1][j - 1] + matchScore,
                                Math.max(dp[i - 1][j] + GAP, dp[i][j - 1] + GAP)
                        ));

                if (dp[i][j] > maxScore) {
                    maxScore = dp[i][j];
                    iMax = i;
                    jMax = j;
                }
            }
        }

        // Traceback start from max score
        int i = iMax;
        int j = jMax;
        int endA = iMax - 1;
        int endB = jMax - 1;

        StringBuilder aAlign = new StringBuilder();
        StringBuilder bAlign = new StringBuilder();

        while (i > 0 && j > 0 && dp[i][j] > 0) {
            double score = dp[i][j];
            double diag = dp[i - 1][j - 1];
            double up = dp[i - 1][j];
            double left = dp[i][j - 1];

            if (score == diag + ((A.charAt(i - 1) == B.charAt(j - 1)) ? MATCH : MISMATCH)) {
                aAlign.append(A.charAt(i - 1));
                bAlign.append(B.charAt(j - 1));
                i--;
                j--;
            } else if (score == up + GAP) {
                aAlign.append(A.charAt(i - 1));
                bAlign.append('-');
                i--;
            } else {
                aAlign.append('-');
                bAlign.append(B.charAt(j - 1));
                j--;
            }
        }

        return new Alignment(
                aAlign.reverse().toString(),
                bAlign.reverse().toString(),
                i, endA,
                j, endB,
                maxScore
        );
    }
}
