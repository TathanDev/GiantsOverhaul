package fr.tathan.giantsoverhaul.common.goals;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Giant;

public class GiantAttackGoal extends MeleeAttackGoal {

    private final Giant giant;
    private int raiseArmTicks;

    public GiantAttackGoal(Giant giant, double p_25553_, boolean p_25554_) {
        super(giant, p_25553_, p_25554_);
        this.giant = giant;
    }

    public void start() {
        super.start();
        this.raiseArmTicks = 0;
    }

    public void stop() {
        super.stop();
        this.giant.setAggressive(false);
    }

    public void tick() {
        super.tick();

        ++this.raiseArmTicks;
        if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
            this.giant.setAggressive(true);
        } else {
            this.giant.setAggressive(false);
        }

    }

}
