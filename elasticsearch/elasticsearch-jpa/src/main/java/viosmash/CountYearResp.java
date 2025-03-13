package viosmash;

public class CountYearResp {
    private Integer year;
    private Integer count;

    public CountYearResp(Integer year, Integer count) {
        this.year = year;
        this.count = count;
    }
    public CountYearResp() {

    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
