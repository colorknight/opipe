package org.datayoo.opipe.core;

import junit.framework.TestCase;
import org.datayoo.moql.EntityMap;
import org.datayoo.moql.EntityMapImpl;
import org.datayoo.moql.MoqlException;

import java.util.regex.Pattern;

public class OpipeTest extends TestCase {

  public void testOpipe1() {
    String s = "2019-10-26 192.1.32.4:8081";
    String pipeline =
        "regParse('data','(?<time>[^\\s]+)\\s(?<ip>[^:]+):(?<port>\\d+)') "
            + "| assignment(ipAndPort, strFormat('%s:%s', ip, port)) "
            + "| printOut(strFormat('%s %s', ipAndPort, time))";
    try {
      Opipe opipe = OpipeBuilder.createOpipe(pipeline);
      EntityMap entityMap = new EntityMapImpl();
      entityMap.putEntity("data", s);
      opipe.operate(entityMap);
    } catch (MoqlException e) {
      e.printStackTrace();
    }
  }
}
