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

import com.cozary.animalia.entities.DirtyPigEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class DirtyPigModel<T extends DirtyPigEntity> extends AgeableModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer legBackRight;
    private final ModelRenderer legBackLeft;
    private final ModelRenderer legFrontRight;
    private final ModelRenderer legFrontLeft;

    public DirtyPigModel() {
        texWidth = 64;
        texHeight = 32;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 12.0F, -6.0F);
        head.texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        head.texOffs(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
        head.texOffs(4, 5).addBox(2.0F, -6.0F, -4.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(4, 2).addBox(1.0F, -8.0F, -4.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(4, 1).addBox(0.0F, -8.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 2).addBox(-1.0F, -10.0F, -4.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 1).addBox(0.0F, -10.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 3).addBox(-2.0F, -9.0F, -4.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 3).addBox(2.0F, -9.0F, -4.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 0).addBox(3.0F, -10.0F, -4.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 0).addBox(4.0F, -11.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 11.0F, 2.0F);
        setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
        body.texOffs(28, 8).addBox(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, 0.0F, false);

        legBackRight = new ModelRenderer(this);
        legBackRight.setPos(-3.0F, 18.0F, 7.0F);
        legBackRight.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

        legBackLeft = new ModelRenderer(this);
        legBackLeft.setPos(3.0F, 18.0F, 7.0F);
        legBackLeft.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, true);

        legFrontRight = new ModelRenderer(this);
        legFrontRight.setPos(-3.0F, 18.0F, -5.0F);
        legFrontRight.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

        legFrontLeft = new ModelRenderer(this);
        legFrontLeft.setPos(3.0F, 18.0F, -5.0F);
        legFrontLeft.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, true);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.body.xRot = ((float) Math.PI / 2F);
        this.legBackRight.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legBackLeft.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontRight.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }


    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}