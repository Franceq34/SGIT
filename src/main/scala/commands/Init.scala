package commands
import tools.FileManager

object Init {

  def apply(): Unit ={
    val dirs = List(".sgit/objects", ".sgit/refs/heads", ".sgit/refs/tags")
    dirs.map((path:String) => FileManager.createDir(path))
    FileManager.writeFile(".sgit/HEAD.txt", "ref: refs/heads/master")
  }
}
