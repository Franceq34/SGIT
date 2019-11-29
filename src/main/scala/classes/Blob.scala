package classes

import java.io.File

import tools.FileManager

case class Blob(override val idHash: String, content: String, path: String) extends SGITObject{

  override def toString: String = idHash + " "  + path

  def hasSamePathThan(b: Blob): Boolean = path == b.path || path == "./"+b.path || "./"+path == b.path

  def getContentSeq:Seq[String] = content.split("\n").toSeq

  def addToObject(): Boolean ={
    //create the correct directory in objects folder
    FileManager.createDir(".sgit"+File.separator +"objects"+File.separator + idHash.substring(0, 2))
    //write the staged files in the objects
    FileManager.writeFile(".sgit"+File.separator +"objects"+File.separator + idHash.substring(0, 2) +File.separator + idHash.substring(2), content)
  }
}
