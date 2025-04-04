package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import com.megacrit.cardcrawl.vfx.combat.FastingEffect;

public class Fasting extends BcPowerCardBase
{
    public static final String ID = "Fasting2";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/power/fasting";
    }
    
    @Override
    public int getCost()
    {
        return 2;
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
        return !upgraded ? 4 : 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !M! Strength. NL Gain !M! Dexterity. NL Gain 1 less [W] at the start of each turn.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new FastingEffect(player.hb.cX, player.hb.cY, Color.CHARTREUSE)));
        
        addToBot(new BcApplyPowerAction(new StrengthPower(player, magicNumber)));
        addToBot(new BcApplyPowerAction(new DexterityPower(player, magicNumber)));
        addToBot(new BcApplyPowerAction(new EnergyDownPower(player, 1, true)));
    }
}
