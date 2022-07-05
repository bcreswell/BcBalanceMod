//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
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
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.vfx.RainbowCardEffect;

public class Rainbow extends BcSkillCardBase
{
    public static final String ID = "Rainbow";
    
    //region card parameters
    @Override
    public int getChanneledOrbCount()
    {
        return !upgraded ? 3 : 4;
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/rainbow";
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
        return 1;
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
            return "Channel 1 Lightning, NL 1 Frost and NL 1 Dark.";
        }
        else
        {
            return "Channel 1 Lightning, NL 1 Frost, NL 1 Dark and NL 1 Plasma.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new RainbowCardEffect()));
        
        addToBot(new ChannelAction(new Lightning()));
        addToBot(new ChannelAction(new Frost()));
        addToBot(new ChannelAction(new Dark()));
        
        if (upgraded)
        {
            addToBot(new ChannelAction(new Plasma()));
        }
    }
}
