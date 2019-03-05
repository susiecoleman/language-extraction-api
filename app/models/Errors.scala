package models

trait Error
case object BodyError extends Error
case class InvalidJsonError(message: String) extends Error
