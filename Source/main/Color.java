package main;

import processing.core.PApplet;

public class Color {
	public final static Color RED=new Color(255, 0, 0);
	public final static Color GREEN=new Color(0, 255, 0);
	public final static Color BLUE=new Color(0, 0, 255);
	public final static Color WHITE=new Color(255, 255, 255);
	public final static Color BLACK=new Color(0, 0, 0);
	public final static Color CYAN=new Color(0, 255, 255);
	public final static Color YELLOW=new Color(255, 255, 0);
	public final static Color ORANGE=new Color(255, 127, 0);
	
	
	int r, g, b;
	
	public Color(Color c){
		r=c.r;
		g=c.g;
		b=c.b;
	}
	
	public Color(int r, int g, int b){
		this.r=r;
		this.g=g;
		this.b=b;
	}
	
	public Color(double r, double g, double b) {
		this((int)r, (int)g, (int)b);
	}

	public Color average(Color c){
		return gradAverage(c, 0.5);
	}
	
	public Color gradAverage(Color c, double partC){
		Color gradAvg=new Color((r*partC+(1.0-partC)*c.r), (g*partC+(1.0-partC)*c.g), (b*partC+(1.0-partC)*c.b));
		return gradAvg;
	}
	
	public void fill(PApplet gc){
		gc.fill(r, g, b);
	}
	
	public void stroke(PApplet gc){
		gc.stroke(r, g, b);
	}
}
