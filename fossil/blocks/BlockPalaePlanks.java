package fossil.blocks;

import fossil.Fossil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockPalaePlanks extends Block
{

	public BlockPalaePlanks(int par1, Material var2Material)
    {
        super(par1, Material.wood);
        this.setCreativeTab(Fossil.tabFBlocks);
    }
    
	@Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.blockIcon = par1IconRegister.registerIcon("fossil:Palae_Planks"); //adding in a texture, 1.5.1 style!
    }
	
}
