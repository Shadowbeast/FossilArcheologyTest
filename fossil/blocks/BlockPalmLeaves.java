package fossil.blocks;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import fossil.Fossil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class BlockPalmLeaves extends BlockLeavesBase implements IShearable
{
	private int baseIndexInPNG;
    //public static final String[] LEAF_TYPES = new String[] {"Palae_Leaves"};
    int[] adjacentTreeBlocks;
    private Icon[][] iconArray = new Icon[2][];

    public BlockPalmLeaves(int par1, int par2)
    {
    	super(par1, Material.leaves, false);
        this.setTickRandomly(true);
        setBurnProperties(this.blockID, 30, 60);
        this.setCreativeTab(Fossil.tabFBlocks);
    }
    
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    /*@Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.blockIcon[0] = par1IconRegister.registerIcon("Fossil:PalaeorapheLeaves"); //adding in a texture, 1.5.1 style!
    }*/
    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        double d0 = 0.5D;
        double d1 = 1.0D;
        return ColorizerFoliage.getFoliageColor(d0, d1);
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    @SideOnly(Side.CLIENT)
    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;

        for (int l1 = -1; l1 <= 1; ++l1)
        {
            for (int i2 = -1; i2 <= 1; ++i2)
            {
                int j2 = par1IBlockAccess.getBiomeGenForCoords(par2 + i2, par4 + l1).getBiomeFoliageColor();
                i1 += (j2 & 16711680) >> 16;
                j1 += (j2 & 65280) >> 8;
                k1 += j2 & 255;
            }
        }

        return (i1 / 9 & 255) << 16 | (j1 / 9 & 255) << 8 | k1 / 9 & 255;
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        //return (par2 & 3) == 1 ? this.iconArray[this.field_94394_cP][1] : ((par2 & 3) == 3 ? this.iconArray[this.field_94394_cP][3] : this.iconArray[this.field_94394_cP][0]);
        return this.iconArray[0][this.graphicsLevel?1:0];
    }
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.iconArray[0] = new Icon[2];
    	this.iconArray[0][1] = par1IconRegister.registerIcon("fossil:Palae_Leaves_green_fancy");
    	this.iconArray[0][0] = par1IconRegister.registerIcon("fossil:Palae_Leaves_green");
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    /*public Icon getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return this.blockIconArray[!this.isOpaqueCube() ? 0 : 1];
    }*/

	//this code is related to leaf decay. you shouldnt need to change it.
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        byte var7 = 1;
        int var8 = var7 + 1;

        if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
        {
            for (int var9 = -var7; var9 <= var7; ++var9)
            {
                for (int var10 = -var7; var10 <= var7; ++var10)
                {
                    for (int var11 = -var7; var11 <= var7; ++var11)
                    {
                        int var12 = par1World.getBlockId(par2 + var9, par3 + var10, par4 + var11);

                        if (Block.blocksList[var12] != null)
                        {
                            Block.blocksList[var12].beginLeavesDecay(par1World, par2 + var9, par3 + var10, par4 + var11);
                        }
                    }
                }
            }
        }
    }

 
    //this code applies the block to be decayed via ticks. 
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            int var6 = par1World.getBlockMetadata(par2, par3, par4);

            if ((var6 & 8) != 0 && (var6 & 4) == 0)
            {
                byte var7 = 5;
                int var8 = var7 + 1;
                byte var9 = 32;
                int var10 = var9 * var9;
                int var11 = var9 / 2;

                if (this.adjacentTreeBlocks == null)
                {
                    this.adjacentTreeBlocks = new int[var9 * var9 * var9];
                }

                int var12;

                if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
                {
                    int var13;
                    int var14;
                    int var15;

                    for (var12 = -var7; var12 <= var7; ++var12)
                    {
                        for (var13 = -var7; var13 <= var7; ++var13)
                        {
                            for (var14 = -var7; var14 <= var7; ++var14)
                            {
                                var15 = par1World.getBlockId(par2 + var12, par3 + var13, par4 + var14);

                                Block block = Block.blocksList[var15];

                                if (block != null && block.canSustainLeaves(par1World, par2 + var12, par3 + var13, par4 + var14))
                                {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
                                }
                                else if (block != null && block.isLeaves(par1World, par2 + var12, par3 + var13, par4 + var14))
                                {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
                                }
                                else
                                {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
                                }
                            }
                        }
                    }

                    for (var12 = 1; var12 <= 4; ++var12)
                    {
                        for (var13 = -var7; var13 <= var7; ++var13)
                        {
                            for (var14 = -var7; var14 <= var7; ++var14)
                            {
                                for (var15 = -var7; var15 <= var7; ++var15)
                                {
                                    if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1)
                                    {
                                        if (this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                var12 = this.adjacentTreeBlocks[var11 * var10 + var11 * var9 + var11];

                if (var12 >= 0)
                {
                    par1World.setBlock(par2, par3, par4, var6 & -9);
                }
                else
                {
                    this.removeLeaves(par1World, par2, par3, par4);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)

    //essentialy this code makes the block leak when it is rained on. 
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.canLightningStrikeAt(par2, par3 + 1, par4) && !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && par5Random.nextInt(15) == 1)
        {
            double var6 = (double)((float)par2 + par5Random.nextFloat());
            double var8 = (double)par3 - 0.05D;
            double var10 = (double)((float)par4 + par5Random.nextFloat());
            par1World.spawnParticle("dripWater", var6, var8, var10, 0.0D, 0.0D, 0.0D);
        }
    }

    private void removeLeaves(World par1World, int par2, int par3, int par4)
    {
        this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
        par1World.setBlock(par2, par3, par4, 0);
    }

   // this code deals with the drop rates
    public int quantityDropped(Random par1Random)
    {
        return par1Random.nextInt(15) == 0 ? 1 : 0;
    }

    //this makes it so sapplings can be droped if leaves are exploded. 
    public int idDropped(int par1, Random par2Random, int par3)
    {
    	return Fossil.palmSap.blockID;
    }

    //this code deals with drops and the rates. 
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!par1World.isRemote)
        {
            byte var8 = 15;

            /*if ((par5 & 3) == 3)
            {//Change probability for Jungle Trees
                var8 = 40;
            }*/

            if (par1World.rand.nextInt(var8) == 0)
            {
                int var9 = Fossil.palmSap.blockID;//this.idDropped(par5, par1World.rand, par7);
                this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var9, 1,0));// this.damageDropped(par5)));
            }

            /*if ((par5 & 3) == 0 && par1World.rand.nextInt(200) == 0)
            {
                this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Fossil.palmSap, 1, 0));
            }*/
        }
    }

    //this makes the block shearable
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
    }

    //for the wood subtypes
    /*public int damageDropped(int par1)
    {
        return par1 & 3;
    }*/

    @SideOnly(Side.CLIENT)
    //this makes the block render correctly
    public boolean isOpaqueCube()
    {
    	return !this.graphicsLevel;
    }
    
    /*public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderBlockPass()
    {
        return 1;
    }*/
    
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return true;
    }
    
    /**
     * Pass true to draw this block using fancy graphics, or false for fast graphics.
     */
    public void setGraphicsLevel(boolean var1)
    {
        this.graphicsLevel = var1;
    }
    
    //sets if the block can be sheared
    @Override
    public boolean isShearable(ItemStack item, World world, int x, int y, int z) 
    {
        return true;
    }

    //tells block to drop itself if sheared
    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune) 
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
        return ret;
    }

    //begins the leaf decay process
    @Override
    public void beginLeavesDecay(World world, int x, int y, int z)
    {
        world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 8,2);//Only for Subblocks
    }

    //tells minecraft this block is made of leaves
    @Override
    public boolean isLeaves(World world, int x, int y, int z)
    {
        return true;
    }

    

}
