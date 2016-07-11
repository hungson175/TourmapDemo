package mobile.dtd.vn.tourmapdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by hungson175 on 7/10/2016.
 */
public class Helpers {
    public static View inflate(Context context, int layoutId) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return li.inflate(layoutId,null,false);
    }
}
