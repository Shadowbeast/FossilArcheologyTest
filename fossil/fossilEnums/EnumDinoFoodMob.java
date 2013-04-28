package fossil.fossilEnums;

import fossil.Fossil;
import fossil.entity.mob.EntityMosasaurus;
import fossil.entity.mob.EntityNautilus;
import fossil.entity.mob.EntityPlesiosaur;
import fossil.entity.mob.EntityPterosaur;
import fossil.entity.mob.EntityVelociraptor;
import fossil.entity.mob.EntityStegosaurus;
import fossil.entity.mob.EntityTRex;
import fossil.entity.mob.EntityTriceratops;
import fossil.entity.mob.EntityDilophosaurus;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import fossil.entity.mob.EntityBrachiosaurus;


public enum EnumDinoFoodMob
{
	Player(EntityPlayer.class,30,2),
	Chicken(EntityChicken.class,20,2),
	Cow(EntityCow.class,50,5),
	Pig(EntityPig.class,30,3),
	Sheep(EntitySheep.class,35,3),
	Squid(EntitySquid.class,30,3),
	Mob(EntityMob.class,20,1),
	Nautilus(null,100,5),
	Triceratops(null,50,3),
	Velociraptor(null,20,3),
	TRex(null,70,5),
	Pterosaur(null,35,2),
	Plesiosaur(null,50,3),
	Mosasaurus(null,50,3),
	Stegosaurus(null,50,3),
	Dilophosaurus(null,25,2),
	Brachiosaurus(null,80,5),
	;
    public Class preyClass;
    public int FoodValue;
    public int HealValue;

    private EnumDinoFoodMob(Class pClass, int Food, int Heal)
    {
        preyClass = pClass;
        FoodValue = Food;
        HealValue = Heal;
    }
    private EnumDinoFoodMob(EnumDinoFoodMob mob0)
    {
        preyClass = mob0.preyClass;
        FoodValue = mob0.FoodValue;
        HealValue = mob0.HealValue;
    }
    public void setDetails(Class pClass)
    {
    	preyClass=pClass;
    }
    /*public static void init()
    {
    	Triceratops.setDetails(EntityTriceratops.class);
    	Velociraptor.setDetails(EntityVelociraptor.class);
    	TRex.setDetails(EntityTRex.class);
    	Pterosaur.setDetails(EntityPterosaur.class);
    	Plesiosaur.setDetails(EntityPlesiosaur.class);
    	Mosasaurus.setDetails(EntityMosasaurus.class);
    	Stegosaurus.setDetails(EntityStegosaurus.class);
    	Dilophosaurus.setDetails(EntityDilophosaurus.class);
    	Brachiosaurus.setDetails(EntityBrachiosaurus.class);
    	
    }*/
}
