package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
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
//        if (!upgraded)
//        {
//            return "ALL Skills cost 1 less and Exhaust when played. NL End of turn: NL Exhaust all remaining Skills in your hand.";
//        }
//        else
//        {
//            return "ALL Skills cost 0 and Exhaust when played. NL End of turn: NL Exhaust all remaining Skills in your hand.";
//        }
        if (!upgraded)
        {
            return "ALL Skills cost 0 and Exhaust when played. NL End of turn: NL Exhaust all remaining Skills in your hand.";
        }
        else
        {
            return "ALL Skills cost 0 and Exhaust when played.";
        }
    }
    //endregion
    
//    @Override
//    public void update(int slot)
//    {
//        super.update(slot);
//
//        //putting this in update() to win the war against snecko oil.
//        for (AbstractCard card : AbstractDungeon.player.hand.group)
//        {
//            applyCorruptionToCard(card);
//        }
//    }

    @Override
    public void onInitialApplication()
    {
        for (AbstractCard card : AbstractDungeon.player.hand.group)
        {
            applyCorruptionToCard(card);
        }
    }

    @Override
    public void onCardCreated(AbstractCard card)
    {
        applyCorruptionToCard(card);
    }

    @Override
    public void onCardDraw(AbstractCard card)
    {
        applyCorruptionToCard(card);
    }
    
    void applyCorruptionToCard(AbstractCard card)
    {
        if (card.type == AbstractCard.CardType.SKILL)
        {
            card.modifyCostForCombat(-9999);

//            if (upgraded)
//            {
//                card.setCostForTurn(0);
//            }
//            else
//            {
//                card.modifyCostForCombat(-1);
//            }

            //glow == ethereal, which isn't technically what is happening here
            //BcUtility.setGlowColor(card, BcUtility.corruptedGlow);
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
    
//    public void atEndOfTurnPreEndTurnCards(boolean isPlayer)
//    {
//        if (isPlayer && !upgraded)
//        {
//            for (AbstractCard card : AbstractDungeon.player.hand.group)
//            {
//                if (card.type == AbstractCard.CardType.SKILL)
//                {
//                    addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
//                }
//            }
//        }
//    }
}