package main;

import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet{
	int defLen=800;
	int defHeight=800;
	int screenLen=1600;
	int screenHeight=1600;
	LyfeBoard board;
	
	int transX, transY;
	int zoom=1;
	
	boolean screenshot;
	boolean auto=false;
	
	public static void main(String[] args){
		PApplet.main("main.Main");
	}
	
	public void settings(){
		size(defLen*5/4, defHeight);
		smooth(8);
	}
	
	public void setup(){
		surface.setResizable(true);
		frameRate(100);
		Button.setGC(this);
		
		int numSettings=7;
		String settings[]=loadStrings(System.getProperty("user.dir")+"\\Settings\\settings.txt");
		for(int i=0;i<numSettings;i++){
			String tmp[]=settings[i].split(": ");
			settings[i]=tmp[1];
		}
		
		//0: Tolerance:
		String tmp[]=settings[0].split("; ");
		for(int i=0;i<tmp.length;i++){
			String tmp2[]=tmp[i].split(", ");
			for(int j=0;j<tmp2.length;j++)
				LyfeTile.tolerances[i][j]=Integer.parseInt(tmp2[j]);
		}
		
		//1: Stroke
		if(Integer.parseInt(settings[1])==0)
			LyfeTile.tileStroke=false;
		else
			LyfeTile.tileStroke=true;
		
		//2: Board Res:
		tmp=settings[2].split(", ");
		defLen=Integer.parseInt(tmp[0]);
		defHeight=Integer.parseInt(tmp[1]);
		surface.setSize(defLen*5/4, defHeight);
		
		//3: Walls
		if(Integer.parseInt(settings[3])==0)
			LyfeBoard.walls=false;
		else
			LyfeBoard.walls=true;
		
		//4: Image Loading:
		tmp=settings[4].split(", ");
		if(Integer.parseInt(tmp[0])==0)
			board=new LyfeBoard(0, 0, width, height, Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
		else{
			String start="";
			if(tmp[1].equals("file"))
				start=System.getProperty("user.dir")+"\\";
			PImage boardImg=loadImage(start+tmp[2]);
			board=new LyfeBoard(0, 0, width, height, boardImg, this);
		}
		
		//5: Color
		if(Integer.parseInt(settings[5])==0)
			LyfeTile.color=false;
		else
			LyfeTile.color=true;
		
		//6: Colors
		if(!settings[6].equals("null")){
			tmp=settings[6].split("; ");
			for(int i=0;i<tmp.length;i++){
				String tmp2[]=tmp[i].split(", ");
				if(LyfeTile.color){
					LyfeTile.colors[i]=new Color(Integer.parseInt(tmp2[0]), Integer.parseInt(tmp2[1]), Integer.parseInt(tmp2[2]));
				}
				else{
					for(LyfeTile t[]: board.cells){
						for(LyfeTile l: t){
							l.stateColors[i]=new Color(Integer.parseInt(tmp2[0]), Integer.parseInt(tmp2[1]), Integer.parseInt(tmp2[2]));
						}
					}
				}
			}
		}
		
		LyfeTile.setPState(0);
	}
	
	public void draw(){
		if(screenshot){
			int w=width;
			int h=height;
			
			pushMatrix();
			surface.setSize(defLen, defHeight);
			
			Button.drawButtons();
			saveFrame("Images\\capture-######.png");
			//surface.setSize(defLen*5/4, defHeight);
			popMatrix();
			
			pushMatrix();
			surface.setSize(board.cells.length, board.cells[0].length);
			float s=(float)width/defLen;
			if((float)height/defHeight<s)
				s=(float)height/defHeight;
			scale(s);
			Button.drawButtons();
			saveFrame("Images\\capture-######SCALED.png");
			scale(1.0f/s);
			//surface.setSize(defLen*5/4, defHeight);
			popMatrix();
			
			pushMatrix();
			surface.setSize(board.cells.length, board.cells[0].length);
			scale(s);
			Button.updateButtons(-1, -1);
			Button.drawButtons();
			saveFrame("Images\\capture-######BWSCALED.png");
			//surface.setSize(defLen*5/4, defHeight);
			scale(1.0f/s);
			popMatrix();
			
			surface.setSize(w, h);
			
			screenshot=false;
		}
		
		//updating
		if(auto)
			board.setRunning(true);
		board.update();
		
		//drawing
		noStroke();
		fill(127,127,127);
		rect(0,0,width,height);
		
		fill(255);
		textSize(20);
		textAlign(CENTER);
		text(""+zoom, width*9/10, height/2);
		
		
		float s=(float)width/defLen;
		if((float)height/defHeight<s)
			s=(float)height/defHeight;
		int number=(width<height)?width:height;
		clip(0, 0, number, number);
		translate(transX*s*zoom, transY*s*zoom);
		scale(s);
		scale(zoom);
		Button.drawButtons();
	}
	
	public void mouseClicked(){
		float s=(float)width/defLen;
		if((float)height/defHeight<s)
			s=(float)height/defHeight;
		if(mouseButton==LEFT)
			Button.updateButtons((int)(((mouseX)/s)/zoom)-transX, (int)(((mouseY)/s)/zoom)-transY);
		else if(mouseButton==RIGHT && LyfeBoard.walls)
			board.setWall((int)(((mouseX)/s)/zoom)-transX, (int)(((mouseY)/s)/zoom)-transY);
	}
	
	public void keyPressed(){
		if(keyCode==LEFT)
			transX-=board.widthOfCell();
		else if(keyCode==RIGHT)
			transX+=board.widthOfCell();
		else if(keyCode==UP)
			transY-=board.widthOfCell();
		else if(keyCode==DOWN)
			transY+=board.widthOfCell();
		else if(key=='+'){
			zoom*=2;
			transX-=board.widthOfCell()*board.cellX()/zoom/2;
			transY-=board.widthOfCell()*board.cellY()/zoom/2;
		}
		else if(key=='-'){
			if(zoom>1){
				transX+=board.widthOfCell()*board.cellX()/zoom/2;
				transY+=board.widthOfCell()*board.cellY()/zoom/2;
				zoom/=2;
			}
		}
		else if(keyCode==ENTER){
			board.setRunning(true);
		}
		else if(key==' '){
			board.setRunning(false);
		}
		else if(key=='0')
			LyfeTile.setPState(0);
		else if(key=='1')
			LyfeTile.setPState(1);
		else if(key=='2')
			LyfeTile.setPState(2);
		else if(key=='h')
			screenshot=true;
		else if(key=='a'){
			auto=!auto;
		}
	}
}
