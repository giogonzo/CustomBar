package com.custombar.custombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class CustomBar extends View {
	private static final int DEFAULT_CENTER_SIZE = 20;
	private static final float DEFAULT_TEXT_SIZE = 30;
	private static final int DEFAULT_TEXT_OFFSET = 30;
	private static final int DEFAULT_CENTER_OFFSET = 30;

	private int width;
	private int height;
	private int verticalPadding;
	private Drawable left;
	private Drawable right;
	private Drawable center;
	private int leftCount;
	private int rightCount;
	private int weightSum;
	private int textColor;
	private float textSize;
	private float textOffset;
	private Paint textPaint;
	private int centerWidth;
	private boolean textAnchorCenter;
	private boolean textLeft;
	private boolean textRight;
	private boolean textShadow;
	private int textShadowColor;
	private int textShadowRadius;
	private int textShadowDX;
	private int textShadowDY;
	private int leftTextWidth;
	private int leftTextHeight;
	private int rightTextWidth;
	private int rightTextHeight;
	private String leftText;
	private String rightText;
	private float centerOffset;

	public CustomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.CustomBar);

		left = a.getDrawable(R.styleable.CustomBar_leftDrawable);
		right = a.getDrawable(R.styleable.CustomBar_rightDrawable);
		center = a.getDrawable(R.styleable.CustomBar_centerDrawable);
		verticalPadding = a.getDimensionPixelOffset(
				R.styleable.CustomBar_verticalPadding, 0);
		centerOffset = a.getDimensionPixelOffset(
				R.styleable.CustomBar_centerOffset, DEFAULT_CENTER_OFFSET);
		centerWidth = center.getIntrinsicWidth();

		leftCount = a.getInteger(R.styleable.CustomBar_leftCount, 0);
		rightCount = a.getInteger(R.styleable.CustomBar_rightCount, 0);
		weightSum = leftCount + rightCount;

		textAnchorCenter = a.getBoolean(R.styleable.CustomBar_textAnchorCenter,
				true);
		textLeft = a.getBoolean(R.styleable.CustomBar_textLeft, true);
		textRight = a.getBoolean(R.styleable.CustomBar_textRight, true);
		textColor = a.getColor(R.styleable.CustomBar_textColor, Color.WHITE);
		textSize = a.getDimension(R.styleable.CustomBar_textSize,
				DEFAULT_TEXT_SIZE);
		textOffset = a.getDimension(R.styleable.CustomBar_textOffset,
				DEFAULT_TEXT_OFFSET);

		textShadow = a.getBoolean(R.styleable.CustomBar_textShadow, true);
		textShadowColor = a.getColor(R.styleable.CustomBar_textShadowColor,
				Color.BLACK);
		textShadowRadius = a.getDimensionPixelOffset(
				R.styleable.CustomBar_textShadowRadius, 0);
		textShadowDX = a.getDimensionPixelOffset(
				R.styleable.CustomBar_textShadowDX, 0);
		textShadowDY = a.getDimensionPixelOffset(
				R.styleable.CustomBar_textShadowDY, 0);

		a.recycle();

		if (textLeft || textRight) {
			textPaint = new Paint();
			textPaint.setAntiAlias(true);
			textPaint.setColor(textColor);
			textPaint.setTextSize(textSize);

			updateLeftText();
			updateRightText();

			if (textShadow) {
				textPaint.setShadowLayer(textShadowRadius, textShadowDX,
						textShadowDY, textShadowColor);
			}
		}

		updateBounds();
	}

	public void setLeftCount(int count) {
		leftCount = count;
		weightSum = leftCount + rightCount;
		updateLeftText();
		updateBounds();
	}

	public void setRightCount(int count) {
		rightCount = count;
		weightSum = leftCount + rightCount;
		updateRightText();
		updateBounds();
	}

	private void updateLeftText() {
		leftText = "" + leftCount;
		Rect leftTextBounds = new Rect();
		textPaint.getTextBounds(leftText.toCharArray(), 0, leftText.length(),
				leftTextBounds);
		leftTextWidth = Math.abs(leftTextBounds.right - leftTextBounds.left);
		leftTextHeight = Math.abs(leftTextBounds.top - leftTextBounds.bottom);
	}

	private void updateRightText() {
		rightText = "" + rightCount;
		rightText = "" + rightCount;
		Rect rightTextBounds = new Rect();
		textPaint.getTextBounds(rightText.toCharArray(), 0, rightText.length(),
				rightTextBounds);
		rightTextWidth = Math.abs(rightTextBounds.right - rightTextBounds.left);
		rightTextHeight = Math
				.abs(rightTextBounds.top - rightTextBounds.bottom);
	}

	private void updateBounds() {
		int centerH = centerHorizontal();

		left.setBounds(0, verticalPadding, centerH, height - verticalPadding);

		right.setBounds(centerH, verticalPadding, width, height
				- verticalPadding);

		if (centerWidth == -1)
			centerWidth = DEFAULT_CENTER_SIZE;
		int centerHeight = center.getIntrinsicHeight();
		if (centerHeight == -1)
			centerHeight = DEFAULT_CENTER_SIZE;
		int centerLeft = Math.min(width - centerWidth,
				Math.max(0, centerH - centerWidth / 2));
		center.setBounds(centerLeft, height / 2 - centerHeight / 2, centerLeft
				+ centerWidth, height / 2 + centerHeight / 2);
	}

	private int centerHorizontal() {
		if (weightSum == 0)
			return width / 2;
		double center = Math.floor(width
				* ((float) leftCount / (float) weightSum));
		return (int) Math.min(width - centerOffset - centerWidth / 2 - 2
				* textOffset, Math.max(centerOffset + centerWidth / 2 + 2
				* textOffset, center));
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		left.draw(canvas);
		right.draw(canvas);
		center.draw(canvas);

		if (!textLeft && !textRight)
			return;

		int centerH = centerHorizontal();

		if (textLeft)
			if (centerH - textOffset - leftTextWidth >= textOffset)
				if (textAnchorCenter)
					canvas.drawText(leftText, centerH - textOffset
							- leftTextWidth, height / 2 + leftTextHeight / 2,
							textPaint);
				else
					canvas.drawText(leftText, textOffset, height / 2
							+ leftTextHeight / 2, textPaint);

		if (textRight)
			if (centerH + textOffset + rightTextWidth <= width - textOffset)
				if (textAnchorCenter)
					canvas.drawText(rightText, centerH + textOffset, height / 2
							+ rightTextHeight / 2, textPaint);
				else
					canvas.drawText(rightText, width - textOffset
							- rightTextWidth, height / 2 + rightTextHeight / 2,
							textPaint);
	}

	@Override
	protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
		super.onSizeChanged(xNew, yNew, xOld, yOld);
		width = xNew;
		height = yNew;
		updateBounds();
	}

}
