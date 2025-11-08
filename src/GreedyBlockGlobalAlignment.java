import java.util.*;

public class GreedyBlockGlobalAlignment {

    static class BlockPair {
        int startA, startB;
        String blockA, blockB;
        int score;

        BlockPair(int startA, int startB, String blockA, String blockB, int score) {
            this.startA = startA;
            this.startB = startB;
            this.blockA = blockA;
            this.blockB = blockB;
            this.score = score;
        }
    }

    // Compute additive similarity: number of matches between blocks
    private static int blockSimilarity(String A, String B) {
        int score = 0;
        for (int i = 0; i < A.length(); i++) {
            if (A.charAt(i) == B.charAt(i)) score++;
        }
        return score;
    }

    public static List<BlockPair> greedyBlockGlobalAlign(String A, String B, int k) {
        int n = A.length(), m = B.length();
        List<BlockPair> allPairs = new ArrayList<>();

        // Enumerate all block pairs
        for (int i = 0; i <= n - k; i++) {
            String blockA = A.substring(i, i + k);
            for (int j = 0; j <= m - k; j++) {
                String blockB = B.substring(j, j + k);
                int score = blockSimilarity(blockA, blockB);
                allPairs.add(new BlockPair(i, j, blockA, blockB, score));
            }
        }

        // Sort by descending score
        allPairs.sort((a, b) -> Integer.compare(b.score, a.score));

        // Greedily select non-overlapping blocks
        boolean[] usedA = new boolean[n];
        boolean[] usedB = new boolean[m];
        List<BlockPair> selected = new ArrayList<>();

        for (BlockPair bp : allPairs) {
            if (!overlaps(bp, usedA, usedB, k)) {
                markUsed(bp, usedA, usedB, k);
                selected.add(bp);
            }
        }

        return selected;
    }

    private static boolean overlaps(BlockPair bp, boolean[] usedA, boolean[] usedB, int k) {
        for (int i = bp.startA; i < bp.startA + k; i++)
            if (usedA[i]) return true;
        for (int j = bp.startB; j < bp.startB + k; j++)
            if (usedB[j]) return true;
        return false;
    }

    private static void markUsed(BlockPair bp, boolean[] usedA, boolean[] usedB, int k) {
        for (int i = bp.startA; i < bp.startA + k; i++)
            usedA[i] = true;
        for (int j = bp.startB; j < bp.startB + k; j++)
            usedB[j] = true;
    }
}
