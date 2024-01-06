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

import com.cozary.animalia.entities.WhiteSharkEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class WhiteSharkModel<T extends WhiteSharkEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart finRight;
    private final ModelPart finRightB;
    private final ModelPart finLeft;
    private final ModelPart finLeftB;
    private final ModelPart tail;
    private final ModelPart finUp;

    public WhiteSharkModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.finRight = root.getChild("finRight");
        this.finRightB = root.getChild("finRightB");
        this.finLeft = root.getChild("finLeft");
        this.finLeftB = root.getChild("finLeftB");
        this.tail = root.getChild("tail");
        this.finUp = root.getChild("finUp");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -7.0F, -20.0F, 19.0F, 14.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 17.0F, -3.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(52, 56).addBox(-7.0F, -5.0F, -8.0F, 15.0F, 11.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(39, 75).addBox(-5.0F, -1.0F, -21.0F, 11.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 80).addBox(-5.0F, 2.0F, -19.0F, 11.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 17.0F, -23.0F));

        PartDefinition finRight = partdefinition.addOrReplaceChild("finRight", CubeListBuilder.create(), PartPose.offset(-12.0F, 24.0F, -15.0F));

        PartDefinition cube_r1 = finRight.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(74, 75).addBox(-13.0937F, -1.0F, -0.5774F, 14.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition finRightB = partdefinition.addOrReplaceChild("finRightB", CubeListBuilder.create(), PartPose.offset(-11.0F, 21.0F, 6.0F));

        PartDefinition cube_r2 = finRightB.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 31).addBox(-10.0F, -2.0F, 1.0F, 10.0F, 3.0F, 8.0F, new CubeDeformation(-2.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.4363F, -0.1745F));

        PartDefinition finLeft = partdefinition.addOrReplaceChild("finLeft", CubeListBuilder.create(), PartPose.offset(7.0F, 24.0F, -15.0F));

        PartDefinition cube_r3 = finLeft.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(80, 18).addBox(-1.0F, -1.0F, -1.0F, 14.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition finLeftB = partdefinition.addOrReplaceChild("finLeftB", CubeListBuilder.create(), PartPose.offset(2.0F, 21.0F, 6.0F));

        PartDefinition cube_r4 = finLeftB.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 20).addBox(3.0F, -2.0F, -1.0F, 10.0F, 3.0F, 8.0F, new CubeDeformation(-2.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.1745F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 56).addBox(-8.0F, -6.0F, 0.0F, 16.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(80, 0).addBox(-6.0F, -2.0F, 10.0F, 12.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(40, 91).addBox(-4.0F, 1.0F, 20.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(6, 94).addBox(-1.0F, -4.0F, 24.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 94).addBox(-1.0F, -8.0F, 26.0F, 2.0F, 24.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-1.0F, -10.0F, 27.0F, 2.0F, 28.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(64, 91).addBox(-1.0F, -8.0F, 28.0F, 2.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 18.0F, 19.0F));

        PartDefinition finUp = partdefinition.addOrReplaceChild("finUp", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -6.0F, -7.0F, 3.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(74, 87).addBox(-1.5F, -7.0F, -5.0F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(80, 30).addBox(-1.5F, -8.0F, -3.0F, 3.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(93, 87).addBox(-1.5F, -9.0F, 0.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(42, 56).addBox(-1.5F, -10.0F, 3.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 11.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.tail.yRot = Mth.sin(ageInTicks * 0.2F) * 0.2F;
        this.tail.z = 17F;
        this.finLeft.yRot = Mth.sin(ageInTicks * 0.1F) * 0.1F;
        this.finLeftB.yRot = Mth.sin(ageInTicks * 0.1F) * 0.1F;
        this.finRight.yRot = Mth.sin(ageInTicks * 0.1F) * 0.1F;
        this.finRightB.yRot = Mth.sin(ageInTicks * 0.1F) * 0.1F;
        this.finUp.zRot = Mth.sin(ageInTicks * 0.1F) * 0.1F;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.finRight, this.finLeft, this.tail, this.finUp, this.finLeftB, this.finRightB);
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
        finRight.render(poseStack, buffer, packedLight, packedOverlay);
        finRightB.render(poseStack, buffer, packedLight, packedOverlay);
        finLeft.render(poseStack, buffer, packedLight, packedOverlay);
        finLeftB.render(poseStack, buffer, packedLight, packedOverlay);
        tail.render(poseStack, buffer, packedLight, packedOverlay);
        finUp.render(poseStack, buffer, packedLight, packedOverlay);
    }
}