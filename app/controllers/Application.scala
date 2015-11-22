package controllers

import models.PointOfInterest
import play.api._
import play.api.mvc._
import play.api.libs.json._

class Application extends Controller {

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
  // Ok("")
  }

  def poiService(lat1: Double, long1: Double, lat2: Double, long2: Double) = Action {
    val poi = PointOfInterest("http://nl.dbpedia.org/resource/Maastunnel", 51.899722, 4.467778, "Maastunnel")
    val poi2 = poi.copy(lat = lat1)
    Ok(Json.toJson(poi2))
  }

}
