package utils

import io.circe.{Json, parser}
import io.circe.generic.auto._
import models.{BodyError, Error, InvalidJsonError, MixedLanguageText}

object JsonParser {

  def extractBody(body: Option[String]): Either[Error, String] = Either.cond(body.isDefined, body.get, BodyError)

  def stringToJson(s: String): Either[InvalidJsonError, Json] = parser.parse(s).fold(_ => Left(InvalidJsonError(s"$s is not valid json")), Right(_))

  def jsonToData(json: Json): Either[InvalidJsonError, MixedLanguageText] = json.as[MixedLanguageText].fold(_ => Left(InvalidJsonError(s"$json is not valid json")), Right(_))
}
