package languageDetectors

import models._
import org.scalatest.{FlatSpec, Matchers}

class ParseInputTest extends FlatSpec with Matchers {

  "SplitDataByNewLine" should "return a list of 2 items if input contains a newline character" in {
    val data = MixedLanguageText("123\nABC")
    ParseInput.splitDataByNewLine(data) should be
    Seq("123", "ABC")
  }
  it should "return a list of 2 items if input contains a carriage return" in {
    val data = MixedLanguageText("123\rABC")
    ParseInput.splitDataByNewLine(data) should be
    Seq("123", "ABC")
  }
  it should "return a list of 4 items if there are multiple new line characters" in {
    val data = MixedLanguageText("123\nABC\r456\nDEF")
    ParseInput.splitDataByNewLine(data) should be
    Seq("123", "ABC", "456", "DEF")
  }
  it should "return a list with one item if there are no new line indicators" in {
    val data = MixedLanguageText("123")
    ParseInput.splitDataByNewLine(data) should be
    Seq("123")
  }
  it should "return an empty list if the data text is an empty string" in {
    val data = MixedLanguageText("")
    ParseInput.splitDataByNewLine(data) should be
    Seq.empty
  }

  "MarkData" should "mark data that passes the filter as Match" in {
    val lines = Seq("hello")
    val filter = (s: String) => s.length > 3
    ParseInput.markData(lines, filter) should be
    Seq(MarkedData("hello", Match))
  }
  it should "mark data that fails the filter as NotAMatch" in {
    val lines = Seq("hi")
    val filter = (s: String) => s.length > 3
    ParseInput.markData(lines, filter) should be
    Seq(MarkedData("hi", NotAMatch))
  }
  it should "mark data correctly based on the filter" in {
    val lines = Seq("hi", "hello")
    val filter = (s: String) => s.length > 3
    ParseInput.markData(lines, filter) should be
    Seq(MarkedData("hi", NotAMatch), MarkedData("hello", Match))
  }

  "SplitDataByMarkers" should "group all data marked as Matching" in {
    val markedData = Seq(MarkedData("hello", Match), MarkedData("goodbye", Match))
    ParseInput.splitDataByMarkers(markedData) should be
    APIResponse("hello\ngoodbye", "")
  }
  it should "group all data marked as NotAMatch" in {
    val markedData = Seq(MarkedData("hi", NotAMatch), MarkedData("dog", NotAMatch))
    ParseInput.splitDataByMarkers(markedData) should be
    APIResponse("", "hi\ndog")
  }
  it should "group data based on Marker" in {
    val markedData =
      Seq(MarkedData("hi", NotAMatch), MarkedData("dog", NotAMatch),MarkedData("hello", Match), MarkedData("goodbye", Match))
    ParseInput.splitDataByMarkers(markedData) should be
    APIResponse("hello\ngoodbye", "hi\ndog")
  }

  "Parse" should "split data into Persian and not Persian" in {
    val data = MixedLanguageText("Hello\nبزرگ\u200Cترین")
    ParseInput.parse(data, LanguageFilters.persianFilter) should be
    APIResponse("بزرگ\u200Cترین", "Hello")
  }
}