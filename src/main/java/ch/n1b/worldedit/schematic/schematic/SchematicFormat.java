/*
 * WorldEdit
 * Copyright (C) 2012 sk89q <http://www.sk89q.com> and contributors
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

import ch.n1b.worldedit.schematic.data.DataException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a format that a schematic can be stored as
 * @author zml2008
 */
public abstract class SchematicFormat {
    private static final Map<String, SchematicFormat> SCHEMATIC_FORMATS = new HashMap<String, SchematicFormat>();

    // Built-in schematic formats
    public static final SchematicFormat MCEDIT = new MCEditSchematicFormat();

    public static Set<SchematicFormat> getFormats() {
        return Collections.unmodifiableSet(new HashSet<SchematicFormat>(SCHEMATIC_FORMATS.values()));
    }

    public static SchematicFormat getFormat(String lookupName) {
        return SCHEMATIC_FORMATS.get(lookupName.toLowerCase());
    }

    public static SchematicFormat getFormat(File file) {
        if (!file.isFile()) {
            return null;
        }

        for (SchematicFormat format : SCHEMATIC_FORMATS.values()) {
            if (format.isOfFormat(file)) {
                return format;
            }
        }
        return null;
    }

    private final String name;
    private final String[] lookupNames;

    protected SchematicFormat(String name, String... lookupNames) {
        this.name = name;
        List<String> registeredLookupNames = new ArrayList<String>(lookupNames.length);
        for (int i = 0; i < lookupNames.length; ++i) {
            if (i == 0 || !SCHEMATIC_FORMATS.containsKey(lookupNames[i].toLowerCase())) {
                SCHEMATIC_FORMATS.put(lookupNames[i].toLowerCase(), this);
                registeredLookupNames.add(lookupNames[i].toLowerCase());
            }
        }
        this.lookupNames = registeredLookupNames.toArray(new String[registeredLookupNames.size()]);
    }

    /**
     * Gets the official/display name for this schematic format
     *
     * @return The display name for this schematic format
     */
    public String getName() {
        return name;
    }

    public String[] getLookupNames() {
        return lookupNames;
    }

    /**
     * Loads a schematic from the given file into a CuboidClipboard
     * @param file The file to load from
     * @return The CuboidClipboard containing the contents of this schematic
     * @throws IOException If an error occurs while reading data
     * @throws DataException if data is not in the correct format
     */
    public abstract Cuboid load(File file) throws IOException, DataException;

    /**
     * Saves the data from the specified CuboidClipboard to the given file, overwriting any
     * existing data in the file
     * @param clipboard The clipboard to get data from
     * @param file The file to save to
     * @throws IOException If an error occurs while writing data
     * @throws DataException If the clipboard has data which cannot be stored
     */
    public abstract void save(Cuboid clipboard, File file) throws IOException, DataException;

    public abstract boolean isOfFormat(File file);
}
