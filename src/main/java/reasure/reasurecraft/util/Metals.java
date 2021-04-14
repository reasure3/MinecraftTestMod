package reasure.reasurecraft.util;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import reasure.reasurecraft.init.ModBlocks;
import reasure.reasurecraft.init.ModItems;
import reasure.reasurecraft.init.ModTags;

public class Metals {
    public static final Metals METALS_SILVER = new Metals("silver",
            ModItems.SILVER_INGOT.get(), ModTags.Items.INGOTS_SILVER)
            .block(ModBlocks.SILVER_BLOCK.get(), ModTags.Items.STORAGE_BLOCKS_SILVER)
            .ore(ModBlocks.SILVER_ORE.get(), ModTags.Items.ORES_SILVER)
            .dust(ModItems.SILVER_DUST.get(), ModTags.Items.DUSTS_SILVER)
            .nugget(ModItems.SILVER_NUGGET.get(), ModTags.Items.NUGGETS_SILVER)
            .sword(ModItems.SILVER_SWORD.get()).pickaxe(ModItems.SILVER_PICKAXE.get())
            .axe(ModItems.SILVER_AXE.get()).shovel(ModItems.SILVER_SHOVEL.get()).hoe(ModItems.SILVER_HOE.get())
            .helmet(ModItems.SILVER_HELMET.get()).chestplate(ModItems.SILVER_CHESTPLATE.get())
            .leggings(ModItems.SILVER_LEGGINGS.get()).boots(ModItems.SILVER_BOOTS.get())
            .horse_armor(ModItems.SILVER_HORSE_ARMOR.get());

    public final String name;
    public final IItemProvider ingot;
    public final ITag<Item> ingotTag;
    public IItemProvider ore;
    public ITag<Item> oreTag;
    public IItemProvider block;
    public ITag<Item> blockTag;
    public IItemProvider nugget;
    public ITag<Item> nuggetTag;
    public IItemProvider dust;
    public ITag<Item> dustTag;
    public IItemProvider chunks;
    public ITag<Item> chunksTag;

    public IItemProvider sword;
    public IItemProvider pickaxe;
    public IItemProvider axe;
    public IItemProvider shovel;
    public IItemProvider hoe;
    public IItemProvider helmet;
    public IItemProvider chestplate;
    public IItemProvider leggings;
    public IItemProvider boots;
    public IItemProvider horse_armor;

    public Metals(String name, IItemProvider ingot, ITag<Item> ingotTag) {
        this.name = name;
        this.ingot = ingot;
        this.ingotTag = ingotTag;
    }

    public Metals ore(IItemProvider item, ITag<Item> tag) {
        this.ore = item;
        this.oreTag = tag;
        return this;
    }

    public Metals block(IItemProvider item, ITag<Item> tag) {
        this.block = item;
        this.blockTag = tag;
        return this;
    }

    public Metals nugget(IItemProvider item, ITag<Item> tag) {
        this.nugget = item;
        this.nuggetTag = tag;
        return this;
    }

    public Metals dust(IItemProvider item, ITag<Item> tag) {
        this.dust = item;
        this.dustTag = tag;
        return this;
    }

    public Metals chunks(IItemProvider item, ITag<Item> tag) {
        this.chunks = item;
        this.chunksTag = tag;
        return this;
    }

    public Metals sword(IItemProvider item) {
        this.sword = item;
        return this;
    }

    public Metals pickaxe(IItemProvider item) {
        this.pickaxe = item;
        return this;
    }

    public Metals axe(IItemProvider item) {
        this.axe = item;
        return this;
    }

    public Metals shovel(IItemProvider item) {
        this.shovel = item;
        return this;
    }

    public Metals hoe(IItemProvider item) {
        this.hoe = item;
        return this;
    }

    public Metals helmet(IItemProvider item) {
        this.helmet = item;
        return this;
    }

    public Metals chestplate(IItemProvider item) {
        this.chestplate = item;
        return this;
    }

    public Metals leggings(IItemProvider item) {
        this.leggings = item;
        return this;
    }

    public Metals boots(IItemProvider item) {
        this.boots = item;
        return this;
    }

    public Metals horse_armor(IItemProvider item) {
        this.horse_armor = item;
        return this;
    }
}
