package edu.softserve.zoo.model;

/**
 * Created by Taras on 11.05.2016.
 */
public class Statistics {
    private Long animalsCount;
    private Long housesCount;
    private Long employeesCount;

    public Statistics(Long animalsCount, Long housesCount, Long employeesCount) {
        this.animalsCount = animalsCount;
        this.housesCount = housesCount;
        this.employeesCount = employeesCount;
    }

    public Long getAnimalsCount() {
        return animalsCount;
    }

    public void setAnimalsCount(Long animalsCount) {
        this.animalsCount = animalsCount;
    }

    public Long getHousesCount() {
        return housesCount;
    }

    public void setHousesCount(Long housesCount) {
        this.housesCount = housesCount;
    }

    public Long getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(Long employeesCount) {
        this.employeesCount = employeesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return animalsCount.equals(that.animalsCount) && housesCount.equals(that.housesCount) && employeesCount.equals(that.employeesCount);
    }

    @Override
    public int hashCode() {
        int result = animalsCount.hashCode();
        result = 31 * result + housesCount.hashCode();
        result = 31 * result + employeesCount.hashCode();
        return result;
    }
}
