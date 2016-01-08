package eip.smart.cscommons.model.geometry;

import com.fasterxml.jackson.annotation.JsonView;
import eip.smart.cscommons.model.JSONViews;

public class Color {

	@JsonView({ JSONViews.HTTP.class, JSONViews.DISK.class })
	private float red;

	@JsonView({ JSONViews.HTTP.class, JSONViews.DISK.class })
	private float green;

	@JsonView({ JSONViews.HTTP.class, JSONViews.DISK.class })
	private float blue;

	@JsonView({ JSONViews.HTTP.class, JSONViews.DISK.class })
	private float alpha;

	public Color(float r, float g, float b) {
		this(r, g, b, 0);
	}

	public Color(float r, float g, float b, float a) {
		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = a;
	}

	public Color(java.awt.Color color) {
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public Color() {
		this(0, 0, 0);
	}

}
