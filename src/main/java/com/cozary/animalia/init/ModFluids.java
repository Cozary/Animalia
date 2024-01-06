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
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Animalia.MOD_ID);

    public static final ResourceLocation MUD_STILL_RL = new ResourceLocation(Animalia.MOD_ID, "blocks/mud_still");
    public static final ResourceLocation MUD_FLOWING_RL = new ResourceLocation(Animalia.MOD_ID,
            "blocks/mud_flowing");
    public static final ResourceLocation MUD_OVERLAY_RL = new ResourceLocation(Animalia.MOD_ID,
            "blocks/mud_overlay");
    public static final RegistryObject<FlowingFluid> MUD_FLUID = FLUIDS.register("mud_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.MUD_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MUD_FLOWING = FLUIDS.register("mud_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MUD_PROPERTIES));
    public static final ForgeFlowingFluid.Properties MUD_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> MUD_FLUID.get(), () -> MUD_FLOWING.get(),
            FluidAttributes.builder(MUD_STILL_RL, MUD_FLOWING_RL)
                    .viscosity(6000)
                    .density(6000)
                    .overlay(ResourceLocation.tryParse("blocks/mud_overlay"))
                    .color(7361598)
                    .sound(SoundEvents.HONEY_DRINK)
                    .overlay(MUD_OVERLAY_RL))
            .bucket(ModItems.mud_bucket)
            .block(() -> ModFluids.MUD_BLOCK.get());
    public static final RegistryObject<FlowingFluidBlock> MUD_BLOCK = ModBlocks.BLOCKS.register("mud",
            () -> new FlowingFluidBlock(() -> ModFluids.MUD_FLUID.get(),
                    Block.Properties
                            .of(Material.WATER)
                            .noCollission()
                            .strength(100.0f)
                            .noDrops()));
}
