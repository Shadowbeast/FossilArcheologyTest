package fossil.fossilEnums;

public enum EnumAnimalType
{
    Pig(3000),
    Sheep(3000),
    Cow(3000),
    Chicken(1000),
    Smilodon(4000),
    Mammoth(6000);
    
    public int GrowTime;
    
    private EnumAnimalType(int grow0)
    {
    	GrowTime=grow0;
    }
}
