package com.github.bakuplayz.craftinggui.utils;

import com.github.bakuplayz.craftinggui.items.RecipeItem;
import com.github.bakuplayz.craftinggui.recipe.IngredientsChoice;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class NameUtil {

    public enum ReadableNames {
        AIR(0),
        STONE(1),
        GRANITE(1, 1),
        POLISHED_GRANITE(1, 2),
        DIORITE(1, 3),
        POLISHED_DIORITE(1, 4),
        ANDESITE(1, 5),
        ANDESITE_DIORITE(1, 6),
        GRASS_BLOCK(2),
        DIRT(3),
        COARSE_DIRT(3, 1),
        PODZOL(3, 2),
        COBBLESTONE(4),
        OAK_PLANKS(5),
        SPRUCE_PLANKS(5, 1),
        BIRCH_PLANKS(5, 2),
        JUNGLE_PLANKS(5, 3),
        ACACIA_PLANKS(5, 4),
        DARK_OAK_PLANKS(5, 5),
        OAK_SAPLING(6),
        SPRUCE_SAPLING(6, 1),
        BIRCH_SAPLING(6, 2),
        JUNGLE_SAPLING(6, 3),
        ACACIA_SAPLING(6, 4),
        DARK_OAK_SAPLING(6, 5),
        BEDROCK(7),
        WATER(9),
        LAVA_NO_SPREAD(10),
        LAVA(11),
        SAND(12),
        RED_SAND(12, 1),
        GRAVEL(13),
        GOLD_ORE(14),
        IRON_ORE(15),
        COAL_ORE(16),
        OAK_WOOD(17),
        SPRUCE_WOOD(17, 1),
        BIRCH_WOOD(17, 2),
        JUNGLE_WOOD(17, 3),
        OAK_LEAVES(18),
        SPRUCE_LEAVES(18, 1),
        BIRCH_LEAVES(18, 2),
        JUNGLE_LEAVES(18, 3),
        SPONGE(19),
        WET_SPONGE(19, 1),
        GLASS(20),
        LAPIS_LAZULI_ORE(21),
        LAPIS_LAZULI_BLOCK(22),
        DISPENSER(23),
        SANDSTONE(24),
        CHISELED_SANDSTONE(24, 1),
        SMOOTH_SANDSTONE(24, 2),
        NOTE_BLOCK(25),
        BED(26),
        POWERED_RAIL(27),
        DETECTOR_RAIL(28),
        STICKY_PISTON(29),
        WEB(30),
        SHRUB(31),
        GRASS(31, 1),
        FERN(31, 2),
        DEAD_BUSH(32),
        PISTON(33),
        PISTON_HEAD(34),
        WHITE_WOOL(35),
        ORANGE_WOOL(35, 1),
        MAGENTA_WOOL(35, 2),
        LIGHT_BLUE_WOOL(35, 3),
        YELLOW_WOOL(35, 4),
        LIME_WOOL(35, 5),
        PINK_WOOL(35, 6),
        GRAY_WOOL(35, 7),
        LIGHT_GRAY_WOOL(35, 8),
        CYAN_WOOL(35, 9),
        PURPLE_WOOL(35, 10),
        BLUE_WOOL(35, 11),
        BROWN_WOOL(35, 12),
        GREEN_WOOL(35, 13),
        RED_WOOL(35, 14),
        BLACK_WOOL(35, 15),
        DANDELION(37),
        POPPY(38),
        BLUE_ORCHID(38, 1),
        ALLIUM(38, 2),
        AZURE_BLUET(38, 3),
        RED_TULIP(38, 4),
        ORANGE_TULIP(38, 5),
        WHITE_TULIP(38, 6),
        PINK_TULIP(38, 7),
        OXEYE_DAISY(38, 8),
        BROWN_MUSHROOM(39),
        RED_MUSHROOM(40),
        GOLD_BLOCK(41),
        IRON_BLOCK(42),
        STONE_SLAB_DOUBLE(43),
        SANDSTONE_SLAB_DOUBLE(43, 1),
        WOODEN_SLAB_DOUBLE(43, 2),
        COBBLESTONE_SLAB_DOUBLE(43, 3),
        BRICK_SLAB_DOUBLE(43, 4),
        STONE_BRICK_SLAB_DOUBLE(43, 5),
        NETHER_BRICK_SLAB_DOUBLE(43, 6),
        QUARTZ_SLAB_DOUBLE(43, 7),
        SMOOTH_STONE_SLAB_DOUBLE(43, 8),
        SMOOTH_SANDSTONE_SLAB_DOUBLE(43, 9),
        STONE_SLAB(44),
        SANDSTONE_SLAB(44, 1),
        WOODEN_SLAB(44, 2),
        COBBLESTONE_SLAB(44, 3),
        BRICK_SLAB(44, 4),
        STONE_BRICK_SLAB(44, 5),
        NETHER_BRICK_SLAB(44, 6),
        QUARTZ_SLAB(44, 7),
        BRICKS(45),
        TNT(46),
        BOOKSHELF(47),
        MOSS_STONE(48),
        OBSIDIAN(49),
        TORCH(50),
        FIRE(51),
        MOB_SPAWNER(52),
        OAK_WOOD_STAIRS(53),
        CHEST(54),
        REDSTONE_WIRE(55),
        DIAMOND_ORE(56),
        BLOCK_OF_DIAMOND(57),
        CRAFTING_TABLE(58),
        WHEAT_CROP(59),
        FARMLAND(60),
        FURNACE(61),
        FURNACE_SMELTING(62),
        SIGN_BLOCK(63),
        WOOD_DOOR_BLOCK(64),
        LADDER(65),
        RAILS(66),
        STONE_STAIRS(67),
        SIGN_WALL_BLOCK(68),
        LEVER(69),
        STONE_PRESSURE_PLATE(70),
        IRON_DOOR_BLOCK(71),
        WOOD_PRESSURE_PLATE(72),
        REDSTONE_ORE(73),
        REDSTONE_ORE_GLOWING(74),
        REDSTONE_TORCH_OFF(75),
        REDSTONE_TORCH(76),
        BUTTON(77),
        SNOW(78),
        ICE(79),
        SNOW_BLOCK(80),
        CACTUS(81),
        CLAY_BLOCK(82),
        SUGAR_CANE_BLOCK(83),
        JUKEBOX(84),
        OAK_FENCE(85),
        PUMPKIN(86),
        NETHERRACK(87),
        SOUL_SAND(88),
        GLOWSTONE(89),
        PORTAL(90),
        JACK_O_LANTERN(91, "Jack o'Lantern"),
        CAKE_BLOCK(92),
        REDSTONE_REPEATER_BLOCK_OFF(93),
        REDSTONE_REPEATER_BLOCK_ON(94),
        WHITE_STAINED_GLASS(95),
        ORANGE_STAINED_GLASS(95, 1),
        MAGENTA_STAINED_GLASS(95, 2),
        LIGHT_BLUE_STAINED_GLASS(95, 3),
        YELLOW_STAINED_GLASS(95, 4),
        LIME_STAINED_GLASS(95, 5),
        PINK_STAINED_GLASS(95, 6),
        GRAY_STAINED_GLASS(95, 7),
        LIGHT_GRAY_STAINED_GLASS(95, 8),
        CYAN_STAINED_GLASS(95, 9),
        PURPLE_STAINED_GLASS(95, 10),
        BLUE_STAINED_GLASS(95, 11),
        BROWN_STAINED_GLASS(95, 12),
        GREEN_STAINED_GLASS(95, 13),
        RED_STAINED_GLASS(95, 14),
        BLACK_STAINED_GLASS(95, 15),
        WOODEN_TRAPDOOR(96),
        STONE_MONSTER_EGG(97),
        COBBLESTONE_MONSTER_EGG(97, 1),
        STONE_BRICK_MONSTER_EGG(97, 2),
        MOSSY_STONE_BRICK_MONSTER_EGG(97, 3),
        CRACKED_STONE_BRICK_MONSTER_EGG(97, 4),
        CHISELED_STONE_BRICK_MONSTER_EGG(97, 5),
        STONE_BRICKS(98),
        MOSSY_STONE_BRICKS(98, 1),
        CRACKED_STONE_BRICKS(98, 2),
        CHISELED_STONE_BRICKS(98, 3),
        IRON_BARS(101),
        GLASS_PANE(102),
        PUMPKIN_VINE(104),
        MELON_VINE(105),
        VINES(106),
        OAK_FENCE_GATE(107),
        BRICK_STAIRS(108),
        STONE_BRICK_STAIRS(109),
        MYCELIUM(110),
        LILY_PAD(111),
        NETHER_BRICK(112),
        NETHER_BRICK_FENCE(113),
        NETHER_BRICK_STAIRS(114),
        ENCHANTMENT_TABLE(116),
        END_PORTAL(119),
        END_PORTAL_FRAME(120),
        END_STONE(121),
        DRAGON_EGG(122),
        REDSTONE_LAMP(123),
        DOUBLE_WOOD_SLAB(125),
        OAK_WOOD_SLAB(126),
        SPRUCE_WOOD_SLAB(126, 1),
        BIRCH_SLAB(126, 2),
        JUNGLE_SLAB(126, 3),
        ACACIA_WOOD_SLAB(126, 4),
        DARK_OAK_WOOD_SLAB(126, 5),
        COCOA_PLANT(127),
        SANDSTONE_STAIRS(128),
        EMERALD_ORE(129),
        ENDER_CHEST(130),
        TRIPWIRE_HOOK(131),
        TRIPWIRE(132),
        BLOCK_OF_EMERALD(133),
        SPRUCE_WOOD_STAIRS(134),
        BIRCH_WOOD_STAIRS(135),
        JUNGLE_WOOD_STAIRS(136),
        COMMAND_BLOCK(137),
        BEACON(138),
        COBBLESTONE_WALL(139),
        MOSSY_COBBLESTONE_WALL(139, 1),
        CARROTS(141),
        POTATOES(142),
        WOODEN_BUTTON(143),
        ANVIL(145),
        SLIGHTLY_DAMAGED_ANVIL(145, 1),
        VERY_DAMAGED_ANVIL(145, 2),
        TRAPPED_CHEST(146),
        LIGHT_WEIGHTED_PRESSURE_PLATE(147),
        HEAVY_WEIGHTED_PRESSURE_PLATE(148),
        REDSTONE_COMPARATOR_INACTIVE(149),
        REDSTONE_COMPARATOR_ACTIVE(150),
        DAYLIGHT_SENSOR(151),
        BLOCK_OF_REDSTONE(152),
        NETHER_QUARTZ_ORE(153),
        HOPPER(154),
        BLOCK_OF_QUARTZ(155),
        CHISELED_QUARTZ_BLOCK(155, 1),
        PILLAR_QUARTZ_BLOCK(155, 2),
        QUARTZ_STAIRS(156),
        ACTIVATOR_RAIL(157),
        DROPPER(158),
        TERRACOTTA(159),
        WHITE_STAINED_GLASS_PANE(160),
        ORANGE_STAINED_GLASS_PANE(160, 1),
        MAGENTA_STAINED_GLASS_PANE(160, 2),
        LIGHT_BLUE_STAINED_GLASS_PANE(160, 3),
        YELLOW_STAINED_GLASS_PANE(160, 4),
        LIME_STAINED_GLASS_PANE(160, 5),
        PINK_STAINED_GLASS_PANE(160, 6),
        GRAY_STAINED_GLASS_PANE(160, 7),
        LIGHT_GRAY_STAINED_GLASS_PANE(160, 8),
        CYAN_STAINED_GLASS_PANE(160, 9),
        PURPLE_STAINED_GLASS_PANE(160, 10),
        BLUE_STAINED_GLASS_PANE(160, 11),
        BROWN_STAINED_GLASS_PANE(160, 12),
        GREEN_STAINED_GLASS_PANE(160, 13),
        RED_STAINED_GLASS_PANE(160, 14),
        BLACK_STAINED_GLASS_PANE(160, 15),
        ACACIA_LEAVES(161),
        DARK_OAK_LEAVES(161, 1),
        ACACIA_WOOD(162),
        DARK_OAK_WOOD(162, 1),
        ACACIA_WOOD_STAIRS(163),
        DARK_OAK_WOOD_STAIRS(164),
        SLIME_BLOCK(165),
        BARRIER(166),
        IRON_TRAPDOOR(167),
        PRISMARINE(168),
        PRISMARINE_BRICKS(168, 1),
        DARK_PRISMARINE(168, 2),
        SEA_LANTERN(169),
        HAY_BLOCK(170),
        WHITE_CARPET(171),
        ORANGE_CARPET(171, 1),
        MAGENTA_CARPET(171, 2),
        LIGHT_BLUE_CARPET(171, 3),
        YELLOW_CARPET(171, 4),
        LIME_CARPET(171, 5),
        PINK_CARPET(171, 6),
        GRAY_CARPET(171, 7),
        LIGHT_GRAY_CARPET(171, 8),
        CYAN_CARPET(171, 9),
        PURPLE_CARPET(171, 10),
        BLUE_CARPET(171, 11),
        BROWN_CARPET(171, 12),
        GREEN_CARPET(171, 13),
        RED_CARPET(171, 14),
        BLACK_CARPET(171, 15),
        HARDENED_CLAY(172),
        BLOCK_OF_COAL(173),
        PACKED_ICE(174),
        SUNFLOWER(175),
        LILAC(175, 1),
        DOUBLE_TALLGRASS(175, 2),
        LARGE_FERN(175, 3),
        ROSE_BUSH(175, 4),
        PEONY(175, 5),
        RED_SANDSTONE(179),
        CHISELED_RED_SANDSTONE(179, 1),
        SMOOTH_RED_SANDSTONE(179, 2),
        RED_SANDSTONE_STAIRS(180),
        RED_STANDSTONE_SLAB(182),
        SPRUCE_FENCE_GATE(183),
        BIRCH_FENCE_GATE(184),
        JUNGLE_FENCE_GATE(185),
        DARK_OAK_FENCE_GATE(186),
        ACACIA_FENCE_GATE(187),
        SPRUCE_FENCE(188),
        BIRCH_FENCE(189),
        JUNGLE_FENCE(190),
        DARK_OAK_FENCE(191),
        ACACIA_FENCE(192),
        END_ROD(198),
        CHORUS_PLANT(199),
        CHORUS_FLOWER(200),
        PURPUR_BLOCK(201),
        PURPUR_PILLAR(202),
        PURPUR_STAIRS(203),
        PURPUR_SLAB(205),
        END_STONE_BRICKS(206),
        GRASS_PATH(208),
        REAPEATING_COMMAND_BLOCK(210),
        MAGMA_BLOCK(213),
        NETHER_WART_BLOCK(214),
        RED_NETHER_BRICK(215),
        BONE_BLOCK(216),
        STRUCTURE_VOID(217),
        OBSERVER(218),
        WHITE_SHULKER_BOX(219),
        ORANGE_SHULKER_BOX(220),
        MAGENTA_SHULKER_BOX(221),
        LIGHT_BLUE_SHULKER_BOX(222),
        YELLOW_SHULKER_BOX(223),
        LIME_SHULKER_BOX(224),
        PINK_SHULKER_BOX(225),
        GRAY_SHULKER_BOX(226),
        LIGHT_GRAY_SHULKER_BOX(227),
        CYAN_SHULKER_BOX(228),
        PURPLE_SHULKER_BOX(229),
        BLUE_SHULKER_BOX(230),
        BROWN_SHULKER_BOX(231),
        GREEN_SHULKER_BOX(232),
        RED_SHULKER_BOX(233),
        BLACK_SHULKER_BOX(234),
        WHITE_GLAZED_TERRACOTTA(235),
        ORANGE_GLAZED_TERRACOTTA(236),
        MAGENTA_GLAZED_TERRACOTTA(237),
        LIGHT_BLUE_GLAZED_TERRACOTTA(238),
        YELLOW_GLAZED_TERRACOTTA(239),
        LIME_GLAZED_TERRACOTTA(240),
        PINK_GLAZED_TERRACOTTA(241),
        GRAY_GLAZED_TERRACOTTA(242),
        LIGHT_GRAY_GLAZED_TERRACOTTA(243),
        CYAN_GLAZED_TERRACOTTA(244),
        PURPLE_GLAZED_TERRACOTTA(245),
        BLUE_GLAZED_TERRACOTTA(246),
        BROWN_GLAZED_TERRACOTTA(247),
        GREEN_GLAZED_TERRACOTTA(248),
        RED_GLAZED_TERRACOTTA(249),
        BLACK_GLAZED_TERRACOTTA(250),
        WHITE_CONCRETE(251),
        ORANGE_CONCRETE(251, 1),
        MAGENTA_CONCRETE(251, 2),
        LIGHT_BLUE_CONCRETE(251, 3),
        YELLOW_CONCRETE(251, 4),
        LIME_CONCRETE(251, 5),
        PINK_CONCRETE(251, 6),
        GRAY_CONCRETE(251, 7),
        LIGHT_GRAY_CONCRETE(251, 8),
        CYAN_CONCRETE(251, 9),
        PURPLE_CONCRETE(251, 10),
        BLUE_CONCRETE(251, 11),
        BROWN_CONCRETE(251, 12),
        GREEN_CONCRETE(251, 13),
        RED_CONCRETE(251, 14),
        BLACK_CONCRETE(251, 15),
        WHITE_CONCRETE_POWDER(252),
        ORANGE_CONCRETE_POWDER(252, 1),
        MAGENTA_CONCRETE_POWDER(252, 2),
        LIGHT_BLUE_CONCRETE_POWDER(252, 3),
        YELLOW_CONCRETE_POWDER(252, 4),
        LIME_CONCRETE_POWDER(252, 5),
        PINK_CONCRETE_POWDER(252, 6),
        GRAY_CONCRETE_POWDER(252, 7),
        LIGHT_GRAY_CONCRETE_POWDER(252, 8),
        CYAN_CONCRETE_POWDER(252, 9),
        PURPLE_CONCRETE_POWDER(252, 10),
        BLUE_CONCRETE_POWDER(252, 11),
        BROWN_CONCRETE_POWDER(252, 12),
        GREEN_CONCRETE_POWDER(252, 13),
        RED_CONCRETE_POWDER(252, 14),
        BLACK_CONCRETE_POWDER(252, 15),
        STRUCTURE_BLOCK(255),
        IRON_SHOVEL(256),
        IRON_PICKAXE(257),
        IRON_AXE(258),
        FLINT_AND_STEEL(259),
        APPLE(260),
        BOW(261),
        ARROW(262),
        COAL(263),
        CHARCOAL(263, 1),
        DIAMOND(264),
        IRON_INGOT(265),
        GOLD_INGOT(266),
        IRON_SWORD(267),
        WOODEN_SWORD(268),
        WOODEN_SHOVEL(269),
        WOODEN_PICKAXE(270),
        WOODEN_AXE(271),
        STONE_SWORD(272),
        STONE_SHOVEL(273),
        STONE_PICKAXE(274),
        STONE_AXE(275),
        DIAMOND_SWORD(276),
        DIAMOND_SHOVEL(277),
        DIAMOND_PICKAXE(278),
        DIAMOND_AXE(279),
        STICK(280),
        BOWL(281),
        MUSHROOM_STEW(282),
        GOLD_SWORD(283),
        GOLD_SHOVEL(284),
        GOLD_PICKAXE(285),
        GOLD_AXE(286),
        STRING(287),
        FEATHER(288),
        GUNPOWDER(289),
        WOODEN_HOE(290),
        STONE_HOE(291),
        IRON_HOE(292),
        DIAMOND_HOE(293),
        GOLD_HOE(294),
        SEEDS(295),
        WHEAT(296),
        BREAD(297),
        LEATHER_HELMET(298),
        LEATHER_CHESTPLATE(299),
        LEATHER_LEGGINGS(300),
        LEATHER_BOOTS(301),
        CHAINMAIL_HELMET(302),
        CHAINMAIL_CHESTPLATE(303),
        CHAINMAIL_LEGGINGS(304),
        CHAINMAIL_BOOTS(305),
        IRON_HELMET(306),
        IRON_CHESTPLATE(307),
        IRON_LEGGINGS(308),
        IRON_BOOTS(309),
        DIAMOND_HELMET(310),
        DIAMOND_CHESTPLATE(311),
        DIAMOND_LEGGINGS(312),
        DIAMOND_BOOTS(313),
        GOLD_HELMET(314),
        GOLD_CHESTPLATE(315),
        GOLD_LEGGINGS(316),
        GOLD_BOOTS(317),
        FLINT(318),
        RAW_PORKCHOP(319),
        COOKED_PORKCHOP(320),
        PAINTING(321),
        GOLDEN_APPLE(322),
        ENCHANTED_GOLDEN_APPLE(322, 1),
        SIGN(323),
        OAK_DOOR(324),
        BUCKET(325),
        WATER_BUCKET(326),
        LAVA_BUCKET(327),
        MINECART(328),
        SADDLE(329),
        IRON_DOOR(330),
        REDSTONE(331),
        SNOWBALL(332),
        OAK_BOAT(333),
        LEATHER(334),
        MILK(335),
        BRICK(336),
        CLAY(337),
        SUGAR_CANE(338),
        PAPER(339),
        BOOK(340),
        SLIME_BALL(341),
        MINECART_WITH_CHEST(342),
        MINECART_WITH_FURNACE(343),
        EGG(344),
        COMPASS(345),
        FISHING_ROD(346),
        CLOCK(347),
        GLOWSTONE_DUST(348),
        RAW_FISH(349),
        RAW_SALMON(349, 1),
        CLOWNFISH(349, 2),
        PUFFERFISH(349, 3),
        COOKED_FISH(350),
        COOKED_SALMON(350, 1),
        INK_SAC(351),
        RED_DYE(351, 1),
        CACTUS_GREEN(351, 2),
        COCOA_BEAN(351, 3),
        LAPIS_LAZULI(351, 4),
        PURPLE_DYE(351, 5),
        CYAN_DYE(351, 6),
        LIGHT_GRAY_DYE(351, 7),
        GRAY_DYE(351, 8),
        PINK_DYE(351, 9),
        LIME_DYE(351, 10),
        YELLOW_DYE(351, 11),
        LIGHT_BLUE_DYE(351, 12),
        MAGENTA_DYE(351, 13),
        ORANGE_DYE(351, 14),
        BONE_MEAL(351, 15),
        BONE(352),
        SUGAR(353),
        CAKE(354),
        WHITE_BED(355),
        ORANGE_BED(355, 1),
        MAGENTA_BED(355, 2),
        LIGHT_BLUE_BED(355, 3),
        YELLOW_BED(355, 4),
        LIME_BED(355, 5),
        PINK_BED(355, 6),
        GRAY_BED(355, 7),
        LIGHT_GRAY_BED(355, 8),
        CYAN_BED(355, 9),
        PURPLE_BED(355, 10),
        BLUE_BED(355, 11),
        BROWN_BED(355, 12),
        GREEN_BED(355, 13),
        RED_BED(355, 14),
        BLACK_BED(355, 15),
        REDSTONE_REPEATER(356),
        COOKIE(357),
        MAP(358),
        SHEARS(359),
        MELON(360),
        PUMPKIN_SEEDS(361),
        MELON_SEEDS(362),
        RAW_BEEF(363),
        STEAK(364),
        RAW_CHICKEN(365),
        ROAST_CHICKEN(366),
        ROTTEN_FLESH(367),
        ENDER_PEARL(368),
        BLAZE_ROD(369),
        GHAST_TEAR(370),
        GOLD_NUGGET(371),
        NETHER_WART(372),
        WATER_BOTTLE(373),
        GLASS_BOTTLE(374),
        SPIDER_EYE(375),
        FERMENTED_SPIDER_EYE(376),
        BLAZE_POWDER(377),
        MAGMA_CREAM(378),
        BREWING_STAND(379),
        CAULDRON(380),
        EYE_OF_ENDER(381),
        GLISTERING_MELON(382),
        SPAWN_EGG(383),
        SPAWN_CREEPER(383, 50),
        SPAWN_SKELETON(383, 51),
        SPAWN_SPIDER(383, 52),
        SPAWN_ZOMBIE(383, 54),
        SPAWN_SLIME(383, 55),
        SPAWN_GHAST(383, 56),
        SPAWN_PIGMAN(383, 57),
        SPAWN_ENDERMAN(383, 58),
        SPAWN_CAVE_SPIDER(383, 59),
        SPAWN_SILVERFISH(383, 60),
        SPAWN_BLAZE(383, 61),
        SPAWN_MAGMA_CUBE(383, 62),
        SPAWN_BAT(383, 65),
        SPAWN_WITCH(383, 66),
        SPAWN_ENDERMITE(383, 67),
        SPAWN_GUARDIAN(383, 68),
        SPAWN_PIG(383, 90),
        SPAWN_SHEEP(383, 91),
        SPAWN_COW(383, 92),
        SPAWN_CHICKEN(383, 93),
        SPAWN_SQUID(383, 94),
        SPAWN_WOLF(383, 95),
        SPAWN_MOOSHROOM(383, 96),
        SPAWN_OCELOT(383, 98),
        SPAWN_HORSE(383, 100),
        SPAWN_RABBIT(383, 101),
        SPAWN_VILLAGER(383, 120),
        BOTTLE_O_ENCHANTING(384, "Bottle o' Enchanting"),
        FIRE_CHARGE(385),
        BOOK_AND_QUILL(386),
        WRITTEN_BOOK(387),
        EMERALD(388),
        ITEM_FRAME(389),
        FLOWER_POT(390),
        CARROT(391),
        POTATO(392),
        BAKED_POTATO(393),
        POISONOUS_POTATO(394),
        EMPTY_MAP(395),
        GOLDEN_CARROT(396),
        SKULL_ITEM(397),
        SKELETON_SKULL(397, 0),
        WITHER_SKELETON_SKULL(397, 1),
        ZOMBIE_HEAD(397, 2),
        HEAD(397, 3),
        CREEPER_HEAD(397, 4),
        DRAGON_HEAD(397, 5),
        CARROT_ON_A_STICK(398),
        NETHER_STAR(399),
        PUMPKIN_PIE(400),
        FIREWORK_ROCKET(401),
        FIREWORK_STAR(402),
        ENCHANTED_BOOK(403),
        REDSTONE_COMPARATOR(404),
        NETHER_QUARTZ(406),
        MINECART_WITH_TNT(407),
        MINECART_WITH_HOPPER(408),
        PRISMARINE_SHARD(409),
        PRISMARINE_CRYSTALS(410),
        RAW_RABBIT(411),
        COOKED_RABBIT(412),
        RABBIT_STEW(413),
        RABBIT_FOOT(414, "Rabbit's Foot"),
        RABBIT_HIDE(415),
        ARMOR_STAND(416),
        IRON_HORSE_ARMOR(417),
        GOLD_HORSE_ARMOR(418),
        DIAMOND_HORSE_ARMOR(419),
        LEAD(420),
        NAME_TAG(421),
        MINECART_WITH_COMMAND_BLOCK(422),
        RAW_MUTTON(423),
        COOKED_MUTTON(424),
        BLACK_BANNER(425),
        RED_BANNER(425, 1),
        GREEN_BANNER(425, 2),
        BROWN_BANNER(425, 3),
        BLUE_BANNER(425, 4),
        PURPLE_BANNER(425, 5),
        CYAN_BANNER(425, 6),
        LIGHT_GRAY_BANNER(425, 7),
        GRAY_BANNER(425, 8),
        PINK_BANNER(425, 9),
        LIME_BANNER(425, 10),
        YELLOW_BANNER(425, 11),
        LIGHT_BLUE_BANNER(425, 12),
        MAGENTA_BANNER(425, 13),
        ORANGE_BANNER(425, 14),
        WHITE_BANNER(425, 15),
        END_CRYSTAL(426),
        SPRUCE_DOOR(427),
        BIRCH_DOOR(428),
        JUNGLE_DOOR(429),
        ACACIA_DOOR(430),
        DARK_OAK_DOOR(431),
        CHORUS_FRUIT(432),
        POPPED_CHORUS_FRUIT(433),
        BEETROOT(434),
        BEETROOT_SEEDS(435),
        BEETROOT_SOUP(436),
        DRAGONS_BREATH(437, "Dragon's Breath"),
        SPECTRAL_ARROW(439),
        SHIELD(442),
        ELYTRA(443),
        SPRUCE_BOAT(444),
        BIRCH_BOAT(445),
        JUNGLE_BOAT(446),
        ACACIA_BOAT(447),
        DARK_OAK_BOAT(448),
        TOTEM_OF_UNDYING(449),
        SHULKER_SHELL(450),
        IRON_NUGGET(452),
        MUSIC_DISK_13(2256),
        MUSIC_DISK_CAT(2257),
        MUSIC_DISK_BLOCKS(2258),
        MUSIC_DISK_CHIRP(2259),
        MUSIC_DISK_FAR(2260),
        MUSIC_DISK_MALL(2261),
        MUSIC_DISK_MELLOHI(2262),
        MUSIC_DISK_STAL(2263),
        MUSIC_DISK_STRAD(2264),
        MUSIC_DISK_WARD(2265),
        MUSIC_DISK_11(2266),
        MUSIC_DISK_WAIT(2267);

        private final int id;
        private final short type;

        private String alternativeName;

        ReadableNames(final int id) {
            this.id = id;
            this.type = 0;
        }

        ReadableNames(final int id, final String alternativeName) {
            this(id);
            this.alternativeName = alternativeName;
        }

        ReadableNames(final int id, final int type) {
            this.id = id;
            this.type = (short) type;
        }
    }

    private static final List<String> LEATHER_ARMOR = Arrays.asList("LEATHER_BOOTS", "LEATHER_LEGGINGS", "LEATHER_CHESTPLATE", "LEATHER_HELMET");

    public static @NotNull String lookup(final ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return "";
        }

        String name = item.getType().name();
        String readableName = getNameAsReadable(item, name);

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return readableName;
        if (meta.hasDisplayName()) return meta.getDisplayName();

        if (LEATHER_ARMOR.contains(name)) {
            Color color = ((LeatherArmorMeta) meta).getColor();
            DyeColor dyeColor = DyeColor.getByColor(color);
            return dyeColor == null ? readableName : capitalizeWords(dyeColor + " " + readableName);
        }

        return readableName;
    }

    public static @NotNull String lookup(final ItemStack item, final RecipeItem recipeItem) {
        if (item == null || recipeItem == null) {
            return "";
        }

        if (recipeItem.hasChoice()) {
            IngredientsChoice choice = recipeItem.getChoice();
            if (choice == null) return lookup(item);
            if (choice.matchesFirst(item)) return getNameAsPlural(item);
            if (choice.matchesSecond(item)) return getNameAsPlural(item);
        }

        return lookup(item);
    }

    public static @NotNull String lookupWithAmount(final @NotNull ItemStack item) {
        return item.getAmount() + " x " + lookup(item);
    }

    public static @NotNull String lookupWithAmount(final @NotNull ItemStack item, final @NotNull RecipeItem recipeItem) {
        return item.getAmount() + " x " + lookup(item, recipeItem);
    }

    private static @NotNull String getNameAsReadable(final @NotNull ItemStack item, final String name) {
        for (ReadableNames readable : ReadableNames.values()) {
            if (readable.id == item.getTypeId() && readable.type == item.getDurability()) {
                if (VersionUtil.isInInterval(0.0, 11.9) && item.getType() == Material.BED) return "Bed";
                if (VersionUtil.isInInterval(0.0, 8.9) && item.getType() == Material.BOAT) return "Boat";
                return readable.alternativeName != null ? readable.alternativeName : capitalizeWords(readable.name());
            }
        }
        return capitalizeWords(name);
    }

    private static @NotNull String getNameAsPlural(final @NotNull ItemStack item) {
        if (item.getType() == Material.COAL) return "Coal";
        if (item.getType() == Material.WOOL) return "Wool";
        if (item.getType() == Material.WOOD) return "Planks";
        if (item.getType() == Material.WOOD_STEP) return "Wood Slabs";
        return lookup(item);
    }

    private static @NotNull String capitalizeWords(final @NotNull String str) {
        return WordUtils.capitalizeFully(str.replace("_", " "));
    }
}
