package commands

import java.io.File

import classes.{Blob, Difference, Index}
import tools.{Diff, FileManager, Printer, Reader}

object SGITDiff {

  def apply(): Boolean = {
    diff(Reader.currentIndex(), Reader.getBlobsFromPath("."))
  }

  def diff(maybeIndex: Option[Index], blobsFromWD: List[Blob]): Boolean = {
    if(maybeIndex.isDefined){
      val allBranchBlobs = Reader.currentBranch().get.getAllBlobs
      val currentIndex = maybeIndex.get
      val blobsUpdated = Index(blobsFromWD).getBlobsNotIn(Index(allBranchBlobs), currentIndex)
      println("blobsUpdated")
      blobsUpdated.list.foreach(blob => println(blob))
      val blobsInStageUpdated = currentIndex.list ++ allBranchBlobs
      println("blobsInStageUpdated")
      blobsInStageUpdated.foreach(blob => println(blob))
      val listBlobsUpdated = Diff.diffBlobs(blobsInStageUpdated, blobsUpdated.list)
      listBlobsUpdated.foreach(blobUpdated => Printer.diffs(blobUpdated))
      true
    } else false
  }

  def seqDiffsToString(seq: Seq[Difference]):String = {
    seq.mkString("\n")
  }
}
