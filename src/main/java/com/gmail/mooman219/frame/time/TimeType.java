package com.gmail.mooman219.frame.time;

public enum TimeType {
    MILLISECOND (0, "millisecond",    1l, true),
    TICK        (1, "tick",          50l, false),
    SECOND      (2, "second",      1000l, true),
    MINUTE      (3, "minute",     60000l, true),
    HOUR        (4, "hour",     3600000l, true),
    DAY         (5, "day",     86400000l, true),
    WEEK        (6, "week",   604800000l, true),
    MONTH       (7, "month", 2629800000l, true),
    YEAR        (8, "year", 31557600000l, true);

    public final int index;
    public final String name;
    public final long modifier;
    public final boolean basic;

    private TimeType(int index, String name, long modifier, boolean basic) {
        this.index = index;
        this.name = name;
        this.modifier = modifier;
        this.basic = basic;
    }
}
