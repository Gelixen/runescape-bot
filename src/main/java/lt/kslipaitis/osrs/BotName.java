package lt.kslipaitis.osrs;

import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.bot.SmithingBot;
import lt.kslipaitis.osrs.bot.baking.PieShellsBakingBot;
import lt.kslipaitis.osrs.bot.baking.PizzaBakingBot;
import lt.kslipaitis.osrs.bot.cooking.AlKharidCookingBot;
import lt.kslipaitis.osrs.bot.fishing.HarpoonBot;
import lt.kslipaitis.osrs.bot.killing.CowKillerBot;
import lt.kslipaitis.osrs.bot.killing.DemonKillerBot;
import lt.kslipaitis.osrs.bot.logging.DraynorOakLoggerBot;
import lt.kslipaitis.osrs.bot.logging.EdgevilleYewLoggerBot;
import lt.kslipaitis.osrs.bot.mining.GoldMiningBot;
import lt.kslipaitis.osrs.bot.mining.IronMiningBot;
import lt.kslipaitis.osrs.bot.mining.SilverMiningBot;
import lt.kslipaitis.osrs.bot.runecrafting.EdgevilleBodyRunecraftingBot;
import lt.kslipaitis.osrs.bot.runecrafting.FaladorAirRunecraftingBot;
import lt.kslipaitis.osrs.bot.smelting.*;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.AllUtils;

public enum BotName {
    COW {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new CowKillerBot(allUtils, allProcessors, allScreenshots);
        }
    }, IRON {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new IronSmeltingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    IRON_POWER_MINE {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new IronMiningBot(allUtils, allProcessors, allScreenshots);
        }
    },

    GOLD {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new GoldMiningBot(allUtils, allProcessors, allScreenshots);
        }
    },

    STEEL {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new SteelSmeltingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    MITHRIL {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new MithrilSmeltingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    SILVER {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new SilverSmeltingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    SILVER_MINING {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new SilverMiningBot(allUtils, allProcessors, allScreenshots);
        }
    },

    SYMBOL {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new SilverSmeltingAndCraftingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    SAPPHIRE {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new

                    JewelleryCraftingBot("Sapphire", allUtils, allProcessors, allScreenshots);
        }
    },

    EMERALD {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new JewelleryCraftingBot("Emerald", allUtils, allProcessors, allScreenshots);
        }
    },

    RUBY {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new JewelleryCraftingBot("Ruby", allUtils, allProcessors, allScreenshots);
        }
    },

    SMITHING {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new SmithingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    BODY {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new EdgevilleBodyRunecraftingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    AIR {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new FaladorAirRunecraftingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    PIZZA {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new PizzaBakingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    PIE_SHELL {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new PieShellsBakingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    DEMON {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new DemonKillerBot(allUtils, allProcessors, allScreenshots);
        }
    },

    COOK {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new AlKharidCookingBot(allUtils, allProcessors, allScreenshots);
        }
    },

    YEW {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new EdgevilleYewLoggerBot(allUtils, allProcessors, allScreenshots);
        }
    },

    OAK {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new DraynorOakLoggerBot(allUtils, allProcessors);
        }
    },

    HARPOON {
        @Override
        Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors) {
            return new HarpoonBot(allUtils, allProcessors, allScreenshots);
        }
    };

    abstract Bot construct(AllUtils allUtils, AllScreenshots allScreenshots, AllProcessors allProcessors);
}
