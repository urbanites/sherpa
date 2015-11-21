package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

class Application extends Controller {

  case class PointOfInterest(uri: String, lat: Double, lon: Double, description: String)

  implicit val poiWrites = new Writes[PointOfInterest] {
    def writes(poi: PointOfInterest) = Json.obj(
      "uri" -> poi.uri,
      "lat" -> poi.lat,
      "long" -> poi.lon,
      "description" -> poi.description
    )
  }

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def poiService = Action {
    val poi = PointOfInterest("http://nl.dbpedia.org/resource/Maastunnel", 51.899722, 4.467778, "Maastunnel")
    Ok(Json.toJson(poi))
  }

}
