package main;

public class LyfeTile extends Button {
	public static int tolerances[][]={{2, 3}, {2, 2}};
	public static int pState=0;
	public static boolean tileStroke=false;
	public static boolean color=false;
	public static Color pBorders[]={Color.RED, Color.BLUE};
	public static Color tileColors[]={Color.WHITE, Color.BLACK};
	
	public static Color colors[]={Color.BLACK, Color.WHITE, Color.ORANGE, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN	};
	public Color clr=new Color(Color.BLACK);
	
	public int playerState;
	public int tolerance[];
	
	public static void setPState(int p){
		pState=p;
	}
	
	public LyfeTile(int x, int y, int len, int height){
		super(x, y, len, height, tileColors);
		this.borderColors=null;
		setState(state);
		stroke=tileStroke;
	}
	
	@Override
	public void draw(){
		if(state>=0){
			if(color)
				this.stateColors[state]=new Color(clr);
			if(playerState==0)
				borderWidth=0;
			else{
				gc.stroke(255);
				borderWidth=len/3;
				pBorders[playerState-1].fill(gc);
				gc.rect(x, y, len, height);
				gc.noStroke();
			}
			super.draw();
		}
		else{
			state=0;
			this.stateColors[state]=new Color(Color.RED);
			super.draw();
			state=-1;
		}
	}
	
	@Override
	public void update(int mx, int my){
		//if(pState==playerState || playerState==0){
		if(state!=-1){
			int oldState=state;
			super.update(mx, my);
			if(oldState==0 && state==1)
				this.playerState=pState;
			tolerance=tolerances[state];
			if(state==0)
				this.playerState=0;
			if(state==0)
				this.clr=new Color(colors[0]);
			if(state!=0)
				this.clr=new Color(colors[1]);
		}
		else
			super.update(mx, my);
		//}
	}
	
	@Override
	public void setState(int state){
		super.setState(state);
		tolerance=tolerances[this.state];
	}
}
