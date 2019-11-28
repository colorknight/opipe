package org.datayoo.opipe.core;

import org.apache.commons.lang3.Validate;
import org.datayoo.moql.MoqlException;
import org.datayoo.moql.Operand;
import org.datayoo.moql.engine.MoqlEngine;
import org.datayoo.opipe.Operation;
import org.datayoo.opipe.function.StrFormat;
import org.datayoo.opipe.operation.Assignment;
import org.datayoo.opipe.operation.PrintOut;
import org.datayoo.opipe.operation.RegParse;

public abstract class OpipeBuilder {

  public static String SYMBOL_PIPE = "\\|";

  static {
    MoqlEngine.registFunction(RegParse.FUNCTION_NAME, RegParse.class.getName());
    MoqlEngine
        .registFunction(Assignment.FUNCTION_NAME, Assignment.class.getName());
    MoqlEngine.registFunction(PrintOut.FUNCTION_NAME, PrintOut.class.getName());
    MoqlEngine
        .registFunction(StrFormat.FUNCTION_NAME, StrFormat.class.getName());
  }

  public static Opipe createOpipe(String pipeline) throws MoqlException {
    Validate.notEmpty(pipeline, "pipeline is empty!");
    String[] expressions = pipeline.split(SYMBOL_PIPE);
    Opipe opipe = new Opipe();
    for (int i = 0; i < expressions.length; i++) {
      String expression = expressions[i].trim();
      if (expression == null)
        continue;
      opipe.getOperations().add(createOperation(expression));
    }
    return opipe;
  }

  public static Operation createOperation(String expression)
      throws MoqlException {
    Operand operand = MoqlEngine.createOperand(expression);
    return (Operation) operand;
  }

  public static String registFunction(String name, String className) {
    return MoqlEngine.registFunction(name, className);
  }

}
