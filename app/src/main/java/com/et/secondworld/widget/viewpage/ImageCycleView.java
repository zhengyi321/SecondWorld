package com.et.secondworld.widget.viewpage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.et.secondworld.R;
import com.et.secondworld.widget.imageview.NormalImagePreActivity;

import java.util.ArrayList;
import java.util.List;

/*
* http://download.csdn.net/download/jimtrency/9633078
* **/
public class ImageCycleView extends LinearLayout {

	private Context mContext;

	private CycleViewPager mBannerPager = null;

	private ImageCycleAdapter mAdvAdapter;

	private ViewGroup mGroup;
	private TextView tvinfostitle;
//	private ArrayList<String> infoListtitle;文字

	private ImageView mImageView = null;
	private EditText etComment;
	private RelativeLayout rlyForumDetailTwo;
	private ImageView[] mImageViews = null;

	private int mImageIndex = 1;

	private float mScale;

	public ImageCycleView(Context context) {
		super(context);
	}

	public ImageCycleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mScale = context.getResources().getDisplayMetrics().density;
		LayoutInflater.from(context).inflate(R.layout.view_banner_content, this);
		mBannerPager = (CycleViewPager) findViewById(R.id.pager_banner);

		mBannerPager.setOnPageChangeListener(new GuidePageChangeListener());
		mBannerPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
//					startImageTimerTask();
					break;
				default:
//					stopImageTimerTask();
					break;
				}
				return false;
			}
		});
		mGroup = (ViewGroup) findViewById(R.id.viewGroup);
		tvinfostitle = (TextView) findViewById(R.id.tvinfostitle);
	}


	public void setImageResources(List<String> urlImageList, ArrayList<String> infoListtitle, ArrayList<Integer> localImageList,
								  ImageCycleViewListener imageCycleViewListener, EditText etComment1, RelativeLayout rlyForumDetailTwo1) {
		mGroup.removeAllViews();
		etComment = etComment1;
		rlyForumDetailTwo = rlyForumDetailTwo1;
//		this.infoListtitle = infoListtitle;
//		ArrayList<Integer> imgLocal = new ArrayList<>();
		if(urlImageList == null){
			urlImageList = new ArrayList<>();
		}
		if(localImageList == null){
			localImageList = new ArrayList<>();
		}
		final int imageCount = urlImageList.size()+localImageList.size();
		mImageViews = new ImageView[imageCount];
		for (int i = 0; i < imageCount; i++) {
			mImageView = new ImageView(mContext);
			int imageParams = (int) (mScale * 20 + 0.5f);
			int imagePadding = (int) (mScale * 5 + 0.5f);
			LayoutParams layout = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			layout.setMargins(3, 0, 3, 0);

			layout.width = 10;
			layout.height = 10;
			mImageView.setLayoutParams(layout);

			//存放小圆点的image
			mImageViews[i] = mImageView;
			if (i == 0) {
				mImageViews[i].setBackgroundResource(R.drawable.point_green);
			} else {
				mImageViews[i].setBackgroundResource(R.drawable.point_gray);
			}
			mGroup.addView(mImageViews[i]);
		}
//		Log.d("cycleView",urlImageList.size()+"");
		mAdvAdapter = new ImageCycleAdapter(mContext, urlImageList,localImageList, imageCycleViewListener);

		mBannerPager.setAdapter(mAdvAdapter);
		mBannerPager.setOffscreenPageLimit(30);
//		mBannerPager.setOffscreenPageLimit(1);
		mBannerPager.setPageMargin(5);
//		mBannerPager.setPageTransformer(false, new ScaleTransformer());
//		mBannerPager.setCurrentItem(0);
		// 预加载
		if (urlImageList.size() == 1){
			mBannerPager.setPadding(0,0,0,0);
//			mBannerPager.setLayoutParams(params);
		}

//		startImageTimerTask();
	}

	public void startImageCycle() {
		startImageTimerTask();
	}

	public void pushImageCycle() {
		stopImageTimerTask();
	}

	private void startImageTimerTask() {
		stopImageTimerTask();
		mHandler.postDelayed(mImageTimerTask, 5000);
	}

	private void stopImageTimerTask() {
		mHandler.removeCallbacks(mImageTimerTask);
	}

	private Handler mHandler = new Handler();

	private Runnable mImageTimerTask = new Runnable() {

		@Override
		public void run() {
			if (mImageViews != null) {
				if ((++mImageIndex) == mImageViews.length + 1) {
					mImageIndex = 1;
				}
				mBannerPager.setCurrentItem(mImageIndex);
			}
		}
	};

	private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
