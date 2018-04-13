package com.cloudjibe.android_googlecloudfirestore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class SensorDataListAdaptor extends RecyclerView.Adapter<SensorDataListAdaptor.ViewHolder> {
    public List<SensorDataModel> sensorDataModelList ;

    public SensorDataListAdaptor(List<SensorDataModel> sensorDataList) {
        this.sensorDataModelList = sensorDataList;
    }

//    public void setSensorDataModelList(List<SensorDataModel> sensorDataModelList) {
//        this.sensorDataModelList = sensorDataModelList;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.humidityText.setText(sensorDataModelList.get(position).getHumidity().toString());
        holder.temperatureText.setText(sensorDataModelList.get(position).getTemperature().toString());
    }

    @Override
    public int getItemCount() {
        return sensorDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView humidityText;
        public TextView temperatureText;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            humidityText = (TextView) mView.findViewById(R.id.humidityText);
            temperatureText = (TextView) mView.findViewById(R.id.temperatureText);
        }
    }
}
