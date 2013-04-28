package fossil.guiBlocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import fossil.Fossil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWorktable extends BlockContainer
{
    private Random furnaceRand = new Random();
    private final boolean isActive;
    private static boolean keepFurnaceInventory = false;
    private Icon Top;
    private Icon Bottom;
    private Icon Side2;

    public BlockWorktable(int var1, boolean var2)
    {
        super(var1, Material.rock);
        this.isActive = var2;
        //this.blockIndexInTexture = 45;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return Fossil.blockworktableIdle.blockID;
    }

    /*public String getTextureFile()
    {
        return "/fossil/textures/Fos_terrian.png";
    }*/

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        super.onBlockAdded(var1, var2, var3, var4);
        this.setDefaultDirection(var1, var2, var3, var4);
    }

    private void setDefaultDirection(World var1, int var2, int var3, int var4)
    {
        if (!var1.isRemote)
        {
            int var5 = var1.getBlockId(var2, var3, var4 - 1);
            int var6 = var1.getBlockId(var2, var3, var4 + 1);
            int var7 = var1.getBlockId(var2 - 1, var3, var4);
            int var8 = var1.getBlockId(var2 + 1, var3, var4);
            byte var9 = 3;

            if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6])var9 = 3;

            if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5])var9 = 2;

            if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8])var9 = 5;

            if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7])var9 = 4;

            var1.setBlockMetadataWithNotify(var2, var3, var4, var9,2);
        }
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    /*public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        if (var5 == 1)
        {
            return this.isActive ? 17 : 16;
        }
        else if (var5 == 0)
        {
            return 68;
        }
        else
        {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            return var5 != var6 ? 32 : 33;
        }
    }*/

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {}

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    /*public int getBlockTextureFromSide(int var1)
    {
        return var1 == 1 ? (this.isActive ? 17 : 16) : (var1 == 0 ? 68 : (var1 != 3 && var1 != 2 ? 32 : 33));
    }*/
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("fossil:Arch_Table_Side1");
        this.Side2 = par1IconRegister.registerIcon("fossil:Arch_Table_Side2");
        this.Top = this.isActive? par1IconRegister.registerIcon("fossil:Arch_Table_Top_Active") : par1IconRegister.registerIcon("fossil:Arch_Table_Top_Idle");
        this.Bottom = par1IconRegister.registerIcon("fossil:Arch_Table_Bottom");
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par1 == 1 ? this.Top : par1==0 ? this.Bottom : (par2&1)==0? this.blockIcon : this.Side2;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        if (var1.isRemote)
        {
            return true;
        }
        else
        {
        	var5.openGui(Fossil.instance, 3, var1, var2, var3, var4);
            return true;
        }
    }

    public static void updateFurnaceBlockState(boolean var0, World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);
        keepFurnaceInventory = true;

        if (var0)
        {
            var1.setBlock(var2, var3, var4, Fossil.blockworktableActive.blockID);
        }
        else
        {
            var1.setBlock(var2, var3, var4, Fossil.blockworktableIdle.blockID);
        }

        keepFurnaceInventory = false;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var5,2);
        var6.validate();
        var1.setBlockTileEntity(var2, var3, var4, var6);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new TileEntityWorktable();
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5, ItemStack par6ItemStack)
    {
        int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)var1.setBlockMetadataWithNotify(var2, var3, var4, 2,2);

        if (var6 == 1)var1.setBlockMetadataWithNotify(var2, var3, var4, 5,2);

        if (var6 == 2)var1.setBlockMetadataWithNotify(var2, var3, var4, 3,2);

        if (var6 == 3)var1.setBlockMetadataWithNotify(var2, var3, var4, 4,2);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        if (!keepFurnaceInventory)
        {
            TileEntityWorktable var7 = (TileEntityWorktable)var1.getBlockTileEntity(var2, var3, var4);

            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
            {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                    float var11 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                    float var12 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                    while (var9.stackSize > 0)
                    {
                        int var13 = this.furnaceRand.nextInt(21) + 10;

                        if (var13 > var9.stackSize)
                        {
                            var13 = var9.stackSize;
                        }

                        var9.stackSize -= var13;
                        EntityItem var14 = new EntityItem(var1, (double)((float)var2 + var10), (double)((float)var3 + var11), (double)((float)var4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
                        float var15 = 0.05F;
                        var14.motionX = (double)((float)this.furnaceRand.nextGaussian() * var15);
                        var14.motionY = (double)((float)this.furnaceRand.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = (double)((float)this.furnaceRand.nextGaussian() * var15);
                        var1.spawnEntityInWorld(var14);
                    }
                }
            }
        }

        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }
}
