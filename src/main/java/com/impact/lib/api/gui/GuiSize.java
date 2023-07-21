package com.impact.lib.api.gui;

/**
 * Represents available GUI size enum.
 */
public enum GuiSize {

    SLOTS_9(9),
    SLOTS_18(18),
    SLOTS_27(27),
    SLOTS_36(36),
    SLOTS_45(45),
    SLOTS_54(54),

    NINE(9),
    EIGHTEEN(18),
    TWENTY_SEVEN(27),
    THIRTY_SIX(36),
    FORTY_FIVE(45),
    FIFTY_FOUR(54),

    ONE_ROW(9),
    TWO_ROWS(18),
    THREE_ROWS(27),
    FOUR_ROWS(36),
    FIVE_ROWS(45),
    SIX_ROWS(54);

    private final int slots;

    GuiSize(int slots) {
        this.slots = slots;
    }

    public int getSlotCount() {
        return slots;
    }
}
