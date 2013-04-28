package fossil.entity.mob;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet250CustomPayload;

public class Packet250RiderInput extends Packet250CustomPayload// implements IPacketHandler
{
    /** Name of the 'channel' used to send data */
    //public String channel;

    /** Length of the data to be read */
    //public int length;

    /** Any data */
    //public byte[] data;
	
	public float Strafe=0.0F;
	public float Forward=0.0F;
	public boolean Jump=false;
	public int Entity=0;

    public Packet250RiderInput() {}

    public Packet250RiderInput(int Entity0,float Strafe0, float Forward0, boolean Jump0)//String par1Str, byte[] par2ArrayOfByte)
    {
        /*this.channel = par1Str;
        this.data = par2ArrayOfByte;

        if (par2ArrayOfByte != null)
        {
            this.length = par2ArrayOfByte.length;

            if (this.length > 32767)
            {
                throw new IllegalArgumentException("Payload may not be larger than 32k");
            }
        }*/
    	//System.out.println(String.valueOf(Entity0)+":Packet "+String.valueOf(Strafe0));
    	this.channel="RiderInput";
    	this.Strafe=Strafe0;
    	this.Forward=Forward0;
    	this.Jump=Jump0;
    	this.Entity=Entity0;
    	ByteArrayOutputStream var3 = new ByteArrayOutputStream();
        DataOutputStream var4 = new DataOutputStream(var3);
        try
        {
        	var4.writeInt(Entity0);
        	var4.writeFloat(Strafe0);
        	var4.writeFloat(Forward0);
        	var4.writeBoolean(Jump0);
        }
        catch(Exception e)
        {
        	System.err.println("ERROR WHILE WRITING Rider Input Data to Packet");
        }
        this.data=var3.toByteArray();
    	if (this.data != null)
        {
            this.length = this.data.length;

            if (this.length > 32767)
            {
                throw new IllegalArgumentException("Payload may not be larger than 32k");
            }
        }
    	 System.out.println(String.valueOf(this.length));
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1) throws IOException
    {
        this.channel = readString(par1, 20);
        this.length = par1.readShort();
        this.Entity = par1.readInt();
        this.Strafe = par1.readFloat();
        this.Forward = par1.readFloat();
        this.Jump = par1.readBoolean();

        /*if (this.length > 0 && this.length < 32767)
        {
            this.data = new byte[this.length];
            par1DataInputStream.readFully(this.data);
        }*/
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(this.channel, par1DataOutputStream);
        par1DataOutputStream.writeShort((short)this.length);

        if (this.data != null)
        {
            par1DataOutputStream.write(this.data);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleCustomPayload(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + this.channel.length() * 2 + 2 + this.length;
    }

	/*@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		System.out.println("S' IST WAS ANGEKOMM!");
	}*/
}
