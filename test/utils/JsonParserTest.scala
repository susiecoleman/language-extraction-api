package utils

import io.circe.Json
import org.scalatest.{FlatSpec, Matchers}
import io.circe.parser._
import models.{MixedLanguageText, InvalidJsonError, Error}

class JsonParserTest extends FlatSpec with Matchers{

  "JsonToData" should "create a Data object if json is a valid form" in {
    val json = parse("""{"text":"myText"}""").right.get
    JsonParser.jsonToData(json) should be
    MixedLanguageText("myText")

  }

  it should "return a left of InvalidJsonError if the json cannot be parsed to a Data object" in {
    val json = parse("""{"name":"myText"}""").right.get
    JsonParser.jsonToData(json).left.get shouldBe a[InvalidJsonError]

  }

  "StringToJson" should "return Right Json if the string can be parsed to json" in {
    val s = """{"text":"myText"}"""
    JsonParser.stringToJson(s).right.get shouldBe a[Json]
  }

  it should "return Left InvalidJsonError if the string cannot be parsed to json" in {
    val s = "INVALID"
    JsonParser.stringToJson(s).left.get shouldBe a[InvalidJsonError]
  }

  it should "return Left InvalidJsonError when passed an empty string" in {
    val s = ""
    JsonParser.stringToJson(s).left.get shouldBe a[InvalidJsonError]
  }

  "ExtractBody" should "return a Right String if body is defined" in {
  val body = Some("data")
    JsonParser.extractBody(body).right.get shouldBe a[String]
  }

  it should "return a Right Error if body is not defined" in {
    val body = None
    JsonParser.extractBody(body).left.get shouldBe a[Error]
  }
}
