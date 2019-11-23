package MusicMain;
import java.awt.*;

public abstract class abstractDraw {

	protected int x, y;
	protected float velX, velY;

	public abstractDraw(int x, int y){
		this.x = x;
		this.y = y;
	}

	public abstract void Render(Graphics g);

	public void SetX(int x){this.x = x;}
	public int GetX(){return x;}

	public void SetY(int y){this.y = y;}
	public int GetY(){return y;}


	public void setVelX(int velX) {this.velX = velX;}

	public float getVelX() {return velX;}

	public void setVelY(int velY) {this.velY = velY;}

	public float getVelY() {return velY; }

}
