package bcBalanceMod.baseCards;

public abstract class BcAttackCardBase extends BcCardBase
{
    public abstract int getDamage();
    
    public abstract boolean isAoeAttack();
    
    @Override
    public final CardType getCardType()
    {
        return CardType.ATTACK;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        if (isAoeAttack())
        {
            return CardTarget.ALL_ENEMY;
        }
        else
        {
            return CardTarget.ENEMY;
        }
    }
}
