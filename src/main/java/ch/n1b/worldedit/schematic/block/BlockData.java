// $Id$
/*
 * WorldEdit
 * Copyright (C) 2010, 2011 sk89q <http://www.sk89q.com> and contributors
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
 * Block data related classes.
 *
 * @author sk89q
 */
public final class BlockData {
    /**
     * Rotate a block's data value 90 degrees (north->east->south->west->north);
     * 
     * @param type
     * @param data
     * @return
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

        case BlockID.QUARTZ_BLOCK:
            switch (data) {
                case 3: return 4;
                case 4: return 3;
            }
            break;

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

        //Flip levers to match up correct orientation, not their actual state
        case BlockID.LEVER:
        case BlockID.STONE_BUTTON:
        case BlockID.WOODEN_BUTTON:
            int thrown = data & 0x8;
            int withoutThrown = data & ~0x8;
            switch (withoutThrown) {
            case 1: return 3 | thrown;
            case 2: return 4 | thrown;
            case 3: return 2 | thrown;
            case 4: return 1 | thrown;
            case 5: if (thrown == 0x8) { return 6; } else { return 6 | 0x8; }
            case 6: return 5 | thrown;
            case 7: return 0 | thrown;
            case 0: return 7 | thrown;
            }
            break;

        case BlockID.WOODEN_DOOR:
        case BlockID.IRON_DOOR:
//            if ((data & 0x8) != 0) {
//                // door top halves contain no orientation information
//                break;
//            }
//
//            /* FALL-THROUGH */
            if ((data & 0x8) != 0) {
                break;
            } else {
                switch (data) {
                    case 0: return 1;
                    case 1: return 2;
                    case 2: return 3;
                    case 3: return 0;
                    case 4: return 5;
                    case 5: return 6;
                    case 6: return 7;
                    case 7: return 4;
                }
            }

        case BlockID.COCOA_PLANT:
        case BlockID.TRIPWIRE_HOOK:
            int extra = data & ~0x3;
            int withoutFlags = data & 0x3;
            switch (withoutFlags) {
            case 0: return 1 | extra;
            case 1: return 2 | extra;
            case 2: return 3 | extra;
            case 3: return 0 | extra;
            }
            break;

        case BlockID.SIGN_POST:
            return (data + 4) % 16;

