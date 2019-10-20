package classes

case class BlobUpdated(blob: Blob, differences: List[Difference]){

  override def toString: String = blob.path + "\n" + differences.mkString("\n")
}


