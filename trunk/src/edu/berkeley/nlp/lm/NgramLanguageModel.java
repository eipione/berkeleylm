package edu.berkeley.nlp.lm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Base interface for an n-gram language model, which exposes only inefficient
 * convenience methods. See {@link ContextEncodedNgramLanguageModel} and
 * {@link ArrayEncodedNgramLanguageModel} for more efficient accessors.
 * 
 * @author adampauls
 * 
 * @param <W>
 * 
 */
public interface NgramLanguageModel<W>
{

	/**
	 * Maximum size of n-grams stored by the model.
	 * 
	 * @return
	 */
	public int getLmOrder();

	/**
	 * Each LM must have a WordIndexer which assigns integer IDs to each word W
	 * in the language.
	 * 
	 * @return
	 */
	public WordIndexer<W> getWordIndexer();

	/**
	 * Scores a complete sentence, taking appropriate care with the start- and
	 * end-of-sentence symbols. This is a convenience method and will generally
	 * be inefficient.
	 * 
	 * @return
	 */
	public float scoreSentence(List<W> sentence);

	/**
	 * 
	 * Scores an n-gram. This is a convenience method and will generally be
	 * relatively inefficient. More efficient versions are available in
	 * {@link ArrayEncodedNgramLanguageModel#getLogProb(int[], int, int)} and
	 * {@link ContextEncodedNgramLanguageModel#getLogProb(long, int, int, edu.berkeley.nlp.lm.ContextEncodedNgramLanguageModel.LmContextInfo)}
	 * .
	 */
	public float getLogProb(List<W> ngram);

	public static class StaticMethods
	{

		public static <T> int[] toIntArray(final List<T> ngram, final ArrayEncodedNgramLanguageModel<T> lm) {
			final int[] ints = new int[ngram.size()];
			final WordIndexer<T> wordIndexer = lm.getWordIndexer();
			for (int i = 0; i < ngram.size(); ++i) {
				ints[i] = wordIndexer.getIndexPossiblyUnk(ngram.get(i));
			}
			return ints;
		}

		public static <T> List<T> toObjectList(final int[] ngram, final ArrayEncodedNgramLanguageModel<T> lm) {
			final List<T> ret = new ArrayList<T>(ngram.length);
			final WordIndexer<T> wordIndexer = lm.getWordIndexer();
			for (int i = 0; i < ngram.length; ++i) {
				ret.add(wordIndexer.getWord(ngram[i]));
			}
			return ret;
		}

	}

}