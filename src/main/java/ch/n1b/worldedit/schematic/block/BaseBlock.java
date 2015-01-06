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

package ch.n1b.worldedit.schematic.block;

/**
 * Represents a block.
 *
 * @see Block new class to replace this one
 * @author sk89q
 */
public class BaseBlock extends Block {
    
    /**
     * Construct the block with its type, with default data value 0.
     *
     * @param type type ID of block
     */
    public BaseBlock(int type) {
        this(type, 0);
    }

    /**
     * Construct the block with its type and data.
     *
     * @param type type ID of block
     * @param data data value
     */
    public BaseBlock(int type, int data) {
        super(type, data);
    }

    public int getMask() {
        return ~0;
    }

    /**
     * Get the type of block.
     * 
     * @return the type
     */
    public int getType() {
        return getId();
    }

    /**
     * Set the type of block.
     * 
     * @param type the type to set
     */
    public void setType(int type) {
        setId(type);
    }

    /**
     * Returns true if it's air.
     *
     * @return if air
     */
    public boolean isAir() {
        return getType() == BlockID.AIR;
    }

    /**
     * Rotate this block 90 degrees.
     * 
     * @return new data value
     */
    public int rotate90() {
        int newData = BlockData.rotate90(getType(), getData());
        setData(newData);
        return newData;
    }

    /**
     * Rotate this block -90 degrees.
     * 
     * @return new data value
     */
    public int rotate90Reverse() {
        int newData = BlockData.rotate90Reverse(getType(), getData());
        setData((short) newData);
        return newData;
    }

    /**
     * Checks whether the type ID and data value are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaseBlock)) {
            return false;
        }

        final BaseBlock otherBlock = (BaseBlock) o;
        if (getType() != otherBlock.getType()) {
            return false;
        }

        return getData() == otherBlock.getData();
    }
}
