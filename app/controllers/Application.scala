package controllers

import play.api.mvc._
import io.circe.syntax._
import io.circe.generic.auto._
import utils.Parser._

class Application(val controllerComponents: ControllerComponents) extends BaseController{

  def index = Action {
    Ok("Language Extraction API")
  }

  def uploadData = Action { req =>
    {
      val data = for {
        body <- extractBody(req.body.asJson.map(_.toString))
        json <- stringToJson(body)
        data <- jsonToData(json)
      } yield data
      val response = data.fold(_ => InternalServerError, r => Ok(r.asJson.noSpaces))
      response.as("text/json")
    }
  }

}




