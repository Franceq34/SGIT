package commands
import classes.Branch
import tools.FileManager

object SGITInit {

  def apply(): Unit ={
    //Creation of .sgit directories
    val dirs = List(".sgit/objects", ".sgit/refs/heads", ".sgit/refs/tags", ".sgit/logs/refs/heads")
    dirs.map((path:String) => FileManager.createDir(path))
    //Initialization of Master Branch
    val masterBranch = Branch("master", List())
    masterBranch.init()
    //HEAD file creation
    FileManager.writeFile(".sgit/HEAD.txt", "ref: refs/heads/master")
  }
}
