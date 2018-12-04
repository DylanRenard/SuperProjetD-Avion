package fr.ensim.superprojetavion.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.R;

/**
 * Created by Dana on 29/11/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<AirportInfo> airportInfoList;

    public MyAdapter(ArrayList<AirportInfo> favorisList){
        airportInfoList = favorisList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AirportInfo airport = airportInfoList.get(position);
        holder.display(airport);
    }


    @Override
    public int getItemCount() {
        return airportInfoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView codeAirport;
        private final TextView nameAirport;

        private AirportInfo currentAirport;

        public MyViewHolder(final View itemView) {
            super(itemView);
            codeAirport = ((TextView) itemView.findViewById(R.id.codeAirport));
            nameAirport = ((TextView) itemView.findViewById(R.id.nameAirport));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        public void display(AirportInfo airport) {
            currentAirport = airport;
            codeAirport.setText(airport.getOaciCode());
            nameAirport.setText(airport.getAirportName());
        }
    }
}
