package org.datayoo.opipe.function;

import org.datayoo.moql.EntityMap;
import org.datayoo.moql.Operand;
import org.datayoo.moql.operand.function.AbstractFunction;
import org.datayoo.opipe.Operation;

import java.util.List;

public class StrFormat extends AbstractFunction {

  public static final String FUNCTION_NAME = "strFormat";

  protected String format;

  public StrFormat(List<Operand> parameters) {
    super(FUNCTION_NAME, VARIANT_PARAMETERS, parameters);
    if (parameters.size() < 2)
      throw new IllegalArgumentException(
          "Invalid format, please refer to the using of String.format!");
    Operand operand = parameters.get(0);
    format = operand.operate(null).toString();
  }

  @Override
  protected Object innerOperate(EntityMap entityMap) {
    String[] values = new String[parameters.size() - 1];
    int index = 0;
    for (Operand operand : parameters) {
      if (index > 0) {
        values[index - 1] = operand.operate(entityMap).toString();
      }
      index++;
    }
    return String.format(format, values);
  }

}
