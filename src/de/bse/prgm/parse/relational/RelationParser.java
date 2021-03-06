package de.bse.prgm.parse.relational;

import de.bse.prgm.parse.arithmetical.ArithmeticalParser;
import de.bse.util.Finder;
import de.bse.vm.Machine;
import de.bse.vm.var.IVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * Parser which can parse relational terms
 * @author Elias Groll
 * @version 2.15
 */
public class RelationParser {
  private static Map<String, Relation> knownOperators = null;

  /**
   * Constructs a new RelationParser.
   */
  public RelationParser() {
    knownOperators = new HashMap<String, Relation>();
    knownOperators.put("=", new Equivalence());
    knownOperators.put(">", new Greater());
    knownOperators.put("<", new Less());
    knownOperators.put(">=", new GreaterOrEqual());
    knownOperators.put("<=", new LessOrEqual());
    knownOperators.put("!=", new InEquivalence());
  }

  /**
   * Parses a relational operation based on a given machine.
   * @param operation to be parsed
   * @param machine on which the operation shall be parsed
   * @return the result of the relational operation
   */
  public IVariable parseRelationalOperation(String operation, Machine machine) {
    // only one relational operator allowed
    Finder finder = findNextRelationalOperator(operation);
    ArithmeticalParser psr1 = new ArithmeticalParser();
    ArithmeticalParser psr2 = new ArithmeticalParser();
    if (finder != null) {
      Relation relation;
      String left = operation.substring(0, finder.beginIndex).trim();
      String right = operation.substring(finder.endIndex, operation.length())
          .trim();
      relation = knownOperators.get(operation.substring(finder.beginIndex,
          finder.endIndex));
      relation.init(psr1.parseArithmeticalOperation(left, machine),
          psr2.parseArithmeticalOperation(right, machine));
      return relation;

    } else {
      return psr1.parseArithmeticalOperation(operation, machine);
    }
  }

  private Finder findNextRelationalOperator(String string) throws RuntimeException {
    Finder retVal = null;
    int ambigous = 0;
    String tmp = string;
    for (String operator : knownOperators.keySet()) {
      if (tmp.contains(operator)) {
        ambigous++;
        retVal = new Finder(tmp.indexOf(operator), tmp.indexOf(operator)
            + operator.length());
        tmp = tmp.substring(0, tmp.indexOf(operator))
            + tmp.substring(tmp.indexOf(operator) + operator.length(),
                tmp.length());

      }
    }
    if (ambigous > 1) {
      throw new RuntimeException("ambigous op");
    }
    return retVal;
  }
}
