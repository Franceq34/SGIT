import tools.FileManager
import commands._

object Launcher {
  def main(args: Array[String]): Unit = {
    SGITInit()
    SGITAdd(path = "test")
    SGITBranch("testBranch")
    SGITCommit("author", "mail", "test")
    SGITAdd(path = "test")
    SGITCommit("author", "mail", "test2")
    SGITAdd(path = "test")
    SGITAdd(path = "test")
    SGITCommit("author", "mail", "test3")
  }
}