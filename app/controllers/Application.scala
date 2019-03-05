package controllers

import play.api.mvc._
import io.circe.syntax._
import io.circe.generic.auto._
import languageDetectors.ParseInput
import models._
import utils.JsonParser._

class Application(val controllerComponents: ControllerComponents) extends BaseController {

  def index = Action {
    Ok("Language Extraction API")
  }

  def extractLanguage = Action { req => {
    val input: Either[Error, MixedLanguageText] =
      for {
        body <- extractBody(req.body.asJson.map(_.toString))
        json <- stringToJson(body)
        data <- jsonToData(json)
      } yield data
    val response = input.fold(_ => InternalServerError, r => Ok(ParseInput.parse(r).asJson.noSpaces))
    response.as("text/json")
  }
  }

}




