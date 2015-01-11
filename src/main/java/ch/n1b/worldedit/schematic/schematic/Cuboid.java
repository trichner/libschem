// $Id$
/*
 * WorldEdit
 * Copyright (C) 2010 sk89q <http://www.sk89q.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package ch.n1b.worldedit.schematic.schematic;


import ch.n1b.vector.Vec3D;
import ch.n1b.worldedit.schematic.block.BaseBlock;
import ch.n1b.worldedit.schematic.block.BlockID;
import ch.n1b.worldedit.schematic.minions.Vectors;
import ch.n1b.worldedit.schematic.vector.Vector;
import ch.n1b.worldedit.schematic.vector.Vector2D;

/**
 * The clipboard remembers the state of a cuboid region.
 *
 * @author sk89q
 */
public class Cuboid implements Cloneable {
    /**
     * Flip direction.
     */

    protected BaseBlock[][][] data;
    protected Vec3D size;

    /**
     * Constructs the clipboard.
     *
     * @param size
     * @deprecated use the {@link Vec3D} version
     */
    public Cuboid(Vector size) {
        this(Vectors.toVec3D(size));
    }

    /**
     * Constructs the clipboard.
     *
     * @param size
     */
    public Cuboid(Vec3D size) {
        this.size = size;
        data = new BaseBlock[size.X][size.Y][size.Z];
    }

    /**
     * Constructs the clipboard.
     *
     * @param cuboid
     */
    public Cuboid(Cuboid cuboid) {
        this.data = cuboid.data;
        this.size = cuboid.size;
    }

    /**
     * Get the width (X-direction) of the clipboard.
     *
     * @return width
     */
    public int getWidth() {
        return size.X;
    }

    /**
     * Get the length (Z-direction) of the clipboard.
     *
     * @return length
     */
    public int getLength() {
        return size.Z;
    }

    /**
     * Get the height (Y-direction) of the clipboard.
     *
     * @return height
     */
    public int getHeight() {
        return size.Y;
    }

    /**
     * Rotate the clipboard in 2D. It can only rotate by angles divisible by 90.
     *
     * @param angle in degrees
     */
    public void rotate2D(int angle) {
        angle = angle % 360;
        if (angle % 90 != 0) { // Can only rotate 90 degrees at the moment
            return;
        }
        final boolean reverse = angle < 0;
        final int numRotations = Math.abs((int) Math.floor(angle / 90.0));

        final int width = getWidth();
        final int length = getLength();
        final int height = getHeight();
        final Vector sizeRotated = Vectors.toVector(size).transform2D(angle, 0, 0, 0, 0);
        final int shiftX = sizeRotated.getX() < 0 ? -sizeRotated.getBlockX() - 1 : 0;
        final int shiftZ = sizeRotated.getZ() < 0 ? -sizeRotated.getBlockZ() - 1 : 0;

        final BaseBlock newData[][][] = new BaseBlock
                [Math.abs(sizeRotated.getBlockX())]
                [Math.abs(sizeRotated.getBlockY())]
                [Math.abs(sizeRotated.getBlockZ())];

        for (int x = 0; x < width; ++x) {
            for (int z = 0; z < length; ++z) {
                final Vector2D v = new Vector2D(x, z).transform2D(angle, 0, 0, shiftX, shiftZ);
                final int newX = v.getBlockX();
                final int newZ = v.getBlockZ();
                for (int y = 0; y < height; ++y) {
                    final BaseBlock block = data[x][y][z];
                    newData[newX][y][newZ] = block;

                    if (block == null) {
                        continue;
                    }

                    if (reverse) {
                        for (int i = 0; i < numRotations; ++i) {
                            block.rotate90Reverse();
                        }
                    } else {
                        for (int i = 0; i < numRotations; ++i) {
                            block.rotate90();
                        }
                    }
                }
            }
        }

        data = newData;
        size = new Vec3D(Math.abs(sizeRotated.getBlockX()),
                          Math.abs(sizeRotated.getBlockY()),
                          Math.abs(sizeRotated.getBlockZ()));
    }

    /**
     * Get one point in the copy. 
     *
     * @param pos The point, relative to the origin of the copy (0, 0, 0) and not to the actual copy origin.
     * @return air, if this block was outside the (non-cuboid) selection while copying
     * @throws ArrayIndexOutOfBoundsException if the position is outside the bounds of the CuboidClipboard
     * @deprecated use the {@link Vec3D} version
     */
    public BaseBlock getPoint(Vector pos) throws ArrayIndexOutOfBoundsException {
        return getPoint(Vectors.toVec3D(pos));
    }

    /**
     * Get one point in the copy.
     *
     * @param pos The point, relative to the origin of the copy (0, 0, 0) and not to the actual copy origin.
     * @return air, if this block was outside the (non-cuboid) selection while copying
     * @throws ArrayIndexOutOfBoundsException if the position is outside the bounds of the CuboidClipboard
     * @deprecated Use {@link #getBlock(Vec3D)} instead
     */
    public BaseBlock getPoint(Vec3D pos) throws ArrayIndexOutOfBoundsException {
        final BaseBlock block = getBlock(pos);
        if (block == null) {
            return new BaseBlock(BlockID.AIR);
        }
        return block;
    }

    /**
     * Get one point in the copy. 
     *
     * @param pos The point, relative to the origin of the copy (0, 0, 0) and not to the actual copy origin.
     * @return null, if this block was outside the (non-cuboid) selection while copying
     * @throws ArrayIndexOutOfBoundsException if the position is outside the bounds of the CuboidClipboard
     */
    public BaseBlock getBlock(Vec3D pos) throws ArrayIndexOutOfBoundsException {
        return data[pos.X][pos.Y][pos.Z];
    }

    /**
     * Get one point in the copy.
     *
     * @param pos The point, relative to the origin of the copy (0, 0, 0) and not to the actual copy origin.
     * @return null, if this block was outside the (non-cuboid) selection while copying
     * @throws ArrayIndexOutOfBoundsException if the position is outside the bounds of the CuboidClipboard
     * @deprecated use the {@link Vec3D} version
     */
    public BaseBlock getBlock(Vector pos) throws ArrayIndexOutOfBoundsException {
        return getBlock(Vectors.toVec3D(pos));
    }

    /**
     * Set one point in the copy. Pass null to remove the block.
     *
     * @param pt The point, relative to the origin of the copy (0, 0, 0) and not to the actual copy origin.
     * @throws ArrayIndexOutOfBoundsException if the position is outside the bounds of the CuboidClipboard
     * @deprecated use the {@link Vec3D} version
     */
    public void setBlock(Vector pt, BaseBlock block) {
        data[pt.getBlockX()][pt.getBlockY()][pt.getBlockZ()] = block;
    }

    /**
     * Set one point in the copy. Pass null to remove the block.
     *
     * @param pt The point, relative to the origin of the copy (0, 0, 0) and not to the actual copy origin.
     * @throws ArrayIndexOutOfBoundsException if the position is outside the bounds of the CuboidClipboard
     */
    public void setBlock(Vec3D pt, BaseBlock block) {
        data[pt.X][pt.Y][pt.Z] = block;
    }

    /**
     * Get the size of the copy.
     *
     * @return
     */
    public Vec3D getSize() {
        return size;
    }
}
