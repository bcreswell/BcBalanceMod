package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

public class FreeYourMind extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Soar");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Free Your Mind";
    }
    
    @Override
    public String getImagePath()
    {
        return "colorless/Soar.png";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getCost()
    {
        return 0;
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
    public CardRarity getCardRarity()
    {
        return CardRarity.SPECIAL;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Free your hand of cards and end your turn. NL NL Then take an extra turn!";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        addToBot(new BcApplyPowerAction(new NoDrawPower(player)));
        addToBot(new DiscardAction(player, player, player.hand.size(), true));
        addToBot(new SkipEnemiesTurnAction());
        addToBot(new PressEndTurnButtonAction());
    }
}
