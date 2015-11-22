package controllers

import models.PointOfInterest
import play.api._
import play.api.libs.ws.WS
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

class Application extends Controller {

  val apiKey = Play.current.configuration.getString("flickr.apiKey").get
  val flickrPhotoInfoUrl = s"https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=$apiKey&bbox=%s,%s,%s,%s&extras=geo&sort=interestingness-desc&format=json&nojsoncallback=1&page=1&per_page=500"

  case class FlickrPhoto(photoId: String, title: String, secret: String, farm: Int, server: String, latitude: Double, longitude: Double) {
    def photoUrl(size: String = "m"): String = s"http://farm$farm.staticflickr.com/$server/${photoId}_${secret}_$size.jpg"
  }

  implicit val flickrReads: Reads[FlickrPhoto] = (
      (JsPath \ "id").read[String] and
      (JsPath \ "title").read[String] and
      (JsPath \ "secret").read[String] and
      (JsPath \ "farm").read[Int] and
      (JsPath \ "server").read[String] and
      (JsPath \ "latitude").read[String].map(_.toDouble) and
      (JsPath \ "longitude").read[String].map(_.toDouble)
    )(FlickrPhoto.apply(_, _, _, _, _, _, _))


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

  def poiService(lat1: Double, long1: Double, lat2: Double, long2: Double) = Action.async {
    val flickrUrl = flickrPhotoInfoUrl format(lat1, long1, lat2, long2)
    Logger.debug(s"flickr url: $flickrUrl")
    WS.url(flickrUrl).get().map { response =>
      Logger.debug(s"response: ${response.json}")
      val flickrJsonPhotos = (response.json \ "photos" \ "photo").as[JsArray].value
      Logger.debug("flickr json: " + flickrJsonPhotos)
      val flickrPhotos = flickrJsonPhotos.map(flickrJsonPhoto => flickrJsonPhoto.as[FlickrPhoto])
      val ownPhotos = flickrPhotos.map(flickrPhoto => poiWrites.writes(PointOfInterest(flickrPhoto.photoUrl(), flickrPhoto.latitude, flickrPhoto.longitude, flickrPhoto.title)))
      Ok(JsArray(ownPhotos))
    }
  }
}
