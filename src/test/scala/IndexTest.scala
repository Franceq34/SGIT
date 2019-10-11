import classes.{Blob, Index}
import org.scalatest.FlatSpec
import org.scalamock.scalatest.MockFactory

class IndexTest extends FlatSpec  with MockFactory {

  "current" should "" in {

  }

  "add" should "" in {

  }

  "update" should "" in {

  }

  "containsBlob" should "" in {

  }

  "saveAsStage" should "" in {

  }

  "toString" should "a stringified version of the index" in{
    val blob1 = Blob("a", "b", "c")
    val blob2 = Blob("d", "f", "g")
    val index1 = Index(List(blob1))
    val index2 = Index(List(blob1, blob2))
    assert(index1.toString == "a c")
    assert(index2.toString == "a c\nd g")
  }

  it should "return an empty string when the Index is empty" in {
    val index = Index(List())
    assert(index.toString == "")

  }
}