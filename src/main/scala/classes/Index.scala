package classes

import tools.{FileManager, CryptSHA1}

case class Index(list: List[Blob] = List()){

  override def toString: String = {
    val newList = list.map(b => b.toStringIndex())
    newList.mkString("\n")
  }

  def add(blob:Blob):Index ={
    val blobPosition = list.indexOf(blob)
    //If the list doesnt contain
    if(blobPosition != -1){
      Index(list ++ List(blob))
    }
    else {
      Index(list.updated(blobPosition, blob))
    }
  }

  def update(oldIndex: Index):Index = {
    //Ajoute à la liste de this tous les blobs de oldIndex qui ne sont pas déjà présents
    Index(list ++ oldIndex.list.flatMap{
      case blob if !list.contains(blob) => {
        Some(blob)
      }
      case _ => None
    })
  }

  def containsBlob(blob: Blob):Boolean = { list.exists(b => blob.hasSamePathThan(b) || blob.hasSameHashThan(b)) }

  def saveAsStage(): Boolean = FileManager.writeFile(".sgit/index", toString)
}
