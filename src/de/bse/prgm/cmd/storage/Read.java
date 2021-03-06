package de.bse.prgm.cmd.storage;

import de.bse.prgm.err.runtime.CannotChangeAConstantValueRuntimeError;
import de.bse.run.app.IConsole;
import de.bse.util.ParserException;
import de.bse.vm.Machine;

/**
 * Reads a value on a location in EEPROM and stores it into a variable.
 * 
 * @author Elias Groll
 * @version 2.15
 */
public class Read extends AccessCommand {

  public Read(String locationString, String varString) {
    super(locationString, varString);
  }

  
  public void execute(Machine machine, IConsole console) {
    initVars(machine, console);
    try {
      var.setValue(machine.getEeprom().read(location.getValue()));
    } catch (ParserException e) {
      machine.getProgram().addError(new CannotChangeAConstantValueRuntimeError(varString));
    } catch (NullPointerException e) {
      // nothing to do here, machine already added a Error
    }

  }

  
  public String infoMsg() {
    return "[Info]Read from \"" + locationString + "\"  and store it at \"" + varString
        + "\" in EEPROM";
  }

}
