package com.nurbol.android.tempmonitor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdviceAdapter extends ArrayAdapter<Advice> {
    public AdviceAdapter(Activity context, ArrayList<Advice> advices) {
        super(context, 0, advices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_advice, parent, false);
        }
        Advice currentAdvice = getItem(position);


        String roomNumber = currentAdvice.getRoom();
        TextView roomNumberView = (TextView) listItemView.findViewById(R.id.room_advice);
        roomNumberView.setText("Room " + roomNumber);

        String desc = currentAdvice.getDescription();
        TextView descView = (TextView) listItemView.findViewById(R.id.desc_advice);
        descView.setText(desc);

        String dateTime = currentAdvice.getDate();

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date dateReal = null;
        try {
            dateReal = fmt.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat fmtOutDate = new SimpleDateFormat("dd.MM.yyyy");
        String dateWithZone = fmtOutDate.format(dateReal);
        TextView dateView = (TextView) listItemView.findViewById(R.id.date_advice);
        dateView.setText(dateWithZone);

        SimpleDateFormat fmtOutTime = new SimpleDateFormat("HH:mm");
        String timeWithZone = fmtOutTime.format(dateReal);
        TextView timeView = (TextView) listItemView.findViewById(R.id.time_advice);
        timeView.setText(timeWithZone);

        return listItemView;

    }
}
