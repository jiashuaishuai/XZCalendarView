package com.example.shuaijia.xzcalendarview.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
      * ViewPager适配器
 */
public class MyViewPagerAdapter extends PagerAdapter {
    private List<View> mListViews;
      
    public MyViewPagerAdapter(List<View> mListViews) {
        this.mListViews = mListViews;  
    }  

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(mListViews.get(position));  
    }  


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
         container.addView(mListViews.get(position), 0);  
         
/////////////如果某个页卡里面有按钮或者什么控件，写在这/////////////////////////
//			Button weibo_button=(Button) findViewById(R.id.button1);//这个需要注意，我们是在重写adapter里面实例化button组件的，如果你在onCreate()方法里这样做会报错的。  
//         weibo_button.setOnClickListener(new OnClickListener() {  
//               
//             public void onClick(View v) {  
//                 Toast.makeText(getA, "click 1", Toast.LENGTH_LONG).show();
//             }  
//         }); 
         
//         Button button2=(Button) findViewById(R.id.button2);
//         if(button2!=null){
//		        button2.setOnClickListener(new OnClickListener() {  
//		              
//		            public void onClick(View v) {  
//		                Toast.makeText(MainActivity.this, "click 3", Toast.LENGTH_LONG).show();
//		            }  
//		        }); 
//         }
			//////////////////////////没有的话，这段省略//////////////////////////
         
         return mListViews.get(position);  
    }  

    @Override
    public int getCount() {           
        return  mListViews.size();  
    }  
      
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;  
    }  
}  