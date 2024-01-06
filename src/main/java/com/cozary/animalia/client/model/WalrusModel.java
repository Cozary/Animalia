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

import com.cozary.animalia.entities.WalrusEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class WalrusModel<T extends WalrusEntity> extends AgeableModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer legFrontRight;
    private final ModelRenderer legFrontLeft;

    public WalrusModel() {
        texWidth = 128;
        texHeight = 128;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 12.0F, -9.0F);
        head.texOffs(32, 32).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 7.0F, 0.0F, false);
        head.texOffs(4, 8).addBox(-2.0F, 0.0F, -6.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 8).addBox(1.0F, 0.0F, -6.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setPos(-1.0F, 20.0F, 1.0F);
        body.texOffs(0, 0).addBox(-5.0F, -8.0F, -11.0F, 12.0F, 11.0F, 20.0F, 0.0F, false);
        body.texOffs(0, 31).addBox(-3.0F, -4.0F, 9.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        tail = new ModelRenderer(this);
        tail.setPos(-1.0F, 20.5F, 10.0F);
        tail.texOffs(0, 0).addBox(-1.0F, 0.5F, 8.0F, 4.0F, 3.0F, 5.0F, 0.0F, false);

        legFrontRight = new ModelRenderer(this);
        legFrontRight.setPos(-4.0F, 23.5F, -7.0F);
        legFrontRight.texOffs(44, 0).addBox(-5.0F, -0.5F, -6.0F, 5.0F, 1.0F, 6.0F, 0.0F, false);

        legFrontLeft = new ModelRenderer(this);
        legFrontLeft.setPos(4.0F, 23.5F, -8.0F);
        legFrontLeft.texOffs(26, 44).addBox(0.0F, -0.5F, -6.0F, 5.0F, 1.0F, 6.0F, 0.0F, false);
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.y = netHeadYaw * ((float) Math.PI / 180F);
        this.legFrontRight.yRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontLeft.yRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        if (entityIn.isBaby()) {
            this.head.z = -6F;
            this.head.y = 13;
        }
        if (!entityIn.isBaby()) {
            this.head.z = -9;
            this.head.y = 12;
        }
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.tail, this.legFrontLeft, this.legFrontRight);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}