package classes

import tools.{FileManager, CryptSHA1}

case class Index(list: List[Blob] = List()){

  override def toString: String = {
    val newList = list.map(b => b.toString())
    newList.mkString("\n")
  }

  def isEmpty: Boolean = list.isEmpty

  def size:Int = list.size

  //Add a blob in the index
  def add(blob:Blob):Index ={
    val blobPosition = list.indexOf(blob)
    //If the list doesnt contain
    if(blobPosition == -1){
      Index(list ++ List(blob))
    }
    else {
      this.copy()
    }
  }

  //Creates an index with the blobs in newIndex that are not already in this
  def getNewBlobs(newIndex: Index):Index = {
    Index(newIndex.list.flatMap{
      case blob if !this.containsBlob(blob) => {
        Some(blob)
      }
      case _ => {
        None
      }
    })
  }

  def containsBlob(blob: Blob):Boolean = list.exists(b => blob.hasSameHashThan(b))

  def saveAsStage(): Boolean = FileManager.writeFile(".sgit/index", text = toString)

}
