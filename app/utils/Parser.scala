package utils

import io.circe.{Json, parser}
import io.circe.generic.auto._
import models.{BodyError, Error, InvalidJsonError, Data}

object Parser {

  def extractBody(body: Option[String]): Either[Error, String] = Either.cond(body.isDefined, body.get, BodyError)

  def stringToJson(s: String): Either[InvalidJsonError, Json] = parser.parse(s).fold(_ => Left(InvalidJsonError(s"$s is not valid json")), Right(_))

  def jsonToData(json: Json): Either[InvalidJsonError, Data] = json.as[Data].fold(_ => Left(InvalidJsonError(s"$json is not valid json")), Right(_))

}
