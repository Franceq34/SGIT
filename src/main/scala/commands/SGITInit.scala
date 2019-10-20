package commands
import java.io.File

import classes.Branch
import tools.{FileManager, Printer}

object SGITInit {

  def apply(): Unit ={
    if(!FileManager.exists(".sgit")){
      init()
      Printer.init()
    } else {
      if(reset()) Printer.reinit()
      else Printer.fatalError()
    }
  }

  def init(): Unit = {
    //Creation of .sgit directories
    val dirs = List(".sgit"+ File.separator +"objects", ".sgit"+ File.separator +"refs"+ File.separator +"heads", ".sgit"+ File.separator +"refs"+ File.separator +"tags", ".sgit"+ File.separator +"logs"+ File.separator +"refs"+ File.separator +"heads")
    dirs.map((path:String) => FileManager.createDir(path))
    //Initialization of Master Branch
    val masterBranch = Branch("master", List())
    masterBranch.init()
    //HEAD file creation
    FileManager.writeFile(".sgit"+ File.separator +"HEAD.txt", "ref: refs"+ File.separator +"heads"+ File.separator +"master")
  }

  def reset(): Boolean = {
    try {
      FileManager.deleteFileRec(".sgit")
      init()
      true
    }
    catch {
      case _: Exception => false
    }
  }
}
