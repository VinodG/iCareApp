package com.icare_clinics.app.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.icare_clinics.app.DoctorsMainActivity;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataobject.ItemDo;

import java.util.ArrayList;

/**
 * Created by WINIT on 28-04-2017.
 */

public class FragmentList extends Fragment
{
    ViewGroup container;
    Bundle savedInstanceState;
    Context context;
    ListView lv;
    ArrayList<ItemDo>  listSpecialty = new ArrayList<ItemDo>();
    ArrayList<ItemDo>  listLocation = new ArrayList<ItemDo>();
    ArrayList<ItemDo>  list= new ArrayList<ItemDo>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        context = getActivity();
        lv =(ListView) view.findViewById(R.id.lv);
        listSpecialty = ((DoctorsMainActivity)context).entireList.get("Speciality");
        listLocation = ((DoctorsMainActivity)context).entireList.get("Location");
//        String from =((DoctorsMainActivity) context).getIntent().getExtras().getString("FROM");
        String from =getArguments().get("FROM").toString();
        if(from.equalsIgnoreCase("SPECIALITY"))
            list = ((DoctorsMainActivity)context).entireList.get("Speciality");
        else
            list =((DoctorsMainActivity)context).entireList.get("Location");

        MyArrayAdapter temp_adapter = new  MyArrayAdapter(context, list);
        lv.setAdapter(temp_adapter);


        return view;
    }

    public class MyArrayAdapter extends BaseAdapter {
        LayoutInflater inflater = null;
        Context context;
        ArrayList< ItemDo> list = new ArrayList< ItemDo>();


        MyArrayAdapter(Context context, ArrayList< ItemDo> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final CheckBox chk;
            final TextView tv;
            inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            View inflatedView = inflater.inflate(R.layout.list_item_with_checkbox, null);
            chk = (CheckBox) inflatedView.findViewById(R.id.chk);
            tv = (TextView) inflatedView.findViewById(R.id.tv);
            if (list.get(i).getAStatus() == false) {
                chk.setButtonDrawable(R.drawable.uncheckedfilter);
            } else {
                chk.setButtonDrawable(R.drawable.checkedfilter);
            }

            chk.setChecked(list.get(i).getAStatus());
            tv.setText(list.get(i).getName());
            chk.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    if (list.get(i).status == false) {
                        list.get(i).status = true;
                        chk.setChecked(true);
                        chk.setButtonDrawable(R.drawable.checkedfilter);
                        Log.d("Checkedbox in slidemenu", i + " with checked values " + chk.isChecked() + tv.getText().toString());
                    } else {
                        chk.setButtonDrawable(R.drawable.uncheckedfilter);
                        list.get(i).status= false;
                        chk.setChecked(false);
                        Log.d("Checkedbox in slidemenu", i + " with checked values " + chk.isChecked() + tv.getText().toString());

                    }

                }
            });
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list.get(i).getAStatus()== false) {
                        list.get(i).status = true;
                        chk.setChecked(true);
                        chk.setButtonDrawable(R.drawable.checkedfilter);
                        Log.d("Checkedbox in slidemenu", i + " with checked values " + chk.isChecked() + tv.getText().toString());
                    } else {
                        chk.setButtonDrawable(R.drawable.uncheckedfilter);
                        list.get(i).status = false;
                        chk.setChecked(false);
                        Log.d("Checkedbox in slidemenu", i + " with checked values " + chk.isChecked() + tv.getText().toString());
                    }

                }
            });
            Log.d("Position of GetView  ", i + " with checked values " + chk.isChecked());
            return inflatedView;
        }
    }
}
