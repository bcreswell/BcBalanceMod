package com.megacrit.cardcrawl.relics;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DeadBranch extends AbstractRelic
{
    public static final String ID = "Dead Branch";
    
    public DeadBranch()
    {
        super("Dead Branch", "deadBranch.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }
    
    public void onExhaust(AbstractCard card)
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && !card.isEthereal)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractCard newCard = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
            BcUtility.makeCardEthereal(newCard);
            BcUtility.setGlowColor(newCard, BcUtility.corruptedGlow);
            addToBot(new MakeTempCardInHandAction(newCard, false));
        }
    }
    
    public String getUpdatedDescription()
    {
        return "Whenever you #yExhaust a non-Ethereal card, add a random card to your hand. It becomes #yEthereal.";
    }
    
    public AbstractRelic makeCopy()
    {
        return new DeadBranch();
    }
}
