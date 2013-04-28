package fossil.client;

import net.minecraftforge.common.Configuration;

public class FossilOptions
{
    /*public static boolean ShouldAnuSpawn = true;
    public static boolean SpawnShipwrecks = true;
    public static boolean SpawnWeaponShop = true;
    public static boolean SpawnAcademy = true;
    public static boolean DinoGrows = true;
    public static boolean DinoHunger = true;
    public static boolean TRexBreakingBlocks = false;
    public static boolean BraBreakingBlocks = false;
    public static boolean PalaeorapheSpawn = false;
    public static final String[] negWords = new String[] {"false", "no", "off", "close"};

    public static boolean isNegtiveWord(String var0)
    {
        for (int var1 = 0; var1 < negWords.length; ++var1)
        {
            if (var0.equalsIgnoreCase(negWords[var1]))
            {
                return true;
            }
        }

        return false;
    }*/
	public static boolean Gen_Palaeoraphe;
	public static boolean Gen_Academy;
	public static boolean Gen_Ships;
	public static String  Lang_Server;
	public static boolean Heal_Dinos;
	public static boolean Dinos_Starve;
	public static boolean Dino_Block_Breaking;
	public static boolean Skull_Overlay;
	
	public void Load(Configuration config)
	{
		Gen_Palaeoraphe = config.get("option", "Palaeoraphe", false).getBoolean(false);
		Gen_Academy = config.get("option", "Academy", true).getBoolean(true);
		Gen_Ships = config.get("option", "Ships", true).getBoolean(true);
		Lang_Server = config.get("option", "Serverlanguage", "en_US").getString();
		Heal_Dinos = config.get("option", "Heal_Dinos", true).getBoolean(true);
		Dinos_Starve = config.get("option", "Dinos_Starve", true).getBoolean(true);
		Dino_Block_Breaking = config.get("option", "Dino_Block_Breaking", true).getBoolean(true);
		Skull_Overlay = config.get("option", "Skull_Overlay", false).getBoolean(false);
	}
}
