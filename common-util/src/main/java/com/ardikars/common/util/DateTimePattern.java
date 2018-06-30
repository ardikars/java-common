/**
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ardikars.common.util;

import com.ardikars.common.annotation.Helper;
import com.ardikars.common.annotation.Immutable;
import java.util.HashMap;
import java.util.Map;

/**
 * Date and time pattern.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Helper
@Immutable
public final class DateTimePattern {

    private static String SPACE_DELIMITER = " ";

    public static final String DEFAULT_PATTERN = DatePattern.DD_MM_YYYY_WITH_SPACE_AS_DELIMITER.getValue()
            + SPACE_DELIMITER
            + TimePattern.HH_MM_SS_WITH_SPACE_AS_DELIMITER.getValue();

    public static final class DatePattern extends NamedObject<String, DatePattern> {

        public static final DatePattern YYYY_MM_DD_WITH_MINUS_AS_DELIMITER = new DatePattern("yyyy-MM-dd", "Year-Month-Day");
        public static final DatePattern DD_MM_YYYY_WITH_MINUS_AS_DELIMITER = new DatePattern("dd-MM-yyyy", "Day-Month-Year");
        public static final DatePattern YYYY_MM_DD_WITH_SLASH_AS_DELIMITER = new DatePattern("yyyy/MM/dd", "Year/Month/Day");
        public static final DatePattern DD_MM_YYYY_WITH_SLASH_AS_DELIMITER = new DatePattern("dd/MM/yyyy", "Day-Month-Year");
        public static final DatePattern YYYY_MM_DD_WITH_SPACE_AS_DELIMITER = new DatePattern("yyyy MM dd", "Year Month Day");
        public static final DatePattern DD_MM_YYYY_WITH_SPACE_AS_DELIMITER = new DatePattern("dd MM yyyy", "Day Month Year");

        private static final Map<String, DatePattern> registry = new HashMap<>();

        public DatePattern(String value, String name) {
            super(value, name);
        }

        public static DatePattern register(DatePattern datePattern) {
            registry.put(datePattern.getValue(), datePattern);
            return datePattern;
        }

        public static DatePattern getDatePattern(String stringDatePattern) {
            return registry.getOrDefault(stringDatePattern, DD_MM_YYYY_WITH_SPACE_AS_DELIMITER);
        }

        static {
            registry.put(YYYY_MM_DD_WITH_MINUS_AS_DELIMITER.getValue(), YYYY_MM_DD_WITH_MINUS_AS_DELIMITER);
            registry.put(DD_MM_YYYY_WITH_MINUS_AS_DELIMITER.getValue(), DD_MM_YYYY_WITH_MINUS_AS_DELIMITER);
            registry.put(YYYY_MM_DD_WITH_SLASH_AS_DELIMITER.getValue(), YYYY_MM_DD_WITH_SLASH_AS_DELIMITER);
            registry.put(DD_MM_YYYY_WITH_SLASH_AS_DELIMITER.getValue(), DD_MM_YYYY_WITH_SLASH_AS_DELIMITER);
            registry.put(YYYY_MM_DD_WITH_SPACE_AS_DELIMITER.getValue(), YYYY_MM_DD_WITH_SPACE_AS_DELIMITER);
            registry.put(DD_MM_YYYY_WITH_SPACE_AS_DELIMITER.getValue(), DD_MM_YYYY_WITH_SPACE_AS_DELIMITER);
        }

    }

    public static final class TimePattern extends NamedObject<String, TimePattern> {

        public static final TimePattern HH_MM_SS_WITH_COLON_AS_DELIMITER = new TimePattern("hh:mm:ss", "Hour:Munite:Second");
        public static final TimePattern HH_MM_WITH_COLON_AS_DELIMITER = new TimePattern("hh:mm", "Hour:Munite");
        public static final TimePattern HH_MM_SS_WITH_MINUS_AS_DELIMITER = new TimePattern("hh-mm-ss", "Hour-Munite-Second");
        public static final TimePattern HH_MM_WITH_MINUS_AS_DELIMITER = new TimePattern("hh-mm", "Hour-Munite");
        public static final TimePattern HH_MM_SS_WITH_SLASH_AS_DELIMITER = new TimePattern("hh/mm/ss", "Hour/Munite/Second");
        public static final TimePattern HH_MM_WITH_SLASH_AS_DELIMITER = new TimePattern("hh/mm", "Hour/Munite");
        public static final TimePattern HH_MM_SS_WITH_SPACE_AS_DELIMITER = new TimePattern("hh mm ss", "Hour Munite Second");
        public static final TimePattern HH_MM_WITH_SPACE_AS_DELIMITER = new TimePattern("hh mm", "Hour Munite");

        private static final Map<String, TimePattern> registry = new HashMap<>();

        public TimePattern(String value, String name) {
            super(value, name);
        }

        public static TimePattern register(TimePattern timePattern) {
            registry.put(timePattern.getValue(), timePattern);
            return timePattern;
        }

        public static TimePattern getTimePattern(String stringTimePattern) {
            return registry.getOrDefault(stringTimePattern, HH_MM_SS_WITH_SPACE_AS_DELIMITER);
        }

        static {
            registry.put(HH_MM_SS_WITH_COLON_AS_DELIMITER.getValue(), HH_MM_SS_WITH_COLON_AS_DELIMITER);
            registry.put(HH_MM_WITH_COLON_AS_DELIMITER.getValue(), HH_MM_SS_WITH_COLON_AS_DELIMITER);
            registry.put(HH_MM_SS_WITH_MINUS_AS_DELIMITER.getValue(), HH_MM_SS_WITH_COLON_AS_DELIMITER);
            registry.put(HH_MM_WITH_MINUS_AS_DELIMITER.getValue(), HH_MM_SS_WITH_COLON_AS_DELIMITER);
            registry.put(HH_MM_SS_WITH_SLASH_AS_DELIMITER.getValue(), HH_MM_SS_WITH_COLON_AS_DELIMITER);
            registry.put(HH_MM_WITH_SLASH_AS_DELIMITER.getValue(), HH_MM_SS_WITH_COLON_AS_DELIMITER);
            registry.put(HH_MM_SS_WITH_SPACE_AS_DELIMITER.getValue(), HH_MM_SS_WITH_COLON_AS_DELIMITER);
            registry.put(HH_MM_WITH_SPACE_AS_DELIMITER.getValue(), HH_MM_SS_WITH_COLON_AS_DELIMITER);
        }

    }

    private String pattern;

    private DateTimePattern(Builder builder) {

        Validate.nullPointer(builder, new NullPointerException("DateTime builder should be not null."));

        if (builder.datePattern == null && builder.timePattern == null) {
            this.pattern = DEFAULT_PATTERN;
        } else if (builder.datePattern != null && builder.timePattern != null) {
            if (builder.timeBeforeDate == false) {
                this.pattern = builder.datePattern.getValue()
                        + SPACE_DELIMITER
                        + builder.timePattern.getValue();
            } else {
                this.pattern = builder.timePattern.getValue()
                        + SPACE_DELIMITER
                        + builder.datePattern.getValue();
            }
        } else if (builder.datePattern != null && builder.timePattern == null) {
            this.pattern = builder.datePattern.getValue();
        } else if (builder.datePattern == null && builder.timePattern != null) {
            this.pattern = builder.timePattern.getValue();
        } else {
            this.pattern = DEFAULT_PATTERN;
        }

    }

    public String getPattern() {
        return pattern;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements com.ardikars.common.util.Builder<DateTimePattern, Void> {

        private DatePattern datePattern;
        private TimePattern timePattern;
        private boolean timeBeforeDate;

        public Builder datePattern(DatePattern datePattern) {
            this.datePattern = datePattern;
            return this;
        }

        public Builder timePattern(TimePattern timePattern) {
            this.timePattern = timePattern;
            return this;
        }

        public Builder timeBeforeDate(boolean timeBeforeDate) {
            this.timeBeforeDate = timeBeforeDate;
            return this;
        }

        @Override
        public DateTimePattern build() {
            return new DateTimePattern(this);
        }

        @Override
        public DateTimePattern build(Void value) {
            throw new UnsupportedOperationException("");
        }

    }

}
