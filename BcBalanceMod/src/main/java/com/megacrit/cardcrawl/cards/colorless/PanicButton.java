package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoBlockPower;

public class PanicButton extends BcSkillCardBase
{
    public static final String ID = "PanicButton";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/panic_button";
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
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 30 : 40;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL You cannot gain Block from cards for !M! turns.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new BcApplyPowerAction(new NoBlockPower(player, magicNumber, false)));
    }
}
