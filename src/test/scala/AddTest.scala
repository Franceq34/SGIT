import commands.Add
import org.scalatest.FlatSpec
import tools.FileManager

class AddTest extends FlatSpec {

  "Add" should "return false if you try to add an unknown directory or file" in{
    val fm:FileManager = new FileManager(globalPath = "./tests")
    assert(!Add(fm, "hntdere"))
    assert(!Add(fm, "thnhz.txt"))
  }

  it should "return false if you try to add .git and its children" in{
    val fm:FileManager = new FileManager(globalPath = "./tests")
    fm.createDir("./.git")
    assert(!Add(fm, ".git"))
    assert(!Add(fm, ".git/test"))
  }

}
