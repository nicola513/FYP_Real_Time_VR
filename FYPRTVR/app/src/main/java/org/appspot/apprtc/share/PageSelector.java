package org.appspot.apprtc.share;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PageSelector extends View {
    private static final int CIRCLE_RADIUS = 20;
    private static final int CIRCLE_MARGIN = 20;
    private static final int CIRCLE_SPACING = CIRCLE_RADIUS * 2 + CIRCLE_MARGIN;
    private Paint paintGray;
    private Paint paintWhite;

    private int mPosition;
    private int mImageCount;

    public PageSelector(Context context) {
        super(context);
        init();
    }

    public PageSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int start_x = canvas.getWidth() / 2 - (CIRCLE_SPACING) * (mImageCount / 2);
        if (mImageCount % 2 == 0) {
            start_x += CIRCLE_SPACING / 2;
        }

        for (int i = 0; i < mImageCount; ++i) {
            Paint paint = (i == mPosition) ? paintWhite : paintGray;
            float x = start_x + i * CIRCLE_SPACING;
            float y = canvas.getHeight() / 2;
            canvas.drawCircle(x, y, CIRCLE_RADIUS, paint);
        }
    }

    public void setPosition(int position) {
        this.mPosition = position;
        invalidate();
    }

    public void setImageCount(int imageCount) {
        this.mImageCount = imageCount;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(CIRCLE_SPACING * mImageCount, CIRCLE_SPACING);
    }

    private void init() {
        paintGray = new Paint();
        paintGray.setStyle(Paint.Style.FILL);
        paintGray.setColor(Color.GRAY);

        paintWhite = new Paint();
        paintWhite.setStyle(Paint.Style.FILL);
        paintWhite.setColor(Color.WHITE);
    }

}
