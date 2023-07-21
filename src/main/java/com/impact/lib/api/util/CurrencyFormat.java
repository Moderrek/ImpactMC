package com.impact.lib.api.util;

import java.util.Locale;

public record CurrencyFormat(String prefix, String suffix, Locale locale, boolean compact, int precision) {
}
