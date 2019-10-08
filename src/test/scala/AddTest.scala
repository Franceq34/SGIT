import commands.{Add, Init}
import crypt._
import org.scalatest.FlatSpec
import tools.FileManager

class AddTest extends FlatSpec {

  "Add" should "return false if you try to add an unknown directory or file" in{
    val fm:FileManager = new FileManager(globalPath = "./tests/")
    Init(fm)
    assert(!Add(fm, "hntdere"))
    assert(!Add(fm, "thnhz.txt"))
  }

  it should "return true if you try to add an existing directory or file" in{
    val fm:FileManager = new FileManager(globalPath = "./tests")
    Init(fm)
    fm.writeFile("testFile.txt", "Bonjour")
    assert(Add(fm, "testFile.txt"))
  }

  it should "add a blob into the hashed path object directory" in{
    val fm:FileManager = new FileManager(globalPath = "./tests/")
    Init(fm)
    fm.writeFile("testFile.txt", "Bonjour")
    assert(Add(fm, "testFile.txt"))
    val cr:Crypt = new CryptSHA1()
    val path:String = cr.crypt("Bonjour").getOrElse("")
    assert(fm.exists(".sgit/objects/"+path.substring(0, 2)+"/"+path.substring(2)))
  }

  it should "add a blob containing the same content" in{
    val fm:FileManager = new FileManager(globalPath = "./tests/")
    Init(fm)
    fm.writeFile("testFile.txt", "Bonjour")
    assert(Add(fm, "testFile.txt"))
    val cr:Crypt = new CryptSHA1()
    val path:String = cr.crypt("Bonjour").getOrElse("")
    assert(fm.readFile(".sgit/objects/"+path.substring(0, 2)+"/"+path.substring(2)).getOrElse("") == "Bonjour")
  }

  it should "return false if you try to add .sgit and its children" in{
    val fm:FileManager = new FileManager(globalPath = "./tests/")
    Init(fm)
    assert(!Add(fm, ".sgit"))
    assert(!Add(fm, ".sgit/test"))
  }

}
