package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Chrysalis extends BcSkillCardBase
{
    public static final String ID = "Chrysalis";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/chrysalis";
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Shuffle !M! random Skills into your draw pile. They cost 0 this combat.";
        }
        else
        {
            return "Shuffle !M! random upgraded Skills into your draw pile. They cost 0 this combat.";
        }
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for (int i = 0; i < this.magicNumber; ++i)
        {
            AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
            if (upgraded)
            {
                card.upgrade();
            }
            
            if (card.cost > 0)
            {
                card.cost = 0;
                card.costForTurn = 0;
                card.isCostModified = true;
            }
            
            this.addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
        }
    }
}
