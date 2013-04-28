package fossil.items.forge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;

public class ForgeShovel extends ItemSpade
{
	String TextureFileName;
	public ForgeShovel(int par1, EnumToolMaterial par2EnumToolMaterial,String TextureFileName0)
	{
		super(par1,par2EnumToolMaterial);
		this.TextureFileName=TextureFileName0;
	}
	
	@SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister iconRegister)
    {
		iconIndex = iconRegister.registerIcon("fossil:"+TextureFileName);
    }
}
