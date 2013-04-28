package fossil.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fossil.entity.mob.EntityDinosaur;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCarrotOnAStick;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWhip extends ItemCarrotOnAStick
{
    public ItemWhip(int var1)
    {
        super(var1);
        //this.setIconCoord(1, 4);
    }
    @SideOnly(Side.CLIENT)

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * Returns true if this item should be rotated by 180 degrees around the Y axis when being held in an entities
     * hands.
     */
    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack I, World W, EntityPlayer P)
    {
    	if (P.isRiding() && P.ridingEntity instanceof EntityDinosaur)
        {//
    		EntityDinosaur D = (EntityDinosaur)P.ridingEntity;

            if (D.getRidingHandler().isControlledByPlayer() && I.getMaxDamage() - I.getItemDamage() >= 5)
            {
                D.getRidingHandler().boostSpeed();
                //System.out.println("SPEED BOOSTED!");
                //System.out.println("Damage before:"+String.valueOf(I.getItemDamage()));
                I.damageItem(5, P);
                //System.out.println("Damage after:"+String.valueOf(I.getItemDamage()));
                //I.setItemDamage(I.getItemDamage()+5);
                //W.playSoundEffect(P.posX, P.posY, P.posZ, "WhipCrack", 0.5F, 1.0F);
                P.ridingEntity.playSound("WhipCrack", 1.0F, 1.0F);
            }
        }
        return I;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister iconRegister)
    {
             iconIndex = iconRegister.registerIcon("fossil:Whip");
    }

}
