/**
 * 
 */
package tetris.object.field;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import tetris.main.GamePanel;
import tetris.object.block.AbstractBlock;
import tetris.object.block.BarBlock;
import tetris.object.block.BlockType;
import tetris.object.block.LShapeBlock;
import tetris.object.block.ReverseLShapeBlock;
import tetris.object.block.ReverseZShapeBlock;
import tetris.object.block.SquareBlock;
import tetris.object.block.TShapeBlock;
import tetris.object.block.ZShapeBlock;

/**
 * @author misskabu
 *
 */
public class Field {
	public static final int COL = 12;//配置するマスの列数
	public static final int ROW = 26;//配置するマスの行数
	public static final int TILE_SIZE = 16; //１マスの辺の長さ
	
	private int field[][];
	
	public Field(){
		field = new int[ROW][COL];
		this.init();
	}
	private void init(){
		for(int i = 0; i < ROW; i++){
			this.makeColumn(i);
		}
	}
	private void makeColumn(int row){
		for(int col = 0; col < COL ; col++){
			if(col == 0 || col == COL -1){
				field[row][col] = 1;
			}
			else if(row == ROW-1){
				field[row][col] = 1;
			}
			else{
				field[row][col] = 0;
			}
		}
	}
	/**
	 * フィールドの壁と落ちて固定されたブロックの描画。
	 * @param g
	 */
	public void draw(Graphics g){
		//まず背景を黒に塗る
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		//壁と落ちて固定されたブロックを塗る

        for (int y = 0; y < ROW; y++) {
            for (int x = 0; x < COL; x++) {
                if (field[y][x] != 0) {
                	AbstractBlock block = null;
                	switch (field[y][x]){
	                	case 1:block = null;break;
	                	case 2:block = new BarBlock(this);break;
	                	case 3:block = new ZShapeBlock(this);break;
	                	case 4:block = new SquareBlock(this);break;
	                	case 5:block = new LShapeBlock(this);break;
	                	case 6:block = new ReverseLShapeBlock(this);break;
	                	case 7:block = new TShapeBlock(this);break;
	                	case 8:block = new ReverseZShapeBlock(this);break;
                	}
                	//block==nullの時は壁が入っているので壁の色を直に指定
                	if (block == null){
                		g.setColor(Color.DARK_GRAY);
                	}else{
                	g.setColor(block.getColor());
                	}
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE,
                            TILE_SIZE);
                }
            }
        }
	}

    /**
     * ブロックを移動できるか調べる
     * 
     * @param newPos
     *            ブロックの移動先座標
     * @param block
     *            ブロック
     * @return 移動できたらtrue
     */
    public boolean isMovable(Point newPos, int[][] block) {
        // block=1のマスすべてについて衝突しているか調べる
        // どれか1マスでも衝突してたら移動できない
        for (int i = 0; i < AbstractBlock.ROW; i++) {
            for (int j = 0; j < AbstractBlock.COL; j++) {
                if (block[i][j] != 0) { // 4x4内でブロックのあるマスのみ調べる
                    if (newPos.y + i < 0) { // そのマスが画面の上端外のとき
                        // ブロックのあるマスが壁のある0列目以下または
                        // COL-1列目以上に移動しようとしている場合は移動できない
                        if (newPos.x + j <= 0 || newPos.x + j >= COL - 1) {
                            return false;
                        }
                    } else if (field[newPos.y + i][newPos.x + j] != 0) { // フィールド内で
                        // 移動先にすでにブロック（壁含む）がある場合は移動できない
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 落ちきったブロックをボードに固定する
     * switch分岐ではフィールドに埋め込む配列（どのタイルにどのブロックがあるか）を書き込んでいる。
     * フィールド側でblockNoを識別して色をぬり分けている。
     * @param pos
     *            ブロックの位置
     * @param block
     *            ブロック
     * @param abstractBlock 
     */
    public void fixBlock(Point pos, int[][] blockShape, AbstractBlock block) {
    	int blockNo=0;
    	BlockType blockType = block.getBlockType();
    	switch(blockType){
    	case WALL:
    		blockNo=1;
    		break;
    	case BAR:
    		blockNo=2;
    		break;
    	case Z_SHAPE:
    		blockNo=3;
    		break;
    	case SQUARE:
    		blockNo=4;
    		break;
    	case L_SHAPE:
    		blockNo=5;
    		break;
    	case REVERSE_L_SHAPE:
    		blockNo=6;
    		break;
    	case T_SHAPE:
    		blockNo=7;
    		break;
    	case REVERSE_Z_SHAPE:
    		blockNo=8;
    		break;
    	
    	}
        for (int i = 0; i < AbstractBlock.ROW; i++) {
            for (int j = 0; j < AbstractBlock.COL; j++) {
                if (blockShape[i][j] == 1) {
                    if (pos.y + i < 0) continue;
                    field[pos.y + i][pos.x + j] = blockNo; // フィールドをブロックで埋める
                }
            }
        }
    }

    /**
     * ブロックがそろった行を消去
     */
    public void deleteLine() {
        for (int y = 0; y < ROW - 1; y++) {
            int count = 0;
            for (int x = 1; x < COL - 1; x++) {
                // ブロックがある列の数を数える
                if (field[y][x] != 0)//fixBlockの中のblockNoが０以外の時何かのブロックがある
                    count++;
            }
            // そろった行が見つかった！
            if (count == Field.COL - 2) {
                // その行を消去
                for (int x = 1; x < COL - 1; x++) {
                    field[y][x] = 0;
                }
                // それより上の行を落とす
                for (int ty = y; ty > 0; ty--) {
                    for (int tx = 1; tx < COL - 1; tx++) {
                        field[ty][tx] = field[ty - 1][tx];
                    }
                }
            }
        }
    }
}
