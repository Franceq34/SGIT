package tools
import commands._

object Parser {

  def apply(args: Array[String]): Unit ={
    args match {
      case Array() => Printer.invalidArgumentsNumber()
      case _ =>
        args(0) match {
          case "init" => init(args.drop(1))
          case "status" => status(args.drop(1))
          case "diff" => diff(args.drop(1))
          case "add" => add(args.drop(1))
          case "commit" => commit(args.drop(1))
          case "log" => log(args.drop(1))
        }
    }
  }

  def init(args: Array[String]): Unit ={
    args match {
      case Array() => SGITInit()
      case _ => Printer.invalidArgumentsNumber()
    }
  }

  def status(args: Array[String]): Unit ={
    args match {
      case Array() => SGITStatus()
      case _ => Printer.invalidArgumentsNumber()
    }
  }

  def diff(args: Array[String]): Unit ={
    //TODO
  }

  def add(args: Array[String]): Unit = {
    args match {
      case Array() => Printer.invalidArgumentsNumber()
      case _ =>
        if(args.length>1) Printer.invalidArgumentsNumber()
        else SGITAdd(args(0))
    }
  }

  def commit(args: Array[String]): Unit = {
    args match {
      case Array() => SGITCommit()
      case _ =>
        args(0) match {
          case "-m" => {
            if(args.length==2)
              SGITCommit(message = args(1))
            else
              Printer.invalidArgumentsNumber()
          }
          case _ => Printer.unknownArgument()
        }
    }
  }

  def log(args: Array[String]): Unit = {
    args match {
      case _ => println("todo")
      //TODO
    }
  }



}