package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.DevotionPower;
import com.megacrit.cardcrawl.vfx.combat.DevotionEffect;

public class Devotion extends BcPowerCardBase
{
    public static final String ID = "Devotion";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/power/devotion";
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
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "At the start of your turn, gain !M! Mantra.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new SFXAction("HEAL_2", -0.4F, true));
        addToBot(new VFXAction(new DevotionEffect(), 0.5F));
        
        addToBot(new BcApplyPowerAction(new DevotionPower(player, magicNumber)));
    }
}
