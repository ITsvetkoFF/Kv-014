package edu.softserve.zoo.dto;

import java.util.Objects;

/**
 * @author Taras Zubrei
 */
public class StatisticsDto {
    private Long animalsCount;
    private Long housesCount;
    private Long employeesCount;

    public StatisticsDto(Long animalsCount, Long housesCount, Long employeesCount) {
        this.animalsCount = animalsCount;
        this.housesCount = housesCount;
        this.employeesCount = employeesCount;
    }

    public StatisticsDto() {}

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
        StatisticsDto that = (StatisticsDto) o;
        return animalsCount.equals(that.animalsCount) && housesCount.equals(that.housesCount) && employeesCount.equals(that.employeesCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalsCount, housesCount, employeesCount);
    }
}
