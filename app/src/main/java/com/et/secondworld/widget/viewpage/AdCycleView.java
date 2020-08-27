package com.et.secondworld.widget.viewpage;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.et.secondworld.R;
import com.et.secondworld.bean.CircleImgBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.widget.imageview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/*
* http://download.csdn.net/download/jimtrency/9633078
* https://www.jianshu.com/p/d307479158a3
* **/
public class AdCycleView extends LinearLayout {

	private Context mContext;

	private AdCycleViewPager mBannerPager = null;

	private ImageCycleAdapter mAdvAdapter;

//	private ViewGroup mGroup;
	private TextView tvinfostitle;
//	private ArrayList<String> infoListtitle;文字

	private ImageView mImageView = null;

	private ImageView[] mImageViews = null;

	private int mImageIndex = 1;

	private float mScale;

	public AdCycleView(Context context) {
		super(context);
	}

	public AdCycleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mScale = context.getResources().getDisplayMetrics().density;
		LayoutInflater.from(context).inflate(R.layout.view_adbanner_content, this);
		mBannerPager = (AdCycleViewPager) findViewById(R.id.cvp_adbanner);
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
//		mGroup = (ViewGroup) findViewById(R.id.viewGroup);
//		tvinfostitle = (TextView) findViewById(R.id.tv_adbanner_title);
	}

	public void setImageResources(List<CircleImgBean.ListBean> urlImageList, ArrayList<String> infoListtitle, ArrayList<Integer> localImageList,
								  ImageCycleViewListener imageCycleViewListener) {
	//	mGroup.removeAllViews();
//		this.infoListtitle = infoListtitle;
		final int imageCount = urlImageList.size()+localImageList.size();
		mImageViews = new ImageView[imageCount];
	/*	for (int i = 0; i < imageCount; i++) {
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
				mImageViews[i].setBackgroundResource(R.drawable.point_wheat);
			} else {
				mImageViews[i].setBackgroundResource(R.drawable.point_red);
			}
			mGroup.addView(mImageViews[i]);
		}*/
		mAdvAdapter = new ImageCycleAdapter(mContext, urlImageList,localImageList, imageCycleViewListener);
		mBannerPager.setAdapter(mAdvAdapter);
		mBannerPager.setPageTransformer(false, new ScaleTransformer());
		mBannerPager.setOffscreenPageLimit(10);
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
//				startImageTimerTask();
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int index) {
            //设置图片 title和圆点变化
			/*if (index == 0 || index == mImageViews.length + 1) {
				return;
			}
			mImageIndex = index;
			index -= 1;
			mImageViews[index].setBackgroundResource(R.drawable.point_wheat);
			for (int i = 0; i < mImageViews.length; i++) {

				if (index != i) {
					mImageViews[i].setBackgroundResource(R.drawable.point_red);
				} else {
//					tvinfostitle.setText(infoListtitle.get(index));
				}
			}*/
		}
	}

	private class ImageCycleAdapter extends PagerAdapter {

		private ArrayList<RoundImageView> mImageViewCacheList;

		private List<CircleImgBean.ListBean> murlList ;
		private ArrayList<Integer> mlocalList ;

		private ImageCycleViewListener mImageCycleViewListener;

		private Context mContext;

		public ImageCycleAdapter(Context context, List<CircleImgBean.ListBean> urlList,ArrayList<Integer> localList,
				ImageCycleViewListener imageCycleViewListener) {
			mContext = context;
			murlList = urlList;
//			Log.d("murlListSize",murlList.size()+"");
			mlocalList = localList;
			mImageCycleViewListener = imageCycleViewListener;
//			mImageViewCacheList = new ArrayList<RoundImageView>();
		}

		@Override
		public int getCount() {
			return (murlList.size()+mlocalList.size());
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {

			return disPlayUrlLocalImageView(container,position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			RoundImageView view = (RoundImageView) object;
			View view = (View) object;
			container.removeView(view);
//			mImageViewCacheList.add(view);
		}

		private View disPlayUrlLocalImageView(ViewGroup container, final int position){
			String imageUrl = "";
			String title = "";
			LayoutInflater inflater = LayoutInflater.from(mContext);
			View view = inflater.inflate(R.layout.adcycleview_item,null,false);
			RoundImageView roundImageView = view.findViewById(R.id.riv_adcycleview_item);
			TextView tvTitle = view.findViewById(R.id.tv_adbanner_title);

			int localImage = 0;
			if (murlList != null && murlList.size() > position  && murlList.get(position).getTitle() != null) {

				if( murlList.get(position).getImg() != null) {
					if (!TextUtils.isEmpty(murlList.get(position).getImg().trim())) {

						imageUrl = murlList.get(position).getImg();
						Log.d("cicleimgurl", imageUrl);

					}
				}
				if (!TextUtils.isEmpty(murlList.get(position).getTitle().trim())) {
					title = murlList.get(position).getTitle();
					tvTitle.setText(title);
				}
			}
			if(murlList != null && mlocalList != null && (murlList.size()+mlocalList.size())>position && murlList.size() <= position){
				localImage = mlocalList.get(position - murlList.size());
			}
			roundImageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(position >= murlList.size()){
						return;
					}
					String articleAccount = murlList.get(position).getAccountid();
					String articleid = murlList.get(position).getArticleid();
					String title = murlList.get(position).getTitle();
					String model = murlList.get(position).getModules();
					if(articleAccount == null){
						articleAccount = "";
					}
					if(articleid == null){
						articleid = "";
					}
					if(title == null){
						title = "";
					}

					if(model == null){
						model = "";
					}
					if(model.equals("M3")) {
						Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
						intent.putExtra("articleAccount", articleAccount);
						intent.putExtra("articleid", articleid);

						intent.putExtra("title", title);
						v.getContext().startActivity(intent);
					}else if(model.equals("M4")){
						Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
						intent.putExtra("articleAccount",articleAccount);
						intent.putExtra("articleid",articleid);

						intent.putExtra("title",title);

						v.getContext().startActivity(intent);
					}else if(model.equals("M1") || model.equals("M2")){
						Intent intent = new Intent(v.getContext(), ForumDetailOneActivity.class);
						intent.putExtra("articleAccount",articleAccount);
						intent.putExtra("articleid",articleid);

						intent.putExtra("title",title);

						v.getContext().startActivity(intent);
					}
				}
			});
			/*RoundImageView imageView = null;
			if (mImageViewCacheList.isEmpty()) {
				imageView = new RoundImageView(mContext);
				imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);

			} else {
//				imageView = mImageViewCacheList.remove(0);
			}
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mImageCycleViewListener.onImageClick(murlList,mlocalList, position, v);
				}
			});
			imageView.setTag(imageUrl);*/
//			container.addView(imageView);
			if(!imageUrl.isEmpty() || !imageUrl.equals("")) {
				Log.d("imagestep","this is imageurl");
//				roundImageView.setImageResource(R.mipmap.forumdetailone2);
				mImageCycleViewListener.displayImageURL(imageUrl, roundImageView);//网络图片  imageurl
//				container.addView(view);
			}
			if(localImage != 0){
//				mImageCycleViewListener.displayImageLocal(localImage,imageView);//本地图片
//				mImageCycleViewListener.displayImageLocalRound(localImage,imageView);//本地图片
				roundImageView.setImageResource(localImage);
//				container.addView(view);
			}
			container.addView(view);
			return view;
		}

	}



	public static interface ImageCycleViewListener {
		public void displayImageURL(String imageURL, ImageView imageView);
		public void displayImageLocal(int mipmap, ImageView imageView);
		public void displayImageLocalRound(int mipmap, RoundImageView imageView);
		public void onImageClick(ArrayList<String> urlList, ArrayList<Integer> localList, int postion, View imageView);
	}

}
