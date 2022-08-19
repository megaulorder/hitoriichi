package com.megaulorder.hitoriichi

import java.util.regex.Pattern

object EmojiRegexUtil {
	private val MiscellaneousSymbolsAndPictographs: String = "[\\uD83C\\uDF00-\\uD83D\\uDDFF]"
	private val SupplementalSymbolsAndPictographs: String = "[\\uD83E\\uDD00-\\uD83E\\uDDFF]"
	private val Emoticons: String = "[\\uD83D\\uDE00-\\uD83D\\uDE4F]"
	private val TransportAndMapSymbols: String = "[\\uD83D\\uDE80-\\uD83D\\uDEFF]"
	private val MiscellaneousSymbols: String = "[\\u2600-\\u26FF]\\uFE0F?"
	private val Dingbats: String = "[\\u2700-\\u27BF]\\uFE0F?"
	private val EnclosedAlphanumerics: String = "\\u24C2\\uFE0F?"
	private val RegionalIndicatorSymbol: String = "[\\uD83C\\uDDE6-\\uD83C\\uDDFF]{1,2}"
	private val EnclosedAlphanumericSupplement: String =
		"[\\uD83C\\uDD70\\uD83C\\uDD71\\uD83C\\uDD7E\\uD83C\\uDD7F\\uD83C\\uDD8E\\uD83C\\uDD91-\\uD83C\\uDD9A]\\uFE0F?"
	private val BasicLatin: String = "[\\u0023\\u002A\\u0030-\\u0039]\\uFE0F?\\u20E3"
	private val Arrows: String = "[\\u2194-\\u2199\\u21A9-\\u21AA]\\uFE0F?"
	private val MiscellaneousSymbolsAndArrows: String =
		"[\\u2B05-\\u2B07\\u2B1B\\u2B1C\\u2B50\\u2B55]\\uFE0F?"
	private val SupplementalArrows: String = "[\\u2934\\u2935]\\uFE0F?"
	private val CJKSymbolsAndPunctuation: String = "[\\u3030\\u303D]\\uFE0F?"
	private val EnclosedCJKLettersAndMonths: String = "[\\u3297\\u3299]\\uFE0F?"
	private val EnclosedIdeographicSupplement: String =
		"[\\uD83C\\uDE01\\uD83C\\uDE02\\uD83C\\uDE1A\\uD83C\\uDE2F\\uD83C\\uDE32-\\uD83C\\uDE3A\\uD83C\\uDE50\\uD83C\\uDE51]\\uFE0F?"
	private val GeneralPunctuation: String = "[\\u203C\\u2049]\\uFE0F?"
	private val GeometricShapes: String = "[\\u25AA\\u25AB\\u25B6\\u25C0\\u25FB-\\u25FE]\\uFE0F?"
	private val LatinSupplement: String = "[\\u00A9\\u00AE]\\uFE0F?"
	private val LetterlikeSymbols: String = "[\\u2122\\u2139]\\uFE0F?"
	private val MahjongTiles: String = "\\uD83C\\uDC04\\uFE0F?"
	private val PlayingCards: String = "\\uD83C\\uDCCF\\uFE0F?"
	private val MiscellaneousTechnical: String =
		"[\\u231A\\u231B\\u2328\\u23CF\\u23E9-\\u23F3\\u23F8-\\u23FA]\\uFE0F?"
	private val fullEmojiRegex: String
		private get() = ("(?:"
			+ MiscellaneousSymbolsAndPictographs + "|"
			+ SupplementalSymbolsAndPictographs + "|"
			+ Emoticons + "|"
			+ TransportAndMapSymbols + "|"
			+ MiscellaneousSymbols + "|"
			+ Dingbats + "|"
			+ EnclosedAlphanumerics + "|"
			+ RegionalIndicatorSymbol + "|"
			+ EnclosedAlphanumericSupplement + "|"
			+ BasicLatin + "|"
			+ Arrows + "|"
			+ MiscellaneousSymbolsAndArrows + "|"
			+ SupplementalArrows + "|"
			+ CJKSymbolsAndPunctuation + "|"
			+ EnclosedCJKLettersAndMonths + "|"
			+ EnclosedIdeographicSupplement + "|"
			+ GeneralPunctuation + "|"
			+ GeometricShapes + "|"
			+ LatinSupplement + "|"
			+ LetterlikeSymbols + "|"
			+ MahjongTiles + "|"
			+ PlayingCards + "|"
			+ MiscellaneousTechnical + ")")

	fun checkEmoji(text: String): Boolean {
		return Pattern.matches(fullEmojiRegex, text)
	}
}
