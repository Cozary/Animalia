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

package com.cozary.animalia.client.proxy;

import com.cozary.animalia.client.render.armor.BearHatModel;
import com.cozary.animalia.client.render.armor.BullHatModel;
import com.cozary.animalia.client.render.armor.FinsModel;
import com.cozary.animalia.client.render.armor.ShellModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy implements IProxy {

    private final ShellModel shell = new ShellModel(1.0f);
    private final ShellModel shellChest = new ShellModel(0.5f);
    private final BearHatModel hat = new BearHatModel(1.0f);
    private final BearHatModel bearHat = new BearHatModel(0.5f);
    private final BullHatModel bhat = new BullHatModel(1.0f);
    private final BullHatModel bullHat = new BullHatModel(0.5f);
    private final FinsModel fins = new FinsModel(1.0f);
    private final FinsModel finsChest = new FinsModel(0.5f);

    @Override
    public void setup(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> A getShellModel(EquipmentSlotType armorSlot) {
        return (A) (armorSlot == EquipmentSlotType.CHEST ? shellChest : shell);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> A getBearHatModel(EquipmentSlotType armorSlot) {
        return (A) (armorSlot == EquipmentSlotType.HEAD ? bearHat : hat);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> A getBullHatModel(EquipmentSlotType armorSlot) {
        return (A) (armorSlot == EquipmentSlotType.HEAD ? bullHat : bhat);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> A getFinsModel(EquipmentSlotType armorSlot) {
        return (A) (armorSlot == EquipmentSlotType.CHEST ? finsChest : fins);
    }
}
