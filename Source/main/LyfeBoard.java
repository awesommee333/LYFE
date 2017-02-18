package main;

import processing.core.PApplet;
import processing.core.PImage;

public class LyfeBoard {
	public static boolean walls=true;
	
	int x, y, len, height, numCellsX, numCellsY, sizeOfTile;
	int turn;
	LyfeTile cells[][];
	boolean running=false;
	
	int surround[][]={{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
	
	public LyfeBoard(int x, int y, int len, int height, int numCellsX, int numCellsY){
		this.x=x;
		this.y=y;
		this.len=len;
		this.height=height;
		this.numCellsX=numCellsX;
		this.numCellsY=numCellsY;
		this.cells=new LyfeTile[numCellsX][];
		sizeOfTile=len/numCellsX;
		if(height/numCellsY<sizeOfTile)
			sizeOfTile=height/numCellsY;
		for(int i=0;i<cells.length;i++){
			cells[i]=new LyfeTile[numCellsY];
			for(int j=0;j<cells[i].length;j++)
				cells[i][j]=new LyfeTile(x+i*sizeOfTile, y+j*sizeOfTile, sizeOfTile, sizeOfTile);
		}
	}
	
	public LyfeBoard(int x, int y, int len, int height, PImage img, PApplet gc){
		this(x, y, len, height, img.width, img.height);
		
		for(int i=0;i<cells.length;i++){
			for(int j=0;j<cells[i].length;j++){
				int f=img.get(i, j);
				if(f==gc.color(255,255,255))
					cells[i][j].update(cells[i][j].x, cells[i][j].y);
				else if(f==gc.color(255,0,0) && walls)
					cells[i][j].state=-1;
			}
		}
	}
	
	public int widthOfCell(){
		return cells[0][0].len;
	}
	
	public int cellX(){
		return numCellsX;
	}
	
	public int cellY(){
		return numCellsY;
	}
	
	public void setRunning(boolean running){
		for(LyfeTile t[]:cells){
			for(LyfeTile c:t)
				c.editable=!running;
		}
		this.running=running;
	}
	
	public void update(){
		if(running){
			int updates[][][]=new int[numCellsX][numCellsY][4];
			for(int i=0;i<numCellsX;i++){
				for(int j=0;j<numCellsY;j++){
					int numAlive=0;
					int pStates[]={0,0,0};
					int states[]=new int[cells[i][j].numStates];
					for(int f=0;f<states.length;f++)
						states[f]=0;
					for(int f=0;f<8;f++){
						int sx=i+surround[f][0];
						int sy=j+surround[f][1];
						if(sx>=0 && sx<cells.length){
							if(sy>=0 && sy<cells[i].length){
								if(cells[sx][sy].state>0){
									numAlive++;
									pStates[cells[sx][sy].playerState]++;
									states[cells[sx][sy].state]++;
								}
							}
						}
					}
					if(cells[i][j].state>0){
						if(numAlive<cells[i][j].tolerance[0] || numAlive>cells[i][j].tolerance[1])
							updates[i][j][0]=0;
						else{
							updates[i][j][0]=1;
							updates[i][j][3]=numAlive;
						}
					}
					else if(cells[i][j].state==0){
						if(numAlive>=cells[i][j].tolerance[0] && numAlive<=cells[i][j].tolerance[1]){
							int bestState=1, numBestState=0;
							for(int f=1;f<states.length;f++){
								if(states[f]>numBestState){
									bestState=f;
									numBestState=0;
								}
							}
							int bestPlayer=0, numBestPlayer=0;
							for(int f=0;f<pStates.length;f++){
								if(pStates[f]>numBestPlayer){
									bestPlayer=f;
									numBestPlayer=0;
								}
							}
							updates[i][j][0]=2;
							updates[i][j][1]=bestState;
							updates[i][j][2]=bestPlayer;
							updates[i][j][3]=numAlive;
						}
						else
							updates[i][j][0]=0;
					}
				}
			}
			for(int i=0;i<cells.length;i++){
				for(int j=0;j<cells[i].length;j++){
					if(updates[i][j][0]==0 && cells[i][j].state>=0){
						cells[i][j].playerState=0;
						cells[i][j].setState(0);
					}
					else if(updates[i][j][0]==2 && cells[i][j].state>=0){
						cells[i][j].setState(updates[i][j][1]);
						cells[i][j].playerState=updates[i][j][2];
					}
					cells[i][j].clr=new Color(LyfeTile.colors[updates[i][j][3]]);
				}
			}
			setRunning(false);
		}
	}
	
	public void setWall(int mx, int my){
		if(walls){
			int nmx=mx-x;
			int nmy=my-y;
			if(nmx>=0 && nmx<len){
				if(nmy>=0 && nmy<height){
					cells[nmx/sizeOfTile][nmy/sizeOfTile].state=-1;
				}
			}
		}
	}
}