        case BlockID.LADDER:
        case BlockID.WALL_SIGN:
        case BlockID.CHEST:
        case BlockID.FURNACE:
        case BlockID.BURNING_FURNACE:
        case BlockID.ENDER_CHEST:
        case BlockID.TRAPPED_CHEST:
        case BlockID.HOPPER:
            switch (data) {
            case 2: return 5;
            case 3: return 4;
            case 4: return 2;
            case 5: return 3;
            }
            break;

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
            //return data & ~0x3 | (data + 1) & 0x3;
            switch (data) {
                case 0: return 1;
                case 1: return 2;
                case 2: return 3;
                case 3: return 0;
                case 8: return 9;
                case 9: return 10;
                case 10: return 11;
                case 11: return 8;
            }
            break;

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
     * @param type
     * @param data
     * @return
     */
    public static int rotate90Reverse(int type, int data) {
        // case ([0-9]+): return ([0-9]+) -> case \2: return \1

        switch (type) {
        case BlockID.TORCH:
        case BlockID.REDSTONE_TORCH_OFF:
        case BlockID.REDSTONE_TORCH_ON:
            switch (data) {
            case 3: return 1;
            case 4: return 2;
            case 2: return 3;
            case 1: return 4;
            }
            break;

        case BlockID.MINECART_TRACKS:
            switch (data) {
            case 7: return 6;
            case 8: return 7;
            case 9: return 8;
            case 6: return 9;
            }
            /* FALL-THROUGH */

        case BlockID.QUARTZ_BLOCK:
            switch (data) {
                case 3: return 4;
                case 4: return 3;
            }
            /* FALL-THROUGH */

        case BlockID.POWERED_RAIL:
        case BlockID.DETECTOR_RAIL:
        case BlockID.ACTIVATOR_RAIL:
            int power = data & ~0x7;
            switch (data & 0x7) {
            case 1: return 0 | power;
            case 0: return 1 | power;
            case 5: return 2 | power;
            case 4: return 3 | power;
            case 2: return 4 | power;
            case 3: return 5 | power;
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
            case 2: return 0;
            case 3: return 1;
            case 1: return 2;
            case 0: return 3;
            case 6: return 4;
            case 7: return 5;
            case 5: return 6;
            case 4: return 7;
            }
            break;

        case BlockID.LEVER:
        case BlockID.STONE_BUTTON:
        case BlockID.WOODEN_BUTTON:
            int thrown = data & 0x8;
            int withoutThrown = data & ~0x8;
            switch (withoutThrown) {
                case 1: return 3 | thrown;
                case 2: return 4 | thrown;
                case 3: return 2 | thrown;
                case 4: return 1 | thrown;
                case 5: return 6 | thrown;
                case 6: if (thrown == 0x8) { return 5; } else { return 5 | 0x8; }
                case 7: return 0 | thrown;
                case 0: return 7 | thrown;
            }
            break;

        case BlockID.WOODEN_DOOR:
        case BlockID.IRON_DOOR:
            if ((data & 0x8) != 0) {
                break;
            } else {
                switch (data) {
                    case 0: return 3;
                    case 1: return 0;
                    case 2: return 1;
                    case 3: return 2;
                    case 4: return 7;
                    case 5: return 4;
                    case 6: return 5;
                    case 7: return 6;
                }
            }

        case BlockID.COCOA_PLANT:
        case BlockID.TRIPWIRE_HOOK:
            int extra = data & ~0x3;
            int withoutFlags = data & 0x3;
            switch (withoutFlags) {
            case 1: return 0 | extra;
            case 2: return 1 | extra;
            case 3: return 2 | extra;
            case 0: return 3 | extra;
            }
            break;

        case BlockID.SIGN_POST:
            return (data + 12) % 16;

        case BlockID.LADDER:
        case BlockID.WALL_SIGN:
        case BlockID.CHEST:
        case BlockID.FURNACE:
        case BlockID.BURNING_FURNACE:
        case BlockID.ENDER_CHEST:
        case BlockID.TRAPPED_CHEST:
        case BlockID.HOPPER:
            switch (data) {
            case 5: return 2;
            case 4: return 3;
            case 2: return 4;
            case 3: return 5;
            }
            break;

        case BlockID.DISPENSER:
        case BlockID.DROPPER:
            int dispPower = data & 0x8;
            switch (data & ~0x8) {
            case 5: return 2 | dispPower;
            case 4: return 3 | dispPower;
            case 2: return 4 | dispPower;
            case 3: return 5 | dispPower;
            }
            break;
        case BlockID.PUMPKIN:
        case BlockID.JACKOLANTERN:
            switch (data) {
            case 1: return 0;
            case 2: return 1;
            case 3: return 2;
            case 0: return 3;
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
            case 1: return 0 | delay;
            case 2: return 1 | delay;
            case 3: return 2 | delay;
            case 0: return 3 | delay;
            }
            break;

        case BlockID.TRAP_DOOR:
            int withoutOrientation = data & ~0x3;
            int orientation = data & 0x3;
            switch (orientation) {
            case 3: return 0 | withoutOrientation;
            case 2: return 1 | withoutOrientation;
            case 0: return 2 | withoutOrientation;
            case 1: return 3 | withoutOrientation;
            }

        case BlockID.PISTON_BASE:
        case BlockID.PISTON_STICKY_BASE:
        case BlockID.PISTON_EXTENSION:
            final int rest = data & ~0x7;
            switch (data & 0x7) {
            case 5: return 2 | rest;
            case 4: return 3 | rest;
            case 2: return 4 | rest;
            case 3: return 5 | rest;
            }
            break;

        case BlockID.BROWN_MUSHROOM_CAP:
        case BlockID.RED_MUSHROOM_CAP:
            if (data >= 10) return data;
            return (data * 7) % 10;

        case BlockID.VINE:
            return ((data >> 1) | (data << 3)) & 0xf;

        case BlockID.FENCE_GATE:
            return ((data + 3) & 0x3) | (data & ~0x3);

        case BlockID.ANVIL:
            return data ^ 0x1;

        case BlockID.BED:
            switch (data) {
                case 0: return 3;
                case 1: return 0;
                case 2: return 1;
                case 3: return 2;
                case 8: return 11;
                case 9: return 8;
                case 10: return 9;
                case 11: return 10;
            }

        case BlockID.HEAD:
            switch (data) {
                case 2: return 4;
                case 3: return 5;
                case 4: return 3;
                case 5: return 2;
            }
        }

        return data;
    }

    /**
     * Flip a block's data value.
     * 
     * @param type
     * @param data
     * @return
     */
    public static int flip(int type, int data) {
        return rotate90(type, rotate90(type, data));
    }


