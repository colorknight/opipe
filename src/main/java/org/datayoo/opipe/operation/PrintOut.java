package org.datayoo.opipe.operation;

import org.datayoo.moql.EntityMap;
import org.datayoo.moql.Operand;
import org.datayoo.moql.operand.function.AbstractFunction;
import org.datayoo.opipe.Operation;

import java.util.List;

public class PrintOut extends AbstractFunction implements Operation {

  public static final String FUNCTION_NAME = "printOut";

  public PrintOut(List<Operand> parameters) {
    super(FUNCTION_NAME, 1, parameters);
  }

  @Override
  protected Object innerOperate(EntityMap entityMap) {
    Object v = parameters.get(0).operate(entityMap);
    System.out.println(v.toString());
    return null;
  }

  @Override
  public Object operate(Object in) {
    return operate((EntityMap) in);
  }
}
