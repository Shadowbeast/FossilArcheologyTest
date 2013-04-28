package fossil.blocks;

import java.util.Random;

import fossil.Fossil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockVolcanicAsh extends Block
{
    public BlockVolcanicAsh(int i, Material par2Material)
    {
        super(i, Material.grass);
    }

    public int idDropped(int i, Random random, int j)
    {
        return Fossil.volcanicAsh.blockID;
    }
    
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.blockIcon = par1IconRegister.registerIcon("fossil:VolcanicAsh"); //adding in a texture, 1.5.1 style!
    }
    
}