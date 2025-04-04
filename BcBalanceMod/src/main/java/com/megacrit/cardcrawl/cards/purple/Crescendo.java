package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class Crescendo extends BcSkillCardBase
{
    public static final String ID = "Crescendo";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/crescendo";
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
        return CardRarity.RARE;
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
    
    @Override //vulnerable count
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        String description = "Remove your Weak. NL Enter Wrath.";
        
        if (magicNumber > 0)
        {
            description = "Inflict !M! Vulnerable NL on ALL Enemies. NL " + description;
        }
        
        return description;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster notUsed)
    {
        addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.RED_TEXT_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.3F));
        
        if (magicNumber > 0)
        {
            //inflict Vulnerable on all enemies
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                addToBot(
                    new ApplyPowerAction(
                        monster,
                        player,
                        new VulnerablePower(
                            monster,
                            magicNumber,
                            false),
                        magicNumber,
                        true,
                        AbstractGameAction.AttackEffect.NONE));
            }
        }
        
        if (BcUtility.getPowerAmount(WeakPower.POWER_ID) > 0)
        {
            addToBot(new RemoveSpecificPowerAction(player, player, WeakPower.POWER_ID));
        }
        
        addToBot(new ChangeStanceAction("Wrath"));
    }
}