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

package com.cozary.animalia.client.model;

import com.cozary.animalia.entities.SnailEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SnailModel<T extends SnailEntity> extends AgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer tail;

    public SnailModel() {
        texWidth = 32;
        texHeight = 32;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 20.0F, 1.0F);
        body.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, 23.0F, -3.0F);
        head.texOffs(14, 16).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 2.0F, 3.0F, 0.0F, false);
        head.texOffs(0, 16).addBox(-2.0F, -6.0F, -6.0F, 4.0F, 7.0F, 3.0F, 0.0F, false);
        head.texOffs(0, 3).addBox(-3.0F, -8.0F, -5.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 0).addBox(2.0F, -8.0F, -5.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        tail = new ModelRenderer(this);
        tail.setPos(0.0F, 23.0F, 5.0F);
        tail.texOffs(14, 21).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isBaby()) {
            this.head.z = -3.5F;
            this.head.y = 18;
        }
        if (!entityIn.isBaby()) {
            this.head.z = -3;
            this.head.y = 23;
        }
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.tail);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}