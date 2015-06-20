/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package ch.n1b.worldedit.schematic.block;


/**
 * Block data related classes.
 */
public final class BlockData {

    private BlockData() {
    }

    /**
     * Rotate a block's data value 90 degrees (north->east->south->west->north);
     * 
     * @param type the type ID of the bock
     * @param data the data ID of the block
     * @return the new data value
     */
    public static int rotate90(int type, int data) {
        switch (type) {
        case BlockID.TORCH:
        case BlockID.REDSTONE_TORCH_OFF:
        case BlockID.REDSTONE_TORCH_ON:
            switch (data) {
            case 1: return 3;
            case 2: return 4;
            case 3: return 2;
            case 4: return 1;
            }
            break;

        case BlockID.MINECART_TRACKS:
            switch (data) {
            case 6: return 7;
            case 7: return 8;
            case 8: return 9;
            case 9: return 6;
            }
            /* FALL-THROUGH */

        case BlockID.POWERED_RAIL:
        case BlockID.DETECTOR_RAIL:
        case BlockID.ACTIVATOR_RAIL:
            switch (data & 0x7) {
            case 0: return 1 | (data & ~0x7);
            case 1: return 0 | (data & ~0x7);
            case 2: return 5 | (data & ~0x7);
            case 3: return 4 | (data & ~0x7);
            case 4: return 2 | (data & ~0x7);
            case 5: return 3 | (data & ~0x7);
            }
            break;

        case BlockID.OAK_WOOD_STAIRS:
        case BlockID.COBBLESTONE_STAIRS:
        case BlockID.BRICK_STAIRS:
        case BlockID.STONE_BRICK_STAIRS:
        case BlockID.NETHER_BRICK_STAIRS:
        case BlockID.SANDSTONE_STAIRS:
        case BlockID.SPRUCE_WOOD_STAIRS:
        case BlockID.BIRCH_WOOD_STAIRS:
        case BlockID.JUNGLE_WOOD_STAIRS:
        case BlockID.QUARTZ_STAIRS:
        case BlockID.ACACIA_STAIRS:
        case BlockID.DARK_OAK_STAIRS:
            switch (data) {
            case 0: return 2;
            case 1: return 3;
            case 2: return 1;
            case 3: return 0;
            case 4: return 6;
            case 5: return 7;
            case 6: return 5;
            case 7: return 4;
            }
            break;

        case BlockID.STONE_BUTTON:
        case BlockID.WOODEN_BUTTON: {
            int thrown = data & 0x8;
            switch (data & ~0x8) {
            case 1: return 3 | thrown;
            case 2: return 4 | thrown;
            case 3: return 2 | thrown;
            case 4: return 1 | thrown;
            // 0 and 5 are vertical
            }
            break;
        }

        case BlockID.LEVER: {
            int thrown = data & 0x8;
            switch (data & ~0x8) {
            case 1: return 3 | thrown;
            case 2: return 4 | thrown;
            case 3: return 2 | thrown;
            case 4: return 1 | thrown;
            case 5: return 6 | thrown;
            case 6: return 5 | thrown;
            case 7: return 0 | thrown;
            case 0: return 7 | thrown;
            }
            break;
        }

        case BlockID.WOODEN_DOOR:
        case BlockID.IRON_DOOR:
            if ((data & 0x8) != 0) {
                // door top halves contain no orientation information
                break;
            }

            /* FALL-THROUGH */

        case BlockID.COCOA_PLANT:
        case BlockID.TRIPWIRE_HOOK: {
            int extra = data & ~0x3;
            int withoutFlags = data & 0x3;
            switch (withoutFlags) {
            case 0: return 1 | extra;
            case 1: return 2 | extra;
            case 2: return 3 | extra;
            case 3: return 0 | extra;
            }
            break;
        }
        case BlockID.SIGN_POST:
            return (data + 4) % 16;

        case BlockID.LADDER:
        case BlockID.WALL_SIGN:
        case BlockID.CHEST:
        case BlockID.FURNACE:
        case BlockID.BURNING_FURNACE:
        case BlockID.ENDER_CHEST:
        case BlockID.TRAPPED_CHEST:
        case BlockID.HOPPER: {
            int extra = data & 0x8;
            int withoutFlags = data & ~0x8;
            switch (withoutFlags) {
            case 2: return 5 | extra;
            case 3: return 4 | extra;
            case 4: return 2 | extra;
            case 5: return 3 | extra;
            }
            break;
        }
        case BlockID.DISPENSER:
        case BlockID.DROPPER:
            int dispPower = data & 0x8;
            switch (data & ~0x8) {
            case 2: return 5 | dispPower;
            case 3: return 4 | dispPower;
            case 4: return 2 | dispPower;
            case 5: return 3 | dispPower;
            }
            break;

        case BlockID.PUMPKIN:
        case BlockID.JACKOLANTERN:
            switch (data) {
            case 0: return 1;
            case 1: return 2;
            case 2: return 3;
            case 3: return 0;
            }
            break;

        case BlockID.HAY_BLOCK:
        case BlockID.LOG:
        case BlockID.LOG2:
            if (data >= 4 && data <= 11) data ^= 0xc;
            break;

        case BlockID.COMPARATOR_OFF:
        case BlockID.COMPARATOR_ON:
        case BlockID.REDSTONE_REPEATER_OFF:
        case BlockID.REDSTONE_REPEATER_ON:
            int dir = data & 0x03;
            int delay = data - dir;
            switch (dir) {
            case 0: return 1 | delay;
            case 1: return 2 | delay;
            case 2: return 3 | delay;
            case 3: return 0 | delay;
            }
            break;

        case BlockID.TRAP_DOOR:
            int withoutOrientation = data & ~0x3;
            int orientation = data & 0x3;
            switch (orientation) {
            case 0: return 3 | withoutOrientation;
            case 1: return 2 | withoutOrientation;
            case 2: return 0 | withoutOrientation;
            case 3: return 1 | withoutOrientation;
            }
            break;

        case BlockID.PISTON_BASE:
        case BlockID.PISTON_STICKY_BASE:
        case BlockID.PISTON_EXTENSION:
            final int rest = data & ~0x7;
            switch (data & 0x7) {
            case 2: return 5 | rest;
            case 3: return 4 | rest;
            case 4: return 2 | rest;
            case 5: return 3 | rest;
            }
            break;

        case BlockID.BROWN_MUSHROOM_CAP:
        case BlockID.RED_MUSHROOM_CAP:
            if (data >= 10) return data;
            return (data * 3) % 10;

        case BlockID.VINE:
            return ((data << 1) | (data >> 3)) & 0xf;

        case BlockID.FENCE_GATE:
            return ((data + 1) & 0x3) | (data & ~0x3);

        case BlockID.ANVIL:
            return data ^ 0x1;

        case BlockID.BED:
            return data & ~0x3 | (data + 1) & 0x3;

        case BlockID.HEAD:
            switch (data) {
                case 2: return 5;
                case 3: return 4;
                case 4: return 2;
                case 5: return 3;
            }
        }

        return data;
    }

    /**
     * Rotate a block's data value -90 degrees (north<-east<-south<-west<-north);
     *
     * @param type the type ID of the bock
     * @param data the data ID of the block
     * @return the new data value
     */
    public static int rotate90Reverse(int type, int data) {
        //Not efficient but easy
        return rotate90(type,rotate90(type,rotate90(type,data)));
    }
}
