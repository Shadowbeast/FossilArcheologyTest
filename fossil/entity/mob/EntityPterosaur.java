package fossil.entity.mob;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;
import fossil.Fossil;
import fossil.fossilAI.DinoAIAttackOnCollide;
import fossil.fossilAI.DinoAIControlledByPlayer;
import fossil.fossilAI.DinoAIFollowOwner;
import fossil.fossilAI.DinoAIGrowup;
import fossil.fossilAI.DinoAIPickItems;
import fossil.fossilAI.DinoAIStarvation;
import fossil.fossilAI.DinoAIUseFeeder;
import fossil.fossilAI.DinoAIWander;
import fossil.fossilEnums.EnumDinoEating;
import fossil.fossilEnums.EnumDinoFoodItem;
import fossil.fossilEnums.EnumDinoType;
import fossil.fossilEnums.EnumOrderType;
import fossil.fossilEnums.EnumSituation;
import fossil.guiBlocks.GuiPedia;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityPterosaur extends EntityDinosaur
{
    //protected final int AGE_LIMIT = 8;
    //public final float HuntLimit = (float)(this.getHungerLimit() * 4 / 5);
    private boolean looksWithInterest;
    /*private float field_25048_b;
    private float field_25054_c;
    private boolean isWolfShaking;
    private boolean field_25052_g;
    public ItemStack ItemInMouth = null;*/
    public int LearningChestTick = 900;
   // public int SubType = 0;
    //public int BreedTick = 3000;
    public float AirSpeed = 0.0F;//Speed of the DinoAIControlledByPlayer instance
    public float AirAngle = 0.0F;
    public float AirPitch = 0.0F;
    //public float LastAirPitch = 0.0F;
    public boolean Landing = false;
    
    //public static final int AIR_SPEED_INDEX = 25;
    public static final int AIR_ANGLE_INDEX = 26;
    public static final int AIR_PITCH_INDEX = 27;

    public EntityPterosaur(World var1)
    {
        super(var1);
        this.SelfType = EnumDinoType.Pterosaur;
        this.looksWithInterest = false;
        //this.CheckSkin();
        //this.setSize(0.8F, 0.8F);
        //this.moveSpeed = 2.0F;
        this.health = 10;
        this.experienceValue=3;
        
        this.Width0=1.2F;
        this.WidthInc=0.3F;
        this.Length0=1.0F;
        this.LengthInc=0.4F;
        this.Height0=0.8F;
        this.HeightInc=0.2F;
        //this.BaseattackStrength=;
        //this.AttackStrengthIncrease=;
        //this.BreedingTime=;
        //this.BaseSpeed=;
        //this.SpeedIncrease=;
        this.MaxAge=9;
        this.BaseHealth=21;
        this.HealthIncrease=1;
        //this.AdultAge=;
        //this.AgingTicks=;
        //this.MaxHunger=;
        //this.Hungrylevel=;
        this.updateSize();
        
        FoodItemList.addItem(EnumDinoFoodItem.FishRaw);
        FoodItemList.addItem(EnumDinoFoodItem.FishCooked);
        FoodItemList.addItem(EnumDinoFoodItem.Sjl);
        FoodItemList.addItem(EnumDinoFoodItem.ChickenRaw);
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.texture = "/fossil/textures/mob/Pterosaur.png";
        //this.tasks.addTask(0, new DinoAIGrowup(this, 8));
        //this.tasks.addTask(0, new DinoAIStarvation(this));
        this.tasks.addTask(2, this.ridingHandler = new DinoAIControlledByPlayer(this));//, 0.34F));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        //this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityTRex.class, 8.0F, 0.3F, 0.35F));
        //this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityBrachiosaurus.class, 8.0F, 0.3F, 0.35F));
        this.tasks.addTask(3, new DinoAIAttackOnCollide(this, true));
        this.tasks.addTask(5, new DinoAIFollowOwner(this, 5.0F, 2.0F));
        this.tasks.addTask(6, new DinoAIUseFeeder(this, 24/*, this.HuntLimit*/, EnumDinoEating.Carnivorous));
        /*this.tasks.addTask(6, new DinoAIPickItem(this, Item.fishRaw, this.moveSpeed, 24, this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this, Item.fishCooked, this.moveSpeed, 24, this.HuntLimit));
        this.tasks.addTask(6, new DinoAIPickItem(this, Fossil.sjl, this.moveSpeed * 2.0F, 24, this.HuntLimit));*/
        this.tasks.addTask(7, new DinoAIPickItems(this, 24));
        this.tasks.addTask(7, new DinoAIWander(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return this.isModelized() ? false : true;//this.riddenByEntity == null;
    }
    
    protected void entityInit()
    {
        super.entityInit();
        //this.dataWatcher.addObject(AIR_SPEED_INDEX, new Integer(0));
        this.dataWatcher.addObject(AIR_ANGLE_INDEX, new Integer(0));
        this.dataWatcher.addObject(AIR_PITCH_INDEX, new Integer(0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setInteger("LearningChestTick", this.LearningChestTick);
        //var1.setBoolean("Angry", this.isSelfAngry());
        var1.setFloat("Airspeed", this.AirSpeed);
        var1.setFloat("AirAngle", this.AirAngle);
        var1.setFloat("AirPitch", this.AirPitch);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        //this.setSelfAngry(var1.getBoolean("Angry"));
        //this.setSelfSitting(var1.getBoolean("Sitting"));

        /*if (var1.hasKey("SubType"))
        {
            this.SubType = var1.getInteger("SubType");
        }*/

        //this.InitSize();
        this.LearningChestTick=var1.getInteger("LearningChestTick");
        this.AirSpeed = var1.getFloat("Airspeed");
        this.AirAngle = var1.getFloat("AirAngle");
        this.AirPitch = var1.getFloat("AirPitch");
    }
    /*public float getAirSpeed()
    {return (float)(this.dataWatcher.getWatchableObjectInt(AIR_SPEED_INDEX)/100F);}
    
    public void setAirSpeed(float var1)
    {this.dataWatcher.updateObject(AIR_SPEED_INDEX, Integer.valueOf((int)(var1*100)));}*/
    
    public float getAirAngle()
    {return (float)(this.dataWatcher.getWatchableObjectInt(AIR_ANGLE_INDEX)/100.0F);}
    
    public void setAirAngle(float var1)
    {this.dataWatcher.updateObject(AIR_ANGLE_INDEX, Integer.valueOf((int)(var1*100)));}
   
    public float getAirPitch()
    {return (float)(this.dataWatcher.getWatchableObjectInt(AIR_PITCH_INDEX)/100.0F);}
    
    public void setAirPitch(float var1)
    {this.dataWatcher.updateObject(AIR_PITCH_INDEX, Integer.valueOf((int)(var1*100)));}

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return this.worldObj.getClosestPlayerToEntity(this, 8.0D) != null ? "PTS_living" : null;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "PTS_hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "raptor_death";
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    /*public void onLivingUpdate()
    {
        this.HandleRiding();
        super.onLivingUpdate();
    }*/

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        if(this.LearningChestTick>0 && this.isNearbyChest() && this.isAdult())
        {
        	this.LearningChestTick--;
        	if(this.LearningChestTick==0)
        		this.SendStatusMessage(EnumSituation.LearningChest);//, this.SelfType);
        }
        /*this.field_25054_c = this.field_25048_b;

        if (this.looksWithInterest)
        {
            this.field_25048_b += (1.0F - this.field_25048_b) * 0.4F;
        }
        else
        {
            this.field_25048_b += (0.0F - this.field_25048_b) * 0.4F;
        }*/

        if (this.looksWithInterest)
        {
            this.numTicksToChaseTarget = 10;
        }
    }
    public boolean hasLearnedChest()
    {
        return this.LearningChestTick == 0;
    }
    private boolean isNearbyChest()
    {
        TileEntity var5 = null;
        for (int var6 = -10; var6 <= 10; ++var6)
        {
            for (int var7 = 0; var7 <= 3; ++var7)
            {
                for (int var8 = -10; var8 <= 10; ++var8)
                {
                    var5 = this.worldObj.getBlockTileEntity((int)(this.posX + (double)var6), (int)(this.posY + (double)var7), (int)(this.posZ + (double)var8));
                    if (var5 instanceof TileEntityChest)
                        return true;
                }
            }
        }
        return false;
    }

    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    /**
     * Disables a mob's ability to move on its own while true.
     */
    protected boolean isMovementCeased()
    {
        return this.isSitting();// || this.field_25052_g;
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        return this.isAngry() ? this.worldObj.getClosestPlayerToEntity(this, 16.0D) : null;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        return this.modelizedDrop() ? true : super.attackEntityFrom(var1, var2);
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity var1, float var2)
    {
        if (var2 > 2.0F && var2 < 5.0F && this.rand.nextInt(10) == 0)
        {
            if (this.onGround)
            {
                double var3 = var1.posX - this.posX;
                double var5 = var1.posZ - this.posZ;
                float var7 = MathHelper.sqrt_double(var3 * var3 + var5 * var5);
                this.motionX = var3 / (double)var7 * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
                this.motionZ = var5 / (double)var7 * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
                this.jump();
            }
        }
        else if ((double)var2 < 1.899999976158142D && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            var1.attackEntityFrom(DamageSource.causeMobDamage(this), 2 + this.getDinoAge());
        }
    }

    public void updateRiderPosition()
    {
        float var1 = -this.getDinoHeight();

        if (this.riddenByEntity != null)
        {
        	int heightt=10;
        	for(int i=1;i<10;i++)
        	{
        		if(this.worldObj.isBlockNormalCube(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY)-i, MathHelper.floor_double(this.posZ)))
        		{
        			heightt=i;
        			break;
        		}
        	}
        	boolean onlyAjump=heightt<3;
            if (this.onGround || this.isInWater() || onlyAjump)
            {
                this.riddenByEntity.setPosition(this.posX, this.posY - (double)var1 * 1.1D, this.posZ);
            }
            else if (this.Landing)
            {
                this.riddenByEntity.setPosition(this.posX, this.posY - (double)var1, this.posZ);
            }
            else
            {
                this.riddenByEntity.setPosition(this.posX, this.posY - (double)var1 * 0.6D, this.posZ);
                //this.riddenByEntity.setAngles(this.rotationYaw, this.AirPitch);
            }
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    /*public boolean interact(EntityPlayer var1)
    {
    	//Add special item interaction code here
        return super.interact(var1);
    }*/

    /*public void handleHealthUpdate(byte var1)
    {
        if (var1 == 7)
        {
            this.showHeartsOrSmokeFX(true);
        }
        else if (var1 == 6)
        {
            this.showHeartsOrSmokeFX(false);
        }
        else if (var1 == 8)
        {
            ;//this.field_25052_g = true;
        }
        else
        {
            super.handleHealthUpdate(var1);
        }
    }*/

   /* public boolean isSelfAngry()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    /*public boolean isSelfSitting()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }*/

    /*public void setSelfAngry(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
            this.moveSpeed = 2.0F;
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
        }
    }*/

    /*public void setSelfSitting(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
        }
    }

    public void setTamed(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 4)));
        }
        else
        {
            this.ItemInMouth = null;
            this.SendStatusMessage(EnumSituation.Bytreate);
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -5)));
        }
    }*/

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float var1)
    {
        if (this.riddenByEntity != null && !this.Landing)
        {
            this.riddenByEntity.fallDistance = var1;
        }

        int var2 = (int)Math.ceil((double)(var1 - 3.0F));

        if (!this.worldObj.isRemote)
        {
            if (var2 > 0)
            {
                int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224D - (double)this.yOffset), MathHelper.floor_double(this.posZ));

                if (var3 > 0)
                {
                    StepSound var4 = Block.blocksList[var3].stepSound;
                    this.worldObj.playSoundAtEntity(this, var4.getBreakSound(), var4.getVolume() * 0.5F, var4.getPitch() * 0.75F);
                }
            }
        }
    }

    /*private void InitSize()
    {
        //this.CheckSkin();
        this.updateSize();
        this.setPosition(this.posX, this.posY, this.posZ);
    }*/

    /*public void CheckSkin()
    {
        if (!this.isModelized())
        {
            this.texture = "/fossil/textures/Pterosaur.png";
        }
        else
        {
            this.texture = this.getModelTexture();
        }
    }*/

    public boolean CheckSpace()
    {
        return !this.isEntityInsideOpaqueBlock();
    }

    /*public boolean HandleEating(int var1)
    {
        if (this.getHunger() >= this.getHungerLimit())
        {
            return false;
        }
        else
        {
            this.increaseHunger(var1);
            this.showHeartsOrSmokeFX(false);

            if (this.getHunger() >= this.getHungerLimit())
            {
                this.setHunger(this.getHungerLimit());
            }

            return true;
        }
    }*/

    /*public void ChangeSubType(int var1)
    {
        if (var1 <= 2 && var1 >= 0)
        {
            this.SubType = var1;
            this.CheckSkin();
        }
    }*/
    
    @SideOnly(Side.CLIENT)
    public void ShowPedia(GuiPedia p0)
    {
    	super.ShowPedia(p0);
    	p0.PrintItemXY(Fossil.dnaPterosaur, 120, 7);
    	if(this.LearningChestTick==0)
    		p0.AddStringLR(Fossil.GetLangTextByKey("PediaText.Chest"), true);
    	if(this.isAdult())
    		p0.AddStringLR(Fossil.GetLangTextByKey("PediaText.Fly"), true);
    }

    /*public void ShowPedia(EntityPlayer var1)
    {
        this.PediaTextCorrection(this.SelfType, var1);

        if (this.isTamed())
        {
            Fossil.ShowMessage(OwnerText + this.getOwnerName(), var1);
            Fossil.ShowMessage(AgeText + this.getDinoAge(), var1);
            Fossil.ShowMessage(HelthText + this.health + "/" + 20, var1);
            Fossil.ShowMessage(HungerText + this.getHunger() + "/" + this.MaxHunger, var1);

            if (this.getDinoAge() >= 5)
            {
                Fossil.ShowMessage(FlyText, var1);
            }

            if (this.getDinoAge() >= 8)
            {
                Fossil.ShowMessage(RidiableText, var1);
            }
        }
        else
        {
            Fossil.ShowMessage(UntamedText, var1);
        }
    }*/

    /*public String[] additionalPediaMessage()
    {
        String[] var1 = null;

        if (!this.isTamed())
        {
            var1 = new String[] {UntamedText};
        }
        else
        {
            ArrayList var2 = new ArrayList();

            if (this.getDinoAge() >= 5)
            {
                var2.add(FlyText);
            }

            if (this.getDinoAge() >= 8)
            {
                var2.add(RidiableText);
            }

            if (!var2.isEmpty())
            {
                var1 = new String[1];
                var1 = (String[])var2.toArray(var1);
            }
        }

        return var1;
    }*/

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    public void jump()
    {
        this.motionY = 0.5D;
    }
    @Override
    public float HandleRiding(float Speed,float SpeedBoosted)
    {
        //if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityClientPlayerMP)
        //{
        //    EntityClientPlayerMP var1 = (EntityClientPlayerMP)this.riddenByEntity;
    	int heightt=10;
    	for(int i=1;i<10;i++)
    	{
    		if(this.worldObj.isBlockNormalCube(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY)-i, MathHelper.floor_double(this.posZ)))
    		{
    			heightt=i;
    			break;
    		}
    	}
    	boolean onlyAjump=heightt<3;
    	//System.out.println("H: "+String.valueOf(heightt));
    	//System.out.println("L: "+String.valueOf(this.Landing));
    	
            if (!this.onGround && !this.inWater && !onlyAjump && Speed>0F)
            {
            	if(this.AirSpeed==-1F)
            	{
            		this.AirPitch=-10F;
            		this.AirSpeed=Speed*2.5F;
            	}
            	this.Landing=this.RiderSneak;
            	/*if (!this.isCollidedVertically)
                    if (!this.Landing && this.AirPitch == 40.0F)
                        this.Landing = true;
                else
                    this.Landing = false;*/
            	
            	if(this.RiderStrafe!=0F)
            		this.AirAngle -= this.RiderStrafe;
            	else
            	{
            		if(this.AirAngle>0)
            			this.AirAngle-=0.5F;
            		if(this.AirAngle<0)
            			this.AirAngle+=0.5F;
            	}

                if (this.AirAngle > 30.0F)
                    this.AirAngle = 30.0F;

                if (this.AirAngle < -30.0F)
                    this.AirAngle = -30.0F;

                //if (Math.abs(this.AirAngle) > 10.0F)
                    //this.rotationYaw += (float)(this.AirAngle > 0.0F ? 1 : -1);
                this.rotationYaw += this.AirAngle/20F;
                

                /*while (this.rotationYaw < -180.0F)
                    this.rotationYaw += 360.0F;

                while (this.rotationYaw >= 180.0F)
                    this.rotationYaw -= 360.0F;*/
                this.rotationYaw=MathHelper.wrapAngleTo180_float(this.rotationYaw);

                if (this.Landing)
                {
                    this.AirPitch = 10.0F;
                    this.AirSpeed = 1.5F * this.getSpeed();

                    if (!this.isCollidedVertically)
                        this.motionY = -0.2D;
                    else
                        this.motionY = 0.0D;
                    this.motionX=-this.AirSpeed*MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F)*MathHelper.cos(this.AirPitch * (float)Math.PI / 180.0F);
                    this.motionZ=this.AirSpeed*MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F)*MathHelper.cos(this.AirPitch * (float)Math.PI / 180.0F);
                    this.posX+=this.motionX;
                    this.posY+=this.motionY;
                    this.posZ+=this.motionZ;
                    //this.setMoveForward(this.AirSpeed);
                }
                else
                {
                    if ((this.isCollidedHorizontally || this.isCollidedVertically) && this.AirSpeed != 0.0F)
                    {
                        this.AirSpeed = 0.0F;
                        this.motionY = -0.2F;
                        this.posY = -0.2F;
                        //this.setMoveForward(0.0F);
                        return 0;
                    }

                    /*if (this.AirSpeed == 0.0F && this.moveForward != 0.0F)
                    {
                        this.AirSpeed = this.moveForward * this.moveSpeed;
                    }*/
                    
                    if(this.RiderForward!=0F)
                    	this.AirPitch -= this.RiderForward*0.5F;
                    else
                    {
                    	if(this.AirPitch<0F)
                    		this.AirPitch+=0.5F;
                    	if(this.AirPitch>0F)
                    		this.AirPitch-=0.5F;
                    }

                    if (this.AirPitch > 40.0F)
                        this.AirPitch = 40.0F;

                    if (this.AirPitch < -60.0F)
                        this.AirPitch = -60.0F;

                    //float var2 = (float)((double)this.AirPitch * 0.017453292519943295D);

                    //if (this.LastAirPitch >= this.AirPitch)
                    {
                        //double var3 = Math.cos((double)var2);

                        //this.setMoveForward(this.AirSpeed * (float)var3);
                        float var5 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
                        float var6 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
                        
                        //this.AirSpeed=this.getSpeed()*2.5F*MathHelper.cos(this.AirPitch * (float)Math.PI / 180.0F);
                        
                        if(this.AirSpeed>this.getSpeed()*2.5*MathHelper.cos(this.AirPitch * (float)Math.PI / 180.0F) && this.AirPitch>=0F)//slow down when rising
                    		this.AirSpeed*=0.99F;
                        if(this.AirSpeed>this.getSpeed()*2.5/MathHelper.cos(this.AirPitch * (float)Math.PI / 180.0F) && this.AirPitch<0F)//slow down when too fast
                    		this.AirSpeed*=0.995F;
                        if(this.AirSpeed<this.getSpeed()*2.5*MathHelper.cos(this.AirPitch * (float)Math.PI / 180.0F))
                    		this.AirSpeed*=1.01F;
                    	if(this.AirPitch<0F)
                    		this.AirSpeed-=MathHelper.sin(this.AirPitch * (float)Math.PI / 180.0F)*0.005F;//get faster
                        
                        this.motionX=-this.AirSpeed*(1.0F+(SpeedBoosted>0?0.3F:0F))*MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F)*MathHelper.cos(this.AirPitch * (float)Math.PI / 180.0F);
                        this.motionZ=this.AirSpeed*(1.0F+(SpeedBoosted>0?0.3F:0F))*MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F)*MathHelper.cos(this.AirPitch * (float)Math.PI / 180.0F);
                        this.motionY=/*0.20000000298023224D+*/this.AirSpeed*(1.0F+(SpeedBoosted>0?0.3F:0F))*MathHelper.sin(this.AirPitch * (float)Math.PI / 180.0F);

                        //System.out.println("AirSpeed: "+String.valueOf(this.AirSpeed));
                        /*System.out.println("AirAngle: "+String.valueOf(this.AirAngle));
                    	System.out.println("AirPitch: "+String.valueOf(this.AirPitch));
                    	System.out.println("W: "+String.valueOf(this.rotationYaw));
                    	System.out.println("X: "+String.valueOf(this.motionX));*/
                    	//System.out.println("Y: "+String.valueOf(this.motionY));
                    	/*System.out.println("Z: "+String.valueOf(this.motionZ));
                    	System.out.println("T: "+String.valueOf(this.ticksExisted));*/
                        
                        this.posX+=this.motionX;
                        this.posY+=this.motionY;
                        this.posZ+=this.motionZ;
                        /*if (this.AirPitch < 60.0F && this.moveForward > 0.1F)
                        {
                            this.motionY = Math.sin((double)var2) * 0.4D;
                        }*/
                    }

                    //this.LastAirPitch = this.AirPitch;
                }
            }
            else
            {
            	this.AirPitch=0;
            	this.AirAngle=0;
            	this.AirSpeed=-1F;//Indicates that the dino was on the ground last
                /*if (this.AirSpeed != 0.0F)
                {
                    this.AirSpeed = 0.0F;
                }

                if (this.AirAngle != 0.0F)
                {
                    this.AirAngle = 0.0F;
                }

                if (this.AirPitch != 0.0F)
                {
                    this.AirPitch = 0.0F;
                }

                for (this.rotationYaw -= this.RiderStrafe * 5.0F; this.rotationYaw < -180.0F; this.rotationYaw += 360.0F)
                {
                    ;
                }

                while (this.rotationYaw >= 180.0F)
                {
                    this.rotationYaw -= 360.0F;
                }

                this.setMoveForward(this.RiderForward * this.moveSpeed);*/
            	if(this.RiderForward>0)
            		Speed += (this.getSpeed()*2.0F - Speed) * 0.3F*this.RiderForward;
            	else
            		if(Speed>0)
            		{
            			Speed += (this.getSpeed()*2.0F - Speed) * 0.8F*this.RiderForward;//Break faster
            			if(Speed<0)Speed=0;
            		}
            		else
            			Speed += (this.getSpeed()*2.0F - Speed) * 0.3F*this.RiderForward;
            	this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.RiderStrafe*5.0F);
            	if(this.RiderJump)
                	this.getJumpHelper().setJumping();
            	//this.motionY-=0.08;
                this.moveEntityWithHeading(0.0F, Speed+Speed*(0.3F+0.85F*MathHelper.sin(SpeedBoosted*(float)Math.PI)));
            }
        //}
        this.setAirAngle(this.AirAngle);
        this.setAirPitch(this.AirPitch);
    	return Speed;
    }
    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void updateFallState(double par1, boolean par3) {}

    public EntityPterosaur spawnBabyAnimal(EntityAgeable var1)
    {
        return new EntityPterosaur(this.worldObj);
    }

   /* public void updateSize()
    {
        this.setSize((float)(0.800000011920929D + 0.2D * (double)((float)this.getDinoAge())), (float)(0.800000011920929D + 0.2D * (double)((float)this.getDinoAge())));
    }

    public float getGLX()
    {
        return (float)(0.800000011920929D + 0.2D * (double)((float)this.getDinoAge()));
    }

    public float getGLY()
    {
        return (float)(0.800000011920929D + 0.2D * (double)((float)this.getDinoAge()));
    }*/

    public EntityAgeable func_90011_a(EntityAgeable var1)
    {
        return this.spawnBabyAnimal(var1);
    }

	@Override
	public EntityAgeable createChild(EntityAgeable var1) 
	{
		return null;
	}
	/**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void moveEntityWithHeading(float par1, float par2)
    {
        double var9;

        if (this.isInWater())
        {
            var9 = this.posY;
            this.moveFlying(par1, par2, this.isAIEnabled() ? 0.04F : 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
            this.motionY -= 0.02D;

            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var9, this.motionZ))
            {
                this.motionY = 0.30000001192092896D;
            }
        }
        else if (this.handleLavaMovement())
        {
            var9 = this.posY;
            this.moveFlying(par1, par2, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
            this.motionY -= 0.02D;

            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var9, this.motionZ))
            {
                this.motionY = 0.30000001192092896D;
            }
        }
        else
        {
            float var3 = 0.91F;

            if (this.onGround)
            {
                var3 = 0.54600006F;
                int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

                if (var4 > 0)
                {
                    var3 = Block.blocksList[var4].slipperiness * 0.91F;
                }
            }

            float var8 = 0.16277136F / (var3 * var3 * var3);
            float var5;

            if (this.onGround)
            {
                if (this.isAIEnabled())
                {
                    var5 = this.getAIMoveSpeed();
                }
                else
                {
                    var5 = this.landMovementFactor;
                }

                var5 *= var8;
            }
            else
            {
                var5 = this.jumpMovementFactor;
            }

            this.moveFlying(par1, par2, var5);
            var3 = 0.91F;

            if (this.onGround)
            {
                var3 = 0.54600006F;
                int var6 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

                if (var6 > 0)
                {
                    var3 = Block.blocksList[var6].slipperiness * 0.91F;
                }
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            if (this.isCollidedHorizontally && this.isOnLadder())
            {
                this.motionY = 0.2D;
            }

            if (this.worldObj.isRemote && (!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ) || !this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded))
            {
                if (this.posY > 0.0D)
                {
                    this.motionY = -0.1D;
                }
                else
                {
                    this.motionY = 0.0D;
                }
            }
            else
            {
            	if(this.riddenByEntity==null || this.AirSpeed<=0F)
            		this.motionY -= 0.08D;
            }

            this.motionY *= 0.9800000190734863D;
            this.motionX *= (double)var3;
            this.motionZ *= (double)var3;
        }

        //this.prevLegYaw = this.legYaw;
        var9 = this.posX - this.prevPosX;
        double var12 = this.posZ - this.prevPosZ;
        float var11 = MathHelper.sqrt_double(var9 * var9 + var12 * var12) * 4.0F;

        if (var11 > 1.0F)
        {
            var11 = 1.0F;
        }

        //this.legYaw += (var11 - this.legYaw) * 0.4F;
        //this.legSwing += this.legYaw;
    }
}
