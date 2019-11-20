package MusicMain;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class CanvasVisualizer extends Canvas {


	public void Render(){
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
		new DrawVisualizer(0,0,ID.One);


	}


}
