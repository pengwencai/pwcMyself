package com.cn.theodore.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
/**
 * GridView拖拽移位效果
 * @author pwc
 *
 */
public class DragGridView extends GridView {

	private int dragPosition;
	private int dropPosition;
	private int holdPosition;
	private int startPosition;
	private int specialPosition = -1;
	private int leftBottomPosition = -1;
	
	private int nColumns = 3;
	private int nRows;
	private int Remainder;
	
	private int itemTotalCount;	
	private int halfItemWidth;	

	private ImageView dragImageView = null;
	private ViewGroup dragItemView = null;

	private WindowManager windowManager = null;
	private WindowManager.LayoutParams windowParams = null;
	
	private int mLastX,xtox;
	private int mLastY,ytoy;
	private int mMoveX;
	private int mMoveY;
	private int specialItemY;
	private int leftBtmItemY;
	
	private String LastAnimationID;
	
	private boolean isCountXY = false;	
	private boolean isMoving = false;
	private boolean isFirst=true;
	private int dragPointX,dragPointY;
	private int dragOffsetX,dragOffsetY;
	//private ArrayList<ViewGroup> mItemViewList ;

	public DragGridView(Context context, AttributeSet attrs) {
		super(context, attrs);		
	}

	public DragGridView(Context context) {
		super(context);
	}

	boolean flag = false;

	public void setLongFlag(boolean temp) {
		flag = temp;
	}
	
