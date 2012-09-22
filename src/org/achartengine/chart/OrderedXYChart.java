package org.achartengine.chart;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class OrderedXYChart extends LineChart {
	private static final long serialVersionUID = 1L;

	public OrderedXYChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
	    super(dataset, renderer);
	}

	@Override
	public void drawSeries(Canvas canvas, Paint paint, float[] points,
			SimpleSeriesRenderer seriesRenderer, float yAxisValue,
			int seriesIndex, int startIndex) {
		int length = points.length;
	    XYSeriesRenderer renderer = (XYSeriesRenderer) seriesRenderer;
	    float lineWidth = paint.getStrokeWidth();
	    paint.setStrokeWidth(renderer.getLineWidth());
	    if (renderer.isFillBelowLine()) {
	      paint.setColor(renderer.getFillBelowLineColor());
	      int pLength = points.length;
	      float[] fillPoints = new float[pLength + 4];
	      System.arraycopy(points, 0, fillPoints, 0, length);
	      fillPoints[0] = points[0] + 1;
	      fillPoints[length] = fillPoints[length - 2];
	      fillPoints[length + 1] = yAxisValue;
	      fillPoints[length + 2] = fillPoints[0];
	      fillPoints[length + 3] = fillPoints[length + 1];
	      for (int i = 0; i < length + 4; i += 2) {
	        if (fillPoints[i + 1] < 0) {
	          fillPoints[i + 1] = 0;
	        }
	      }
	      paint.setStyle(Style.FILL);
	      drawPath(canvas, fillPoints, paint, true);
	    }
	    paint.setColor(seriesRenderer.getColor());
	    paint.setStyle(Style.STROKE);
	    drawPath(canvas, points, paint, false);
	    paint.setStrokeWidth(lineWidth);
	}

	@Override
	public String getChartType() {
		return "Ordered";
	}
}
