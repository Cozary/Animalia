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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class HippopotamusModel<T extends HippopotamusEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart boca;
    private final ModelPart boca2;
    private final ModelPart orejaright;
    private final ModelPart orejaleft;
    private final ModelPart legsLeft;
    private final ModelPart legsRight2;
    private final ModelPart legsLeft2;
    private final ModelPart legsRight;
    private final ModelPart tail;
    private final ModelPart eyeR;
    private final ModelPart eyeL;

    public HippopotamusModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.boca = root.getChild("boca");
        this.boca2 = root.getChild("boca2");
        this.orejaright = root.getChild("orejaright");
        this.orejaleft = root.getChild("orejaleft");
        this.legsLeft = root.getChild("legsLeft");
        this.legsRight2 = root.getChild("legsRight2");
        this.legsLeft2 = root.getChild("legsLeft2");
        this.legsRight = root.getChild("legsRight");
        this.tail = root.getChild("tail");
        this.eyeR = root.getChild("eyeR");
        this.eyeL = root.getChild("eyeL");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -12.0F, -23.0F, 24.0F, 20.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 15.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 68).addBox(-10.0F, -10.0F, -9.0F, 22.0F, 15.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(62, 68).addBox(-9.0F, -8.0F, -20.0F, 20.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 9.0F, -8.0F));

        PartDefinition boca = partdefinition.addOrReplaceChild("boca", CubeListBuilder.create().texOffs(49, 91).addBox(-7.0F, -3.3229F, -11.9158F, 14.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 4.5F, -27.5F, 0.3491F, 0.0F, 0.0F));

        PartDefinition dientes2 = boca.addOrReplaceChild("dientes2", CubeListBuilder.create().texOffs(0, 22).addBox(5.9F, -1.0F, 0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 11).addBox(-6.9F, -1.0F, 0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.9969F, -11.3533F, 0.4363F, 0.0F, 0.0F));

        PartDefinition boca2 = partdefinition.addOrReplaceChild("boca2", CubeListBuilder.create().texOffs(96, 0).addBox(-7.0F, -0.6467F, -12.0031F, 14.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 9.9771F, -27.4658F, 0.4363F, 0.0F, 0.0F));

        PartDefinition dientes = boca2.addOrReplaceChild("dientes", CubeListBuilder.create().texOffs(30, 17).addBox(-6.9F, -2.5F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(5.9F, -2.5F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.8538F, -10.7338F, 0.2618F, 0.0F, 0.0F));

        PartDefinition dientesmid = dientes.addOrReplaceChild("dientesmid", CubeListBuilder.create().texOffs(0, 0).addBox(6.1F, -3.5F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(5.1F, -3.5F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 1.2837F, 1.2891F, 0.6545F, 0.0F, 0.0F));

        PartDefinition orejaright = partdefinition.addOrReplaceChild("orejaright", CubeListBuilder.create().texOffs(158, 182).addBox(-3.0F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.5F, 2.0F, -22.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition orejaleft = partdefinition.addOrReplaceChild("orejaleft", CubeListBuilder.create().texOffs(140, 186).addBox(-0.9F, -3.5F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, 2.0F, -22.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition legsLeft = partdefinition.addOrReplaceChild("legsLeft", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, 0.0F, -3.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 18.0F, 0.0F));

        PartDefinition legsRight2 = partdefinition.addOrReplaceChild("legsRight2", CubeListBuilder.create().texOffs(0, 11).addBox(-21.0F, -1.0F, -3.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 19.0F, 30.0F));

        PartDefinition legsLeft2 = partdefinition.addOrReplaceChild("legsLeft2", CubeListBuilder.create().texOffs(15, 17).addBox(17.0F, 0.0F, -3.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 18.0F, 30.0F));

        PartDefinition legsRight = partdefinition.addOrReplaceChild("legsRight", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -3.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 18.0F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 24).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.0F, 40.0F, -0.48F, 0.0F, 0.0F));

        PartDefinition eyeR = partdefinition.addOrReplaceChild("eyeR", CubeListBuilder.create().texOffs(182, 189).addBox(-6.0F, -2.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.5F, -24.5F));

        PartDefinition eyeL = partdefinition.addOrReplaceChild("eyeL", CubeListBuilder.create().texOffs(217, 189).addBox(-3.0F, -3.0F, 0.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.5F, -25.5F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.legsLeft.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legsLeft2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legsRight.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legsRight2.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.boca.xRot = Mth.sin(ageInTicks * 0.1F) * 0.1F;
        this.boca2.xRot = -Mth.sin(ageInTicks * 0.1F) * 0.1F;
        this.eyeL.xRot = headPitch * ((float) Math.PI / 180F);
        this.eyeL.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.eyeR.xRot = headPitch * ((float) Math.PI / 180F);
        this.eyeR.yRot = netHeadYaw * ((float) Math.PI / 180F);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.boca, this.boca2, this.orejaright, this.orejaleft, this.eyeR, this.eyeL);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.head, this.body, this.legsLeft, this.legsLeft2, this.legsRight, this.legsRight2, this.tail);
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
        boca.render(poseStack, buffer, packedLight, packedOverlay);
        boca2.render(poseStack, buffer, packedLight, packedOverlay);
        orejaright.render(poseStack, buffer, packedLight, packedOverlay);
        orejaleft.render(poseStack, buffer, packedLight, packedOverlay);
        legsLeft.render(poseStack, buffer, packedLight, packedOverlay);
        legsRight2.render(poseStack, buffer, packedLight, packedOverlay);
        legsLeft2.render(poseStack, buffer, packedLight, packedOverlay);
        legsRight.render(poseStack, buffer, packedLight, packedOverlay);
        tail.render(poseStack, buffer, packedLight, packedOverlay);
        eyeR.render(poseStack, buffer, packedLight, packedOverlay);
        eyeL.render(poseStack, buffer, packedLight, packedOverlay);
    }
}