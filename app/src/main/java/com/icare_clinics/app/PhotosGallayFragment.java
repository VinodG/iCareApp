package com.icare_clinics.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;
import com.icare_clinics.app.adapter.GalleryAdapter;
import com.icare_clinics.app.adapter.GalleryListAdapter;
import com.icare_clinics.app.common.Preference;
import com.icare_clinics.app.dataobject.GalleryDO;
import com.icare_clinics.app.utilities.GalleryUtil;

import java.util.ArrayList;


public class PhotosGallayFragment extends Fragment {

    /* private int[] gallayImages = {R.drawable.gallery_imag1, R.drawable.gallery_imag2, R.drawable.gallery_imag3,
             R.drawable.gallery_imag4, R.drawable.gallery_imag5, R.drawable.gallery_imag6, R.drawable.gallery_imag7,
             R.drawable.gallery_imag8};
 */
    private AsymmetricGridView listView;
    private GalleryAdapter adapter;
    protected Preference preference;
    protected ArrayList<String> gallayImages;
    private ArrayList<GalleryDO> images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photos_gallay, container, false);
        listView = (AsymmetricGridView) view.findViewById(R.id.lvAsymmetricView);
        LinearLayout llNoImages = (LinearLayout)view.findViewById(R.id.llNoImages);
        Bundle photoBundle = getArguments();
        images = (ArrayList)photoBundle.getSerializable("Photos");
        if(images != null && images.size() >0) {
            listView.setVisibility(View.VISIBLE);
            llNoImages.setVisibility(View.GONE);
            GalleryUtil demoUtils = new GalleryUtil(images);
            adapter = new GalleryListAdapter(getActivity(), demoUtils.moarItems(images.size()));

            listView.setRequestedColumnCount(3);
            listView.setRequestedHorizontalSpacing(Utils.dpToPx(getActivity(), 3));
            listView.setAdapter(getNewAdapter());
            listView.setDebugging(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ((BaseActivity) getActivity()).showImagePopUp(images, position);
                }
            });
        }else{
            listView.setVisibility(View.GONE);
            llNoImages.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private AsymmetricGridViewAdapter getNewAdapter() {
        return new AsymmetricGridViewAdapter(getActivity(), listView, adapter);
    }

}
