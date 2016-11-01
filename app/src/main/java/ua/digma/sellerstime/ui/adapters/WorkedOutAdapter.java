package ua.digma.sellerstime.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.digma.sellerstime.R;
import ua.digma.sellerstime.storage.db.entity.TimeTrackingEntity;

public class WorkedOutAdapter extends BaseAdapter {
    private Context context;
    private List<TimeTrackingEntity> timeTrackingList;

    public WorkedOutAdapter(Context context) {
        this.context = context;
        timeTrackingList = new ArrayList<>();
    }

    public void setTimeTrackingList(List<TimeTrackingEntity> timeTrackingList) {
        this.timeTrackingList = timeTrackingList;
    }

    @Override
    public int getCount() {
        return timeTrackingList.size();
    }

    @Override
    public Object getItem(int position) {
        return timeTrackingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.worked_time_item, parent, false);
        }
        TimeTrackingEntity tracking = timeTrackingList.get(position);
        TextView tvStart = (TextView) v.findViewById(R.id.tv_start);
        tvStart.setText(tracking.getDateStart());
        TextView tvFinish = (TextView) v.findViewById(R.id.tv_finish);
        tvFinish.setText(tracking.getDateFinish());
        TextView tvFio = (TextView) v.findViewById(R.id.tv_fio);
        tvFio.setText(tracking.getFio());
        TextView tvWorkedOut = (TextView) v.findViewById(R.id.tv_worked_out);
        tvWorkedOut.setText(tracking.getWorkedOut());
        TextView tvWarehouse = (TextView) v.findViewById(R.id.tv_warehouse);
        tvWarehouse.setText(tracking.getWarehouseName());
        return v;
    }
}
