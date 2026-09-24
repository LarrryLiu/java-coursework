/**
 * use to remember the result of classification
 *
 */
public class ConfusionMatrix
{
	private int trueNegatives;
	private int truePositives;
	private int falseNegatives;
	private int falsePositives;
	/**
	 * build a new confusion matrix and make all the initial numbers to be zero 
	 */
	public ConfusionMatrix()
	{
		trueNegatives = 0;
		truePositives = 0;
		falseNegatives = 0;
		falsePositives = 0;
	}
	/**
	 * get the number of true negatives
	 * @return get the number of true negatives
	 * 
	 */
	public int getTrueNegatives()
	{
		return trueNegatives;
	}
	/**
	 * 
	 * get the number of true positives 
	 * @return the number of true positives
	 */
	public int getTruePositives()
	{
		return truePositives;
	}
	/**
	 * get the number of false negatives
	 * @return the number of false negatives 
	 */
	public int getFalseNegatives()
	{
		return falseNegatives;
	}
	/**
	 * get the number of false positives
	 * @return the number of false positives
	 */
	public int getFalsePositives()
	{
		return falsePositives;
	}
	/**
	 * add one to TP/TN/FP/FN by useing the practical result and predict result
	 * @param actuallySpam whether the document is practical spam
	 * @param predictedSpam whether the document is predicted as spam
	 */
	public void addResult(boolean actuallySpam, boolean predictedSpam)
	{
		if (actuallySpam && predictedSpam)
		{
			truePositives++;
		}
		else if (actuallySpam && !predictedSpam)
		{
			falseNegatives++;
		}
		else if (!actuallySpam && predictedSpam)
		{
			falsePositives++;
		}
		else
		{
			trueNegatives++;
		}
	}
}
