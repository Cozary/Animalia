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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class FinsModel extends HumanoidModel<LivingEntity> {

    private final ModelPart fins;

    public FinsModel(ModelPart root) {
        super(root);
        this.fins = root.getChild("fins");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition fins = partdefinition.addOrReplaceChild("fins", CubeListBuilder.create().texOffs(16, 32).addBox(-1.0F, -4.0F, -7.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -3.0F, -6.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 16).addBox(-1.0F, -2.0F, -5.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(-1.0F, -1.0F, -4.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 5).addBox(-1.0F, 1.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 16).addBox(8.0F, -5.0F, -9.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(9.0F, -5.0F, -9.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 16).addBox(10.0F, -5.0F, -9.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 16).addBox(11.0F, -5.0F, -9.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 16).addBox(-9.0F, -5.0F, -9.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-10.0F, -5.0F, -9.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-11.0F, -5.0F, -9.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(27, 20).addBox(-12.0F, -5.0F, -9.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 9.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        fins.render(poseStack, buffer, packedLight, packedOverlay);
    }

}
