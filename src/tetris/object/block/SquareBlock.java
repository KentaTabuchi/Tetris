/**
 * 
 */
package tetris.object.block;

import java.awt.Color;

import tetris.object.field.Field;

/**
 * @author misskabu
 *
 */
public class SquareBlock extends AbstractBlock {

	public SquareBlock(Field field) {
		super(field);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/* (非 Javadoc)
	 * @see tetris.object.block.AbstractBlock#decrareShape()
	 */
	@Override
	public void decrareShape() {
        // □□□□
        // □■■□
        // □■■□
        // □□□□
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[2][1] = 1;
        shape[2][2] = 1;
	}

	@Override
	protected Color setColor() {
		return Color.ORANGE;
	}

	@Override
	protected BlockType setBlockType() {
		// TODO 自動生成されたメソッド・スタブ
		return BlockType.SQUARE;
	}

}
