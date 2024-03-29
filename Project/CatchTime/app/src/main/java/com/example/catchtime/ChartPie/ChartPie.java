package com.example.catchtime.ChartPie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.catchtime.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxt on 2017/8/5.
 * 主要包含以下处理点:
 * <p>
 * 1.仅绘制可视区域
 * 2.饼状图动画
 * 3.辅助线位置限定
 * 4.辅助文字坐标纠正
 */
public class ChartPie extends BaseChart {

    private Paint basePaint, centerPiePaint, linePaint, pointPaint, textPaint;
    private float basePadding = 30;
    private float startX, endX, startY, endY;

    private boolean isDrawLines, isDrawCenter, isUserAnimator;
    private boolean starting = false, isFirst = true;
    private float mAnimatorValue = 0;
    private int endAnimatorValue = 360;
    private ValueAnimator valueAnimator;

    private RectF selectOval;
    private Paint paint;

    private boolean isTouch=false;
    private ImageView paintImg;
    private ImageView addImg;




    /**
     * 动画执行的时长
     */
    private long duration;
    /**
     * 圆心坐标
     */
    private float[] radius;
    /**
     * 内部白色圆半径
     */
    private float whiteR;
    /**
     * 外部圆半径
     */
    private float pieR;
    /**
     * 绘制扇形的区域
     */
    private RectF areaArc;
    /**
     * 存储各个扇形圆弧中心点的坐标
     */
    private Map<Integer, float[]> arcPoints = new HashMap<>();
    /**
     * 存储各个扇形的画笔
     */
    private Map<Integer, Paint> paintMap = new HashMap<>();
    /**
     * 存储各个扇形的数据对象
     */
    private Map<Integer, ChartPieBean> pieBeanMap = new HashMap<>();

    public ChartPie(Context context) {
        super(context);
    }

