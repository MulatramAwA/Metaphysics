package com.example.cryptography.mixin;

import com.example.cryptography.CoordinateAPI;
import com.example.cryptography.CryptographyAPI;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.IntSupplier;

import static com.example.cryptography.Cryptography.ComputerPosMapper;

@Mixin(TurtleBlockEntity.class)
public abstract class TileTurtleMixin {
    private BlockPos pos;

    @Inject(method = "createComputer",at = @At("RETURN"),remap = false)
    private void createComputer(int id, CallbackInfoReturnable<ServerComputer> cir){
        ComputerPosMapper.put(id, pos);
        cir.getReturnValue().addAPI(new CryptographyAPI());
        Level level = cir.getReturnValue().getLevel();
        cir.getReturnValue().addAPI(new CoordinateAPI(ComputerPosMapper.get(id), id, level, cir.getReturnValue().getComputer()));
    }

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    public void TurtleBlockEntity(BlockEntityType type, BlockPos pos, BlockState state, IntSupplier fuelLimit, ComputerFamily family, CallbackInfo ci) {
        this.pos = pos;
    }
}