package com.liltof.library.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CustomWheel extends ListView {
	// constructor 1 required for in-code creation
	public CustomWheel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// constructor 2 required for inflation from resource file

	public CustomWheel(Context context, AttributeSet attr) {

		super(context, attr);

	}

	// constructor 3 required for inflation from resource file

	public CustomWheel(Context context, AttributeSet attr, int defaultStyles) {

		super(context, attr, defaultStyles);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);

		int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

		int d = Math.min(measuredWidth, measuredHeight);

		setMeasuredDimension(d, d);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int rayon = getMeasuredHeight() / 2;
		double x0 = getMeasuredHeight() / 2;
		double y0 = getMeasuredHeight() / 2;
		double xp = ev.getX();
		double yp = ev.getY();
		// racine_carre((x_point - x_centre)² + (y_centre - y_point))
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (Math.sqrt(Math.pow(xp - x0, 2) + Math.pow(yp - y0, 2)) < rayon)
				Toast.makeText(
						getContext(),
						"Diametre : " + getMeasuredHeight() + " Coord : "
								+ (ev.getX() - rayon) + ","
								+ (ev.getY() - rayon), Toast.LENGTH_SHORT)
						.show();
		}

		return super.onTouchEvent(ev);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int height = getMeasuredHeight();

		int width = getMeasuredWidth();
		Paint circlePaint = new Paint();
		circlePaint.setColor(Color.CYAN);
		circlePaint.setStyle(Paint.Style.FILL);
		Paint arcPaint = new Paint();
		arcPaint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(width / 2, height / 2, height / 2, circlePaint);
		RectF square = new RectF(0, 0, height, height);
		arcPaint.setColor(Color.BLUE);
		canvas.drawArc(square, 270, 120, true, arcPaint);
		arcPaint.setColor(Color.BLACK);
		canvas.drawArc(square, 30, 120, true, arcPaint);
		arcPaint.setColor(Color.YELLOW);
		canvas.drawArc(square, 150, 120, true, arcPaint);
		arcPaint.setColor(Color.RED);

		double x0 = height / 2;
		double y0 = height / 2;
		double r = height / 2;
		double t = Math.PI * (120 - 90) / 180;
		double t2 = Math.PI * (240 - 90) / 180;
		canvas.drawLine((int) x0, (int) y0, (int) (x0 + r * Math.cos(t)),
				(int) (y0 + r * Math.sin(t)), arcPaint);
		canvas.drawLine((int) x0, (int) y0, (int) (x0 + r * Math.cos(t2)),
				(int) (y0 + r * Math.sin(t2)), arcPaint);
	}
}
