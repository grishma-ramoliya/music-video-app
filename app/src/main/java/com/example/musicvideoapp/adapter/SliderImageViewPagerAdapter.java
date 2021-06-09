//package com.example.musicvideoapp.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.viewpager.widget.ViewPager;
//
//import com.bumptech.glide.Glide;
//import com.example.musicvideoapp.R;
//import com.example.musicvideoapp.items.Constant;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.BitmapImageViewTarget;
//
//
//import java.util.ArrayList;
//
//public class SliderImageViewPagerAdapter extends PagerAdapter {
//    private Context context;
//    private LayoutInflater layoutInflater;
//    private ArrayList<String> propertyImageItems=new ArrayList<>();
//    private ImageView ivPropertyImage;
//    public SliderImageViewPagerAdapter(Context context,ArrayList<String> propertyImageItems) {
//        this.context = context;
//        this.propertyImageItems=propertyImageItems;
//    }
//
//    @Override
//    public int getCount() {
//        return propertyImageItems.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view==object;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view=layoutInflater.inflate(R.layout.silder_image_layout,null);
//        ivPropertyImage=view.findViewById(R.id.ivPropertyImages);
//        Glide.with(context)
//                .asBitmap()
//                .load(propertyImageItems.get(position))
//                .into(ivPropertyImage);
//
//        ViewPager viewPager= (ViewPager) container;
//        viewPager.addView(view,0);
//        return view;
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        ViewPager vp = (ViewPager) container;
//        View view = (View) object;
//        vp.removeView(view);
//    }
//}
