package fr.ensim.superprojetavion.Adapter;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import fr.ensim.superprojetavion.R;

/**
 * Created by Dana on 29/11/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final List<Pair<String, String>> characters = Arrays.asList(
            Pair.create("ESSA", "Stockholm Arlanda"),
            Pair.create("LFPO", "Paris Orly"),
            Pair.create("LICC", "Catania Fontanarossa"),
            Pair.create("KJFK", "JFK New York"),
            Pair.create("KJFK", "JFK New York")

    );

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pair<String, String> pair = characters.get(position);
        holder.display(pair);
    }


    @Override
    public int getItemCount() {
        return characters.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView codeAirport;
        private final TextView nameAirport;

        private Pair<String, String> currentPair;

        public MyViewHolder(final View itemView) {
            super(itemView);
            codeAirport = ((TextView) itemView.findViewById(R.id.codeAirport));
            nameAirport = ((TextView) itemView.findViewById(R.id.nameAirport));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle(currentPair.first)
                            .setMessage(currentPair.second)
                            .show();
                }
            });
        }

        public void display(Pair<String, String> pair) {
            currentPair = pair;
            codeAirport.setText(pair.first);
            nameAirport.setText(pair.second);
        }
    }
}
