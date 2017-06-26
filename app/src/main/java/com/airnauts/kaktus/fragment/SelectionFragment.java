package com.airnauts.kaktus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airnauts.kaktus.R;
import com.airnauts.kaktus.model.facebook.Album;
import com.airnauts.kaktus.model.facebook.Photo;
import com.airnauts.kaktus.provider.StringProvider;
import com.airnauts.kaktus.view.ViewPagerIndicator;
import com.airnauts.kaktus.view.transformer.RotationalPageTransformer;
import com.airnauts.toolkit.data.DataManager;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mradziko on 10.11.2015.
 */
public class SelectionFragment extends Fragment {
    private final static String PICTURE = "https://graph.facebook.com/%s/picture";
    private final static String PHOTOS_PATH = "/photos";
    private final static String CACTUS_ALBUM_ID = "1730128753877587";
    private final static String POT_ALBUM_ID = "1730128753877587";
    private final static String ALBUM_ID = "1730128753877587";

    @Bind(R.id.view_pager_top)
    ViewPager mTopViewPager;
    @Bind(R.id.view_pager_bottom)
    ViewPager mBottomViewPager;
    @Bind(R.id.indicator_top)
    ViewPagerIndicator mTopIndicator;
    @Bind(R.id.indicator_bottom)
    ViewPagerIndicator mBottomIndicator;

    public static SelectionFragment newInstance() {

        Bundle args = new Bundle();

        SelectionFragment fragment = new SelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_select, container, false);
        ButterKnife.bind(this, view);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest graphRequest = GraphRequest.newGraphPathRequest(accessToken, CACTUS_ALBUM_ID + PHOTOS_PATH, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                if (response != null && response.getRawResponse() != null) {
                    Log.v(SelectionFragment.class.getSimpleName(), response.getRawResponse());
                    Album album = new Gson().fromJson(response.getRawResponse(), Album.class);
                    if (album != null) {
                        initPagerWithAlbum(album, mTopViewPager, mTopIndicator);
                        initPagerWithAlbum(album, mBottomViewPager, mBottomIndicator);
                    }
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name");
        parameters.putString("limit", "100");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

        mTopViewPager.setPageTransformer(false, new RotationalPageTransformer());
        mBottomViewPager.setPageTransformer(false, new RotationalPageTransformer());
        return view;
    }

    private void initPagerWithAlbum(Album album, ViewPager viewPager, final ViewPagerIndicator viewPagerIndicator) {
        final int count = album.data != null ? album.data.size() : 0;
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(), album.data);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagerIndicator.setSelection(position < count ? position : count - 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerIndicator.setCount(count);
        viewPagerIndicator.setSelection(0);
        viewPagerIndicator.animateIndicators();
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private ArrayList<Photo> mPhotos;
        private Context mContext;

        public ViewPagerAdapter(Context context, ArrayList<Photo> photos) {
            mContext = context;
            mPhotos = photos;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewPager pager = (ViewPager) container;
            ImageView imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.layout_image, container, false);
            Picasso.with(mContext).load(String.format(PICTURE, mPhotos.get(position).id)).into(imageView);
            pager.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return mPhotos != null ? mPhotos.size() : 0;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            ((ViewPager) container).removeView((View) view);
        }
    }

}