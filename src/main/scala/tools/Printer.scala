package tools

import classes.{BlobUpdated, Branch, Commit, Difference, Index}
import commands.SGITDiff.seqDiffsToString

object Printer {
  def logCommits(commits:List[Commit]): Unit = println(commits.map(commit => commit.toStringLog).mkString("\n") + "\n")

  def diffs(blobUpdated: BlobUpdated):Unit = if(blobUpdated.differences.nonEmpty) println(blobUpdated + "\n")

  def notAGitRepo(): Unit = println("fatal: not a sgit repository")

  def unknownArgument(): Unit = println("Unknown argument")

  def init(): Unit = println("Initialized empty SGit repository in " + System.getProperty("user.dir") + "/.sgit/")

  def reinit(): Unit = println("Reinitialized existing SGit repository in " + System.getProperty("user.dir") + "/.sgit/")

  def fatalError(): Unit = println("Fatal error")

  def changesNotStaged(blobsUpdated: Index): Unit = println("Changes not staged for commit:\n"+blobsUpdated+"\n")

  def nothingAddedButUntrackedFiles(): Unit = println("nothing added to commit but untracked files present")

  def noChangesAdded(): Unit = println("no changes added to commit.")

  def changesToBeCommitted(blobsFromIndex: Index): Unit =  println("Changes to be committed:\n"+blobsFromIndex + "\n")

  def untrackedFiles(blobsUntracked: Index): Unit = println("Untracked files:\n"+blobsUntracked + "\n")

  def noCommits(): Unit = println("No commits yet\n")

  def currentBranch(currentBranch: Branch): Unit = println("On branch " + currentBranch.name + "\n")

  def invalidArgumentsNumber(): Unit = println("Invalid argument number")


}
