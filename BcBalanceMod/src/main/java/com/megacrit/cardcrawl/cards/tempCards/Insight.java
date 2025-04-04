package com.megacrit.cardcrawl.cards.tempCards;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightBulbEffect;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

public class Insight extends BcSkillCardBase
{
    public static final String ID = "Insight";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/insight";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.SPECIAL;
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
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Draw !M! cards.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (Settings.FAST_MODE)
        {
            addToBot(new VFXAction(new LightBulbEffect(player.hb)));
        }
        else
        {
            addToBot(new VFXAction(new LightBulbEffect(player.hb), 0.2F));
        }
        
        addToBot(new DrawCardAction(player, magicNumber));
    }
}
