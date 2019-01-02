package com.example.hector.planets.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.example.hector.planets.Graph;
import com.example.hector.planets.pojos.GraphFunction;
import com.example.hector.planets.pojos.GraphPoints;
import com.example.hector.planets.pojos.Label;
import com.example.hector.planets.pojos.Point;
import com.example.hector.planets.pojos.ScreenPoint;
import com.example.hector.planets.services.Function;

import java.util.ArrayList;
import java.util.List;

public class GraphView extends View {
    private Graph g;
    private int labelOffset;
    private Paint paint;
    private int pointRadius;
    private int textSize;
    private int tickOffset;

    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init(){
        int strokeWidth;
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager =(WindowManager)  getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int densityDpi = metrics.densityDpi;
        if (densityDpi <= 120) {
            this.pointRadius = 3;
            this.tickOffset = 3;
            this.labelOffset = 3;
            this.textSize = 7;
            strokeWidth = 1;
        } else if (densityDpi <= 160) {
            this.pointRadius = 3;
            this.tickOffset = 4;
            this.labelOffset = 3;
            this.textSize = 8;
            strokeWidth = 1;
        } else if (densityDpi <= 240) {
            this.pointRadius = 4;
            this.tickOffset = 7;
            this.labelOffset = 5;
            this.textSize = 15;
            strokeWidth = 1;
        } else if (densityDpi <= 320) {
            this.pointRadius = 6;
            this.tickOffset = 7;
            this.labelOffset = 5;
            this.textSize = 20;
            strokeWidth = 2;
        } else if (densityDpi <= 480) {
            this.pointRadius = 8;
            this.tickOffset = 9;
            this.labelOffset = 7;
            this.textSize = 30;
            strokeWidth = 2;
        } else {
            this.pointRadius = 10;
            this.tickOffset = 10;
            this.labelOffset = 9;
            this.textSize = 35;
            strokeWidth = 3;
        }
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth((float) strokeWidth);

    }

    public void setGraph(Graph g){
        this.g = g;
        invalidate();
    }

    public int toScreenX(double x) {
        return (int) ((x - this.g.getxMin()) * (((double) getWidth()) / (this.g.getxMax() - this.g.getxMin())));
    }

    public int toScreenY(double y) {
        return (int) ((y - this.g.getyMax()) * (((double) getHeight()) / (this.g.getyMin() - this.g.getyMax())));
    }

    public double toWorldX(int x) {
        return (((double) x) * ((this.g.getxMax() - this.g.getxMin()) / ((double) getWidth()))) + this.g.getxMin();
    }

    public double toWorldY(int y) {
        return (((double) y) * ((this.g.getyMin() - this.g.getyMax()) / ((double) getHeight()))) + this.g.getyMax();
    }

