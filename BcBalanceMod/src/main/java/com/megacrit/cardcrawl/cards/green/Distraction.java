//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Distraction extends BcSkillCardBase
{
    public static final String ID = "Distraction";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/distraction";
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Create a random Skill. NL It costs 0 this turn.";
        }
        else
        {
            return "Create a random Skill. NL It costs 0 for the rest of this combat.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractCard cardToCreate = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();
        if (upgraded)
        {
            cardToCreate.cost = 0;
            cardToCreate.costForTurn = 0;
            cardToCreate.isCostModified = true;
        }
        else
        {
            cardToCreate.setCostForTurn(0);
        }
        
        addToBot(new MakeTempCardInHandAction(cardToCreate, true));
    }
}
