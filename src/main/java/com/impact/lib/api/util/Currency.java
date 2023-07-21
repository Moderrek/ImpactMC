package com.impact.lib.api.util;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

public final class Currency {

    public final static CurrencyFormat DEFAULT_FORMAT = new CurrencyFormat("$", "", Locale.US, false, 2);
    public final static CurrencyFormat DEFAULT_COMPACT_FORMAT = new CurrencyFormat("$", "", Locale.US, true, 2);

    public static @NotNull String format(final double money) {
        return format(money, DEFAULT_COMPACT_FORMAT);
    }

    public static @NotNull String format(final double money, @NotNull final CurrencyFormat format) {
        NumberFormat numberFormat = (format.compact() && money > 1000)
                ? NumberFormat.getCompactNumberInstance(format.locale(), NumberFormat.Style.SHORT)
                : NumberFormat.getNumberInstance(format.locale());
        numberFormat.setMaximumFractionDigits(format.precision());
        return format.prefix() +
                numberFormat.format(money) +
                format.suffix();
    }

}
