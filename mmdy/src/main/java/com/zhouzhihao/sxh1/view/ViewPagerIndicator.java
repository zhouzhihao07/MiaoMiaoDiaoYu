package com.zhouzhihao.sxh1.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zhouzhihao.sxh1.miaomiaodiaoyu.R;

import java.util.List;

import static org.xutils.common.util.DensityUtil.getScreenWidth;

/**
 * Created by zhouzhihao on 2016/7/15 0015.
 */
public class ViewPagerIndicator extends LinearLayout{
    /**
     * 标题正常时的颜色
     */
    private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
    /**
     * 标题选中时的颜色
     */
    private static final int COLOR_TEXT_HIGHLIGHTCOLOR = R.color.colorPrimary;
    /**
     * 默认的Tab数量
     */
    private static final int COUNT_DEFAULT_TAB = 4;
    /**
     * tab数量
     */
    private int mTabVisibleCount = COUNT_DEFAULT_TAB;
    //默认画笔
    private Paint paint;
    private ViewPager mViewPager;
    //标题列表
    private List<String> mTabTitles;
    private Context context;
    //indicator 指示器
    private Path mPath;
    /**
     * 初始时，三角形指示器的偏移量
     */
    private int mInitTranslationX;
    /**
     * 手指滑动时的偏移量
     */
    private float mTranslationX;

    /**
     * 三角形的宽度
     */
    private int mTriangleWidth;
    /**
     * 三角形的高度
     */
    private int mTriangleHeight;

    /**
     * 三角形的宽度为单个Tab的1/6
     */
    private static final float RADIO_TRIANGEL = 1.0f / 1;
    /**
     * 三角形的最大宽度
     */
    private final int DIMENSION_TRIANGEL_WIDTH = (int) (getScreenWidth() / 3 * RADIO_TRIANGEL);

    public ViewPagerIndicator(Context context) {
        super(context);
        this.context=context;
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        // 获得自定义属性，tab的数量
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ViewPagerIndicator);
        mTabVisibleCount= typedArray.getInt(R.styleable.ViewPagerIndicator_item_count,COUNT_DEFAULT_TAB);
        if (mTabVisibleCount < 0)
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        typedArray.recycle();
        //初始化画笔
      paint=new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(new CornerPathEffect(5));

    }
    /**
     * 对外的ViewPager的回调接口
     *
     * @author zhy
     *
     */
    public interface PageChangeListener
    {
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }

    // 对外的ViewPager的回调接口
    private PageChangeListener onPageChangeListener;

    // 对外的ViewPager的回调接口的设置
    public void setOnPageChangeListener(PageChangeListener pageChangeListener)
    {
        this.onPageChangeListener = pageChangeListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGEL);// 1/6 of
        // width
        mTriangleWidth = Math.min(DIMENSION_TRIANGEL_WIDTH, mTriangleWidth);

        // 初始化三角形
        initTriangle();

        // 初始时的偏移量
        mInitTranslationX = getWidth() / mTabVisibleCount / 2 - mTriangleWidth
                / 2;
    }

    private void initTriangle() {
        mPath = new Path();
        mTriangleHeight = 10;
//        mTriangleHeight = (int) (mTriangleWidth / 2 / Math.sqrt(2));
        mPath.moveTo(0, -mTriangleHeight);
        mPath.lineTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth, -mTriangleHeight);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();

    }

    /**
     * 设置布局中view的一些必要属性；如果设置了setTabTitles，布局中view则无效
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int cCount = getChildCount();

        if (cCount == 0)
            return;

        for (int i = 0; i < cCount; i++)
        {
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view
                    .getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }

    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    public void setViewPager(ViewPager viewPager, int pos) {
    mViewPager=viewPager;
    mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 滚动
            scroll(position, positionOffset);

            // 回调
            if (onPageChangeListener != null)
            {
                onPageChangeListener.onPageScrolled(position,
                        positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
// 设置字体颜色高亮
            resetTextViewColor();
            highLightTextView(position);

            // 回调
            if (onPageChangeListener != null)
            {
                onPageChangeListener.onPageSelected(position);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // 回调
            if (onPageChangeListener != null)
            {
                onPageChangeListener.onPageScrollStateChanged(state);
            }

        }
    });
    // 设置当前页
    mViewPager.setCurrentItem(pos);
    // 高亮
    highLightTextView(pos);
}
    /**
     * 设置可见的tab的数量
     *
     * @param count
     */
    public void setVisibleTabCount(int count)
    {
        this.mTabVisibleCount = count;
    }
    /**
     * 设置tab的标题内容 可选，可以自己在布局文件中写死
     *
     * @param datas
     */
    public void setTabItemTitles(List<String> datas)
    {
        // 如果传入的list有值，则移除布局文件中设置的view
        if (datas != null && datas.size() > 0)
        {
            this.removeAllViews();
            this.mTabTitles = datas;

            for (String title : mTabTitles)
            {
                // 添加view
                addView(generateTextView(title));
            }
            // 设置item的click事件
            setItemClickEvent();
        }

    }

    private void setItemClickEvent() {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    private View generateTextView(String title) {
        TextView tv=new TextView(context);
        tv.setTextSize(getResources().getDimension(R.dimen.VpInTextSize));
        tv.setWidth(getScreenWidth() / mTabVisibleCount);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setPadding(20, 20, 20, 20);

        tv.setText(title);
        return tv;
    }
    /**
     * 高亮文本
     *
     * @param position
     */
    protected void highLightTextView(int position)
    {
        View view = getChildAt(position);
        if (view instanceof TextView)
        {
            ((TextView) view).setTextColor(getResources().getColor(COLOR_TEXT_HIGHLIGHTCOLOR));
        }

    }

    /**
     * 重置文本颜色
     */
    private void resetTextViewColor()
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            View view = getChildAt(i);
            if (view instanceof TextView)
            {
                ((TextView) view).setTextColor(getResources().getColor(R.color.gary));
            }
        }
    }
    /**
     * 指示器跟随手指滚动，以及容器滚动
     *
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset)
    {
        /**
         * <pre>
         *  0-1:position=0 ;1-0:postion=0;
         * </pre>
         */
        // 不断改变偏移量，invalidate
        mTranslationX = getWidth() / mTabVisibleCount * (position + offset);

        int tabWidth = getScreenWidth() / mTabVisibleCount;

        // 容器滚动，当移动到倒数最后一个的时候，开始滚动
        if (offset > 0 && position >= (mTabVisibleCount - 2)
                && getChildCount() > mTabVisibleCount)
        {
            if (mTabVisibleCount != 1)
            {
                this.scrollTo((position - (mTabVisibleCount - 2)) * tabWidth
                        + (int) (tabWidth * offset), 0);
            } else
            // 为count为1时 的特殊处理
            {
                this.scrollTo(
                        position * tabWidth + (int) (tabWidth * offset), 0);
            }
        }

        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        // 画笔平移到正确的位置
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
        canvas.drawPath(mPath, paint);
        canvas.restore();

    }
}
