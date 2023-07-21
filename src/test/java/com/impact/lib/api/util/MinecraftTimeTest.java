package com.impact.lib.api.util;

class MinecraftTimeTest {

    public static void main(String[] args) {
        MinecraftTime minecraftTime = new MinecraftTime(MinecraftTime.Unit.MINUTE, 3);
        System.out.println(minecraftTime);
        System.out.println(minecraftTime.getTicks());
        System.out.println(minecraftTime.getSeconds());

        final MinecraftTime time1 = MinecraftTime.weeks(1);
        System.out.println(time1);
        System.out.println(time1.getTicks());
        final MinecraftTime time2 = MinecraftTime.seconds(1L);
        System.out.println(time2);
        System.out.println(time2.getTicks());
        System.out.println(time1.equals(time2));

        MinecraftTime time3 = MinecraftTime.ticks(20);
        MinecraftTime time4 = MinecraftTime.seconds(1);
        System.out.println(time3);
        System.out.println(time4);
        System.out.println(time3.hashCode());
        System.out.println(time4.hashCode());
        System.out.println(time3.equals(time4));
    }
}