package bcBalanceMod.baseCards;

public abstract class BcPowerCardBase extends BcCardBase
{
    @Override
    public final CardType getCardType()
    {
        return CardType.POWER;
    }
    
    @Override
    public final CardTarget getCardTarget()
    {
        return CardTarget.SELF;
    }
}
