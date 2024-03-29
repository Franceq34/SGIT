package classes

import java.io.File

import tools.FileManager

case class Commit(override val idHash: String, author: String = "", mail: String = "", message : String, index: Index) extends SGITObject{

  override def toString: String = idHash + " " + author + " " + mail + " " + message

  def toStringLog: String = "commit " + idHash + "\nAuthor: " + author + " <" + mail + ">\n" + message

  def saveToObjects(): Boolean ={
    //create the correct directory in objects folder
    FileManager.createDir(".sgit"+ File.separator +"objects"+ File.separator + idHash.substring(0, 2))
    //write the staged files in the objects
    FileManager.writeFile(".sgit"+ File.separator +"objects"+ File.separator + idHash.substring(0, 2) + "/" + idHash.substring(2), index.toString)
  }
}

