package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class Apparition extends BcSkillCardBase
{
    public static final String ID = "Ghostly";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Apparition";
    }
    
    @Override
    public String getImagePath()
    {
        return "colorless/skill/apparition";
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
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public boolean getEthereal()
    {
        return !upgraded;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.SPECIAL;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain 1 Intangible.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(player, player, new IntangiblePlayerPower(player, 1), 1));
    }
}
