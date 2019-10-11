package tools

object CryptSHA1 {
  def apply(str:String): Option[String] = {
    try {
      val md = java.security.MessageDigest.getInstance("SHA-1")
      Some(md.digest(str.getBytes("UTF-8")).map("%02x".format(_)).mkString)
    } catch {
      case _: Exception => None
    }
  }
}
