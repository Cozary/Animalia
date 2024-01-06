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

import com.cozary.animalia.entities.JellyfishEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import java.util.Arrays;

public class JellyfishModel<T extends JellyfishEntity> extends HierarchicalModel<T> {
    private final ModelPart[] tentacles = new ModelPart[8];
    private final ModelPart root;

    public JellyfishModel(ModelPart p_170989_) {
        this.root = p_170989_;
        Arrays.setAll(this.tentacles, (p_170995_) -> {
            return p_170989_.getChild(createTentacleName(p_170995_));
        });
    }

    private static String createTentacleName(int p_170992_) {
        return "tentacle" + p_170992_;
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 15.0F, 0.0F));
        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(8, 8).addBox(-1.0F, 2.0F, -4.0F, 1.0F, 5.0F, 1.0F);


        for (int k = 0; k < 8; ++k) {
            double d0 = (double) k * Math.PI * 2.0D / 8.0D;
            float f = (float) Math.cos(d0) * 5.0F;
            float f1 = 14.0F;
            float f2 = (float) Math.sin(d0) * 5.0F;
            d0 = (double) k * Math.PI * -2.0D / 8.0D + (Math.PI / 2D);
            float f3 = (float) d0;
            partdefinition.addOrReplaceChild(createTentacleName(k), cubelistbuilder, PartPose.offsetAndRotation(f + 1, f1, f2, 0.0F, f3, 0.0F));
        }

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        for (ModelPart modelpart : this.tentacles) {
            modelpart.xRot = ageInTicks / 2;
        }
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}