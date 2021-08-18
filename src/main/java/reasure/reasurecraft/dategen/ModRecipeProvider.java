package reasure.reasurecraft.dategen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.NBTIngredient;
import reasure.reasurecraft.init.ModItems;
import reasure.reasurecraft.util.Metals;
import reasure.reasurecraft.util.ModResourceLocation;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        horse_armors(consumer);
        chain_armors(consumer);
        metals(consumer, 1.5f, Metals.METALS_SILVER);

        ShapedRecipeBuilder.shaped(Items.SADDLE)
                .define('#', Tags.Items.LEATHER)
                .define('C', Items.TRIPWIRE_HOOK)
                .pattern("###")
                .pattern("###")
                .pattern("C C")
                .unlockedBy("has_leather", has(Tags.Items.LEATHER))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.POISON_APPLE.get())
                .requires(Items.APPLE)
                .requires(Items.SPIDER_EYE)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .unlockedBy("has_apple", has(Items.APPLE))
                .unlockedBy("has_spider_eye", has(Items.SPIDER_EYE))
                .save(consumer);
    }

    private void horse_armors(Consumer<IFinishedRecipe> consumer) {
        InventoryChangeTrigger.Instance hasChain = has(Items.CHAIN);
        InventoryChangeTrigger.Instance hasLeather = has(Tags.Items.LEATHER);
        InventoryChangeTrigger.Instance hasLeatherHorseArmor = has(Items.LEATHER_HORSE_ARMOR);

        ShapedRecipeBuilder.shaped(Items.LEATHER_HORSE_ARMOR)
                .define('#', Tags.Items.LEATHER)
                .define('C', Items.CHAIN)
                .pattern("#C#")
                .pattern("###")
                .pattern("#C#")
                .unlockedBy("has_leather", hasLeather)
                .unlockedBy("has_chain", hasChain)
                .save(consumer);

        ShapedRecipeBuilder.shaped(Items.LEATHER_HORSE_ARMOR)
                .define('#', Tags.Items.LEATHER)
                .define('C', Items.CHAIN)
                .pattern("###")
                .pattern("C#C")
                .pattern("###")
                .unlockedBy("has_leather", hasLeather)
                .unlockedBy("has_chain", hasChain)
                .save(consumer, new ResourceLocation(Items.LEATHER_HORSE_ARMOR.getRegistryName() + "_horizontal"));

        ShapelessRecipeBuilder.shapeless(Items.IRON_HORSE_ARMOR)
                .requires(Tags.Items.INGOTS_IRON)
                .requires(Items.LEATHER_HORSE_ARMOR)
                .unlockedBy("has_leather_horse_armor", hasLeatherHorseArmor)
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.GOLDEN_HORSE_ARMOR)
                .requires(Tags.Items.INGOTS_GOLD)
                .requires(Items.LEATHER_HORSE_ARMOR)
                .unlockedBy("has_leather_horse_armor", hasLeatherHorseArmor)
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.DIAMOND_HORSE_ARMOR)
                .requires(Tags.Items.GEMS_DIAMOND)
                .requires(Items.LEATHER_HORSE_ARMOR)
                .unlockedBy("has_leather_horse_armor", hasLeatherHorseArmor)
                .save(consumer);
    }

    private void chain_armors(Consumer<IFinishedRecipe> consumer) {
        InventoryChangeTrigger.Instance hasChain = has(Items.CHAIN);

        ShapedRecipeBuilder.shaped(Items.CHAINMAIL_HELMET)
                .define('#', Items.CHAIN)
                .pattern("###")
                .pattern("# #")
                .unlockedBy("has_chain", hasChain)
                .save(consumer);

        ShapedRecipeBuilder.shaped(Items.CHAINMAIL_CHESTPLATE)
                .define('#', Items.CHAIN)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_chain", hasChain)
                .save(consumer);

        ShapedRecipeBuilder.shaped(Items.CHAINMAIL_LEGGINGS)
                .define('#', Items.CHAIN)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .unlockedBy("has_chain", hasChain)
                .save(consumer);

        ShapedRecipeBuilder.shaped(Items.CHAINMAIL_BOOTS)
                .define('#', Items.CHAIN)
                .pattern("# #")
                .pattern("# #")
                .unlockedBy("has_chain", hasChain)
                .save(consumer);
    }

    private void oreSmelting(Consumer<IFinishedRecipe> consumer, ITag<Item> ore, IItemProvider item, String name) {
        CookingRecipeBuilder.smelting(Ingredient.of(ore), item, 0.2F, 200)
                .unlockedBy("has_ore", has(ore))
                .save(consumer, ModResourceLocation.getId(name + "_ore_smelting"));

        CookingRecipeBuilder.blasting(Ingredient.of(ore), item, 0.2F, 100)
                .unlockedBy("has_ore", has(ore))
                .save(consumer, ModResourceLocation.getId(name + "_ore_blasting"));
    }

    private void metals(Consumer<IFinishedRecipe> consumer, float smeltingXp, Metals metal) {
        if (metal.ore != null) {
            oreSmelting(consumer, metal.oreTag, metal.ingot, metal.name);
        }

        InventoryChangeTrigger.Instance hasIngot = has(metal.ingotTag);

        if (metal.block != null) {
            ShapelessRecipeBuilder.shapeless(metal.ingot, 9)
                    .requires(metal.blockTag)
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer, new ResourceLocation(metal.ingot.asItem().getRegistryName() + "_from_block"));
            ShapedRecipeBuilder.shaped(metal.block)
                    .define('#', metal.ingotTag)
                    .pattern("###")
                    .pattern("###")
                    .pattern("###")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
        }

        if (metal.nugget != null) {
            ShapelessRecipeBuilder.shapeless(metal.nugget, 9)
                    .requires(metal.ingotTag)
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
            ShapedRecipeBuilder.shaped(metal.ingot)
                    .define('#', metal.nuggetTag)
                    .pattern("###")
                    .pattern("###")
                    .pattern("###")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer, new ResourceLocation(metal.ingot.asItem().getRegistryName() + "_from_nuggets"));
        }

        if (metal.dustTag != null) {
            Ingredient dustOrChunks = metal.chunksTag != null
                    ? Ingredient.fromValues(Stream.of(new Ingredient.TagList(metal.chunksTag), new Ingredient.TagList(metal.dustTag)))
                    : Ingredient.of(metal.dustTag);
            CookingRecipeBuilder.smelting(dustOrChunks, metal.ore, smeltingXp, 200)
                    .unlockedBy("has_ore", has(metal.oreTag))
                    .save(consumer, ModResourceLocation.getId(metal.name + "_dust_smelting"));
        }

        if (metal.sword != null) {
            ShapedRecipeBuilder.shaped(metal.sword)
                    .define('#', metal.ingotTag)
                    .define('R', Tags.Items.RODS_WOODEN)
                    .pattern("#")
                    .pattern("#")
                    .pattern("R")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
        }

        if (metal.shovel != null) {
            ShapedRecipeBuilder.shaped(metal.shovel)
                    .define('#', metal.ingotTag)
                    .define('R', Tags.Items.RODS_WOODEN)
                    .pattern("#")
                    .pattern("R")
                    .pattern("R")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
        }

        if (metal.pickaxe != null) {
            ShapedRecipeBuilder.shaped(metal.pickaxe)
                    .define('#', metal.ingotTag)
                    .define('R', Tags.Items.RODS_WOODEN)
                    .pattern("###")
                    .pattern(" R ")
                    .pattern(" R ")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
        }

        if (metal.axe != null) {
            ShapedRecipeBuilder.shaped(metal.axe)
                    .define('#', metal.ingotTag)
                    .define('R', Tags.Items.RODS_WOODEN)
                    .pattern("##")
                    .pattern("#R")
                    .pattern(" R")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
        }

        if (metal.hoe != null) {
            ShapedRecipeBuilder.shaped(metal.hoe)
                    .define('#', metal.ingotTag)
                    .define('R', Tags.Items.RODS_WOODEN)
                    .pattern("##")
                    .pattern(" R")
                    .pattern(" R")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
        }

        if (metal.helmet != null) {
            ShapedRecipeBuilder.shaped(metal.helmet)
                    .define('#', metal.ingotTag)
                    .pattern("###")
                    .pattern("# #")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
        }

        if (metal.chestplate != null) {
            ShapedRecipeBuilder.shaped(metal.chestplate)
                    .define('#', metal.ingotTag)
                    .pattern("# #")
                    .pattern("###")
                    .pattern("###")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
        }

        if (metal.leggings != null) {
            ShapedRecipeBuilder.shaped(metal.leggings)
                    .define('#', metal.ingotTag)
                    .pattern("###")
                    .pattern("# #")
                    .pattern("# #")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
        }

        if (metal.boots != null) {
            ShapedRecipeBuilder.shaped(metal.boots)
                    .define('#', metal.ingotTag)
                    .pattern("# #")
                    .pattern("# #")
                    .unlockedBy("has_ingot", hasIngot)
                    .save(consumer);
        }

        if (metal.horse_armor != null) {
            ShapelessRecipeBuilder.shapeless(metal.horse_armor)
                    .requires(metal.ingotTag)
                    .requires(Items.LEATHER_HORSE_ARMOR)
                    .unlockedBy("has_leather_horse_armor", has(Items.LEATHER_HORSE_ARMOR))
                    .save(consumer);
        }
    }

    protected static class ModNBTIngredient extends NBTIngredient {
        private final ItemStack stack;

        protected ModNBTIngredient(ItemStack stack) {
            super(stack);
            this.stack = stack;
        }

        @Override
        public JsonElement toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("type", Objects.requireNonNull(CraftingHelper.getID(Serializer.INSTANCE)).toString());
            json.addProperty("item", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
            if (stack.getCount() > 1) {
                json.addProperty("count", stack.getCount());
            }
            if (stack.hasTag()) {
                assert stack.getTag() != null;
                json.addProperty("nbt", stack.getTag().toString());
            }
            return json;
        }
    }
}
