import crypt.CryptSHA1
import org.scalatest._

class CryptSHA1Test extends FlatSpec {

  "CryptSHA1 crypt method" should "return a SHA1 crypted value given a string" in {
    val cr:CryptSHA1 = new CryptSHA1()
    assert(cr.crypt("").get == "da39a3ee5e6b4b0d3255bfef95601890afd80709")
    assert(cr.crypt("a").get == "86f7e437faa5a7fce15d1ddcb9eaeaea377667b8")
  }

}
