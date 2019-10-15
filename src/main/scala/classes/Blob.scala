package classes

import tools.FileManager

case class Blob(override val idHash: String, content: String, path: String) extends SGITObject{

  override def toString: String = idHash + " "  + path

  def hasSamePathThan(b: Blob): Boolean = path == b.path

  def addToObject(): Boolean ={
    //create the correct directory in objects folder
    FileManager.createDir(".sgit/objects/" + idHash.substring(0, 2))
    //write the staged files in the objects
    FileManager.writeFile(".sgit/objects/" + idHash.substring(0, 2) + "/" + idHash.substring(2), content)
  }
}
