package bcBalanceMod.baseCards;

import com.megacrit.cardcrawl.cards.*;

public abstract class BcCurseCardBase extends BcCardBase
{
    @Override
    public AbstractCard.CardType getCardType()
    {
        return CardType.CURSE;
    }
    
    @Override
    public AbstractCard.CardRarity getCardRarity()
    {
        return CardRarity.CURSE;
    }
    
    @Override
    public AbstractCard.CardTarget getCardTarget()
    {
        return AbstractCard.CardTarget.SELF;
    }
    
    @Override
    public boolean canUpgrade()
    {
        return false;
    }
    
    public boolean isPurgeable()
    {
        return true;
    }
}
