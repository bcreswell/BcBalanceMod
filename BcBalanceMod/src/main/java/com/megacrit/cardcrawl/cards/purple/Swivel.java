package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
        return !upgraded ? 8 : 12;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL Your next Attack that costs 2 or more will refund its energy.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new ApplyPowerAction(player, player, new SwivelPower(player, 1), 1));
    }
}