package com.github.bakuplayz.craftinggui.api;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class LanguageAPI {

    public enum Console {
        LOAD_RECIPES("Loading recipes..."),
        LOADED_RECIPES("Recipes have loaded."),
        RELOAD_RECIPES("Reloading recipes..."),
        ERROR_RECIPES("Recipes did not load properly, please try reloading the server and report the issue."),

        FAILED_COMMAND_REGISTERING("Commands failed to register, please reload the server."),

        FILE_RELOAD("Reloading &e%s&7."),
        FILE_SETUP_LOAD("Loading &e%s&7."),
        FILE_SAVE_FAILED("Could not save &e%s&7."),
        FILE_SETUP_FAILED("Could not setup &e%s&7."),

        OLD_SETTINGS_REMOVED("Old settings was &aSuccessfully &7removed."),
        OLD_SETTINGS_FAILED("Old settings &cFailed &7to remove."),
        OLD_SETTINGS_SUCCESS("Old settings were &aSuccessfully &7deleted."),

        UPDATE_FETCH_FAILED("Update fetch failed! Make sure your online, to keep the plugin up to date."),
        NOT_SUPPORTED_VERSION("This CraftingGUI.jar only supports 1.8 to 1.12. In order to run the plugin, please change to the correct jar for your server version.");

        private final String message;

        Console(final String message) {
            this.message = message;
        }

        public void sendMessage() {
            Bukkit.getConsoleSender().sendMessage(getMessage());
        }

        public void sendMessage(final String replacement) {
            Bukkit.getConsoleSender().sendMessage(String.format(getMessage(), replacement));
        }

        @Contract(" -> new")
        private @NotNull String getMessage() {
            return ChatColor.translateAlternateColorCodes('&', "[&aCraftingGUI&f] &7" + message);
        }
    }

    public enum Command {
        HELP_DESCRIPTION("help", "description"),
        RESET_DESCRIPTION("reset", "description"),
        RELOAD_DESCRIPTION("reload", "description"),
        DEFAULT_DESCRIPTION("default", "description"),
        SETTINGS_DESCRIPTION("settings", "description"),
        RECIPES_DESCRIPTION("manage", "description"),

        RESET_DELETE("reset", "delete"),
        RESET_FAILED("reset", "failed"),
        RESET_SUCCESS("reset", "success"),

        RELOAD_SUCCESS("reload", "success"),

        PLAYER_ONLY_COMMAND("general", "playerOnlyCommand"),
        PLAYER_LACK_PERMISSION("general", "playerLackPermission", "%permission%");

        private final String key;
        private final String subCategory;

        private String oldValue = "";

        Command(final String subCategory, final String key) {
            this.key = key;
            this.subCategory = subCategory;
        }

        Command(final String subCategory, final String key, final String oldValue) {
            this(subCategory, key);
            this.oldValue = oldValue;
        }

        public @NotNull String getMessage(final @NotNull CraftingGUI plugin) {
            return plugin.getLanguageConfig().getMessage("command", subCategory, key);
        }

        public @NotNull String getMessage(final @NotNull CraftingGUI plugin, final @NotNull String newValue) {
            return getMessage(plugin).replace(oldValue, newValue);
        }

        public void sendMessage(final @NotNull CraftingGUI plugin, final @NotNull CommandSender sender) {
            sender.sendMessage(getMessage(plugin));
        }

        public void sendMessage(final @NotNull CraftingGUI plugin, final @NotNull CommandSender sender, final @NotNull String newValue) {
            sender.sendMessage(getMessage(plugin, newValue));
        }
    }

    public enum Menu {
        MULTI_CHOICE("general", "multiChoice"),
        TABLE_CHOICE("general", "tableChoice"),
        DEFAULT_CHOICE("general", "defaultChoice"),
        SELECTED_CHOICE("general", "selectedChoice"),

        TOGGLE_ENABLED("general", "toggleEnabled"),
        TOGGLE_DISABLED("general", "toggleDisabled"),

        NEXT_PAGE_ITEM_NAME("general", "nextPageItemName"),
        PREVIOUS_PAGE_ITEM_NAME("general", "previousPageItemName"),
        CURRENT_PAGE_ITEM_NAME("general", "currentPageItemName", "%page%"),

        BACK_ITEM_NAME("general", "backItemName"),
        MULTI_ITEM_NAME("general", "multiItemName"),
        TABLE_ITEM_NAME("general", "tableItemName"),
        SETTINGS_ITEM_NAME("general", "settingsItemName"),

        MULTI_CHOICE_ITEM_NAME("general", "multiChoiceItemName"),
        TABLE_CHOICE_ITEM_NAME("general", "tableChoiceItemName"),
        DEFAULT_CHOICE_ITEM_NAME("general", "defaultChoiceItemName"),
        TOGGLE_ITEM_NAME("general", "toggleItemName"),

        HELP_TITLE("title", "help"),
        SETTINGS_TITLE("title", "settings"),
        CRAFT_ITEM_TITLE("title", "craftItem"),
        TABLE_CHOICE_TITLE("title", "tableChoice"),
        MULTI_CHOICE_TITLE("title", "multiChoice"),
        COLOR_PICKER_TITLE("title", "colorPicker"),
        MANAGE_RECIPE_TITLE("title", "manageRecipe"),
        MANAGE_RECIPES_TITLE("title", "manageRecipes"),
        MULTI_CRAFTING_TITLE("title", "multiCrafting"),
        CRAFTING_TABLE_TITLE("title", "craftingTable"),
        ADMIN_SETTINGS_TITLE("title", "adminSettings"),
        UPDATE_SETTINGS_TITLE("title", "updateSettings"),

        HELP_ITEM_NAME("help", "itemName", "%name%"),
        HELP_ITEM_USAGE("help", "itemUsage", "%usage%"),
        HELP_ITEM_DESCRIPTION("help", "itemDescription", "%description%"),
        HELP_ITEM_PERMISSION("help", "itemPermission", "%permission%"),

        COLOR_PICKER_ITEM_NAME("settings", "colorPickerItemName"),

        ADMIN_SETTINGS_ITEM_NAME("settings", "adminSettingsItemName"),

        COLOR_PICKER_STATUS("settings", "colorPickerStatus", "%status%"),

        MULTI_CHOICE_STATUS("settings", "multiChoiceStatus", "%status%"),

        TABLE_CHOICE_STATUS("settings", "tableChoiceStatus", "%status%"),

        MANAGE_RECIPES_ITEM_NAME("adminSettings", "manageRecipesItemName"),

        RELOAD_RECIPES_ITEM_NAME("adminSettings", "reloadRecipesItemName"),

        UPDATE_SETTINGS_ITEM_NAME("adminSettings", "updateSettingsItemName"),

        MESSAGE_ENABLED("updateSettings", "messageEnabled"),

        MESSAGE_DISABLED("updateSettings", "messageDisabled"),

        PLAYER_MESSAGE_ITEM_NAME("updateSettings", "playerMessageItemName"),

        CONSOLE_MESSAGE_ITEM_NAME("updateSettings", "consoleMessageItemName"),

        PLAYER_MESSAGE_STATUS("updateSettings", "playerMessageStatus", "%status%"),

        CONSOLE_MESSAGE_STATUS("updateSettings", "consoleMessageStatus", "%status%"),

        NEW_UPDATE("updateSettings", "newUpdate"),

        NO_NEW_UPDATE("updateSettings", "noNewUpdate"),

        UPDATE_STATUS_ITEM_NAME("updateSettings", "updateStatusItemName"),

        UPDATE_STATUS("updateSettings", "updateStatus", "%status%"),

        COLOR_ITEM_NAME("colorPicker", "colorItemName", "%color%"),

        BAN_ITEM_NAME("manageRecipe", "banItemName"),

        RECIPE_BANNED("manageRecipe", "recipeBanned"),

        RECIPE_NOT_BANNED("manageRecipe", "recipeNotBanned"),

        RESULT_HEADER("menuItem", "resultHeader"),

        RESULT_NAME("menuItem", "resultName", "%name%"),

        INGREDIENTS_HEADER("menuItem", "ingredientHeader"),

        INGREDIENT_NAME("menuItem", "ingredientName", "%name%"),

        CAN_CRAFT_X("craftItem", "canCraftX", "%x%"),

        CAN_CRAFT_MAX("craftItem", "canCraftMax"),

        CANNOT_CRAFT_X("craftItem", "cannotCraftX", "%x%"),

        CANNOT_CRAFT_MAX("craftItem", "cannotCraftMax"),

        CRAFT_X_ITEM_NAME("craftItem", "craftXItemName", "%x%"),

        CRAFT_MAX_ITEM_NAME("craftItem", "craftMaxItemName"),

        GLASS_ITEM_NAME("craftingTable", "glassItemName"),

        RESULTS_ITEM_NAME("craftingTable", "resultsItemName");

        private final String key;
        private final String subCategory;

        private String oldValue = "";

        Menu(final String subCategory, final String key) {
            this.key = key;
            this.subCategory = subCategory;
        }

        Menu(final String subCategory, final String key, final String oldValue) {
            this(subCategory, key);
            this.oldValue = oldValue;
        }

        public @NotNull String getMessage(final @NotNull CraftingGUI plugin) {
            return plugin.getLanguageConfig().getMessage("menu", subCategory, key);
        }

        public @NotNull String getMessage(final @NotNull CraftingGUI plugin, final @NotNull Object newValue) {
            return getMessage(plugin).replace(oldValue, newValue.toString());
        }

        public @NotNull String getTitle(final @NotNull CraftingGUI plugin) {
            return "CraftingGUI: " + getMessage(plugin);
        }

    }
}