    protected void drawViewFrame(Canvas canvas) {
        canvas.drawColor(this.g.getBackgroundColor());
        this.paint.setColor(this.g.getAxesColor());
        this.paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.paint);
    }

    private void drawAxes(Canvas canvas){
        int axisX = toScreenX(this.g.getAxisx());
        int axisY = toScreenY(this.g.getAxisY());
        Rect bounds = new Rect();
        this.paint.setColor(this.g.getAxesColor());
        this.paint.setTextSize((float) this.textSize);

        if (isOnScreenY(axisY)) {
            canvas.drawLine(0.0f, (float) axisY, (float) getWidth(), (float) axisY, this.paint);
            this.paint.setTextAlign(Paint.Align.CENTER);
            String xTickLabel;
            int xTickLabelHeight;
            int screenXTick;
            if (this.g.getxLabels().size() > 0) {
                for (Label label : this.g.getxLabels()) {
                    xTickLabel = label.getLabel();
                    this.paint.getTextBounds(xTickLabel, 0, xTickLabel.length(), bounds);
                    xTickLabelHeight = bounds.height();
                    screenXTick = toScreenX(label.getTick());
                    if (isOnScreenX(screenXTick)) {
                        canvas.drawLine((float) screenXTick, (float) (axisY - this.tickOffset), (float) screenXTick, (float) (this.tickOffset + axisY), this.paint);
                        canvas.drawText(xTickLabel, (float) screenXTick, (float) (((this.tickOffset + axisY) + this.labelOffset) + xTickLabelHeight), this.paint);
                    }
                }
            } else {
                for (Double xTick : this.g.getxTicks()) {
                    xTickLabel = Double.toString(xTick.doubleValue());
                    if (xTick.doubleValue() == Math.rint(xTick.doubleValue())) {
                        xTickLabel = Long.toString(Math.round(xTick.doubleValue()));
                    }
                    this.paint.getTextBounds(xTickLabel, 0, xTickLabel.length(), bounds);
                    xTickLabelHeight = bounds.height();
                    screenXTick = toScreenX(xTick.doubleValue());
                    if (isOnScreenX(screenXTick)) {
                        canvas.drawLine((float) screenXTick, (float) (axisY - this.tickOffset), (float) screenXTick, (float) (this.tickOffset + axisY), this.paint);
                        canvas.drawText(xTickLabel, (float) screenXTick, (float) (((this.tickOffset + axisY) + this.labelOffset) + xTickLabelHeight), this.paint);
                    }
                }
            }
        }
        if (isOnScreenX(axisX)) {
            canvas.drawLine((float) axisX, 0.0f, (float) axisX, (float) getHeight(), this.paint);
            this.paint.setTextAlign(Paint.Align.CENTER);
            String yTickLabel;
            int yTickLabelHeight;
            int yTickLabelWidth;
            int screenYTick;
            if (this.g.getyLabels().size() > 0) {
                for (Label label2 : this.g.getyLabels()) {
                    yTickLabel = label2.getLabel();
                    this.paint.getTextBounds(yTickLabel, 0, yTickLabel.length(), bounds);
                    yTickLabelHeight = bounds.height();
                    yTickLabelWidth = bounds.width();
                    screenYTick = toScreenY(label2.getTick());
                    if (isOnScreenY(screenYTick)) {
                        canvas.drawLine((float) (axisX - this.tickOffset), (float) screenYTick, (float) (this.tickOffset + axisX), (float) screenYTick, this.paint);
                        canvas.drawText(yTickLabel, (float) (((axisX - this.tickOffset) - this.labelOffset) - (yTickLabelWidth / 2)), (float) ((yTickLabelHeight / 2) + screenYTick), this.paint);
                    }
                }
                return;
            }
            for (Double doubleValue : this.g.getyTicks()) {
                double yTick = doubleValue.doubleValue();
                yTickLabel = Double.toString(yTick);
                if (yTick == Math.rint(yTick)) {
                    yTickLabel = Long.toString(Math.round(yTick));
                }
                this.paint.getTextBounds(yTickLabel, 0, yTickLabel.length(), bounds);
                yTickLabelHeight = bounds.height();
                yTickLabelWidth = bounds.width();
                screenYTick = toScreenY(yTick);
                if (isOnScreenY(screenYTick)) {
                    canvas.drawLine((float) (axisX - this.tickOffset), (float) screenYTick, (float) (this.tickOffset + axisX), (float) screenYTick, this.paint);
                    canvas.drawText(yTickLabel, (float) (((axisX - this.tickOffset) - this.labelOffset) - (yTickLabelWidth / 2)), (float) ((yTickLabelHeight / 2) + screenYTick), this.paint);
                }
            }
        }
    }


    private void drawFunction(GraphFunction graphFunction, Canvas canvas){
        List<ScreenPoint> screenPoints = getScreenPointsForFunction(graphFunction.getFunction());
        Path path = new Path();
        if (screenPoints.size() > 0) {
            ScreenPoint screenPoint = (ScreenPoint) screenPoints.get(0);
            int screenX = screenPoint.getX();
            path.moveTo((float) screenPoint.getX(), (float) screenPoint.getY());
            for (int i = 1; i < screenPoints.size(); i++) {
                screenPoint = (ScreenPoint) screenPoints.get(i);
                if (screenPoint.getX() == screenX + 1) {
                    path.lineTo((float) screenPoint.getX(), (float) screenPoint.getY());
                } else {
                    path.moveTo((float) screenPoint.getX(), (float) screenPoint.getY());
                }
                screenX = screenPoint.getX();
            }
        }
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(graphFunction.getColor());
        canvas.drawPath(path, this.paint);

    }

    private void drawLineGraph(GraphPoints graphPoints, Canvas canvas) {
        drawPoints(graphPoints, canvas);
        Path path = new Path();
        List<Point> points = graphPoints.getPoints();
        Point point = (Point) points.get(0);
        path.moveTo((float) toScreenX(point.getX()), (float) toScreenY(point.getY()));
        for (int i = 1; i < points.size(); i++) {
            point = (Point) points.get(i);
            path.lineTo((float) toScreenX(point.getX()), (float) toScreenY(point.getY()));
        }
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(graphPoints.getColor());
        canvas.drawPath(path, this.paint);
    }


    private void drawGraphPoints(Canvas canvas) {
        for (GraphPoints graphPoint : this.g.getGraphPoints()) {
            drawPoints(graphPoint, canvas);
        }
    }

    private void drawPoints(GraphPoints graphPoint, Canvas canvas) {
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(graphPoint.getColor());
        for (Point point : graphPoint.getPoints()) {
            int screenX = toScreenX(point.getX());
            int screenY = toScreenY(point.getY());
            if (isNearScreenX(screenX) && isNearScreenY(screenY)) {
                canvas.drawCircle((float) screenX, (float) screenY, (float) this.pointRadius, this.paint);
            }
        }
    }

    private void drawFunctions(Canvas canvas) {
        for (GraphFunction graphFunction : this.g.getFunctions()) {
            drawFunction(graphFunction, canvas);
        }
    }

    private void drawLineGraphs(Canvas canvas) {
        for (GraphPoints graphPoints : this.g.getLineGraphs()) {
            drawLineGraph(graphPoints, canvas);
        }
    }


    private List<ScreenPoint> getScreenPointsForFunction(Function f) {
        List<ScreenPoint> screenPoints = new ArrayList(getWidth() + 2);
        for (int screenX = -1; screenX <= getWidth(); screenX++) {
            double y = f.apply(toWorldX(screenX));
            if (isFinite(y)) {
                int screenY = toScreenY(y);
                if (isNearScreenY(screenY)) {
                    screenPoints.add(new ScreenPoint(screenX, screenY));
                }
            }
        }
        return screenPoints;
    }


    private ScreenPoint nextPoint(int screenX, Function f) {
        int screenY = Integer.MAX_VALUE;
        do {
            double y = f.apply(toWorldX(screenX));
            if (isFinite(y)) {
                screenY = toScreenY(y);
                if (!isNearScreenY(screenY)) {
                    screenX++;
                }
            } else {
                screenX++;
            }
            if (screenX > getWidth()) {
                break;
            }
        } while (!isNearScreenY(screenY));
        return new ScreenPoint(screenX, screenY);
    }



    private boolean isOnScreenX(int screenX) {
        return screenX >= 0 && screenX < getWidth();
    }

    private boolean isOnScreenY(int screenY) {
        return screenY >= 0 && screenY <= getHeight();
    }

    private boolean isNearScreenX(int screenX) {
        return Math.abs(screenX) <= getWidth() * 2;
    }

    private boolean isNearScreenY(int screenY) {
        return Math.abs(screenY) <= getHeight() * 2;
    }

    private boolean isFinite(double d) {
        return Math.abs(d) <= Double.MAX_VALUE;
    }

    protected void onDraw(Canvas canvas) {
        drawViewFrame(canvas);
        drawAxes(canvas);
        drawFunctions(canvas);
        drawGraphPoints(canvas);
        drawLineGraphs(canvas);
    }
}
