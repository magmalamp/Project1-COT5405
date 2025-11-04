import java.util.*;

public class GreedyMotifMatching {

    static class MotifPair {
        int startA, startB;
        String motifA, motifB;
        double score;
        String alignedA, alignedB;

        MotifPair(int startA, int startB, String motifA, String motifB,
                  double score, String alignedA, String alignedB) {
            this.startA = startA;
            this.startB = startB;
            this.motifA = motifA;
            this.motifB = motifB;
            this.score = score;
            this.alignedA = alignedA;
            this.alignedB = alignedB;
        }
    }

    public static List<MotifPair> greedyMotifMatch(String A, String B, int k) {
        int n = A.length(), m = B.length();
        List<MotifPair> matches = new ArrayList<>();

        for (int i = 0; i <= n - k; i++) {
            String subA = A.substring(i, i + k);
            for (int j = 0; j <= m - k; j++) {
                String subB = B.substring(j, j + k);
                SmithWatermanResult sw = smithWaterman(subA, subB);
                matches.add(new MotifPair(i, j, subA, subB, sw.score, sw.alignedA, sw.alignedB));
            }
        }

        matches.sort((a, b) -> Double.compare(b.score, a.score));

        List<MotifPair> selected = new ArrayList<>();
        boolean[] usedA = new boolean[n];
        boolean[] usedB = new boolean[m];

        for (MotifPair match : matches) {
            if (!overlaps(match, usedA, usedB, k)) {
                markUsed(match, usedA, usedB, k);
                selected.add(match);
            }
        }

        return selected;
    }

    // Container for SW result (score + aligned strings)
    static class SmithWatermanResult {
        double score;
        String alignedA;
        String alignedB;
        SmithWatermanResult(double score, String a, String b) {
            this.score = score;
            this.alignedA = a;
            this.alignedB = b;
        }
    }

    // Smith–Waterman with traceback
    private static SmithWatermanResult smithWaterman(String A, String B) {
        int n = A.length();
        int m = B.length();
        double match = 1.0, mismatch = -1.0, gap = -0.5;  // double scoring
        double[][] H = new double[n + 1][m + 1];          // DP matrix

        int maxI = 0, maxJ = 0;
        double maxScore = 0.0;

        // Fill DP matrix
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double scoreDiag = H[i - 1][j - 1] + (A.charAt(i - 1) == B.charAt(j - 1) ? match : mismatch);
                double scoreDel  = H[i - 1][j] + gap;
                double scoreIns  = H[i][j - 1] + gap;

                H[i][j] = Math.max(0.0, Math.max(scoreDiag, Math.max(scoreDel, scoreIns)));

                if (H[i][j] > maxScore) {
                    maxScore = H[i][j];
                    maxI = i;
                    maxJ = j;
                }
            }
        }

        // Traceback from max cell
        StringBuilder alignA = new StringBuilder();
        StringBuilder alignB = new StringBuilder();
        int i = maxI;
        int j = maxJ;

        while (i > 0 && j > 0 && H[i][j] > 0) {
            double score = H[i][j];
            double diag  = H[i - 1][j - 1];
            double up    = H[i - 1][j];
            double left  = H[i][j - 1];

            if (score == diag + (A.charAt(i - 1) == B.charAt(j - 1) ? match : mismatch)) {
                alignA.append(A.charAt(i - 1));
                alignB.append(B.charAt(j - 1));
                i--; j--;
            } else if (score == up + gap) {
                alignA.append(A.charAt(i - 1));
                alignB.append('-');
                i--;
            } else { // score == left + gap
                alignA.append('-');
                alignB.append(B.charAt(j - 1));
                j--;
            }
        }

        alignA.reverse();
        alignB.reverse();

        return new SmithWatermanResult(maxScore, alignA.toString(), alignB.toString());
    }

    private static boolean overlaps(MotifPair p, boolean[] usedA, boolean[] usedB, int k) {
        for (int i = p.startA; i < p.startA + k; i++)
            if (usedA[i]) return true;
        for (int j = p.startB; j < p.startB + k; j++)
            if (usedB[j]) return true;
        return false;
    }

    private static void markUsed(MotifPair p, boolean[] usedA, boolean[] usedB, int k) {
        for (int i = p.startA; i < p.startA + k; i++)
            usedA[i] = true;
        for (int j = p.startB; j < p.startB + k; j++)
            usedB[j] = true;
    }

    public static void main(String[] args) {
        String A = "ATCGT";
        String B = "ATGGT";
        int k = 5;

        List<MotifPair> result = greedyMotifMatch(A, B, k);

        for (MotifPair p : result) {
            System.out.printf(
                    "A[%d..%d]: %s | B[%d..%d]: %s | score=%.1f%n",
                    p.startA, p.startA + k - 1, p.motifA,
                    p.startB, p.startB + k - 1, p.motifB,
                    p.score
            );
            System.out.println("Alignment:");
            System.out.println("A: " + p.alignedA);
            System.out.println("B: " + p.alignedB);
            System.out.println();
        }
    }
}
