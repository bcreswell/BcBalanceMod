//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;

import java.util.Iterator;

public class PiercingWail extends BcSkillCardBase
{
    public static final String ID = "PiercingWail";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/piercing_wail";
    }
    
    @Override
    public int getCost()
    {
        return 1;
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
        return !upgraded ? 4 : 5;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Wail !M! times. NL ALL enemies temporarily lose 1 Strength each time.";
        //return "ALL enemies lose 1 Strength !M! times this turn.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        this.addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        
        for (int i = 0; i < this.magicNumber; i++)
        {
            addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveType.CHAOTIC), 0.1F));
            
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                addToBot(new ApplyPowerAction(monster, player, new StrengthPower(monster, -1), -1, true, AttackEffect.NONE));
            }
        }
        
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            int strReboundAmount = this.magicNumber;
            
            AbstractPower artifact = monster.getPower("Artifact");
            if (artifact != null)
            {
                strReboundAmount -= artifact.amount;
            }
            
            if (strReboundAmount > 0)
            {
                this.addToBot(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, strReboundAmount), strReboundAmount, true, AttackEffect.NONE));
            }
        }
    }
}
