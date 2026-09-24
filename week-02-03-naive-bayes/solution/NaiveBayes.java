import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * to build a Naive Bayes spam classifier, sort the documents and train using the specified focus words
 */
public class NaiveBayes
{
	private WordCounter[] wordCounters;
	private int spamDocuments;
	private int noSpamDocuments;
	/**
	 * build a new Naive Bayes classifier and create a wordcounter for every specified focus word
	 * 
	 * @param focusWords the focus words used for classification
	 */
	public NaiveBayes(String[] focusWords)
	{
		wordCounters = new WordCounter[focusWords.length];
		spamDocuments = 0;
		noSpamDocuments = 0;

		for (int i = 0; i < focusWords.length; i++)
		{
			wordCounters[i] = new WordCounter(focusWords[i]);
		}
	}
	/**
	 * Add a labeled training document to the classifier and update the statistics for each word as well as the number of spam/non-spam documents
	 * @param document the document used for training
	 */
	public void addSample(String document)
	{
		for (WordCounter counter : wordCounters) 
		{
			counter.addSample(document);
		}

		String[] words = document.split(" ");
		if (words[0].equals("1"))
		{
			spamDocuments++;
		}
		else
		{
			noSpamDocuments++;
		}
	}
	/**
	 * Use the already trained Naive Bayes classifier to determine whether an unclassified document is spam
	 * @param unclassifiedDocument the document that needs to be classified
	 * @return whether the document is classified as spam
	 */
	public boolean classify(String unclassifiedDocument)
	{
		int totalDocuments = spamDocuments + noSpamDocuments;
		double spamScore = (double) spamDocuments / totalDocuments;
		double noSpamScore = (double) noSpamDocuments / totalDocuments;
		String[] words = unclassifiedDocument.split(" ");

		for (String word : words) 
		{
			for (WordCounter counter : wordCounters) 
			{
				if (word.equals(counter.getFocusWord())) 
				{
					spamScore = spamScore 
							* counter.getConditionalSpam();
					noSpamScore = noSpamScore 
							* counter.getConditionalNoSpam();
				}
			}
		}

		return spamScore > noSpamScore;
	}
	/**
	 * Read the training samples line by line from the given training file and use these samples to train the classifier
	 * @param trainingFile the file containing the training data
	 * @throws IOException if there is an error reading the file
	 */
	public void trainClassifier(File trainingFile) throws IOException
	{
		try (BufferedReader reader = new BufferedReader(
				new FileReader(trainingFile)))
		{
			String document = reader.readLine();

			while (document != null)
			{
				addSample(document);
				document = reader.readLine();
			}
		}
	}
	/**
	 * Classify each line of document in the input file and write the classification results to the output file
	 * @param input	the input file
	 * @param output the output file
	 * @throws IOException if there is an error reading or writing the file
	 */
	public void classifyFile(File input, File output) throws IOException
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(input));
			PrintWriter writer = new PrintWriter(output))
		{
			String document = reader.readLine();

			while (document != null)
			{
				if (classify(document))
				{
					writer.println("1");
				}
				else
				{
					writer.println("0");
				}

				document = reader.readLine();
			}
		}
	}
	/**
	 * Create a confusion matrix using the real labels in the test file and the classifier prediction results
	 * @param testdata the file containing the test data
	 * @return the confusion matrix that contains Statistics of classification results
	 * @throws IOException if there is an error reading the file
	 */
	public ConfusionMatrix computeAccuracy(File testdata) throws IOException
	{
		ConfusionMatrix matrix = new ConfusionMatrix();

		try (BufferedReader reader = new BufferedReader(new FileReader(testdata)))
		{
			String sample = reader.readLine();

			while (sample != null)
			{
				boolean actuallySpam = sample.substring(0, 1).equals("1");
				String document = sample.substring(2);
				boolean predictedSpam = classify(document);

				matrix.addResult(actuallySpam, predictedSpam);
				sample = reader.readLine();
			}
		}

		return matrix;
	}
}
