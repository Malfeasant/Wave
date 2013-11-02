package us.malfeasant.wave;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
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
	private static final int RANGE = 256;
	private static final Dimension PREFSIZE = new Dimension(SAMPLES_PER_FRAME, RANGE);
	private static final double TAU = Math.PI * 2.0;
	
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
						v.rate = sr;
						v.render();
						v.repaint();
					}
				}
			});
			if (v.rate == sr) item.setSelected(true);
			rateMenu.add(item);
		}
		fileMenu.add(saveItem);
		bar.add(fileMenu);
		bar.add(rateMenu);
		frame.setJMenuBar(bar);
		frame.pack();
		frame.setVisible(true);
		((JRadioButtonMenuItem)(rateMenu.getMenuComponent(2))).doClick();
	}
	
	private SampleRate rate;
	private final BufferedImage image;
	
	private Viewer() {
		image = new BufferedImage(SAMPLES_PER_FRAME, RANGE, BufferedImage.TYPE_BYTE_GRAY);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		AffineTransform xform = new AffineTransform();
		xform.scale(getWidth() / (double)SAMPLES_PER_FRAME, getHeight() / (double)RANGE);
		g2.drawRenderedImage(image, xform);
	}
	private void render() {
		image.createGraphics().clearRect(0, 0, SAMPLES_PER_FRAME, RANGE);
		float scale = (float)rate.SAMPLES_PER_SECOND / SAMPLES_PER_FRAME;
		for (int x = 0; x < SAMPLES_PER_FRAME; x++) {
			int y = doSin(x, scale, 1.0f);
			image.setRGB(x, y, 0xffffff);
		}
	}
	private void save() {
		System.out.println("save...");
		// TODO
	}
	private static int doSin(int x, float scale, float amp) {
		double rad = (x / scale) * TAU;
		double y = Math.sin(rad) * amp;	// -1 <= y <= 1
//		System.out.print(y + "\t");
		y *= 0.5;	// -0.5 <= y <= 0.5
//		System.out.print(y + "\t");
		y += 0.5;	// 0 <= y <= 1
//		System.out.print(y + "\t");
		y *= (RANGE - 1);	// 0 <= y < RANGE
//		System.out.println(y);
		return (int)y;
	}

	@Override
	public Dimension getPreferredSize() {
		return PREFSIZE;
	}
}
