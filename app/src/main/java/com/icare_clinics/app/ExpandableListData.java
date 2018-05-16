package com.icare_clinics.app;

import com.icare_clinics.app.dataobject.ExpandableListDo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Baliram.Kumar on 21-03-2017.
 */

public class ExpandableListData {

    public static ArrayList<ExpandableListDo> arraylistExpandable=new ArrayList<ExpandableListDo>();
    public static String []type={"Profile Detail","Gender","Date of Birth","Height","Weight"};
    public static int []icons={R.drawable.profile_icon,R.drawable.gender_icon,R.drawable.dob_icon,R.drawable.weight_icon,R.drawable.height_icon};
    public static  int []layouts={R.layout.profile_child_cell,R.layout.gender_child_cell,R.layout.layout_dob_cell,R.layout.height_child_cell,R.layout.weight_child_cell};
    static HashMap<String, Integer> getData() {

        HashMap<String, Integer> expandableListDetail = new HashMap<String, Integer>();

        for(int i=0;i<icons.length;i++)
        {
            ExpandableListDo expandableListDo=new ExpandableListDo();
            expandableListDo.title=type[i];
            expandableListDo.image=icons[i];
            arraylistExpandable.add(expandableListDo);
            expandableListDetail.put(type[i],layouts[i]);
        }

        return expandableListDetail;
    }
}
