package ch.n1b.libschem;

import ch.n1b.worldedit.schematic.data.DataException;
import ch.n1b.worldedit.schematic.schematic.Cuboid;
import ch.n1b.worldedit.schematic.schematic.SchematicFormat;

import java.io.File;
import java.io.IOException;

/**
 * API for libschem, simple Facade
 * @version schemli 20.06.2015
 * @author Thomas
 */
public class LibschemAPI {

    public static Cuboid loadSchematic(File file) throws IOException, DataException {
        return SchematicFormat.MCEDIT.load(file);
    }

    public static Cuboid loadSchematic(String filename) throws IOException, DataException {
        return SchematicFormat.MCEDIT.load(new File(filename));
    }

    public static void saveSchematic(File file,Cuboid cuboid) throws IOException, DataException {
        SchematicFormat.MCEDIT.save(cuboid,file);
    }

    public static void saveSchematic(String filename,Cuboid cuboid) throws IOException, DataException {
        SchematicFormat.MCEDIT.save(cuboid,new File(filename));
    }

}
