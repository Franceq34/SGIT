import tools.FileManager
import commands._

object Launcher {
  def main(args: Array[String]): Unit = {
    Init()
    Add(path = "test")
  }
}