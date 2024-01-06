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

import com.cozary.animalia.entities.HippopotamusEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class HippopotamusModel<T extends HippopotamusEntity> extends AgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer boca;
    private final ModelRenderer dientes2;
    private final ModelRenderer boca2;
    private final ModelRenderer dientes;
    private final ModelRenderer dientesmid;
    private final ModelRenderer orejaright;
    private final ModelRenderer orejaleft;
    private final ModelRenderer legsLeft;
    private final ModelRenderer legsRight2;
    private final ModelRenderer legsLeft2;
    private final ModelRenderer legsRight;
    private final ModelRenderer tail;
    private final ModelRenderer eyeR;
    private final ModelRenderer eyeL;

    public HippopotamusModel() {
        texWidth = 256;
        texHeight = 256;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 10.0F, 15.0F);
        body.texOffs(0, 0).addBox(-15.0F, -12.0F, -23.0F, 24.0F, 20.0F, 48.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(-4.0F, 9.0F, -8.0F);
        head.texOffs(0, 68).addBox(-10.0F, -10.0F, -9.0F, 22.0F, 15.0F, 9.0F, 0.0F, false);
        head.texOffs(62, 68).addBox(-9.0F, -8.0F, -20.0F, 20.0F, 12.0F, 11.0F, 0.0F, false);

        boca = new ModelRenderer(this);
        boca.setPos(-3.0F, 4.5F, -27.5F);
        setRotationAngle(boca, 0.3491F, 0.0F, 0.0F);
        boca.texOffs(49, 91).addBox(-7.0F, -3.3229F, -11.9158F, 14.0F, 6.0F, 13.0F, 0.0F, false);

        dientes2 = new ModelRenderer(this);
        dientes2.setPos(0.0F, 2.9969F, -11.3533F);
        boca.addChild(dientes2);
        setRotationAngle(dientes2, 0.4363F, 0.0F, 0.0F);
        dientes2.texOffs(0, 22).addBox(5.9F, -1.0F, 0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        dientes2.texOffs(15, 11).addBox(-6.9F, -1.0F, 0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        boca2 = new ModelRenderer(this);
        boca2.setPos(-3.0F, 9.9771F, -27.4658F);
        setRotationAngle(boca2, 0.4363F, 0.0F, 0.0F);
        boca2.texOffs(96, 0).addBox(-7.0F, -0.6467F, -12.0031F, 14.0F, 2.0F, 12.0F, 0.0F, false);

        dientes = new ModelRenderer(this);
        dientes.setPos(0.0F, -0.8538F, -10.7338F);
        boca2.addChild(dientes);
        setRotationAngle(dientes, 0.2618F, 0.0F, 0.0F);
        dientes.texOffs(30, 17).addBox(-6.9F, -2.5F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        dientes.texOffs(0, 32).addBox(5.9F, -2.5F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        dientesmid = new ModelRenderer(this);
        dientesmid.setPos(-6.0F, 1.2837F, 1.2891F);
        dientes.addChild(dientesmid);
        setRotationAngle(dientesmid, 0.6545F, 0.0F, 0.0F);
        dientesmid.texOffs(0, 0).addBox(6.1F, -3.5F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        dientesmid.texOffs(0, 11).addBox(5.1F, -3.5F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        orejaright = new ModelRenderer(this);
        orejaright.setPos(-10.5F, 2.0F, -22.0F);
        setRotationAngle(orejaright, 0.0F, 0.0F, -0.5236F);
        orejaright.texOffs(158, 182).addBox(-3.0F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, 0.0F, false);

        orejaleft = new ModelRenderer(this);
        orejaleft.setPos(5.5F, 2.0F, -22.0F);
        setRotationAngle(orejaleft, 0.0F, 0.0F, 0.5236F);
        orejaleft.texOffs(140, 186).addBox(-0.9F, -3.5F, 0.0F, 3.0F, 4.0F, 1.0F, 0.0F, false);

        legsLeft = new ModelRenderer(this);
        legsLeft.setPos(6.0F, 18.0F, 0.0F);
        legsLeft.texOffs(20, 0).addBox(-2.0F, 0.0F, -3.0F, 5.0F, 6.0F, 5.0F, 0.0F, false);

        legsRight2 = new ModelRenderer(this);
        legsRight2.setPos(6.0F, 19.0F, 30.0F);
        legsRight2.texOffs(0, 11).addBox(-21.0F, -1.0F, -3.0F, 5.0F, 6.0F, 5.0F, 0.0F, false);

        legsLeft2 = new ModelRenderer(this);
        legsLeft2.setPos(-13.0F, 18.0F, 30.0F);
        legsLeft2.texOffs(15, 17).addBox(17.0F, 0.0F, -3.0F, 5.0F, 6.0F, 5.0F, 0.0F, false);

        legsRight = new ModelRenderer(this);
        legsRight.setPos(-13.0F, 18.0F, 0.0F);
        legsRight.texOffs(0, 0).addBox(-2.0F, 0.0F, -3.0F, 5.0F, 6.0F, 5.0F, 0.0F, false);

        tail = new ModelRenderer(this);
        tail.setPos(-3.0F, -2.0F, 40.0F);
        setRotationAngle(tail, -0.48F, 0.0F, 0.0F);
        tail.texOffs(0, 24).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 4.0F, 4.0F, 0.0F, false);

        eyeR = new ModelRenderer(this);
        eyeR.setPos(-3.0F, 0.5F, -24.5F);
        eyeR.texOffs(182, 189).addBox(-6.0F, -2.0F, -1.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

        eyeL = new ModelRenderer(this);
        eyeL.setPos(3.0F, 1.5F, -25.5F);
        eyeL.texOffs(217, 189).addBox(-3.0F, -3.0F, 0.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.legsLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legsLeft2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legsRight.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legsRight2.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.boca.xRot = MathHelper.sin(ageInTicks * 0.1F) * 0.1F;
        this.boca2.xRot = -MathHelper.sin(ageInTicks * 0.1F) * 0.1F;
        this.eyeL.xRot = headPitch * ((float) Math.PI / 180F);
        this.eyeL.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.eyeR.xRot = headPitch * ((float) Math.PI / 180F);
        this.eyeR.yRot = netHeadYaw * ((float) Math.PI / 180F);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.boca, this.boca2, this.orejaright, this.orejaleft, this.eyeR, this.eyeL);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.head, this.body, this.legsLeft, this.legsLeft2, this.legsRight, this.legsRight2, this.tail);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}