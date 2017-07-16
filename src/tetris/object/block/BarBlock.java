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
public class BarBlock extends AbstractBlock {

	public BarBlock(Field field) {
		super(field);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/* (非 Javadoc)
	 * @see tetris.object.block.AbstractBlock#decrareShape()
	 */
	@Override
	public void decrareShape() {
        // □■□□
        // □■□□
        // □■□□
        // □■□□
        shape[0][1] = 1;
        shape[1][1] = 1;
        shape[2][1] = 1;
        shape[3][1] = 1;
	}

	@Override
	protected Color setColor() {
		return Color.BLUE;
	}

	@Override
	protected BlockType setBlockType() {
		// TODO 自動生成されたメソッド・スタブ
		return BlockType.BAR;
	}
}

