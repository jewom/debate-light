package com.liltof.library.map;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class PolyLine extends Overlay {
	private ArrayList<GeoPoint> list = new ArrayList<GeoPoint>();
	private Integer color = Color.BLUE;
	private Style style = Paint.Style.STROKE;
	private Cap cap =  Paint.Cap.ROUND;
	private Join join = Paint.Join.BEVEL;
	private Projection projection;
	public PolyLine(ArrayList<GeoPoint> listOfPoints) {
		list = listOfPoints;
	}

	public void draw(Canvas canvas, MapView mapv, boolean shadow) {
		super.draw(canvas, mapv, shadow);
		if (list.size() > 1) {
			projection = mapv.getProjection();
			Paint mPaint = new Paint();
			mPaint.setDither(true);
			mPaint.setColor(color);
			
			mPaint.setStyle(style);
			mPaint.setStrokeJoin(join);
			mPaint.setStrokeCap(cap);
			mPaint.setStrokeWidth(2);
			Path path = new Path();
			Point p1 = new Point();
			projection.toPixels(list.get(0), p1);
			path.moveTo(p1.x, p1.y);
			Point old = p1;
			for (int i = 1; i < list.size(); i++) {
				
				
				Point p2 = new Point();
				projection.toPixels(list.get(i), p2);
				path.lineTo(p2.x, p2.y);
				Log.d("DISTANCE ", "" + Math.sqrt(Math.pow(old.x - p2.x, 2) + Math.pow(old.y - p2.y, 2)));
				
			}
			path.lineTo(p1.x, p1.y);
		
			canvas.drawPath(path, mPaint);
		}

	}
}