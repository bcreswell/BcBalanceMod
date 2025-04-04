package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OutnumberedPower extends BcPowerBase
{
    public static final String POWER_ID = "OutnumberedPower";
    
    public OutnumberedPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Outnumbered";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "demonForm";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "When play an Attack, temporarily reduce the target's Strength by #b"+amount+" for each [G] spent.";
    }
    //endregion
    
    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster monster)
    {
        if ((card.type == AbstractCard.CardType.ATTACK) &&
            (card.costForTurn > 0) &&
            !card.freeToPlayOnce)
        {
            flash();
            
            int strengthToSap = card.costForTurn * amount;
            
            if ((card.target == AbstractCard.CardTarget.ALL_ENEMY) ||
                (card.target == AbstractCard.CardTarget.NONE))
            {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
                {
                    applyTempStrengthLoss(strengthToSap, m);
                }
            }
            else if (card.target == AbstractCard.CardTarget.ENEMY)
            {
                applyTempStrengthLoss(strengthToSap, monster);
            }
        }
    }
    
    void applyTempStrengthLoss(int strengthLoss, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(monster, player, new StrengthPower(monster, -strengthLoss), -strengthLoss, true, AbstractGameAction.AttackEffect.NONE));
        
        //only do strength up if the strength down wasn't blocked by artifact
        if (monster.getPower("Artifact") == null)
        {
            addToBot(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, strengthLoss), strengthLoss, true, AbstractGameAction.AttackEffect.NONE));
        }
    }
}
