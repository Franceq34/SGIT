import classes.Blob
import org.scalatest.FlatSpec

class BlobTest extends FlatSpec{

  "toStringIndex" should "return a String that includes hash and path" in{
    val blob:Blob = Blob(idHash = "a", content = "b", "c")
    assert(blob.toStringIndex() == "a c")
  }

  "hasSamePathThan" should "return true" in{
    val blob1:Blob = Blob(idHash = "a", content = "b", path = "c")
    val blob2:Blob = Blob(idHash = "a", content = "b", path = "c")
    assert(blob1.hasSamePathThan(blob2))
  }

  it should "return false if two blobs has a different path" in{
    val blob1:Blob = Blob(idHash = "a", content = "b", path = "c")
    val blob2:Blob = Blob(idHash = "a", content = "b", path = "d")
    assert(!blob1.hasSamePathThan(blob2))
  }
}
