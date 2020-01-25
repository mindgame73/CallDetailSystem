package ru.niiar.oracleSpringTest.dto;

public class DivisionCountDto {
    private String divisionName;

    private Integer totalCount;

    public DivisionCountDto() {
    }

    public DivisionCountDto(String divisionName, Integer totalCount) {
        this.divisionName = divisionName;
        this.totalCount = totalCount;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public Integer getTotalCount() {
        return totalCount;
    }
}
