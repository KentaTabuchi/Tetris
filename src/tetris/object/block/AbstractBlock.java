/**
 * 
 */
package tetris.object.block;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import tetris.object.field.Field;

/**
 * フィールドに落ちてくるブロックを定義。
 * 継承してコンストラクタで形状を指定して使う。
 * @author misskabu
 *
 */
public abstract class AbstractBlock {

	public static final int ROW = 4; //ブロックの最大サイズ
	public static final int COL = 4;
	public static final int TILE_SIZE = Field.TILE_SIZE;
    // 移動方向
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;

    // ブロックの名前
    public static final int BAR = 0;
    public static final int Z_SHAPE = 1;
    public static final int SQUARE = 2;
    public static final int L_SHAPE = 3;
    public static final int REVERSE_Z_SHAPE = 4;
    public static final int T_SHAPE = 5;
    public static final int REVERSE_L_SHAPE = 6;
    public static final int WALL = 7;
	
    protected int shape[][] = new int [ROW][COL]; //ブロックの形状を格納
	protected Point pos;  // 位置（単位：マス）
	protected Field field;
	protected abstract void decrareShape();//継承先で形を決める
	protected abstract Color setColor();//継承先で色を決める
	protected abstract BlockType setBlockType();
	private Color blockColor;
	private BlockType blockType;
	
	
	public AbstractBlock(Field field){
		this.field = field;
		this.shapeInitializeing();
		pos = new Point(4,-4);
		this.decrareShape();
		this.blockColor = this.setColor();
		this.blockType = this.setBlockType();
	}
	
	/**
	 * ブロックの形状マップを初期化
	 * 	0000 
	 * 	0000
	 * 	0000
	 * 	0000
	 */
	private void shapeInitializeing(){
		for(int i=0;i<ROW;i++){
			for(int j=0;j<COL;j++){
				shape[i][j]=0;
			}
		}
	}
	public void draw(Graphics g){
		g.setColor(this.blockColor);
		
		for(int i = 0;i < ROW; i++){
			for(int j = 0; j < COL; j++){
				if(shape[i][j] == 1){
					g.fillRect((pos.x + j) * TILE_SIZE, (pos.y + i) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
			}
		}
	}
    public boolean move(int dir) {
        switch (dir) {
            case LEFT :
                Point newPos = new Point(pos.x - 1, pos.y);
                if (field.isMovable(newPos, shape)) { // 衝突しなければ位置を更新
                    pos = newPos;
                }
                break;
            case RIGHT :
                newPos = new Point(pos.x + 1, pos.y);
                if (field.isMovable(newPos, shape)) {
                    pos = newPos;
                }
                break;
            case DOWN :
                newPos = new Point(pos.x, pos.y + 1);
                if (field.isMovable(newPos, shape)) {
                    pos = newPos;
                } else { // 移動できない＝他のブロックとぶつかる＝固定する
                    // ブロックをフィールドに固定する
                    field.fixBlock(pos, shape,this);
                    // 固定されたらtrueを返す
                    return true;
                }
                break;
        }

        return false;
    }
    /**
     * ブロックを回転される
     */
    public void turn() {
        int[][] turnedBlock = new int[ROW][COL];

        // 回転したブロック
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                turnedBlock[j][ROW - 1 - i] = shape[i][j];
            }
        }

        // 回転可能か調べる
        if (field.isMovable(pos, turnedBlock)) {
            shape = turnedBlock;
        }
    }
	public BlockType getBlockType() {
		return blockType;
	}
	public Color getColor(){
		return blockColor;
		
	}

	
}
