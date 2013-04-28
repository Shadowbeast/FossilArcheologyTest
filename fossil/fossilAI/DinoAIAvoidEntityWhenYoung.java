package fossil.fossilAI;

import fossil.entity.mob.EntityDinosaur;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class DinoAIAvoidEntityWhenYoung extends EntityAIAvoidEntity
{
    EntityDinosaur dinoEntity;

    public DinoAIAvoidEntityWhenYoung(EntityCreature var1, Class var2, float var3, float var4, float var5)
    {
        super(var1, var2, var3, var4, var5);
        this.dinoEntity = (EntityDinosaur)var1;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.dinoEntity.isAdult() ? false : super.shouldExecute();
    }
}
