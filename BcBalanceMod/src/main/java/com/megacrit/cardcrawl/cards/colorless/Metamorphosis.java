package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Metamorphosis extends BcSkillCardBase
{
    public static final String ID = "Metamorphosis";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/metamorphosis";
    }
    
    @Override
    public int getCost()
    {
        return 1;
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
            return "Shuffle !M! random Attacks into your draw pile. They cost 0 this combat.";
        }
        else
        {
            return "Shuffle !M! random upgraded Attacks into your draw pile. They cost 0 this combat.";
        }
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for (int i = 0; i < this.magicNumber; ++i)
        {
            AbstractCard card = BcUtility.getRandomCard(
                null,
                CardType.ATTACK,
                false,
                true,
                false,
                true);
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
