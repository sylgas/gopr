package com.springapp.mvc.dto;

import java.util.List;

public class TmpLayerDto {
    private List<LayerDto> geometries;

    public TmpLayerDto(List<LayerDto> geometries) {
        this.geometries = geometries;
    }

    public List<LayerDto> getGeometries() {
        return geometries;
    }

    public void setGeometries(List<LayerDto> geometries) {
        this.geometries = geometries;
    }
}
