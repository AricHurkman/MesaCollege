package MusicMain;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class CanvasVisualizer extends Canvas {


	DrawVisualizer visualizer;
	public void Render(MusicPlayer player){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(10,10, 580, 600);
		g.dispose();
		bs.show();
		DrawCircle(g);



	}

	private void DrawCircle(Graphics g){

	}

}
