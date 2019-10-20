package classes

case class Difference(isAddition: Boolean, lineIndex: Int, lineContent: String){

  override def toString:String = if(isAddition) "+ " + lineContent else "- " + lineContent

  def isDeletion:Boolean = !isAddition
}
