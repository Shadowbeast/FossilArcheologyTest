package fossil.fossilAI;

import fossil.Fossil;
import fossil.client.FossilOptions;
import fossil.entity.mob.EntityDinosaur;
import fossil.fossilEnums.EnumDinoType;
import fossil.fossilEnums.EnumSituation;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class DinoAIGrowup extends EntityAIBase
{
    protected EntityDinosaur AITarget;

    public DinoAIGrowup(EntityDinosaur var1)
    {
        this.AITarget = var1;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {  
        if (/*FossilOptions.DinoGrows && */this.AITarget.getDinoAge() < this.AITarget.MaxAge)
        {
            this.AITarget.increaseDinoAgeTick();
            return this.AITarget.getDinoAgeTick() >= this.AITarget.AgingTicks;
        }
        return false;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if(!this.AITarget.worldObj.isRemote)
        {
	        //this.AITarget.setPosition(this.AITarget.posX, this.AITarget.posY, this.AITarget.posZ);
	
	        if ((this.AITarget.CheckSpace() && this.AITarget.SelfType!=EnumDinoType.Mosasaurus) || (this.AITarget.isInWater() && this.AITarget.SelfType==EnumDinoType.Mosasaurus))
	        {
	        	this.AITarget.setDinoAgeTick(0);
	            this.AITarget.increaseDinoAge();
	            this.AITarget.worldObj.setEntityState(this.AITarget, this.AITarget.AGING_MESSAGE);
	            //this.AITarget.CheckSkin();
	            this.AITarget.updateSize();
	            if (this.AITarget.getHealth() < this.AITarget.getMaxHealth())
	            {//the dino heals itself 5% when growing up
	            	if(Fossil.FossilOptions.Heal_Dinos)
	            		this.AITarget.heal(MathHelper.ceiling_double_int(this.AITarget.getMaxHealth()*0.05f));
	            }
	            /*this.AITarget.setDinoAge(this.AITarget.getDinoAge()-1);
	            this.AITarget.worldObj.setEntityState(this.AITarget, this.AITarget.AGING_MESSAGE);
	            //this.AITarget.CheckSkin();
	
	            if (this.AITarget.getHealth() > MathHelper.ceiling_double_int(this.AITarget.getMaxHealth()*0.05f))
	            {
	            	if(Fossil.FossilOptions.Heal_Dinos)
	            		this.AITarget.attackEntityFrom(DamageSource.generic, MathHelper.ceiling_double_int(this.AITarget.getMaxHealth()*0.05f));
	            }
	
	            this.AITarget.updateSize();
	            //this.AITarget.setPosition(this.AITarget.posX, this.AITarget.posY, this.AITarget.posZ);
	
	            if (this.AITarget.isTamed())
	            {
	                this.AITarget.SendStatusMessage(EnumSituation.NoSpace);//, this.AITarget.SelfType);
	            }*/
	        }
	        else
	        	this.AITarget.SendStatusMessage(EnumSituation.NoSpace);//, this.AITarget.SelfType);
        }
    }
}
