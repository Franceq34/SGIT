import classes.{Blob, BlobUpdated, Difference}
import org.scalatest.FlatSpec

class BlobUpdatedTest extends FlatSpec{

  "toString" should "return a String version of the blob path followed by the differences" in {
    val difference1 = Difference(isAddition = true, 1, "ligne")
    val difference2 = Difference(isAddition = false, 1, "ligne")
    val blob = Blob("a", "b", "c")
    val blobUpdated = BlobUpdated(blob, List(difference1, difference2))
    assert(blobUpdated.toString == blob.path + "\n" + difference1.toString + "\n" + difference2.toString)
  }

  it should "return the Blob path when the differences is empty" in {
    val blob = Blob("a", "b", "c")
    val blobUpdated = BlobUpdated(blob, List())
    assert(blobUpdated.toString == blob.path + "\n")
  }
}
