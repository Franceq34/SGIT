package commands

import classes.Branch
import tools.Reader

object SGITBranch {

  def apply(branchName:String): Boolean = {
    val currentBranch:Branch = Reader.currentBranch().get
    val newBranch = Branch(branchName, currentBranch.commits)
    newBranch.init()
  }
}
