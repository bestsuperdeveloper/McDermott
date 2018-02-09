package zdalyapp.mayah.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Constants;
import zdalyapp.mayah.global.Utils;

/**
 * TODO: document your custom view class.
 */
public class MCGraphView extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private boolean mDynamicWidth;

    private JSONObject graphJson;
    public MCGraphView(Context context) {
        super(context);
        Log.d("init", "init1");
        init(null, 0);
    }

    public MCGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("init", "init2");
        init(attrs, 0);
    }

    public MCGraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.d("init", "init3");
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MCGraphView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.MCGraphView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.MCGraphView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.MCGraphView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.MCGraphView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.MCGraphView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft() + 70;
        int paddingTop = getPaddingTop() + 50;
        int paddingRight = getPaddingRight() + 30;
        int paddingBottom = getPaddingBottom() + 50;
        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);
        drawGraph(canvas);
        // Draw the text.


        // Draw the example drawable on top of the text.

    }
    protected static final PointF PointOnCircle(float radius, float angleInDegrees, PointF origin) {
        // Convert from degrees to radians via multiplication by PI/180
        float x = (float) (radius * Math.cos(angleInDegrees * Math.PI / 180F)) + origin.x;
        float y = (float) (radius * Math.sin(angleInDegrees * Math.PI / 180F)) + origin.y;

        return new PointF(x, y);
    }
    private void drawGraph(Canvas canvas) {


        int paddingLeft = getPaddingLeft() + 70;
        int paddingTop = getPaddingTop() + 50;
        int paddingRight = getPaddingRight() + 30;
        int paddingBottom = getPaddingBottom() + 50;
        float barWidth = 50;
        float barSpace = 4;
        float barGroupSpace = 50;
        float startOffset = 64 + paddingLeft;

        int columnCnt = 0;
        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        int yAxisHeight = contentHeight;

        ArrayList<String> yAxisStrArr = new ArrayList<>();
        ArrayList<String> xAxisStrArr = new ArrayList<>();
       // canvas.rotate(45, paddingLeft + (contentWidth - mTextWidth) / 2, paddingTop + (contentHeight + mTextHeight) / 2);

         try {
            JSONObject jsonObject = new JSONObject(mExampleString);
            boolean isStack = jsonObject.getBoolean("isStack");


            JSONArray configArray = jsonObject.getJSONArray("configuration");
            int configlength = configArray.length();
            for (int i = 0; i < configlength; i++)
            {
                JSONObject jsonObject1 = configArray.getJSONObject(i);
                String type = jsonObject1.getString("type");
                if (type.equals("column"))
                    columnCnt++;
            }
            if (isStack == true)
                columnCnt = 1;

            float barGroupWidth = (columnCnt * (barWidth + barSpace));

            if (columnCnt == 0)
                barGroupSpace = barGroupSpace + barWidth;

            JSONArray values = jsonObject.getJSONArray("values");
            int valueSize = values.length();
            Paint drawPaint = new Paint();
            drawPaint.setColor(Color.RED);
            drawPaint.setAntiAlias(true);
            drawPaint.setStrokeWidth(5);
            drawPaint.setStyle(Paint.Style.STROKE);
            drawPaint.setStrokeJoin(Paint.Join.ROUND);
            drawPaint.setStrokeCap(Paint.Cap.ROUND);
         //   canvas.drawRect(paddingLeft, paddingTop, paddingLeft + 100, paddingTop + 100, drawPaint);

      //      Utils.drawTextRotate("hello", -45, 100, 100, 30, canvas);

            float sumValue[] = new float[valueSize];
            for (int i = 0; i < valueSize; i++)
                sumValue[i] = 0;
             float eachValHeight = 0;
             float maxBarVal = 0;
             float minBarVal = 1000000;
             float maxYAxis, minYAxis;
             float maxTotalValue = 0;
             for (int i = 0; i < valueSize; i++)
             {
                 JSONObject valueObj = values.getJSONObject(i);
                 if (valueObj.has("Total"))
                 {
                     if (maxTotalValue < (float) valueObj.getDouble("Total"))
                         maxTotalValue = (float) valueObj.getDouble("Total");
                 }
             }
            if (isStack == true)
            {
                for (int i = 0; i < valueSize; i++)
                {
                    JSONObject jsonObject1 = values.getJSONObject(i);
                    float sumVal = 0;
                    for (int j = 0; j < configlength; j++) {
                        JSONObject configObj = configArray.getJSONObject(j);
                        String title = configObj.getString("title");
                        if (jsonObject1.has(title))
                        {
                            float barVal = (float)jsonObject1.getDouble(title);
                            sumVal += barVal;
                        }
                    }
                    if (maxBarVal < sumVal)
                        maxBarVal = sumVal;
                }
                maxYAxis = maxBarVal + maxBarVal / 50;
                minYAxis = 0;

                if (maxTotalValue > 0)
                {
                    if (maxTotalValue - minYAxis > 5)
                    {
                        maxYAxis = maxTotalValue;
                    }
                    else
                        maxYAxis = maxTotalValue + (maxTotalValue * 2) / 100;
                }
            }
            else
            {
                for (int i = 0; i < valueSize; i++)
                {
                    JSONObject jsonObject1 = values.getJSONObject(i);
                    float sumVal = 0;
                    for (int j = 0; j < configlength; j++) {
                        JSONObject configObj = configArray.getJSONObject(j);
                        String title = configObj.getString("title");
                        if (jsonObject1.has(title))
                        {
                            float barVal = (float)jsonObject1.getDouble(title);
                            if (maxBarVal < barVal)
                                maxBarVal = barVal;
                            if (minBarVal > barVal)
                                minBarVal = barVal;
                        }
                    }
                }
                maxYAxis = maxBarVal + maxBarVal / 50;
                minYAxis = minBarVal - minBarVal / 50;
            }
            double yAxisOffset;
            yAxisOffset = (maxYAxis - minYAxis) / 4;
            if (maxBarVal == minBarVal)
            {
                yAxisStrArr.add("0.0");
                String minYAxisStr = String.format("%d", (int)minYAxis);
                if (minYAxisStr.length() >= 5)
                    minYAxisStr = String.format("%dk", (int)(minYAxis / 1000));
                yAxisStrArr.add(minYAxisStr);
            }
            else
            {
                if (maxYAxis - minYAxis > 5)
                {
                    for (int i = 0; i < 5; i++)
                    {
                        String yAxisStr = String.format("%d", (int)(i * yAxisOffset + minYAxis));
                        if (yAxisStr.length() >= 5)
                        {
                            yAxisStr = String.format("%dk", (int)((i * yAxisOffset + minYAxis) / 1000));
                        }
                        yAxisStrArr.add(yAxisStr);
                    }
                }
                else
                {
                    for (int i = 0; i < 5; i++)
                    {
                        String yAxisStr = String.format("%.1f", (i * yAxisOffset + minYAxis));
                        if (yAxisStr.length() >= 5)
                        {
                            yAxisStr = String.format("%.1fk", ((i * yAxisOffset + minYAxis) / 1000));
                        }
                        yAxisStrArr.add(yAxisStr);
                    }
                }
            }
            if (maxBarVal != 0)
                eachValHeight = yAxisHeight / (maxYAxis - minYAxis);float yAxisUnitHeight = yAxisHeight / (yAxisStrArr.size() - 1);


             // Draw Y-Axis line and label

            Paint yAxisLinePaint = new Paint();
            yAxisLinePaint.setColor(Color.RED);
             yAxisLinePaint.setAntiAlias(true);
             yAxisLinePaint.setStrokeWidth(8);
             yAxisLinePaint.setStyle(Paint.Style.STROKE);
             yAxisLinePaint.setStrokeJoin(Paint.Join.ROUND);
             yAxisLinePaint.setStrokeCap(Paint.Cap.SQUARE);
             Paint yGridLinePaint = new Paint();
             yGridLinePaint.setColor(Color.GRAY);
             yGridLinePaint.setAntiAlias(true);
             yGridLinePaint.setStrokeWidth(3);
             yGridLinePaint.setStyle(Paint.Style.STROKE);
             yGridLinePaint.setStrokeJoin(Paint.Join.ROUND);
             yGridLinePaint.setStrokeCap(Paint.Cap.SQUARE);

            canvas.drawLine(paddingLeft, paddingBottom, paddingLeft, paddingBottom + contentHeight,  yAxisLinePaint);

             Paint paint = new Paint();
             paint.setStyle(Paint.Style.FILL);

             paint.setColor(Color.GRAY);
             paint.setStrokeWidth(2);
             paint.setTextSize(30);
             paint.setTextAlign(Paint.Align.RIGHT);

             for (int i = 0; i < yAxisStrArr.size(); i++)
            {
                canvas.drawLine(paddingLeft - 30, paddingBottom + i * yAxisUnitHeight, paddingLeft, paddingBottom + i * yAxisUnitHeight, yAxisLinePaint);
                canvas.drawLine(paddingLeft + 6 , paddingBottom + i * yAxisUnitHeight, paddingLeft + contentWidth, paddingBottom + i * yAxisUnitHeight, yGridLinePaint);
                canvas.drawText(yAxisStrArr.get(yAxisStrArr.size() - 1 - i), paddingLeft - 50, paddingBottom + i * yAxisUnitHeight + 12, paint);
            }

             Paint xAxisLinePaint = new Paint();
             xAxisLinePaint.setColor(Color.BLACK);
             xAxisLinePaint.setAntiAlias(true);
             xAxisLinePaint.setStrokeWidth(7);
             xAxisLinePaint.setStyle(Paint.Style.STROKE);
             xAxisLinePaint.setStrokeJoin(Paint.Join.ROUND);
             xAxisLinePaint.setStrokeCap(Paint.Cap.SQUARE);
             canvas.drawLine(paddingLeft , paddingBottom + contentHeight - 2, paddingLeft + contentWidth, paddingBottom + contentHeight - 2 , xAxisLinePaint);


             // Draw Bar and Line Height

             int columnIndex = 0;
             int deltaYPadding = 5;
             for (int i = 0; i < configlength; i++)
             {
                 JSONObject configObj = configArray.getJSONObject(i);
                 String title = configObj.getString("title");
                 String type = configObj.getString("type");
                 int lineAlpha = (int)(configObj.getDouble("lineAlpha") * 255);
                 int fillAlpha = (int)(configObj.getDouble("fillAlphas")* 255);

                 int fillColor = 0;
                 if (configObj.has("fillColors")) {
                     fillColor = Color.parseColor(configObj.getString("fillColors"));

                 }
                 else {

                     fillColor = Constants.GraphColorList.get(i);
                 }
                 int lineColor = 0;
                 if (configObj.has("lineColor"))
                     lineColor = Color.parseColor(configObj.getString("lineColor"));
                 else
                     lineColor = Constants.GraphColorList.get(i);

                 if (type.equals("column"))
                 {
                     if (isStack == true)
                     {
                         for (int j = 0; j < valueSize; j++)
                         {

                             JSONObject valueObj = values.getJSONObject(j);

                             float barVal = 0;
                             if (valueObj.has(title))
                                 barVal = (float) valueObj.getDouble(title);
                             float sumVal = sumValue[j];
                            sumVal += barVal;
                            sumValue[j] = sumVal;

                             Paint paintRect = new Paint();
                             paint.setStyle(Paint.Style.FILL);
                             paintRect.setStrokeJoin(Paint.Join.ROUND);
                             paintRect.setStrokeCap(Paint.Cap.SQUARE);
                             paintRect.setColor(fillColor);
                             paintRect.setAlpha(fillAlpha);
                            Rect rect = new Rect((int)(startOffset + j * (barGroupWidth + barGroupSpace)), (int)(yAxisHeight - sumVal * eachValHeight + paddingBottom - deltaYPadding),
                                    (int)(startOffset + j * (barGroupWidth + barGroupSpace) + barWidth), (int)(barVal * eachValHeight + yAxisHeight - sumVal * eachValHeight + paddingBottom - deltaYPadding));
                            canvas.drawRect(rect, paintRect);

                         }
                     }
                     else
                     {
                         for (int j = 0; j < valueSize; j++)
                         {
                             JSONObject valueObj = values.getJSONObject(j);
                             float barVal = (float) valueObj.getDouble(title) - minYAxis;
                             if (barVal < 0) barVal = 0;
                             Rect rect = new Rect((int)(startOffset + columnIndex * (barWidth + barSpace) +  j * (barGroupWidth + barGroupSpace)) , (int)(yAxisHeight - barVal * eachValHeight + paddingBottom - deltaYPadding),
                                     (int)(barWidth + startOffset + columnIndex * (barWidth + barSpace) +  j * (barGroupWidth + barGroupSpace)), (int)(barVal * eachValHeight + yAxisHeight - barVal * eachValHeight + paddingBottom - deltaYPadding));
                             Paint paintRect = new Paint();
                             paintRect.setStyle(Paint.Style.FILL);
                             paintRect.setStrokeJoin(Paint.Join.ROUND);
                             paintRect.setStrokeCap(Paint.Cap.SQUARE);
                             paintRect.setColor(fillColor);
                             paintRect.setAlpha(fillAlpha);
                             canvas.drawRect(rect, paintRect);
                         }
                         columnIndex ++;
                     }
                 }
                 else
                 {
                     ArrayList<PointF> linePointArr = new ArrayList<>();
                     for (int j = 0; j < valueSize; j++)
                     {
                         JSONObject valueObj = values.getJSONObject(j);
                         float lineVal = (float) valueObj.getDouble(title) - minYAxis;
                         if (lineVal < 0) lineVal = 0;
                        float middlePosInBars = startOffset + (barGroupSpace + barGroupWidth) * j + (barGroupWidth - barSpace) / 2;
                        if (j == 0)
                        {
                            linePointArr.add(new PointF(middlePosInBars, yAxisHeight));
                        }

                        linePointArr.add(new PointF(middlePosInBars, (yAxisHeight - lineVal * eachValHeight)));
                        if (j == valueSize - 1)
                        {
                            linePointArr.add(new PointF(middlePosInBars, yAxisHeight));
                        }
                     }


//                     for (int j = 0; j < linePointArr.size(); j++)
//                     {
//                        PointF point = linePointArr.get(i);
//                        canvas.drawCircle(point.x, point.y, 5, circlePaint);
//                     }
                 }
             }

             xAxisLinePaint.setColor(Color.GRAY);

             for (int i = 0; i < valueSize; i++)
             {

                 JSONObject valueObj = values.getJSONObject(i);
                 String dateString = valueObj.getString("ValueDateString");
                 float middlePosInBars = startOffset + (barGroupSpace + barGroupWidth) * i + (barGroupWidth - barSpace) / 2;
                 canvas.drawLine((int) middlePosInBars, paddingBottom + contentHeight - 2, (int)middlePosInBars, paddingBottom + contentHeight + 10, xAxisLinePaint);
             }



             // Draw Dot
             Paint circlePaint = new Paint();
             circlePaint.setStyle(Paint.Style.FILL);
             circlePaint.setColor(Color.RED);
             for (int i = 0; i < configlength; i++)
             {
                 JSONObject configObj = configArray.getJSONObject(i);
                 String title = configObj.getString("title");
                 String type = configObj.getString("type");

                 int lineColor = 0;
                 if (configObj.has("lineColor")) {
                     lineColor = Color.parseColor(configObj.getString("lineColor"));

                 }
                 else {
                     lineColor = Constants.GraphColorList.get(i);
                 }
                 float px = 0, py = 0;
                 Paint linePaint = new Paint();
                 linePaint.setColor(lineColor);
                 linePaint.setAntiAlias(true);
                 linePaint.setStrokeWidth(3);
                 linePaint.setStyle(Paint.Style.STROKE);
                 linePaint.setStrokeJoin(Paint.Join.ROUND);
                 linePaint.setStrokeCap(Paint.Cap.ROUND);
                 if (type.equals("line"))
                 {
                     for (int j = 0; j < valueSize; j++) {
                         JSONObject valueObj = values.getJSONObject(j);
                         float lineVal = (float)valueObj.getDouble(title) - minYAxis;
                         if (lineVal < 0)
                             lineVal = 0;

                         float middlePosInBars = startOffset + (barGroupSpace + barGroupWidth) * j + (barGroupWidth - barSpace) / 2;
                         canvas.drawCircle(middlePosInBars, yAxisHeight - lineVal * eachValHeight + paddingBottom - 2, 7, circlePaint);
                         if (px != 0 && py != 0)
                         {
                            canvas.drawLine(px, py, middlePosInBars, yAxisHeight - lineVal * eachValHeight + paddingBottom - 2, linePaint);
                         }
                         px = middlePosInBars;
                         py = yAxisHeight - lineVal * eachValHeight + paddingBottom - 2;
                     }
                 }
             }

             ///
             for (int i = 0; i < valueSize; i++)
             {

                 JSONObject valueObj = values.getJSONObject(i);
                 String dateString = valueObj.getString("ValueDateString");
                 float middlePosInBars = startOffset + (barGroupSpace + barGroupWidth) * i + (barGroupWidth - barSpace) / 2;

                 Paint xAxisLabelPaint = new Paint();
                 xAxisLabelPaint.setStyle(Paint.Style.FILL);

                 //    canvas.drawText(rotatedtext , 0, 0, paint);
                 xAxisLabelPaint.setStyle(Paint.Style.STROKE);
                 xAxisLabelPaint.setColor(Color.GRAY);
                 xAxisLabelPaint.setTextSize(27);

                 Rect textrect = new Rect();
                 paint.getTextBounds(dateString, 0, dateString.length(), textrect);
                 float x = middlePosInBars - textrect.width() + 20;
                 float y = paddingBottom + contentHeight + 80;
                 canvas.rotate(-45, x + textrect.exactCenterX(),y + textrect.exactCenterY());
                 canvas.drawText(dateString, x, y, xAxisLabelPaint);
                 canvas.rotate(45, x + textrect.exactCenterX(),y + textrect.exactCenterY());
             }

             int totalWidth = (int)(startOffset + (barGroupSpace + barGroupWidth) * valueSize + (barGroupWidth - barSpace) / 2);
             ViewGroup.LayoutParams params = getLayoutParams();
             int width = params.width;
             params.width = totalWidth > width ? totalWidth : width;
             setLayoutParams(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        int size = 0;
//        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
//        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
//        int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();
//
//        // set the dimensions
//        if (widthWithoutPadding > heigthWithoutPadding) {
//            size = heigthWithoutPadding;
//        } else {
//            size = widthWithoutPadding;
//        }
//
//        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
//    }
    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
