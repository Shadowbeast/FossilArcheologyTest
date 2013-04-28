package fossil.blocks;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockFossilSkull extends BlockDirectional
{
    private boolean isLantern;
    private Icon Front;
    private Icon Back;

    public BlockFossilSkull(int var1, boolean var3)
    {
        super(var1, Material.pumpkin);
        //this.blockIndexInTexture = var2;
        this.setTickRandomly(true);
        this.isLantern = var3;
    }
    public int getRenderType()
    {
    	return 2303;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    /*public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        if (var1 == 1)
        {
            return 65;
        }
        else if (var1 == 0)
        {
            return 65;
        }
        else
        {
            byte var3 = 48;

            if (this.blockType)
            {
                var3 = 49;
            }

            return var2 == 2 && var1 == 2 ? var3 : (var2 == 3 && var1 == 5 ? var3 : (var2 == 0 && var1 == 3 ? var3 : (var2 == 1 && var1 == 4 ? var3 : 64)));
        }
    }*/

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    /*public int getBlockTextureFromSide(int var1)
    {
        return var1 == 1 ? 65 : (var1 == 0 ? 65 : (var1 == 3 ? 48 : 64));
    }*/
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("fossil:Skull_Side");
        this.Front = this.isLantern? par1IconRegister.registerIcon("fossil:Skull_Lantern_Front") : par1IconRegister.registerIcon("fossil:Skull_Front");
        this.Back = par1IconRegister.registerIcon("fossil:Skull_Back");//TODO: Bottom!
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par1 == 1 || par1 == 0 || (par1%2!=par2%2)? this.blockIcon : par1==par2 ? this.Front : this.Back;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        super.onBlockAdded(var1, var2, var3, var4);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6, 0);
    }
}
