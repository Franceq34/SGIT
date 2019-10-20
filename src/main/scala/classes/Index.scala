package classes

import java.io.File

import tools.{CryptSHA1, FileManager}

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

  //Creates an index with the blobs in newIndex that have same path but different hash than another blob  in this
  def getBlobsUpdated(newIndex: Index):Index = {
    Index(newIndex.list.flatMap{
      case blob if this.containsBlobWithSamePathButDifferentHash(blob) => {
        Some(blob)
      }
      case _ => {
        None
      }
    })
  }

  def containsBlobWithSamePathButDifferentHash(blob: Blob):Boolean = list.exists(b => !blob.hasSameHashThan(b) && blob.hasSamePathThan(b))

  def containsBlob(blob: Blob):Boolean = list.exists(b => blob.hasSameHashThan(b))

  def saveAsStage(): Boolean = FileManager.writeFile(".sgit"+ File.separator +"index", text = toString)

}
