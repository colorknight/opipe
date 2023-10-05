package org.datayoo.opipe.operation;

import org.datayoo.moql.EntityMap;
import org.datayoo.moql.Operand;
import org.datayoo.moql.operand.function.AbstractFunction;
import org.datayoo.opipe.Operation;

import java.util.List;

public class Assignment extends AbstractFunction implements Operation {

  public static final String FUNCTION_NAME = "assignment";

  protected String field;

  public Assignment(List<Operand> parameters) {
    super(FUNCTION_NAME, 2, parameters);
    Operand operand = parameters.get(0);
    field = operand.getName();
  }

  @Override
  protected Object innerOperate(EntityMap entityMap) {
    entityMap.putEntity(field, parameters.get(1).operate(entityMap));
    return entityMap;
  }

  @Override
  protected Object innerOperate(Object[] entityArray) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object operate(Object in) {
    return operate((EntityMap) in);
  }
}
