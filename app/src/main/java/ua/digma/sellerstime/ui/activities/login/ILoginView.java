package ua.digma.sellerstime.ui.activities.login;


import java.util.List;

import ua.digma.sellerstime.storage.db.entity.TimeTrackingEntity;
import ua.digma.sellerstime.ui.simplecore.IBaseView;

public interface ILoginView extends IBaseView{
    void updateSellersTrackingList(List<TimeTrackingEntity> timeTrackingList);
    void onEnteredPassword(String password);
    void onCancel();
}
