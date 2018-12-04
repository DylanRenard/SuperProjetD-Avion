package fr.ensim.superprojetavion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ensim.superprojetavion.Activity.CodeActivity;
import fr.ensim.superprojetavion.Activity.MainActivity;
import fr.ensim.superprojetavion.Activity.SearchActivity;
import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.Model.CodeInfo;
import fr.ensim.superprojetavion.R;
import fr.ensim.superprojetavion.Service.SnowtamService;

/**
 * Created by Dana on 29/11/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<AirportInfo> airportInfoList;
    private Context context;
    private CodeInfo snowtam;

    public MyAdapter(ArrayList<AirportInfo> favorisList, Context context){
        airportInfoList = favorisList;
        this.context = context;
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
                    Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            String snowtamInfo = null;
                            int i;

                            try{
                                for (i=0 ; i<response.length() ; i++) {
                                    JSONObject info = response.getJSONObject(i);

                                    if(info.getString("id").contains("SW")){
                                        snowtamInfo = info.getString("all");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(snowtamInfo!=null) snowtam = new CodeInfo(snowtamInfo,currentAirport);

                            Intent intent = new Intent(context, CodeActivity.class);
                            intent.putExtra("snowtam", snowtam);
                            context.startActivity(intent);
                        }
                    };

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    };

                    SnowtamService.searchSnowtam(currentAirport.getOaciCode(),responseListener,errorListener,context);
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
