package com.murik.places.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.murik.places.MainActivity;
import com.murik.places.MapsActivity;
import com.murik.places.R;

import java.util.List;

public class TypePlaceAdapter extends  RecyclerView.Adapter<TypePlaceAdapter.TypePlacesViewHolder> {

    private Context mContext;
    List<String> typePlace;


    public  TypePlaceAdapter(Context context,List<String> typePlace) {
        mContext = context;
        this.typePlace = typePlace;
    }

    @Override
    public TypePlaceAdapter.TypePlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_type_plece, parent, false);

        return new TypePlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TypePlaceAdapter.TypePlacesViewHolder holder, int position) {
        holder.bind(typePlace.get(position));
    }

    @Override
    public int getItemCount() {
        return typePlace.size();
    }


    public class TypePlacesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView tvTypePlace;

        public TypePlacesViewHolder(View itemView) {
            super(itemView);
            tvTypePlace = itemView.findViewById(R.id.tvTypePlace);
            itemView.setOnClickListener(this);
        }

        public void bind(String s) {
            tvTypePlace.setText(s);
        }

        @Override
        public void onClick(View v) {
            Log.d("MyLogs", "message " + tvTypePlace.getText());

            Intent intent = new Intent(v.getContext(), MapsActivity.class);
            intent.putExtra(MainActivity.TYPE_PLACE__KEY, tvTypePlace.getText().toString());
            mContext.startActivity(intent);
        }
    }
}
