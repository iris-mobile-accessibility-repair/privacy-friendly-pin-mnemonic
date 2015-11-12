package de.tudarmstadt.informatik.secuso.privacyfriendlypin.pinhelpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by yonjuni on 10.11.15.
 */
public class DrawView extends View {

    Paint paint;
    View startView;
    View endView;
    int digitOne;
    int digitTwo;

    public DrawView(Context context, View startView, View endView, int digitOne, int digitTwo) {
        super(context);
        this.startView = startView;
        this.endView = endView;
        this.digitOne = digitOne;
        this.digitTwo = digitTwo;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(startView.getX() + 90, startView.getY() + 80, endView.getX() + 90, endView.getY() + 80, paint);
        drawArrowHead(canvas);
    }

    public void drawArrowHead(Canvas canvas) {
        Path path = new Path();

        //draws triangle pointing to the right
        path.moveTo(endView.getX() + 50, endView.getY() + 105);
        path.lineTo(endView.getX() + 98, endView.getY() + 78);
        path.lineTo(endView.getX() + 50, endView.getY() + 60);
        path.close();

        //turns triangle
        Matrix matrix = new Matrix();
        matrix.reset();
        float rotate = whichArrow(startView, endView, digitOne, digitTwo);
        matrix.postRotate(rotate, endView.getX() + 90, endView.getY() + 80);
        path.transform(matrix);

        canvas.drawPath(path, paint);
    }

    public int whichArrow(View view1, View view2, int one, int two){
        //Arrow should point from left to right
        if ((one < two) && (view1.getY() == view2.getY())) {
            return 0;
        }
        //Arrow should point from right to left
        if ((one > two) && (view1.getY() == view2.getY())) {
            return 180;
        }
        // Arrow should point downwards
        if ((one < two) && (view1.getX() == view2.getX())) {
            return 90;
        }
        // Arrow should point upwards
        if ((((one > two)||( one == 0)) && (view1.getX() == view2.getX())))  {
            return 270;
        }
        // Arrow should point down from left to right
        if ((one < two) && (two-one == 4 || two-one == 8)) {
            return 45;
        }
        // Arrow should point down from right to left
        if ((one > two) && (one-two == 4 || one-two == 8)) {
            return 135;
        }
        //TODO Add other turns
        // Arrow should point up from right to left
        if ((one < two) && (one-two == 4)) {
            return 0;
        }
        // Arrow should point up from left to right
        if ((one > two) && (two-one == 4 || one-two == 2)) {
            return 270;
        }
        return 0;
    }

    public void setStrokeWidth(int strokeWidth) {
        paint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public void setColor(int color) {
        paint.setColor(color);
        invalidate();
    }
}
