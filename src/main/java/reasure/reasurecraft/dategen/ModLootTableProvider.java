package reasure.reasurecraft.dategen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.util.ResourceLocation;
import reasure.reasurecraft.init.ModBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "More Machine - Loot Tables";
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach(
                (id, table) -> LootTableManager.validate(validationtracker, id, table)
        );
    }

    public static class ModBlockLootTables extends BlockLootTables {
        @Override
        protected void addTables() {
            dropSelf(ModBlocks.SILVER_BLOCK.get());
            dropSelf(ModBlocks.SILVER_ORE.get());
            dropSelf(ModBlocks.METAL_PRESS.get());
            dropSelf(ModBlocks.OBSIDIAN_FRAME.get());
            cropDrop(ModBlocks.PEANUTS.get(), 5, 0.5714286f);
        }

        @SuppressWarnings("SameParameterValue")
        private void cropDrop(CropsBlock block, int extra, float probability) {
            ILootCondition.IBuilder builder = BlockStateProperty.hasBlockStateProperties(block)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(block.getAgeProperty(), 7));

            this.add(block, applyExplosionDecay(block, LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(ItemLootEntry.lootTableItem(block)))
                    .withPool(LootPool.lootPool().when(builder).add(ItemLootEntry.lootTableItem(block)
                            .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, probability, extra)))
                    )
            ));
        }


        @Override
        protected Iterable<Block> getKnownBlocks() {
            ArrayList<Block> blocks = new ArrayList<>();
            blocks.add(ModBlocks.SILVER_BLOCK.get());
            blocks.add(ModBlocks.SILVER_ORE.get());
            blocks.add(ModBlocks.METAL_PRESS.get());
            blocks.add(ModBlocks.OBSIDIAN_FRAME.get());
            blocks.add(ModBlocks.PEANUTS.get());
            return blocks;
        }
    }
}
