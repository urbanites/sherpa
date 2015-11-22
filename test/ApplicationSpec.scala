import org.specs2.matcher
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import play.api.libs.json.{JsArray, Json}

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beSome.which (status(_) == NOT_FOUND)
    }

    "render the index page" in new WithApplication{
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain ("Hello world")
    }

    "send a poi json " in new WithApplication {
      val poi = route(FakeRequest(GET, "/poi?lat1=51.8&long1=4.4&lat2=51.9&long2=4.5")).get

      status(poi) must equalTo(OK)
      contentType(poi) must beSome.which(_ == "application/json")

      val json = Json.parse(contentAsString(poi)).as[JsArray]
      (json(0) \ "description").as[String] must startWith("2012-11-05 Rotterdam - Carnisse 3Hoek")
    }

    "require coordinates " in new WithApplication {
      val poi = route(FakeRequest(GET, "/poi")).get

      status(poi) must equalTo(BAD_REQUEST)
    }
  }
}

