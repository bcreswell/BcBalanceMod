package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
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

public class Tranquility extends BcSkillCardBase
{
    public static final String ID = "ClearTheMind";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/tranquility";
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
        return CardRarity.COMMON;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 0 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (getMagicNumber() == 0)
        {
            return "Enter Calm.";
        }
        else
        {
            return "Enter Calm. NL When Retained, gain !M! Block.";
        }
    }
    //endregion
    
    @Override
    public void onRetained()
    {
        if ((magicNumber > 0) && (AbstractDungeon.player != null))
        {
            addToBot(new GainBlockAction(AbstractDungeon.player, block));
        }
    }
    
    public void use(AbstractPlayer player, AbstractMonster notUsed)
    {
        addToBot(new ChangeStanceAction("Calm"));
        
        if (block > 0)
        {
            addToBot(new GainBlockAction(player, block));
        }
        
//        if (magicNumber > 0)
//        {
//            addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.2F));
//
//            //inflict Weak on all enemies
//            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
//            {
//                addToBot(
//                        new ApplyPowerAction(
//                                monster,
//                                player,
//                                new WeakPower(
//                                        monster,
//                                        magicNumber,
//                                        false),
//                                magicNumber,
//                                true,
//                                AbstractGameAction.AttackEffect.NONE));
//            }
//        }
    }
}
