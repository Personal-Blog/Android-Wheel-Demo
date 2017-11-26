package com.github.angads25.androidwheel;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * <p>
 * Created by Angad Singh on 22/11/17.
 * </p>
 */

public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.PickerViewHolder> {
    private Context context;
    private ArrayList<DateBean> strings;

    public PickerAdapter(Context context, ArrayList<DateBean> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public PickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_date_time, parent, false);
        return new PickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PickerViewHolder holder, int position) {
        holder.tvData.setText(strings.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    class PickerViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvData;

        PickerViewHolder(View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tv_data);
        }
    }
}
