package commands

import classes.Branch
import tools.Reader

object SGITBranch {

  def apply(branchName:String): Boolean = {
    branch(branchName, Reader.currentBranch())
  }

  def branch(branchName:String, currentBranchOption: Option[Branch]): Boolean = {
    if(currentBranchOption.isDefined){
      val currentBranch = currentBranchOption.get
      val newBranch = Branch(branchName, currentBranch.commits)
      newBranch.init()
    }
    currentBranchOption.isDefined
  }
}
