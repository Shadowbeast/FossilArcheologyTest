package fossil.blocks;

import java.util.Random;

import fossil.Fossil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class BlockFossil extends BlockStone
{
    public BlockFossil(int var1, int var2)
    {
        super(var1);
        //this.blockIndexInTexture = 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        int i = (new Random()).nextInt(1000);
        if(i < 1)		return Fossil.gem.itemID;
        if(i < 6)	return Fossil.brokenSword.itemID;
        if(i < 11)	return Fossil.brokenhelmet.itemID;
        if(i < 13) 	return Fossil.legBone.itemID;
        if(i < 15)	return Fossil.skull.itemID;
        if(i < 17)	return Fossil.claw.itemID;
        if(i < 19)  return Fossil.foot.itemID;
        if(i < 50)	return Fossil.blockSkull.blockID;
        if(i < 250)	return Fossil.biofossil.itemID;
        if(i < 450)	return Fossil.relic.itemID;
        if(i < 900)	return Item.bone.itemID;
        return Block.cobblestone.blockID;
    }
    
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.blockIcon = par1IconRegister.registerIcon("fossil:Fossil"); //adding in a texture, 1.5.1 style!
    }
    
}