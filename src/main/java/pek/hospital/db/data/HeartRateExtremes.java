package pek.hospital.db.data;


public class HeartRateExtremes {

    private Integer min;
    private Integer max;

    public HeartRateExtremes(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HeartRateExtremes{");
        sb.append("min=").append(min);
        sb.append(", max=").append(max);
        sb.append('}');
        return sb.toString();
    }

}
