# SGIT
## A Scala git-like

## Installation:
> This project uses sbt assembly & scala version 2.13.1

Please perform this set of commands to enable SGIT

* `git clone https://github.com/Franceq34/SGIT.git`
* `cd SGIT`
* `source install.sh`

The SGIT instance will be available while this terminal is opened.
## Test Use-cases

* `mkdir SGITtest`
* `cd SGITtest`
* `clear`
* `sgit init`
* `touch lapin`
* `sgit status`
* `sgit add lapin`
* `sgit status`
* `sgit commit -m "Premier"`
* `sgit status`
* `sgit log`
* `sgit log -p`
* `echo "Ligne1" > lapin`
* `echo "Ligne2" >> lapin`
* `echo "Ligne3" >> lapin`
* `sgit diff`
* `sgit add .`
* `sgit diff`
* `sgit status`
* `sgit commit -m "Second`
* `sgit status`
* `sgit log`
* `sgit log -p`
