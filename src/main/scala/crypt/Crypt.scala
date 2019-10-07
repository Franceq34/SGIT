package crypt

trait Crypt {
  def crypt(str: String):Option[String]
}
