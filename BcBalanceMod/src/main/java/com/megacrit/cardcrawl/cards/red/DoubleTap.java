package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleTapPower;

public class DoubleTap extends BcSkillCardBase
{
    public static final String ID = "Double Tap";
    
    //region Description
    @Override
    public String getImagePath()
    {
        return "red/skill/double_tap";
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
    public String getBaseDescription()
    {
        return "Your next Attack is played twice.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        DoubleTapPower dtp = new DoubleTapPower(player,1);
        //dtp.upgraded = upgraded;
        addToBot(new BcApplyPowerAction(dtp));
    }
}
