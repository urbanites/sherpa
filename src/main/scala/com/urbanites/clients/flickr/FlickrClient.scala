package com.urbanites.clients.flickr

import dispatch.{Http, url}


class FlickrClient {

  val svc = url("http://api.hostip.info/country.php")
  val country = Http(svc OK as.String)
}
