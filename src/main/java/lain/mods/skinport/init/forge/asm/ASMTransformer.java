package lain.mods.skinport.init.forge.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import net.minecraft.launchwrapper.IClassTransformer;

public class ASMTransformer implements IClassTransformer
{

    class transformer001 extends ClassVisitor
    {

        class method001 extends MethodVisitor
        {

            public method001(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode)
            {
                if (opcode == Opcodes.ARETURN)
                {
                    this.visitVarInsn(Opcodes.ASTORE, 1);
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitVarInsn(Opcodes.ALOAD, 1);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "getLocationCape", "(Lnet/minecraft/client/entity/AbstractClientPlayer;Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/util/ResourceLocation;", false);
                }
                super.visitInsn(opcode);
            }

        }

        class method002 extends MethodVisitor
        {

            public method002(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode)
            {
                if (opcode == Opcodes.ARETURN)
                {
                    this.visitVarInsn(Opcodes.ASTORE, 1);
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitVarInsn(Opcodes.ALOAD, 1);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "getLocationSkin", "(Lnet/minecraft/client/entity/AbstractClientPlayer;Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/util/ResourceLocation;", false);
                }
                super.visitInsn(opcode);
            }

        }

        class method003 extends MethodVisitor
        {

            public method003(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode)
            {
                if (opcode == Opcodes.IRETURN)
                {
                    this.visitVarInsn(Opcodes.ISTORE, 1);
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitVarInsn(Opcodes.ILOAD, 1);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "hasCape", "(Lnet/minecraft/client/entity/AbstractClientPlayer;Z)Z", false);
                }
                super.visitInsn(opcode);
            }

        }

        class method004 extends MethodVisitor
        {

            public method004(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode)
            {
                if (opcode == Opcodes.IRETURN)
                {
                    this.visitVarInsn(Opcodes.ISTORE, 1);
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitVarInsn(Opcodes.ILOAD, 1);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "hasSkin", "(Lnet/minecraft/client/entity/AbstractClientPlayer;Z)Z", false);
                }
                super.visitInsn(opcode);
            }

        }

        ObfHelper m001 = ObfHelper.newMethod("func_110303_q", "net/minecraft/client/entity/AbstractClientPlayer", "()Lnet/minecraft/util/ResourceLocation;").setDevName("getLocationCape");
        ObfHelper m002 = ObfHelper.newMethod("func_110306_p", "net/minecraft/client/entity/AbstractClientPlayer", "()Lnet/minecraft/util/ResourceLocation;").setDevName("getLocationSkin");
        ObfHelper m003 = ObfHelper.newMethod("func_152122_n", "net/minecraft/client/entity/AbstractClientPlayer", "()Z"); // hasCape
        ObfHelper m004 = ObfHelper.newMethod("func_152123_o", "net/minecraft/client/entity/AbstractClientPlayer", "()Z"); // hasSkin

        public transformer001(ClassVisitor cv)
        {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            if (m001.match(name, desc))
                return new method001(super.visitMethod(access, name, desc, signature, exceptions));
            if (m002.match(name, desc))
                return new method002(super.visitMethod(access, name, desc, signature, exceptions));
            if (m003.match(name, desc))
                return new method003(super.visitMethod(access, name, desc, signature, exceptions));
            if (m004.match(name, desc))
                return new method004(super.visitMethod(access, name, desc, signature, exceptions));
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    class transformer002 extends ClassVisitor
    {

        class method001 extends MethodVisitor
        {

            ObfHelper target = ObfHelper.newMethod("func_147499_a", "net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer", "(Lnet/minecraft/util/ResourceLocation;)V").setDevName("bindTexture");
            ObfHelper humanoidHead = ObfHelper.newField("field_147533_g", "net/minecraft/client/renderer/tileentity/TileEntitySkullRenderer", "Lnet/minecraft/client/model/ModelSkeletonHead;"); // humanoidHead

            int lastALOAD = -1;

            public method001(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
            {
                if (opcode == Opcodes.INVOKEVIRTUAL && target.match(name, desc) && lastALOAD == 9)
                {
                    this.visitInsn(Opcodes.POP);
                    this.visitVarInsn(Opcodes.ALOAD, 7);
                    this.visitVarInsn(Opcodes.ALOAD, 9);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "TileEntitySkullRenderer_bindTexture", "(Lcom/mojang/authlib/GameProfile;Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/util/ResourceLocation;", false);
                    this.visitVarInsn(Opcodes.ASTORE, 9);
                    this.visitVarInsn(Opcodes.ALOAD, 9);
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    this.visitVarInsn(Opcodes.ALOAD, 9);
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitFieldInsn(Opcodes.GETFIELD, humanoidHead.getData(0), "field_147533_g", humanoidHead.getData(2)); // hacky solution
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "TileEntitySkullRenderer_bindHumanoidHead", "(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/model/ModelSkeletonHead;)Lnet/minecraft/client/model/ModelSkeletonHead;", false);
                    this.visitVarInsn(Opcodes.ASTORE, 8);
                }
                else
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
            }

            @Override
            public void visitVarInsn(int opcode, int var)
            {
                if (opcode == Opcodes.ALOAD)
                    lastALOAD = var;
                super.visitVarInsn(opcode, var);
            }

        }

        ObfHelper m001 = ObfHelper.newMethod("func_152674_a", "net/minecraft/client/renderer/tileentity/TileEntitySkullRenderer", "(FFFIFILcom/mojang/authlib/GameProfile;)V"); // renderSkull

        public transformer002(ClassVisitor cv)
        {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            if (m001.match(name, desc))
                return new method001(super.visitMethod(access, name, desc, signature, exceptions));
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    class transformer003 extends ClassVisitor
    {

        class method001 extends MethodVisitor
        {

            public method001(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode)
            {
                if (opcode == Opcodes.RETURN)
                {
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "RenderManager_postRenderManagerInit", "(Lnet/minecraft/client/renderer/entity/RenderManager;)V", false);
                }
                super.visitInsn(opcode);
            }

        }

        class method002 extends MethodVisitor
        {

            public method002(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode)
            {
                if (opcode == Opcodes.ARETURN)
                {
                    this.visitVarInsn(Opcodes.ASTORE, 2);
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitVarInsn(Opcodes.ALOAD, 1);
                    this.visitVarInsn(Opcodes.ALOAD, 2);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "RenderManager_getEntityRenderObject", "(Lnet/minecraft/client/renderer/entity/RenderManager;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/entity/Render;)Lnet/minecraft/client/renderer/entity/Render;", false);
                }
                super.visitInsn(opcode);
            }

        }

        ObfHelper m001 = ObfHelper.newMethod("<init>", "net/minecraft/client/renderer/entity/RenderManager", "()V");
        ObfHelper m002 = ObfHelper.newMethod("func_78713_a", "net/minecraft/client/renderer/entity/RenderManager", "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/client/renderer/entity/Render;").setDevName("getEntityRenderObject");

        public transformer003(ClassVisitor cv)
        {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            if (m001.match(name, desc))
                return new method001(super.visitMethod(access, name, desc, signature, exceptions));
            if (m002.match(name, desc))
                return new method002(super.visitMethod(access, name, desc, signature, exceptions));
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    class transformer004 extends ClassVisitor
    {

        class method001 extends MethodVisitor
        {

            public method001(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode)
            {
                if (opcode == Opcodes.RETURN)
                {
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitFieldInsn(Opcodes.GETFIELD, f001.getData(0), f001.getData(1), f001.getData(2));
                    this.visitVarInsn(Opcodes.ASTORE, 1);
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitVarInsn(Opcodes.ALOAD, 1);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "GuiOptions_postInitGui", "(Lnet/minecraft/client/gui/GuiOptions;Ljava/util/List;)V", false);
                }
                super.visitInsn(opcode);
            }

        }

        class method002 extends MethodVisitor
        {

            public method002(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitCode()
            {
                super.visitCode();

                this.visitVarInsn(Opcodes.ALOAD, 0);
                this.visitVarInsn(Opcodes.ALOAD, 1);
                this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "GuiOptions_preActionPerformed", "(Lnet/minecraft/client/gui/GuiOptions;Lnet/minecraft/client/gui/GuiButton;)V", false);
            }

        }

        ObfHelper m001 = ObfHelper.newMethod("func_73866_w_", "net/minecraft/client/gui/GuiOptions", "()V").setDevName("initGui");
        ObfHelper m002 = ObfHelper.newMethod("func_146284_a", "net/minecraft/client/gui/GuiOptions", "(Lnet/minecraft/client/gui/GuiButton;)V").setDevName("actionPerformed");

        ObfHelper f001 = ObfHelper.newField("field_146292_n", "net/minecraft/client/gui/GuiScreen", "Ljava/util/List;").setDevName("buttonList");

        public transformer004(ClassVisitor cv)
        {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            if (m001.match(name, desc))
                return new method001(super.visitMethod(access, name, desc, signature, exceptions));
            if (m002.match(name, desc))
                return new method002(super.visitMethod(access, name, desc, signature, exceptions));
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    class transformer005 extends ClassVisitor
    {

        class method001 extends MethodVisitor
        {

            public method001(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
            {
                if (opcode == Opcodes.INVOKESPECIAL && "<init>".equals(name))
                {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);

                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitVarInsn(Opcodes.ILOAD, 3);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "ModelBiped_initWidth", "(Lnet/minecraft/client/model/ModelBiped;I)I", false);
                    this.visitVarInsn(Opcodes.ISTORE, 3);

                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitVarInsn(Opcodes.ILOAD, 4);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/init/forge/asm/Hooks", "ModelBiped_initHeight", "(Lnet/minecraft/client/model/ModelBiped;I)I", false);
                    this.visitVarInsn(Opcodes.ISTORE, 4);
                }
                else
                {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            }

        }

        ObfHelper m001 = ObfHelper.newMethod("<init>", "net/minecraft/client/model/ModelBiped", "(FFII)V");

        public transformer005(ClassVisitor cv)
        {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            if (m001.match(name, desc))
                return new method001(super.visitMethod(access, name, desc, signature, exceptions));
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        if ("net.minecraft.client.entity.AbstractClientPlayer".equals(transformedName))
            return transform001(bytes);
        if ("net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer".equals(transformedName))
            return transform002(bytes);
        if ("net.minecraft.client.renderer.entity.RenderManager".equals(transformedName))
            return transform003(bytes);
        if ("net.minecraft.client.gui.GuiOptions".equals(transformedName))
            return transform004(bytes);
        if ("net.minecraft.client.model.ModelBiped".equals(transformedName))
            return transform005(bytes);
        return bytes;
    }

    private byte[] transform001(byte[] bytes)
    {
        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classReader.accept(new transformer001(classWriter), ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    private byte[] transform002(byte[] bytes)
    {
        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classReader.accept(new transformer002(classWriter), ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    private byte[] transform003(byte[] bytes)
    {
        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classReader.accept(new transformer003(classWriter), ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    private byte[] transform004(byte[] bytes)
    {
        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classReader.accept(new transformer004(classWriter), ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    private byte[] transform005(byte[] bytes)
    {
        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classReader.accept(new transformer005(classWriter), ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

}
