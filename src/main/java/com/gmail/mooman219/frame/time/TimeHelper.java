package com.gmail.mooman219.frame.time;

import com.gmail.mooman219.frame.text.TextHelper;

public class TimeHelper {
    public static long convert(long time, TimeType from, TimeType to) {
        if(from.getID() == to.getID()) {
            return time;
        } else {
            return ((time * from.getModifier()) / to.getModifier());
        }
    }

    public static TimeType getType(String message) {
        String lMessage = TextHelper.spacePattern.split(message.toLowerCase(), 2)[0];
        switch (lMessage) {
        case "milliseconds":
        case "millisecond":
        case "milli":
        case "mills":
        case "mill":
        case "mil":
        case "ms":
            return TimeType.MILLISECOND;
        case "ticks":
        case "tick":
        case "tock":
        case "tic":
        case "ti":
        case "t":
            return TimeType.TICK;
        case "seconds":
        case "second":
        case "sec":
        case "s":
            return TimeType.SECOND;
        case "minutes":
        case "minute":
        case "min":
        case "m":
            return TimeType.MINUTE;
        case "hours":
        case "hour":
        case "hr":
        case "h":
            return TimeType.HOUR;
        case "days":
        case "day":
        case "da":
        case "d":
            return TimeType.DAY;
        case "weeks":
        case "week":
        case "we":
        case "w":
            return TimeType.WEEK;
        case "months":
        case "month":
        case "mon":
        case "mo":
            return TimeType.MONTH;
        case "years":
        case "year":
        case "ye":
        case "y":
            return TimeType.YEAR;
        default:
            return TimeType.MILLISECOND;
        }
    }

    public static TimeString getLargestType(float time, TimeType type) {
        time = time * type.getModifier();
        for(int i = TimeType.values().length - 1; i >= 0; i--) {
            if(time >= TimeType.values()[i].getModifier() && TimeType.values()[i].isBasic()) {
                return new TimeString(TimeType.values()[i], (float) (Math.floor((time / TimeType.values()[i].getModifier()) * 10) / 10));
            }
        }
        return new TimeString(TimeType.MILLISECOND, time);
    }

    public static long time() {
        return System.currentTimeMillis();
    }
}
