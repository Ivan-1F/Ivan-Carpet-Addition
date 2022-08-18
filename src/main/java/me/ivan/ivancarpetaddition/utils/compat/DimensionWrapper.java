package me.ivan.ivancarpetaddition.utils.compat;

import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.Objects;

/*
 * Reference: Carpet TIS Addition
 * A wrapper class to deal with dimension type class differences between minecraft version:
 * - DimensionType in 1.15-
 * - RegistryKey<World> in 1.16+
 */
public class DimensionWrapper {
    public static final DimensionWrapper OVERWORLD = of(DimensionType.OVERWORLD);
    public static final DimensionWrapper THE_NETHER = of(DimensionType.THE_NETHER);
    public static final DimensionWrapper THE_END = of(DimensionType.THE_END);

    private final DimensionType dimensionType;

    public DimensionWrapper(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public static DimensionWrapper of(DimensionType dimensionType) {
        return new DimensionWrapper(dimensionType);
    }

    public static DimensionWrapper of(World world) {
        return new DimensionWrapper(world.getDimension().getType());
    }

    public static DimensionWrapper of(Entity entity) {
        return of(entity.getEntityWorld());
    }

    public DimensionType getValue() {
        return this.dimensionType;
    }

    public Identifier getIdentifier() {
        return DimensionType.getId(this.dimensionType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DimensionWrapper that = (DimensionWrapper) o;
        return Objects.equals(dimensionType, that.dimensionType);
    }

    @Override
    public int hashCode() {
        return this.dimensionType.hashCode();
    }

    public String getIdentifierString() {
        return this.dimensionType.toString();
    }

    @Deprecated
    @Override
    public String toString() {
        return this.getIdentifierString();
    }
}
