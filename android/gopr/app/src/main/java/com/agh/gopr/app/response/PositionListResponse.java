package com.agh.gopr.app.response;

import java.util.List;

public class PositionListResponse {
    private long userInActionId;
    private List<PositionDto> positions;

    public PositionListResponse() {
    }

    public PositionListResponse(long userInActionId, List<PositionDto> positions) {
        this.userInActionId = userInActionId;
        this.positions = positions;
    }

    public long getUserInActionId() {
        return userInActionId;
    }

    public void setUserInActionId(long userInActionId) {
        this.userInActionId = userInActionId;
    }

    public List<PositionDto> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionDto> positions) {
        this.positions = positions;
    }
}