    /**
     * Cycle a block's data value. This usually goes through some rotational pattern
     * depending on the block. If it returns -1, it means the id and data specified
     * do not have anything to cycle to.
     *
     * @param type block id to be cycled
     * @param data block data value that it starts at
     * @param increment whether to go forward (1) or backward (-1) in the cycle
     * @return the new data value for the block
     */
    public static int cycle(int type, int data, int increment) {
        if (increment != -1 && increment != 1) {
            throw new IllegalArgumentException("Increment must be 1 or -1.");
        }

        int store;
        switch (type) {

        // special case here, going to use "forward" for type and "backward" for orientation
        case BlockID.LOG:
        case BlockID.LOG2:
            if (increment == -1) {
                store = data & 0x3; // copy bottom (type) bits
                return mod((data & ~0x3) + 4, 16) | store; // switch orientation with top bits and reapply bottom bits;
            } else {
                store = data & ~0x3; // copy top (orientation) bits
                return mod((data & 0x3) + 1, 4) | store;  // switch type with bottom bits and reapply top bits
            }

        case BlockID.LONG_GRASS:
        case BlockID.SANDSTONE:
        case BlockID.DIRT:
            if (data > 2) return -1;
            return mod((data + increment), 3);

        case BlockID.TORCH:
        case BlockID.REDSTONE_TORCH_ON:
        case BlockID.REDSTONE_TORCH_OFF:
            if (data < 1 || data > 4) return -1;
            return mod((data - 1 + increment), 4) + 1;

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
            if (data > 7) return -1;
            return mod((data + increment), 8);

        case BlockID.STONE_BRICK:
        case BlockID.QUARTZ_BLOCK:
        case BlockID.PUMPKIN:
        case BlockID.JACKOLANTERN:
        case BlockID.NETHER_WART:
        case BlockID.CAULDRON:
        case BlockID.WOODEN_STEP:
        case BlockID.DOUBLE_WOODEN_STEP:
        case BlockID.HAY_BLOCK:
            if (data > 3) return -1;
            return mod((data + increment), 4);

        case BlockID.STEP:
        case BlockID.DOUBLE_STEP:
        case BlockID.CAKE_BLOCK:
        case BlockID.PISTON_BASE:
        case BlockID.PISTON_STICKY_BASE:
        case BlockID.SILVERFISH_BLOCK:
            if (data > 5) return -1;
            return mod((data + increment), 6);

        case BlockID.DOUBLE_PLANT:
            store = data & 0x8; // top half flag
            data &= ~0x8;
            if (data > 5) return -1;
            return mod((data + increment), 6) | store;

        case BlockID.CROPS:
        case BlockID.PUMPKIN_STEM:
        case BlockID.MELON_STEM:
            if (data > 6) return -1;
            return mod((data + increment), 7);

        case BlockID.SOIL:
        case BlockID.RED_FLOWER:
            if (data > 8) return -1;
            return mod((data + increment), 9);

        case BlockID.RED_MUSHROOM_CAP:
        case BlockID.BROWN_MUSHROOM_CAP:
            if (data > 10) return -1;
            return mod((data + increment), 11);

        case BlockID.CACTUS:
        case BlockID.REED:
        case BlockID.SIGN_POST:
        case BlockID.VINE:
        case BlockID.SNOW:
        case BlockID.COCOA_PLANT:
            if (data > 15) return -1;
            return mod((data + increment), 16);

        case BlockID.FURNACE:
        case BlockID.BURNING_FURNACE:
        case BlockID.WALL_SIGN:
        case BlockID.LADDER:
        case BlockID.CHEST:
        case BlockID.ENDER_CHEST:
        case BlockID.TRAPPED_CHEST:
        case BlockID.HOPPER:
            if (data < 2 || data > 5) return -1;
            return mod((data - 2 + increment), 4) + 2;

        case BlockID.DISPENSER:
        case BlockID.DROPPER:
            store = data & 0x8;
            data &= ~0x8;
            if (data > 5) return -1;
            return mod((data + increment), 6) | store;

        case BlockID.REDSTONE_REPEATER_OFF:
        case BlockID.REDSTONE_REPEATER_ON:
        case BlockID.COMPARATOR_OFF:
        case BlockID.COMPARATOR_ON:
        case BlockID.TRAP_DOOR:
        case BlockID.FENCE_GATE:
        case BlockID.LEAVES:
        case BlockID.LEAVES2:
            if (data > 7) return -1;
            store = data & ~0x3;
            return mod(((data & 0x3) + increment), 4) | store;

        case BlockID.MINECART_TRACKS:
            if (data < 6 || data > 9) return -1;
            return mod((data - 6 + increment), 4) + 6;

        case BlockID.SAPLING:
            if ((data & 0x3) == 3 || data > 15) return -1;
            store = data & ~0x3;
            return mod(((data & 0x3) + increment), 3) | store;

        case BlockID.FLOWER_POT:
            if (data > 13) return -1;
            return mod((data + increment), 14);

        case BlockID.CLOTH:
        case BlockID.STAINED_CLAY:
        case BlockID.CARPET:
        case BlockID.STAINED_GLASS:
        default:
            return -1;
        }
    }

    /**
     * Better modulo, not just remainder.
     */
    private static int mod(int x, int y) {
        int res = x % y;
        return res < 0 ? res + y : res;
    }
}
