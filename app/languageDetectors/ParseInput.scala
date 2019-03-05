package languageDetectors

import models._
import LanguageFilters.persianFilter

object ParseInput {
  def parse(data: Data): Response = {
    val splitData = splitDataByNewLine(data)
    val markedData = markData(splitData, persianFilter)
    splitDataByMarkers(markedData)
  }

  def splitDataByNewLine(data: Data): Seq[String] = data.text.split("\r|\n")

  def markData(lines: Seq[String], filter: String => Boolean): Seq[MarkedData] = {
    lines.map(line => if (filter(line)) MarkedData(line, Match) else MarkedData(line, NotAMatch))
  }

  def splitDataByMarkers(markedData: Seq[MarkedData]): Response = {
    markedData.foldLeft(Response("", ""))((acc, data) => {
      data.marker match {
        case Match => Response(acc.language1 + "\n" + data.data, acc.language2)
        case NotAMatch => Response(acc.language1, acc.language2 + "\n" + data.data)
      }
    })
  }
}
