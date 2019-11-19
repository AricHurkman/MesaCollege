package MusicMain;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MusicPlayer implements ActionListener {


	private Player player;
	private String musicFile;
	JButton play = new JButton("Play");
	JButton stop = new JButton("Stop");
	JButton pause = new JButton("Pause");
	JButton open = new JButton("Open");


	public static void main(String[] args){

		new MusicPlayer().createGUI();
	}

	public void createGUI(){
		JFrame frame = new JFrame("Music Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout layout = new GridLayout(1,4,5,5);
		frame.setLayout(layout);
		frame.add(play);
		frame.add(stop);
		frame.add(pause);
		frame.add(open);
		frame.pack();
		frame.setVisible(true);
		play.addActionListener(this);
		stop.addActionListener(this);
		pause.addActionListener(this);
		open.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == play){
			if(musicFile == null){

				musicFile = openFile().getAbsolutePath();
				play(musicFile);

			}else {
				play(musicFile);
			}

		}else if(e.getSource() == stop){

				if(player != null){
					player.close();
				}


		}else if(e.getSource() == pause){


		}else if(e.getSource() == open){
			musicFile = openFile().getAbsolutePath();
			play(musicFile);
		}

	}

	private void play(String file){


		Thread thread = new Thread(() ->{
			try  {
				FileInputStream stream = new FileInputStream(file);
				player = new Player(stream);
				player.play();
			} catch (JavaLayerException | FileNotFoundException e) {
				e.printStackTrace();
			}

		});
		thread.start();

	}
	private File openFile ()  {
		JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		File f = null;
		int r =	j.showOpenDialog(null);
		if(r == JFileChooser.APPROVE_OPTION){
			musicFile = j.getSelectedFile().getAbsolutePath();
			f = new File(musicFile);
		}else {
			System.out.println("Canceled");
		}
		return f;
	}
}
