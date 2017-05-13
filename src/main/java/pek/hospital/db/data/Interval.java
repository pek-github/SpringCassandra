package pek.hospital.db.data;

import java.time.Instant;


public class Interval {
    private Instant from;
    private Instant to;
    private Integer minutesStep;

    public Interval() {
    }

    public Interval(Instant from, Instant to, Integer minutesStep) {
        this.from = from;
        this.to = to;
        this.minutesStep = minutesStep;
    }

    public Instant getFrom() {
        return from;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public Instant getTo() {
        return to;
    }

    public void setTo(Instant to) {
        this.to = to;
    }

    public Integer getMinutesStep() {
        return minutesStep;
    }

    public void setMinutesStep(Integer minutesStep) {
        this.minutesStep = minutesStep;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Interval{");
        sb.append("from=").append(from);
        sb.append(", to=").append(to);
        sb.append(", minutesStep=").append(minutesStep);
        sb.append('}');
        return sb.toString();
    }

}
