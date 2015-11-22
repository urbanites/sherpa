package clients.flickr

import dispatch._, Defaults._


class FlickrClient {

  val svc = url("http://api.hostip.info/country.php")
  val country  = Http(svc OK as.String)
}
