package reasure.reasurecraft.dategen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.init.ModBlocks;
import reasure.reasurecraft.init.ModItems;
import reasure.reasurecraft.item.itemgroup.ModItemGroup;
import reasure.reasurecraft.util.TranslateHelper;

import java.util.Objects;

public class ModLanguageProvider extends LanguageProvider {
    private final String locale;

    public ModLanguageProvider(DataGenerator gen, String locale) {
        super(gen, ReasureCraft.MOD_ID, locale);
        this.locale = locale;
    }

    private void blocks(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.add(ModBlocks.SILVER_ORE.get(), "Silver Ore");
                helper.add(ModBlocks.SILVER_BLOCK.get(), "Block of Silver");
                helper.add(ModBlocks.METAL_PRESS.get(), "Metal Press");
                helper.add(ModBlocks.DISPLAY_CASE.get(), "Display Case");
                helper.add(ModBlocks.OBSIDIAN_FRAME.get(), "Obsidian Frame");
                helper.add(ModBlocks.PEANUTS.get(), "Peanuts");
                helper.add(ModBlocks.RUBBER.get(), "Rubber");
                helper.add(ModBlocks.LIGHTER_BLOCK.get(), "Lighter Block");
                break;
            case "ko_kr":
                helper.add(ModBlocks.SILVER_ORE.get(), "은광석");
                helper.add(ModBlocks.SILVER_BLOCK.get(), "은 블록");
                helper.add(ModBlocks.METAL_PRESS.get(), "금속 유압기");
                helper.add(ModBlocks.DISPLAY_CASE.get(), "전시장");
                helper.add(ModBlocks.OBSIDIAN_FRAME.get(), "흑요석 프레임");
                helper.add(ModBlocks.PEANUTS.get(), "땅콩");
                helper.add(ModBlocks.RUBBER.get(), "고무");
                helper.add(ModBlocks.LIGHTER_BLOCK.get(), "라이터 블럭");
                break;
        }
    }

    private void items(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.add(ModItems.SILVER_INGOT.get(), "Silver Ingot");
                helper.add(ModItems.SILVER_NUGGET.get(), "Silver Nugget");
                helper.add(ModItems.SILVER_DUST.get(), "Silver Dust");
                helper.add(ModItems.SILVER_SWORD.get(), "Silver Sword");
                helper.add(ModItems.SILVER_SHOVEL.get(), "Silver Shovel");
                helper.add(ModItems.SILVER_PICKAXE.get(), "Silver Pickaxe");
                helper.add(ModItems.SILVER_AXE.get(), "Silver Axe");
                helper.add(ModItems.SILVER_HOE.get(), "Silver Hoe");
                helper.add(ModItems.SILVER_HELMET.get(), "Silver Helmet");
                helper.add(ModItems.SILVER_CHESTPLATE.get(), "Silver Chestplate");
                helper.add(ModItems.SILVER_LEGGINGS.get(), "Silver Leggings");
                helper.add(ModItems.SILVER_BOOTS.get(), "Silver Boots");
                helper.add(ModItems.SILVER_HORSE_ARMOR.get(), "Silver Horse Armor");
                helper.add(ModItems.POISON_APPLE.get(), "Poisonous Apple");
                helper.add(ModItems.SPECIAL_COAL.get(), "Special Coal");
                helper.add(ModItems.BLAZE_INGOT.get(), "Blaze Ingot");
                helper.add(ModItems.BLAZE_AND_STEEL.get(), "Blaze And Steel");
                helper.add(ModItems.PEANUT.get(), "Peanut");
                helper.add(ModItems.RUBBER_BUCKET.get(), "Rubber Bucket");
                helper.add(ModItems.OBSIDIAN_STICK.get(), "Obsidian Stick");
                helper.add(ModItems.ENDER_STICK.get(), "Ender Stick");
                helper.add(ModItems.TELEPORT_STAFF.get(), "Teleport Staff");
                helper.add(ModItems.TORCH_PLACER.get(), "Torch Placer");
                break;
            case "ko_kr":
                helper.add(ModItems.SILVER_INGOT.get(), "은괴");
                helper.add(ModItems.SILVER_NUGGET.get(), "은 조각");
                helper.add(ModItems.SILVER_DUST.get(), "은 가루");
                helper.add(ModItems.SILVER_SWORD.get(), "은 검");
                helper.add(ModItems.SILVER_SHOVEL.get(), "은 삽");
                helper.add(ModItems.SILVER_PICKAXE.get(), "은 곡괭이");
                helper.add(ModItems.SILVER_AXE.get(), "은 도끼");
                helper.add(ModItems.SILVER_HOE.get(), "은 괭이");
                helper.add(ModItems.SILVER_HELMET.get(), "은 투구");
                helper.add(ModItems.SILVER_CHESTPLATE.get(), "은 흉갑");
                helper.add(ModItems.SILVER_LEGGINGS.get(), "은 각반");
                helper.add(ModItems.SILVER_BOOTS.get(), "은 부츠");
                helper.add(ModItems.SILVER_HORSE_ARMOR.get(), "은 말 갑옷");
                helper.add(ModItems.POISON_APPLE.get(), "독사과");
                helper.add(ModItems.SPECIAL_COAL.get(), "특별한 석탄");
                helper.add(ModItems.BLAZE_INGOT.get(), "블레이즈 주괴");
                helper.add(ModItems.BLAZE_AND_STEEL.get(), "블레이즈 라이터");
                helper.add(ModItems.PEANUT.get(), "땅콩");
                helper.add(ModItems.RUBBER_BUCKET.get(), "고무 양동이");
                helper.add(ModItems.OBSIDIAN_STICK.get(), "흑요석 막대기");
                helper.add(ModItems.ENDER_STICK.get(), "엔드 막대기");
                helper.add(ModItems.TELEPORT_STAFF.get(), "순간이동 지팡이");
                helper.add(ModItems.TORCH_PLACER.get(), "토치 자동 설치 아이템");
                break;
        }
    }

    private void itemGroups(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.add(ModItemGroup.reasurecraft, "Reasure Craft");
                break;
            case "ko_kr":
                helper.add(ModItemGroup.reasurecraft, "래져 크래프트");
        }
    }

    private void containers(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.addContainer(ModBlocks.METAL_PRESS.get(), "Metal Press");
                helper.addContainer(ModBlocks.DISPLAY_CASE.get(), "Display Case");
                break;
            case "ko_kr":
                helper.addContainer(ModBlocks.METAL_PRESS.get(), "금속 유압기");
                helper.addContainer(ModBlocks.DISPLAY_CASE.get(), "전시장");
        }
    }

    private void tooltips(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.addTooltip("more_information", "Hold %1$s%2$s%3$s for more information!");
                helper.addTooltip("when_eat", "When eat: ");
                helper.addTooltip("when_in_inventory", "When in inventory: ");
                helper.addTooltip(ModItems.SPECIAL_COAL.get(),
                        "This is an infinite coal.\nIts burning time is only 1tick, but not consumed after used as a fuel.");
                helper.addTooltip(ModItems.BLAZE_AND_STEEL.get(), "It also light living entity!");
                helper.addTooltip(ModItems.TELEPORT_STAFF.get(), "Teleport where you're looking.");
                helper.addTooltip("food.none", "Not Food");
                helper.addTooltip(ModItems.TORCH_PLACER.get(), "If the brightness is below %s, place the torch in the inventory automatically.");
                helper.addTooltip("activate_item", "Sneaking and Right Click to Activate.");
                helper.addTooltip("deactivate_item", "Sneaking and Right Click to Deactivate");
                break;
            case "ko_kr":
                helper.addTooltip("more_information", "%1$s%2$s%3$s를 눌러 더 많은 정보 확인!");
                helper.addTooltip("when_eat", "먹을때: ");
                helper.addTooltip("when_in_inventory", "인벤토리에 있을 때: ");
                helper.addTooltip(ModItems.SPECIAL_COAL.get(),
                        "무한한 석탄\n타는 시간은 고작 1틱 뿐이지만, 연료로 사용되고 난 후에 사라지지 않음.");
                helper.addTooltip(ModItems.BLAZE_AND_STEEL.get(), "몹도 불을 붙임!");
                helper.addTooltip(ModItems.TELEPORT_STAFF.get(), "바라보는 곳으로 순간이동.");
                helper.addTooltip("food.none", "음식 아님");
                helper.addTooltip(ModItems.TORCH_PLACER.get(), "밝기가 %s 이하인 경우, 인벤토리의 횃불을 자동으로 설치합니다.");
                helper.addTooltip("activate_item", "웅크린 후 우클릭으로 활성화");
                helper.addTooltip("deactivate_item", "웅크린 후 우클릭으로 비활성화");
        }

        helper.addTooltip("potion.with_percent", "%s [%s%%]");
    }

    private void jeiCategories(PrefixHelper helper, String locale) {
        switch (locale) {
            case "en_us":
                helper.addCategory("tossing", "Item Tossing Recipe");
                break;
            case "ko_kr":
                helper.addCategory("tossing", "아이템 던지기");
        }
    }

    @Override
    protected void addTranslations() {
        PrefixHelper helper = new PrefixHelper(this);
        blocks(helper, locale);
        items(helper, locale);
        itemGroups(helper, locale);
        containers(helper, locale);
        tooltips(helper, locale);
        jeiCategories(helper, locale);
    }

    @SuppressWarnings("unused")
    public String getLocale() {
        return locale;
    }

    private static class PrefixHelper {
        private final ModLanguageProvider provider;
        private String prefix;

        public PrefixHelper(ModLanguageProvider provider) {
            this.provider = provider;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix + ".";
        }

        @SuppressWarnings("unused")
        public void add(String translationKey, String translation) {
            provider.add(prefix + translationKey, translation);
        }

        public void add(Block key, String name) {
            provider.add(key, name);
        }

        public void add(Item key, String name) {
            provider.add(key, name);
        }

        public void add(ModItemGroup key, String name) {
            provider.add("itemGroup." + key.getLabel(), name);
        }

        public void addContainer(Block key, String name) {
            setPrefix("container.reasurecraft");
            provider.add(prefix + Objects.requireNonNull(key.getRegistryName()).getPath(), name);
        }

        public void addTooltip(String key, String name) {
            provider.add(TranslateHelper.getTooltipPrefix(key), name);
        }

        public void addTooltip(Item key, String name) {
            provider.add(TranslateHelper.getTooltipPrefix(key), name);
        }

        public void addCategory(String key, String name) {
            setPrefix("categoty." + ReasureCraft.MOD_ID);
            provider.add(prefix + key, name);
        }
    }
}
