package edu.softserve.zoo.model;

/**
 * Created by Taras on 11.05.2016.
 */
public class Statistics {
    private Integer animalsCount;
    private Integer housesCount;
    private Integer employeesCount;

    public Statistics(Integer animalsCount, Integer housesCount, Integer employeesCount) {
        this.animalsCount = animalsCount;
        this.housesCount = housesCount;
        this.employeesCount = employeesCount;
    }

    public Integer getAnimalsCount() {
        return animalsCount;
    }

    public void setAnimalsCount(Integer animalsCount) {
        this.animalsCount = animalsCount;
    }

    public Integer getHousesCount() {
        return housesCount;
    }

    public void setHousesCount(Integer housesCount) {
        this.housesCount = housesCount;
    }

    public Integer getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(Integer employeesCount) {
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
