package org.datayoo.opipe;

import junit.framework.TestCase;
import org.datayoo.opipe.utils.RegexUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest extends TestCase {

  public void testQuote() {
    String data = "192.168.2.3 data1 data2";
    String pattern = Pattern.quote(data);
    System.out.println(pattern);
  }

  public void testGroupName() {
    String s = "2015-10-26";
    Pattern p = Pattern
        .compile("(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})");
    Matcher m = p.matcher(s);
    if (m.find()) {
      System.out.println("year: " + m.group("year")); //年
      System.out.println("month: " + m.group("month")); //月
      System.out.println("day: " + m.group("day")); //日
    }
  }

  public void testGroupNames() {
    String regex = "(?<year.2>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})";
    List<String> list = RegexUtils.extractGroupNames(regex);
    for (String data : list) {
      System.out.println(data);
    }
  }
}
