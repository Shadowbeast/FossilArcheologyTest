package fossil.fossilEnums;

import net.minecraft.item.Item;
import fossil.Fossil;
import fossil.entity.mob.EntityBrachiosaurus;
import fossil.entity.mob.EntityMosasaurus;
import fossil.entity.mob.EntityNautilus;
import fossil.entity.mob.EntityPlesiosaur;
import fossil.entity.mob.EntityPterosaur;
import fossil.entity.mob.EntityVelociraptor;
import fossil.entity.mob.EntityStegosaurus;
import fossil.entity.mob.EntityTRex;
import fossil.entity.mob.EntityTriceratops;
import fossil.entity.mob.EntityDilophosaurus;

interface C
{
	public static final boolean NO_MODEL =false;
	public static final boolean MODEL =true;
	public static final boolean NO_TAME =false;
	public static final boolean TAME =true;
	public static final boolean NO_RIDE =false;
	public static final boolean RIDE =true;
	public static final boolean NO_CARRY =false;
	public static final boolean CARRY =true;
}

public enum EnumDinoType
{
	//Name(Class	Modelable	Tameable	Rideable	Can Carry Items
    Triceratops( 	C.MODEL,	C.TAME,		C.RIDE,		C.NO_CARRY),
    Velociraptor( 	C.NO_MODEL,	C.TAME,		C.NO_RIDE,	C.CARRY),
    TRex( 			C.NO_MODEL,	C.NO_TAME,	C.RIDE,		C.NO_CARRY),
    Pterosaur( 		C.MODEL,	C.TAME,		C.RIDE,		C.NO_CARRY),
    Nautilus( 		C.NO_MODEL,	C.NO_TAME,	C.NO_RIDE,	C.NO_CARRY),//I think not really neccessary...
    Plesiosaur( 	C.MODEL,	C.TAME,		C.RIDE,		C.NO_CARRY),
    Mosasaurus( 	C.NO_MODEL,	C.NO_TAME,	C.NO_RIDE,	C.NO_CARRY),
    Stegosaurus( 	C.NO_MODEL,	C.TAME,		C.NO_RIDE,	C.NO_CARRY),
    Dilophosaurus(	C.NO_MODEL,	C.TAME,		C.NO_RIDE,	C.CARRY),
    Brachiosaurus(	C.MODEL,	C.TAME,		C.RIDE,		C.NO_CARRY);


    /*Triceratops(EntityTriceratops.class, 	C.MODEL,	C.TAME,		C.RIDE,		C.NO_CARRY,		Item.stick,			Fossil.rawTriceratops, 	Fossil.dnaTriceratops, 	Fossil.eggTriceratops),
    Velociraptor(EntityVelociraptor.class, 	C.NO_MODEL,	C.TAME,		C.NO_RIDE,	C.CARRY,		Item.bone,			Fossil.rawVelociraptor, Fossil.dnaVelociraptor, Fossil.eggVelociraptor),
    TRex(EntityTRex.class, 					C.NO_MODEL,	C.NO_TAME,	C.RIDE,		C.NO_CARRY,		Fossil.skullStick,	Fossil.rawTRex, 		Fossil.dnaTRex, 		Fossil.eggTRex),
    Pterosaur(EntityPterosaur.class, 		C.MODEL,	C.TAME,		C.NO_RIDE,	C.NO_CARRY,		Item.arrow,			Fossil.rawPterosaur, 	Fossil.dnaPterosaur, 	Fossil.eggPterosaur),
    Nautilus(EntityNautilus.class, 			C.NO_MODEL,	C.NO_TAME,	C.NO_RIDE,	C.NO_CARRY,		null,				Fossil.rawNautilus, 	Fossil.dnaNautilus, 	Fossil.shellNautilus),//I think not really neccessary...
    Plesiosaur(EntityPlesiosaur.class, 		C.MODEL,	C.TAME,		C.RIDE,		C.NO_CARRY,		Fossil.magicConch/*.emptyShell*,	Fossil.rawPlesiosaur, 	Fossil.dnaPlesiosaur, 	Fossil.eggPlesiosaur),
    Mosasaurus(EntityMosasaurus.class, 		C.NO_MODEL,	C.NO_TAME,	C.NO_RIDE,	C.NO_CARRY,		null,				Fossil.rawMosasaurus, 	Fossil.dnaMosasaurus, 	Fossil.eggMosasaurus),
    Stegosaurus(EntityStegosaurus.class, 	C.NO_MODEL,	C.TAME,		C.NO_RIDE,	C.NO_CARRY,		Item.stick,			Fossil.rawStegosaurus, 	Fossil.dnaStegosaurus, 	Fossil.eggStegosaurus),
    Dilophosaurus(EntityDilophosaurus.class,C.NO_MODEL,	C.TAME,		C.NO_RIDE,	C.CARRY,		Item.bone,			Fossil.rawDilophosaurus,Fossil.dnaDilophosaurus,Fossil.eggDilophosaurus),
    Brachiosaurus(EntityBrachiosaurus.class,C.MODEL,	C.TAME,		C.RIDE,		C.NO_CARRY,		Item.stick,			Fossil.rawBrachiosaurus,Fossil.dnaBrachiosaurus,Fossil.eggBrachiosaurus);*/

