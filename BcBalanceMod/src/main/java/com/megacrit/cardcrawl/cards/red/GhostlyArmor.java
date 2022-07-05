package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GhostlyArmor extends BcSkillCardBase
{
    public static final String ID = "Ghostly Armor";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/ghostly_armor";
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
    public boolean getEthereal()
    {
        return true;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 17 : 25;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
    }
}
