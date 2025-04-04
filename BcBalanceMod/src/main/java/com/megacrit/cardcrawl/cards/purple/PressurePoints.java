package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.TriggerMarksAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

public class PressurePoints extends BcSkillCardBase
{
    public static final String ID = "PathToVictory";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/pressure_points";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.ENEMY;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }

    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 8 : 12;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Apply !M! *Mark. NL ALL enemies lose HP equal to their *Mark.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new PressurePointEffect(monster.hb.cX, monster.hb.cY)));
        addToBot(new BcApplyPowerAction(monster, player, new MarkPower(monster, magicNumber)));
        addToBot(new TriggerMarksAction(this));
    }
}
