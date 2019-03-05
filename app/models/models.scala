package models

case class Data(text: String)

case class Response(language1: String, language2: String)

case class MarkedData(data: String, marker: Marker)

sealed trait Marker

case object Match extends Marker

case object NotAMatch extends Marker