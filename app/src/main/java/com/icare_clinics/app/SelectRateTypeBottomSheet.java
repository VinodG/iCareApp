package com.icare_clinics.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.icare_clinics.app.AddReminder;
import com.icare_clinics.app.adapter.RateTypeAdapter;


/**
 * Created by Baliram.Kumar on 05-05-2017.
 */

public class SelectRateTypeBottomSheet extends BottomSheetDialogFragment {
   TextView tvDone;
    ListView lvRateType;
    private RateTypeAdapter adapter;
    private Context context;
    String selectedType;

    private String[] strRateType=new String[]{"Once a day","Twice a day","Three times a day","Four times a dayvations","Five times a day"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_rate_type_bottom_sheet, container, false);
        initializeControls(view);
        return view;
    }

    private void initializeControls(final View view) {
        tvDone=(TextView)view.findViewById(R.id.tvDone);
        lvRateType=(ListView)view.findViewById(R.id.lvRateType);
        context=getActivity();
        adapter=new RateTypeAdapter(context, strRateType);
        lvRateType.setAdapter(adapter);
        lvRateType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                adapter.selectedPosition = position;
                adapter.refresh(strRateType);
                selectedType = strRateType[position];
                ((AddReminder) context).showPosition(position);
                dismiss();
            }
        });
    }
}
