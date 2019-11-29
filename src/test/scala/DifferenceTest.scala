/*
import classes.Difference
import org.scalatest.FlatSpec

class DifferenceTest extends FlatSpec{

  "toString" should "return a String representing the difference when it's an addition" in {
    val diff1 = Difference(isAddition = true, 1, "Hey")
    assert(diff1.toString == "+ Hey")
  }

  it should "return a String representing the difference when it's a deletion" in {
    val diff1 = Difference(isAddition = false, 1, "Hey")
    assert(diff1.toString == "- Hey")
  }

  "isDeletion" should "never return same value than isAddition" in {
    val diff1 = Difference(isAddition = true, 1, "Hey")
    assert(diff1.isAddition != diff1.isDeletion)
    val diff2 = Difference(isAddition = false, 1, "Hey")
    assert(diff2.isAddition != diff2.isDeletion)
  }
}
*/
