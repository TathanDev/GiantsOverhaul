package fr.tathan.giantsoverhaul.mixin;

import fr.tathan.giantsoverhaul.goals.GiantAttackGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Giant.class)
public class GiantMixin {

    protected void registerGoals() {
        Giant livingEntity = (Giant) ((Object) this);

        //livingEntity.goalSelector.addGoal(4, new Zombie.ZombieAttackTurtleEggGoal(this, 1.0D, 3));
        livingEntity.goalSelector.addGoal(8, new LookAtPlayerGoal(livingEntity, Player.class, 8.0F));
        livingEntity.goalSelector.addGoal(8, new RandomLookAroundGoal(livingEntity));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        Giant livingEntity = (Giant) ((Object) this);

        livingEntity.goalSelector.addGoal(2, new GiantAttackGoal(livingEntity, 0.7D, false));
        livingEntity.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(livingEntity, 1.0D));
        livingEntity.targetSelector.addGoal(1, (new HurtByTargetGoal(livingEntity)).setAlertOthers(ZombifiedPiglin.class));
        livingEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(livingEntity, Player.class, true));
        livingEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(livingEntity, AbstractVillager.class, false));
        livingEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(livingEntity, IronGolem.class, true));
        livingEntity.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(livingEntity, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.setReturnValue(Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0D).add(Attributes.MOVEMENT_SPEED, (double)0.23F).add(Attributes.ATTACK_KNOCKBACK, 1.5D).add(Attributes.ATTACK_DAMAGE, 20.0D).add(Attributes.ARMOR, 2.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE));
    }

}
