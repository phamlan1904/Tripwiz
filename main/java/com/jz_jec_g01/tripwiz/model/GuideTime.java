package com.jz_jec_g01.tripwiz.model;

import java.time.LocalTime;

public class GuideTime {
    private int weekday; //案内可能曜日変数
    private LocalTime startTime;
    private LocalTime endTime;

    public int getWeekday() {
        return weekday;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }
}
