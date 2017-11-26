package com.github.angads25.androidwheel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private String[] months = {
            "Jan", "Feb", "Mar",
            "Apr", "May", "Jun",
            "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"
    };

    private AppCompatTextView dateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Maximum visible items in WheelPicker as a time.
        int maxItems = 3;

        // We will adjust height of recyclerView to fit
        // exactly maximum visible items.
        int recyclerViewHeight = 0;

        TypedValue tv = new TypedValue();

        if (getTheme().resolveAttribute(android.R.attr.listPreferredItemHeight, tv, true)) {

            // Calculating recyclerView height from listItemHeight
            // we are using in our recyclerView list item.
            recyclerViewHeight = maxItems * TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        dateTv = findViewById(R.id.tv_date);

        final RecyclerView dateList = findViewById(R.id.date_picker);
        LinearLayoutCompat.LayoutParams dateParams = (LinearLayoutCompat.LayoutParams) dateList.getLayoutParams();
        dateParams.height = recyclerViewHeight;
        dateList.setLayoutParams(dateParams);

        final ArrayList<DateBean> arrayList = new ArrayList<>();

        // Adding blank field in beginning of list so that
        // first date item is selectable.
        DateBean padding = new DateBean();
        padding.setDate("");
        arrayList.add(padding);

        // Adding 7 consecutive days to date picker.
        Calendar calendar = Calendar.getInstance();
        for(int i = 0; i < 7; i++) {
            DateBean date = new DateBean();
            date.setDate(
                    months[calendar.get(Calendar.MONTH)] + " " +
                    calendar.get(Calendar.DAY_OF_MONTH) + ", " +
                    calendar.get(Calendar.YEAR));
            date.setTimeStamp(calendar.getTimeInMillis());
            arrayList.add(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Adding blank field in last so that last item is
        // selectable.
        arrayList.add(padding);

        PickerAdapter adapter = new PickerAdapter(MainActivity.this, arrayList);

        final LinearLayoutManager manager = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.VERTICAL,
                false);


        final LinearSnapHelper snapHelper = new LinearSnapHelper();

        // Attaching SnapHelper to recyclerView.
        snapHelper.attachToRecyclerView(dateList);

        // Setting layout manager to recyclerView.
        dateList.setLayoutManager(manager);

        //
        dateList.setOnFlingListener(snapHelper);

        // Setting adapter to recyclerView.
        dateList.setAdapter(adapter);

        // Listening to scroll events on RecyclerView.
        dateList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(manager);

                    // Getting position of the center/snapped item.
                    int pos = manager.getPosition(centerView);
                    dateTv.setText(arrayList.get(pos).getDate());
                }
            }
        });
    }
}
