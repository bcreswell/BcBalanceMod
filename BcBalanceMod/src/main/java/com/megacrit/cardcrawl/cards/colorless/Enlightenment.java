package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.EnlightenmentAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

public class Enlightenment extends BcSkillCardBase
{
    public static final String ID = "Enlightenment";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/enlightenment.png";
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !M! Mantra. NL NL Reduce the cost of all cards in your hand by 1 this turn.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        addToBot(new BcApplyPowerAction(new MantraPower(player, magicNumber)));
        addToBot(new EnlightenmentAction(false));
    }
}
