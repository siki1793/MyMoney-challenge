package com.saikrishna.mymoney.constants;

import java.util.Arrays;
import java.util.List;

public enum Month {
  JANUARY,
  FEBRUARY,
  MARCH,
  APRIL,
  MAY,
  JUNE,
  JULY,
  AUGUST,
  SEPTEMBER,
  OCTOBER,
  NOVEMBER,
  DECEMBER;

  public static List<Month> getMonthList() {
    return Arrays.asList(Month.values());
  }

  public static Month getPreviousMonth(Month month) {
    int previousIndex = getMonthIndex(month) - 1;
    return previousIndex < 0 ? DECEMBER : getMonthList().get(previousIndex);
  }

  public static int getMonthIndex(Month month) {
    return getMonthList().indexOf(month);
  }

  public static Month getMonth(String month) {
    try {
      return Month.valueOf(month);
    } catch (Exception ex) {
      return null;
    }
  }
}
