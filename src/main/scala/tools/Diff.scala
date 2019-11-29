package tools

import classes.{Blob, BlobUpdated, Difference}

object Diff {

  def diffSeqStr(text1: Seq[String], text2: Seq[String]) : List[Difference] = {
    def aux(t1: Seq[String], t2: Seq[String], diffs: Seq[Difference], index: Int) : Seq[Difference] = {
      if (t1.isEmpty && t2.isEmpty)  diffs
      else if (t1.isEmpty && t2.nonEmpty) aux(t1, t2.tail, diffs :+ Difference(isAddition = true, index, t2.head), index + 1)
      else if (t2.isEmpty && t1.nonEmpty) aux(t1.tail, t2, diffs :+ Difference(isAddition = false, index, t1.head), index + 1)
      else if (t1.head.equals(t2.head)) aux(t1.tail, t2.tail, diffs, index + 1)
      else {
        val pos1 = aux(t1, t2.tail, diffs :+ Difference(isAddition = true, index, t2.head), index + 1)
        val pos2 = aux(t1.tail, t2, diffs :+ Difference(isAddition = false, index, t1.head), index + 1)
        if (pos1.length < pos2.length) pos1 else pos2
      }
    }
    aux(text1, text2, Seq(), 0).toList
  }

  def diffBlobs(list1: List[Blob], list2: List[Blob]):List[BlobUpdated] ={
    list1.flatMap(blob1 =>
      list2.flatMap(blob2 =>
        if(blob1.hasSamePathThan(blob2))  Some(BlobUpdated(blob2, diffSeqStr(blob1.getContentSeq, blob2.getContentSeq)))
        else None
      )
    )
  }

}
