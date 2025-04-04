package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class NaturalViolentFire extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("NaturalViolentFire");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Natural Violent Fire";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/naturalViolentFire.png";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
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
    public String getBaseDescription()
    {
        return "Start Turn in Wrath: NL Gain [W] and Draw a card. NL NL End Turn in Wrath: NL Retain your Block.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new VFXAction(player, new InflameEffect(player), 1.0F));
        addToBot(new BcApplyPowerAction(new NaturalViolentFirePower(player, 1)));
    }
}
