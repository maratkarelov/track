package ua.digma.sellerstime.ui.activities.timetracking;


import java.util.List;

import ua.digma.sellerstime.storage.db.entity.TimeTrackingEntity;

public interface ITimeTrackingView {
    void updateSellersTrackingList(List<TimeTrackingEntity> timeTrackingList);
    void updateWorkedOut();
}
