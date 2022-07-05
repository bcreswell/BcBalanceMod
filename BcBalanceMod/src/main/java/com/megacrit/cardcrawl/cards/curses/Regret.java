package com.megacrit.cardcrawl.cards.curses;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Regret extends BcCurseCardBase
{
    public static final String ID = "Regret";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "curse/regret";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getCost()
    {
        return -2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Unplayable. NL At the end of your turn, lose HP equal to the number of other cards in your hand.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (dontTriggerOnUseCard)
        {
            if (magicNumber > 0)
            {
                addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }
    
    public void triggerOnEndOfTurnForPlayingCard()
    {
        dontTriggerOnUseCard = true;
        magicNumber = baseMagicNumber = AbstractDungeon.player.hand.size() - 1;
        
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
}
