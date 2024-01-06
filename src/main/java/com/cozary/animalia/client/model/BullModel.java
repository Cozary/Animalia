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

import com.cozary.animalia.entities.BullEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class BullModel<T extends BullEntity> extends AgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer leg0;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer tail;

    public BullModel() {
        texWidth = 64;
        texHeight = 64;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 5.0F, 2.0F);
        setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
        body.texOffs(0, 0).addBox(-6.0F, -10.0F, -10.0F, 12.0F, 21.0F, 13.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, 4.0F, -8.0F);
        head.texOffs(0, 34).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, 0.0F, false);
        head.texOffs(22, 34).addBox(4.0F, -4.0F, -4.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
        head.texOffs(23, 47).addBox(-7.0F, -6.0F, -7.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);
        head.texOffs(0, 7).addBox(-7.0F, -4.0F, -4.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
        head.texOffs(11, 49).addBox(5.0F, -6.0F, -7.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);
        head.texOffs(0, 48).addBox(-3.0F, 0.0F, -8.0F, 6.0F, 4.0F, 2.0F, 0.0F, false);

        leg0 = new ModelRenderer(this);
        leg0.setPos(-4.0F, 15.0F, 11.0F);
        leg0.texOffs(46, 30).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.0F, false);

        leg1 = new ModelRenderer(this);
        leg1.setPos(4.0F, 15.0F, 11.0F);
        leg1.texOffs(40, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.0F, false);

        leg2 = new ModelRenderer(this);
        leg2.setPos(-4.0F, 15.0F, -5.0F);
        leg2.texOffs(37, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.0F, false);

        leg3 = new ModelRenderer(this);
        leg3.setPos(4.0F, 15.0F, -5.0F);
        leg3.texOffs(28, 34).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.0F, false);

        tail = new ModelRenderer(this);
        tail.setPos(0.0F, 4.0F, 13.0F);
        tail.texOffs(50, 13).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.body.xRot = ((float) Math.PI / 2F);
        this.leg0.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg1.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.tail.zRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        if (entityIn.isBaby()) {
            this.head.z = -6;
            this.head.y = 9;
        }
        if (!entityIn.isBaby()) {
            this.head.z = -8;
            this.head.y = 4;
        }
        if (entityIn.isAggressive()) {
            this.head.xRot = MathHelper.cos(0.6662F);
            this.leg0.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg3.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg1.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.tail.zRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.leg0, this.tail, this.leg1, this.leg2, this.leg3);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}