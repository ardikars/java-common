package com.ardikars.common.logging;

import com.ardikars.common.annotation.Incubating;

@Incubating
class NoLogger implements Logger {

    private final String name;

    NoLogger(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isEnabled(LogLevel level) {
        return false;
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void log(LogLevel level, String message) {

    }

    @Override
    public void log(LogLevel level, String format, Object arg1) {

    }

    @Override
    public void log(LogLevel level, String format, Object arg1, Object arg2) {

    }

    @Override
    public void log(LogLevel level, String format, Object... args) {

    }

    @Override
    public void log(LogLevel level, Throwable throwable) {

    }

    @Override
    public void log(LogLevel level, String message, Throwable throwable) {

    }

    @Override
    public void debug(String message) {

    }

    @Override
    public void debug(Throwable throwable) {

    }

    @Override
    public void debug(String format, Object arg1) {

    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {

    }

    @Override
    public void debug(String format, Object... args) {

    }

    @Override
    public void debug(String message, Throwable throwable) {

    }

    @Override
    public void info(String message) {

    }

    @Override
    public void info(Throwable throwable) {

    }

    @Override
    public void info(String format, Object obj1) {

    }

    @Override
    public void info(String format, Object obj1, Object obj2) {

    }

    @Override
    public void info(String format, Object... args) {

    }

    @Override
    public void info(String message, Throwable throwable) {

    }

    @Override
    public void warn(String message) {

    }

    @Override
    public void warn(Throwable throwable) {

    }

    @Override
    public void warn(String format, Object arg1) {

    }

    @Override
    public void warn(String format, Object arg1, Object obj2) {

    }

    @Override
    public void warn(String format, Object... args) {

    }

    @Override
    public void warn(String message, Throwable throwable) {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void error(Throwable throwable) {

    }

    @Override
    public void error(String format, Object obj1) {

    }

    @Override
    public void error(String format, Object obj1, Object obj2) {

    }

    @Override
    public void error(String format, Object... args) {

    }

    @Override
    public void error(String message, Throwable throwable) {

    }

}
