//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.LimitBreakAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class LimitBreak extends BcSkillCardBase
{
    public static final String ID = "Limit Break";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/limit_break";
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
        return CardRarity.RARE;
    }
    
    @Override
    public boolean getExhaust()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Double your Strength temporarily.";
        }
        else
        {
            return "Triple your Strength temporarily.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int strengthAmount = BcUtility.getPowerAmount(StrengthPower.POWER_ID);
        
        if (upgraded)
        {
            strengthAmount *= 2;
        }
    
        if (strengthAmount > 0)
        {
            addToBot(new VFXAction(player, new InflameEffect(player), 1.0F));
            addToBot(new BcApplyPowerAction(new StrengthPower(player, strengthAmount)));
            addToBot(new BcApplyPowerAction(new LoseStrengthPower(player, strengthAmount)));
        }
    }
}
