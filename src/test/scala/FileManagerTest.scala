import org.scalatest._
import _root_.tools.FileManager

class FileManagerTest extends FlatSpec {

  "FileManager" should "be instancied in the current directory when no parameters are given" in {
    val fm:FileManager = new FileManager()
    assert(fm.globalPath == ".")
  }

  it should "accept a globalPath string parameter" in{
    val fm:FileManager = new FileManager(globalPath = "./tests")
    assert(fm.globalPath == "./tests")
  }

  "writeFile" should "accept one path String and one text String parameter" in {
    val fm:FileManager = new FileManager("./tests")
    assert(fm.writeFile(path = "./testfile", text =""))
  }

  it should "create a file if it doesn't exist" in {
    val fm:FileManager = new FileManager("./tests")
    assert(fm.writeFile(path = "./testfile", text ="HelloWorld"))
  }

  it should "return true if the operation succeeded" in {
    val fm:FileManager = new FileManager("./tests")
    assert(fm.writeFile(path = "./testfile", text = "HelloWorld"))
  }

  it should "return false if the operation failed" in {
    val fm:FileManager = new FileManager("./tests")
    assert(!fm.writeFile(path = ".//", text = "HelloWorld"))
  }

  "readFile" should "accept one path String parameter" in {
    val fm:FileManager = new FileManager("./tests")
    assert(fm.readFile(path = "./testfile").get.isInstanceOf[String])
  }

  it should "return Option[String] if the operation succeeded" in {
    val fm:FileManager = new FileManager("./tests")
    assert(fm.readFile(path = "./testfile").get.isInstanceOf[String])
  }

  it should "return false if the operation failed" in {
    val fm:FileManager = new FileManager("./tests")
    assert(fm.readFile(path = ".//").isEmpty)
  }


}
