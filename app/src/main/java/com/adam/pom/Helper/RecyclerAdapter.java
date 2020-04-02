package com.adam.pom.Helper;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adam.pom.Chat;
import com.adam.pom.R;

import java.util.ArrayList;

import butterknife.OnClick;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewHolder>{

    private ArrayList<String> names;

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public viewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.rList_item_txt);
        }

        @Override
        public void onClick(View view){
            Intent intent = new Intent(view.getContext(), Chat.class);
            view.getContext().startActivity(intent);
        }
    }

    public RecyclerAdapter(ArrayList<String> mNames){
        this.names = mNames;
    }

    @NonNull
    @Override
    public RecyclerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent,false);
        viewHolder vh = new viewHolder(v);
        v.setOnClickListener(vh);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.viewHolder holder, int position) {
        // - get element from your dataset at this position.
        // - replace the contents of the view with this element
        holder.textView.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


}
