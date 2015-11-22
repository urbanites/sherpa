package controllers

import models.PointOfInterest
import play.api._
import play.api.libs.ws.WS
import play.api.mvc._
import play.api.libs.json._

import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

class Application extends Controller {

  val apiKey = Play.current.configuration.getString("flickr.apiKey").get
  val flickrPhotoInfoUrl = s"https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=$apiKey&bbox=%s,%s,%s,%s&extras=geo&sort=interestingness-desc&format=json&nojsoncallback=1&page=1&per_page=500"


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

  def poiService(lat1: Double, long1: Double, lat2: Double, long2: Double) = Action.async {
    val flickrUrl = flickrPhotoInfoUrl format(lat1, long1, lat2, long2)
    Logger.debug(s"flickr url: $flickrUrl")
    WS.url(flickrUrl).get().map { response =>
      val flickrJsonPhotos = (response.json \ "photos" \ "photo").as[JsArray]
      Logger.debug("flickr json: " + flickrJsonPhotos)
      // flickrJsonPhotos.transform()
      val ownJsonPhotos = flickrJsonPhotos.value.map { flickrPhoto =>
        PointOfInterest("http://nl.dbpedia.org/resource/Maastunnel", 51.899722, 4.467778, "Maastunnel")
      }
      Ok(flickrJsonPhotos)
    }
    // val poi = PointOfInterest("http://nl.dbpedia.org/resource/Maastunnel", 51.899722, 4.467778, "Maastunnel")
    //val poi2 = poi.copy(lat = lat1)
    // Ok(Json.toJson(poi2))
  }

}
