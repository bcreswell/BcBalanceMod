package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.AggregateEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Aggregate extends BcSkillCardBase
{
    public static final String ID = "Aggregate";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/aggregate";
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
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain [B] for every !M! cards in your draw pile.";
    }
    //endregion
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        int energyGain = AbstractDungeon.player.drawPile.size() / magicNumber;
        return "+" + energyGain + " Energy";
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new AggregateEnergyAction(magicNumber));
    }
}
