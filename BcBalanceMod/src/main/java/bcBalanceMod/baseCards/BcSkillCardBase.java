package bcBalanceMod.baseCards;

public abstract class BcSkillCardBase extends BcCardBase
{
    @Override
    public final CardType getCardType()
    {
        return CardType.SKILL;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.SELF;
    }
}
