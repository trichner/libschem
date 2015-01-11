package ch.n1b.worldedit.schematic.minions;

import ch.n1b.vector.Vec3D;
import ch.n1b.worldedit.schematic.vector.Vector;

/**
 * Created on 11.01.2015.
 *
 * @author Thomas
 */
public class Vectors {
    public static Vec3D toVec3D(Vector vector){
        return new Vec3D(vector.getBlockX(),vector.getBlockY(),vector.getBlockZ());
    }
    public static Vector toVector(Vec3D vector){
        return new Vector(vector.X,vector.Y,vector.Z);
    }
}