//			if (state == ViewPager.SCROLL_STATE_IDLE)
				startImageTimerTask();
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int index) {
            //设置图片 title和圆点变化
//			if (index == 0 || index == mImageViews.length + 1) {
			if (index == 0 ) {
				return;
			}
			index = index % mImageViews.length;
			mImageIndex = index;
//			index -= 1;
			mImageViews[index].setBackgroundResource(R.drawable.point_green);
			for (int i = 0; i < mImageViews.length; i++) {

				if (index != i) {
					mImageViews[i].setBackgroundResource(R.drawable.point_gray);
				} else {
//					tvinfostitle.setText(infoListtitle.get(index));
				}
			}
		}
	}

	private class ImageCycleAdapter extends PagerAdapter {

//		private ArrayList<ImageView> mImageViewCacheList;

		private List<String> murlList ;
		private ArrayList<Integer> mlocalList ;

		private ImageCycleViewListener mImageCycleViewListener;

		private Context mContext;

		public ImageCycleAdapter(Context context, List<String> urlList,ArrayList<Integer> localList,
				ImageCycleViewListener imageCycleViewListener) {
			mContext = context;
			murlList = urlList;
			mlocalList = localList;
			mImageCycleViewListener = imageCycleViewListener;
//			mImageViewCacheList = new ArrayList<ImageView>();
		}

		@Override
		public int getCount() {
			return murlList.size()+mlocalList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {

			return disPlayUrlLocalImageView(container,position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			ImageView view = (ImageView) object;
//			container.removeView(view);
//			mImageViewCacheList.add(view);
		}

		@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
		private View disPlayUrlLocalImageView(ViewGroup container, final int position){
			String imageUrl = "";
			int localImage = 0;
			if(position >= 0) {
				if (murlList != null && murlList.size() > position) {

					if (!TextUtils.isEmpty(murlList.get(position).trim())) {
						imageUrl = murlList.get(position);
					}
				}
				if (murlList != null && mlocalList != null && (murlList.size() + mlocalList.size()) > position && murlList.size() <= position) {
					localImage = mlocalList.get(position - murlList.size());
				}
			}

			LayoutInflater inflater = LayoutInflater.from(mContext);
			View view = inflater.inflate(R.layout.cycleview_item,null,false);
			ImageView imageView = view.findViewById(R.id.iv_cycleview_item);

//			ImageView imageView = null;
//			if (mImageViewCacheList.isEmpty()) {
//				imageView = new ImageView(mContext);
//				imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//
//			} else {
//				imageView = mImageViewCacheList.remove(0);
//			}
//			imageView.setTransitionName("image");
//			Pair<View, String> pair = new Pair<View, String>(imageView,"image");
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					mImageCycleViewListener.onImageClick(murlList,mlocalList, position, v);
					if(etComment != null) {
						InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
						if (imm.isActive(etComment)) {
							imm.hideSoftInputFromWindow(((Activity) mContext).getWindow().getDecorView().getWindowToken(), 0);
							rlyForumDetailTwo.setFocusableInTouchMode(true);
							rlyForumDetailTwo.requestFocus();
						} else {
							startImagePre(position, imageView);
						}

					}else {
						startImagePre(position, imageView);
					}
//					v.getContext().startActivity(intent);
//					((Activity)v.getContext()).overridePendingTransition(0,0);

				}
			});
//			imageView.setTag(imageUrl);
			container.addView(view);
//			Log.d("cycleView",imageUrl);
//			Log.d("imgcycle", "" + position);
			if(!imageUrl.isEmpty() || !imageUrl.equals("")) {

				mImageCycleViewListener.displayImageURL(imageUrl, imageView);//网络图片  imageurl
			}
			if(localImage != 0){
				mImageCycleViewListener.displayImageLocal(localImage,imageView);//本地图片
			}
			return view;
		}

		private void startImagePre(int position, ImageView v){
			Intent intent =new Intent(mContext, NormalImagePreActivity.class);
			intent.putStringArrayListExtra("imgurlList",(ArrayList<String>) murlList);
			intent.putExtra("imagePosition",position);
//					ActivityOptionsCompat optionsCompat =
//							ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), v, "image");

			ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v,100,100,400,400);
			mContext.startActivity(intent,compat.toBundle());
		}

	}




	public static interface ImageCycleViewListener {
		public void displayImageURL(String imageURL, ImageView imageView);
		public void displayImageLocal(int mipmap,ImageView imageView);
		public void onImageClick( ArrayList<String> urlList,ArrayList<Integer> localList, int postion, View imageView);
	}

}
