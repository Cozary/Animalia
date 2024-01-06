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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BrownBearModel<T extends BrownBearEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leg0;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;

    public BrownBearModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leg0 = root.getChild("leg0");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 0.0F, -6.0F, 14.0F, 14.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 25).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, -1.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 47).addBox(-3.5F, -3.0F, -7.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(39, 0).addBox(-2.5F, 1.0F, -10.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-4.5F, -4.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(2.5F, -4.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -13.0F));

        PartDefinition leg0 = partdefinition.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(42, 17).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 14.0F, 8.0F));

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(36, 39).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 14.0F, 8.0F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(54, 54).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 14.0F, -7.0F));

        PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(50, 0).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 14.0F, -7.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entityIn.isBaby()) {
            this.head.z = -15.5F;
            this.head.y = 10.5F;
        }
        this.leg0.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg1.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
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
            this.head.xRot = ((float) Math.PI / 2F) + 0.2F * Mth.sin(ageInTicks * 0.6F);
            this.leg2.xRot = -0.4F - 0.2F * Mth.sin(ageInTicks * 0.6F);
            this.leg2.yRot = -0.4F - 0.2F * Mth.sin(ageInTicks * 0.6F);
            this.leg3.xRot = -0.4F - 0.2F * Mth.sin(ageInTicks * 0.6F);
            this.leg3.yRot = 0.4F - 0.2F * Mth.sin(ageInTicks * 0.6F);
        } else {
            this.leg2.yRot = 0.0F;
            this.leg3.yRot = 0.0F;
        }
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.leg0, this.leg1, this.leg2, this.leg3);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, buffer, packedLight, packedOverlay);
        head.render(poseStack, buffer, packedLight, packedOverlay);
        leg0.render(poseStack, buffer, packedLight, packedOverlay);
        leg1.render(poseStack, buffer, packedLight, packedOverlay);
        leg2.render(poseStack, buffer, packedLight, packedOverlay);
        leg3.render(poseStack, buffer, packedLight, packedOverlay);
    }
}