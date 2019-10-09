package classes

trait SGITObject{

  val idHash:String

   def hasSameHashThan(o: SGITObject): Boolean = o match {
    case that: SGITObject => that.idHash == this.idHash
    case _ => false
  }
}
