package languageDetectors

import scala.util.matching.Regex

object LanguageFilters {
//  Filter is based on the Unicode Range for Arabic Script
  def persianFilter(input: String): Boolean =  {
    val regex = new Regex("[\u0600-\u06ff]")
    val matches = regex.findAllMatchIn(input)
    matches.nonEmpty
  }
}
