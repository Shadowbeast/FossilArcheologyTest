package fossil;

import fossil.blocks.BlockPalmSapling;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class FossilBonemealEvent
{
  @ForgeSubscribe
  public void onUseBonemeal(BonemealEvent event)
  {
    EntityPlayer player = event.entityPlayer;
    World world = event.world;
    if (event.ID == Fossil.palmSap.blockID)
    {
      if (!event.world.isRemote)
      {
        ((BlockPalmSapling)Fossil.palmSap).generateTree(event.world, event.X, event.Y, event.Z, event.world.rand, event.world.getBlockMetadata(event.X, event.Y, event.Z));
        event.setResult(event.getResult().ALLOW);
      }

    }
  }
}
