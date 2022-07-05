//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
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
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

import java.util.Iterator;

public class Corruption extends BcPowerCardBase
{
    public static final String ID = "Corruption";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/power/corruption";
    }
    
    @Override
    public int getCost()
    {
        return 3;
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
    public boolean getInnate()
    {
        return true;
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "ALL Skills cost 1 less and Exhaust when played. NL End of turn: NL Exhaust all remaining Skills in your hand.";
        }
        else
        {
            return "ALL Skills cost 0 and Exhaust when played. NL End of turn: NL Exhaust all remaining Skills in your hand.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new VFXAction(player, new VerticalAuraEffect(Color.BLACK, player.hb.cX, player.hb.cY), 0.33F));
        addToBot(new SFXAction("ATTACK_FIRE"));
        
        addToBot(new VFXAction(player, new VerticalAuraEffect(Color.PURPLE, player.hb.cX, player.hb.cY), 0.33F));
        addToBot(new VFXAction(player, new VerticalAuraEffect(Color.CYAN, player.hb.cX, player.hb.cY), 0.0F));
        addToBot(new VFXAction(player, new BorderLongFlashEffect(Color.MAGENTA), 0.0F, true));
        
        //remove the other version of Corruption if needed.
        if (upgraded)
        {
            addToBot(new RemoveSpecificPowerAction(player, player, BcCorruptionPower.POWER_ID));
            addToBot(new BcApplyPowerAction(new BcCorruptionPower(true)));
        }
        else if (!BcUtility.playerHasPower(BcCorruptionPower.POWER_ID+"+"))
        {
            //only add unupgraded corruption if upgraded version doesn't already exist
            addToBot(new BcApplyPowerAction(new BcCorruptionPower(false)));
        }
    }
}
