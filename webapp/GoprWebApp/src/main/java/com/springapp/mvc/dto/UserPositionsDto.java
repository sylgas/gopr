package com.springapp.mvc.dto;

import java.util.*;

public class UserPositionsDto {
    private Long userInActionId;
    private List<PositionDto> positions;

    public UserPositionsDto() {}

    public UserPositionsDto(Long userInActionId, List<PositionDto> positions) {
        this.userInActionId = userInActionId;
        this.positions = positions;
    }

    public void setUserInActionId(Long userInActionId) {
        this.userInActionId = userInActionId;
    }

    public void setPositions(List<PositionDto> positions) {
        this.positions = positions;
    }
}
