package de.bse.prgm.err;

public class ShebangMissingError implements IError {

  
  public String errorMsg() {
    return "[Error, line 1]Couldnt detect a valid stamp -> ' {$STAMP BS1}";
  }

}
