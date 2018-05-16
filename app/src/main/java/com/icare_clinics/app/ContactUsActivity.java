package com.icare_clinics.app;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataaccesslayer.ClinicsDA;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.utilities.StringUtils;
import com.icare_clinics.app.webaccessLayer.ServiceUrls;

import java.util.ArrayList;

import static com.icare_clinics.app.R.id.tvNumOfPages;
import static com.icare_clinics.app.R.id.viewPager;

/**
 * Created by Satish.Babu on 1/2/2017.
 */

public class ContactUsActivity extends BaseActivity implements TabHost.OnTabChangeListener{

    LinearLayout llmain;
    private ImageView facebook,twitter,in;
    private Button btnRequestAppointment;
    private TabLayout tabLayout;
    private ArrayList<ClinicDO> arrClinic;

    @Override
    public void initialise() {
        llmain=(LinearLayout) inflater.inflate(R.layout.contactus,null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        llBody.addView(llmain,param);
        ivMenu.setVisibility(View.VISIBLE);
        ivLogo.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvCancel.setVisibility(View.GONE);

        initialiseControls();

        tvTitle.setText(getString(R.string.contact_us));

        setStatusBarColor();
        loadData();

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ContactUsActivity.this,FacebookWebViewActivity.class);
                startActivity(intent);
            }
        });

        btnRequestAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ContactUsActivity.this,AppointmentForm.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initialiseControls() {
        facebook=(ImageView)llmain.findViewById(R.id.iv_facebook);
        btnRequestAppointment=(Button)llmain.findViewById(R.id.btnRequestAppointment);

        tabLayout=(TabLayout)llmain.findViewById(R.id.tabLayout);
    }

    @Override
    public void loadData() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                ClinicsDA clinicsDA=new ClinicsDA(ContactUsActivity.this);
                arrClinic=clinicsDA.getClinics();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arrClinic!=null && arrClinic.size()>0){

                            updateTab(arrClinic);
                        }else {
                            arrClinic=new ArrayList<ClinicDO>();
                        }
                    }
                });
            }
        }).start();
    }

    private void updateTab(ArrayList<ClinicDO> arrClinic) {
        if (arrClinic != null) {

            LayoutInflater inflater = getLayoutInflater();
            if (tabLayout.getTabCount() > 0) {
                tabLayout.removeAllTabs();
            }
            for (int i = 0; i < arrClinic.size(); i++) {
                View tabItem = null;
                tabItem = (View) inflater.inflate(R.layout.contactus_tab_item, null);
                TextView tvplace=(TextView)tabItem.findViewById(R.id.tvplace);
                TextView tvAddress=(TextView)tabItem.findViewById(R.id.tvAddress);
                TextView tvTimings=(TextView)tabItem.findViewById(R.id.tvTimings);

                tvplace.setTypeface(AppConstants.DinproRegular);
                tvAddress.setTypeface(AppConstants.DinproRegular);
                tvTimings.setTypeface(AppConstants.DinproRegular);

                tvplace.setText(arrClinic.get(i).description);
                tvAddress.setText(StringUtils.fromHtml((arrClinic.get(i).add1)+"|"+"\n"+StringUtils.fromHtml(arrClinic.get(i).add2)));
                tvTimings.setText(StringUtils.fromHtml(arrClinic.get(i).timing));

                tabLayout.addTab(tabLayout.newTab().setCustomView(tabItem));
            }
        }
    }

    @Override
    public void onTabChanged(String s) {

    }
}
