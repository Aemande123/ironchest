/*******************************************************************************
 * Copyright (c) 2012 cpw. All rights reserved. This program and the accompanying materials are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 * <p>
 * Contributors: cpw - initial API and implementation
 ******************************************************************************/
package cpw.mods.ironchest.common.items;

import static cpw.mods.ironchest.common.blocks.chest.IronChestType.COPPER;
import static cpw.mods.ironchest.common.blocks.chest.IronChestType.CRYSTAL;
import static cpw.mods.ironchest.common.blocks.chest.IronChestType.DIAMOND;
import static cpw.mods.ironchest.common.blocks.chest.IronChestType.GOLD;
import static cpw.mods.ironchest.common.blocks.chest.IronChestType.IRON;
import static cpw.mods.ironchest.common.blocks.chest.IronChestType.OBSIDIAN;
import static cpw.mods.ironchest.common.blocks.chest.IronChestType.SILVER;
import static cpw.mods.ironchest.common.blocks.chest.IronChestType.WOOD;

import cpw.mods.ironchest.common.blocks.chest.IronChestType;
import cpw.mods.ironchest.common.items.chest.ItemChestChanger;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum ChestChangerType
{
    //@formatter:off
    IRON_GOLD(IRON, GOLD, "iron_gold_chest_upgrade", "mmm", "msm", "mmm"),
    GOLD_DIAMOND(GOLD, DIAMOND, "gold_diamond_chest_upgrade", "GGG", "msm", "GGG"),
    COPPER_SILVER(COPPER, SILVER, "copper_silver_chest_upgrade", "mmm", "msm", "mmm"),
    SILVER_GOLD(SILVER, GOLD, "silver_gold_chest_upgrade", "mGm", "GsG", "mGm"),
    COPPER_IRON(COPPER, IRON, "copper_iron_chest_upgrade", "mGm", "GsG", "mGm"),
    DIAMOND_CRYSTAL(DIAMOND, CRYSTAL, "diamond_crystal_chest_upgrade", "GGG", "GOG", "GGG"),
    WOOD_IRON(WOOD, IRON, "wood_iron_chest_upgrade", "mmm", "msm", "mmm"),
    WOOD_COPPER(WOOD, COPPER, "wood_copper_chest_upgrade", "mmm", "msm", "mmm"),
    DIAMOND_OBSIDIAN(DIAMOND, OBSIDIAN, "diamond_obsidian_chest_upgrade", "mmm", "mGm", "mmm");
    //@formatter:on

    public static final ChestChangerType[] VALUES = values();

    public final IronChestType source;
    public final IronChestType target;
    public final String itemName;
    public ItemChestChanger item;
    private String[] recipe;

    ChestChangerType(IronChestType source, IronChestType target, String itemName, String... recipe)
    {
        this.source = source;
        this.target = target;
        this.itemName = itemName;
        this.recipe = recipe;
    }

    public boolean canUpgrade(IronChestType from)
    {
        return from == this.source;
    }

    public ItemChestChanger buildItem()
    {
        this.item = new ItemChestChanger(this);

        this.item.setRegistryName(this.itemName);

        GameRegistry.register(this.item);

        return this.item;
    }

    public void addRecipes()
    {
        for (String sourceMat : this.source.matList)
        {
            for (String targetMat : this.target.matList)
            {
                Object targetMaterial = IronChestType.translateOreName(targetMat);
                Object sourceMaterial = IronChestType.translateOreName(sourceMat);
                //@formatter:off
                IronChestType.addRecipe(new ItemStack(this.item), this.recipe, 'm', targetMaterial, 's', sourceMaterial, 'G', "blockGlass", 'O', Blocks.OBSIDIAN);
                //@formatter:on
            }
        }
    }

    public static void buildItems()
    {
        for (ChestChangerType type : VALUES)
        {
            type.buildItem();
        }
    }

    public static void generateRecipes()
    {
        for (ChestChangerType item : VALUES)
        {
            item.addRecipes();
        }
    }
}
