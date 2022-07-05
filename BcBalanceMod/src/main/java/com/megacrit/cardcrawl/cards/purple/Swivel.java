package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.*;

public class Swivel extends BcSkillCardBase
{
    public static final String ID = "Swivel";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/swivel";
    }
    
    @Override
    public int getCost()
    {
        return 2;
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
    public int getBlock()
    {
        return !upgraded ? 10 : 14;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL Your next Attack that would cost 2 or more will instead cost 0.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new BcApplyPowerAction(new SwivelPower(player, 1)));
    }
}
