import org.scalatest._
import _root_.tools.CryptSHA1

class CryptSHA1Test extends FlatSpec {

  "CryptSHA1 apply method" should "return a SHA1 crypted value given a string" in {
    assert(CryptSHA1("").get == "da39a3ee5e6b4b0d3255bfef95601890afd80709")
    assert(CryptSHA1("a").get == "86f7e437faa5a7fce15d1ddcb9eaeaea377667b8")
  }

}
