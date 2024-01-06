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

import com.cozary.animalia.entities.BrownBearEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class BrownBearModel<T extends BrownBearEntity> extends AgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer leg0;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;

    public BrownBearModel() {
        texWidth = 128;
        texHeight = 128;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 8.0F, -1.0F);
        setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
        body.texOffs(0, 0).addBox(-7.0F, 0.0F, -6.0F, 14.0F, 14.0F, 11.0F, 0.0F, false);
        body.texOffs(0, 25).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 10.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, 10.0F, -13.0F);
        head.texOffs(0, 47).addBox(-3.5F, -3.0F, -7.0F, 7.0F, 7.0F, 7.0F, 0.0F, false);
        head.texOffs(39, 0).addBox(-2.5F, 1.0F, -10.0F, 5.0F, 3.0F, 3.0F, 0.0F, false);
        head.texOffs(0, 3).addBox(-4.5F, -4.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 0).addBox(2.5F, -4.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        leg0 = new ModelRenderer(this);
        leg0.setPos(-4.5F, 14.0F, 8.0F);
        leg0.texOffs(42, 17).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 10.0F, 8.0F, 0.0F, false);

        leg1 = new ModelRenderer(this);
        leg1.setPos(4.5F, 14.0F, 8.0F);
        leg1.texOffs(36, 39).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 10.0F, 8.0F, 0.0F, false);

        leg2 = new ModelRenderer(this);
        leg2.setPos(-3.5F, 14.0F, -7.0F);
        leg2.texOffs(54, 54).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 10.0F, 6.0F, 0.0F, false);

        leg3 = new ModelRenderer(this);
        leg3.setPos(3.5F, 14.0F, -7.0F);
        leg3.texOffs(50, 0).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 10.0F, 6.0F, 0.0F, false);
    }

    //TODO AL LEVANTARSE PARA ATACAR LA ANIMACIÓN ES ERRONEA

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entityIn.isBaby()) {
            this.head.z = -15.5F;
            this.head.y = 10.5F;
        }
        this.leg0.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg1.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        float f = ageInTicks - (float) entityIn.tickCount;
        float f1 = entityIn.getStandingAnimationScale(f);
        f1 = f1 * f1;
        float f2 = 1.0F - f1;
        this.body.xRot = ((float) Math.PI / 2F) - f1 * (float) Math.PI * 0.35F;
        this.body.y = 9.0F * f2 + 2.0F * f1;
        this.leg2.y = 14.0F * f2 - 6.0F * f1;
        this.leg2.z = -8.0F * f2 - 4.0F * f1;
        this.leg2.xRot -= f1 * (float) Math.PI * 0.45F;
        this.leg3.y = this.leg2.y;
        this.leg3.z = this.leg2.z;
        this.leg3.xRot -= f1 * (float) Math.PI * 0.45F;
        if (this.young) {
            this.head.y = 11.5F * f2 - 9.0F * f1;
            this.head.z = -9.0F * f2 - 7.0F * f1;
        } else {
            this.head.y = 10.0F * f2 - 14.0F * f1;
            this.head.z = -13.0F * f2 - 3.0F * f1;
        }
        this.head.xRot += f1 * (float) Math.PI * 0.15F;
        boolean flag2 = entityIn.func_213578_dZ();
        if (flag2) {
            this.head.xRot = ((float) Math.PI / 2F) + 0.2F * MathHelper.sin(ageInTicks * 0.6F);
            this.leg2.xRot = -0.4F - 0.2F * MathHelper.sin(ageInTicks * 0.6F);
            this.leg2.yRot = -0.4F - 0.2F * MathHelper.sin(ageInTicks * 0.6F);
            this.leg3.xRot = -0.4F - 0.2F * MathHelper.sin(ageInTicks * 0.6F);
            this.leg3.yRot = 0.4F - 0.2F * MathHelper.sin(ageInTicks * 0.6F);
        } else {
            this.leg2.yRot = 0.0F;
            this.leg3.yRot = 0.0F;
        }
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.leg0, this.leg1, this.leg2, this.leg3);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}