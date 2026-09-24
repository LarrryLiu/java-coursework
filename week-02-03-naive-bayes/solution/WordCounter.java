/**
 * counting the occurrence of a specified word in spam and non-spam documents and calculating its corresponding conditional probability
 */
public class WordCounter
{
	private String focusWord;
	private int totalSpamWords;
	private int totalNoSpamWords;
	private int focusWordInSpam;
	private int focusWordInNoSpam;
	private int spamDocuments;
	private int noSpamDocuments;
	/**
	 * create a new wordcounter, and counting the specified word, and initialize all counts to 0
	 * @param focusWord the word that needs to be counted
	 */
	public WordCounter(String focusWord)
	{
		this.focusWord = focusWord;
		totalSpamWords = 0;
		totalNoSpamWords = 0;
		focusWordInSpam = 0;
		focusWordInNoSpam = 0;
		spamDocuments = 0;
		noSpamDocuments = 0;
	}
	/**
	 * return the word tha we are counting
	 * @return The target words that need to be counted
	 */
	public String getFocusWord()
	{
		return focusWord;
	}
	/**
	 * we first seperate document and decide whether the first term is one, and then Count the number of words in the document and the occurrence frequency of focus word.
	 * @param document the document that needs to be counted
	 */
	public void addSample(String document)
	{
		String[] words = document.split(" ");
		boolean isSpam = words[0].equals("1");
		int numberOfWords = words.length - 1;
		int numberOfFocusWords = 0;

		for (int i = 1; i < words.length; i++)
		{
			if (words[i].equals(focusWord))
			{
				numberOfFocusWords++;
			}
		}

		if (isSpam)
		{
			totalSpamWords = totalSpamWords + numberOfWords;
			focusWordInSpam = focusWordInSpam + numberOfFocusWords;
			spamDocuments++;
		}
		else
		{
			totalNoSpamWords = totalNoSpamWords + numberOfWords;
			focusWordInNoSpam = focusWordInNoSpam + numberOfFocusWords;
			noSpamDocuments++;
		}
	}
	/**
	 * check whether this wordcounter already have enough data to culculate the probability
	 * @return Return true if there is already sufficient training data; otherwise, return false
	 */
	public boolean isCounterTrained()
	{
		return focusWordInSpam + focusWordInNoSpam > 0
			&& spamDocuments > 0
			&& noSpamDocuments > 0;
	}
	/**
	 * culculate and return the probability of target word that is in the non spam document
	 * @throws IllegalStateException if the WordCounter is not trained
	 * @return the conditional probability of the focus word in non spam documents
	 */
	public double getConditionalNoSpam()
	{
		if (!isCounterTrained())
		{
			throw new IllegalStateException("WordCounter is not trained");
		}

		return (double) focusWordInNoSpam / totalNoSpamWords;
	}
	/**
	 * culculate and return the probability of target word that is in the spam document
	 * @throws IllegalStateException if the WordCounter is not trained
	 * @return the conditional probability of the focus word in spam documents
	 */
	public double getConditionalSpam()
	{
		if (!isCounterTrained())
		{
			throw new IllegalStateException("WordCounter is not trained");
		}

		return (double) focusWordInSpam / totalSpamWords;
	}
}
