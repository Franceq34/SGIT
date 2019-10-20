import classes.{Blob, Index}
import org.scalatest.FlatSpec

class IndexTest extends FlatSpec {

  "toString" should "return a string representation of a one blob index" in {
    val blob1 = Blob("a", "b", "c")
    val index1 = Index(List(blob1))
    assert(index1.toString == blob1.toString)
  }

  it should "return a string representation of a two blobs index" in {
    val blob1 = Blob("a", "b", "c")
    val blob2 = Blob("d", "e", "f")
    val index2 = Index(List(blob1, blob2))
    assert(index2.toString == blob1.toString + "\n" + blob2.toString)
  }

  it should "return an empty string when it has no blobs" in {
    val emptyIndex = Index(List())
    assert(emptyIndex.toString == "")
  }

  "isEmpty" should "return true when given an empty index" in {
    val emptyIndex = Index(List())
    assert(emptyIndex.isEmpty)
  }

  it should "return false when given an index with one element" in {
    val blob = Blob("a", "b", "c")
    val emptyIndex = Index(List(blob))
    assert(!emptyIndex.isEmpty)
  }

  "add" should "return a new index containing only the blob given in parameter when it is empty" in {
    val emptyIndex = Index(List())
    val blob = Blob("a", "b", "c")
    val newIndex = emptyIndex.add(blob)
    assert(newIndex.list.contains(blob) && newIndex.size == 1)
  }

  it should "return a new index with a size of n+1 when given a new blob in parameter" in {
    val blob1 = Blob("a", "b", "c")
    val index = Index(List(blob1))
    val blob2 = Blob("d", "e", "f")
    val newIndex = index.add(blob2)
    assert(newIndex.list.contains(blob2) && newIndex.size == index.size + 1)
  }

  it should "return a new index with a size of n when given a blob already in the index" in {
    val blob1 = Blob("a", "b", "c")
    val index = Index(List(blob1))
    val newIndex = index.add(blob1)
    assert(newIndex.list.contains(blob1) && newIndex.size == index.size)
  }

  "getNewBlobs" should "return an index of size 0 when given an empty index in parameter" in{
    val aBlob = Blob("a", "b", "c")
    val anIndex = Index(List(aBlob))
    val emptyIndex = Index(List())
    assert(anIndex.getNewBlobs(emptyIndex).size == 0)
  }

  it should "return an index of size 1 when given an index with 1 blob which has no blob with same hash" in{
    val aBlob = Blob("a", "b", "c")
    val anotherBlob = Blob("d", "b", "c")
    val anIndex = Index(List(aBlob))
    val anotherIndex = Index(List(anotherBlob))
    assert(anIndex.getNewBlobs(anotherIndex).size == 1)
  }

  it should "return an index of size 0 when given an index with 1 blob which has same hash than another blob" in{
    val aBlob = Blob("a", "b", "c")
    val anotherBlob = Blob("a", "z", "e")
    val anIndex = Index(List(aBlob))
    val anotherIndex = Index(List(anotherBlob))
    assert(anIndex.getNewBlobs(anotherIndex).size == 0)
  }

  "containsBlob" should "return true when given a blob that has same hash than another in the index" in {
    val aBlob = Blob("a", "b", "c")
    val anotherBlob = Blob("a", "z", "e")
    val anIndex = Index(List(aBlob))
    assert(anIndex.containsBlob(anotherBlob))
  }

  it should "return false when given a blob that hasnt same hash than another in the index" in {
    val aBlob = Blob("a", "b", "c")
    val anotherBlob = Blob("d", "b", "c")
    val anIndex = Index(List(aBlob))
    assert(!anIndex.containsBlob(anotherBlob))
  }
}

