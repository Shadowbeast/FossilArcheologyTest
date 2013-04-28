package fossil.blocks;

import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import fossil.Fossil;
import fossil.gens.WorldGenPalaeoraphe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockPalmSapling extends BlockFlower
{
	public static final String[] WOOD_TYPES = new String[] {"palmSapling"};
	
	public BlockPalmSapling(int i)
	{
        super(i);
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setCreativeTab(Fossil.tabFBlocks);
	}
	
	/**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
	public void registerIcons(IconRegister var1)
    {
        this.blockIcon = var1.registerIcon("fossil:Palae_Sapling");
    }
	
	/**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return this.blockIcon;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int i, int j, int k, Random random)
    {
      super.updateTick(world, i, j, k, random);

      if ((world.getBlockLightValue(i, j + 1, k) >= 9) && (random.nextInt(30) == 0))
      {
        int l = world.getBlockMetadata(i, j, k);

        if (random.nextInt(3) == 0)
        {
          generateTree(world, i, j, k, random, l);
        }
      }
    }
    
    public void generateTree(World world, int i, int j, int k, Random random, int l)
	{
		world.setBlock(i, j, k, 0);

		WorldGenPalaeoraphe w0 = new WorldGenPalaeoraphe();
		int j1 = world.getBlockId(i, j - 1, k);
		if((j1 == Block.grass.blockID || j1 == Block.dirt.blockID) && j < 128 - 12 - 1)
        {
            w0.generate(world,random,i,j,k);
            world.setBlock(i, j, k, Fossil.palmLog.blockID);
        }
	}

}
