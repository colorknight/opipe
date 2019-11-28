package org.datayoo.opipe.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexUtils {

  public static Pattern pattern = Pattern.compile("\\?\\<([\\w\\._]+)\\>");

  public static List<String> extractGroupNames(String regExp) {
    Matcher matcher = pattern.matcher(regExp);
    List<String> groupNames = new LinkedList<>();
    while (matcher.find()) {
      String name = matcher.group(1);
      if (isValidateName(name))
        groupNames.add(name);
    }
    return groupNames.size() == 0 ? null : groupNames;
  }

  protected static boolean isValidateName(String data) {
    // 首字母不能为数字
    if (Character.isDigit(data.charAt(0)))
      return false;
    char prev = 0;
    for (int i = 0; i < data.length(); i++) {
      char ch = data.charAt(i);
      if (ch == prev && prev == '.') {
        return false;
      }
      prev = ch;
    }
    return true;
  }
}
