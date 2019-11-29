/*
import classes.{Blob, BlobUpdated, Difference}
import org.scalatest.FlatSpec
import tools.Diff

class DiffTest extends FlatSpec{

  "diffBlobs" should "return a list of blobsUpdated" in {
    val blob1 = Blob("a", "b", "c")
    val blob2 = Blob("a", "b\nc", "c")
    val difference1 = Difference(isAddition = true, 1, "c")
    assert(Diff.diffBlobs(List(blob1), List(blob2)) == List(BlobUpdated(blob2, List(difference1))))
  }
}
*/
