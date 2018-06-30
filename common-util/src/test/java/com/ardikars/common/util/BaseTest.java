package com.ardikars.common.util;

import com.ardikars.common.util.DateTimePattern.DatePattern;
import com.ardikars.common.util.DateTimePattern.TimePattern;
import java.text.Format;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class BaseTest {

    @Test
    public void doNoting() {
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern(
                        DateTimePattern.builder()
                            .datePattern(DatePattern.DD_MM_YYYY_WITH_MINUS_AS_DELIMITER)
                            .timePattern(TimePattern.HH_MM_SS_WITH_COLON_AS_DELIMITER)
                                .timeBeforeDate(true)
                            .build().getPattern()
        );
        Format format = formatter.toFormat();
        System.out.println(format.format(ZonedDateTime.now()));
    }

}
