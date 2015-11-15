package com.urbanites.sherpa

import akka.actor.ActorSystem

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
 
object ApplicationMain extends App {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher
 
  val route =
    path("hello") {
      get {
        complete {
          "hello"
        }
      }
    }
 
  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  // TODO elegantly wait forever?
  while(true) {
    Thread.sleep(1000)
  }
  
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.shutdown()) // and shutdown when done
}
