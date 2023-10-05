package org.datayoo.opipe.operation;

import org.datayoo.moql.EntityMap;
import org.datayoo.moql.EntityMapImpl;
import org.datayoo.moql.Operand;
import org.datayoo.moql.operand.function.AbstractFunction;
import org.datayoo.opipe.Operation;
import org.datayoo.opipe.utils.RegexUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegParse extends AbstractFunction implements Operation {

  public static final String FUNCTION_NAME = "regParse";

  protected String field;

  protected Pattern pattern;

  protected List<String> groupNames;

  public RegParse(List<Operand> parameters) {
    super(FUNCTION_NAME, 2, parameters);
    Operand operand = parameters.get(0);
    field = operand.operate((EntityMap) null).toString();
    operand = parameters.get(1);
    String regex = operand.operate((EntityMap) null).toString();
    pattern = Pattern.compile(regex);
    groupNames = RegexUtils.extractGroupNames(regex);
  }

  @Override
  protected Object innerOperate(EntityMap entityMap) {
    String data = (String) entityMap.getEntity(field);
    return innerOperate(data);
  }

  protected Object innerOperate(String data) {
    if (data == null)
      throw new IllegalArgumentException(
          String.format("There is no data of field '%s'!", field));
    Matcher matcher = pattern.matcher(data);
    if (matcher.find()) {
      EntityMap dataMap = new EntityMapImpl();
      if (groupNames != null) {
        for (String name : groupNames) {
          dataMap.putEntity(name, matcher.group(name));
        }
      } else {
        for (int i = 1; i <= matcher.groupCount(); i++) {
          dataMap.putEntity(String.valueOf(i), matcher.group(i));
        }
      }
      dataMap.putEntity(field, data);
      return dataMap;
    }
    return null;
  }

  @Override
  protected Object innerOperate(Object[] entityArray) {
    String data = (String) entityMap.getEntity(field);
    return innerOperate(data);
  }

  @Override
  public Object operate(Object in) {
    return operate((EntityMap) in);
  }
}
