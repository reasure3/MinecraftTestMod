package reasure.reasurecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class ModMaterial {
    public static final Material RUBBER = new ModMaterial.Builder(MaterialColor.SNOW).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();

    public static class Builder {
        private final MaterialColor color;
        private PushReaction pushReaction;
        private boolean blocksMotion;
        private boolean flammable;
        private boolean liquid;
        private boolean replaceable;
        private boolean solid;
        private boolean solidBlocking;

        public Builder(MaterialColor color) {
            this.pushReaction = PushReaction.NORMAL;
            this.blocksMotion = true;
            this.solid = true;
            this.solidBlocking = true;
            this.color = color;
        }

        public ModMaterial.Builder liquid() {
            this.liquid = true;
            return this;
        }

        public ModMaterial.Builder nonSolid() {
            this.solid = false;
            return this;
        }

        public ModMaterial.Builder noCollider() {
            this.blocksMotion = false;
            return this;
        }

        private ModMaterial.Builder notSolidBlocking() {
            this.solidBlocking = false;
            return this;
        }

        protected ModMaterial.Builder flammable() {
            this.flammable = true;
            return this;
        }

        public ModMaterial.Builder replaceable() {
            this.replaceable = true;
            return this;
        }

        protected ModMaterial.Builder destroyOnPush() {
            this.pushReaction = PushReaction.DESTROY;
            return this;
        }

        protected ModMaterial.Builder notPushable() {
            this.pushReaction = PushReaction.BLOCK;
            return this;
        }

        public Material build() {
            return new Material(this.color, this.liquid, this.solid, this.blocksMotion, this.solidBlocking, this.flammable, this.replaceable, this.pushReaction);
        }
    }
}
