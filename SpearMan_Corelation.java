
// This part is in the Main.java of Xin's repository
String lucenedefault = "result-lucene.run";
HashMap<String, HashMap<String, String>> lucene_data = read_dataFile(lucenedefault);
String tfIdf_anc_apc = "tfidf_anc_apc.run";
HashMap<String, HashMap<String, String>> tfIdf_anc_apcData = read_dataFile(tfIdf_anc_apc);
String tfidf_lnc_ltn1 = "./tfidf_lnc_ltn.run";
HashMap<String, HashMap<String, String>> lnc_ltnData = read_dataFile(tfidf_lnc_ltn1);
String tfidf_bnn_bnn1 = "./tfidf_bnn_bnn.run";
HashMap<String, HashMap<String, String>> bnn_bnnData = read_dataFile(tfidf_bnn_bnn1);
System.out.println("Correlation between lucene-default and anc_apc");
calculateCorrelation(lucene_data, tfIdf_anc_apcData);
System.out.println("Correlation between lucene-default and lnc_ltn");
calculateCorrelation(lucene_data, lnc_ltnData);
System.out.println("Correlation between lucene-default and bnn_bnn");
calculateCorrelation(lucene_data, bnn_bnnData);


public static void calculateCorrelation(
        HashMap<String, HashMap<String, String>> lucene_data,
        HashMap<String, HashMap<String, String>> TFIDF_data) {
        float SpearMan_rank_correlation = (float) 0.0;
        float d = 0, d_square = 0, rank_correlation = (float) 0.0;

        for (String q : lucene_data.keySet()) {
            HashMap<String, String> luceneRanks, customRanks;
            if (TFIDF_data.keySet().contains(q)) {
                luceneRanks = lucene_data.get(q);
                customRanks = TFIDF_data.get(q);
                int missingFile = 0;
                int n = luceneRanks.size();
                if (n == 1) {
                    n = 2;
                }
                for (String key : luceneRanks.keySet()) {
                    int num1 = Integer.parseInt(luceneRanks.get(key));
                    if (customRanks.containsKey(key)) {
                        int num2 = Integer.parseInt(customRanks.get(key));

                        d = Math.abs(num1 - num2);
                        d_square += (d * d);
                    } else {
                        missingFile++;

                        d = Math.abs(num1 - (n + missingFile));
                        d_square += (d * d);
                    }
                }

                rank_correlation = 1 - (6 * d_square / (n * n * n - n));

                SpearMan_rank_correlation += rank_correlation;
            }
        }
        System.out.println("\nSpearman Coefficient  between lucene-Default and TF_IDF data: "+ Math.abs(SpearMan_rank_correlation / lucene_data.size()) + "\n");
    }