package mobile.dtd.vn.tourmapdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by hungson175 on 7/12/2016.
 */
public class PlaceDetailsActivity extends ActionBarActivity {
    public static String PARAM_PLACE_ID = "PLACE_ID";
    private ViewPager intro_images;
    private LinearLayout page_indicators;
    private ViewPagerAdapter mAdapter;
    private int[] mImageResources = {
            R.mipmap.abc1,
            R.mipmap.abc2,
            R.mipmap.abc3,
            R.mipmap.abc4,
            R.mipmap.abc5
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        String placeId = getIntent().getExtras().getString(PARAM_PLACE_ID);
        Toast.makeText(getApplicationContext(),"PlaceId: " + placeId, Toast.LENGTH_LONG).show();
        setReference();
        setContentView(R.layout.demo_viewpager);
        intro_images = (ViewPager) findViewById(R.id.pager_introduction);
        page_indicators = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        mAdapter = new ViewPagerAdapter(PlaceDetailsActivity.this, mImageResources);
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setReference() {

    }

    public class ViewPagerAdapter extends PagerAdapter {
        public ViewPagerAdapter(Context context, int[] mImageResources) {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
