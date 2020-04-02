package com.adam.pom.Helper;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adam.pom.Objects.Cards;
import com.adam.pom.R;

import java.util.List;

public class CardsArrayAdapter extends ArrayAdapter<Cards>{

    public CardsArrayAdapter(Context context, int resourceId, List<Cards> cards){
        super(context, resourceId, cards);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Cards card = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.card_text);
        ImageView imageView = convertView.findViewById(R.id.card_image);

        textView.setText(card.getName());
        imageView.setImageResource(card.getImg());

        return convertView;
    }
}