	public boolean setOnItemLongClickListener(final MotionEvent ev) {
		this.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				int x = (int) ev.getX();
				int y = (int) ev.getY();
				mLastX=x;
				mLastY=y;
				startPosition = dragPosition = dropPosition = arg2;
				if (dragPosition == AdapterView.INVALID_POSITION) {

				}				
				ViewGroup itemView = (ViewGroup) getChildAt(dragPosition
						- getFirstVisiblePosition());
				   dragPointX = x - itemView.getLeft();
			        dragPointY = y - itemView.getTop();
			        dragOffsetX = (int) (ev.getRawX() - x);
			        dragOffsetY = (int) (ev.getRawY() - y);

				
				
				if(!isCountXY){
					halfItemWidth = itemView.getWidth()/2;
				    int rows;
				    itemTotalCount = getCount();
				    rows = itemTotalCount/nColumns;
				    Remainder = itemTotalCount%nColumns;
				    nRows =  Remainder == 0 ?  rows : rows + 1;
				    specialPosition = itemTotalCount - 1 - Remainder;
				    if(Remainder!=1)
				    	leftBottomPosition = nColumns*(nRows-1);
				    if(Remainder == 0 || nRows == 1)
				    	specialPosition = -1;			    
				    isCountXY = true;
				}
			    if(specialPosition != dragPosition && dragPosition != -1){
			        specialItemY = getChildAt(specialPosition).getTop();
			    }else{
			    	specialItemY = -1;
			    }
			    if(leftBottomPosition != dragPosition && dragPosition != -1){
			    	leftBtmItemY = getChildAt(leftBottomPosition).getTop();
			    }else{
			    	leftBtmItemY = -1;
			    }
			    
//			    if(itemView!=null&&dragPointX>itemView.getLeft()&&dragPointX<itemView.getRight()&&dragPointY>itemView.getTop()&&dragPointY<itemView.getBottom()+20){
//			    	upScrollBounce = Math.min(y-scaledTouchSlop, getHeight()/4);
//			    	downScrollBounce = Math.max(y+scaledTouchSlop, getHeight()*3/4);

			    
				dragItemView = itemView;
				itemView.destroyDrawingCache();
				itemView.setDrawingCacheEnabled(true);
				itemView.setDrawingCacheBackgroundColor(0x8d8d8d8d);
				Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache(true));
				Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());
				startDrag(bitmap, x, y);
				hideDropItem();
				itemView.setVisibility(View.INVISIBLE);				
				isMoving = false;
				return false;
			};
		});
		return super.onInterceptTouchEvent(ev);
	}
	
	public void GetItemShadow(int x,int y){
		
		
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			return setOnItemLongClickListener(ev);
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (dragImageView != null
				&& dragPosition != AdapterView.INVALID_POSITION) {
			int  x = (int) ev.getX();
			int y = (int) ev.getY();
		   if(isFirst){
			   mMoveX=x;
				mMoveY=y;
				isFirst=false;
		   }
			switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
			
				
				if(!isCountXY) {
					xtox = x-mLastX;
				    ytoy = y-mLastY;
				    isCountXY= true;
				}
				
				onDrag(x, y);
				if(!isMoving )
				    OnMove(x,y);			
				break;
			case MotionEvent.ACTION_UP:
				stopDrag();
				onDrop(x, y);
				break;
			case MotionEvent.ACTION_DOWN:
				
				break;
			}
		}
		return super.onTouchEvent(ev);
	}

	private void startDrag(Bitmap bm, int x, int y) {
		stopDrag();
		windowParams = new WindowManager.LayoutParams();
		windowParams.gravity = Gravity.TOP | Gravity.LEFT;
//		windowParams.x = dragItemView.getLeft() + 8;
//		windowParams.y = dragItemView.getTop()+(int)(45*Configure.screenDensity);
		windowParams.x =x-dragPointX+dragOffsetX;
		windowParams.y =y-dragPointY+dragOffsetY;
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  
				               | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE  
				                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON  
				                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN; 

//		windowParams.alpha = 0.8f;
		windowParams.format = PixelFormat.TRANSLUCENT;
		Log.e("pwc","x:"+x );
		Log.e("pwc","y:"+y );
		Log.e("pwc","dragPointX:"+dragPointX );
		Log.e("pwc","dragPointY"+dragPointY );
		Log.e("pwc","dragOffsetX:"+dragOffsetX );
		Log.e("pwc","dragPointY:"+dragPointY );
		Log.e("pwc","45*Configure.screenDensity:"+45*Configure.screenDensity );
		ImageView iv = new ImageView(getContext());
		iv.setImageBitmap(bm);
		windowManager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		windowManager.addView(iv, windowParams);
		
		dragImageView = iv;
	}
	
	public  void OnMove(int x, int y){
		

		
		
		int TempPosition = pointToPosition(x,y);
		int sOffsetY = specialItemY == -1 ? y - mLastY : y - specialItemY - halfItemWidth;
		int lOffsetY = leftBtmItemY == -1 ? y - mLastY : y - leftBtmItemY - halfItemWidth;
		if(TempPosition != AdapterView.INVALID_POSITION && TempPosition != dragPosition){
			dropPosition = TempPosition;
		}else if(specialPosition != -1 && dragPosition == specialPosition && sOffsetY >= halfItemWidth){
			dropPosition = (itemTotalCount - 1);
		}else if(leftBottomPosition != -1 && dragPosition == leftBottomPosition && lOffsetY >= halfItemWidth){
			dropPosition = (itemTotalCount - 1);
		}	
		if(dragPosition != startPosition)
			dragPosition = startPosition;
		int MoveNum = dropPosition - dragPosition;
		if(dragPosition != startPosition && dragPosition == dropPosition)
			MoveNum = 0;
		if(MoveNum != 0){
			int itemMoveNum = Math.abs(MoveNum);
			float Xoffset,Yoffset;
			for (int i = 0;i < itemMoveNum;i++){
			if(MoveNum > 0){
				holdPosition = dragPosition + 1;
				Xoffset = (dragPosition/nColumns == holdPosition/nColumns) ? (-1) : (nColumns -1);
				Yoffset = (dragPosition/nColumns == holdPosition/nColumns) ? 0 : (-1);
			}else{
				holdPosition = dragPosition - 1;
				Xoffset = (dragPosition/nColumns == holdPosition/nColumns) ? 1 : (-(nColumns-1));
				Yoffset = (dragPosition/nColumns == holdPosition/nColumns) ? 0 : 1;
			}
		    ViewGroup moveView = (ViewGroup)getChildAt(holdPosition);				
			Animation animation = getMoveAnimation(Xoffset,Yoffset);
			moveView.startAnimation(animation);
			dragPosition = holdPosition;
			if(dragPosition == dropPosition)
				LastAnimationID = animation.toString();
			final DragGridAdapter adapter = (DragGridAdapter)this.getAdapter();
			animation.setAnimationListener(new Animation.AnimationListener() {
					
				@Override
				public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
					isMoving = true;
				}
					
				@Override
				public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
				}
					
				@Override
				public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
					String animaionID = animation.toString();
					if(animaionID.equalsIgnoreCase(LastAnimationID)){
						adapter.exchange(startPosition, dropPosition);
						startPosition = dropPosition;
						isMoving = false;
					}					
				}
			});	
		  }
	   }
	}
	
	private void onDrop(int x,int y){
	    final DragGridAdapter adapter = (DragGridAdapter) this.getAdapter();
		adapter.showDropItem(true);
		adapter.notifyDataSetChanged();	
	}

	private void onDrag(int x, int y) {
		if (dragImageView != null) {
			windowParams.alpha = 0.8f;
//			windowParams.x = (x-mLastX-xtox)+dragItemView.getLeft()+8;
//			windowParams.y = (y-mLastY-ytoy)+dragItemView.getTop()+(int)(45*Configure.screenDensity);
			windowParams.x =x-dragPointX+dragOffsetX;
			windowParams.y =y-dragPointY+dragOffsetY;
//			windowParams.x = x-dragItemView.getWidth()/2;
//			windowParams.y = y-dragItemView.getHeight()/2;
			Log.i("pwc","x:"+x );
			Log.i("pwc","y:"+y );
			Log.i("pwc","dragItemView.getLeft():"+dragItemView.getLeft() );
			Log.i("pwc","dragItemView.getTop():"+dragItemView.getTop() );
			Log.i("pwc","mLastX:"+mLastX );
			Log.i("pwc","mLastY:"+mLastY );
			Log.i("pwc","45*Configure.screenDensity:"+45*Configure.screenDensity );
			Log.i("pwc","xtox:"+xtox );
			Log.i("pwc","ytoy:"+ytoy );
			
			windowManager.updateViewLayout(dragImageView, windowParams);
		}
//		int tempPosition = pointToPosition(x, y);
//		if(tempPosition!=INVALID_POSITION){
//		dragPosition = tempPosition;
//		}
//		//滚动
//		if(y<upScrollBounce||y>downScrollBounce){
//		//使用setSelection来实现滚动
//		setSelection(dragPosition);
//		}
		
	}
	
	private void stopDrag() {
		if (dragImageView != null) {
			windowManager.removeView(dragImageView);
			dragImageView = null;
		}
	}
	
	private void hideDropItem(){
		final DragGridAdapter adapter = (DragGridAdapter)this.getAdapter();
		adapter.showDropItem(false);
	}
	
	public Animation getMoveAnimation(float x,float y){
		TranslateAnimation go = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, x, 
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, y);
		go.setFillAfter(true);
		go.setDuration(300);	
		return go;
	}
//	
//	public Animation getDropAnimation(){
//		ScaleAnimation scale = new ScaleAnimation(1.2f,1.0f,1.2f,1.0f);
//		scale.setDuration(550);
//		scale.setFillAfter(true);
//		return scale;
//		
//	}
//	public Animation getDownAnimation(float x,float y){
//		AnimationSet set = new AnimationSet(true);
//		TranslateAnimation go = new TranslateAnimation(Animation.RELATIVE_TO_SELF, x, Animation.RELATIVE_TO_SELF, x, 
//				Animation.RELATIVE_TO_SELF, y, Animation.RELATIVE_TO_SELF, y);
//		go.setFillAfter(true);go.setDuration(500);
//		
//		AlphaAnimation alpha = new AlphaAnimation(0.0f, 0.0f);
//		alpha.setFillAfter(true);
//		alpha.setDuration(500);
//		
//		set.addAnimation(go);
//		set.addAnimation(alpha);
//		return set;
//	}
	

}















