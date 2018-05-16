package com.icare_clinics.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.icare_clinics.app.AddReminder;
import com.icare_clinics.app.MyReminder;
import com.icare_clinics.app.R;
import com.icare_clinics.app.dataaccesslayer.ReminderDA;
import com.icare_clinics.app.dataobject.ReminderDo;

import java.util.ArrayList;

/**
 * Created by Baliram.Kumar on 27-02-2017.
 */

public class MyReminderAdapter  extends RecyclerSwipeAdapter<MyReminderAdapter.ViewHolder> {
    private ArrayList<ReminderDo> arrReminder;
    private LayoutInflater inflater;
    Context context;
    private ReminderDA reminderDA;
     int pos;
     String id;
    public MyReminderAdapter(Context context,ArrayList<ReminderDo> arrReminder) {
        this.context = context;
        this.arrReminder = arrReminder;
    }


    @Override
    public MyReminderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reminder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.d("onCreateViewHolder ", "clicked " + viewType);
        //view.setOnClickListener();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ReminderDo reminderDo=arrReminder.get(position);
        reminderDA=new ReminderDA(context);
         pos=position;
        String medicineName=reminderDo.medicineName;
        String numberOfCapsules=reminderDo.numberOfCapsules;
        String numberOfDays=reminderDo.days;

        String timing=reminderDo.timing;
        holder.tvMedicineName.setText(medicineName);
        holder.numberOfCapsules.setText(numberOfCapsules + " " +reminderDo.medicineType+" "+"|" + " "+ numberOfDays);
      //  holder.numberOfDays.setText(numberOfDays);
        holder.tvTiming.setText(timing);
        holder.llReminderItem.setTag(position);
        if(reminderDo.medicineType.equals("Tablet")){
            holder.ivSelecttedTablets.setVisibility(View.VISIBLE);
            holder.ivunSelecttedCapsules.setVisibility(View.GONE);
            holder.ivunSelecttedDroplets.setVisibility(View.GONE);
            holder.ivunSelecttedInjection.setVisibility(View.GONE);
        }else if(reminderDo.medicineType.equals("Capsule")){
            holder.ivSelecttedTablets.setVisibility(View.GONE);
            holder.ivunSelecttedCapsules.setVisibility(View.VISIBLE);
            holder.ivunSelecttedDroplets.setVisibility(View.GONE);
            holder.ivunSelecttedInjection.setVisibility(View.GONE);
        }else if(reminderDo.medicineType.equals("Drop")){
            holder.ivSelecttedTablets.setVisibility(View.GONE);
            holder.ivunSelecttedCapsules.setVisibility(View.GONE);
            holder.ivunSelecttedDroplets.setVisibility(View.VISIBLE);
            holder.ivunSelecttedInjection.setVisibility(View.GONE);
        }else if(reminderDo.medicineType.equals("Injection")){
            holder.ivSelecttedTablets.setVisibility(View.GONE);
            holder.ivunSelecttedCapsules.setVisibility(View.GONE);
            holder.ivunSelecttedDroplets.setVisibility(View.GONE);
            holder.ivunSelecttedInjection.setVisibility(View.VISIBLE);
        }

        holder.llReminderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reminderDo!=null)
                clickForReminderDetails(reminderDo,(int)v.getTag());
            }
        });

        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder((context));
                myAlertDialog.setTitle("Alert");
                myAlertDialog.setMessage("Do you want to Delete Reminder");
                myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        mItemManger.removeShownLayouts(holder.swipeLayout);
                        id=reminderDo.MedicinID;
                        arrReminder.remove(position);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                reminderDA.deleteReminder(id);
                            }
                        }).start();

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, arrReminder.size());
                        mItemManger.closeAllItems();
                    }});
                myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                    }});
                myAlertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        if (arrReminder != null)
            return arrReminder.size();
        return 0;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        TextView tvMedicineName,numberOfCapsules,numberOfDays,tvTiming;
        ImageView menuItem,ivSelecttedTablets,ivunSelecttedCapsules,ivunSelecttedDroplets,ivunSelecttedInjection;
        LinearLayout llReminder,llReminderItem,llDelete;
        SwipeLayout swipeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            tvMedicineName = (TextView) itemView.findViewById(R.id.tvMedicineName);
            numberOfCapsules = (TextView) itemView.findViewById(R.id.numberOfCapsules);
            numberOfDays = (TextView) itemView.findViewById(R.id.numberOfDays);
            tvTiming = (TextView) itemView.findViewById(R.id.tvTiming);
            menuItem = (ImageView) itemView.findViewById(R.id.menuItem);

            ivSelecttedTablets = (ImageView) itemView.findViewById(R.id.ivSelecttedTablets);
            ivunSelecttedCapsules = (ImageView) itemView.findViewById(R.id.ivunSelecttedCapsules);
            ivunSelecttedDroplets = (ImageView) itemView.findViewById(R.id.ivunSelecttedDroplets);
            ivunSelecttedInjection = (ImageView) itemView.findViewById(R.id.ivunSelecttedInjection);

            llReminder=(LinearLayout)itemView.findViewById(R.id.llReminder);
            llReminderItem=(LinearLayout)itemView.findViewById(R.id.llReminderItem);
            llDelete=(LinearLayout)itemView.findViewById(R.id.llDelete);
            swipeLayout=(SwipeLayout)itemView.findViewById(R.id.swipe);

        }

    }

    public void refresh(ArrayList<ReminderDo> arrReminder) {
        this.arrReminder = arrReminder;
        notifyDataSetChanged();
    }

    private void clickForReminderDetails(ReminderDo reminderDo,int pos) {
        //Log.d(" Items ", "clicked on " + position);
        Intent intent = new Intent(context, AddReminder.class);
        intent.putExtra("ReminderDo", reminderDo);
        intent.putExtra("position",String.valueOf(pos));
        ((MyReminder)context).startActivityForResult(intent,4);

    }

}
