package classes

case class Blob(override val idHash: String, content: String, path: String) extends SGITObject{

  def toStringIndex(): String = {
    idHash + " "  + path
  }

  def hasSamePathThan(b: Blob): Boolean = b match {
    case that: SGITObject => that.path == this.path
    case _ => false
  }
}
