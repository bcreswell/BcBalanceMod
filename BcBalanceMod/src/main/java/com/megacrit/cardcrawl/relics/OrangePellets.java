package com.megacrit.cardcrawl.relics;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.rooms.*;

public class OrangePellets extends AbstractRelic
{
    public static final String ID = "OrangePellets";
    private static boolean SKILL = false;
    private static boolean POWER = false;
    private static boolean ATTACK = false;
    
    public OrangePellets()
    {
        super("OrangePellets", "pellets.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.CLINK);
    }
    
    public String getUpdatedDescription()
    {
        String desc = DESCRIPTIONS[0];
        
        if (BcUtility.isPlayerInCombat())
        {
            String attackStr = "[  ] Attack NL ";
            String powerStr = "[  ] Power NL ";
            String skillStr = "[  ] Skill";
            
            if (ATTACK)
            {
                attackStr = "[+] Attack NL ";
            }
            
            if (POWER)
            {
                powerStr = "[+] Power NL ";
            }
            
            if (SKILL)
            {
                skillStr = "[+] Skill";
            }
            
            desc += " NL NL " + attackStr + powerStr + skillStr;
        }
        
        return desc;
    }
    
    public void atTurnStart()
    {
        SKILL = false;
        POWER = false;
        ATTACK = false;
        
        updateDescription();
    }
    
    @Override
    public void onVictory()
    {
        updateDescription();
    }
    
    @Override
    public void onEnterRoom(AbstractRoom room)
    {
        updateDescription();
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.type == AbstractCard.CardType.ATTACK)
        {
            ATTACK = true;
        }
        else if (card.type == AbstractCard.CardType.SKILL)
        {
            SKILL = true;
        }
        else if (card.type == AbstractCard.CardType.POWER)
        {
            POWER = true;
        }
        
        if (ATTACK && SKILL && POWER)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new RemoveDebuffsAction(AbstractDungeon.player));
            SKILL = false;
            POWER = false;
            ATTACK = false;
        }
    
        updateDescription();
    }
    
    public void updateDescription()
    {      
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }
    
    public AbstractRelic makeCopy()
    {
        return new OrangePellets();
    }
}
