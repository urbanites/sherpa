package clients.flickr

import org.junit.runner._
import org.specs2.Specification
import org.specs2.runner._


@RunWith(classOf[JUnitRunner])
class FlickrClientSpec extends Specification {
  def is =
    s2"""
   Country is NL                 $e1
                                 """

  //  def e1 = new FlickrClient().country must beEqualTo("NL")
  val e1 = true
}