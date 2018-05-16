package com.icare_clinics.app.utilities;

import com.icare_clinics.app.dataobject.GalleryDO;

import java.util.ArrayList;
import java.util.List;

public final class GalleryUtil {
  private int currentOffset;
  private ArrayList<GalleryDO> arrGalleryDO;

  public GalleryUtil(ArrayList<GalleryDO> arrGalleryDO) {
    this.arrGalleryDO = arrGalleryDO;
  }

  public List<GalleryDO> moarItems(int qty) {
    List<GalleryDO> items = new ArrayList<>();
    for (int i = 0; i < qty; i++) {
      // int colSpan = Math.random() < 0.2f ? 2 : 1;
      int colSpan=1;
      int rowSpan=1;
      // Swap the next 2 lines to have items with variable
      // column/row span.
      // int rowSpan = Math.random() < 0.2f ? 2 : 1;
      if((i-3)%8==0){
        colSpan=2;
        rowSpan=2;
      }else {
        if((i+1)%8==0){
          colSpan=2;
          rowSpan=1;
        }
      }
      GalleryDO item = arrGalleryDO.get(i);
      item.setColumnSpan(colSpan);
      item.setRowSpan(rowSpan);
      item.setPosition(currentOffset + i);
      items.add(item);
    }
    currentOffset += qty;
    return items;
  }
}
