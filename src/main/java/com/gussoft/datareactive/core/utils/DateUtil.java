package com.gussoft.datareactive.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

  private static DateUtil instance;

  public static DateUtil getInstance() {
    if (instance == null) {
      synchronized (DateUtil.class) {
        if (instance == null) {
          instance = new DateUtil();
        }
      }
    }
    return instance;
  }

  public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
    return LocalDateTime.ofInstant(
      dateToConvert.toInstant(), ZoneId.systemDefault());
  }

  public LocalDate convertToLocalDate(Date dateToConvert) {
    return LocalDate.ofInstant(
      dateToConvert.toInstant(), ZoneId.systemDefault());
  }

  public int calculateAge(String birthDate) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate birthday = LocalDate.parse(birthDate, format);
    return Period.between(birthday,LocalDate.now()).getYears();
  }

  /*
    format: LocalDate.of(1989, 3, 27)
   */
  public int calculateAge(LocalDate birthDate) {
    return Period.between(birthDate, LocalDate.now()).getYears();
  }


}