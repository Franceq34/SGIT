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
      val currentIndex = maybeIndex.get
      val blobsUpdated = currentIndex.getBlobsUpdated(Index(blobsFromWD))
      val blobsInStageUpdated = currentIndex.list
        .map(blob =>
          blob.copy(content =
            FileManager.readFile(
              path = ".sgit"+File.separator+"objects"+File.separator + blob.idHash.substring(0,2) + File.separator + blob.idHash.substring(2))
              .getOrElse("")))
      val listBlobsUpdated = Diff.diffBlobs(blobsInStageUpdated, blobsUpdated.list)
      listBlobsUpdated.foreach(blobUpdated => Printer.diffs(blobUpdated))
      true
    } else false
  }

  def seqDiffsToString(seq: Seq[Difference]):String = {
    seq.mkString("\n")
  }
}
