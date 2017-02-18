package main;

import java.util.ArrayList;

import processing.core.PApplet;

public class Button {
	protected static PApplet gc;
	public static ArrayList<Button> allButtons=new ArrayList<Button>();
	
	public int x, y, len, height;
	public int borderWidth=0;
	public int numStates, state;
	public Color stateColors[];
	public Color borderColors[];
	public boolean editable=true;
	
	public String dispText;
	public Color textColor;
	public int textSize;//for obvious reasons this is hard to scale
	
	boolean stroke=true;
	
	public static void setGC(PApplet p){
		gc=p;
	}
	
	public static void updateButtons(int mx, int my){
		for(Button b: allButtons)
			b.update(mx, my);
	}
	
	public static void drawButtons(){
		for(Button b: allButtons)
			b.draw();
	}
	
	public Button(int x, int y, int len, int height, Color stateColors[]){
		allButtons.add(this);
		this.x=x;
		this.y=y;
		this.len=len;
		this.height=height;
		this.stateColors=new Color[stateColors.length];
		for(int i=0;i<stateColors.length;i++){
			this.stateColors[i]=new Color(stateColors[i]);
		}
		this.numStates=stateColors.length;
	}
	
	public Button(int x, int y, int len, int height, Color stateColors[], int borderWidth, Color borderColors[]){
		this(x, y, len, height, stateColors);
		this.borderWidth=borderWidth;
		this.borderColors=new Color[borderColors.length];
		for(int i=0;i<borderColors.length;i++){
			this.borderColors[i]=new Color(stateColors[i]);
		}
	}
	
	public void setState(int state){
		this.state=state;
	}
	
	public void addText(String text, Color textColor, int textSize){
		this.dispText=text;
		this.textColor=textColor;
		this.textSize=textSize;
	}
	
	public void update(int mx, int my){
		if(editable){
			if(x<=mx && mx<x+len){
				if(y<=my && my<y+height){
					state++;
					state%=numStates;
				}
			}
		}
	}
	
	
	public void draw(){
		if(stroke)
			gc.stroke(127);
		else
			gc.noStroke();
		if(borderWidth>0 && borderColors!=null){		
			borderColors[state%borderColors.length].fill(gc);
			gc.rect(x, y, len, height);
			gc.noStroke();
		}
		
		stateColors[state].fill(gc);
		gc.rect(x+borderWidth, y+borderWidth, len-2*borderWidth, height-2*borderWidth);
		
		if(dispText!="" && dispText!=null){
			gc.textAlign(gc.CENTER, gc.CENTER);
			gc.textSize(textSize);
			textColor.fill(gc);
			gc.text(dispText, x+len/2, y+height/2);
		}
	}
}
