package fit.magic.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.mediapipe.formats.proto.LandmarkProto;

import java.util.List;

public class DataPointsView extends View implements LandmarksListListener {

    private final Object lock = new Object();

    private List<LandmarkProto.NormalizedLandmark> landmarks;

    private static final float TEXT_SIZE = 30.0f;

    private Paint boxPaint;
    private Paint textPaint;

    private RectF rect = new RectF();

    public DataPointsView(Context context) {
        super(context);

        init();
    }

    public DataPointsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public DataPointsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public DataPointsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        boxPaint = new Paint();
        boxPaint.setColor(Color.WHITE);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(TEXT_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        System.out.println();

        if(getWidth() == 0 || getHeight() == 0) {
            return;
        }

        if(landmarks != null) {
            float textHeight = TEXT_SIZE * 1.5f;

            float padding = getWidth() / 33f;
            float boxHeight = (textHeight * 6) + (padding * 2);

            float boxTop = getHeight() - padding - boxHeight;
            rect.set(padding, boxTop, getWidth() - padding, getHeight() - padding);

            canvas.drawRoundRect(rect, padding, padding, boxPaint);

            float startX = padding * 2;
            float startY = boxTop + padding;

            float x = startX;
            float y = startY;

            float textWidth = TEXT_SIZE * 5f;

            for (LandmarkProto.NormalizedLandmark landmark : landmarks) {
                String text = "[" + ((int) (landmark.getX() * 1000) ) + "," + ((int) (landmark.getY() * 1000) ) + "]";
                canvas.drawText(text, x, y + TEXT_SIZE, textPaint);

                x += textWidth;

                if (x + textWidth > canvas.getWidth()) {
                    x = startX;
                    y += textHeight;
                }
            }
        }
    }

    @Override
    public void listUpdated(List<LandmarkProto.NormalizedLandmark> landmarks) {
        this.landmarks = landmarks;
        invalidate();
    }
}
