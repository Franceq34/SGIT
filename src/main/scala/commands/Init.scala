package commands
import tools.FileManager

object Init {

  def apply(fileManager: FileManager): Unit ={
    val dirs = List(".sgit/objects", ".sgit/refs/heads", ".sgit/refs/tags")
    dirs.map((path:String) => fileManager.createDir(path))
    fileManager.writeFile(".sgit/HEAD.txt", "ref: refs/heads/master")
  }
}
