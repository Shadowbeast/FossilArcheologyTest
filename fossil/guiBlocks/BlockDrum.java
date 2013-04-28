package fossil.guiBlocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import fossil.Fossil;
import fossil.fossilEnums.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockDrum extends BlockContainer
{
	Icon Top1;
	Icon Top2;
	Icon Top3;
	Icon Bottom;
    public BlockDrum(int var1)
    {
        super(var1, Material.wood);
    }

    /*public String getTextureFile()
    {
        return "/fossil/textures/Fos_terrian.png";
    }*/
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return Fossil.drumID;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    /*public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        if (var1 != 1 && var1 != 0)
        {
            return 4;
        }
        else
        {
            switch (var2)
            {
                case 0:
                default:
                    return 1;

                case 1:
                    return 2;

                case 2:
                    return 3;
            }
        }
    }*/
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("fossil:Drum_Side");
        this.Top1 = par1IconRegister.registerIcon("fossil:Drum_Top1");
        this.Top2 = par1IconRegister.registerIcon("fossil:Drum_Top2");
        this.Top3 = par1IconRegister.registerIcon("fossil:Drum_Top3");
        this.Bottom = par1IconRegister.registerIcon("fossil:Drum_Bottom");
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par1 != 1 && par1 != 0)
        {
            return blockIcon;
        }
        else
        {
        	if(par1==0)
        		return Bottom;
            switch (par2)
            {
                case 0:
                default:
                    return Top1;

                case 1:
                    return Top2;

                case 2:
                    return Top3;
            }
        }
    }

    /*
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    /*public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {//plays a sound when powered....what for, this does nothing.....but keep the code...
        if (var5 > 0 && Block.blocksList[var5].canProvidePower())
        {
            boolean var6 = var1.isBlockGettingPowered(var2, var3, var4);
            TileEntityDrum var7 = (TileEntityDrum)var1.getBlockTileEntity(var2, var3, var4);

            if (var7.previousRedstoneState != var6)
            {
                if (var6)
                {
                    var7.triggerNote(var1, var2, var3, var4);
                }

                var7.previousRedstoneState = var6;
            }
        }
    }*/

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        if (!var1.isRemote)
        {
            TileEntityDrum var10 = (TileEntityDrum)var1.getBlockTileEntity(var2, var3, var4);
            var10.TriggerOrder(var5);
            var1.setBlock(var2, var3, var4, var10.Order.ordinal());
        }
        return true;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World var1, int var2, int var3, int var4, EntityPlayer var5)
    {
        if (!var1.isRemote)
        {
            TileEntityDrum var6 = (TileEntityDrum)var1.getBlockTileEntity(var2, var3, var4);
            if (var5.inventory.getCurrentItem() != null)
                var6.SendOrder(var5.inventory.getCurrentItem().itemID, var5);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new TileEntityDrum();
    }
}