    public ChartPie(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartPie(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        initPaint();
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartPie);
            try {
                duration = (long) typedArray.getFloat(R.styleable.ChartPie_pie_duration, DEFAULT_DURATION);
                isDrawCenter = typedArray.getBoolean(R.styleable.ChartPie_pie_isDrawCenter, false);
                isDrawLines = typedArray.getBoolean(R.styleable.ChartPie_pie_isDrawLines, false);
                isUserAnimator = typedArray.getBoolean(R.styleable.ChartPie_pie_isUseAnimator, false);
                int textColor = typedArray.getColor(R.styleable.ChartPie_pie_text_color, ContextCompat.getColor(context, R.color.colorAccent));
                textPaint.setColor(textColor);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            } finally {
                typedArray.recycle();
            }
        }
    }

    private void initPaint() {
        basePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        basePaint.setColor(Color.GRAY);
        basePaint.setStrokeWidth(dip2px(0.5f));
        basePaint.setTextSize(dip2px(14));
        basePaint.setTextAlign(Paint.Align.LEFT);
        basePaint.setStrokeCap(Paint.Cap.ROUND);
        basePaint.setDither(true);

        centerPiePaint = new Paint(basePaint);
        centerPiePaint.setColor(Color.WHITE);

        linePaint = new Paint(basePaint);
        linePaint.setColor(Color.RED);
        pointPaint = new Paint(basePaint);
        pointPaint.setStrokeWidth(dip2px(5));

        textPaint = new Paint(basePaint);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        Typeface font0 = Typeface.create(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
        textPaint.setTypeface(font0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            startX = getPaddingLeft() + basePadding;
            endX = getMeasuredWidth() - getPaddingRight() - basePadding;
            startY = getMeasuredHeight() - getPaddingBottom() - basePadding;
            endY = getPaddingTop() + basePadding;
            radius = new float[2];
            float R1 = startY - endY;
            float R2 = endX - -startX;
            radius[0] = startX + R2 / 2;
            radius[1] = endY + R1 / 2;
            whiteR = Math.min(R1, R2) / 6;//宽度>高度  选择高度 , 宽度<高度 选择宽度  , 能避免在大屏下坐标越界
            pieR = Math.min(R1, R2) / 2;
            areaArc = new RectF(radius[0] - pieR, radius[1] - pieR, radius[0] + pieR, radius[1] + pieR);
            selectOval = new RectF();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mAnimatorValue == 0) return;
        drawPie(canvas);//画饼图
        if (!isDrawCenter) {
            drawCenter(canvas);//画中心覆盖层
        }
        if (!isDrawLines) {
            drawLines(canvas);//画折线
        }
    }

    private void drawPie(Canvas canvas) {
        double arcPI = Math.PI * 2 / 360;//π的值
        float[] point;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        if (!pieBeanMap.isEmpty()) {
            float startAngle = 0;
            float sweepAngle = 0;
            for (int i = 0; i < pieBeanMap.size(); i++) {
                ChartPieBean bean = pieBeanMap.get(i);
                float angle = bean.startAngle + bean.sweepAngle;
                if (angle <= mAnimatorValue) {
                    startAngle = bean.startAngle;
                    sweepAngle = bean.sweepAngle;
                    if(isTouch) {
                        if (select >= 0 && i == select) {
                            selectOval = new RectF(radius[0] - pieR, radius[1] - pieR, radius[0] + pieR, radius[1] + pieR);
                            int middle = (int) ((bean.startAngle + bean.sweepAngle) / 2);
                            if (middle <= 90) {
                                int top = (int) (Math.sin(Math.toRadians(middle)) * 15);
                                int left = (int) (Math.cos(Math.toRadians(middle)) * 15);
                                selectOval.left += left;
                                selectOval.right += left;
                                selectOval.top += top;
                                selectOval.bottom += top;
                            }
                            if (middle > 90 && middle <= 180) {
                                middle = 180 - middle;
                                int top = (int) (Math.sin(Math.toRadians(middle)) * 15);
                                int left = (int) (Math.cos(Math.toRadians(middle)) * 15);
                                selectOval.left -= left;
                                selectOval.right -= left;
                                selectOval.top += top;
                                selectOval.bottom += top;
                            }
                            if (middle > 180 && middle <= 270) {
                                middle = 270 - middle;
                                int left = (int) (Math.sin(Math.toRadians(middle)) * 15);
                                int top = (int) (Math.cos(Math.toRadians(middle)) * 15);
                                selectOval.left -= left;
                                selectOval.right -= left;
                                selectOval.top -= top;
                                selectOval.bottom -= top;
                            }
                            if (middle > 270 && middle <= 360) {
                                middle = 360 - middle;
                                int top = (int) (Math.sin(Math.toRadians(middle)) * 15);
                                int left = (int) (Math.cos(Math.toRadians(middle)) * 15);
                                selectOval.left += left;
                                selectOval.right += left;
                                selectOval.top -= top;
                                selectOval.bottom -= top;
                            }
                            canvas.drawArc(selectOval, startAngle, sweepAngle, true,
                                    paintMap.get(i));

                        }else{
                            paint = new Paint(basePaint);
                            paint.setColor(Color.GRAY);
                            canvas.drawArc(areaArc, bean.startAngle, sweepAngle, true, paint);

                        }

                    } else {
                            canvas.drawArc(areaArc, bean.startAngle, sweepAngle, true, paintMap.get(i));
                            //计算当前圆弧上的中心点'

                        }

                    point = new float[2];
                    float tangle = bean.startAngle + (bean.sweepAngle)/ 2;
                    point[0] = (float) (radius[0] + pieR*2/3 * Math.cos(arcPI * tangle));
                    point[1] = (float) (radius[1] + pieR*2/3 * Math.sin(arcPI * tangle));
                    //canvas.drawText(bean.type, point[0], point[1], textPaint);
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),bean.img);
                    canvas.drawBitmap(bitmap,point[0], point[1],textPaint);
                }
            }
        }
        //drawLines(canvas);

    }



    public ChartPie setData(List<ChartPieBean> data) {
        if (data != null) {
            float total = getTotal(data);
            Paint paint;
            float rate;
            float[] point;
            double arcPI = Math.PI * 2 / (360);//π的值
            float startAngle = 0;// 扇形开始的角度
            for (int i = 0; i < data.size(); i++) {
                ChartPieBean bean = data.get(i);
                rate = bean.value / total;//当前对象值所占比例
                bean.rate = rate;
                bean.startAngle = startAngle;
                bean.sweepAngle = rate * (360-data.size());//当前对象所占比例 对应的 角度

                //计算当前圆弧上的中心点'
                if (radius != null) {
                    point = new float[2];
                    point[0] = (float) (radius[0] + pieR * Math.cos(arcPI * bean.startAngle + bean.sweepAngle));
                    point[1] = (float) (radius[1] + pieR * Math.sin(arcPI * bean.startAngle + bean.sweepAngle));
                    arcPoints.put(i, point);
                    Log.i("圆弧",arcPoints.get(i)[0]+"");
                }

                paint = new Paint(basePaint);
                paint.setColor(ContextCompat.getColor(getContext(), bean.colorRes));
                paintMap.put(i, paint);
                pieBeanMap.put(i, bean);


                startAngle += bean.sweepAngle+1;
            }
        }
        return this;
    }

    /**
     * 计算数据总和
     *
     * @param data
     * @return
     */
    private float getTotal(List<ChartPieBean> data) {
        if (data != null) {
            float total = 0;
            for (int i = 0; i < data.size(); i++) {
                total += data.get(i).value;
            }
            return total;
        }
        return 0;
    }

    private void drawCenter(Canvas canvas) {
        canvas.drawCircle(radius[0], radius[1], whiteR, centerPiePaint);
    }

    /**
     * 已知圆弧半径，圆弧夹角，起始点坐标，怎么求终点坐标？
     * 半径r,角度θ,圆弧中心(a,b)，起点坐标(x0,y0)
     * a,b请根据起点坐标折算成中心坐标
     * x=a+r*cosθ
     * y=b+r*sinθ
     * 这两个函数中的θ
     * 都是指的“弧度”而非“角度”
     * 弧度的计算公式为：2*PI/360*角度；30°角度的弧度= 2*PI/360*30
     * <p>
     * 假设一个圆的圆心坐标是(a,b)，半径为r，则圆上每个点的
     * X坐标=a + Math.sin(2*Math.PI / 360) * r ；
     * Y坐标=b + Math.cos(2*Math.PI / 360) * r
     */
    private void drawLines(Canvas canvas) {
        if (mAnimatorValue == endAnimatorValue) {
            float[] point;
            float startX;
            float startY;
            float stopX;
            float stopY;
            double arcPI = Math.PI * 2 / 360;//π的值
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float height = fontMetrics.bottom - fontMetrics.top;//计算行高

            for (int i = 0; i < pieBeanMap.size(); i++) {
                ChartPieBean bean = pieBeanMap.get(i);

                /*
                if (point[0] < radius[0] && point[1] < radius[1]) {//在圆心左上

                    point[0] -= basePadding;
                    point[1] -= basePadding;
                    startX = point[0] - basePadding / 2;
                    startY = point[1] - basePadding / 2;
                    stopX = point[0] - basePadding * 2;
                    stopY = point[1] - basePadding * 1.5f;

                    linePaint.setColor(ContextCompat.getColor(getContext(), bean.colorRes));
                    canvas.drawLine(startX, startY, stopX, stopY, linePaint);
                    canvas.drawLine(stopX, stopY, this.startX, stopY, linePaint);

                    textPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText(String.valueOf(bean.value), this.startX, stopY - height / 4, textPaint);
                    canvas.drawText(bean.type, this.startX, stopY + height / 4 * 3, textPaint);

                } else if (point[0] < radius[0] && point[1] > radius[1]) {//在圆心 左下

                    point[0] -= basePadding;
                    point[1] += basePadding;
                    startX = point[0] - basePadding / 2;
                    startY = point[1] + basePadding / 2;
                    stopX = point[0] - basePadding * 2;
                    stopY = point[1] + basePadding * 1.5f;

                    linePaint.setColor(ContextCompat.getColor(getContext(), bean.colorRes));
                    canvas.drawLine(startX, startY, stopX, stopY, linePaint);
                    canvas.drawLine(stopX, stopY, this.startX, stopY, linePaint);

                    textPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText(String.valueOf(bean.value), this.startX, stopY - height / 4, textPaint);
                    canvas.drawText(bean.type, this.startX, stopY + height / 4 * 3, textPaint);

                } else if (point[0] > radius[0] && point[1] > radius[1]) {//右下

                    point[0] += basePadding;
                    point[1] += basePadding;
                    startX = point[0] + basePadding / 2;
                    startY = point[1] + basePadding / 2;
                    stopX = point[0] + basePadding * 2;
                    stopY = point[1] + basePadding * 1.5f;

                    linePaint.setColor(ContextCompat.getColor(getContext(), bean.colorRes));
                    canvas.drawLine(startX, startY, stopX, stopY, linePaint);
                    canvas.drawLine(stopX, stopY, endX, stopY, linePaint);

                    textPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(bean.value), this.endX, stopY - height / 4, textPaint);
                    canvas.drawText(bean.type, this.endX, stopY + height / 4 * 3, textPaint);

                } else if (point[0] > radius[0] && point[1] < radius[1]) {//右上

                    point[0] += basePadding;
                    point[1] -= basePadding;
                    startX = point[0] + basePadding / 2;
                    startY = point[1] - basePadding / 2;
                    stopX = point[0] + basePadding * 2;
                    stopY = point[1] - basePadding * 1.5f;

                    linePaint.setColor(ContextCompat.getColor(getContext(), bean.colorRes));
                    canvas.drawLine(startX, startY, stopX, stopY, linePaint);
                    canvas.drawLine(stopX, stopY, endX, stopY, linePaint);

                    textPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(bean.value), this.endX, stopY - height / 4, textPaint);
                    canvas.drawText(bean.type, this.endX, stopY + height / 4 * 3, textPaint);
                }

                arcPoints.put(i, point);
                pointPaint.setColor(ContextCompat.getColor(getContext(), bean.colorRes));
                canvas.drawPoint(arcPoints.get(i)[0], arcPoints.get(i)[1], pointPaint);*/
            }
        }
    }

    public ChartPie setAnimDurationTime(long duration) {
        this.duration = duration;
        return this;
    }

    private void startAnimator() {
        if (!isUserAnimator) {
            mAnimatorValue = endAnimatorValue;
            invalidate();
            isFirst = false;
        }
        if (!isFirst) return;//只能绘制一次
        if (starting) {
            return;
        }
        starting = true;
        valueAnimator = ValueAnimator.ofFloat(0, endAnimatorValue).setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                if (starting) {
                    invalidate();
                }
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                starting = false;
                isFirst = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    public void start() {
        super.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isCover(ChartPie.this)) {
                startAnimator();
            } else {
                this.post(new Runnable() {//可以避免页面未初始化完成造成的 空白
                    @Override
                    public void run() {
                        if (isCover(ChartPie.this)) {
                            startAnimator();
                        }
                    }
                });
            }
        } else {
            this.post(new Runnable() {
                @Override
                public void run() {
                    startAnimator();
                }
            });
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (valueAnimator != null && valueAnimator.isRunning()) valueAnimator.cancel();
        super.onDetachedFromWindow();
    }

    public boolean onTouchEvent(MotionEvent event) {
        View otherView = LayoutInflater.from(getContext()).inflate(R.layout.item_chart_pie,null);
        paintImg=otherView.findViewById(R.id.chartpie_paint);
        Log.i("src",paintImg.getVisibility()+"");
        addImg=otherView.findViewById(R.id.chartpie_add);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(!isTouch) {
                isTouch = true;
                paintImg.setImageResource(R.drawable.header_left);
                addImg.setVisibility(View.VISIBLE);
            }
            else {
                isTouch = false;
                paintImg.setVisibility(View.INVISIBLE);
                addImg.setVisibility(View.INVISIBLE);
            }
            float x = event.getX();
            float y = event.getY();
            int radius = 0;
            if (x >= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((y - getMeasuredHeight() / 2) * 1.0f
                        / (x - getMeasuredWidth() / 2)) * 180 / Math.PI);
            }
            // 第二象限
            if (x <= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((getMeasuredWidth() / 2 - x)
                        / (y - getMeasuredHeight() / 2))
                        * 180 / Math.PI + 90);
            }
            // 第三象限
            if (x <= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((getMeasuredHeight() / 2 - y)
                        / (getMeasuredWidth() / 2 - x))
                        * 180 / Math.PI + 180);
            }
            // 第四象限
            if (x >= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((x - getMeasuredWidth() / 2)
                        / (getMeasuredHeight() / 2 - y))
                        * 180 / Math.PI + 270);
            }
            for (int i = 0; i < pieBeanMap.size(); i++) {
                ChartPieBean bean = pieBeanMap.get(i);
                if (bean.startAngle <= radius && bean.sweepAngle >= radius) {
                    select = i;
                    //Toast.makeText(getContext(), "点击了" +i,
                    //Toast.LENGTH_SHORT)
                     //.show();
                    invalidate();
                    return false;
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
    private int select = -1;


}