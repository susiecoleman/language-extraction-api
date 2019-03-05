package languageDetectors

import models._
import LanguageFilters.persianFilter

object ParseInput {
//  Takes the text from data object.
// Splits it on new lines and divides the lines into 2 groups based on the filter
  def parse(data: MixedLanguageText): APIResponse = {
    val splitData = splitDataByNewLine(data)
    val markedData = markData(splitData, persianFilter)
    splitDataByMarkers(markedData)
  }

  def splitDataByNewLine(data: MixedLanguageText): Seq[String] = data.text.split("\r|\n")

  def markData(lines: Seq[String], filter: String => Boolean): Seq[MarkedData] = {
    lines.map(line => if (filter(line)) MarkedData(line, Match) else MarkedData(line, NotAMatch))
  }

  def splitDataByMarkers(markedData: Seq[MarkedData]): APIResponse = {
    markedData.foldLeft(APIResponse("", ""))((acc: APIResponse, markedData: MarkedData) => {
      markedData.marker match {
        case Match => APIResponse(s"${acc.matches}\n${markedData.data}", acc.notAMatch)
        case NotAMatch => APIResponse(acc.matches, s"${acc.notAMatch}\n${markedData.data}")
      }
    })
  }
}
