package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;

public class Offering extends BcSkillCardBase
{
    public static final String ID = "Offering";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/offering";
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
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 6;
    }
    
    int getEnergyGain()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Sacrifice 6 HP. NL Gain [R] [R]. NL Draw !M! cards.";
        }
        else
        {
            return "Sacrifice 6 HP. NL Gain [R] [R] [R]. NL Draw !M! cards.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (Settings.FAST_MODE)
        {
            addToBot(new VFXAction(new OfferingEffect(), 0.1F));
        }
        else
        {
            addToBot(new VFXAction(new OfferingEffect(), 0.5F));
        }
        
        addToBot(new LoseHPAction(player, player, 6));
        addToBot(new GainEnergyAction(getEnergyGain()));
        addToBot(new DrawCardAction(player, magicNumber));
    }
}
