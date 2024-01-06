/*
 *
 *  * Copyright (c) 2024 Cozary
 *  *
 *  * This file is part of Animalia, a mod made for Minecraft.
 *  *
 *  * Animalia is free software: you can redistribute it and/or modify it
 *  * under the terms of the GNU General Public License as published
 *  * by the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * Animalia is distributed in the hope that it will be useful, but
 *  * WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * License along with Animalia.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.cozary.animalia.init;

import com.cozary.animalia.Animalia;
import com.cozary.animalia.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Animalia.MOD_ID);

    //Mud
    public static final RegistryObject<Block> MUD_BLOCK = BLOCKS.register("mud_block", MudBlock::new);
    public static final RegistryObject<Block> DECORATIVE_MUD = BLOCKS.register("decorative_mud", DecorativeMud::new);
    public static final RegistryObject<Block> DECORATIVE_MUD_STAIRS = BLOCKS.register("decorative_mud_stairs", () -> new StairsBlock(() ->
            ModBlocks.DECORATIVE_MUD.get().defaultBlockState(),
            Block.Properties.copy(ModBlocks.DECORATIVE_MUD.get())));
    public static final RegistryObject<Block> DECORATIVE_MUD_SLAB = BLOCKS.register("decorative_mud_slab", () -> new SlabBlock(Block.Properties.copy(ModBlocks.DECORATIVE_MUD.get())));
    public static final RegistryObject<Block> HARDENED_MUD = BLOCKS.register("hardened_mud", HardenedMud::new);
    //Snail
    public static final RegistryObject<Block> SHELL = BLOCKS.register("shell", Shell::new);

    //Jellyfish
    public static final RegistryObject<Block> JELLY_BLOCK = BLOCKS.register("jelly_block", JellyBlock::new);

}
