import tools.FileManager
import commands._

object Main {
  def main(args: Array[String]): Unit = {
    val fm:FileManager = new FileManager("/home/quentin/IdeaProjects/output/")
    Init(fileManager = fm)
    Add(fileManager = fm, path = "")
  }
}