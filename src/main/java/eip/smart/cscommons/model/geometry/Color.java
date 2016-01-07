package eip.smart.cscommons.model.geometry;

import java.awt.color.ColorSpace;

import com.fasterxml.jackson.annotation.JsonView;

import eip.smart.cscommons.model.JSONViews;

public class Color extends java.awt.Color {

	public Color(ColorSpace cspace, float[] components, float alpha) {
		super(cspace, components, alpha);
	}

	public Color(float r, float g, float b) {
		super(r, g, b);
	}

	public Color(float r, float g, float b, float a) {
		super(r, g, b, a);
	}

	public Color(int rgb) {
		super(rgb);
	}

	public Color(int rgba, boolean hasalpha) {
		super(rgba, hasalpha);
	}

	public Color(int r, int g, int b) {
		super(r, g, b);
	}

	public Color(int r, int g, int b, int a) {
		super(r, g, b, a);
	}

	public Color(java.awt.Color color) {
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public Color() {
		super(0, 0, 0);
	}

	@JsonView({ JSONViews.HTTP.class })
	@Override
	public int getAlpha() {
		return super.getAlpha();
	}

	@JsonView({ JSONViews.HTTP.class })
	@Override
	public int getBlue() {
		return super.getBlue();
	}

	@JsonView({ JSONViews.HTTP.class })
	@Override
	public int getGreen() {
		return super.getGreen();
	}

	@JsonView({ JSONViews.HTTP.class })
	@Override
	public int getRed() {
		return super.getRed();
	}

}
