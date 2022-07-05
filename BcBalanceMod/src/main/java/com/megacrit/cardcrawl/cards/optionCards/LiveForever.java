package com.megacrit.cardcrawl.cards.optionCards;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;

public class LiveForever extends BcPowerCardBase
{
    public static final String ID = "LiveForever";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/power/live_forever";
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
        return -2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 5 : 6;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !M! *Regen.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        onChoseThisOption();
    }
    
    public void onChoseThisOption()
    {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new VFXAction(new BorderFlashEffect(Color.CHARTREUSE, true)));
        addToBot(new VFXAction(player, new MiracleEffect(Color.CHARTREUSE, Color.LIME, "BLOCK_GAIN_1"), 1.0F));
        addToBot(new BcApplyPowerAction(new RegenPower(player, magicNumber)));
    }
}
