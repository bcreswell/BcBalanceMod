package bcBalanceMod.baseCards;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

public abstract class BcStatusCardBase extends BcCardBase
{
    @Override
    public CardType getCardType()
    {
        return CardType.STATUS;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.SELF;
    }
    
    @Override
    public boolean canUpgrade()
    {
        return false;
    }
}