    public Class dinoClass;
    private final boolean modelable;
    private final boolean tameable;
    private final boolean rideable;
    private final boolean carryitems;
    public Item OrderItem;
    public Item DropItem;
    public Item DNAItem;
    public Item EggItem;

    /**
     * Params: Class, Modelable,Tameable,Rideable,CanCarryItems
     */
    private EnumDinoType(/*Class class0,*/ boolean model0,boolean tame0,boolean ride0,boolean carry0/*,Item i0*/)
    {//Class is senseless if used 
        this.dinoClass = null;//class0;
        this.modelable = model0;
        this.tameable = tame0;
        this.rideable = ride0;
        this.carryitems = carry0;
        //this.OrderItem = i0;
    }
    /*private EnumDinoType(Class clas, boolean model,boolean tame,boolean ride,boolean carry,Item i,Item drop,Item dna,Item egg)
    {
        this.dinoClass = clas;
        this.DropItem = drop;
        this.DNAItem = dna;
        this.EggItem = egg;
        this.modelable = model;
        this.tameable = tame;
        this.rideable = ride;
        this.carryitems = carry;
        this.OrderItem = i;
        //System.out.println(String.valueOf(this.DropItem!=null));
    }*/
    private void setDetails(/*Class class0,*/Item order,Item drop,Item dna,Item egg)
    {
    	//this.dinoClass=class0;
    	this.DropItem = drop;
        this.DNAItem = dna;
        this.EggItem = egg;
        this.OrderItem = order;
    }
    public static void init()
    {//								Order Item			Drop Item				DNA Item				Egg Item
    	Triceratops.setDetails(		/*EntityTriceratops.class,*/	Item.stick,			Fossil.rawTriceratops, 	Fossil.dnaTriceratops, 	Fossil.eggTriceratops);
        Velociraptor.setDetails(	/*EntityVelociraptor.class,*/	Item.bone,			Fossil.rawVelociraptor, Fossil.dnaVelociraptor, Fossil.eggVelociraptor);
        TRex.setDetails(			/*EntityTRex.class,*/			Fossil.skullStick,	Fossil.rawTRex, 		Fossil.dnaTRex, 		Fossil.eggTRex);
        Pterosaur.setDetails(		/*EntityPterosaur.class,*/		Item.arrow,			Fossil.rawPterosaur, 	Fossil.dnaPterosaur, 	Fossil.eggPterosaur);
        Nautilus.setDetails(		/*EntityNautilus.class,*/		null,				Fossil.rawNautilus, 	Fossil.dnaNautilus, 	Fossil.shellNautilus);
        Plesiosaur.setDetails(		/*EntityPlesiosaur.class,*/		Fossil.magicConch,	Fossil.rawPlesiosaur, 	Fossil.dnaPlesiosaur, 	Fossil.eggPlesiosaur);
        Mosasaurus.setDetails(		/*EntityMosasaurus.class,*/		null,				Fossil.rawMosasaurus, 	Fossil.dnaMosasaurus, 	Fossil.eggMosasaurus);
        Stegosaurus.setDetails(		/*EntityStegosaurus.class,*/	Item.stick,			Fossil.rawStegosaurus, 	Fossil.dnaStegosaurus, 	Fossil.eggStegosaurus);
        Dilophosaurus.setDetails(	/*EntityDilophosaurus.class,*/	Item.bone,			Fossil.rawDilophosaurus,Fossil.dnaDilophosaurus,Fossil.eggDilophosaurus);
        Brachiosaurus.setDetails(	/*EntityBrachiosaurus.class,*/	Item.stick,			Fossil.rawBrachiosaurus,Fossil.dnaBrachiosaurus,Fossil.eggBrachiosaurus);
    }
    public boolean isDinoDNA(Item i0)
    {
		for(int i=0;i<this.values().length;i++)
		{
		    if(this.values()[i].DNAItem.itemID == i0.itemID)
		    	return true;
		}
		return false;
    }
    public boolean isDinoDrop(Item i0)
    {
		for(int i=0;i<this.values().length;i++)
		{
		    if(this.values()[i].DropItem.itemID == i0.itemID)
		    	return true;
		}
		return false;
    }
    public Item getDNA(Item i0)
    {
		for(int i=0;i<this.values().length;i++)
		{
		    if(this.values()[i].DropItem.itemID == i0.itemID || this.values()[i].EggItem.itemID == i0.itemID)
		    	return this.values()[i].DNAItem;
		}
		return null;
    }
    public Item getDrop(Item i0)
    {
		for(int i=0;i<this.values().length;i++)
		{
		    if(this.values()[i].DNAItem.itemID == i0.itemID || this.values()[i].EggItem.itemID == i0.itemID)
			return this.values()[i].DropItem;
		}
		return null;
    }
    public Item getEgg(Item i0)
    {
		for(int i=0;i<this.values().length;i++)
		{
		    if(this.values()[i].DNAItem.itemID == i0.itemID || this.values()[i].DropItem.itemID == i0.itemID)
			return this.values()[i].EggItem;
		}
		return null;
    }
    public Class getDinoClass()
    {
        return this.dinoClass;
    }
    public boolean isModelable()
    {
        return this.modelable;
    }
    public boolean isTameable()
    {
        return this.tameable;
    }
    public boolean isRideable()
    {
        return this.rideable;
    }
    public boolean canCarryItems()
    {
        return this.carryitems;
    }
}
