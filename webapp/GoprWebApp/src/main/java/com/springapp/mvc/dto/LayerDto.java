package com.springapp.mvc.dto;

public class LayerDto {
    private int areaId;
    private String area;
    private String name;

    public LayerDto(int areaId, String area, String name) {
        this.areaId = areaId;
        this.area = area;
        this.name = name;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

