package com.adam.pom.Fragments;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.adam.pom.Helper.HttpHelper;
import com.adam.pom.R;

public class FragmentSettings extends Fragment {

    private Spinner spinner_interestedIn, spinner_sex;
    private String sex, interested_in;

    private Button btn_save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        sex = "";
        interested_in = "";

        spinner_sex = rootView.findViewById(R.id.frag_settings_sex);
        ArrayAdapter<CharSequence> adapter_sex = ArrayAdapter.createFromResource(getContext(), R.array.spinner_sex, android.R.layout.simple_spinner_item);
        adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex.setAdapter(adapter_sex);

        spinner_interestedIn = rootView.findViewById(R.id.frag_settings_interestedIn);
        ArrayAdapter<CharSequence> adapter_interested_in = ArrayAdapter.createFromResource(getContext(), R.array.spinner_interestedin, android.R.layout.simple_spinner_item);
        adapter_interested_in.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_interestedIn.setAdapter(adapter_interested_in);

        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = parent.getItemAtPosition(position).toString().toLowerCase().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_interestedIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                interested_in = parent.getItemAtPosition(position).toString().toLowerCase().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_save = rootView.findViewById(R.id.frag_settings_btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        return rootView;
    }
}
