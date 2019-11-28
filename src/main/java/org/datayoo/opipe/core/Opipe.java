package org.datayoo.opipe.core;

import org.apache.commons.lang3.Validate;
import org.datayoo.moql.OperateException;
import org.datayoo.opipe.Operation;

import java.util.LinkedList;
import java.util.List;

public class Opipe implements Operation {

  protected List<Operation> operations = new LinkedList<Operation>();

  public Opipe() {
  }

  public Opipe(List<Operation> operations) {
    Validate.notEmpty(operations, "operatons is empty!");
    this.operations = operations;
  }

  @Override
  public Object operate(Object in) {
    for (Operation operation : operations) {
      try {
        in = operation.operate(in);
        if (in == null)
          return null;
      } catch (Throwable t) {
        throw new OperateException(String
            .format("Operation '%s' execution failed!", operation.toString()),
            t);
      }
    }
    return in;
  }

  public List<Operation> getOperations() {
    return operations;
  }
}
