package de.bse.prgm.cmd.control.power;

import de.bse.prgm.cmd.ICommand;
import de.bse.run.app.IConsole;
import de.bse.vm.Machine;

/**
 * Terminates the execution of the actual program.
 * 
 * @author Elias Groll
 * @version 2.15
 */
public class End implements ICommand {

  
  public void execute(Machine machine, IConsole console) {
    machine.setExecutionIndex(machine.getProgram().getCommands().size() - 1);
  }

  
  public String infoMsg() {
    return "[Info]Terminating execution";
  }

  
  public String toString() {
    return "END";
  }

}
