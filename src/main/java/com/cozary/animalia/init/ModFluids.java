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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Animalia.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Animalia.MOD_ID);

    public static final ResourceLocation MUD_STILL_RL = new ResourceLocation(Animalia.MOD_ID, "blocks/water_still");
    public static final ResourceLocation MUD_FLOWING_RL = new ResourceLocation(Animalia.MOD_ID, "blocks/water_flowing");
    public static final ResourceLocation MUD_OVERLAY_RL = new ResourceLocation(Animalia.MOD_ID, "blocks/water_overlay");
    public static final RegistryObject<FlowingFluid> MUD_FLUID = FLUIDS.register("mud_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.MUD_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MUD_FLOWING = FLUIDS.register("mud_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.MUD_PROPERTIES));
    public static final ForgeFlowingFluid.Properties MUD_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> MUD_FLUID.get(), () -> MUD_FLOWING.get(),
            FluidAttributes.builder(MUD_STILL_RL, MUD_FLOWING_RL)
                    .viscosity(6000)
                    .density(6000)
                    .overlay(MUD_OVERLAY_RL)
                    .color(0xff70543e)
                    .sound(SoundEvents.BUCKET_EMPTY)
                    .overlay(MUD_OVERLAY_RL))
            .bucket(ModItems.MUD_BUCKET)
            .block(() -> ModFluids.MUD_BLOCK.get());
    public static final RegistryObject<LiquidBlock> MUD_BLOCK = BLOCKS.register("mud",
            () -> new LiquidBlock(() -> ModFluids.MUD_FLUID.get(),
                    BlockBehaviour.Properties
                            .of(Material.WATER)
                            .noOcclusion()
                            .noCollission()
                            .strength(100.0f)
                            .noDrops()));

}
