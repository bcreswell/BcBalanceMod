package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
        return !upgraded ? 2 : 1;
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
    public int getMagicNumber()
    {
        return 3;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (player != null)
        {
            this.addToBot(new VFXAction(new FastingEffect(player.hb.cX, player.hb.cY, Color.CHARTREUSE)));
        }
        
        this.addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(player, player, new DexterityPower(player, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(player, player, new EnergyDownPower(player, 1, true), 1));
    }
}
