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

package com.cozary.animalia.client.render.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BullHatModel extends BipedModel<LivingEntity> {

    public BullHatModel(float modelSize) {
        super(modelSize, 0.0F, 64, 64);

        ModelRenderer BullHorns = new ModelRenderer(this);
        hat.addChild(BullHorns);
        BullHorns.setPos(0.0F, 24.0F, 0.0F);
        BullHorns.texOffs(0, 32).addBox(4.0F, -30.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        BullHorns.texOffs(0, 32).addBox(-6.0F, -30.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        BullHorns.texOffs(0, 16).addBox(6.0F, -33.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
        BullHorns.texOffs(0, 16).addBox(-8.0F, -33.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
        BullHorns.texOffs(24, 0).addBox(-8.0F, -33.0F, -7.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        BullHorns.texOffs(24, 0).addBox(6.0F, -33.0F, -7.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
