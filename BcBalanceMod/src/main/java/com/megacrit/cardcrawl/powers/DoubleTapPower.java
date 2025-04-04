//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.BcPowerBase;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DoubleTapPower extends BcPowerBase
{
    public static final String POWER_ID = "Double Tap";
    
    @Override
    public String getDisplayName()
    {
        return POWER_ID;
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "doubleTap";
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
        String playCount = "twice";
        if (upgraded)
        {
            playCount = "thrice";
        }
        
        String description;
        if (this.amount == 1)
        {
            description = "Your next attack is played " + playCount + ".";
        }
        else
        {
            description = "Your next " + amount + " attacks are played " + playCount + ".";
        }
        
        return description;
    }
    
    public DoubleTapPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (!card.purgeOnUse && (card.type == CardType.ATTACK) && (amount > 0))
        {
            flash();
            AbstractMonster monster = null;
            if (action.target != null)
            {
                monster = (AbstractMonster) action.target;
            }
            
            int repeat = !upgraded ? 1 : 2;
            for (int i = 0; i < repeat; i++)
            {
                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                if (monster != null)
                {
                    tmp.calculateCardDamage(monster);
                }
                
                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, monster, card.energyOnUse, true, true), true);
            }
            
            --amount;
            if (amount == 0)
            {
                addToBot(new RemoveSpecificPowerAction(owner, owner, "Double Tap"));
            }
        }
    }
    
    public void atEndOfTurn(boolean isPlayer)
    {
//        if (isPlayer)
//        {
//            addToBot(new RemoveSpecificPowerAction(owner, owner, "Double Tap"));
//        }
    }
}
