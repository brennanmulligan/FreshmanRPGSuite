package edu.ship.engr.shipsim.util;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Creates a texture region grid that can be used as a spritesheet for
 * animations
 *
 * @author nhydock
 */
public class SpriteSheet
{
    TextureRegion[][] rows;
    TextureRegion[][] columns;

    /**
     * Number of columns in the sprite sheet
     */
    public final int xFrames;
    /**
     * Number of rows in the sprite sheet
     */
    public final int yFrames;
    private int[] frameSize;
    private TextureRegion tex;

    /**
     * Total number of cells in the sprite sheet
     */
    public final int frameCount;

    /**
     * Creates a spritesheet from a texture
     *
     * @param tex     - texture file
     * @param xFrames - number of horizontal frames in the spritesheet
     * @param yFrames - number of vertical frames in the spritesheet
     */
    public SpriteSheet(Texture tex, int xFrames, int yFrames)
    {
        this(new TextureRegion(tex), xFrames, yFrames);
    }

    /**
     * Creates a spritesheet from a texture region
     *
     * @param tex     - texture region of a source texture file to divide up
     * @param xFrames - number of horizontal frames in the spritesheet
     * @param yFrames - number of vertical frames in the spritesheet
     */
    public SpriteSheet(TextureRegion tex, final int xFrames, final int yFrames)
    {
        this.xFrames = xFrames;
        this.yFrames = yFrames;
        this.frameCount = xFrames * yFrames;
        rows = new TextureRegion[yFrames][xFrames];
        columns = new TextureRegion[xFrames][yFrames];
        frameSize = new int[]
                {tex.getRegionWidth() / xFrames, tex.getRegionHeight() / yFrames};
        rows = tex.split(frameSize[0], frameSize[1]);

        for (int y = 0; y < yFrames; y++)
        {
            for (int x = 0; x < xFrames; x++)
            {
                TextureRegion r = rows[y][x];
                columns[x][y] = r;
            }
        }
        this.tex = tex;
    }

    /**
     * Loads an animation directly from a given path
     *
     * @param path    - path to file
     * @param xFrames - number of horizontal cells in the spritesheet
     * @param yFrames - number of vertical cells in the spritesheet
     */
    public SpriteSheet(FileHandle path, int xFrames, int yFrames)
    {
        this(new Texture(path), xFrames, yFrames);
    }

    /**
     * @return the original texture region used in parsing the sprite sheet
     */
    public TextureRegion getTexture()
    {
        return tex;
    }

    /**
     * Pick a row of the spritesheet
     *
     * @param row - the row index to pick out of the spritesheet
     * @return all the texture regions of the specified row
     */
    public TextureRegion[] getRow(int row)
    {
        return rows[row];
    }

    /**
     * Pick a column of the spritesheet
     *
     * @param col - the column index of frames to pick out
     * @return all the texture regions of the specified column
     */
    public TextureRegion[] getColumn(int col)
    {
        return columns[col];
    }

    /**
     * Pick a single frame
     *
     * @param col - horizontal index
     * @param row - vertical index
     * @return cell at x, y in the spritesheet
     */
    public TextureRegion getFrame(int col, int row)
    {
        return columns[col][row];
    }

    /**
     * Pick a single frame as if the Spritesheet is along one long row/col
     *
     * @param frame          - frame index in the spritesheet (0...n-1)
     * @param indexDirection - true for down rows then next column, false for across
     *                       columns then next row
     * @return a region of the spritesheet
     */
    public TextureRegion getFrame(int frame, boolean indexDirection)
    {
        if (indexDirection)
        {
            return columns[frame / yFrames][frame % yFrames];
        }
        else
        {
            return rows[frame / xFrames][frame % xFrames];
        }
    }

    /**
     * Picks a single frame as if the Spritesheet is along one long row By
     * default we read across horizontally across the sheet
     *
     * @param frame - the frame index in the spritsheet (0...n-1)
     * @return region of the spritesheet
     */
    public TextureRegion getFrame(int frame)
    {
        return this.getFrame(frame, false);
    }

    /**
     * @return the width of each frame in the spritesheet
     */
    public int getFrameWidth()
    {
        return frameSize[0];
    }

    /**
     * @return the height of each frame in the spritesheet
     */
    public int getFrameHeight()
    {
        return frameSize[1];
    }
}
