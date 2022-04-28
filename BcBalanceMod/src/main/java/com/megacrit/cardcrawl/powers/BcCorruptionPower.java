package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.*;

import java.util.*;

public class BcCorruptionPower extends BcPowerBase
{
    public static final String POWER_ID = "BcCorruptionPower";
    static Color corruptedGlow = new Color(1f, 0, 1f, 1);
    ArrayList<AbstractCard> corruptedCards = new ArrayList<>();
    
    public BcCorruptionPower(boolean isUpgraded)
    {
        super(isUpgraded);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Corruption";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "corruption";
    }
    
    @Override
    public BuffDebuffApplicationType getApplicationType()
    {
        return BuffDebuffApplicationType.Unique;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "ALL Skills are now Ethereal, Exhaust, and cost 1 less.";
        }
        else
        {
            return "ALL Skills are now Ethereal, Exhaust, and are free.";
        }
    }
    //endregion
    
    @Override
    public void update(int slot)
    {
        super.update(slot);
        
        //dont know of another way to detect the cards added to hand w/o being drawn
        for (AbstractCard card : AbstractDungeon.player.hand.group)
        {
            applyCorruptionToCard(card);
        }
    }
    
    void applyCorruptionToCard(AbstractCard card)
    {
        if ((card.type == AbstractCard.CardType.SKILL) && !corruptedCards.contains(card))
        {
            corruptedCards.add(card);
            
            if (upgraded)
            {
                card.setCostForTurn(0);
            }
            else
            {
                card.modifyCostForCombat(-1);
            }
            
            //card.glowColor = corruptedGlow;
        }
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.type == AbstractCard.CardType.SKILL)
        {
            flash();
            action.exhaustCard = true;
        }
    }
    
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer)
    {
        if (isPlayer)
        {
            for (AbstractCard card : AbstractDungeon.player.hand.group)
            {
                if (card.type == AbstractCard.CardType.SKILL)
                {
                    addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                }
            }
        }
    }
}