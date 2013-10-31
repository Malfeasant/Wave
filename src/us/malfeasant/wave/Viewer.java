package us.malfeasant.wave;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

public class Viewer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int SAMPLES_PER_FRAME = 1152;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				go();
			}
		});
	}
	
	private static void go() {
		JFrame frame = new JFrame("Wave");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Viewer v = new Viewer();
		frame.add(v);
		
		JMenuBar bar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu rateMenu = new JMenu("Sample Rate");
		JMenuItem saveItem = new JMenuItem("Save...");
		saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				v.save();
			}
		});
		ButtonGroup rateGroup = new ButtonGroup();
		for (final SampleRate sr : SampleRate.values()) {
			JRadioButtonMenuItem item = new JRadioButtonMenuItem(sr.toString());
			rateGroup.add(item);
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (v.rate != sr) {
						v.image = null;
					}
					v.rate = sr;
					v.repaint();
				}
			});
			if (v.rate == sr) item.setSelected(true);
			rateMenu.add(item);
		}
		fileMenu.add(saveItem);
		bar.add(fileMenu);
		bar.add(rateMenu);
		frame.setJMenuBar(bar);
		frame.setVisible(true);
	}
	
	private SampleRate rate = SampleRate.SP;
	private BufferedImage image;
	
	private Viewer() {
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (image == null) {
			image = render(rate);
		}
		g2.drawImage(image, 0, 0, null);
	}
	
	private void save() {
		// TODO
		System.out.println("save...");
	}
	private BufferedImage render(SampleRate sr) {
		BufferedImage img = new BufferedImage(SAMPLES_PER_FRAME, 256, BufferedImage.TYPE_BYTE_GRAY);
		int rate = sr.SAMPLES_PER_SECOND;
		
		// TODO
		return img;
	}
}
