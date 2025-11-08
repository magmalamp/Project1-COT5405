import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Integer max_k = 10;
        List<String> seqsA = List.of(
                "ATGCTACGTA",
                "GATTACAGGCTTA",
                "TGCATGACTGACTGA",
                "ATCGTAGCTAGCTAGCT",
                "GGATCCGTAGCTAGCTTACG",
                "ATGCGTTAATGCGTTAATGCGT",
                "GATTACAGGCTTACGAATCGTA",
                "CGTACGTTAGCTAGCTAACGTA",
                "TGCATGACTGACTGACAGACTGA",
                "ATGCGTACGTTAGCGTACGTTAGC",
                "CGTAGCTAGCTAACGTTACGATGCT",
                "GCTAGCGTACGTAGCTAGCTAACGA",
                "TACGTTAGCTAGCTAGCGTTAATCGT",
                "GGCTTACGATCGTAGCTAGCAGTTAG",
                "ATCGTACGTTAGCTAGCTAACGTTAGC",
                "GATCGTAGCTAGCTAGCTAACGTTACGA",
                "TGCATGACTGACTGACTGACGTAGCTAG",
                "CGTACGTAGCTAGCTAGCTAACTGACTGA",
                "GCTTAGCGTACGTTAGCTAGCGTAGCTAGC",
                "TACGTTAGCTAGCGTACGTTAGCTAGCTAAC",
                "ATCGTAGCTAACGTTAGCTAGCTAACGTTAGC",
                "GGATCCGTAGCTTACGTTACGATCGTAGCTAG",
                "TGCATGACTGACTGACTGACTGACTGACTGAC",
                "ATGCGTTAATGCGTTAATGCGTTAATGCGTTA",
                "GATTACAGGCTTACGAATCGTACGTTAGCTAGC",
                "CGTACGTTAGCTAGCTAACGTAGCTAGCTAACG",
                "TGCATGACTGACTGACAGACTGACTGACTGACTG",
                "ATGCGTACGTTAGCGTACGTTAGCGTACGTTAGC",
                "CGTAGCTAGCTAACGTTACGATCGTAGCTAGCTAA",
                "GCTAGCGTACGTAGCTAGCTAACGTTAGCTAGCTA",
                "TACGTTAGCTAGCTAGCGTTAATCGTAGCTAACGTT",
                "GGCTTACGATCGTAGCTAGCAGTTAGCTAACGTTAG",
                "ATCGTACGTTAGCTAGCTAACGTTAGCGTAGCTAACG",
                "GATCGTAGCTAGCTAGCTAACGTTACGTTAGCTAGCTA",
                "TGCATGACTGACTGACTGACGTAGCTAGCTAACGTTAGC",
                "CGTACGTAGCTAGCTAGCTAACTGACTGACTGACTGACTG",
                "GCTTAGCGTACGTTAGCTAGCGTAGCTAGCTAACGTTAGCT",
                "TACGTTAGCTAGCGTACGTTAGCTAGCTAACGTTAGCTAGCT",
                "ATCGTAGCTAACGTTAGCTAGCTAACGTTAGCGTACGTTAGCT",
                "GGATCCGTAGCTTACGTTACGATCGTAGCTAGCTAACGTTAGCT",
                "TGCATGACTGACTGACTGACTGACTGACTGACTGACTGACTGACT",
                "ATGCGTTAATGCGTTAATGCGTTAATGCGTTAATGCGTTAATGCGT",
                "GATTACAGGCTTACGAATCGTACGTTAGCTAGCTAACGTTAGCTAGC",
                "CGTACGTTAGCTAGCTAACGTAGCTAGCTAACGTTAGCTAGCTAACG",
                "TGCATGACTGACTGACAGACTGACTGACTGACTGACTGACTGACTGA",
                "ATGCGTACGTTAGCGTACGTTAGCGTACGTTAGCGTACGTTAGCGTAC",
                "CGTAGCTAGCTAACGTTACGATCGTAGCTAGCTAACGTTAGCTAGCTAA",
                "GCTAGCGTACGTAGCTAGCTAACGTTAGCTAGCTAACGTTAGCTAGCTA",
                "TACGTTAGCTAGCTAGCGTTAATCGTAGCTAACGTTAGCTAGCTAACGT",
                "GGCTTACGATCGTAGCTAGCAGTTAGCTAACGTTAGCTAGCTAACGTTAG"
        );

        List<String> seqsB = List.of(
                "ATGCTACGTT",
                "GATTACAGGCTTG",
                "TGCATGACTGACTGT",
                "ATCGTAGCTAGCTAGCA",
                "GGATCCGTAGCTAGCTTACC",
                "ATGCGTTAATGCGTTAATGCGTG",
                "GATTACAGGCTTACGAATCGTTG",
                "CGTACGTTAGCTAGCTAACGTG",
                "TGCATGACTGACTGACAGACTGC",
                "ATGCGTACGTTAGCGTACGTTAGT",
                "CGTAGCTAGCTAACGTTACGATGCC",
                "GCTAGCGTACGTAGCTAGCTAACGT",
                "TACGTTAGCTAGCTAGCGTTAATCGC",
                "GGCTTACGATCGTAGCTAGCAGTTAC",
                "ATCGTACGTTAGCTAGCTAACGTTAGT",
                "GATCGTAGCTAGCTAGCTAACGTTACGT",
                "TGCATGACTGACTGACTGACGTAGCTAA",
                "CGTACGTAGCTAGCTAGCTAACTGACTGC",
                "GCTTAGCGTACGTTAGCTAGCGTAGCTAGT",
                "TACGTTAGCTAGCGTACGTTAGCTAGCTAAT",
                "ATCGTAGCTAACGTTAGCTAGCTAACGTTAGT",
                "GGATCCGTAGCTTACGTTACGATCGTAGCTAA",
                "TGCATGACTGACTGACTGACTGACTGACTGAT",
                "ATGCGTTAATGCGTTAATGCGTTAATGCGTTG",
                "GATTACAGGCTTACGAATCGTACGTTAGCTAGT",
                "CGTACGTTAGCTAGCTAACGTAGCTAGCTAACGC",
                "TGCATGACTGACTGACAGACTGACTGACTGACTGA",
                "ATGCGTACGTTAGCGTACGTTAGCGTACGTTAGCT",
                "CGTAGCTAGCTAACGTTACGATCGTAGCTAGCTAT",
                "GCTAGCGTACGTAGCTAGCTAACGTTAGCTAGCTC",
                "TACGTTAGCTAGCTAGCGTTAATCGTAGCTAACGTA",
                "GGCTTACGATCGTAGCTAGCAGTTAGCTAACGTTAT",
                "ATCGTACGTTAGCTAGCTAACGTTAGCGTAGCTAACG",
                "GATCGTAGCTAGCTAGCTAACGTTACGTTAGCTAGCTT",
                "TGCATGACTGACTGACTGACGTAGCTAGCTAACGTTAGT",
                "CGTACGTAGCTAGCTAGCTAACTGACTGACTGACTGACTA",
                "GCTTAGCGTACGTTAGCTAGCGTAGCTAGCTAACGTTAGCT",
                "TACGTTAGCTAGCGTACGTTAGCTAGCTAACGTTAGCTAGCA",
                "ATCGTAGCTAACGTTAGCTAGCTAACGTTAGCGTACGTTAGCC",
                "GGATCCGTAGCTTACGTTACGATCGTAGCTAGCTAACGTTAGCA",
                "TGCATGACTGACTGACTGACTGACTGACTGACTGACTGACTGATA",
                "ATGCGTTAATGCGTTAATGCGTTAATGCGTTAATGCGTTAATGCGTC",
                "GATTACAGGCTTACGAATCGTACGTTAGCTAGCTAACGTTAGCTAGT",
                "CGTACGTTAGCTAGCTAACGTAGCTAGCTAACGTTAGCTAGCTAACGC",
                "TGCATGACTGACTGACAGACTGACTGACTGACTGACTGACTGACTGAG",
                "ATGCGTACGTTAGCGTACGTTAGCGTACGTTAGCGTACGTTAGCGTAT",
                "CGTAGCTAGCTAACGTTACGATCGTAGCTAGCTAACGTTAGCTAGCTAT",
                "GCTAGCGTACGTAGCTAGCTAACGTTAGCTAGCTAACGTTAGCTAGCTT",
                "TACGTTAGCTAGCTAGCGTTAATCGTAGCTAACGTTAGCTAGCTAACGC",
                "GGCTTACGATCGTAGCTAGCAGTTAGCTAACGTTAGCTAGCTAACGTTAT"
        );

        try (FileWriter csvWriter = new FileWriter("greedy-results.csv")) {
            // Write CSV header
            csvWriter.append("k,SequenceIndex,SeqA_Length,SeqB_Length,StartA,EndA,StartB,EndB,MotifA,MotifB,Score,Runtime_ms,MemUsage_KB\n");

            for (int k = 1; k <= max_k; k++) {
                for (int i = 0; i < seqsA.size(); i++) {
                    long startTime = System.nanoTime();
                    Runtime runtime = Runtime.getRuntime();
                    runtime.gc();
                    long memBefore = runtime.totalMemory() - runtime.freeMemory();

                    String seqA = seqsA.get(i);
                    String seqB = seqsB.get(i);

                    List<GreedyMotifMatching.MotifPair> result = GreedyMotifMatching.greedyMotifMatch(seqA, seqB, k);

                    long endTime = System.nanoTime();
                    long memAfter = runtime.totalMemory() - runtime.freeMemory();

                    double time = ((endTime - startTime) / 1_000_000.0);
                    double totalMemUsage = (memAfter - memBefore) / 1024.0;

                    for (GreedyMotifMatching.MotifPair p : result) {
                        csvWriter.append(String.format(
                                "%d,%d,%d,%d,%d,%d,%d,%d,%s,%s,%.2f,%.3f,%.3f\n",
                                k,
                                (i + 1),
                                seqA.length(),
                                seqB.length(),
                                p.startA,
                                p.startA + k - 1,
                                p.startB,
                                p.startB + k - 1,
                                p.motifA,
                                p.motifB,
                                p.score,
                                time,
                                totalMemUsage
                        ));
                    }
                }
            }

            csvWriter.flush();
            System.out.println("Greedy results written to greedy-results.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter csvWriter = new FileWriter("dynamic-results.csv")) {
            csvWriter.append("SequenceIndex,SeqA_Length,SeqB_Length,StartA,EndA,StartB,EndB,LocalA,LocalB,Score,Runtime_ms,MemUsage_KB\n");

            for (int i = 0; i < seqsA.size(); i++) {
                long startTime = System.nanoTime();
                Runtime runtime = Runtime.getRuntime();
                runtime.gc();
                long memBefore = runtime.totalMemory() - runtime.freeMemory();

                String seqA = seqsA.get(i);
                String seqB = seqsB.get(i);

                DynamicLocalAlignment.Alignment result = DynamicLocalAlignment.align(seqA, seqB);

                long endTime = System.nanoTime();
                long memAfter = runtime.totalMemory() - runtime.freeMemory();

                double time = ((endTime - startTime) / 1_000_000.0);
                double totalMemUsage = (memAfter - memBefore) / 1024.0;

                csvWriter.append(String.format(
                        "%d,%d,%d,%d,%d,%d,%d,%s,%s,%.2f,%.3f,%.3f\n",
                        (i + 1),
                        seqA.length(),
                        seqB.length(),
                        result.startA,
                        result.endA,
                        result.startB,
                        result.endB,
                        result.alignedA,
                        result.alignedB,
                        result.score,
                        time,
                        totalMemUsage
                ));
            }
            csvWriter.flush();
            System.out.println("Dynamic results written to dynamic-results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
