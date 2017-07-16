package tetris.main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;

import tetris.object.block.AbstractBlock;
import tetris.object.block.BarBlock;
import tetris.object.block.LShapeBlock;
import tetris.object.block.ReverseLShapeBlock;
import tetris.object.block.ReverseZShapeBlock;
import tetris.object.block.SquareBlock;
import tetris.object.block.TShapeBlock;
import tetris.object.block.ZShapeBlock;
import tetris.object.field.Field;

/**
 * 
 */

/**
 * @author misskabu
 *
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener,Runnable {
	public static final int WIDTH = 192;
	public static final int HEIGHT = 416;
	
	private Field field;
	private AbstractBlock block;
	private AbstractBlock nextBlock;
	private Thread gameLoop;
	private Random rand;
	/**
	 * 
	 */
	public GamePanel() {
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setFocusable(true);
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		field = new Field();
		block = this.createBlock(field);
		this.addKeyListener(this);
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		field.draw(g);
		block.draw(g);
	}
	/**
	 * ブロックをランダムに選んで生成する。
	 * @param field
	 * @return
	 */
	private AbstractBlock createBlock(Field field){
		int blockNo = rand.nextInt(7);
		switch(blockNo){
		case AbstractBlock.BAR:
			return new BarBlock(field);
		case AbstractBlock.Z_SHAPE:
			return new ZShapeBlock(field);
		case AbstractBlock.SQUARE :
			return new SquareBlock(field);
		case AbstractBlock.L_SHAPE :
			return new LShapeBlock(field);
		case AbstractBlock.REVERSE_Z_SHAPE :
			return new ReverseZShapeBlock(field);
		case AbstractBlock.T_SHAPE :
			return new TShapeBlock(field);
		case AbstractBlock.REVERSE_L_SHAPE :
			return new ReverseLShapeBlock(field);
		}
		return null;
		
	}
	/* (非 Javadoc)
	 * @see java.lang.Runnable#run()
	 * ゲームのメインループ
	 * ブロックが固定されていたら新しいブロックを作る。
	 * 同時に揃っているラインを消す。揃っているかどうかの判定はFieldクラスのdeleteLine()で定義
	 */
	@Override
	public void run() {
		while(true){
			boolean isFixed ; //ブロックが固定されていたら(落下が終わって動かせなくなったら)true
			isFixed = block.move(AbstractBlock.DOWN);
			if(isFixed){
				nextBlock = this.createBlock(field);
				block = nextBlock;
			}
			field.deleteLine();
			repaint();

			try{
				Thread.sleep(200);
			}catch(InterruptedException e){
				e.printStackTrace();
			}


		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// 何もしない
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT){
			block.move(AbstractBlock.LEFT);
		}else if(key == KeyEvent.VK_RIGHT){
			block.move(AbstractBlock.RIGHT);
		}else if(key == KeyEvent.VK_DOWN){
			block.move(AbstractBlock.DOWN);
		}else if(key == KeyEvent.VK_SPACE){
			block.turn();
		}
		this.repaint();
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
