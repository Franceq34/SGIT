package crypt

class CryptSHA1 extends Crypt {
  override def crypt(str: String): String = {
    val md = java.security.MessageDigest.getInstance("SHA-1")
    md.digest(str.getBytes("UTF-8")).map("%02x".format(_)).mkString
  }
}
