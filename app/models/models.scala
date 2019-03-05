package models

case class MixedLanguageText(text: String)

case class APIResponse(matches: String, notAMatch: String)

case class MarkedData(data: String, marker: Marker)

sealed trait Marker
case object Match extends Marker
case object NotAMatch extends Marker