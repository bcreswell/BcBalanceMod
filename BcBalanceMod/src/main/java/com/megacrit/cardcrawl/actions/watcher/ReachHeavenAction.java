package com.megacrit.cardcrawl.actions.watcher;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class ReachHeavenAction extends AbstractGameAction
{
    AbstractCard cardToCreate;
    
    public ReachHeavenAction(AbstractCard cardToCreate)
    {
        this.cardToCreate = cardToCreate;
    }
    
    public void update()
    {
        if (BcUtility.isPlayerInStance(DivinityStance.STANCE_ID))
        {
            addToBot(new MakeTempCardInHandAction(cardToCreate.makeStatEquivalentCopy(), 1));
        }
        else
        {
            addToBot(new MakeTempCardInDrawPileAction(cardToCreate.makeStatEquivalentCopy(), 1, true, true));
        }
        
        isDone = true;
    }
}
