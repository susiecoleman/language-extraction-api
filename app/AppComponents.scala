import controllers.AssetsComponents
import play.api.ApplicationLoader.Context
import play.api.{BuiltInComponentsFromContext, NoHttpFiltersComponents}
import play.api.routing.Router
import router.Routes

class AppComponents(context: Context) extends BuiltInComponentsFromContext(context) with AssetsComponents with NoHttpFiltersComponents {

  lazy val appController = new controllers.Application(controllerComponents)
  val router: Router = new Routes(httpErrorHandler, appController, assets)

}
